package org.example;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class TransitDataBundle {
    public List<String> columns;
    public List<Map<String, Object>> data;

    // Constructor to initialize the lists
    public TransitDataBundle() {
        this.columns = new ArrayList<>();
        this.data = new ArrayList<>();
    }

    // Method to set column names
    public void setColumnNames(List<String> columnNames) {
        this.columns = columnNames;
    }

    // Method to set table data
    public void setTableData(List<List<Object>> tableData) {
        this.data.clear();
        for (List<Object> row : tableData) {
            Map<String, Object> rowMap = new HashMap<>();
            for (int i = 0; i < columns.size(); i++) {
                rowMap.put(columns.get(i), row.get(i));
            }
            this.data.add(rowMap);
        }
    }

    // Getter methods for columns and data
    public List<String> getColumns() {
        return columns;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }
}
