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

            // для проверки, хард-код data
            initStorage();
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



    // test method to hard-code some data
    private void initStorage() {
        statisticStorage.put(new VideoSelectedEventDataRow(null, 100, 60));
        statisticStorage.put(new VideoSelectedEventDataRow(null, 300, 500));
        VideoSelectedEventDataRow event;
        event = new VideoSelectedEventDataRow (null, 15, 20);
        event.setDate(new Date(113, 1,1));
        statisticStorage.put(event);
        statisticStorage.put(event);
        event = new VideoSelectedEventDataRow (null, 1000, 666);
        event.setDate(new Date(101, 9,7));
        statisticStorage.put(event);
        statisticStorage.put(event);

        /////////

        CookedOrderEventDataRow cookedEvent = new CookedOrderEventDataRow("tab1","Petya", 600, null);
        statisticStorage.put(cookedEvent);
        cookedEvent = new CookedOrderEventDataRow("tab1","Petya", 480, null);
        statisticStorage.put(cookedEvent);
        cookedEvent = new CookedOrderEventDataRow("tab1","Darya", 1800, null);
        statisticStorage.put(cookedEvent);
        cookedEvent = new CookedOrderEventDataRow("tab1","Darya", 1200, null);
        statisticStorage.put(cookedEvent);
        cookedEvent = new CookedOrderEventDataRow("tab1","Darya", 900, null);
        statisticStorage.put(cookedEvent);
        cookedEvent = new CookedOrderEventDataRow("tab1","Petya", 600, null);
        statisticStorage.put(cookedEvent);
        cookedEvent = new CookedOrderEventDataRow("tab1","Vasiliy", 120, null);
        statisticStorage.put(cookedEvent);
        cookedEvent = new CookedOrderEventDataRow("tab1","Vasiliy", 240, null);
        statisticStorage.put(cookedEvent);


    }

    private Set<Cook> cooks = new HashSet<>();

    public void register(Cook cook) {
        cooks.add(cook);
    }
}
