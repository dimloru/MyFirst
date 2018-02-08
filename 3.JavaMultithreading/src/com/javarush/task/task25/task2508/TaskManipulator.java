package com.javarush.task.task25.task2508;

public class TaskManipulator implements Runnable, CustomThreadManipulator {
    Thread thread;

    @Override
    public void run() {
    }

    @Override
    public void start(String threadName) {
        thread = new Thread(this, threadName); //??

    }

    @Override
    public void stop() {

    }
}
