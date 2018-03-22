package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.List;

public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes = null;

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        initDishes();
    }

    protected void initDishes() throws IOException {
        dishes = ConsoleHelper.getAllDishesForOrder();
    }

    public int getTotalCookingTime() {
        int totalCookingTime = 0;
        for (Dish dish : dishes) totalCookingTime += dish.getDuration();
        return totalCookingTime;
    }

    public Tablet getTablet() {
        return tablet;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    @Override
    public String toString() {
        if (dishes == null || dishes.isEmpty()) return "";

        StringBuilder sb = new StringBuilder("Your order: [");
        for (Dish dish : dishes) {
            sb.append(dish + ", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("] of ");
        sb.append(tablet.toString());

        return sb.toString();
    }

    public boolean isEmpty() {
        return (dishes == null || dishes.isEmpty());
    }
}
