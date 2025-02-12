package dev.breje.bingo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Bingo90Ticket {

    public static final int ROWS = 3;
    public static final int COLUMNS = 9;
    public static final int NUMBERS_PER_TICKET = 15;

    private final Map<Integer, LinkedList<Integer>> columns;

    public Bingo90Ticket() {
        columns = new HashMap<>(COLUMNS);
        IntStream.range(0, COLUMNS).forEach(index -> columns.put(index, new LinkedList<>()));
    }

    public List<Integer> getColumn(int index) {
        return columns.get(index);
    }

    public Map<Integer, LinkedList<Integer>> getDataAsColumns() {
        return columns;
    }

    public List<List<Integer>> getDataPerRows() {
        List<List<Integer>> rows = new ArrayList<>(ROWS);
        IntStream.range(0, ROWS).forEach(index -> rows.add(new ArrayList<>(COLUMNS)));
        IntStream.range(0, COLUMNS).forEach(
                columnIndex -> IntStream.range(0, ROWS).forEach(
                        rowIndex -> {
                            rows.get(rowIndex).add(columns.get(columnIndex).get(rowIndex));
                        }
                )
        );
        return rows;
    }

    public List<List<Integer>> getDataPerColumns() {
        List<List<Integer>> columns = new ArrayList<>(COLUMNS);
        IntStream.range(0, COLUMNS).forEach(index -> columns.add(new ArrayList<>(ROWS)));
        IntStream.range(0, ROWS).forEach(
                rowIndex -> IntStream.range(0, COLUMNS).forEach(
                        columnIndex -> {
                            columns.get(columnIndex).add(this.columns.get(rowIndex).get(columnIndex));
                        }
                )
        );
        return columns;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, COLUMNS).forEach(
                index -> {
                    sb.append(columns.get(index));
                    sb.append("\n");
                }
        );
        return sb.toString();
    }
}
