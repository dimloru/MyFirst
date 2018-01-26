package com.javarush.task.task22.task2207;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/* 
Обращенные слова
*/
public class Solution {
    public static List<Pair> result = new LinkedList<>();

    public static void main(String[] args) {
        String file = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            file = reader.readLine();
        } catch (IOException e) {}

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append(" ");
            }
        } catch (IOException e) {}

        String[] words = sb.toString().split(" ");
        ArrayList<String> wordList = new ArrayList<>(Arrays.asList(words));
//        wordList = Arrays.asList(words);

        for (int i = 0; i < wordList.size(); i++) {
            if (i >= wordList.size()) break;
            StringBuilder sBuilder = new StringBuilder(wordList.get(i));
            String checking = sBuilder.reverse().toString();

            for (int j = i + 1; j < wordList.size(); j++) { //может быть ошибка выхода за пределы
                if (j >= wordList.size()) break;
                if (wordList.get(j).equals(checking)) {
                    result.add(new Pair(wordList.get(i), wordList.get(j)));
                    wordList.remove(j);
                    wordList.remove(i);
                    i--;
                }
            }
        }

//        result.forEach(pair -> System.out.println(pair));
    }

    public static class Pair {
        String first;
        String second;

        public Pair() {}

        public Pair(String first, String second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
            return second != null ? second.equals(pair.second) : pair.second == null;

        }

        @Override
        public int hashCode() {
            int result = first != null ? first.hashCode() : 0;
            result = 31 * result + (second != null ? second.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return  first == null && second == null ? "" :
                    first == null && second != null ? second :
                    second == null && first != null ? first :
                    first.compareTo(second) < 0 ? first + " " + second : second + " " + first;

        }
    }

}
