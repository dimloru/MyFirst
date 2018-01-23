package com.javarush.task.task19.task1921;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* 
Хуан Хуанович
*/

public class Solution {
    public static final List<Person> PEOPLE = new ArrayList<Person>();

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(" ");
                String dateStr = split[split.length - 3] + " " + split[split.length - 2] + " " + split[split.length - 1];
                SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy");

                Date date = new Date();
                try {
                    date = format.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < split.length - 3; i++) {
                    sb.append(split[i] + " ");
                }

                PEOPLE.add(new Person(sb.toString().trim(), date));
            }
        } catch (IOException e) {}

//        PEOPLE.forEach(person -> System.out.println(person.getName() + " " + person.getBirthday()));

    }
}
