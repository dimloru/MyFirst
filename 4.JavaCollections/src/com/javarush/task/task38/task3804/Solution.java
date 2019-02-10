package com.javarush.task.task38.task3804;

/* 
Фабрика исключений
*/

import java.util.Arrays;

public class Solution {
    public static Class getFactoryClass() {
        return ExceptionFactory.class;
    }

    public static void main(String[] args) {

    }

    public static class ExceptionFactory {
        public static Throwable getException(Enum en) {
            if (en == null) {
                return new IllegalArgumentException();
            }

            StringBuilder sb = new StringBuilder(en.toString().toLowerCase());
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            String msg = sb.toString().replace("_", " ");

            if (en instanceof ApplicationExceptionMessage) {
                return new Exception(msg);
            } else if (en instanceof DatabaseExceptionMessage) {
                return new RuntimeException(msg);
            } else if (en instanceof UserExceptionMessage) {
                return new Error(msg);
            }

            return new IllegalArgumentException();
        }
    }
}