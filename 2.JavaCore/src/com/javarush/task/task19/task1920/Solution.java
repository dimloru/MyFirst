package com.javarush.task.task19.task1920;

/* 
Самый богатый
*/

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) {
        Map<String, Double> map = new TreeMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String line;
            reader.lines().forEach(s -> map.merge(s.substring(0, s.indexOf(" ")),
                    Double.parseDouble(s.substring(s.indexOf(" "))),
                    Double::sum ));
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


//          Еще один способ заполнения Map
//        map = br.lines()
//                .collect( Collectors.toMap(
//                        (s) -> s.substring(0, s.indexOf(32)),
//                        (s) -> Double.parseDouble( s.substring( s.indexOf(32)+1 )),
//                        (d1, d2) -> d1 + d2,
//                        TreeMap::new ) );

//        map.forEach((k, v) -> System.out.println(k + " " + v));

        List<String> best = new ArrayList<>();
        Double max = Double.MIN_VALUE;
        for (Map.Entry<String, Double> pair : map.entrySet()) {
            if (pair.getValue() > max) {
                max = pair.getValue();
                best = new ArrayList<>();
                best.add(pair.getKey());
            } else if (pair.getValue().equals(max)) {
                best.add(pair.getKey());
            }
        }

        best.forEach(v -> System.out.print(v + " "));
    }
}
