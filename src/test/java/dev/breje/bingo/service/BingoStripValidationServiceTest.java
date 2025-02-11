package dev.breje.bingo.service;

import dev.breje.bingo.exceptions.Bingo90StripValidationException;
import dev.breje.bingo.model.Bingo90Strip;
import dev.breje.bingo.model.Bingo90Ticket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class BingoStripValidationServiceTest {

    @InjectMocks
    private BingoStripValidationService bingoStripValidationService;
    @Mock
    private Bingo90Strip mockBingo90Strip;
    @Mock
    private Bingo90Ticket mockBingo90Ticket;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void validateTicketCompleteness_whenTicketHasAllNumbers_thenPassFurther() {
        List<List<Integer>> columnsWithMissingNumbers = Arrays.asList(
                Arrays.asList(2, 12, 20, 39, 0, 0, 0, 0, 81),
                Arrays.asList(7, 13, 23, 0, 0, 50, 66, 0, 0),
                Arrays.asList(9, 16, 27, 0, 49, 0, 0, 79, 0)
        );
        when(mockBingo90Ticket.getDataPerColumns()).thenReturn(columnsWithMissingNumbers);

        bingoStripValidationService.validateTicketCompleteness(mockBingo90Ticket);
    }

    @Test
    void validateTicketCompleteness_whenTicketDoesNotHaveAllNumbers_throwsException() {
        List<List<Integer>> columnsWithMissingNumbers = Arrays.asList(
                Arrays.asList(2, 12, 20, 39, 0, 0, 0, 0, 81),
                Arrays.asList(7, 13, 0, 0, 0, 50, 66, 0, 0),
                Arrays.asList(9, 16, 27, 0, 49, 0, 0, 0, 0)
        );
        when(mockBingo90Ticket.getDataPerColumns()).thenReturn(columnsWithMissingNumbers);

        Bingo90StripValidationException exception = assertThrows(Bingo90StripValidationException.class, () -> bingoStripValidationService.validateTicketCompleteness(mockBingo90Ticket));
        assertEquals("Not all the required numbers were used", exception.getMessage());
    }

    @Test
    void validateTicketCorrectness_whenTicketIsCorrect_thenProceedFurther() {
        List<List<Integer>> rowsWithTooManyWhiteSpaces = Arrays.asList(
                Arrays.asList(4, 13, 22, 0, 42, 0, 0, 0, 89),
                Arrays.asList(5, 17, 23, 0, 0, 51, 69, 0, 0),
                Arrays.asList(9, 18, 28, 33, 0, 0, 0, 70, 0)
        );
        when(mockBingo90Ticket.getDataPerRows()).thenReturn(rowsWithTooManyWhiteSpaces);

        bingoStripValidationService.validateTicketCorrectness(mockBingo90Ticket);
    }

    @Test
    void validateTicketCorrectness_whenTooManyWhiteSpacesInRow_throwsException() {
        List<List<Integer>> rowsWithTooManyWhiteSpaces = Arrays.asList(
                Arrays.asList(4, 13, 22, 0, 42, 0, 0, 0, 89),
                Arrays.asList(5, 17, 23, 0, 0, 51, 69, 15, 0),
                Arrays.asList(9, 18, 28, 33, 0, 0, 0, 0, 0)
        );
        when(mockBingo90Ticket.getDataPerRows()).thenReturn(rowsWithTooManyWhiteSpaces);

        Bingo90StripValidationException exception = assertThrows(Bingo90StripValidationException.class, () -> bingoStripValidationService.validateTicketCorrectness(mockBingo90Ticket));
        assertEquals("Too many white spaces on a row", exception.getMessage());
    }

    @Test
    void validateTicketCorrectness_whenNumbersOnColumnAreNotAscending_throwsException() {
        List<List<Integer>> rowsWithTooManyWhiteSpaces = Arrays.asList(
                Arrays.asList(4, 17, 22, 0, 42, 0, 0, 0, 89),
                Arrays.asList(5, 13, 23, 0, 0, 51, 69, 0, 0),
                Arrays.asList(9, 18, 28, 33, 0, 0, 0, 70, 0)
        );
        when(mockBingo90Ticket.getDataPerRows()).thenReturn(rowsWithTooManyWhiteSpaces);

        Bingo90StripValidationException exception = assertThrows(Bingo90StripValidationException.class, () -> bingoStripValidationService.validateTicketCorrectness(mockBingo90Ticket));
        assertEquals("The values are not in ascending order per column", exception.getMessage());
    }

    @Test
    void validateTicketCorrectness_whenTooManyWhiteSpacesInColumn_throwsException() {
        List<List<Integer>> columnsWithTooManyWhiteSpaces = Arrays.asList(
                Arrays.asList(4, 5, 9),
                Arrays.asList(13, 17, 18),
                Arrays.asList(22, 23, 28),
                Arrays.asList(0, 0, 33),
                Arrays.asList(42, 0, 0),
                Arrays.asList(0, 51, 0),
                Arrays.asList(0, 69, 0),
                Arrays.asList(70, 0, 71),
                Arrays.asList(0, 0, 0)
        );
        when(mockBingo90Ticket.getDataPerColumns()).thenReturn(columnsWithTooManyWhiteSpaces);

        Bingo90StripValidationException exception = assertThrows(Bingo90StripValidationException.class, () -> bingoStripValidationService.validateTicketCorrectness(mockBingo90Ticket));
        assertEquals("Too many white spaces on a column", exception.getMessage());
    }


    @Test
    void validateBingo90Strip_whenTheStripIsValidStrip_thenProceedFurther() {
        Bingo90Ticket ticket1 = Mockito.mock(Bingo90Ticket.class);
        List<List<Integer>> dataForTicket1 = Arrays.asList(
                Arrays.asList(4, 13, 22, 0, 42, 0, 0, 0, 89),
                Arrays.asList(5, 17, 23, 0, 0, 51, 69, 0, 0),
                Arrays.asList(9, 18, 28, 33, 0, 0, 0, 70, 0)
        );
        when(ticket1.getDataPerRows()).thenReturn(dataForTicket1);

        Bingo90Ticket ticket2 = Mockito.mock(Bingo90Ticket.class);
        List<List<Integer>> dataForTicket2 = Arrays.asList(
                Arrays.asList(0, 10, 20, 37, 43, 0, 62, 0, 0),
                Arrays.asList(6, 12, 24, 0, 0, 53, 0, 0, 81),
                Arrays.asList(7, 14, 26, 38, 0, 0, 0, 78, 0)
        );
        when(ticket2.getDataPerRows()).thenReturn(dataForTicket2);

        Bingo90Ticket ticket3 = Mockito.mock(Bingo90Ticket.class);
        List<List<Integer>> dataForTicket3 = Arrays.asList(
                Arrays.asList(0, 0, 21, 31, 40, 54, 0, 73, 0),
                Arrays.asList(8, 0, 0, 34, 41, 55, 0, 0, 82),
                Arrays.asList(0, 16, 0, 35, 46, 57, 61, 0, 0)
        );
        when(ticket3.getDataPerRows()).thenReturn(dataForTicket3);

        Bingo90Ticket ticket4 = Mockito.mock(Bingo90Ticket.class);
        List<List<Integer>> dataForTicket4 = Arrays.asList(
                Arrays.asList(3, 19, 0, 0, 45, 52, 0, 71, 0),
                Arrays.asList(0, 0, 27, 32, 47, 56, 0, 0, 83),
                Arrays.asList(0, 0, 0, 39, 49, 58, 65, 0, 85)
        );
        when(ticket4.getDataPerRows()).thenReturn(dataForTicket4);

        Bingo90Ticket ticket5 = Mockito.mock(Bingo90Ticket.class);
        List<List<Integer>> dataForTicket5 = Arrays.asList(
                Arrays.asList(1, 0, 25, 0, 44, 0, 60, 72, 80),
                Arrays.asList(0, 0, 0, 36, 0, 50, 66, 75, 86),
                Arrays.asList(0, 11, 0, 0, 0, 0, 68, 79, 87)
        );
        when(ticket5.getDataPerRows()).thenReturn(dataForTicket5);

        Bingo90Ticket ticket6 = Mockito.mock(Bingo90Ticket.class);
        List<List<Integer>> dataForTicket6 = Arrays.asList(
                Arrays.asList(0, 15, 0, 0, 48, 0, 63, 74, 84),
                Arrays.asList(2, 0, 29, 0, 0, 0, 64, 76, 88),
                Arrays.asList(0, 0, 0, 30, 0, 59, 67, 77, 90)
        );
        when(ticket6.getDataPerRows()).thenReturn(dataForTicket6);

        when(mockBingo90Strip.getTickets()).thenReturn(Arrays.asList(ticket1, ticket2, ticket3, ticket4, ticket5, ticket6));

        bingoStripValidationService.validateBingo90Strip(mockBingo90Strip);
    }

    @Test
    void validateBingo90Strip_whenDuplicateNumber_throwsException() {
        Bingo90Ticket ticket1 = Mockito.mock(Bingo90Ticket.class);
        List<List<Integer>> dataForTicket1 = Arrays.asList(
                Arrays.asList(4, 13, 22, 0, 42, 0, 0, 0, 89),
                Arrays.asList(5, 17, 23, 0, 0, 51, 69, 0, 0),
                Arrays.asList(9, 18, 28, 33, 0, 0, 0, 70, 0)
        );
        when(ticket1.getDataPerRows()).thenReturn(dataForTicket1);

        Bingo90Ticket ticket2 = Mockito.mock(Bingo90Ticket.class);
        List<List<Integer>> dataForTicket2 = Arrays.asList(
                Arrays.asList(0, 10, 20, 37, 43, 0, 62, 0, 0),
                Arrays.asList(6, 12, 24, 0, 0, 53, 0, 0, 81),
                Arrays.asList(7, 14, 26, 38, 0, 0, 0, 78, 0)
        );
        when(ticket2.getDataPerRows()).thenReturn(dataForTicket2);

        Bingo90Ticket ticket3 = Mockito.mock(Bingo90Ticket.class);
        List<List<Integer>> dataForTicket3 = Arrays.asList(
                Arrays.asList(0, 0, 21, 31, 40, 54, 0, 73, 0),
                Arrays.asList(8, 0, 0, 34, 41, 55, 0, 0, 82),
                Arrays.asList(0, 16, 0, 35, 46, 57, 61, 0, 0)
        );
        when(ticket3.getDataPerRows()).thenReturn(dataForTicket3);

        Bingo90Ticket ticket4 = Mockito.mock(Bingo90Ticket.class);
        List<List<Integer>> dataForTicket4 = Arrays.asList(
                Arrays.asList(3, 19, 0, 0, 45, 52, 0, 71, 0),
                Arrays.asList(0, 0, 27, 32, 47, 56, 0, 0, 83),
                Arrays.asList(0, 0, 0, 39, 49, 58, 65, 0, 85)
        );
        when(ticket4.getDataPerRows()).thenReturn(dataForTicket4);

        Bingo90Ticket ticket5 = Mockito.mock(Bingo90Ticket.class);
        List<List<Integer>> dataForTicket5 = Arrays.asList(
                Arrays.asList(1, 0, 25, 0, 44, 0, 60, 72, 81),
                Arrays.asList(0, 0, 0, 36, 0, 50, 66, 75, 86),
                Arrays.asList(0, 11, 0, 0, 0, 0, 68, 79, 87)
        );
        when(ticket5.getDataPerRows()).thenReturn(dataForTicket5);

        Bingo90Ticket ticket6 = Mockito.mock(Bingo90Ticket.class);
        List<List<Integer>> dataForTicket6 = Arrays.asList(
                Arrays.asList(0, 15, 0, 0, 48, 0, 63, 74, 84),
                Arrays.asList(2, 0, 29, 0, 0, 0, 64, 76, 88),
                Arrays.asList(0, 0, 0, 30, 0, 59, 67, 77, 90)
        );
        when(ticket6.getDataPerRows()).thenReturn(dataForTicket6);

        when(mockBingo90Strip.getTickets()).thenReturn(Arrays.asList(ticket1, ticket2, ticket3, ticket4, ticket5, ticket6));

        Bingo90StripValidationException exception = assertThrows(Bingo90StripValidationException.class, () -> bingoStripValidationService.validateBingo90Strip(mockBingo90Strip));
        assertEquals("Number 81 is duplicated", exception.getMessage());
    }

    @Test
    void validateBingo90Strip_whenMissingNumber_throwsException() {
        Bingo90Ticket ticket1 = Mockito.mock(Bingo90Ticket.class);
        List<List<Integer>> dataForTicket1 = Arrays.asList(
                Arrays.asList(4, 13, 22, 0, 42, 0, 0, 0, 89),
                Arrays.asList(5, 17, 23, 0, 0, 51, 69, 0, 0),
                Arrays.asList(9, 18, 28, 33, 0, 0, 0, 70, 0)
        );
        when(ticket1.getDataPerRows()).thenReturn(dataForTicket1);

        Bingo90Ticket ticket2 = Mockito.mock(Bingo90Ticket.class);
        List<List<Integer>> dataForTicket2 = Arrays.asList(
                Arrays.asList(0, 10, 20, 37, 43, 0, 62, 0, 0),
                Arrays.asList(6, 12, 24, 0, 0, 53, 0, 0, 81),
                Arrays.asList(7, 14, 26, 38, 0, 0, 0, 78, 0)
        );
        when(ticket2.getDataPerRows()).thenReturn(dataForTicket2);

        Bingo90Ticket ticket3 = Mockito.mock(Bingo90Ticket.class);
        List<List<Integer>> dataForTicket3 = Arrays.asList(
                Arrays.asList(0, 0, 21, 31, 40, 54, 0, 73, 0),
                Arrays.asList(8, 0, 0, 34, 41, 55, 0, 0, 82),
                Arrays.asList(0, 16, 0, 35, 46, 57, 61, 0, 0)
        );
        when(ticket3.getDataPerRows()).thenReturn(dataForTicket3);

        Bingo90Ticket ticket4 = Mockito.mock(Bingo90Ticket.class);
        List<List<Integer>> dataForTicket4 = Arrays.asList(
                Arrays.asList(3, 19, 0, 0, 45, 52, 0, 71, 0),
                Arrays.asList(0, 0, 27, 32, 47, 56, 0, 0, 83),
                Arrays.asList(0, 0, 0, 39, 49, 58, 65, 0, 85)
        );
        when(ticket4.getDataPerRows()).thenReturn(dataForTicket4);

        Bingo90Ticket ticket5 = Mockito.mock(Bingo90Ticket.class);
        List<List<Integer>> dataForTicket5 = Arrays.asList(
                Arrays.asList(1, 0, 25, 0, 44, 0, 60, 72, 80),
                Arrays.asList(0, 0, 0, 36, 0, 50, 66, 75, 86),
                Arrays.asList(0, 11, 0, 0, 0, 0, 0, 79, 87)
        );
        when(ticket5.getDataPerRows()).thenReturn(dataForTicket5);

        Bingo90Ticket ticket6 = Mockito.mock(Bingo90Ticket.class);
        List<List<Integer>> dataForTicket6 = Arrays.asList(
                Arrays.asList(0, 15, 0, 0, 48, 0, 63, 74, 84),
                Arrays.asList(2, 0, 29, 0, 0, 0, 64, 76, 88),
                Arrays.asList(0, 0, 0, 30, 0, 59, 67, 77, 90)
        );
        when(ticket6.getDataPerRows()).thenReturn(dataForTicket6);

        when(mockBingo90Strip.getTickets()).thenReturn(Arrays.asList(ticket1, ticket2, ticket3, ticket4, ticket5, ticket6));

        Bingo90StripValidationException exception = assertThrows(Bingo90StripValidationException.class, () -> bingoStripValidationService.validateBingo90Strip(mockBingo90Strip));
        assertEquals("Not all the required numbers were used", exception.getMessage());
    }
}
