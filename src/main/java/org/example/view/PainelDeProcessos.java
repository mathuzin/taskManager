package org.example.view;

import org.example.system.ProcessInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Locale;

public class PainelDeProcessos extends JPanel {

    private JTable tabela;
    private DefaultTableModel modelo;

    public PainelDeProcessos() {
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(
                new Object[]{"Nome", "Memória (MB)", "CPU (%)"}, 0
        );

        tabela = new JTable(modelo);

        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    public void updateTable(List<ProcessInfo> processos) {
        modelo.setRowCount(0);

        for (ProcessInfo p : processos) {
            modelo.addRow(new Object[]{
                    p.getName(),
                    String.format(Locale.US, "%.2f", p.getMemoryMB()),
                    String.format(Locale.US, "%.2f", p.getCpu())
            });
        }
    }
}
