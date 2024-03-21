package components;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Canvas extends JPanel implements ActionListener {
    private final Map<String, Ball> balls;
    public static final int TIMER_DELAY = 10;

    public Canvas() {
        balls = new HashMap<>();
        Timer timer = new Timer(TIMER_DELAY, this);
        timer.start();
    }

    public void addBall(SystemPropsPoller.ProcessProps processProps) {
        Random rand = new Random();
        int x = rand.nextInt(Constants.WINDOW_WIDTH - 30) + 10;
        int y = rand.nextInt(Constants.WINDOW_HEIGHT - 30) + 10;
        balls.put(processProps.pid, new Ball(x, y, processProps));
        repaint();
    }

    public void removeAllBalls() {
        balls.clear();
        repaint();
    }

    public void refreshBalls() {
        removeAllBalls();
        Map<String, SystemPropsPoller.ProcessProps> processProps = SystemPropsPoller.getSystemProps();
        processProps.forEach((pid, props) -> {
            addBall(props);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Ball ball : balls.values()) {
            ball.move(getWidth(), getHeight());
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Ball ball : balls.values()) {
            ball.draw(g);
        }
    }
}

