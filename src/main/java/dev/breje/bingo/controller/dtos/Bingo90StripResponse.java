package dev.breje.bingo.controller.dtos;

import java.util.List;

public class Bingo90StripResponse {

    List<Bingo90TicketResponse> tickets;

    public List<Bingo90TicketResponse> getTickets() {
        return tickets;
    }

    public void setTickets(List<Bingo90TicketResponse> tickets) {
        this.tickets = tickets;
    }
}
