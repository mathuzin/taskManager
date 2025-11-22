package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PainelDePerformance extends JPanel {

    private final List<Double> history = new ArrayList<>();

    public void addCpuValue(double value) {
        if (history.size() > 300) history.remove(0);
        history.add(value);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth();
        int h = getHeight();

        g.drawRect(0, 0, w - 1, h - 1);

        int x = 0;
        for (double v : history) {
            int y = (int) (h - (v / 100.0) * h);
            g.drawLine(x, h, x, y);
            x++;
        }
    }
}
