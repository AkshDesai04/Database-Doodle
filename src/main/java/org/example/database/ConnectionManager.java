package org.example.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public Connection getConnection() {
        return connection;
    }
}
