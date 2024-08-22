package org.example;

import org.example.database.ConnectionManager;
import org.example.database.DatabaseReader;
import org.example.database.output.OutputHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of databases you want to connect to:");
        int dbCount = scanner.nextInt();
        scanner.nextLine();

        List<ConnectionManager> connectionManagers = new ArrayList<>();

        for (int i = 1; i <= dbCount; i++) {
            ConnectionManager connectionManager = new ConnectionManager();

            System.out.println("Enter database " + i + " URL:");
            String url = scanner.nextLine();

            System.out.println("Enter username for database " + i + ":");
            String username = scanner.nextLine();

            System.out.println("Enter password for database " + i + ":");
            String password = scanner.nextLine();

            connectionManager.connect(url, username, password);
            connectionManagers.add(connectionManager);
        }

        try {
            for (int i = 0; i < connectionManagers.size(); i++) {
                selectAndExportTables(connectionManagers.get(i), scanner, String.valueOf(i + 1));
            }
        } finally {
            for (ConnectionManager connectionManager : connectionManagers) {
                closeConnection(connectionManager);
            }
        }
    }

    private static void selectAndExportTables(ConnectionManager connectionManager, Scanner scanner, String dbLabel) {
        String csvPath = "C:\\DatabaseDoodleOutput\\";
        List<String> databases = connectionManager.getDatabases();
        System.out.println("Available databases for connection " + dbLabel + ":");
        for (int i = 0; i < databases.size(); i++) {
            System.out.println((i + 1) + ". " + databases.get(i));
        }

        System.out.println("Select a database by number for connection " + dbLabel + ":");
        int dbIndex = scanner.nextInt();
        scanner.nextLine();
        String selectedDatabase = databases.get(dbIndex - 1);
        connectionManager.selectDatabase(selectedDatabase);

        List<String> tables = connectionManager.getTables();
        System.out.println("Available tables in database " + selectedDatabase + ":");
        for (int i = 0; i < tables.size(); i++) {
            System.out.println((i + 1) + ". " + tables.get(i));
        }

        System.out.println("Select tables by numbers (comma-separated) for connection " + dbLabel + ":");
        String[] selectedTableIndexes = scanner.nextLine().split(",");
        List<String> selectedTables = new ArrayList<>();
        for (String index : selectedTableIndexes) {
            int tableIndex = Integer.parseInt(index.trim()) - 1;
            if (tableIndex >= 0 && tableIndex < tables.size()) {
                selectedTables.add(tables.get(tableIndex));
            } else {
                System.out.println("Invalid table number: " + (tableIndex + 1));
            }
        }

        DatabaseReader databaseReader = new DatabaseReader(connectionManager);
        OutputHandler outputHandler = new OutputHandler();

        for (String selectedTable : selectedTables) {
            TransitDataBundle dataBundle = databaseReader.getTableDataBundle(selectedTable);

            // Display table data before saving to CSV
            outputHandler.printTableData(dataBundle);

            System.out.println("\nEnter the CSV file name for table '" + selectedTable + "' (without extension):");
            String fileName = scanner.nextLine();

            // Export table data
            String outputPath = csvPath + fileName + ".csv";
            outputHandler.exportTableDataToCSV(dataBundle, outputPath);
            System.out.println("Data successfully exported to " + outputPath);
        }
    }

    private static void closeConnection(ConnectionManager connectionManager) {
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
