package dev.breje.bingo.service.impl;

import dev.breje.bingo.model.Bingo90Strip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BingoStripServiceTest {

    private BingoStripService bingoStripService;

    @BeforeEach
    void setUp() {
        BingoStripGeneratorService bingoStripGeneratorService = new BingoStripGeneratorService();
        BingoStripValidationService bingoStripValidationService = new BingoStripValidationService();
        bingoStripService = new BingoStripService(bingoStripGeneratorService, bingoStripValidationService);
    }

    @Test
    void testGenerateBingo90Strip() {
        Bingo90Strip bingo90Strip = bingoStripService.generateBingo90Strip();

        BingoStripValidationService validationService = new BingoStripValidationService();
        validationService.validateBingo90Strip(bingo90Strip);
    }

    @Test
    void testGenerateBingo90StripByExecutionTime() {
        long startTime = System.nanoTime();

        IntStream.range(0, 10000).forEach(index -> bingoStripService.generateBingo90Strip());

        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Execution time: " + duration + " seconds");
        assertTrue(duration < 1); // 1 second
    }
}
