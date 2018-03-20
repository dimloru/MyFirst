package com.javarush.task.task27.task2712.ad;



import com.javarush.task.task27.task2712.ConsoleHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    int timeSeconds; // cooking time in seconds
//    private List<List<Advertisement>> adVariants = new ArrayList<>();
    private List<Advertisement> toShow = new ArrayList<>();

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() {
        if (storage.list().isEmpty()) {
            throw new NoVideoAvailableException();
        }

        searchAdVariants(0, null, 0); //можно потом перенести и сделать return

        if (toShow.isEmpty()) {
            throw new NoVideoAvailableException();
        }

        Collections.sort(toShow, (p1, p2) -> {
            long amountPerOneDisplayingP1 = p1.getAmountPerOneDisplaying();
            long amountPerOneDisplayingP2 = p2.getAmountPerOneDisplaying();

            int perDisplayingResult =  Long.compare(amountPerOneDisplayingP2, amountPerOneDisplayingP1);
            if (perDisplayingResult != 0) return perDisplayingResult;

//            double secCostP1 = amountPerOneDisplayingP1 / (double) p1.getDuration(); //secCost - to Advert
//            double secCostP2 = amountPerOneDisplayingP2 / (double) p2.getDuration();

//            return Double.compare(secCostP1, secCostP2);
            return Double.compare(p1.getOneSecCost(), p2.getOneSecCost());
        });

        for (Advertisement ad : toShow) {
            ConsoleHelper.writeMessage(ad.getName() + " is displaying... " + ad.getAmountPerOneDisplaying() + ", " +
                    + (int)(ad.getOneSecCost() * 1000));   //secCost - to Advert
            ad.revalidate();
        }
    }

    private void searchAdVariants (int startFrom, List<Advertisement> inList, int inTime) {
        if (inList == null) inList = new ArrayList<>();
        int maxMoney = 0;

        for (int i = startFrom; i < storage.list().size(); i++) {
            Advertisement element = storage.list().get(i);

            //hits check

            if (element.hits > 0 && (inTime + element.getDuration()) <= timeSeconds) { // если элемент можо добавить по времени
                int outTime = inTime + element.getDuration();
                List<Advertisement> outList = new ArrayList<>(inList);
                outList.add(element);
                if (toShow.isEmpty()) toShow = outList;

                int thisMoney = 0;
                int toShowMoney = 0;
                for (Advertisement ad : outList) {
                    thisMoney += ad.getAmountPerOneDisplaying();
                }
                for (Advertisement ad : toShow) {
                    toShowMoney += ad.getAmountPerOneDisplaying();
                }

                if (thisMoney == toShowMoney) {
                    int thisTime = 0;
                    int toShowTime = 0;
                    for (Advertisement ad : outList) {
                        thisTime += ad.getDuration();
                    }
                    for (Advertisement ad : toShow) {
                        toShowTime += ad.getDuration();
                    }

                    if (thisTime > toShowTime) {
                        toShow = outList;
                    } else if (thisTime == toShowTime) {
                        if (outList.size() < toShow.size()){
                            toShow = outList;
                        }
                    }

//                    adVariants.add(outList);

                } else if (thisMoney > toShowMoney) {
                    toShow = outList;
//                    adVariants.clear();
//                    adVariants.add(outList);
                    maxMoney = thisMoney;
                }

                if (i < storage.list().size()) searchAdVariants(i + 1, outList, outTime);
            }
        }
    }


}
