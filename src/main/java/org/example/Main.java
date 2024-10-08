package org.example;

import org.example.database.ConnectionManager;
import org.example.database.DatabaseReader;
import org.example.database.JoinOperations;
import org.example.database.TransitDataBundle;
import org.example.database.output.OutputHandler;

import java.nio.file.Paths;
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

            // Connect to the database
            System.out.println("Connecting to database: " + url);
            connectionManager.connect(url, username, password);
            System.out.println("Connected to database: " + url);
            connectionManagers.add(connectionManager);
        }

        try {
            for (int i = 0; i < connectionManagers.size(); i++) {
                selectAndExportTables(connectionManagers.get(i), scanner, String.valueOf(i + 1));
            }

            if (dbCount > 1) {
                handleJoinOperations(connectionManagers, scanner);
            }
        } finally {
            for (ConnectionManager connectionManager : connectionManagers) {
                closeConnection(connectionManager);
            }
        }
    }

    private static void selectAndExportTables(ConnectionManager connectionManager, Scanner scanner, String dbLabel) {
        String defaultCsvPath = "C:\\DatabaseDoodleOutput\\";
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

            outputHandler.printTableData(dataBundle);

            System.out.println("\nEnter the CSV file name for table '" + selectedTable + "' (without extension):");
            String fileName = scanner.nextLine();

            System.out.println("Do you want to specify a custom path for the file? (yes/no):");
            String customPathChoice = scanner.nextLine().trim().toLowerCase();

            String outputPath = Paths.get(customPathChoice.equals("yes") ? scanner.nextLine() : defaultCsvPath, fileName + ".csv").toString();

            outputHandler.exportTableDataToCSV(dataBundle, outputPath);
            System.out.println("Data successfully exported to " + outputPath);
        }
    }

    private static void handleJoinOperations(List<ConnectionManager> connectionManagers, Scanner scanner) {
        System.out.println("You have multiple connections. Do you want to perform join operations? (yes/no):");
        String joinChoice = scanner.nextLine().trim().toLowerCase();

        if (joinChoice.equals("yes")) {
            JoinOperations joinOperations = new JoinOperations(connectionManagers);

            System.out.println("Select the type of join operation:");
            System.out.println("1. Inner Join");
            System.out.println("2. Outer Join");
            System.out.println("3. Left Outer Join");
            System.out.println("4. Right Outer Join");
            System.out.println("5. Full Outer Join");
            System.out.println("6. Cross Join");
            System.out.println("7. Self Join");
            System.out.println("8. Natural Join");
            System.out.println("9. Equi Join");
            System.out.println("10. Non-Equi Join");

            int joinChoiceIndex = scanner.nextInt();
            scanner.nextLine();
            String joinType = "";

            switch (joinChoiceIndex) {
                case 1: joinType = "inner"; break;
                case 2: joinType = "outer"; break;
                case 3: joinType = "left"; break;
                case 4: joinType = "right"; break;
                case 5: joinType = "full"; break;
                case 6: joinType = "cross"; break;
                case 7: joinType = "self"; break;
                case 8: joinType = "natural"; break;
                case 9: joinType = "equi"; break;
                case 10: joinType = "non-equi"; break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    return;
            }

            System.out.println("Select the index of the first connection:");
            for (int i = 0; i < connectionManagers.size(); i++) {
                System.out.println((i + 1) + ". Connection " + (i + 1));
            }
            int firstDbIndex = scanner.nextInt();
            scanner.nextLine();
            ConnectionManager firstConnection = connectionManagers.get(firstDbIndex - 1);

            System.out.println("Select the index of the second connection:");
            for (int i = 0; i < connectionManagers.size(); i++) {
                System.out.println((i + 1) + ". Connection " + (i + 1));
            }
            int secondDbIndex = scanner.nextInt();
            scanner.nextLine();
            ConnectionManager secondConnection = connectionManagers.get(secondDbIndex - 1);

            System.out.println("Available tables in first database connection " + firstDbIndex + ":");
            List<String> firstTables = firstConnection.getTables();
            for (int i = 0; i < firstTables.size(); i++) {
                System.out.println((i + 1) + ". " + firstTables.get(i));
            }
            System.out.println("Select the table name from the first database:");
            int firstTableIndex = scanner.nextInt();
            scanner.nextLine();
            String firstTable = firstTables.get(firstTableIndex - 1);

            System.out.println("Available tables in second database connection " + secondDbIndex + ":");
            List<String> secondTables = secondConnection.getTables();
            for (int i = 0; i < secondTables.size(); i++) {
                System.out.println((i + 1) + ". " + secondTables.get(i));
            }
            System.out.println("Select the table name from the second database:");
            int secondTableIndex = scanner.nextInt();
            scanner.nextLine();
            String secondTable = secondTables.get(secondTableIndex - 1);

            TransitDataBundle joinResult = joinOperations.performJoin(joinType, firstConnection, firstTable, secondConnection, secondTable);

            if (joinResult != null) {
                OutputHandler outputHandler = new OutputHandler();
                outputHandler.printTableData(joinResult);

                System.out.println("\nEnter the CSV file name for the joined table (without extension):");
                String fileName = scanner.nextLine();

                System.out.println("Do you want to specify a custom path for the file? (yes/no):");
                String customPathChoice = scanner.nextLine().trim().toLowerCase();

                String outputPath = Paths.get(customPathChoice.equals("yes") ? scanner.nextLine() : "C:\\DatabaseDoodleOutput\\", fileName + ".csv").toString();

                outputHandler.exportTableDataToCSV(joinResult, outputPath);
                System.out.println("Joined data successfully exported to " + outputPath);
            } else {
                System.out.println("No data found for the join operation.");
            }
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
