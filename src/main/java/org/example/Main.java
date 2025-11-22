package org.example;

import org.example.controller.SystemMonitorController;
import org.example.view.PainelDePerformance;
import org.example.view.PainelDeProcessos;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame janela = new JFrame("Mini Gerenciador de Tarefas");
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            janela.setSize(900, 600);
            janela.setLayout(new BorderLayout());

            PainelDeProcessos painelDeProcessos = new PainelDeProcessos();
            PainelDePerformance painelDePerformance = new PainelDePerformance();

            janela.add(painelDeProcessos, BorderLayout.CENTER);
            janela.add(painelDePerformance, BorderLayout.SOUTH);

            // CONTROLLER DEFINITIVO
            new SystemMonitorController(painelDeProcessos, painelDePerformance);

            janela.setVisible(true);
        });
    }
}
