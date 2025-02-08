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
        IntStream.rangeClosed(0, 8)
                .boxed()
                .forEach(group -> availableNumbersPerGroup.put(group, new LinkedList<>()));

        // randomize the numbers order (Step 1)
        List<Integer> shuffledNumbers = IntStream.rangeClosed(1, 90)
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

        // add one number to all the columns of all the tickets (Step 3)
        Bingo90Strip result = new Bingo90Strip();
        IntStream.range(0, 6).forEach(ticketIndex -> {
                    Bingo90Ticket ticket = result.getTicket(ticketIndex);
                    IntStream.rangeClosed(0, 8).forEach(columnIndex ->
                            {
                                List<Integer> column = ticket.getColumn(columnIndex);
                                column.add(availableNumbersPerGroup.get(columnIndex).pop());
                                Collections.sort(column);
                            }
                    );
                }
        );

//        IntStream.range(0, 3).forEach(index -> {
//                    Bingo90Ticket ticket = result.getTicket(index);
//
//                }
//        );

        return result;
    }

}
