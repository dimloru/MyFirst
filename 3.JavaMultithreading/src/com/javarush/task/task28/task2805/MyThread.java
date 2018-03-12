package com.javarush.task.task28.task2805;

import java.util.concurrent.atomic.AtomicInteger;

public class MyThread extends Thread {
    public static AtomicInteger priorityCounter = new AtomicInteger(Thread.MIN_PRIORITY);

    public MyThread() {
        super();

        priorityCounter.compareAndSet(Thread.MAX_PRIORITY + 1, Thread.MIN_PRIORITY);
        setPriority(priorityCounter.getAndIncrement());
    }

    public MyThread(Runnable target) {
        super(target);

        priorityCounter.compareAndSet(Thread.MAX_PRIORITY + 1, Thread.MIN_PRIORITY);
        setPriority(priorityCounter.getAndIncrement());

    }

    public MyThread(ThreadGroup group, Runnable target) {
        super(group, target);

        priorityCounter.compareAndSet(Thread.MAX_PRIORITY + 1, Thread.MIN_PRIORITY);
        //необязательная проверка, при заданном max_priority, приоритет просто не выставится выше
        int thisPriority = priorityCounter.get() < group.getMaxPriority() ? priorityCounter.get() : group.getMaxPriority();
        setPriority(thisPriority);
        priorityCounter.incrementAndGet();

    }

    public MyThread(String name) {
        super(name);

        priorityCounter.compareAndSet(Thread.MAX_PRIORITY + 1, Thread.MIN_PRIORITY);
        setPriority(priorityCounter.getAndIncrement());

    }

    public MyThread(ThreadGroup group, String name) {
        super(group, name);

        priorityCounter.compareAndSet(Thread.MAX_PRIORITY + 1, Thread.MIN_PRIORITY);
        int thisPriority = priorityCounter.get() < group.getMaxPriority() ? priorityCounter.get() : group.getMaxPriority();
        setPriority(thisPriority);
        priorityCounter.incrementAndGet();

    }

    public MyThread(Runnable target, String name) {
        super(target, name);

        priorityCounter.compareAndSet(Thread.MAX_PRIORITY + 1, Thread.MIN_PRIORITY);
        setPriority(priorityCounter.getAndIncrement());

    }

    public MyThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);

        priorityCounter.compareAndSet(Thread.MAX_PRIORITY + 1, Thread.MIN_PRIORITY);
        int thisPriority = priorityCounter.get() < group.getMaxPriority() ? priorityCounter.get() : group.getMaxPriority();
        setPriority(thisPriority);
        priorityCounter.incrementAndGet();

    }

    public MyThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        super(group, target, name, stackSize);

        priorityCounter.compareAndSet(Thread.MAX_PRIORITY + 1, Thread.MIN_PRIORITY);
        setPriority(priorityCounter.getAndIncrement());
    }
}
