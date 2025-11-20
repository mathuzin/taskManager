package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class PainelDePerformance extends JPanel {
    private final LinkedList<Double> values = new LinkedList<>();
    private final int maxValues = 120;

    public void addCpuValue(double v) {
        if (values.size() >= maxValues) {
            values.removeFirst();
        }

        values.add(v);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth();
        int h = getHeight();

        g.setColor(new Color(30, 30, 30));
        g.fillRect(0, 0, w, h);

        g.setColor(new Color(70, 70, 70));
        for (int i = 1; i < 5; i++) {
            int y = h * i / 5;
            g.drawLine(0, y, w, y);
        }

        g.setColor(Color.WHITE);
        g.drawString("CPU (%)", 10, 20);

        if (values.size() < 2)
            return;

        int step = w / maxValues;
        int x = w - values.size() * step;

        int lastX = x;
        int lastY = h - (int) (values.getFirst() / 100.0 * h);

        g.setColor(new Color(50, 200, 255));

        int index = 0;
        for (double val: values) {
            int y = h - (int) (val / 100.0 * h);
            int xx = x + index * step;

            g.drawLine(lastX, lastY, xx, y);

            lastX = xx;
            lastY = y;
            index++;
        }
    }
}
