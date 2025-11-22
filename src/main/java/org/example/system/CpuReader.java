package org.example.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CpuReader {

    public static Map<Integer, Double> computeCpuPercent(
            Map<Integer, Double> oldValues,
            List<RawProcessInfo> currentList
    ) {
        Map<Integer, Double> result = new HashMap<>();
        int cores = Runtime.getRuntime().availableProcessors();

        for (RawProcessInfo raw : currentList) {
            double oldVal = oldValues.getOrDefault(raw.pid, raw.cpuTotalSeconds);
            double delta = raw.cpuTotalSeconds - oldVal;

            double percent = Math.max(delta / 1.0 * 100 / cores, 0);

            result.put(raw.pid, percent);
        }

        return result;
    }

    public static Map<Integer, Double> readCpuUsage() {
        return new HashMap<>();
    }
}
