package dev.breje.bingo.service;

import dev.breje.bingo.model.Bingo90Ticket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class BingoTicketGeneratorService {

    private final Random random = new Random();

    public Bingo90Ticket generateBingo90Ticket(Set<Integer> availableNumbers) {
        Bingo90Ticket result = new Bingo90Ticket();

        List<List<Integer>> columns = new ArrayList<>();
        for (int col = 0; col < Bingo90Ticket.COLUMNS; ++col) {
            int min = col * 10 + 1;
            int max = (col == 8) ? 90 : (col == 0) ? 9 : (col + 1) * 10;
            List<Integer> numbers = getUniqueRandomNumbers(min, max, availableNumbers, getRandomNumbersPerColumn(result));
            columns.add(numbers);
        }
        result.addColumns(columns);

        return result;
    }

    private static List<Integer> getUniqueRandomNumbers(int min, int max, Set<Integer> availableNumbers, int count) {
        List<Integer> numbers = new ArrayList<>();
        Random rand = new Random();
        while (numbers.size() < count) {
            int num = rand.nextInt(max - min + 1) + min;
            if (availableNumbers.contains(num)) {
                availableNumbers.remove(num);
                numbers.add(num);
            }
        }
        Collections.sort(numbers);
        return numbers;
    }

    private int getRandomNumbersPerColumn(Bingo90Ticket ticket) {
        int remaining = Bingo90Ticket.MAX_NUMBERS - ticket.getCurrentNumbersCount();
        if (remaining < 3) {
            return remaining;
        }
        return Math.min(remaining, random.nextInt(Bingo90Ticket.ROWS) + 1);
    }

}
