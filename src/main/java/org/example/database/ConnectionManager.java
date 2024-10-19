package org.example.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionManager {
    private Connection connection;

    public void connect(String url, String username, String password) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getDatabases() {
        List<String> databases = new ArrayList<>();
        try (ResultSet rs = connection.getMetaData().getCatalogs()) {
            while (rs.next()) {
                databases.add(rs.getString("TABLE_CAT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve databases.");
        }
        return databases;
    }

    public void selectDatabase(String databaseName) {
        try {
            connection.setCatalog(databaseName);
            System.out.println("Selected database: " + databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to select database.");
        }
    }

    public List<String> getTables() {
        List<String> tables = new ArrayList<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(connection.getCatalog(), null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve tables.");
        }
        return tables;
    }

    public TransitDataBundle executeQuery(String query) {
        TransitDataBundle resultBundle = new TransitDataBundle();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Process the ResultSet directly
            resultBundle.processResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
        return resultBundle;
    }

    public Connection getConnection() {
        return connection;
    }
}
