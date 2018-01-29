package com.javarush.task.task22.task2212;

/* 
Проверка номера телефона
*/
public class Solution {
    public static boolean checkTelNumber(String telNumber) {
        if (telNumber == null || telNumber.length() < 10) return false;
        if (telNumber.indexOf("(") > telNumber.indexOf(")") ||
                (!telNumber.contains("(") && telNumber.contains(")"))) return false;

        char[] tel = telNumber.toCharArray();
        int digtsCount = 0;
        for (char ch : tel) {
            if (Character.isDigit(ch)) digtsCount++;
        }

        if ((tel[0] == '+' && digtsCount == 12) || digtsCount == 10) {
            if (telNumber.matches("\\+?\\d*\\(?\\d{3}\\)?\\d*-?\\d+-?\\d+")) return true;
        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println(checkTelNumber("(0)501234567"));

    }
}
