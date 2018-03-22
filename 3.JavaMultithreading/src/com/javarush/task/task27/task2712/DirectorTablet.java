package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class DirectorTablet {
    public void printAdvertisementProfit() {
        StatisticManager statisticManager = StatisticManager.getInstance();
        Map<Date, Long> profitMap= statisticManager.countDailyProfit();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        long totalAmount = 0;
        for (Map.Entry<Date, Long> entry : profitMap.entrySet()) {
            totalAmount += entry.getValue();
            ConsoleHelper.writeMessage(dateFormat.format(entry.getKey()) + " - " +
                    String.format("%.2f", (double)entry.getValue() / 100));
        }
        ConsoleHelper.writeMessage("Total - " + String.format("%.2f", (double)totalAmount / 100));
        ConsoleHelper.writeMessage("");
    }

    public void printCookWorkloading() {
        StatisticManager statisticManager = StatisticManager.getInstance();
        Map<Date, Map<String, Integer>> result = statisticManager.countCookWork();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        for (Date date : result.keySet()) {
            ConsoleHelper.writeMessage(dateFormat.format(date));
            for (Map.Entry<String, Integer> cookTime : result.get(date).entrySet()) {
                ConsoleHelper.writeMessage(cookTime.getKey() + " - " + (int)Math.ceil((double)cookTime.getValue() / 60) + " min");
            }
            ConsoleHelper.writeMessage("");
        }
        ConsoleHelper.writeMessage("");
    }

    public void printActiveVideoSet() {
        Map<String, Integer> result = StatisticAdvertisementManager.getInstance().getActiveVideoSet();
        result.forEach((k, v) -> ConsoleHelper.writeMessage(k + " - " + v));
    }

    public void printArchivedVideoSet() {
        Map<String, Integer> result = StatisticAdvertisementManager.getInstance().getArchivedVideoSet();
        result.keySet().forEach(ConsoleHelper::writeMessage);
    }
}
