package com.javarush.task.task27.task2707;

/* 
Определяем порядок захвата монитора
*/
public class Solution {
    public void someMethodWithSynchronizedBlocks(Object obj1, Object obj2) {
        synchronized (obj1) {
            synchronized (obj2) {
                System.out.println(obj1 + " " + obj2);
            }
        }
    }

    public static boolean isNormalLockOrder(final Solution solution, final Object o1, final Object o2) throws Exception {
        new Thread(() -> {
            synchronized (o2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
            }
        }).start();

        Thread.sleep(100);

        Thread methodThread = new Thread (() -> {
            solution.someMethodWithSynchronizedBlocks(o1, o2);
        });
        methodThread.start();

        Thread.sleep(100);

        Thread t2 = new Thread(() -> {
            synchronized (o1) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();

        Thread.sleep(100);

        if (t2.getState() == Thread.State.BLOCKED) return true;
        else return false;
    }

    public static void main(String[] args) throws Exception {
        final Solution solution = new Solution();
        final Object o1 = new Object();
        final Object o2 = new Object();

        System.out.println(isNormalLockOrder(solution, o1, o2));
    }
}
