package com.javarush.task.task39.task3913;

import java.nio.file.Paths;
import java.util.Date;


public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("c:/JavaX/logs/"));
//        System.out.println(logParser.getNumberOfUniqueIPs(null, new Date()));

        System.out.println(logParser.getAllSolvedTasksAndTheirNumber(null, null));

        System.out.println(logParser.execute("get ip for user = \"Amigo\" and date between \"03.01.2014 03:45:23\" and \"29.2.2028 5:4:8\""));

    }
}