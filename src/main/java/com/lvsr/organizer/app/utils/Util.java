package com.lvsr.organizer.app.utils;

import java.util.Objects;

public class Util {

    public static Long notZeroOrNull(Long number) {

        if(Objects.isNull(number) || number.equals(0L)) {

            throw new NullPointerException();

        }

        return number;

    }

}
