package org.example.system;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

public class MemoryReader {

    private static final OperatingSystemMXBean osBean =
            (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    public static double getTotalMemoryMB() {
        return osBean.getTotalPhysicalMemorySize() / 1024.0 / 1024.0;
    }

    public static double getFreeMemoryMB() {
        return osBean.getFreePhysicalMemorySize() / 1024.0 / 1024.0;
    }

    public static double getUsedMemoryMB() {
        return getTotalMemoryMB() - getFreeMemoryMB();
    }
}
