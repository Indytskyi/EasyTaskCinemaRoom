package com.indytskyi.introduction;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;

import com.indytskyi.entity.Cinema;
import com.indytskyi.service.impl.CinemaServiceImpl;
import com.indytskyi.validation.ValidateInputInteger;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CinemaIntroductionTest {

    Cinema cinema;

    @Mock
    CinemaServiceImpl cinemaService;

    CinemaIntroduction cinemaIntroduction;

    ValidateInputInteger validateInputInteger;

    @Before
    public void setUp() {
        cinema = new Cinema();
        validateInputInteger = new ValidateInputInteger();
        cinemaIntroduction = new CinemaIntroduction(cinema, cinemaService, validateInputInteger);
    }

    @Test
    public void canShowTheSeatsWithMenu() {
        //GIVEN
        ByteArrayInputStream in = new ByteArrayInputStream("3\n3\n1\n0\n".getBytes());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        System.setIn(in);
        System.setOut(ps);

        //WHEN
        boolean response = cinemaIntroduction.menu();
        assertTrue(response);
    }

    @Test
    public void canCheckValidationOfFormatInputRowsAndSeats() {
        //GIVEN
        String mockConsole = """
                Hello
                Hello
                3
                3
                0
                """;
        ByteArrayInputStream in = new ByteArrayInputStream(mockConsole.getBytes());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        System.setIn(in);
        System.setOut(ps);

        //WHEN
        boolean response = cinemaIntroduction.menu();
        assertTrue(response);
    }

    @Test
    public void canCheckValidationOfSizeInputRowsAndSeats() {
        //GIVEN
        String mockConsole = """
                1
                222
                3
                3
                0
                """;
        ByteArrayInputStream in = new ByteArrayInputStream(mockConsole.getBytes());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        System.setIn(in);
        System.setOut(ps);

        //WHEN
        boolean response = cinemaIntroduction.menu();
        assertTrue(response);
    }

    @Test
    public void canCheckValidationOfControllerMenu() {
        //GIVEN
        String mockConsole = """
                3
                3
                22
                0
                """;
        ByteArrayInputStream in = new ByteArrayInputStream(mockConsole.getBytes());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        System.setIn(in);
        System.setOut(ps);

        //WHEN
        boolean response = cinemaIntroduction.menu();
        assertTrue(response);
    }

    @Test
    public void canCheckNavigationToBookTicket() {
        //GIVEN
        String mockConsole = """
                3
                3
                2
                0
                """;
        ByteArrayInputStream in = new ByteArrayInputStream(mockConsole.getBytes());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        System.setIn(in);
        System.setOut(ps);

        //WHEN
        doNothing().when(cinemaService).bookTickets();
        boolean response = cinemaIntroduction.menu();
        assertTrue(response);
    }

    @Test
    public void canCheckNavigationToGetStatistic() {
        //GIVEN
        String mockConsole = """
                3
                3
                3
                0
                """;
        ByteArrayInputStream in = new ByteArrayInputStream(mockConsole.getBytes());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        System.setIn(in);
        System.setOut(ps);

        //WHEN
        doNothing().when(cinemaService).statistic();
        boolean response = cinemaIntroduction.menu();
        assertTrue(response);
    }
}