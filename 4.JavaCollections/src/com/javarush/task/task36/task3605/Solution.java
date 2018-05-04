package com.javarush.task.task36.task3605;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Set<Character> result = new TreeSet<>();

        String line = null;

        while ((line = reader.readLine()) != null) {
            char[] chars = line.toLowerCase().toCharArray();
            if (chars.length != 0) {
                for (char ch : chars) {
                    if (Character.isLetter(ch)) {
                        result.add(ch);
                    }
                }
            }
        }

        reader.close();

        Iterator<Character> iterator = result.iterator();
        for (int i = 0; i < 5 && iterator.hasNext(); i++) {
            System.out.print(iterator.next());
        }
    }
}
