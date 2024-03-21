package components;

import java.awt.*;

public class Ball {
    double x;
    double y;
    double radius;
    double dx = Constants.DELTA_X;
    double dy = Constants.DELTA_Y;
    private Color color;

    public Ball(int x, int y, SystemPropsPoller.ProcessProps props) {
        this.x = x;
        this.y = y;
        this.radius = calculateSize(props.memorySize);
        double speedFactor = calculateSpeed(props.cpuUsage);
        this.dx = Constants.DELTA_X * speedFactor;
        this.dy = Constants.DELTA_Y * speedFactor;
        this.color = calculateColor(props.fileDescriptorCount);
    }

    public void move(int width, int height) {
        x += dx;
        y += dy;
        if (x - radius < 0 || x + radius > width) {
            dx *= -1;
        }
        if (y - radius < 0 || y + radius > height) {
            dy *= -1;
        }
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval((int) (x - radius), (int) (y - radius), (int) radius * 2, (int) radius * 2);
    }

    private double calculateSize(double memorySize) {
        return Math.log(memorySize);
    }

    private double calculateSpeed(double cpuUsage) {
        return 1 + cpuUsage / 100.0;
    }

    private Color calculateColor(int fileDescriptorCount) {
        int blue = (int)((fileDescriptorCount / 1000.0) * 255);
        return new Color(0, 255 - blue, blue);
    }
}
