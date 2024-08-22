package org.example;

import org.example.database.ConnectionManager;
import org.example.database.DatabaseReader;
import org.example.database.output.OutputHandler;
import org.example.database.sorting.Sorting;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of databases you want to connect to (1 or 2):");
        int dbCount = scanner.nextInt();
        scanner.nextLine();

        ConnectionManager connectionManager1 = new ConnectionManager();
        ConnectionManager connectionManager2 = null;

        System.out.println("Enter database 1 URL:");
        String url1 = scanner.nextLine();

        System.out.println("Enter username for database 1:");
        String username1 = scanner.nextLine();

        System.out.println("Enter password for database 1:");
        String password1 = scanner.nextLine();

        connectionManager1.connect(url1, username1, password1);

        if (dbCount == 2) {
            connectionManager2 = new ConnectionManager();

            System.out.println("Enter database 2 URL:");
            String url2 = scanner.nextLine();

            System.out.println("Enter username for database 2:");
            String username2 = scanner.nextLine();

            System.out.println("Enter password for database 2:");
            String password2 = scanner.nextLine();

            connectionManager2.connect(url2, username2, password2);
        }

        try {
            selectAndExportTables(connectionManager1, scanner, "1");
            if (connectionManager2 != null) {
                selectAndExportTables(connectionManager2, scanner, "2");
            }
        } finally {
            closeConnection(connectionManager1);
            if (connectionManager2 != null) {
                closeConnection(connectionManager2);
            }
        }
    }

    private static void selectAndExportTables(ConnectionManager connectionManager, Scanner scanner, String dbLabel) {
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

            // Display table data
            outputHandler.printTableData(Sorting.sort(dataBundle, "SurfaceArea", true));

            System.out.println("\n\nEnter the CSV file name for table '" + selectedTable + "' (without extension):");
            String fileName = scanner.nextLine();
            String csvPath = "C:\\Users\\karan\\OneDrive\\Desktop\\Temp\\" + fileName + ".csv";

            // Export table data
            outputHandler.exportTableDataToCSV(Sorting.sort(dataBundle, "SurfaceArea", true), csvPath);
            System.out.println("Data successfully exported to " + csvPath);
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
