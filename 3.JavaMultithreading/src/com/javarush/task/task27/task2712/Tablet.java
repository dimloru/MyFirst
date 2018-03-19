package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Order;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet {
    private final int number;
//    private Order order;//??
    private static Logger logger = Logger.getLogger(Tablet.class.getName());

    public Tablet (int number) {
        this.number = number;
    }

    public void createOrder() {
        try {
            new Order(this);
//            this.order = new Order(this);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }

    }

//    public int getNumber() {
//        return number;
//    }

    @Override
    public String toString() {
        return "Tablet{number=" + number + "}"; //"Tablet number=" + number; //??? order??
    }
}
