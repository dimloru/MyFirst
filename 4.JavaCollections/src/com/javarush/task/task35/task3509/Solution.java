package com.javarush.task.task35.task3509;

import java.util.*;


/* 
Collections & Generics
*/
public class Solution {

    public static void main(String[] args) {
    }

    public static <T> ArrayList<T> newArrayList(T... elements) {

        return new ArrayList<T>(Arrays.asList(elements));
    }

    public static <T> HashSet<T> newHashSet(T... elements) {

        return new HashSet<>(Arrays.asList(elements));
    }

    public static <K,V> HashMap<K,V> newHashMap(List<? extends K> keys, List<? extends V> values) {
        //args check
        if (keys == null || values == null || keys.size() != values.size() || keys.size() == 0) {
            throw new IllegalArgumentException();
        }

        HashMap<K,V> result = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            result.put(keys.get(i), values.get(i));
        }

        return result;
    }
}
