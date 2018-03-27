package com.javarush.task.task30.task3009;

import java.util.HashSet;
import java.util.Set;

/* 
Палиндром?
*/

public class Solution {
    public static void main(String[] args) {
        System.out.println(getRadix("112"));        //expected output: [3, 27, 13, 15]
        System.out.println(getRadix("123"));        //expected output: [6]
        System.out.println(getRadix("5321"));       //expected output: []
        System.out.println(getRadix("1A"));         //expected output: []
    }

    private static Set<Integer> getRadix(String number) {
        Set<Integer> result = new HashSet<>();
        int decimal;

        try {
            decimal = Integer.parseInt(number, 10);
        } catch (NumberFormatException e) {
            return result;
        }

        for (int i = 2; i < 37; i++) {
            String stringNumber = Integer.toString(decimal, i);

            char[] chars = stringNumber.toCharArray();
            boolean palindrom = true;
            for (int j = 0; j < chars.length / 2; j++) {
                if (chars[j] != chars[chars.length - 1 - j]) {
                    palindrom = false;
                    break;
                }
            }
            if(palindrom) result.add(i);
        }
        return result;
    }
}