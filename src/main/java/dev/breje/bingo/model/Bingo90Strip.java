package dev.breje.bingo.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Bingo90Strip {

    public static final int TICKETS_PER_STRIP = 6;
    public static final int NUMBERS_PER_STRIP = 90;

    private final Map<Integer, Bingo90Ticket> tickets;

    public Bingo90Strip() {
        tickets = new HashMap<>(TICKETS_PER_STRIP);
        IntStream.range(0, TICKETS_PER_STRIP).forEach(index -> tickets.put(index, new Bingo90Ticket()));
    }

    public Bingo90Ticket getTicket(int index) {
        return tickets.get(index);
    }
    
    public List<Bingo90Ticket> getTickets() {
        return tickets.values()
                .stream()
                .toList();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        IntStream.range(0, TICKETS_PER_STRIP).forEach(
                index -> {
                    sb.append("Ticket [").append(index + 1).append("]\n");
                    sb.append(tickets.get(index)).append("\n\n");
                }
        );

        return sb.toString();
    }
}
