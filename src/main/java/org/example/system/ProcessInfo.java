package org.example.system;

public class ProcessInfo {
    private String name;
    private int pid;
    private double memoryBytes;

    public ProcessInfo(String name, int pid, double memoryBytes) {
        this.name = name;
        this.pid = pid;
        this.memoryBytes = memoryBytes;
    }

    public String getName() { return name; }
    public int getPid() { return pid; }
    public double getMemoryBytes() { return memoryBytes; }

    public double getMemoryMB() {
        return memoryBytes / (1024.0 * 1024);
    }
}
