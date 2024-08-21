package org.example.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseReader {
    private final ConnectionManager connectionManager;

    public DatabaseReader(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public List<String> getTableColumns(String tableName) {
        List<String> columns = new ArrayList<>();
        try (ResultSet rs = connectionManager.getConnection().getMetaData().getColumns(connectionManager.getConnection().getCatalog(), null, tableName, null)) {
            while (rs.next()) {
                columns.add(rs.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve table columns.");
        }
        return columns;
    }

    public List<Map<String, Object>> getTableData(String tableName) {
        List<Map<String, Object>> data = new ArrayList<>();
        try (
                Connection connection = connectionManager.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)
        ) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve table data.");
        }
        return data;
    }
}
