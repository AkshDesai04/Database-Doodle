package org.example.database.joining;

import org.example.TransitDataBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Joining {
    public static TransitDataBundle performJoin(TransitDataBundle dataBundle1, TransitDataBundle dataBundle2, String joinColumn1, String joinColumn2) {
        List<String> columnNames1 = dataBundle1.columns;
        List<String> columnNames2 = dataBundle2.columns;

        // Check if the join columns exist in both data bundles
        if (!columnNames1.contains(joinColumn1) || !columnNames2.contains(joinColumn2)) {
            throw new IllegalArgumentException("Join column not found in one of the data bundles");
        }

        // Create a map to store the rows of the second data bundle based on the join column from dataBundle2
        Map<Object, Map<String, Object>> joinMap = new HashMap<>();

        // Fill the join map with dataBundle2, using joinColumn2 for matching
        for (Map<String, Object> row : dataBundle2.data) {
            Object joinValue = row.get(joinColumn2);
            joinMap.put(joinValue, row);
        }

        List<Map<String, Object>> joinedData = new ArrayList<>();

        // Perform the join operation, using joinColumn1 from dataBundle1
        for (Map<String, Object> row1 : dataBundle1.data) {
            Object joinValue = row1.get(joinColumn1);
            Map<String, Object> row2 = joinMap.get(joinValue);

            // If a matching row is found in dataBundle2, combine the rows
            if (row2 != null) {
                Map<String, Object> combinedRow = new HashMap<>(row1);
                combinedRow.putAll(row2);
                joinedData.add(combinedRow);
            }
        }

        // Prepare the result TransitDataBundle
        TransitDataBundle resultDataBundle = new TransitDataBundle();
        resultDataBundle.columns = new ArrayList<>(columnNames1);
        resultDataBundle.columns.addAll(columnNames2); // Include all column names from the second bundle
        resultDataBundle.data = joinedData;

        return resultDataBundle;
    }
}
