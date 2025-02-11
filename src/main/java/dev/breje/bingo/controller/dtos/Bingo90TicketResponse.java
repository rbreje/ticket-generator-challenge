package dev.breje.bingo.controller.dtos;

import java.util.List;

public class Bingo90TicketResponse {

    private List<List<Integer>> dataPerRows;

    public List<List<Integer>> getDataPerRows() {
        return dataPerRows;
    }

    public void setDataPerRows(List<List<Integer>> dataPerRows) {
        this.dataPerRows = dataPerRows;
    }
}
