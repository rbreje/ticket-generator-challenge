package dev.breje.bingo.model;

import dev.breje.bingo.exceptions.BingoStripOverflowException;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class BingoStrip {

    private static final int TICKETS_PER_STRIP = 6;

    private final BingoTicket[] tickets;

    private int ticketsAdded = 0;

    public BingoStrip() {
        tickets = new BingoTicket[TICKETS_PER_STRIP];
    }

    public Set<BingoTicket> getTickets() {
        return Arrays.stream(tickets).collect(Collectors.toSet());
    }

    public void addTicket(BingoTicket ticket) throws BingoStripOverflowException {
        if (ticketsAdded >= 6) {
            throw new BingoStripOverflowException("The Bingo strip is already full.");
        }
        tickets[ticketsAdded++] = ticket;
    }
}
