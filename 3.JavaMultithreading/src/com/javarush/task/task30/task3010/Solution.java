package com.javarush.task.task30.task3010;

/* 
Минимальное допустимое основание системы счисления
*/


import java.math.BigInteger;

public class Solution {
    public static void main(String[] args) {
        //напишите тут ваш код
        try {
            int minRadix = 37;
            for (int i = 36; i >= 2 ; i--) {
                try {
                    BigInteger bigInteger = new BigInteger(args[0], i);
                    minRadix = i;
                } catch (NumberFormatException e) {}
            }

            if (minRadix < 37) System.out.println(minRadix);
            else System.out.println("incorrect");
        } catch (Exception e) {
        }


    }
}