package org.example.database.sorting;

import org.example.database.TransitDataBundle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Sorting {
    public static TransitDataBundle sort(TransitDataBundle data, String column, boolean aesc) {
        List<Map<String, Object>> dataList = data.data;
        List<String> columns = data.columns;

        // Ensure the specified column exists
        if (!columns.contains(column)) {
            throw new IllegalArgumentException("Column not found: " + column);
        }

        // Sort the data based on the specified column using merge sort
        List<Map<String, Object>> sortedData = mergeSort(dataList, column, aesc);

        // Update the TransitDataBundle with the sorted data
        data.data = sortedData;

        return data;
    }

    private static List<Map<String, Object>> mergeSort(List<Map<String, Object>> dataList, String column, boolean aesc) {
        if (dataList.size() <= 1) {
            return dataList;
        }

        int mid = dataList.size() / 2;
        List<Map<String, Object>> left = new ArrayList<>(dataList.subList(0, mid));
        List<Map<String, Object>> right = new ArrayList<>(dataList.subList(mid, dataList.size()));

        return merge(mergeSort(left, column, aesc), mergeSort(right, column, aesc), column, aesc);
    }

    private static List<Map<String, Object>> merge(List<Map<String, Object>> left, List<Map<String, Object>> right, String column, boolean aesc) {
        List<Map<String, Object>> merged = new ArrayList<>();
        int i = 0, j = 0;

        Comparator<Map<String, Object>> comparator = new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> row1, Map<String, Object> row2) {
                Comparable<Object> value1 = (Comparable<Object>) row1.get(column);
                Comparable<Object> value2 = (Comparable<Object>) row2.get(column);

                if (value1 == null && value2 == null) return 0;
                if (value1 == null) return aesc ? -1 : 1;
                if (value2 == null) return aesc ? 1 : -1;

                return aesc ? value1.compareTo(value2) : value2.compareTo(value1);
            }
        };

        while (i < left.size() && j < right.size()) {
            int cmp = comparator.compare(left.get(i), right.get(j));
            if (cmp <= 0)
                merged.add(left.get(i++));
            else
                merged.add(right.get(j++));
        }

        while (i < left.size())
            merged.add(left.get(i++));
        while (j < right.size())
            merged.add(right.get(j++));

        return merged;
    }
}
