package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PainelDePerformance extends JPanel {

    private final List<Double> cpuHistory = new ArrayList<>();
    private final List<Double> memHistory = new ArrayList<>();

    public PainelDePerformance() {
        setPreferredSize(new Dimension(900, 200));
    }

    public void addCpuValue(double value) {
        if (cpuHistory.size() > 300) cpuHistory.remove(0);
        cpuHistory.add(value);
        repaint();
    }

    public void addMemoryValue(double value) {
        if (memHistory.size() > 300) memHistory.remove(0);
        memHistory.add(value);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth();
        int h = getHeight();

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, w, h);

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, w - 1, h - 1);

        drawHistory(g, cpuHistory, w, h, Color.GREEN);

        drawHistory(g, memHistory, w, h, Color.BLUE);

        if (!cpuHistory.isEmpty()) {
            double lastCpu = cpuHistory.get(cpuHistory.size() - 1);
            g.setColor(Color.GREEN);
            g.drawString(String.format("CPU: %.1f%%", lastCpu), 10, 20);
        }
        if (!memHistory.isEmpty()) {
            double lastMem = memHistory.get(memHistory.size() - 1);
            g.setColor(Color.BLUE);
            g.drawString(String.format("Memória: %.1f%%", lastMem), 10, 40);
        }
    }

    private void drawHistory(Graphics g, List<Double> history, int w, int h, Color color) {
        if (history.isEmpty()) return;

        g.setColor(color);
        int step = history.size() > 1 ? w / history.size() : w;

        int prevX = 0;
        int prevY = h - (int) ((history.get(0) / 100.0) * h);

        int x = 0;
        for (double v : history) {
            int y = h - (int) ((v / 100.0) * h);
            g.drawLine(prevX, prevY, x, y);
            prevX = x;
            prevY = y;
            x += step;
        }
    }
}
