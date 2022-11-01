package com.indytskyi.validation;

import java.util.Objects;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class InputValidator {

    public static boolean validate(String input) {
            return Objects.nonNull(input) && input.matches("\\d+");
    }
}
