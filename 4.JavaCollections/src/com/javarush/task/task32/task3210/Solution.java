package com.javarush.task.task32.task3210;

import java.io.IOException;
import java.io.RandomAccessFile;

/* 
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) {
        String fileName = args[0];
        int number = Integer.parseInt(args[1]);
        String text = args[2]; //no null check
        // if ... throw new

        try (RandomAccessFile raf = new RandomAccessFile(fileName,"rw")) {
            String readText = null;
            if ((number + text.length()) <= raf.length()) {
                byte[] readBytes = new byte[text.getBytes().length];
                raf.seek(number);
                raf.read(readBytes, 0, readBytes.length);
                readText = new String(readBytes);
            }

            raf.seek(raf.length());
            if (readText != null && readText.equals(text)) {
                raf.write("true".getBytes());
            } else {
                raf.write("false".getBytes());
            }
        } catch (IOException e) {}

    }
}
