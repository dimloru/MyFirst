package com.javarush.task.task19.task1918;

/* 
Знакомство с тегами
*/

//import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static List<String> lines = new ArrayList<>();

    public static void main(String[] args) {
        String file = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            file = reader.readLine();
        } catch (IOException e) {
        }

        String openTag = "<" + args[0];
        String closeTag = "</" + args[0] + ">";
//        System.out.println(openTag + " " + closeTag);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while (reader.ready()) {
                sb.append((reader.readLine()));
//                lines.add(reader.readLine());
            }
        } catch (IOException e) {
        }
//        System.out.println(sb.toString());
        String input = sb.toString();

        int start = 0;

        while (true) {
            int begin = input.indexOf(openTag, start);
            int end = input.indexOf(closeTag, begin + 3);
//            System.out.println(begin + " - " + end);
            if (begin == -1 || end == -1) break;


            // Apache Commons - String Utils
//            while (StringUtils.countMatches(input.substring(begin, end + 2 + args[0].length() + 1), openTag) >
//                    StringUtils.countMatches(input.substring(begin, end + 2 + args[0].length() + 1), closeTag)) {
//                end = input.indexOf(closeTag, end + 1);
//            }
            //написать замену countMatches
            while (countMatches(input.substring(begin, end + 2 + args[0].length() + 1), openTag) >
                    countMatches(input.substring(begin, end + 2 + args[0].length() + 1), closeTag)) {
                end = input.indexOf(closeTag, end + 1);
            }

            System.out.println(input.substring(begin, end + 2 + args[0].length() + 1));
            start = begin + 1;
//            System.out.println(start);
        }
    }

    public static int countMatches (String where, String what) {
        int result = 0;
        int beginIndex = 0;
        int tmp;
        while ((tmp = where.indexOf(what, beginIndex)) != -1) {
            result++;
            beginIndex = tmp + what.length();
        }
        return result;
    }
}
