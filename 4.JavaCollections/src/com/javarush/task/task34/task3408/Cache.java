package com.javarush.task.task34.task3408;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

public class Cache<K, V> {
    private Map<K, V> cache = new WeakHashMap<>();


    public V getByKey(K key, Class<V> clazz) throws Exception {
        if (cache.containsKey(key)) {
            return cache.get(key);
        } else {
            V newValue = clazz.getConstructor(key.getClass()).newInstance(key); //.asSubclass(clazz)
            cache.put(key, newValue);
            return newValue;
        }
    }

    public boolean put(V obj) {
        try {
            Method method = obj.getClass().getDeclaredMethod("getKey", null);
            method.setAccessible(true);
            Object keyObj = method.invoke(obj, null);
            cache.put((K) keyObj, obj);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public int size() {
        return cache.size();
    }
}
