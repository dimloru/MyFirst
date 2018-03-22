package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;

    public static void main(String[] args) {
        Tablet tablet = new Tablet(5);
        Cook cookVasya = new Cook("Vasya");
        tablet.addObserver(cookVasya);
        Waiter waiter = new Waiter();
        cookVasya.addObserver(waiter);

        Order order = tablet.createOrder();

        //stats block
        DirectorTablet directorTablet = new DirectorTablet();

        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();

    }
}
