package com.indytskyi.validation;

public class ValidateInputInteger {

    public boolean validate(String input) {
        try {
            int rows = Integer.parseInt(input);
            return true;
        } catch (Exception e) {

            return false;
        }
    }
}
