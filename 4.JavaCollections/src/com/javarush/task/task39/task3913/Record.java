package com.javarush.task.task39.task3913;

import java.util.Date;

public class Record {
    public String ip;
    public String userName;
    public Date date;
    public Event event;
    public int taskNumber; // -1 if no data
    public Status status;

    public Record(String ip, String userName, Date date, Event event, int taskNumber, Status status) {
        this.ip = ip;
        this.userName = userName;
        this.date = date;
        this.event = event;
        this.taskNumber = taskNumber;
        this.status = status;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    @Override
    public String toString() {
        return "[ip = " + ip +
                "; userName = " + userName +
                "; date = " + date +
                "; event = " + event +
                "; taskN = " + taskNumber +
                "; status = " + status;
    }
}
