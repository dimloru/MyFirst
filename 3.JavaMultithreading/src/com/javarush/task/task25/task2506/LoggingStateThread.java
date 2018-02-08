package com.javarush.task.task25.task2506;

public class LoggingStateThread extends Thread {
    private Thread t;
    private Thread.State state = State.TERMINATED;

    public LoggingStateThread (Thread t) {
//        this.setDaemon(true);
        this.t = t;
    }

    @Override
    public void run() {
        while (true) {
            if (t.getState() != state) {
                state = t.getState();
                System.out.println(state);
                if (state == State.TERMINATED) break;
            }
        }
    }
}