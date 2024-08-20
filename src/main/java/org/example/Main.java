package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter file path (CSV or Excel):");
        String filePath = scanner.nextLine();

        List<Map<String, Object>> data;
        try {
            if (filePath.endsWith(".csv")) {
                data = convertCsvToMap(filePath);
            } else if (filePath.endsWith(".xlsx")) {
                data = convertExcelToMap(filePath);
            } else {
                System.out.println("Unsupported file format. Please provide a CSV or Excel file.");
                return;
            }

            if (data.isEmpty()) {
                System.out.println("No data found.");
            } else {
                System.out.println("Data from the file:");
                printTableData(new ArrayList<>(data.get(0).keySet()), data);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    public static List<Map<String, Object>> convertCsvToMap(String filePath) throws IOException {
        List<Map<String, Object>> recordsList = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                Map<String, Object> rowMap = new HashMap<>();
                for (String header : csvParser.getHeaderNames()) {
                    rowMap.put(header, record.get(header));
                }
                recordsList.add(rowMap);
            }
        }

        return recordsList;
    }

    public static List<Map<String, Object>> convertExcelToMap(String filePath) throws IOException {
        List<Map<String, Object>> recordsList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row headerRow = rowIterator.next();  // Assuming the first row is the header
            List<String> headers = new ArrayList<>();

            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue());
            }

            while (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();
                Map<String, Object> rowMap = new HashMap<>();

                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = currentRow.getCell(i);
                    rowMap.put(headers.get(i), getCellValue(cell));
                }

                recordsList.add(rowMap);
            }
        }

        return recordsList;
    }

    private static Object getCellValue(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return null;
        }
    }

    public static void printTableData(List<String> columns, List<Map<String, Object>> data) {
        // Print header
        System.out.println(String.join(" | ", columns));

        // Print data
        for (Map<String, Object> row : data) {
            for (String column : columns) {
                System.out.print(row.get(column) + " | ");
            }
            System.out.println();
        }
    }
}
