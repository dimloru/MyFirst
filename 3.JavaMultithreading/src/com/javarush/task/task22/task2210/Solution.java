package com.javarush.task.task22.task2210;

import java.util.StringTokenizer;

/*
StringTokenizer
*/
public class Solution {
    public static void main(String[] args) {
        for (String el : getTokens("Some !test. string", "!.")) {
            System.out.println(el);
        }

//        for (String el : "abc.def.gh".split("\\.")) {
//            System.out.println(el);
//        }

    }
    public static String [] getTokens(String query, String delimiter) {
//        delimiter = "\\" + delimiter;
        StringTokenizer tokenizer = new StringTokenizer(query, delimiter);
        String[] result = new String[tokenizer.countTokens()];
        for (int i = 0; i < result.length; i++) {
            result[i] = tokenizer.nextToken();
//            System.out.println(result[i]);
        }
//        System.out.println("--------------------");
        return result;

//        System.out.println(delimiter);
//        return query.split(delimiter);
    }
}
