package dev.breje.bingo.controller.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Bingo90StripResponse {

    @JsonProperty("bingoStrip")
    private List<Bingo90TicketResponse> tickets;

    public List<Bingo90TicketResponse> getTickets() {
        return tickets;
    }

    public void setTickets(List<Bingo90TicketResponse> tickets) {
        this.tickets = tickets;
    }
}
