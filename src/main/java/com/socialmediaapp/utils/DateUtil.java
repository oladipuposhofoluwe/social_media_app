package com.socialmediaapp.utils;


import java.time.Instant;
import java.util.Date;
public class DateUtil {

    public static Date now() {
        return Date.from(Instant.now());
    }

}
