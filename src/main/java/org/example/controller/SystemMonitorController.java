package org.example.controller;

import org.example.system.CpuReader;
import org.example.system.ProcessInfo;
import org.example.system.ProcessReader;
import org.example.view.PainelDePerformance;
import org.example.view.PainelDeProcessos;

import javax.swing.*;
import java.util.List;

public class SystemMonitorController {

    private ProcessReader processReader = new ProcessReader();
    private CpuReader cpuReader = new CpuReader();

    private PainelDeProcessos painelDeProcessos;
    private PainelDePerformance painelDePerformance;

    public SystemMonitorController(PainelDeProcessos painelDeProcessos, PainelDePerformance painelDePerformance) {
        this.painelDeProcessos = painelDeProcessos;
        this.painelDePerformance = painelDePerformance;

        new Timer(1000, e -> update()).start();
    }

    private void update() {
        double cpu = cpuReader.getCpuUsage();
        painelDePerformance.addCpuValue(cpu);

        var processos = processReader.getProcessList();
        painelDeProcessos.updateTable(processos);
    }
}
