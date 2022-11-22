package com.indytskyi.service.impl;

import static org.junit.Assert.assertEquals;

import com.indytskyi.entity.Cinema;
import com.indytskyi.service.CinemaService;
import com.indytskyi.validation.InputValidator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CinemaServiceImplTest {

    Cinema cinema;
    CinemaService cinemaService;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @Before
    public void setUp() {
        cinema = new Cinema();
        cinemaService = new CinemaServiceImpl(cinema, System.in);
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @After
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void calculateTotalIncomeInTheRoomWithLessThan60Seats() {
        //GIVEN
        cinema.setRows(3);
        cinema.setSeats(3);
        int expected = 90;

        //WHEN
        cinemaService.calculateTotalIncome();

        //THEN
        assertEquals(expected, cinema.getTotalIncome());
    }

    @Test
    public void calculateTotalIncomeInTheRoomWithMoreThan60Seats() {
        //GIVEN
        cinema.setRows(10);
        cinema.setSeats(10);
        int expected = 900;

        //WHEN
        cinemaService.calculateTotalIncome();

        //THEN
        assertEquals(expected, cinema.getTotalIncome());
    }

    @Test
    public void viewStatisticWithoutClients() {
        //GIVEN
        String expected = """

                Number of purchased tickets: 0\s
                Percentage: 0.00%\s
                Current income: $0\s
                Total income: $900
                """;
        cinema.setRows(10);
        cinema.setSeats(10);
        cinema.setTotalIncome(900);

        //WHEN
        cinemaService.getStatistic();

        //THEN
        assertEquals(expected, outputStreamCaptor.toString());
    }

    @Test
    public void viewStatisticWithClients() {
        //GIVEN
        String expected = """

                Number of purchased tickets: 5\s
                Percentage: 5.00%\s
                Current income: $46\s
                Total income: $900
                """;
        cinema.setRows(10);
        cinema.setSeats(10);
        cinema.setTotalIncome(900);
        cinema.setNumberOgPurchasedTickets(5);
        cinema.setCurrentIncome(46);

        //WHEN
        cinemaService.getStatistic();

        //THEN
        assertEquals(expected, outputStreamCaptor.toString());
    }

    @Test
    public void bookTicketWithIncorrectInputFormat() {
        //GIVEN
        ByteArrayInputStream in = new ByteArrayInputStream("dfd\n6\n3\n3\n".getBytes());
        System.setIn(in);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        System.setOut(ps);
        cinema.setRows(5);
        cinema.setSeats(5);
        cinema.setCinemaRoom(new String[5][5]);
        String expected = """

                Enter a row number:
                Enter a seat number in that row:
                You input incorrect values (Input only integers)!!!
                                           
                Enter a row number:
                Enter a seat number in that row:
                Ticket price: $10

                """;
        cinemaService = new CinemaServiceImpl(cinema, System.in);

        //WHEN
        cinemaService.bookTickets();

        //THEN
        assertEquals(expected, byteArrayOutputStream.toString());
    }


    @Test
    public void bookTicketWithIncorrectNumberOfSeat() {
        //GIVEN
        ByteArrayInputStream in = new ByteArrayInputStream("6\n6\n3\n3\n".getBytes());
        System.setIn(in);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        System.setOut(ps);
        cinema.setRows(5);
        cinema.setSeats(5);
        cinema.setCinemaRoom(new String[5][5]);
        String expected = """

                Enter a row number:
                Enter a seat number in that row:
                Wrong input!

                Enter a row number:
                Enter a seat number in that row:
                Ticket price: $10

                """;
        cinemaService = new CinemaServiceImpl(cinema, System.in);

        //WHEN
        cinemaService.bookTickets();

        //THEN
        assertEquals(expected, byteArrayOutputStream.toString());
    }

    @Test
    public void bookTicketWithCorrectNumberOfSeat() {
        //GIVEN
        ByteArrayInputStream in = new ByteArrayInputStream("3\n3\n4\n4".getBytes());
        System.setIn(in);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        System.setOut(ps);
        cinema.setRows(5);
        cinema.setSeats(5);
        String expected = """

                Enter a row number:
                Enter a seat number in that row:
                That ticket has already been purchased!

                Enter a row number:
                Enter a seat number in that row:
                Ticket price: $10

                """;
        cinemaService = new CinemaServiceImpl(cinema, System.in);
        String[][] given = new String[6][6];
        cinema.setCinemaRoom(given);
        given[3][3] = "B";

        //WHEN
        cinemaService.bookTickets();

        //THEN
        assertEquals(expected, byteArrayOutputStream.toString());
    }


    @Test
    public void bookTicketWithNumberOfSeatWithOrdinaryPrice() {
        //GIVEN
        ByteArrayInputStream in = new ByteArrayInputStream("8\n8\n".getBytes());
        System.setIn(in);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        System.setOut(ps);
        cinema.setRows(10);
        cinema.setSeats(10);
        cinema.setCinemaRoom(new String[10][10]);
        String expected = """

                Enter a row number:
                Enter a seat number in that row:
                Ticket price: $8

                """;
        cinemaService = new CinemaServiceImpl(cinema, System.in);

        //WHEN
        cinemaService.bookTickets();

        //THEN
        assertEquals(expected, byteArrayOutputStream.toString());
    }

    @Test
    public void bookTicketWithNumberOfSeatWithPremiumPrice() {
        //GIVEN
        ByteArrayInputStream in = new ByteArrayInputStream("5\n5\n".getBytes());
        System.setIn(in);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        System.setOut(ps);
        cinema.setRows(10);
        cinema.setSeats(10);
        cinema.setCinemaRoom(new String[10][10]);
        String expected = """

                Enter a row number:
                Enter a seat number in that row:
                Ticket price: $10

                """;
        cinemaService = new CinemaServiceImpl(cinema, System.in);

        //WHEN
        cinemaService.bookTickets();

        //THEN
        assertEquals(expected, byteArrayOutputStream.toString());
    }
}