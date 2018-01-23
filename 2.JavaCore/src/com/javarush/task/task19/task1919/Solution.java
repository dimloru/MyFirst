package com.javarush.task.task19.task1919;

/*
Считаем зарплаты
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution {
    private static Map<String, Double> map = new TreeMap<>();

    public static void main(String[] args) {
        String file = args[0];
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String str;
            while ((str = reader.readLine()) != null) {
                String[] line = str.split(" ");
                String name = line[0];
                Double wage = Double.valueOf(line[1]);
                if (map.containsKey(name)) {
                    Double prevWage = map.get(name);
                    map.put(name, prevWage + wage);
                } else {
                    map.put(name, wage);
                }
            }
        } catch (IOException e) {}

        map.forEach((k,v) -> System.out.println(k + " " + v));



        /*
        ArrayList<String> names = new ArrayList(map.keySet());
        Collections.sort(names);

        for (String el : names) {
            System.out.println(el + " " + map.get(el));
        }
        */

        //Отсортировать
        /*
        for (Map.Entry<String, Double> pair : map.entrySet()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }
        */
    }
}

