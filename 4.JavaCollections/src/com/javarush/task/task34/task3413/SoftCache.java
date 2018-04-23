package com.javarush.task.task34.task3413;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SoftCache {
    private Map<Long, SoftReference<AnyObject>> cacheMap = new ConcurrentHashMap<>();

    public AnyObject get(Long key) {
        AnyObject result = null;
        if (cacheMap.containsKey(key)) {
            SoftReference<AnyObject> softReference = cacheMap.get(key);
            if (softReference != null) {
                result = softReference.get();
                softReference.clear();
            }
        }
        return result;
    }

    public AnyObject put(Long key, AnyObject value) {
        SoftReference<AnyObject> softReference = new SoftReference<>(value);
        SoftReference<AnyObject> fromMap = cacheMap.putIfAbsent(key, softReference);
        AnyObject result = null;
        if (fromMap != null) {
            result = fromMap.get();
            fromMap.clear();
        }
        return result;
    }

    public AnyObject remove(Long key) {
        SoftReference<AnyObject> softReference = cacheMap.remove(key);
        AnyObject result = null;
        if (softReference != null) {
            result = softReference.get();
            softReference.clear();
        }

        return result;
    }
}