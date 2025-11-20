package org.example.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CpuReader {

    public double getCpuUsage() {
        try {
            Process p = Runtime.getRuntime().exec("wmic cpu get loadpercentage");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            br.readLine();
            String line = br.readLine();

            if (line != null && !line.trim().isEmpty()) {
                return Double.parseDouble(line.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }
}
