package com.javarush.task.task26.task2601;

import java.util.Arrays;

/*
Почитать в инете про медиану выборки
*/
public class Solution {

    public static void main(String[] args) {

    }

    public static Integer[] sort(Integer[] array) {
        //implement logic here
        Arrays.sort(array);
        double median;
        if (array.length % 2 != 0) median = array[array.length / 2];
        else median = (array[array.length / 2] + array[array.length / 2 - 1]) / 2;
        Arrays.sort(array, (a,b) -> (int) (Math.abs(a - median) - Math.abs(b - median)));
        return array;
    }
}
