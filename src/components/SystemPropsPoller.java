package components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SystemPropsPoller {

    public static class ProcessProps {
        public String pid;
        public double cpuUsage;
        public long memorySize;
        public int fileDescriptorCount;

        public ProcessProps(String pid, double cpuUsage, long memorySize, int fileDescriptorCount) {
            this.pid = pid;
            this.cpuUsage = cpuUsage;
            this.memorySize = memorySize;
            this.fileDescriptorCount = fileDescriptorCount;
        }
    }

    public static Map<String, ProcessProps> getSystemProps() {
        Map<String, ProcessProps> props = new HashMap<>();
        String os = System.getProperty("os.name").toLowerCase();

        try {
            Process process;
            if (os.contains("win")) {
                process = Runtime.getRuntime().exec("tasklist");
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                process = Runtime.getRuntime().exec("ps -eo pid,pcpu,rss");
            } else {
                throw new UnsupportedOperationException("Unsupported operating system");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 3) {
                    String pid = parts[0];
                    double cpuUsage = Double.parseDouble(parts[1]);
                    if (cpuUsage == 0.0) {
                        continue;
                    }
                    long memorySize = Long.parseLong(parts[2]);
                    Process processLsof = new ProcessBuilder("lsof", "-p", String.valueOf(pid)).start();
                    BufferedReader readerLsof = new BufferedReader(new InputStreamReader(processLsof.getInputStream()));
                    int fileDescriptorCount = 0;
                    while (readerLsof.readLine() != null) {
                        fileDescriptorCount++;
                    }
                    props.put(pid, new ProcessProps(pid, cpuUsage, memorySize, fileDescriptorCount));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

}