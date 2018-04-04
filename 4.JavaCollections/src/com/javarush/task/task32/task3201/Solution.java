package com.javarush.task.task32.task3201;

import java.io.IOException;
import java.io.RandomAccessFile;

/*
Запись в существующий файл
*/
public class Solution {
    public static void main(String... args) {
        try (RandomAccessFile raf = new RandomAccessFile(args[0], "rw")) {
            long number = Long.parseLong(args[1]);
            if (number > raf.length()) {
                number = raf.length();
            }
            raf.seek(number);
            raf.write(args[2].getBytes());
        } catch (IOException e) {}

    }
}
