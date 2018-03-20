package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    int timeSeconds; // cooking time in seconds

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() {
//        List<Advertisement> toShow = new ArrayList<>();
//        toShow.add(storage.list().get(0));

        if (storage.list().isEmpty()) {
            throw new NoVideoAvailableException();
        }

//        Collections.sort(toShow, (p1, p2) -> {
//            long amountPerOneDisplayingP1 = p1.getAmountPerOneDisplaying();
//            long amountPerOneDisplayingP2 = p2.getAmountPerOneDisplaying();
//
//            int perDisplayingResult =  Long.compare(amountPerOneDisplayingP2, amountPerOneDisplayingP1);
//            if (perDisplayingResult != 0) return perDisplayingResult;
//
////            double secCostP1 = amountPerOneDisplayingP1 / (double) p1.getDuration(); //secCost - to Advert
////            double secCostP2 = amountPerOneDisplayingP2 / (double) p2.getDuration();
//
////            return Double.compare(secCostP1, secCostP2);
//            return Double.compare(p1.getOneSecCost(), p2.getOneSecCost());
//        });

//        for (Advertisement ad : toShow) {
//          ConsoleHelper
//            System.out.println(ad.getName() + " is displaying... " + ad.getAmountPerOneDisplaying() + ", " +
//                    + (int)(ad.getOneSecCost() * 1000));   //secCost - to Advert
//
//        }



    }
}
