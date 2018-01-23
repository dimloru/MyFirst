package com.javarush.task.task19.task1926;

/* 
Перевертыши
*/

import java.io.*;

public class Solution {
    public static void main(String[] args) {
        String file = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            file = reader.readLine();
        } catch (IOException e) {}
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                StringBuilder sb = new StringBuilder(line);
                System.out.println(sb.reverse().toString());
            }
        } catch (IOException e) {}
    }
}
