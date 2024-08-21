package org.example;

import org.example.database.ConnectionManager;
import org.example.database.DatabaseReader;
import org.example.database.output.OutputHandler;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter database URL:");
        String url = scanner.nextLine();

        System.out.println("Enter username:");
        String username = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.connect(url, username, password);

        try {
            List<String> databases = connectionManager.getDatabases();
            System.out.println("Available databases:");
            for (int i = 0; i < databases.size(); i++) {
                System.out.println((i + 1) + ". " + databases.get(i));
            }

            System.out.println("Select a database by number:");
            int dbIndex = scanner.nextInt();
            scanner.nextLine();  // consume the newline character
            String selectedDatabase = databases.get(dbIndex - 1);
            connectionManager.selectDatabase(selectedDatabase);

            List<String> tables = connectionManager.getTables();
            System.out.println("Available tables:");
            for (int i = 0; i < tables.size(); i++) {
                System.out.println((i + 1) + ". " + tables.get(i));
            }

            System.out.println("Select a table by number:");
            int tableIndex = scanner.nextInt();
            scanner.nextLine();  // consume the newline character
            String selectedTable = tables.get(tableIndex - 1);

            DatabaseReader databaseReader = new DatabaseReader(connectionManager);
            List<String> columns = databaseReader.getTableColumns(selectedTable);
            List<Map<String, Object>> data = databaseReader.getTableData(selectedTable);

            OutputHandler outputHandler = new OutputHandler();
            outputHandler.printTableData(columns, data);
        } finally {
            // Ensure the connection is closed after operations
            try {
                if (connectionManager.getConnection() != null && !connectionManager.getConnection().isClosed()) {
                    connectionManager.getConnection().close();
                    System.out.println("Connection closed.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}