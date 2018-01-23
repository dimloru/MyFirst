package com.javarush.task.task19.task1925;

/* 
Длинные слова
*/

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(args[0]));
                BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]))
        ) {
            String[] words;
            StringBuilder sb = new StringBuilder();
            while (reader.ready()) {
                String line = reader.readLine();
                words = line.split(" ");
                for (String el : words) {
                    if (el.length() > 6) sb.append(el + ",");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            writer.write(sb.toString());
        } catch (IOException e) {}

    }
}
