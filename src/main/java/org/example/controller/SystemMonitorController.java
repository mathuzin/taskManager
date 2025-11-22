package org.example.controller;

import org.example.system.CpuReader;
import org.example.system.MemoryReader;
import org.example.system.ProcessReader;
import org.example.system.RawProcessInfo;
import org.example.system.ProcessInfo;
import org.example.view.PainelDePerformance;
import org.example.view.PainelDeProcessos;

import javax.swing.*;
import javax.swing.Timer;
import java.util.*;

public class SystemMonitorController {

    private final PainelDeProcessos painelProcessos;
    private final PainelDePerformance painelPerformance;

    private final Map<Integer, Double> oldCpuValues = new HashMap<>();

    private long lastTimestamp = System.nanoTime();

    public SystemMonitorController(PainelDeProcessos painelProcessos,
                                   PainelDePerformance painelPerformance) {
        this.painelProcessos = painelProcessos;
        this.painelPerformance = painelPerformance;

        Timer timer = new Timer(1000, e -> atualizar());
        timer.start();
    }

    private void atualizar() {
        List<RawProcessInfo> rawList = ProcessReader.readProcesses();

        long now = System.nanoTime();
        double elapsedSeconds = (now - lastTimestamp) / 1_000_000_000.0;
        lastTimestamp = now;

        Map<Integer, Double> cpuMap = new HashMap<>();
        int cores = Runtime.getRuntime().availableProcessors();

        for (RawProcessInfo raw : rawList) {
            double oldVal = oldCpuValues.getOrDefault(raw.pid, raw.cpuTotalSeconds);
            double deltaCpuSeconds = raw.cpuTotalSeconds - oldVal;

            double percent = Math.max((deltaCpuSeconds / elapsedSeconds) * 100.0 / cores, 0);
            cpuMap.put(raw.pid, percent);
        }

        oldCpuValues.clear();
        for (RawProcessInfo raw : rawList) {
            oldCpuValues.put(raw.pid, raw.cpuTotalSeconds);
        }

        List<ProcessInfo> processos = new ArrayList<>();
        for (RawProcessInfo raw : rawList) {
            double cpu = cpuMap.getOrDefault(raw.pid, 0.0);
            processos.add(new ProcessInfo(
                    raw.pid,
                    raw.name,
                    cpu,
                    raw.memory / 1024.0 / 1024.0
            ));
        }

        painelProcessos.updateTable(processos);

        double cpuTotal = processos.stream().mapToDouble(ProcessInfo::getCpu).sum();
        double memPercent = (MemoryReader.getUsedMemoryMB() / MemoryReader.getTotalMemoryMB()) * 100.0;

        painelPerformance.addCpuValue(cpuTotal);
        painelPerformance.addMemoryValue(memPercent);
    }
}
