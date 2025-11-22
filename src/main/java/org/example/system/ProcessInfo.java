package org.example.system;

public class ProcessInfo {

    private int pid;
    private String name;
    private double cpu;
    private double memoryMB;

    public ProcessInfo(int pid, String name, double cpu, double memoryMB) {
        this.pid = pid;
        this.name = name;
        this.cpu = cpu;
        this.memoryMB = memoryMB;
    }

    public int getPid() { return pid; }
    public String getName() { return name; }
    public double getCpu() { return cpu; }
    public double getMemoryMB() { return memoryMB; }
}
