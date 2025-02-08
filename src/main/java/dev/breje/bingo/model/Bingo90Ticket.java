package dev.breje.bingo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Bingo90Ticket {

    public static final int ROWS = 3;

    private final Map<Integer, List<Integer>> columns;

    public Bingo90Ticket() {
        columns = new HashMap<>(9);
        IntStream.rangeClosed(0, 8).forEach(index -> columns.put(index, new ArrayList<>()));
    }

    public List<Integer> getColumn(int index) {
        return columns.get(index);
    }

    public List<Integer> getRow(int index) {
        return IntStream.rangeClosed(0, 8)
                .mapToObj(columnIndex -> columns.get(columnIndex).get(index))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        IntStream.range(0, ROWS).forEach(
                index -> {
                    List<Integer> row = getRow(index);
                    sb.append(row);
                    sb.append("\n");
                }
        );

        return sb.toString();
    }
}
