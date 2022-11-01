package com.indytskyi.service.impl;

import static com.indytskyi.entity.Cinema.PRICE_CHANGE;
import static com.indytskyi.entity.Cinema.PRICE_OF_ORDINARY_SEATS;
import static com.indytskyi.entity.Cinema.PRICE_OF_PREMIUM_SEATS;
import static com.indytskyi.validation.InputValidator.validate;

import com.indytskyi.entity.Cinema;
import com.indytskyi.service.CinemaService;
import java.io.InputStream;
import java.util.Scanner;

public class CinemaServiceImpl implements CinemaService {

    private final Cinema cinema;
    private final Scanner scanner;

    public CinemaServiceImpl(Cinema cinema, InputStream inputStream) {
        this.cinema = cinema;
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public void getStatistic() {
        double percentageFullness = cinema.getNumberOgPurchasedTickets() == 0 ? 0 :
                cinema.getNumberOgPurchasedTickets() * 100 / (double) (cinema.getRows() * cinema.getSeats());
        System.out.printf("""

                        Number of purchased tickets: %d\s
                        Percentage: %.2f%s\s
                        Current income: $%d\s
                        Total income: $%d
                        """,
                cinema.getNumberOgPurchasedTickets(),
                percentageFullness,
                "%",
                cinema.getCurrentIncome(), cinema.getTotalIncome());

    }

    @Override
    public void bookTickets() {
        String[][] cinemaRoom = cinema.getCinemaRoom();
        String selectedRow;
        String selectedSeat;

        do {
            System.out.println("\nEnter a row number:");
            selectedRow = scanner.nextLine();
            System.out.println("Enter a seat number in that row:");
            selectedSeat = scanner.nextLine();
        }
        while (!validateTargetLocation(selectedRow, selectedSeat, cinemaRoom));

        int price = (cinema.getRows() * cinema.getSeats() < PRICE_CHANGE) ?
                PRICE_OF_PREMIUM_SEATS :
                cinema.getSelectedRow() > cinema.getRows() / 2 ?
                        PRICE_OF_ORDINARY_SEATS : 10;

        System.out.println("Ticket price: $" + price + "\n");
        cinemaRoom[cinema.getSelectedRow()][cinema.getSelectedSeat()] = "B";
        cinema.setCurrentIncome(cinema.getCurrentIncome() + price);
        cinema.increaseForOnePurchasedTickets();
    }


    private boolean validateTargetLocation(String selectedRow, String selectedSeat,
                                           String[][] cinemaRoom) {
        if (!validate(selectedRow) || !validate(selectedSeat)) {
            System.out.println("You input incorrect values (Input only integers)!!!");
            return false;
        }

        cinema.setSelectedRow(Integer.parseInt(selectedRow));
        cinema.setSelectedSeat(Integer.parseInt(selectedSeat));

        if (cinema.getSelectedRow() > cinema.getRows() ||
                cinema.getSelectedSeat() > cinema.getSeats()) {
            System.out.println("Wrong input!");
            return false;
        } else if ("B".equals(cinemaRoom[cinema.getSelectedRow()][cinema.getSelectedSeat()])) {
            System.out.println("That ticket has already been purchased!");
            return false;
        }


        return true;
    }

    @Override
    public void calculateTotalIncome() {
        int totalIncome;
        if (cinema.getRows() * cinema.getSeats() < PRICE_CHANGE) {
            totalIncome = cinema.getRows() * cinema.getSeats() * PRICE_OF_PREMIUM_SEATS;
        } else {
            totalIncome = cinema.getRows() * cinema.getSeats() * PRICE_OF_ORDINARY_SEATS
                    + cinema.getSeats() * (cinema.getRows());
        }
        cinema.setTotalIncome(totalIncome);
    }


}
