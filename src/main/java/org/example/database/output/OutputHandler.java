package org.example.database.output;

import org.example.database.output.data_export.CSVExporter;

import java.util.List;
import java.util.Map;

public class OutputHandler {
    private CSVExporter csvExporter;

    public OutputHandler() {
        this.csvExporter = new CSVExporter();
    }

    public void printTableData(List<String> columns, List<Map<String, Object>> data) {
        if (columns.isEmpty() || data.isEmpty()) {
            System.out.println("No data found.");
            return;
        }

        // Print column headers
        for (String column : columns) {
            System.out.print(column + "\t");
        }
        System.out.println();

        // Print rows of data
        for (Map<String, Object> row : data) {
            for (String column : columns) {
                System.out.print(row.get(column) + "\t");
            }
            System.out.println();
        }
    }

    public void exportTableDataToCSV(List<String> columns, List<Map<String, Object>> data, String pathToCsv) {
        if (columns.isEmpty() || data.isEmpty()) {
            System.out.println("No data to export.");
            return;
        }

        try {
            csvExporter.csv_exporter(columns, data, pathToCsv);
            System.out.println("Data successfully exported to " + pathToCsv);
        } catch (Exception e) {
            System.err.println("Failed to export data: " + e.getMessage());
        }
    }

    public void outputHandler(List<String> columns, List<Map<String, Object>> data) {
        printTableData(columns, data);
    }
}
