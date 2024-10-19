package org.example.database.grouping;

import org.example.TransitDataBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grouping {
    public static TransitDataBundle groupBy(TransitDataBundle dataBundle, String groupByColumn) {
        List<String> columns = dataBundle.columns;

        // Check if the group by column exists in the data bundle
        if (!columns.contains(groupByColumn)) {
            throw new IllegalArgumentException("Group by column not found in the data bundle");
        }

        // Create a map to hold grouped data
        Map<Object, List<Map<String, Object>>> groupedDataMap = new HashMap<>();

        // Group the data
        for (Map<String, Object> row : dataBundle.data) {
            Object groupValue = row.get(groupByColumn);
            groupedDataMap.computeIfAbsent(groupValue, k -> new ArrayList<>()).add(row);
        }

        // Prepare the result TransitDataBundle
        TransitDataBundle resultDataBundle = new TransitDataBundle();
        resultDataBundle.columns = new ArrayList<>(columns);
        resultDataBundle.data = new ArrayList<>();

        // For each group, create a combined row (you can modify how to combine the rows as needed)
        for (Map.Entry<Object, List<Map<String, Object>>> entry : groupedDataMap.entrySet()) {
            Object groupKey = entry.getKey();
            List<Map<String, Object>> groupRows = entry.getValue();

            // Create a new row for the group
            Map<String, Object> groupedRow = new HashMap<>();
            groupedRow.put(groupByColumn, groupKey); // Add the group key

            // Optionally combine other column values as needed (e.g., summing, averaging, etc.)
            for (Map<String, Object> row : groupRows) {
                for (String col : columns) {
                    if (!col.equals(groupByColumn)) {
                        // Here, we can choose to combine the values in some way (e.g., average, sum)
                        // For simplicity, we'll just keep the first non-null value we encounter
                        if (groupedRow.get(col) == null) {
                            groupedRow.put(col, row.get(col));
                        }
                    }
                }
            }

            // Add the grouped row to the result data bundle
            resultDataBundle.data.add(groupedRow);
        }

        return resultDataBundle;
    }
}
