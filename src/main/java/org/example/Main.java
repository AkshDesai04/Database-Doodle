package org.example;

import org.example.database.ConnectionManager;
import org.example.database.DatabaseReader;
import org.example.database.grouping.Grouping;
import org.example.database.output.OutputHandler;
import org.example.database.sorting.Sorting;
import org.example.database.joining.Joining;

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

            // Display the columns in the table
            System.out.println("Available columns in the table '" + selectedTable + "':");
            for (int i = 0; i < dataBundle.columns.size(); i++) {
                System.out.println((i + 1) + ". " + dataBundle.columns.get(i));
            }

            // Display the data and ask for operations
            System.out.println("Select an operation to perform on the table:");
            System.out.println("1. Sort");
            System.out.println("2. Join");
            System.out.println("3. Group By");
            System.out.println("4. Export to CSV");

            int operationChoice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (operationChoice) {
                case 1 -> { // Sort Operation
                    System.out.println("Enter the number corresponding to the column to sort by:");
                    int sortColumnIndex = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    String sortColumn = dataBundle.columns.get(sortColumnIndex - 1);
                    System.out.println("Sort ascending? (true/false):");
                    boolean ascending = scanner.nextBoolean();
                    scanner.nextLine();  // Consume newline

                    dataBundle = Sorting.sort(dataBundle, sortColumn, ascending);
                    outputHandler.printTableData(dataBundle);
                }
                case 2 -> { // Join Operation
                    System.out.println("Enter the table number to join with:");
                    for (int i = 0; i < tables.size(); i++) {
                        System.out.println((i + 1) + ". " + tables.get(i));
                    }
                    int joinTableIndex = scanner.nextInt();
                    scanner.nextLine();
                    String joinTable = tables.get(joinTableIndex - 1);

                    // Fetch the columns from the join table
                    TransitDataBundle joinDataBundle = databaseReader.getTableDataBundle(joinTable);

                    // Display columns from the first table (current table)
                    System.out.println("Columns in the current table (" + selectedTable + "):");
                    for (int i = 0; i < dataBundle.columns.size(); i++) {
                        System.out.println((i + 1) + ". " + dataBundle.columns.get(i));
                    }

                    System.out.println("Enter the number corresponding to the column to join from the current table:");
                    int joinColumnIndex1 = scanner.nextInt();
                    scanner.nextLine();
                    String joinColumn1 = dataBundle.columns.get(joinColumnIndex1 - 1);

                    // Display columns from the second table (join table)
                    System.out.println("Columns in the join table (" + joinTable + "):");
                    for (int i = 0; i < joinDataBundle.columns.size(); i++) {
                        System.out.println((i + 1) + ". " + joinDataBundle.columns.get(i));
                    }

                    System.out.println("Enter the number corresponding to the column to join from the join table:");
                    int joinColumnIndex2 = scanner.nextInt();
                    scanner.nextLine();
                    String joinColumn2 = joinDataBundle.columns.get(joinColumnIndex2 - 1);

                    // Perform the join based on the selected columns from both tables
                    dataBundle = Joining.performJoin(dataBundle, joinDataBundle, joinColumn1, joinColumn2);

                    // Print the merged table after the join
                    outputHandler.printTableData(dataBundle);
                }
                case 3 -> { // Group By Operation
                    System.out.println("Enter the number corresponding to the column to group by:");
                    int groupByColumnIndex = scanner.nextInt();
                    scanner.nextLine();
                    String groupByColumn = dataBundle.columns.get(groupByColumnIndex - 1);

                    dataBundle = Grouping.groupBy(dataBundle, groupByColumn);
                    outputHandler.printTableData(dataBundle);
                }
                case 4 -> { // Export to CSV
                    System.out.println("Enter the CSV file name for table '" + selectedTable + "' (without extension):");
                    String fileName = scanner.nextLine();

                    System.out.println("Do you want to specify a custom path for the file? (yes/no):");
                    String customPathChoice = scanner.nextLine().trim().toLowerCase();

                    String outputPath;
                    if (customPathChoice.equals("yes")) {
                        System.out.println("Enter the custom file path:");
                        String customPath = scanner.nextLine();
                        outputPath = customPath + "\\" + fileName + ".csv"; // Use custom path
                    } else {
                        outputPath = defaultCsvPath + fileName + ".csv"; // Use default path
                    }

                    // Export table data to CSV
                    outputHandler.exportTableDataToCSV(dataBundle, outputPath);
                    System.out.println("Data successfully exported to " + outputPath);
                }
                default -> System.out.println("Invalid choice.");
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
