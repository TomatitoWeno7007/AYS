package com.ays.ms.service.utils;

public class MathUtils {

    public static int randomNumber(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }


}
