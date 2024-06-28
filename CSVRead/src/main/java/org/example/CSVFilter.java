package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVFilter {

    public List<String[]> filterAndPaginate(String csvFilePath, String filterExpression, int pageNumber) {
        List<String[]> rows;
        try {
            rows = readCsvFile(csvFilePath);
            List<String[]> filteredRows = filterRows(rows, filterExpression);
            return paginateRows(filteredRows, pageNumber);
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            return new ArrayList<>(); // Return an empty list or handle gracefully
        }
    }

    private List<String[]> readCsvFile(String csvFilePath) throws IOException {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                rows.add(row);
            }
        }
        return rows;
    }

    private List<String[]> filterRows(List<String[]> rows, String filterExpression) {
        List<String[]> filteredRows = new ArrayList<>();
        for (String[] row : rows) {
            if (filterExpression.isEmpty() || row[2].equals(filterExpression)) {
                filteredRows.add(row);
            }
        }
        return filteredRows;
    }

    private List<String[]> paginateRows(List<String[]> rows, int pageNumber) {
        int pageSize = 5; // You can adjust the page size as needed
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, rows.size());
        return rows.subList(startIndex, endIndex);
    }

    public static void main(String[] args) {
        CSVFilter csvFilter = new CSVFilter();
        String csvFilePath = "/home/alugunuri/Downloads/example.csv";
        String filterExpression = "practo";
        int pageNumber = 1;
        List<String[]> paginatedRows = csvFilter.filterAndPaginate(csvFilePath, filterExpression, pageNumber);
        for (String[] row : paginatedRows) {
            System.out.println(String.join(",", row));
        }
    }
}
