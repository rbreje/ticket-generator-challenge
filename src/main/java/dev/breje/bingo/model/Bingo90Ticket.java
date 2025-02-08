package dev.breje.bingo.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Bingo90Ticket {

    public static final int ROWS = 3;
    public static final int COLUMNS = 9;
    public static final int MAX_NUMBERS = 15;

    private final int[][] matrix;

    public Bingo90Ticket() {
        matrix = new int[ROWS][COLUMNS];
        for (int[] row : matrix) Arrays.fill(row, -1);
    }

    public void addColumns(List<List<Integer>> columns) {
        int numbersPlaced = 0;
        for (int col = 0; col < COLUMNS; col++) {
            List<Integer> numbers = columns.get(col);
            List<Integer> rows = new ArrayList<>(Arrays.asList(0, 1, 2));
            Collections.shuffle(rows);
            for (int i = 0; i < numbers.size() && numbersPlaced < MAX_NUMBERS; i++) {
                matrix[rows.get(i)][col] = numbers.get(i);
                numbersPlaced++;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        IntStream.range(0, ROWS).forEach(
                index -> sb.append(Arrays.stream(matrix[index])
                        .mapToObj(val -> val == -1 ? "  " : val < 10 ? " " + val : String.valueOf(val))
                        .collect(Collectors.joining(", "))).append("\n")
        );

        return sb.toString();
    }

    public int getCurrentNumbersCount() {
        return Arrays.stream(matrix)
                .flatMapToInt(Arrays::stream)
                .filter(val -> val != -1)
                .toArray().length;
    }
}
