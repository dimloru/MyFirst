package com.javarush.task.task22.task2202;

/* 
Найти подстроку
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getPartOfString("JavaRush - лучший сервис обучения Java."));
        System.out.println(getPartOfString("Амиго и Диего лучшие друзья!"));
        System.out.println(getPartOfString("Амиго и Диего"));

    }

    public static String getPartOfString(String string) {
        try {
            int beginIndex = string.indexOf(" ");
            int endIndex = beginIndex;
            for (int i = 0; i < 3; i++) {
                endIndex = string.indexOf(" ", endIndex + 1);
            }
            int lastSpace;
            if ((lastSpace = string.indexOf(" ", endIndex + 1)) != -1) {
                return string.substring(beginIndex, lastSpace);
            } else {
                return string.substring(beginIndex);
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw new TooShortStringException();
        }
    }

    public static class TooShortStringException extends RuntimeException {
    }
}
