package com.indytskyi;

import com.indytskyi.entity.Cinema;
import com.indytskyi.introduction.CinemaIntroduction;
import com.indytskyi.service.impl.CinemaServiceImpl;
import com.indytskyi.validation.ValidateInputInteger;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Cinema cinema = new Cinema();
        ValidateInputInteger validateInputInteger = new ValidateInputInteger();
        CinemaServiceImpl cinemaService = new CinemaServiceImpl(cinema, System.in, validateInputInteger);
        CinemaIntroduction cinemaIntroduction = new CinemaIntroduction(cinema, cinemaService, validateInputInteger);
        cinemaIntroduction.menu();
    }
}
