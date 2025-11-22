package org.example.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessReader {

    public static List<RawProcessInfo> readProcesses() {
        List<RawProcessInfo> list = new ArrayList<>();

        try {
            Process process = new ProcessBuilder(
                    "powershell",
                    "-Command",
                    "Get-Process | Select-Object Id,ProcessName,PM,CPU | ConvertTo-Csv -NoTypeInformation"
            )
                    .redirectErrorStream(true)
                    .start();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "Cp1252"))) {
                String line = br.readLine(); // cabeçalho
                if (line == null) return list;

                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    String[] cols = splitCsvLine(line);
                    if (cols.length < 4) continue;

                    try {
                        int pid = Integer.parseInt(cols[0].trim());
                        String name = cols[1].trim();

                        long mem = Long.parseLong(cols[2].trim());
                        String cpuStr = cols[3].trim();
                        cpuStr = cpuStr.replace(',', '.');

                        double cpuTotalSeconds = cpuStr.isEmpty()
                                ? 0.0
                                : Double.parseDouble(cpuStr);

                        RawProcessInfo r = new RawProcessInfo();
                        r.pid = pid;
                        r.name = name;
                        r.memory = mem;
                        r.cpuTotalSeconds = cpuTotalSeconds;

                        list.add(r);
                    } catch (Exception ignored) {}
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private static String[] splitCsvLine(String line) {
        List<String> cols = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
                continue;
            }
            if (c == ',' && !inQuotes) {
                cols.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }

        cols.add(cur.toString());
        return cols.toArray(new String[0]);
    }
}
