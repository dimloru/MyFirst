package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.ArrayList;

public class TestOrder extends Order {

    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() throws IOException {
        Dish[] allDishes = Dish.values();
        dishes = new ArrayList<>();
        for (Dish dish : allDishes) {
            if (Math.random() > 0.5) dishes.add(dish);
        }
    }
}
