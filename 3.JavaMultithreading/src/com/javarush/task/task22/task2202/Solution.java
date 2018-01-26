package com.javarush.task.task22.task2202;

/* 
Найти подстроку
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getPartOfString("JavaRush - лучший сервис обучения Java."));
        System.out.println(getPartOfString("Амиго и Диего лучшие друзья!"));
//        System.out.println(getPartOfString("Амиго и Диего"));
        System.out.println(getPartOfString("F"));


    }

    public static String getPartOfString(String string) {
        if (string == null) throw new TooShortStringException();

        int beginIndex = string.indexOf(" ");
        int endIndex = beginIndex;
        for (int i = 0; i < 3; i++) {
            endIndex = string.indexOf(" ", endIndex + 1);
            if (endIndex == -1) throw new TooShortStringException();
        }
        int lastSpace;
        if ((lastSpace = string.indexOf(" ", endIndex + 1)) != -1) {
            return string.substring(beginIndex + 1, lastSpace);
        } else {
            return string.substring(beginIndex + 1);
        }
    }

    public static class TooShortStringException extends RuntimeException {
    }
}
