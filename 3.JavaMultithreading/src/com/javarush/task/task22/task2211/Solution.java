package com.javarush.task.task22.task2211;

import java.io.*;

/* 
Смена кодировки
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        try (
                InputStream is = new FileInputStream(args[0]);
                OutputStream os = new FileOutputStream(args[1])
        ) {
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            String s = new String(buffer, "Windows-1251");
            buffer = s.getBytes("UTF-8");
            os.write(buffer);
        } catch (IOException e) {}
    }
}
