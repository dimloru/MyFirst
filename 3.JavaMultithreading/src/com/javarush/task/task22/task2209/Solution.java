package com.javarush.task.task22.task2209;

import java.io.File;
import java.io.IOException;
import java.util.*;

/*
Составить цепочку слов
*/
public class Solution {
    public static void main(String[] args) {
        //...
        Scanner sc = new Scanner(System.in);
        String file = sc.next();
        sc.close();
        System.out.println(file);
        List<String> words = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(file))) {
            while (scanner.hasNext()) words.add(scanner.next());
        } catch (IOException e) {}
        String[] wordsArray = new String[words.size()];
        words.toArray(wordsArray);
        StringBuilder result = getLine(wordsArray);
//        System.out.println(result.toString());

    }

    public static StringBuilder getLine(String... words) {
        StringBuilder sb = new StringBuilder();
        List<Integer> used = new ArrayList<>();
        List<String> wordsList = new ArrayList<>(Arrays.asList(words));
        String word = wordsList.get(0);
        sb.append(word);
        char checkChar = word.toLowerCase().charAt(word.length() - 1);
        wordsList.remove(0);
        while (wordsList.size() > 0) {
            for (int i = 0; i < wordsList.size(); i++) {
                String w = wordsList.get(i);
                if (w.toLowerCase().charAt(0) == checkChar) {
                    sb.append(" " + w);
                    checkChar = w.toLowerCase().charAt(w.length() - 1);
                    wordsList.remove(i);
                    break;  // выходим из for
                }
                break;
            }
        }


          return null;
    }
}
