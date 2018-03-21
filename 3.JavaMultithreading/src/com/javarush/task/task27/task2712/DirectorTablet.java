package com.javarush.task.task27.task2712;

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
            System.out.println(dateFormat.format(entry.getKey()) + " - " + entry.getValue());
        }
        System.out.println("Total - " + totalAmount);
    }

    public void printCookWorkloading() {

    }

    public void printActiveVideoSet() {

    }

    public void printArchivedVideoSet() {

    }
}
