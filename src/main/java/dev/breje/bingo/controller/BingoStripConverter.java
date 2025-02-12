package dev.breje.bingo.controller;

import dev.breje.bingo.controller.dtos.Bingo90StripResponse;
import dev.breje.bingo.controller.dtos.Bingo90TicketDataResponse;
import dev.breje.bingo.controller.dtos.Bingo90TicketResponse;
import dev.breje.bingo.model.Bingo90Strip;
import dev.breje.bingo.model.Bingo90Ticket;

import java.util.stream.IntStream;

public class BingoStripConverter {

    public Bingo90StripResponse convertToBingo90StripResponse(Bingo90Strip bingo90Strip) {
        Bingo90StripResponse response = new Bingo90StripResponse();
        response.setTickets(IntStream.rangeClosed(1, bingo90Strip.getTickets().size())
                .mapToObj(index -> convertToBingo90TicketResponse(index, bingo90Strip.getTicket(index - 1)))
                .toList());
        return response;
    }

    public Bingo90TicketResponse convertToBingo90TicketResponse(int index, Bingo90Ticket bingo90Ticket) {
        Bingo90TicketResponse response = new Bingo90TicketResponse();
        response.setTicket(convertToBingo90TicketDataResponse(index, bingo90Ticket));
        return response;
    }

    public Bingo90TicketDataResponse convertToBingo90TicketDataResponse(int index, Bingo90Ticket bingo90Ticket) {
        Bingo90TicketDataResponse response = new Bingo90TicketDataResponse();
        response.setIndex(index);
        response.setTicket(bingo90Ticket.getDataPerRows());
        return response;
    }
}
