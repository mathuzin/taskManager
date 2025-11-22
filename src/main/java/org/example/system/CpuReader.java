package org.example.system;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CpuReader {

    private static final OperatingSystemMXBean osBean =
            (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    /**
     * Calcula % de CPU por processo com base no delta de tempo de CPU.
     */
    public static Map<Integer, Double> computeCpuPercent(
            Map<Integer, Double> oldValues,
            List<RawProcessInfo> currentList
    ) {
        Map<Integer, Double> result = new HashMap<>();
        int cores = Runtime.getRuntime().availableProcessors();

        for (RawProcessInfo raw : currentList) {
            double oldVal = oldValues.getOrDefault(raw.pid, raw.cpuTotalSeconds);
            double delta = raw.cpuTotalSeconds - oldVal;

            // delta é em segundos de CPU desde a última leitura
            double percent = Math.max(delta * 100 / cores, 0);

            result.put(raw.pid, percent);
        }

        return result;
    }

    /**
     * Uso global da CPU (0.0 a 1.0).
     */
    public static double readSystemCpuUsage() {
        return osBean.getSystemCpuLoad() * 100.0;
    }
}
