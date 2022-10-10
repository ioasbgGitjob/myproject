package com.example.self.forkjoin;

import org.junit.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author szy
 * @version 1.0
 * @description 非阻塞线程
 * @date 2022-01-19 10:53:36
 */

public class ManagedBlockerDemo {

    String threadDateTimeInfo() {
        return DateTimeFormatter.ISO_TIME.format(LocalTime.now()) + Thread.currentThread().getName();
    }

    @Test
    public void test2() {
        List<IOBlockerTask<String>> tasks = Stream.generate(() -> new IOBlockerTask<String>(() -> {
            System.out.println(threadDateTimeInfo() + ":simulate io task blocking for 2 seconds···");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new Error(e);
            }
            return threadDateTimeInfo() + ": io blocking task returns successfully";
        })).limit(30).collect(Collectors.toList());
        tasks.forEach(e -> e.fork());
        tasks.forEach(e -> {
            try {
                System.out.println(e.get());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }
}


class IOBlockerTask<T> extends RecursiveTask<T> {
    private MyManagedBlockerImpl blocker;

    public IOBlockerTask(Supplier<T> supplier) {
        this.blocker = new MyManagedBlockerImpl<T>(supplier);
    }

    static class MyManagedBlockerImpl<T> implements ForkJoinPool.ManagedBlocker {
        private Supplier<T> supplier;
        private T result;

        public MyManagedBlockerImpl(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        @Override
        public boolean block() throws InterruptedException {
            result = supplier.get();
            return true;
        }

        @Override
        public boolean isReleasable() {
            return false;
        }
    }

    @Override
    protected T compute() {
        try {
            ForkJoinPool.managedBlock(blocker);
            setRawResult((T) blocker.result);
            return getRawResult();
        } catch (InterruptedException e) {
            throw new Error(e);
        }
    }
}
