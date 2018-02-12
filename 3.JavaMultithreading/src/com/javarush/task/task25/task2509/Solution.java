package com.javarush.task.task25.task2509;

import java.util.concurrent.*;

/* 
Все не так легко, как кажется
*/
public class Solution extends ThreadPoolExecutor {
    public Solution(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if (callable instanceof CancellableTask) {
            return ((CancellableTask<T>) callable).newTask(); //если переданный callable - "наш", обращается к переопределенному newTask
        } else {
            return super.newTaskFor(callable); // обращение к newTaskFor из AbstractExecutorService, возвращает RunnableFutute<T> на переданный Callable<T>
        }
    }

    public static void main(String[] args) {
    }
}