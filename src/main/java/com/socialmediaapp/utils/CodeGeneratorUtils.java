package com.socialmediaapp.utils;

import java.util.UUID;

public class CodeGeneratorUtils {

    public static String generateRefreshToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


}
