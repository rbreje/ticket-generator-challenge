package dev.breje.bingo.controller.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Bingo90TicketDataResponse {

    @JsonProperty("bingoTicketNo")
    private int index;
    @JsonProperty("data")
    private List<List<Integer>> ticket;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<List<Integer>> getTicket() {
        return ticket;
    }

    public void setTicket(List<List<Integer>> ticket) {
        this.ticket = ticket;
    }
}
