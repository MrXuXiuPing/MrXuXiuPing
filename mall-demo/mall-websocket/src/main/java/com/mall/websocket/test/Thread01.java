package com.mall.websocket.test;

import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Thread01 {

    private static final Vector<Integer> vector = new Vector<>();

    private static final ThreadLocal<Integer> time = new ThreadLocal<>();

    private Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        final int threadSize = 10000000;
        ThreadUnsafeExample example = new ThreadUnsafeExample();
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < threadSize; i++) {
            executorService.execute(() -> {
                example.add();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println(example.get());
        String str = "a,b,c,d,e,f,g,";
        if (str.endsWith(",")) {
            str = str.substring(0, str.length() - 1);
        }

        System.out.println(str);
    }

}

class ThreadUnsafeExample {
    //    private int cnt = 0;
    private static AtomicInteger cnt = new AtomicInteger();

    public void add() {
//        cnt++;
        cnt.incrementAndGet();
    }

    //加一
    public AtomicInteger get() {
        return cnt;
    }
}
