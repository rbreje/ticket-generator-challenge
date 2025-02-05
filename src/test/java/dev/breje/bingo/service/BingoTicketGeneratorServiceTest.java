package dev.breje.bingo.service;

import dev.breje.bingo.model.Bingo90Strip;
import dev.breje.bingo.model.Bingo90Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class BingoTicketGeneratorServiceTest {
    
    private BingoTicketGeneratorService bingoTicketGeneratorService;
    
    @BeforeEach
    public void setUp() {
        bingoTicketGeneratorService = new BingoTicketGeneratorService();
    }
    
    @Test
    public void testGenerateBingo90Ticket() {
        Set<Integer> availableNumbers = new HashSet<>(Bingo90Strip.ALL_NUMBERS);
        
        Bingo90Ticket ticket = bingoTicketGeneratorService.generateBingo90Ticket(availableNumbers);

        System.out.println(ticket);
    }
    
}
