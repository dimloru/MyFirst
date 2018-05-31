package com.javarush.task.task37.task3708.retrievers;

import com.javarush.task.task37.task3708.cache.LRUCache;
import com.javarush.task.task37.task3708.storage.Storage;

public class CachingProxyRetriever implements Retriever {
    OriginalRetriever originalRetriever;
    LRUCache lruCache;

    public CachingProxyRetriever(Storage storage) {
        originalRetriever = new OriginalRetriever(storage);
        lruCache = new LRUCache(1024);
    }

    @Override
    public Object retrieve(long id) {
        Object result = null;
        if ((result = lruCache.find(id)) != null)
            return result;
        else {
            result = originalRetriever.retrieve(id);
            lruCache.set(id, result);
            return result;
        }
    }
}
