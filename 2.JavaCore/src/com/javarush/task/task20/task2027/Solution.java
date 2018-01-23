package com.javarush.task.task20.task2027;

import java.util.ArrayList;
import java.util.List;

/*
Кроссворд
*/
public class Solution {
    public static void main(String[] args) {
        int[][] crossword = new int[][]{
                {'f', 'd', 'e', 'r', 'l', 'k'},
                {'u', 's', 'a', 'm', 'e', 'o'},
                {'l', 'n', 'g', 'r', 'o', 'v'},
                {'m', 'l', 'p', 'r', 'r', 'h'},
                {'p', 'o', 'e', 'e', 'j', 'j'}
        };
        detectAllWords(crossword, "home", "same", "jee", "rrad", "roo");
        System.out.println(result);
        /*
Ожидаемый результат
home - (5, 3) - (2, 0)
same - (1, 1) - (4, 1)
         */
    }

    public static List<Word> result;
    public static boolean wordFound;

    public static void searchVector (int[][]crossword, char[] wordChars, String word, int firstX, int firstY, int xShift, int yShift) {
        for (int k = 1; k < word.length(); k++) {
            if ((firstX + word.length() * xShift > crossword[firstY].length) ||
                    (firstX + 1 + word.length() * xShift) < 0) {
//                System.out.println(xShift + " " + yShift + " X break");
                break;
            }

            if ((firstY + word.length() * yShift > crossword.length) ||
                    (firstY + 1 + word.length() * yShift < 0)) {
//                System.out.println(xShift + " " + yShift + " Y break");
                break;
            }

//            System.out.println("Letter being checked: " +  (firstX + k * xShift) + " " + (firstY + k * yShift));
            if (!(crossword[firstY + k * yShift][firstX + k * xShift] == wordChars[k])) {
//                System.out.println(" - wrong");
                break;
            }


            if (k == (word.length() - 1) && crossword[firstY + k * yShift][firstX + k * xShift] == wordChars[k]) {
                System.out.println(word + ": " + firstX + " " + firstY +
                        " / " + (firstX + k * xShift) + " " + (firstY + k * yShift));

                Word found = new Word(word);
                found.setStartPoint(firstX, firstY);
                found.setEndPoint(firstX + k * xShift, firstY + k * yShift);
                result.add(found);
                wordFound = true;
            }
        }
    }

    public static List<Word> detectAllWords(int[][] crossword, String... words) {
        result = new ArrayList<>();
        for (String word : words) {
            char[] wordChars = word.toCharArray();
            for (int j = 0; j < crossword.length; j++) {
                for (int i = 0; i < crossword[j].length; i++) {
                    if (crossword[j][i] == wordChars[0]){
                        wordFound = false;
                        for (int m = -1; m <= 1; m++) {
                            for (int n = -1; n <= 1; n++) {
                                searchVector(crossword, wordChars, word, i, j, m, n);
                                if (wordFound) break;
                            }
                            if (wordFound) break;
                        }
                    }
                }
            }
        }
        return result;
    }

    public static class Word {
        private String text;
        private int startX;
        private int startY;
        private int endX;
        private int endY;

        public Word(String text) {
            this.text = text;
        }

        public void setStartPoint(int i, int j) {
            startX = i;
            startY = j;
        }

        public void setEndPoint(int i, int j) {
            endX = i;
            endY = j;
        }

        @Override
        public String toString() {
            return String.format("%s - (%d, %d) - (%d, %d)", text, startX, startY, endX, endY);
        }
    }
}

