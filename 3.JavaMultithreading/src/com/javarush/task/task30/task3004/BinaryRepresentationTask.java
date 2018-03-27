package com.javarush.task.task30.task3004;

import java.util.concurrent.RecursiveTask;

public class BinaryRepresentationTask extends RecursiveTask<String> {
    private int x;
    public BinaryRepresentationTask(int x) {
        this.x = x;
    }

    @Override
    protected String compute() {
        int a = x % 2;
        int b = x / 2;

        BinaryRepresentationTask f1 = new BinaryRepresentationTask(b);
        f1.fork();

        String result = String.valueOf(a);
        if (b > 0) result = f1.join() + result;

        return result;

    }
}
