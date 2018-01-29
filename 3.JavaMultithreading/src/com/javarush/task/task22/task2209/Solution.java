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
        List<String> words = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(file))) {
            while (scanner.hasNext()) words.add(scanner.next());
        } catch (IOException e) {}
        String[] wordsArray = new String[words.size()];
        words.toArray(wordsArray);
        StringBuilder result = getLine(wordsArray);
        System.out.println(result.toString());
    }

    public static StringBuilder getLine(String... words) {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        if (words == null || words.length == 0) return sb;
        List<String> wordsList = new ArrayList<>(Arrays.asList(words));

//        System.out.println("1 " + words);
//        System.out.println("2 " + wordsList);
        //Searching for the first word (earliest letter)
        String firstWord = wordsList.get(0);
        char checkFirst = firstWord.toLowerCase().charAt(0);
        int firstIndex = 0;
        for (int i = 1; i < wordsList.size(); i++) {
            if (wordsList.get(i).charAt(0) < checkFirst) {
                checkFirst = wordsList.get(i).charAt(0);
                firstIndex = i;
                firstWord = wordsList.get(i);
            }
        }
        sb.append(firstWord);
        char checkChar = firstWord.toLowerCase().charAt(firstWord.length() - 1);
        wordsList.remove(firstIndex);
        boolean finish = false;
        while (wordsList.size() > 0 && !finish) {
            for (int i = 0; i < wordsList.size(); i++) {
                String w = wordsList.get(i);
//                System.out.print(w + " ");
                finish = true;
                if (w.toLowerCase().charAt(0) == checkChar) {
                    sb.append(" " + w);
                    checkChar = w.toLowerCase().charAt(w.length() - 1);
                    wordsList.remove(i);
                    finish = false;
//                    System.out.println();
                    break;
                }
            }
            // нашел слово - вышел из цикла или цикл закончился, пройдя все итерации
        }
        return sb;
    }
}
