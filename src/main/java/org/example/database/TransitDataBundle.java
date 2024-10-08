package org.example.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransitDataBundle {
    public List<String> columns;
    public List<Map<String, Object>> data;

    // Constructor
    public TransitDataBundle() {
        this.columns = new ArrayList<>();
        this.data = new ArrayList<>();
    }

    public void processResultSet(ResultSet resultSet) throws SQLException {
        // Clear previous data
        columns.clear();
        data.clear();

        // Process columns
        int columnCount = resultSet.getMetaData().getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            columns.add(resultSet.getMetaData().getColumnName(i));
        }

        // Process rows
        while (resultSet.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = resultSet.getMetaData().getColumnName(i);
                Object columnValue = resultSet.getObject(i);
                row.put(columnName, columnValue);
            }
            data.add(row);
        }
    }
}
