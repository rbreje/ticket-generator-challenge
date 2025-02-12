package dev.breje.bingo.service.impl;

import dev.breje.bingo.exceptions.Bingo90StripValidationException;
import dev.breje.bingo.model.Bingo90Strip;
import dev.breje.bingo.model.Bingo90Ticket;
import dev.breje.bingo.service.IBingoStripValidationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BingoStripValidationService implements IBingoStripValidationService {

    @Override
    public void validateBingo90Strip(Bingo90Strip bingo90Strip) {
        validateStripCompleteness(bingo90Strip);
        bingo90Strip.getTickets().forEach(this::validateTicketCorrectness);
        bingo90Strip.getTickets().forEach(this::validateTicketCompleteness);
    }

    void validateStripCompleteness(Bingo90Strip bingo90Strip) {
        List<Integer> usedNumbers = new ArrayList<>();

        bingo90Strip.getTickets()
                .stream()
                .map(Bingo90Ticket::getDataPerRows)
                .forEach(
                        rows -> rows.forEach(
                                row -> row.forEach(
                                        number -> {
                                            if (usedNumbers.contains(number) && 0 != number) {
                                                throw new Bingo90StripValidationException("Number " + number + " is duplicated");
                                            }
                                            if (0 != number) {
                                                usedNumbers.add(number);
                                            }
                                        }
                                )
                        )
                );
        if (usedNumbers.size() != Bingo90Strip.NUMBERS_PER_STRIP) {
            throw new Bingo90StripValidationException("Not all the required numbers were used");
        }
    }

    void validateTicketCorrectness(Bingo90Ticket bingo90Ticket) {
        bingo90Ticket.getDataPerRows()
                .forEach(
                        row -> {
                            AtomicInteger whiteSpacesCount = new AtomicInteger(0);
                            row.forEach(
                                    number -> {
                                        if (0 == number) {
                                            whiteSpacesCount.getAndIncrement();
                                        }
                                    });
                            if (whiteSpacesCount.get() > 4) {
                                throw new Bingo90StripValidationException("Too many white spaces on a row");
                            }
                        }
                );
        bingo90Ticket.getDataPerColumns()
                .forEach(
                        column -> {
                            AtomicInteger whiteSpacesCount = new AtomicInteger();
                            AtomicInteger prevNumber = new AtomicInteger(-1);
                            column.forEach(
                                    number -> {
                                        if (0 == number) {
                                            whiteSpacesCount.getAndIncrement();
                                        } else {
                                            if (number < prevNumber.get()) {
                                                throw new Bingo90StripValidationException("The values are not in ascending order per column");
                                            }
                                            prevNumber.set(number);
                                        }
                                    });
                            if (whiteSpacesCount.get() == 3) {
                                throw new Bingo90StripValidationException("Too many white spaces on a column");
                            }
                        }
                );
    }

    void validateTicketCompleteness(Bingo90Ticket bingo90Ticket) {
        AtomicInteger numbersCount = new AtomicInteger();
        bingo90Ticket.getDataPerColumns().forEach(
                column -> column.forEach(
                        number -> {
                            if (0 != number) {
                                numbersCount.getAndIncrement();
                            }
                        }
                )
        );
        if (numbersCount.get() != Bingo90Ticket.NUMBERS_PER_TICKET) {
            throw new Bingo90StripValidationException("Not all the required numbers were used");
        }
    }
}
