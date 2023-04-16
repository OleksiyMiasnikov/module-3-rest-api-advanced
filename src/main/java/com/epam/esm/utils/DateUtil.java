package com.epam.esm.utils;

import java.time.ZonedDateTime;

public class DateUtil {
    public static String getDate(){
        return ZonedDateTime.now().toLocalDateTime().toString();
    }
}
