package org.example.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessReader {

    public List<ProcessInfo> getProcessList() {
        List<ProcessInfo> list = new ArrayList<>();

        try {
            Process p = Runtime.getRuntime().exec("tasklist /fo csv /nh");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.replace("\"", "").split(",");

                if (parts.length >= 5) {
                    String name = parts[0];
                    int pid = Integer.parseInt(parts[1]);
                    double memBytes = parseMemory(parts[4]);
                    list.add(new ProcessInfo(name, pid, memBytes));
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    private Double parseMemory(String memory) {
        try {
            memory = memory.replace(",", "").trim();
            Double value = Double.parseDouble(memory);

            return value;
        } catch (Exception e) {
            return 0.0;
        }
    }
}
