package org.example.system;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CpuReader {

    private static final OperatingSystemMXBean osBean =
            (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    private static long lastTimestamp = System.nanoTime();

    public static Map<Integer, Double> computeCpuPercent(
            Map<Integer, Double> oldValues,
            List<RawProcessInfo> currentList
    ) {
        Map<Integer, Double> result = new HashMap<>();
        int cores = Runtime.getRuntime().availableProcessors();

        long now = System.nanoTime();
        double elapsedSeconds = (now - lastTimestamp) / 1_000_000_000.0;
        lastTimestamp = now;

        for (RawProcessInfo raw : currentList) {
            double oldVal = oldValues.getOrDefault(raw.pid, raw.cpuTotalSeconds);
            double deltaCpuSeconds = raw.cpuTotalSeconds - oldVal;

            double percent = Math.max((deltaCpuSeconds / elapsedSeconds) * 100.0 / cores, 0);

            result.put(raw.pid, percent);
        }

        return result;
    }

    public static double readSystemCpuUsage() {
        return osBean.getSystemCpuLoad() * 100.0;
    }
}
