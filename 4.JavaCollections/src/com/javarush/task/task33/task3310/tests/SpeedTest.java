package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {

    public long getTimeForGettingIds(Shortener shortener, Set<String> strings, Set<Long> ids) {
        Date start = new Date();
        for (String string : strings) {
            ids.add(shortener.getId(string));
        }
        Date finish = new Date();

        long time = finish.getTime() - start.getTime();
        return time;

    }

    public long getTimeForGettingStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
        Date start = new Date();
        for (Long id : ids) {
            strings.add(shortener.getString(id));
        }
        Date finish = new Date();
        long time = finish.getTime() - start.getTime();
        return time;
    }

    @Test
    public void testHashMapStorage() {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());

        Set<String> origStrings = new HashSet<>();

        for (int i = 0; i < 10000; i++) {
            origStrings.add(Helper.generateRandomString());
        }

        Set<Long> ids1 = new HashSet<>(), ids2 = new HashSet<>();
        Set<String> strings1 = new HashSet<>(), strings2 = new HashSet<>();

        long time4Shortener1 = getTimeForGettingIds(shortener1, origStrings, ids1);
        long time4Shortener2 = getTimeForGettingIds(shortener2, origStrings, ids2);

        Assert.assertTrue(time4Shortener1 > time4Shortener2);

        time4Shortener1 = getTimeForGettingStrings(shortener1, ids1, strings1);
        time4Shortener2 = getTimeForGettingStrings(shortener2, ids2, strings2);

        Assert.assertEquals(time4Shortener1, time4Shortener2, 30f);
    }

}
