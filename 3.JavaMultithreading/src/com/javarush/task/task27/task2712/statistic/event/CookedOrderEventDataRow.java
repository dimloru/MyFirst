package com.javarush.task.task27.task2712.statistic.event;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.util.Date;
import java.util.List;

public class CookedOrderEventDataRow implements EventDataRow {
    private Date currentDate;
    private String tabletName;
    private String cookName;
    private int cookingTimeSeconds;
    private List<Dish> cookingDishs;

    public CookedOrderEventDataRow(String tabletName, String cookName, int cookingTimeSeconds, List<Dish> cookingDishs) {
        this.tabletName = tabletName;
        this.cookName = cookName;
        this.cookingTimeSeconds = cookingTimeSeconds;
        this.cookingDishs = cookingDishs;

        this.currentDate = new Date();
    }

    @Override
    public EventType getType() {
        return EventType.COOKED_ORDER;
    }
}
