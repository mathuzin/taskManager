package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class MiniTaskManager extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private SystemInfo si;
    private OperatingSystem os;
    private java.util.Map<Integer, OSProcess> cacheAnterior = new java.util.HashMap<>();
    private final int logicalCpus = new SystemInfo().getHardware().getProcessor().getLogicalProcessorCount();



    public MiniTaskManager() {
        super("Mini Task Manager - Java + Swing");

        si = new SystemInfo();
        os = si.getOperatingSystem();

        model = new DefaultTableModel(new Object[]{"PID", "Nome", "CPU %", "Memória (MB)"}, 0);
        table = new JTable(model);

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        Timer timer = new Timer(1500, e -> atualizarTabela());
        timer.start();

        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void atualizarTabela() {
        model.setRowCount(0);

        List<OSProcess> processos = os.getProcesses(
                p -> true,
                Comparator.comparingDouble(OSProcess::getProcessCpuLoadCumulative).reversed(),
                40
        );

        for (OSProcess p : processos) {

            OSProcess anterior = cacheAnterior.get(p.getProcessID());

            double cpuPercent = 0.0;

            if (anterior != null) {
                double load = p.getProcessCpuLoadBetweenTicks(anterior);
                cpuPercent = load * logicalCpus;
            }

            cacheAnterior.put(p.getProcessID(), p);

            model.addRow(new Object[]{
                    p.getProcessID(),
                    p.getName(),
                    String.format("%.1f", cpuPercent),
                    String.format("%.1f", p.getResidentSetSize() / (1024.0 * 1024))
            });
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(MiniTaskManager::new);
    }
}
