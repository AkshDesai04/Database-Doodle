package org.example.database;

import org.example.TransitDataBundle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlatFileReader {

    private ConnectionManager connectionManager;

    public FlatFileReader(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    // Reads the CSV file and returns data as a TransitDataBundle object
    public TransitDataBundle getCsvFileData(String filePath) {
        List<Map<String, Object>> data = new ArrayList<>();
        List<String> columns = getCsvFileColumns(filePath); // Get column names

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip the header line
                    continue;
                }

                String[] values = line.split(",");
                Map<String, Object> row = new HashMap<>();
                for (int i = 0; i < values.length; i++) {
                    row.put(columns.get(i), values[i]);
                }
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        TransitDataBundle dataBundle = new TransitDataBundle();
        dataBundle.columns = columns;
        dataBundle.data = data;
        return dataBundle;
    }

    // Reads the CSV file and returns the column names
    public List<String> getCsvFileColumns(String filePath) {
        List<String> columns = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            if (line != null) {
                String[] columnNames = line.split(",");
                for (String columnName : columnNames) {
                    columns.add(columnName.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return columns;
    }
}
