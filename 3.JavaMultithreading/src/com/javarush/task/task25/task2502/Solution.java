package com.javarush.task.task25.task2502;

import java.util.*;

/* 
Машину на СТО не повезем!
*/
public class Solution {
    public static enum Wheel {
        FRONT_LEFT,
        FRONT_RIGHT,
        BACK_LEFT,
        BACK_RIGHT
    }

    public static class Car {
        protected List<Wheel> wheels = new ArrayList<>();

        public Car() {
            //init wheels here
            String[] wheelDB = loadWheelNamesFromDB();
            if (wheelDB == null || wheelDB.length != 4) throw new IllegalArgumentException("Empty DB");
            for (String wheel : wheelDB) {
                if (wheel != null) {
                    wheels.add(Wheel.valueOf(wheel));
                } else {
                    throw new IllegalArgumentException();
                }
            }
        }

        protected String[] loadWheelNamesFromDB() {
            //this method returns mock data
            return new String[]{"FRONT_LEFT", "FRONT_RIGHT", "BACK_LEFT", "BACK_RIGHT"};
        }
    }

    public static void main(String[] args) {
    }
}
