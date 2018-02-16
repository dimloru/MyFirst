package com.javarush.task.task26.task2603;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

/*
Убежденному убеждать других не трудно
*/
public class Solution {

    public static void main(String[] args) {

    }

    public static class CustomizedComparator<T> implements Comparator<T> {
        private Comparator<T>[] comparators;

        public CustomizedComparator (Comparator<T>... comparators) {
            this.comparators = comparators;
        }

        public int compare(T o1, T o2) {
            int result = 0;
//            for (Comparator<T> comparator : comparators) {
//                result = comparator.compare(o1, o2);
//                if (result != 0) break;
//            }
//            result = Arrays.stream(comparators)
//                    .reduce((t1, t2) -> 0, Comparator::thenComparing)
//                    .compare(o1, o2);

            result = Arrays.stream(comparators)
                    .flatMap(comparator -> Stream.of(comparator.compare(o1,o2)))
                    .filter( num -> num != 0)
                    .findFirst()
                    .orElse(0);

            return result;

        }
    }
}
