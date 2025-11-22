package org.example.view;

import org.example.controller.SystemMonitorController;
import org.example.system.ProcessInfo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JanelaPrincipal extends JFrame {

    public JanelaPrincipal() {
        super("Mini Task Manager Manual");

        PainelDeProcessos painelProcessos = new PainelDeProcessos();
        PainelDePerformance painelPerformance = new PainelDePerformance();

        SystemMonitorController controller =
                new SystemMonitorController(painelProcessos, painelPerformance);

        setLayout(new BorderLayout());
        add(painelProcessos, BorderLayout.CENTER);
        add(painelPerformance, BorderLayout.SOUTH);

        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        Timer timer = new Timer(1000, e -> {
            List<ProcessInfo> processos = controller.getProcessInfos();

            painelProcessos.updateTable(processos);

            double cpuTotal = processos.stream()
                    .mapToDouble(ProcessInfo::getCpu)
                    .sum();

            painelPerformance.addCpuValue(cpuTotal);
        });

        timer.start();
    }
}
