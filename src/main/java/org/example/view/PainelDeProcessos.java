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

    public PainelDeProcessos() {
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(
                new Object[]{"Nome", "Memória (MB)", "CPU (%)"}, 0
        );

        tabela = new JTable(modelo);

        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    public void updateTable(List<ProcessInfo> processos) {
        this.processosAtuais = processos;
        aplicarFiltro();
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

    // 🔍 Filtrar por nome
    public void filtrarPorNome(String termo) {
        this.termoPesquisa = termo; // guarda termo
        aplicarFiltro();
    }

    private void aplicarFiltro() {
        if (processosAtuais == null) return;
        if (termoPesquisa == null || termoPesquisa.isEmpty()) {
            preencherTabela(processosAtuais); // mostra todos
        } else {
            List<ProcessInfo> filtrados = processosAtuais.stream()
                    .filter(p -> p.getName().toLowerCase().contains(termoPesquisa.toLowerCase()))
                    .collect(Collectors.toList());
            preencherTabela(filtrados);
        }
    }
}
