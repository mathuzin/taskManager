package org.example.view;

import org.example.controller.SystemMonitorController;

import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {

    public JanelaPrincipal() {
        super("Mini Task Manager Manual");

        // cria os painéis
        PainelDeProcessos painelProcessos = new PainelDeProcessos();
        PainelDePerformance painelPerformance = new PainelDePerformance();

        // cria o controlador que já inicia o Timer interno
        new SystemMonitorController(painelProcessos, painelPerformance);

        // layout da janela
        setLayout(new BorderLayout());

        // painel de performance em cima
        add(painelPerformance, BorderLayout.NORTH);

        // tabela de processos no centro, com scroll
        add(new JScrollPane(painelProcessos), BorderLayout.CENTER);

        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JanelaPrincipal::new);
    }
}
