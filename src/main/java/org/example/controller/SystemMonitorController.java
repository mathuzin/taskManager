package org.example.controller;

import org.example.system.*;
import org.example.view.PainelDePerformance;
import org.example.view.PainelDeProcessos;

import javax.swing.*;
import javax.swing.Timer;
import java.util.*;

public class SystemMonitorController {

    private final PainelDeProcessos processos;
    private final PainelDePerformance performance;

    // mapa de valores antigos de CPU por processo
    private Map<Integer, Double> oldCpuValues = new HashMap<>();

    public SystemMonitorController(PainelDeProcessos processos, PainelDePerformance performance) {
        this.processos = processos;
        this.performance = performance;
        iniciarAtualizacao();
    }

    public List<ProcessInfo> getProcessInfos() {
        List<ProcessInfo> result = new ArrayList<>();

        List<RawProcessInfo> list = ProcessReader.readProcesses();
        Map<Integer, Double> cpuMap = CpuReader.computeCpuPercent(oldCpuValues, list);

        // atualiza oldCpuValues para próxima rodada
        oldCpuValues.clear();
        for (RawProcessInfo raw : list) {
            oldCpuValues.put(raw.pid, raw.cpuTotalSeconds);
        }

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

            // uso global da CPU
            double cpuTotal = CpuReader.readSystemCpuUsage();

            performance.addCpuValue(cpuTotal);

            double memPercent = (MemoryReader.getUsedMemoryMB() / MemoryReader.getTotalMemoryMB()) * 100.0;
            performance.addMemoryValue(memPercent);
        });

        timer.start();
    }
}
