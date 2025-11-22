package org.example.controller;

import org.example.system.CpuReader;
import org.example.system.ProcessInfo;
import org.example.system.ProcessReader;
import org.example.system.RawProcessInfo;
import org.example.view.PainelDePerformance;
import org.example.view.PainelDeProcessos;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SystemMonitorController {

    private final PainelDeProcessos processos;
    private final PainelDePerformance performance;

    public SystemMonitorController(PainelDeProcessos processos, PainelDePerformance performance) {
        this.processos = processos;
        this.performance = performance;
        iniciarAtualizacao();
    }

    public List<ProcessInfo> getProcessInfos() {
        List<ProcessInfo> result = new ArrayList<>();

        List<RawProcessInfo> list = ProcessReader.readProcesses();
        Map<Integer, Double> cpuMap = CpuReader.readCpuUsage();

        for (RawProcessInfo raw : list) {
            double cpu = cpuMap.getOrDefault(raw.pid, 0.0);

            result.add(new ProcessInfo(
                    raw.pid,
                    raw.name,
                    cpu,
                    raw.memory / 1024.0 / 1024.0
            ));
        }

        return result;
    }

    private void iniciarAtualizacao() {
        Timer timer = new Timer(1000, e -> {
            List<ProcessInfo> lista = getProcessInfos();

            processos.updateTable(lista);

            double cpuTotal = lista.stream()
                    .mapToDouble(ProcessInfo::getCpu)
                    .sum();

            performance.addCpuValue(cpuTotal);
        });

        timer.start();
    }

}
