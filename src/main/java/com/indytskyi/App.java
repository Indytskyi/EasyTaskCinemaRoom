package com.indytskyi;

import com.indytskyi.entity.Cinema;
import com.indytskyi.introduction.CinemaIntroduction;
import com.indytskyi.service.impl.CinemaServiceImpl;
import com.indytskyi.validation.InputValidator;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Cinema cinema = new Cinema();
        CinemaServiceImpl cinemaService = new CinemaServiceImpl(cinema, System.in);
        CinemaIntroduction cinemaIntroduction = new CinemaIntroduction(cinema, cinemaService);
        cinemaIntroduction.menu();
    }
}
