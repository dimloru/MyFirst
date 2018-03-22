package com.javarush.task.task27.task2712.ad;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager ourInstance = new StatisticAdvertisementManager();

    public static StatisticAdvertisementManager getInstance() {
        return ourInstance;
    }

    private StatisticAdvertisementManager() {
    }

    private AdvertisementStorage storage = AdvertisementStorage.getInstance();

    public Map<String, Integer> getActiveVideoSet() {
        Map<String, Integer> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (Advertisement ad : storage.list()) {
            if (ad.getHits() > 0) result.put(ad.getName(), ad.getHits());
        }

        //stream version
//        result = storage.list().stream()
//                .filter(ad -> ad.getHits() > 0)
//                .sorted(Comparator.comparing(Advertisement::getName))
//                .collect(Collectors.toMap(Advertisement::getName, Advertisement::getHits));

        return result;
    }

    public Map<String, Integer> getArchivedVideoSet() {
        Map<String, Integer> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (Advertisement ad : storage.list()) {
            if (ad.getHits() <= 0) result.put(ad.getName(), ad.getHits());
        }
        return result;
    }
}
