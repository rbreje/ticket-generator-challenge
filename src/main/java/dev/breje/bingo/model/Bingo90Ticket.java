package dev.breje.bingo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Bingo90Ticket {

    public static final int ROWS_NO = 3;
    public static final int COLUMNS_NO = 9;
    public static final int NUMBERS_PER_TICKET = 15;

    private final Map<Integer, LinkedList<Integer>> columns;

    public Bingo90Ticket() {
        columns = new HashMap<>(COLUMNS_NO);
        IntStream.range(0, COLUMNS_NO).forEach(index -> columns.put(index, new LinkedList<>()));
    }

    public List<Integer> getColumn(int index) {
        return columns.get(index);
    }

    public Map<Integer, LinkedList<Integer>> getDataAsColumns() {
        return columns;
    }

    public List<List<Integer>> getDataPerRows() {
        List<List<Integer>> rows = new ArrayList<>(ROWS_NO);
        IntStream.range(0, ROWS_NO).forEach(index -> rows.add(new ArrayList<>(COLUMNS_NO)));
        IntStream.range(0, COLUMNS_NO).forEach(
                columnIndex -> IntStream.range(0, ROWS_NO).forEach(
                        rowIndex -> rows.get(rowIndex).add(columns.get(columnIndex).get(rowIndex))
                )
        );
        return rows;
    }

    public List<List<Integer>> getDataPerColumns() {
        List<List<Integer>> localColumns = new ArrayList<>(COLUMNS_NO);
        IntStream.range(0, COLUMNS_NO).forEach(index -> localColumns.add(new ArrayList<>(ROWS_NO)));
        IntStream.range(0, ROWS_NO).forEach(
                rowIndex -> IntStream.range(0, COLUMNS_NO).forEach(
                        columnIndex -> localColumns.get(columnIndex).add(this.columns.get(columnIndex).get(rowIndex))
                )
        );
        return localColumns;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, COLUMNS_NO).forEach(
                index -> {
                    sb.append(columns.get(index));
                    sb.append("\n");
                }
        );
        return sb.toString();
    }
}
