package dev.breje.bingo.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BingoTicket {

    public static final int ROWS = 3;
    public static final int COLUMNS = 9;

    private final int[][] matrix;

    public BingoTicket() {
        matrix = new int[ROWS][COLUMNS];
        for (int[] row : matrix) Arrays.fill(row, -1);
    }

    public List<String> getFirstRow() {
        return Arrays.stream(matrix[0])
                .mapToObj(val -> val == -1 ? " " : String.valueOf(val))
                .collect(Collectors.toList());
    }

    public List<String> getSecondRow() {
        return Arrays.stream(matrix[1])
                .mapToObj(val -> val == -1 ? " " : String.valueOf(val))
                .collect(Collectors.toList());
    }

    public List<String> getThirdRow() {
        return Arrays.stream(matrix[2])
                .mapToObj(val -> val == -1 ? " " : String.valueOf(val))
                .collect(Collectors.toList());
    }
}
