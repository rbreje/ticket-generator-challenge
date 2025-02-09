package dev.breje.bingo.service;

import dev.breje.bingo.model.Bingo90Strip;
import dev.breje.bingo.model.Bingo90Ticket;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BingoStripGeneratorService {

    public Bingo90Strip generateBingo90Strip() {
        // initiate the groups (Step 1)
        Map<Integer, LinkedList<Integer>> availableNumbersPerGroup = new HashMap<>();
        IntStream.range(0, Bingo90Ticket.COLUMNS)
                .boxed()
                .forEach(group -> availableNumbersPerGroup.put(group, new LinkedList<>()));

        // randomize the numbers order (Step 1)
        List<Integer> shuffledNumbers = IntStream.rangeClosed(1, Bingo90Strip.NUMBERS_PER_STRIP)
                .boxed()
                .collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
                    Collections.shuffle(collected);
                    return collected.stream();
                }))
                .toList();

        // split them into separate lists & sort them (Step 2)
        shuffledNumbers.forEach(
                number -> {
                    int group = BigInteger.valueOf(number).divide(BigInteger.valueOf(10)).intValue();
                    group = group == 9 ? 8 : group;
                    availableNumbersPerGroup.get(group).add(number);
                    Collections.sort(availableNumbersPerGroup.get(group));
                }
        );

        // add one number to all the columns of all the tickets (Step 3 & 4)
        Bingo90Strip result = new Bingo90Strip();
        IntStream.range(0, Bingo90Strip.TICKETS_PER_STRIP).forEach(ticketIndex -> {
                    Bingo90Ticket ticket = result.getTicket(ticketIndex);
                    IntStream.range(0, Bingo90Ticket.COLUMNS).forEach(columnIndex ->
                            {
                                List<Integer> column = ticket.getColumn(columnIndex);
                                column.add(availableNumbersPerGroup.get(columnIndex).pop());
                                Collections.sort(column);
                            }
                    );
                }
        );

        // first 3 tickets use a random selection (Step 5)
        IntStream.range(0, 3).forEach(index -> {
                    Bingo90Ticket ticket = result.getTicket(index);
                    pickRandomNumbers(ticket.getDataAsColumns(), availableNumbersPerGroup);
                }
        );

        // 4th ticket pick 1 number from the last column (Step 6)
        if (availableNumbersPerGroup.get(8).size() == 5) {
            result.getTicket(3).getDataAsColumns().get(8).add(availableNumbersPerGroup.get(8).pop());
            Collections.sort(result.getTicket(3).getDataAsColumns().get(8));
        }
        pickRandomNumbers(result.getTicket(3).getDataAsColumns(), availableNumbersPerGroup);

        return result;
    }

    private void pickRandomNumbers(Map<Integer, List<Integer>> ticketColumns, Map<Integer, LinkedList<Integer>> availableNumbersPerGroup) {
        int numbersToWithdraw = Bingo90Ticket.NUMBERS_PER_TICKET - ticketColumns.values()
                .stream()
                .map(List::size)
                .reduce(0, Integer::sum);

        int firstAttempt = Math.min(numbersToWithdraw, Bingo90Ticket.ROWS);
        int secondAttempt = numbersToWithdraw - firstAttempt;

        IntStream.range(0, firstAttempt).forEach(iteration -> {
            for (Map.Entry<Integer, LinkedList<Integer>> colUnusedNumbers : availableNumbersPerGroup.entrySet()) {
                if (!colUnusedNumbers.getValue().isEmpty() && ticketColumns.get(colUnusedNumbers.getKey()).size() < Bingo90Ticket.ROWS) {
                    ticketColumns.get(colUnusedNumbers.getKey()).add(colUnusedNumbers.getValue().pop());
                    Collections.sort(ticketColumns.get(colUnusedNumbers.getKey()));
                    break;
                }
            }
        });

        IntStream.range(0, secondAttempt).forEach(iteration -> {
            for (Map.Entry<Integer, LinkedList<Integer>> colUnusedNumbers : availableNumbersPerGroup.entrySet()) {
                if (!colUnusedNumbers.getValue().isEmpty() && ticketColumns.get(colUnusedNumbers.getKey()).size() < Bingo90Ticket.ROWS) {
                    ticketColumns.get(colUnusedNumbers.getKey()).add(colUnusedNumbers.getValue().pop());
                    Collections.sort(ticketColumns.get(colUnusedNumbers.getKey()));
                    break;
                }
            }
        });
    }

}
