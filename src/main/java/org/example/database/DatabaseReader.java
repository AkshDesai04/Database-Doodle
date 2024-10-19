package org.example.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseReader {
    private final ConnectionManager connectionManager;

    public DatabaseReader(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    // Returns a TransitDataBundle object with table columns and data
    public TransitDataBundle getTableDataBundle(String tableName) {
        List<String> columns = new ArrayList<>();
        List<Map<String, Object>> data = new ArrayList<>();

        // Get table columns
        try (ResultSet rs = connectionManager.getConnection().getMetaData().getColumns(
                connectionManager.getConnection().getCatalog(), null, tableName, null)) {
            while (rs.next()) {
                columns.add(rs.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve table columns.");
        }

        // Get table data
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

        // Create and populate the TransitDataBundle object
        TransitDataBundle dataBundle = new TransitDataBundle();
        dataBundle.columns = columns;
        dataBundle.data = data;
        return dataBundle;
    }
}
