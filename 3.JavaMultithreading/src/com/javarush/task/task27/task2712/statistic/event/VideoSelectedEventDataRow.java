package com.javarush.task.task27.task2712.statistic.event;

import com.javarush.task.task27.task2712.ad.Advertisement;

import java.util.Date;
import java.util.List;

public class VideoSelectedEventDataRow implements EventDataRow {
    private Date currentDate;
    private List<Advertisement> optimalVideoSet;
    private long amount;
    private int totalDuration;

    public VideoSelectedEventDataRow(List<Advertisement> optimalVideoSet, long amount, int totalDuration) {
        this.optimalVideoSet = optimalVideoSet;
        this.amount = amount;
        this.totalDuration = totalDuration;

        this.currentDate = new Date();
    }

    @Override
    public EventType getType() {
        return EventType.SELECTED_VIDEOS;
    }

    @Override
    public Date getDate() {
        return currentDate;
    }


    //method to test
    public void setDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public int getTime() {
        return 0;
    }

    public long getAmount() {
        return amount;
    }
}
