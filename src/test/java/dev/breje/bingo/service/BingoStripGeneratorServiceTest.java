package dev.breje.bingo.service;

import dev.breje.bingo.model.Bingo90Strip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BingoStripGeneratorServiceTest {

    private BingoStripGeneratorService bingoStripGeneratorService;

    @BeforeEach
    public void setUp() {
        bingoStripGeneratorService = new BingoStripGeneratorService();
    }

    @Test
    public void testGenerateBingo90Strip() {
        Bingo90Strip bingo90Strip = bingoStripGeneratorService.generateBingo90Strip();

        System.out.println(bingo90Strip);
    }

}
