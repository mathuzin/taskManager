package org.example.system;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

public class MemoryReader {
    public static void printMemory() {
        OperatingSystemMXBean osBean =
                (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        long total = osBean.getTotalPhysicalMemorySize();
        long free = osBean.getFreePhysicalMemorySize();
        long used = total - free;

        System.out.println("Total: " + total / (1024*1024) + " MB");
        System.out.println("Usada: " + used / (1024*1024) + " MB");
        System.out.println("Livre: " + free / (1024*1024) + " MB");
    }
}
