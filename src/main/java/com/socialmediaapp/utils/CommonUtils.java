package com.socialmediaapp.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;


@Slf4j
public class CommonUtils {

    public static <T> T castToNonNull(@Nullable T x) {
        if (x == null) {
           log.warn(x+"is null") ;
        }
        return x;
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}



