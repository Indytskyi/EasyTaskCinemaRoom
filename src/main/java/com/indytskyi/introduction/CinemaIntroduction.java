package com.indytskyi.introduction;

import static com.indytskyi.entity.Cinema.MAX_SIZE_OF_COUNT_ROWS;
import static com.indytskyi.entity.Cinema.MAX_SIZE_OF_SEATS_IN_ROW;

import com.indytskyi.entity.Cinema;
import com.indytskyi.service.CinemaService;
import com.indytskyi.validation.ValidateInputInteger;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CinemaIntroduction {

    private final Cinema cinema;
    private final CinemaService cinemaService;

    private final ValidateInputInteger validateInputInteger;


    public boolean menu() {
        Scanner scanner = new Scanner(System.in);

        String inputRows;
        String inputSeatsInRow;
        do {
            System.out.println("Enter the number of rows:");
            inputRows = scanner.nextLine();
            System.out.println("Enter the number of seats in each row:");
            inputSeatsInRow = scanner.nextLine();
        }
        while (!validationSizeOfCinemaRoom(inputRows, inputSeatsInRow));

        createCinemaRoom();
        defaultRoom(cinema.getCinemaRoom());

        while (true) {
            System.out.println("""

                    1. Show the seats
                    2. Buy a ticket
                    3. Statistics
                    0. Exit""");
            int controller;
            String controllerString = scanner.nextLine();
            if (!controllerString.matches("[0-3]")) {
                System.out.println("\nIncorrect option! Try again.\n");
                continue;
            } else {
                controller = Integer.parseInt(controllerString);
            }
            switch (controller) {
                case 1 -> showTheSeats(cinema.getCinemaRoom());
                case 2 -> cinemaService.bookTickets();
                case 3 -> cinemaService.statistic();
                case 0 -> {
                    return true;
                }
            }

        }
    }

    public boolean validationSizeOfCinemaRoom(String inputRows, String inputSeatsInRow) {

        if (!validateInputInteger.validate(inputRows)
                || !validateInputInteger.validate(inputSeatsInRow)) {
            System.out.println("You input incorrect values (Input only integers)!!!");
            return false;
        }
        int seatsInRow = Integer.parseInt(inputSeatsInRow);
        int rows = Integer.parseInt(inputRows);
        if (rows <= 0 || rows > MAX_SIZE_OF_COUNT_ROWS
                || seatsInRow <= 0 || seatsInRow > MAX_SIZE_OF_SEATS_IN_ROW) {
            System.out.printf("""
                    The room must have size:
                    - rows between (1-%d)
                    - seats in a row (1-%d)
                    %n""", MAX_SIZE_OF_COUNT_ROWS, MAX_SIZE_OF_SEATS_IN_ROW);
            return false;
        }

        cinema.setRows(rows);
        cinema.setSeats(seatsInRow);
        return true;
    }


    public void showTheSeats(String[][] cinemaRoom) {
        System.out.println("Cinema: ");
        for (String[] seatsInRow : cinemaRoom) {
            for (String oneSeat : seatsInRow) {
                System.out.print(oneSeat + " ");
            }
            System.out.println();
        }
    }


    public void defaultRoom(String[][] cinemaRoom) {
        for (int i = 0; i <= cinema.getRows(); i++) {
            for (int j = 0; j <= cinema.getSeats(); j++) {
                if (i == 0 && j == 0) {
                    cinemaRoom[i][j] = " ";
                } else if (i == 0) {
                    cinemaRoom[i][j] = String.valueOf(j);
                } else if (j == 0) {
                    cinemaRoom[i][j] = String.valueOf(i);
                } else {
                    cinemaRoom[i][j] = "S";
                }
            }
        }
        cinemaService.calculateTotalIncome();
    }

    private void createCinemaRoom() {
        cinema.setCinemaRoom(new String[cinema.getRows() + 1][cinema.getSeats() + 1]);
    }


}
