package dev.breje.bingo.service.impl;

import dev.breje.bingo.model.Bingo90Strip;
import dev.breje.bingo.service.IBingoStripValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BingoStripGeneratorServiceTest {

    private BingoStripGeneratorService bingoStripGeneratorService;

    @BeforeEach
    public void setUp() {
        bingoStripGeneratorService = new BingoStripGeneratorService();
    }

    @Test
    void testGenerateBingo90Strip() {
        Bingo90Strip bingo90Strip = bingoStripGeneratorService.generateBingo90Strip();

        IBingoStripValidationService validationService = new BingoStripValidationService();
        validationService.validateBingo90Strip(bingo90Strip);
    }

    @Test
    void testGenerateBingo90StripByExecutionTime() {
        long startTime = System.nanoTime();

        IntStream.range(0, 10000).forEach(index -> bingoStripGeneratorService.generateBingo90Strip());

        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Execution time: " + duration + " seconds");
        assertTrue(duration < 1); // 1 second
    }

}
