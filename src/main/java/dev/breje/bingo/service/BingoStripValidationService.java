package dev.breje.bingo.service;

import dev.breje.bingo.exceptions.Bingo90StripValidationException;
import dev.breje.bingo.model.Bingo90Strip;
import dev.breje.bingo.model.Bingo90Ticket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BingoStripValidationService {

    public void validateBingo90Strip(Bingo90Strip bingo90Strip) {
        // check if all numbers are present
        // check each ticket has 15 numbers
        // check the white spaces on each line
        // check the ascending order per column

        List<Integer> usedNumbers = new ArrayList<>();

        bingo90Strip.getTickets()
                .stream()
                .map(Bingo90Ticket::getDataPerRows)
                .forEach(
                        rows -> rows.forEach(
                                row -> row.forEach(
                                        number -> {
                                            if (usedNumbers.contains(number)) {
                                                throw new Bingo90StripValidationException("Number " + number + " is duplicated");
                                            }
                                            usedNumbers.add(number);
                                        }
                                )
                        )
                );
        if (usedNumbers.size() != Bingo90Strip.NUMBERS_PER_STRIP) {
            throw new Bingo90StripValidationException("Not all the required numbers were used");
        }

        bingo90Strip.getTickets()
                .stream()
                .map(Bingo90Ticket::getDataPerRows)
                .forEach(
                        rows -> rows.forEach(
                                row -> {
                                    AtomicInteger whiteSpacesCount = new AtomicInteger();
                                    row.forEach(
                                            number -> {
                                                if (0 == number) {
                                                    whiteSpacesCount.getAndIncrement();
                                                }
                                            });
                                    if (whiteSpacesCount.get() >= 4) {
                                        throw new Bingo90StripValidationException("Too many white spaces on a row");
                                    }
                                }
                        )
                );

        bingo90Strip.getTickets()
                .stream()
                .map(Bingo90Ticket::getDataPerColumns)
                .forEach(columns -> columns.forEach(
                        column -> {
                            AtomicInteger whiteSpacesCount = new AtomicInteger();
                            column.forEach(
                                    number -> {
                                        if (0 == number) {
                                            whiteSpacesCount.getAndIncrement();
                                        }
                                    });
                            if (whiteSpacesCount.get() == 3) {
                                throw new Bingo90StripValidationException("Too many white spaces on a columno");
                            }
                        }
                ));
    }


}
