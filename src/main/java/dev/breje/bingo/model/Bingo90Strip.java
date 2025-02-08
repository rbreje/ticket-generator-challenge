package dev.breje.bingo.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class Bingo90Strip {

    public static final int TICKETS_PER_STRIP = 6;

    private final Map<Integer, Bingo90Ticket> tickets;

    public Bingo90Strip() {
        tickets = new HashMap<>(TICKETS_PER_STRIP);
        IntStream.range(0, TICKETS_PER_STRIP).forEach(index -> tickets.put(index, new Bingo90Ticket()));
    }

    public Set<Bingo90Ticket> getTickets() {
        return new HashSet<>(tickets.values());
    }

    public Bingo90Ticket getTicket(int index) {
        return tickets.get(index);
    }
}
