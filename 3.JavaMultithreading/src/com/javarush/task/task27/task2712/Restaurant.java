package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        Cook cookVasya = new Cook("Vasya");
        cookVasya.setQueue(orderQueue);
        Cook cookAndrey = new Cook("Andrey");
        cookAndrey.setQueue(orderQueue);

        Waiter waiter = new Waiter();
        cookVasya.addObserver(waiter);
        cookAndrey.addObserver(waiter);

        Thread cookAndreyThread = new Thread(cookAndrey);
        cookAndreyThread.start();

        Thread cookVasyaThread = new Thread(cookVasya);
        cookVasyaThread.start();

        ArrayList<Tablet> tablets = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Tablet tablet = new Tablet(i + 1);
            tablet.setQueue(orderQueue);
            tablets.add(tablet);
        }

        try {
            Thread randomOrderGenerator = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
            randomOrderGenerator.start();
            Thread.sleep(1000);
            randomOrderGenerator.interrupt();
            cookAndreyThread.interrupt();
            cookVasyaThread.interrupt();

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
