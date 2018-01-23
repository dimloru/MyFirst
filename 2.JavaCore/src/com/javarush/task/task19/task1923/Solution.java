package com.javarush.task.task19.task1923;

/* 
Слова с цифрами
*/

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
    public static void main(String[] args) {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(args[0]));
                BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]))
        ) {
            StringBuilder sb = new StringBuilder();
            while (reader.ready()) {
                String[] words = reader.readLine().split(" ");
                Pattern p = Pattern.compile("\\d");
                Matcher m = p.matcher("");
                for (String el : words) {
                    m.reset(el);
                    if (m.find()) sb.append(el + " ");
                }
            }
            writer.write(sb.toString().trim());
        } catch (IOException e) {}
    }
}
