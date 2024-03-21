package components;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Window extends JFrame {
    private final Canvas canvas;

    public Window() {
        canvas = new Canvas();
        add(canvas, BorderLayout.CENTER);
        canvas.setBackground(Color.BLACK);
        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Bouncing Ball Game");
        setVisible(true);

        Map<String, SystemPropsPoller.ProcessProps> processProps = SystemPropsPoller.getSystemProps();

        processProps.forEach((pid, props) -> {
            canvas.addBall(props);
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                canvas.refreshBalls();
            }
        }, 10000, 10000);
        });

    }
}