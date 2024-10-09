package org.example.database;

import org.example.TransitDataBundle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseReader {
    private final ConnectionManager connectionManager;

    public DatabaseReader(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public TransitDataBundle getTableDataBundle(String tableName) {
        TransitDataBundle dataBundle = new TransitDataBundle();
        try {
            Connection connection = connectionManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);

            // Fetch column names
            List<String> columnNames = new ArrayList<>();
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(resultSet.getMetaData().getColumnName(i));
            }

            // Fetch table data
            List<List<Object>> tableData = new ArrayList<>();
            while (resultSet.next()) {
                List<Object> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getObject(i));
                }
                tableData.add(row);
            }

            // Set the column names and table data in the data bundle
            dataBundle.setColumnNames(columnNames);
            dataBundle.setTableData(tableData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataBundle;
    }

    public List<String> getDatabases() {
        List<String> databases = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            ResultSet resultSet = connection.getMetaData().getCatalogs();
            while (resultSet.next()) {
                databases.add(resultSet.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databases;
    }

    public List<String> getTables() {
        List<String> tables = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            ResultSet resultSet = connection.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
            while (resultSet.next()) {
                tables.add(resultSet.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tables;
    }
}
