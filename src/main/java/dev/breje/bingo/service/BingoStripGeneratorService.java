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
import java.util.concurrent.atomic.AtomicInteger;
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

        // 4th ticket pick 1 number from the last column if there are too many (Step 6)
        if (availableNumbersPerGroup.get(8).size() == 5) {
            result.getTicket(3).getDataAsColumns().get(8).add(availableNumbersPerGroup.get(8).pop());
        }
        pickRandomNumbers(result.getTicket(3).getDataAsColumns(), availableNumbersPerGroup);

        // 5th ticket (Step 7)
        availableNumbersPerGroup.forEach((key, value) -> {
            if (value.size() == 4) {
                result.getTicket(4).getDataAsColumns().get(key).add(value.pop());
                result.getTicket(4).getDataAsColumns().get(key).add(value.pop());
            }
            if (value.size() == 3) {
                result.getTicket(4).getDataAsColumns().get(key).add(value.pop());
            }
        });
        pickRandomNumbers(result.getTicket(4).getDataAsColumns(), availableNumbersPerGroup);

        // 6th ticket (Step 8)
        availableNumbersPerGroup.forEach((key, value) -> {
            value.forEach(number -> result.getTicket(5).getDataAsColumns().get(key).add(number));
        });

        // Sort already added numbers (Step 9)
        result.getTickets().forEach(ticket -> {
            ticket.getDataAsColumns().values().forEach(Collections::sort);
        });

        // Adding blank spaces (Step 10)
        result.getTickets().forEach(ticket -> {
            Map<Integer, LinkedList<Integer>> ticketColumns = ticket.getDataAsColumns();

            // substep 1
            ticketColumns.values().stream()
                    .filter(columnList -> columnList.size() < 3)
                    .collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
                        Collections.shuffle(collected);
                        return collected.stream();
                    }))
                    .limit(4)
                    .forEach(columnList -> columnList.addFirst(0));

            AtomicInteger secondRowInsertCounter = new AtomicInteger();
            // substep 2
            ticketColumns.values().stream()
                    .filter(columnList -> columnList.size() == 1)
                    .collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
                        Collections.shuffle(collected);
                        return collected.stream();
                    }))
                    .limit(4)
                    .forEach(columnList -> {
                        secondRowInsertCounter.getAndIncrement();
                        columnList.add(1, 0);
                        columnList.addLast(0);
                    });

            // substep 3
            ticketColumns.values().stream()
                    .filter(columnList -> columnList.size() == 2)
                    .collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
                        Collections.shuffle(collected);
                        return collected.stream();
                    }))
                    .limit(4 - secondRowInsertCounter.get())
                    .forEach(columnList -> columnList.add(1, 0));

            // substep 4
            ticketColumns.values().stream()
                    .filter(columnList -> columnList.size() == 2)
                    .forEach(columnList -> columnList.add(0));
        });

        return result;
    }

    private void pickRandomNumbers(Map<Integer, LinkedList<Integer>> ticketColumns, Map<Integer, LinkedList<Integer>> availableNumbersPerGroup) {
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
                    break;
                }
            }
        });

        IntStream.range(0, secondAttempt).forEach(iteration -> {
            for (Map.Entry<Integer, LinkedList<Integer>> colUnusedNumbers : availableNumbersPerGroup.entrySet()) {
                if (!colUnusedNumbers.getValue().isEmpty() && ticketColumns.get(colUnusedNumbers.getKey()).size() < Bingo90Ticket.ROWS) {
                    ticketColumns.get(colUnusedNumbers.getKey()).add(colUnusedNumbers.getValue().pop());
                    break;
                }
            }
        });
    }

}
