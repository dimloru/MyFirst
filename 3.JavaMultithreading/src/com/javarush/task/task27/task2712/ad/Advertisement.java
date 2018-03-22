package com.javarush.task.task27.task2712.ad;

public class Advertisement {
    private Object content;    //- видео
    private String name;       //- имя/название
    private long initialAmount; // - начальная сумма, стоимость рекламы в копейках. Используем long, чтобы избежать проблем с округлением
    int hits;           // - количество оплаченных показов
    int duration;      // - продолжительность в секундах

    private long amountPerOneDisplaying; //

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;

        if (hits > 0) {
            amountPerOneDisplaying = initialAmount / hits;
        } else amountPerOneDisplaying = -1;
    }

    public String getName() {
        return name;
    }

    public int getHits() {
        return hits;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public double getOneSecCost() {
        return amountPerOneDisplaying / (double) duration;
    }

    public void revalidate() {
        if (hits <= 0) throw new UnsupportedOperationException();
        hits--;
    }
}
