package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount; //if repeatCount < 1 throw or sth
        map = new HashMap<>();
    }

    @Override
    public int size() {
        int size = 0;
        if (map.keySet().size() > 0) {
            for (K key : map.keySet()) {
                List<V> values = map.get(key);
                if (values != null) {
                    size += values.size();
                }
            }
        }
        return size;

    }

    @Override
    public V put(K key, V value) {
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<>(Collections.singletonList(value))); // NO -- can be replaced with an ArrayBlockingQueue / poll / offer
            return null;
        } else {
            List<V> values = map.get(key);
            //supposing repeatCount >= 1
            if (values == null || values.size() < 1) {
                map.put(key, new ArrayList<>(Collections.singletonList(value)));
                return null; // code repetition
            } else {
                V toReturn = values.get(values.size() - 1); // need to use DEqueue because of this
                if (values.size() < repeatCount) {
                    values.add(value);
                } else {
                    values.remove(0);
                    values.add(value);
                }
                return toReturn;
            }
        }
    }

    @Override
    public V remove(Object key) {
        if (!map.containsKey(key)) return null;
        List<V> values = map.get(key);
        V toReturn;
        if (values == null) {
            map.remove(key);
            return null;
        } else {
            toReturn = values.remove(0);
        }
        if (values.size() == 0) {
            map.remove(key);
        }
        return toReturn;
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        //напишите тут ваш код
        List<V> result = new ArrayList<>();
        if (map.keySet().size() > 0) {
            for (K key : map.keySet()) {
                List<V> values = map.get(key);
                if (values != null) {
                    for (V value : values) result.add(value);
                }
            }
        }
        return result;
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}