package org.example.database.output;

import org.example.database.TransitDataBundle;
import org.example.database.output.data_export.CSVExporter;

import java.util.Map;

public class OutputHandler {
    private CSVExporter csvExporter;

    // Constructor to initialize CSVExporter
    public OutputHandler() {
        // Initialize the CSVExporter instance here
        this.csvExporter = new CSVExporter();
    }

    // Constructor to initialize CSVExporter with an external instance
    public OutputHandler(CSVExporter csvExporter) {
        this.csvExporter = csvExporter;
    }

    public void printTableData(TransitDataBundle dataBundle) {
        if (dataBundle.columns.isEmpty() || dataBundle.data.isEmpty()) {
            System.out.println("No data found.");
            return;
        }

        // Print column headers
        for (String column : dataBundle.columns) {
            System.out.print(column + "\t");
        }
        System.out.println();

        // Print rows of data
        for (Map<String, Object> row : dataBundle.data) {
            for (String column : dataBundle.columns) {
                System.out.print(row.get(column) + "\t");
            }
            System.out.println();
        }
    }

    public void exportTableDataToCSV(TransitDataBundle dataBundle, String pathToCsv) {
        if (dataBundle.columns.isEmpty() || dataBundle.data.isEmpty()) {
            System.out.println("No data to export.");
            return;
        }

        if (csvExporter == null) {
            throw new IllegalStateException("CSVExporter is not initialized.");
        }

        try {
            csvExporter.csv_exporter(dataBundle, pathToCsv);
            System.out.println("Data successfully exported to " + pathToCsv);
        } catch (Exception e) {
            System.err.println("Failed to export data: " + e.getMessage());
        }
    }

    public void outputHandler(TransitDataBundle dataBundle) {
        printTableData(dataBundle);
    }
}
