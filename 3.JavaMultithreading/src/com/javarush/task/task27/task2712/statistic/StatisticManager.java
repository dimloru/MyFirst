package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.*;

import java.util.*;

public class StatisticManager {
    private static StatisticManager ourInstance = new StatisticManager();

    public static StatisticManager getInstance() {
        return ourInstance;
    }

    private StatisticManager() {
//        statisticStorage.put(new NoAvailableVideoEventDataRow(60));

    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    private StatisticStorage statisticStorage = new StatisticStorage();

    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage;

        private StatisticStorage() {
            storage = new HashMap<>();
            storage.put(EventType.COOKED_ORDER, new ArrayList<EventDataRow>());
            storage.put(EventType.NO_AVAILABLE_VIDEO, new ArrayList<EventDataRow>());
            storage.put(EventType.SELECTED_VIDEOS, new ArrayList<EventDataRow>());

//            // для проверки, хард-код data
//            initStorage();
        }

        private void put(EventDataRow data) {
            storage.get(data.getType()).add(data);
        }

        private Map<EventType, List<EventDataRow>> getStorage() {
            return storage;
        }
    }

    public Map<Date, Long> countDailyProfit () {
        //для проверки хард-код
//        initAdStorage();

        List<EventDataRow> adEvents = statisticStorage.getStorage().get(EventType.SELECTED_VIDEOS);

        Map<Date, Long> result = new TreeMap<>(Collections.reverseOrder());
        for (EventDataRow event : adEvents) {
            VideoSelectedEventDataRow videoEvent = (VideoSelectedEventDataRow) event;
            Date date = videoEvent.getDate();
            long amount = videoEvent.getAmount();

            Date justDate = new Date(date.getYear(), date.getMonth(), date.getDate());

            result.merge(justDate, amount, Long::sum);
        }
        return result;
    }

    public Map<Date, Map<String, Integer>> countCookWork() {
//        initCookStorage();
        Map<Date, Map<String, Integer>> result = new TreeMap<>(Collections.reverseOrder());
        List<EventDataRow> events = statisticStorage.getStorage().get(EventType.COOKED_ORDER);

        for (EventDataRow event : events) {
            CookedOrderEventDataRow cookEvent = (CookedOrderEventDataRow) event;
            Date date = cookEvent.getDate();
            String name = cookEvent.getCookName();
            int time = cookEvent.getTime();
            Date justDate = new Date(date.getYear(), date.getMonth(), date.getDate());

            if (result.keySet().contains(justDate)) {
                result.get(justDate)
                        .merge(name, time, Integer::sum);
            } else {
                Map<String, Integer> nameTimeMap = new TreeMap<>();
                nameTimeMap.put(name, time);
                result.put(justDate, nameTimeMap);
            }
        }

        return result;
    }

    // test method to hard-code some data
//    private void initAdStorage() {
//        statisticStorage.put(new VideoSelectedEventDataRow(null, 100, 60));
//        statisticStorage.put(new VideoSelectedEventDataRow(null, 300, 500));
//        VideoSelectedEventDataRow event;
//        event = new VideoSelectedEventDataRow(null, 15, 20);
//        event.setDate(new Date(113, 1, 1));
//        statisticStorage.put(event);
//        statisticStorage.put(event);
//        event = new VideoSelectedEventDataRow(null, 1000, 666);
//        event.setDate(new Date(101, 9, 7));
//        statisticStorage.put(event);
//        statisticStorage.put(event);
//    }
//
//        /////////
//    private void initCookStorage() {
//        CookedOrderEventDataRow cookedEvent = new CookedOrderEventDataRow("tab1","Petya", 600, null);
//        statisticStorage.put(cookedEvent);
//        cookedEvent = new CookedOrderEventDataRow("tab1","Petya", 480, null);
//        statisticStorage.put(cookedEvent);
//        cookedEvent = new CookedOrderEventDataRow("tab1","Darya", 1800, null);
//        cookedEvent.setDate(new Date(113, 1, 1));
//        statisticStorage.put(cookedEvent);
//        cookedEvent = new CookedOrderEventDataRow("tab1","Darya", 1200, null);
//        cookedEvent.setDate(new Date(113, 1, 1));
//        statisticStorage.put(cookedEvent);
//        cookedEvent = new CookedOrderEventDataRow("tab1","Darya", 900, null);
//        statisticStorage.put(cookedEvent);
//        cookedEvent = new CookedOrderEventDataRow("tab1","Petya", 600, null);
//        cookedEvent.setDate(new Date(113, 1, 1));
//        statisticStorage.put(cookedEvent);
//        cookedEvent = new CookedOrderEventDataRow("tab1","Vasiliy", 120, null);
//        statisticStorage.put(cookedEvent);
//        cookedEvent = new CookedOrderEventDataRow("tab1","Vasiliy", 181, null);
//        cookedEvent.setDate(new Date(108, 3, 20));
//        statisticStorage.put(cookedEvent);
//    }

    private Set<Cook> cooks = new HashSet<>();

    public void register(Cook cook) {
        cooks.add(cook);
    }
}
