package org.example.view;

import org.example.system.ProcessInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PainelDeProcessos extends JPanel {

    private JTable tabela;
    private DefaultTableModel modelo;
    private List<ProcessInfo> processosAtuais; // lista completa
    private String termoPesquisa = ""; // guarda o termo atual

    private final JLabel lblCpuTotal;
    private final JLabel lblMemTotal;

    public PainelDeProcessos() {
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(
                new Object[]{"Nome", "Memória (MB)", "CPU (%)"}, 0
        );

        tabela = new JTable(modelo);

        JPanel painelTotais = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblCpuTotal = new JLabel("CPU Total: 0.0%");
        lblMemTotal = new JLabel("Memória Total: 0.0 MB");
        painelTotais.add(lblCpuTotal);
        painelTotais.add(lblMemTotal);

        add(painelTotais, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    public void updateTable(List<ProcessInfo> processos) {
        this.processosAtuais = processos;
        aplicarFiltro();
        atualizarTotais();
    }

    private void preencherTabela(List<ProcessInfo> processos) {
        modelo.setRowCount(0);
        for (ProcessInfo p : processos) {
            modelo.addRow(new Object[]{
                    p.getName(),
                    String.format(Locale.US, "%.2f", p.getMemoryMB()),
                    String.format(Locale.US, "%.2f", p.getCpu())
            });
        }
    }

    public void filtrarPorNome(String termo) {
        this.termoPesquisa = termo;
        aplicarFiltro();
        atualizarTotais();
    }

    private void aplicarFiltro() {
        if (processosAtuais == null) return;
        if (termoPesquisa == null || termoPesquisa.isEmpty()) {
            preencherTabela(processosAtuais);
        } else {
            List<ProcessInfo> filtrados = processosAtuais.stream()
                    .filter(p -> p.getName().toLowerCase().contains(termoPesquisa.toLowerCase()))
                    .collect(Collectors.toList());
            preencherTabela(filtrados);
        }
    }

    private void atualizarTotais() {
        if (processosAtuais == null) return;

        double cpuTotal = processosAtuais.stream().mapToDouble(ProcessInfo::getCpu).sum();
        double memTotal = processosAtuais.stream().mapToDouble(ProcessInfo::getMemoryMB).sum();

        lblCpuTotal.setText(String.format("CPU Total: %.2f%%", cpuTotal));
        lblMemTotal.setText(String.format("Memória Total: %.2f MB", memTotal));
    }
}
