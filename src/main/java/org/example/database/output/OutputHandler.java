package org.example.database.output;

import java.util.List;
import java.util.Map;

public class OutputHandler {
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
}
