package com.indytskyi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Cinema {

    public static final int MAX_SIZE_OF_COUNT_ROWS = 15;
    public static final int MAX_SIZE_OF_SEATS_IN_ROW = 40;

    /**
     * The number of seats after which the price for the first half drops to 8 $
     */
    private final int priceChange = 60;

    /**
     * price for all seats in cinema room which have less than 60 seats
     * and if the room have more than 60 seats,
     * the first part of seat will cost 10$
     */
    private final int priceForThePremiumSeats = 10;
    /**
     * price for all seats in cinema room which have less than 60 seats
     * and if the room have more than 60 seats,
     * the second part of seat will cost 8$
     */
    private final int priceForTheOrdinarySeats = 8;
    private int rows;
    private int seats;
    private int selectedRow;
    private int selectedSeat;
    private int totalIncome;
    private int currentIncome;
    private int numberOgPurchasedTickets;
    private String[][] cinemaRoom;


    public void increaseForOnePurchasedTickets() {
        this.numberOgPurchasedTickets++;
    }
}
