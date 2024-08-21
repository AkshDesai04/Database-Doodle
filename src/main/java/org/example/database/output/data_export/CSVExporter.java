package org.example.database.output.data_export;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CSVExporter {

    public void csv_exporter(List<String> columns, List<Map<String, Object>> data, String pathToCsv) throws IOException {
        try (FileWriter writer = new FileWriter(pathToCsv)) {
            // Write the column headers
            for (int i = 0; i < columns.size(); i++) {
                writer.append(columns.get(i));
                if (i < columns.size() - 1) {
                    writer.append(",");
                }
            }
            writer.append("\n");

            // Write the rows of data
            for (Map<String, Object> row : data) {
                for (int i = 0; i < columns.size(); i++) {
                    Object value = row.get(columns.get(i));
                    writer.append(value != null ? value.toString() : "");
                    if (i < columns.size() - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }
        }
    }
}
