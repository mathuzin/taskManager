package org.example.view;

import org.example.controller.SystemMonitorController;

import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {

    public JanelaPrincipal() {
        super("Mini Task Manager Manual");

        PainelDeProcessos processo = new PainelDeProcessos();
        PainelDePerformance performance = new PainelDePerformance();

        new SystemMonitorController(processo, performance);

        setLayout(new BorderLayout());
        add(processo, BorderLayout.CENTER);
        add(performance, BorderLayout.SOUTH);

        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
