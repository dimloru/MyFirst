package com.javarush.task.task27.task2712.ad;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager ourInstance = new StatisticAdvertisementManager();

    public static StatisticAdvertisementManager getInstance() {
        return ourInstance;
    }

    private StatisticAdvertisementManager() {
    }

    private AdvertisementStorage starage = AdvertisementStorage.getInstance();

    public Map<String, Integer> getActiveVideoSet() {
        Map<String, Integer> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (Advertisement ad : starage.list()) {
            if (ad.getHits() > 0) result.put(ad.getName(), ad.getHits());
        }
        return result;
    }

    public Map<String, Integer> getArchivedVideoSet() {
        Map<String, Integer> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (Advertisement ad : starage.list()) {
            if (ad.getHits() <= 0) result.put(ad.getName(), ad.getHits());
        }
        return result;
    }
}
