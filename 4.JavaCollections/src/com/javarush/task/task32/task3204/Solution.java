package com.javarush.task.task32.task3204;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ThreadLocalRandom;

/* 
Генератор паролей
*/
public class Solution {
    public static void main(String[] args) {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() {
        // digits 48 - 57 incl
        // capital let 65 - 90
        // let 97 - 122
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(getRandomDigit());
        baos.write(getRandomLetter());
        baos.write(getRandomCapitalLetter());
        //the rest can be generated through cycle with calling random generating methods
        baos.write(getRandomDigit());
        baos.write(getRandomLetter());
        baos.write(getRandomCapitalLetter());
        baos.write(getRandomDigit());
        baos.write(getRandomLetter());

        return baos;
    }

    private static int getRandomDigit() {
        return ThreadLocalRandom.current().nextInt(48, 58);
    }

    private static int getRandomLetter() {
        return ThreadLocalRandom.current().nextInt(97, 123);
    }

    private static int getRandomCapitalLetter() {
        return ThreadLocalRandom.current().nextInt(65, 91);
    }
}