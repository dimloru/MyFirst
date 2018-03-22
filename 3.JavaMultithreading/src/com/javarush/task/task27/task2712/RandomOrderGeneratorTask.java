package com.javarush.task.task27.task2712;

import java.util.ArrayList;
import java.util.List;

public class RandomOrderGeneratorTask implements Runnable {
    private List<Tablet> tablets;
    private int orderCreatingInterval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.tablets = tablets;
        this.orderCreatingInterval = interval;
    }

    @Override
    public void run() {
        int randomTabNumber = (int)(Math.random() * tablets.size());
        if (randomTabNumber >= tablets.size()) randomTabNumber = tablets.size();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                tablets.get(randomTabNumber).createTestOrder();
                Thread.sleep(orderCreatingInterval);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}
