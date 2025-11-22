package org.example.view;

import org.example.controller.SystemMonitorController;

import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {

    private final PainelDeProcessos painelProcessos;
    private final PainelDePerformance painelPerformance;
    private final JPanel painelCentral;
    private final CardLayout cardLayout;

    private final JPanel painelPesquisa;
    private final JTextField campoPesquisa;

    public JanelaPrincipal() {
        super("Mini Task Manager Manual");

        painelProcessos = new PainelDeProcessos();
        painelPerformance = new PainelDePerformance();

        new SystemMonitorController(painelProcessos, painelPerformance);

        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        painelCentral = new JPanel(cardLayout);
        painelCentral.add(painelPerformance, "PERFORMANCE");
        painelCentral.add(painelProcessos, "PROCESSOS");

        painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        campoPesquisa = new JTextField(20);
        JButton btnPesquisar = new JButton("Pesquisar");

        painelPesquisa.add(new JLabel("Nome do processo:"));
        painelPesquisa.add(campoPesquisa);
        painelPesquisa.add(btnPesquisar);
        painelPesquisa.setVisible(false);

        btnPesquisar.addActionListener(e -> {
            String termo = campoPesquisa.getText().trim();
            painelProcessos.filtrarPorNome(termo);
        });

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnTrocar = new JButton("Mostrar Processos");
        painelBotoes.add(btnTrocar);

        btnTrocar.addActionListener(e -> {
            cardLayout.next(painelCentral);
            if (painelPerformance.isShowing()) {
                btnTrocar.setText("Mostrar Processos");
                painelPesquisa.setVisible(false);
            } else {
                btnTrocar.setText("Mostrar Gráfico");
                painelPesquisa.setVisible(true);
            }
        });

        add(painelPesquisa, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JanelaPrincipal::new);
    }
}
