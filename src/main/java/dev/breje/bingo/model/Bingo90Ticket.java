package dev.breje.bingo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Bingo90Ticket {

    public static final int ROWS = 3;
    public static final int COLUMNS = 9;
    public static final int NUMBERS_PER_TICKET = 15;

    private final Map<Integer, List<Integer>> columns;

    public Bingo90Ticket() {
        columns = new HashMap<>(COLUMNS);
        IntStream.range(0, COLUMNS).forEach(index -> columns.put(index, new ArrayList<>()));
    }

    public List<Integer> getColumn(int index) {
        return columns.get(index);
    }

    public Map<Integer, List<Integer>> getDataAsColumns() {
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
