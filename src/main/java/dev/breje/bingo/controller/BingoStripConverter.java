package dev.breje.bingo.controller;

import dev.breje.bingo.controller.dtos.Bingo90StripResponse;
import dev.breje.bingo.controller.dtos.Bingo90TicketResponse;
import dev.breje.bingo.model.Bingo90Strip;
import dev.breje.bingo.model.Bingo90Ticket;

public class BingoStripConverter {

    public Bingo90StripResponse convertToBingo90StripResponse(Bingo90Strip bingo90Strip) {
        Bingo90StripResponse response = new Bingo90StripResponse();
        response.setTickets(bingo90Strip.getTickets()
                .stream()
                .map(this::convertToBingo90TicketResponse)
                .toList());
        return response;
    }

    public Bingo90TicketResponse convertToBingo90TicketResponse(Bingo90Ticket bingo90Ticket) {
        Bingo90TicketResponse response = new Bingo90TicketResponse();
        response.setDataPerRows(bingo90Ticket.getDataPerRows());
        return response;
    }
}
