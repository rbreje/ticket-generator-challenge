package dev.breje.bingo.service;

import dev.breje.bingo.model.Bingo90Strip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

@Service
public class BingoStripGeneratorService {

    @Autowired
    private BingoTicketGeneratorService ticketGeneratorService;

    public Bingo90Strip generateBingo90Strip() {
        Bingo90Strip result = new Bingo90Strip();

        // TODO extract to a factory or bean
        Set<Integer> availableNumbers = new HashSet<>(Bingo90Strip.ALL_NUMBERS);

        IntStream.range(0, Bingo90Strip.TICKETS_PER_STRIP).forEach(
                index -> result.addTicket(ticketGeneratorService.generateBingo90Ticket(availableNumbers))
        );

        return result;
    }

}
