package dev.breje.bingo.controller.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bingo90TicketResponse {

    @JsonProperty("bingoTicket")
    private Bingo90TicketDataResponse ticket;

    public Bingo90TicketDataResponse getTicket() {
        return ticket;
    }

    public void setTicket(Bingo90TicketDataResponse ticket) {
        this.ticket = ticket;
    }
}
