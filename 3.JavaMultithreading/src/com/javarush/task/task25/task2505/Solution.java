package com.javarush.task.task25.task2505;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
Без дураков
*/
public class Solution {

    public static void main(String[] args) {
        MyThread myThread = new Solution().new MyThread("super secret key");
        myThread.start();
    }

    public class MyThread extends Thread {
        private String secretKey;
        private Logger logger = Logger.getLogger(MyThread.class.getName());

        public MyThread(String secretKey) {
            this.secretKey = secretKey;
            setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        }

        @Override
        public void run() {
            throw new NullPointerException("it's an example");
        }

        private class MyUncaughtExceptionHandler implements UncaughtExceptionHandler {
            @Override
            public void uncaughtException(Thread t, Throwable e){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                String msg = String.format("%s, %s, %s", secretKey, t.getName(), e.getMessage());
                System.out.println(msg);
                logger.log(Level.INFO, msg);
            }
        }
    }
}

