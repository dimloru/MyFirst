package com.javarush.task.task33.task3310;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Helper {
    public static String generateRandomString() {
//        SecureRandom random = new SecureRandom();
//        int length = 16; //bytes
//        byte[] values = new byte[length];
//        random.nextBytes(values);
//        return new BigInteger(1, values).toString(32);
        return new BigInteger(130, new SecureRandom()).toString(32);

    }

    public static void printMessage(String message) {
        System.out.println(message);
    }
}
