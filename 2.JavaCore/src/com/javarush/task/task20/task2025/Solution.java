package com.javarush.task.task20.task2025;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
Алгоритмы-числа
*/
public class Solution {
    public static List<Long> getNumbers(long N) {
//        public static long[] getNumbers(long N) {
        List<Long> resultList = new ArrayList<>();

        for (long j = 1; j < N; j++) {
//            System.out.println(j);
            List<Integer> numbers = new ArrayList();
            long currentNumber = j;
            if (currentNumber >= 10) {
                do {
                    numbers.add((int) currentNumber % 10);
                    currentNumber = currentNumber / 10;
//                    System.out.print(currentNumber % 10 + " ");
                    //currentNumber = currentNumber / 10;
    //                System.out.println(currentNumber);
                } while (currentNumber / 10 > 0);
                numbers.add((int)currentNumber);
            } else {
                numbers.add((int)currentNumber);
            }

//            System.out.println(numbers);
            int m = numbers.size();
//            System.out.println("m = " + m);
            long tempResult = 0;
            for (int i = 0; i < m; i++) {
                tempResult += Math.pow(numbers.get(i), m);
                if (tempResult > j) break;
            }
//            System.out.println(j + " - " + tempResult);
//            System.out.println("*********************************************");
            if (tempResult == j) resultList.add(j);
        }


//        long[] result = null;
        return resultList;
    }

    public static void main(String[] args) {
//        Date date = new Date();
//        System.out.println(date.getTime());
//        System.out.println(getNumbers(100000000));
//        date = new Date();
//        System.out.println(date.getTime());
        System.out.println(Long.MAX_VALUE);


    }
}
