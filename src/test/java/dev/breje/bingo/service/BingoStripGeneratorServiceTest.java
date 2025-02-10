package dev.breje.bingo.service;

import dev.breje.bingo.model.Bingo90Strip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

        System.out.println(bingo90Strip.getTicket(0).getDataPerRows());
    }

    @Test
    public void testGenerateBingo90StripByExecutionTime() {
        long startTime = System.nanoTime();

        IntStream.range(0, 10000).forEach(index -> bingoStripGeneratorService.generateBingo90Strip());

        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Execution time: " + duration + " seconds");
        assertTrue(duration < 1); // 1 second
    }

}
