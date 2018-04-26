package com.javarush.task.task35.task3505;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertableUtil {

    public static <K,V extends Convertable<K>> Map<K,V> convert(List<V> list) {
        // no args check
        Map<K,V> result = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            K key = list.get(i).getKey();
            result.put(key, list.get(i));
        }

        return result;
    }
}
