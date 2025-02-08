package dev.breje.bingo.service;

import dev.breje.bingo.model.Bingo90Strip;
import dev.breje.bingo.model.Bingo90Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class BingoStripGeneratorServiceTest {
    
    private BingoStripGeneratorService bingoStripGeneratorService;

    @BeforeEach
    public void setUp() {
        bingoStripGeneratorService = new BingoStripGeneratorService();
    }

    @Test
    public void testGenerateBingo90Strip() {
        bingoStripGeneratorService.generateBingo90Strip();
    }
    
}
