package com.javarush.task.task22.task2212;

/* 
Проверка номера телефона
*/
public class Solution {
    public static boolean checkTelNumber(String telNumber) {
        if (telNumber == null || telNumber.length() < 10) return false;
        if (telNumber.indexOf("(") > telNumber.indexOf(")")) return false;
        if (telNumber.matches("\\+?\\d*\\(?\\d{3}\\)?\\d*")) return true;
        return false;
    }

    public static void main(String[] args) {
        System.out.println(checkTelNumber("+380501234567"));

    }
}
