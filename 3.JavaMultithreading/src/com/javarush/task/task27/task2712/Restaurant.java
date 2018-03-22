package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.ArrayList;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;

    public static void main(String[] args) {
        Cook cookVasya = new Cook("Vasya");
        Cook cookAndrey = new Cook("Andrey");
        StatisticManager.getInstance().register(cookVasya);
        StatisticManager.getInstance().register(cookAndrey);

        Waiter waiter = new Waiter();
        cookVasya.addObserver(waiter);
        cookAndrey.addObserver(waiter);

        ArrayList<Tablet> tablets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Tablet tablet = new Tablet(i + 1);
            tablet.addObserver(cookVasya);
            tablet.addObserver(cookAndrey);
            tablets.add(tablet);
        }

        try {
            Thread randomOrderGenerator = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
            randomOrderGenerator.start();
            Thread.sleep(1000);
            randomOrderGenerator.interrupt();
        } catch (InterruptedException e) {

        }


        //stats block
        DirectorTablet directorTablet = new DirectorTablet();

        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();

    }
}
