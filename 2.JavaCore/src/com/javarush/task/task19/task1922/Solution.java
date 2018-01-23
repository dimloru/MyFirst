package com.javarush.task.task19.task1922;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* 
Ищем нужные строки
*/

public class Solution {
    public static List<String> words = new ArrayList<String>();

    static {
        words.add("файл");
        words.add("вид");
        words.add("В");
    }

    public static void main(String[] args) {
        String file = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            file = reader.readLine();
        } catch (IOException e) {}

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int counter = 0;
                for (String el : words) {
                    if (line.contains(el)) counter++;
//                    System.out.print(el + counter + " " );
                }
                if (counter == 2) System.out.println(line);
//                System.out.println();
            }

        } catch (IOException e) {}

//        FileReader fileReader = new FileReader(filename);
//        BufferedReader reader = new BufferedReader(fileReader);
//
//        List<String> fStrings = reader.lines().collect(Collectors.toList());   // тут лямбдики
//        fileReader.close();
//
//        fStrings.stream() // и тут лямбдики))) И Stream'ики)))
//                .filter(string -> words.stream()
//                        .filter(word -> Arrays.asList(string.split(" ")).contains(word))
//                        .count() == 2)
//                .forEachOrdered(System.out::println);

    }
}
