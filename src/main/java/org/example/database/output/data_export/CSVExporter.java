package org.example.database.output.data_export;

import org.example.database.TransitDataBundle;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CSVExporter {

    public void csv_exporter(TransitDataBundle dataBundle, String pathToCsv) throws IOException {
        try (FileWriter writer = new FileWriter(pathToCsv)) {
            // Extract columns and data from the TransitDataBundle object
            List<String> columns = dataBundle.columns;
            List<Map<String, Object>> data = dataBundle.data;

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
