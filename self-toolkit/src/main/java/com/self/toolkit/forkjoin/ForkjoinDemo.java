package com.self.toolkit.forkjoin;

import cn.hutool.core.date.StopWatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author szy
 * @version 1.0
 * @description 使用多线程
 * @资料: https://www.notion.so/szyy/ForkJoinPool-a6f1b81550ce44aa8dce15b70426845c#1c1147de0cfd4b93b9d1d03ea604fa9e
 * @date 2022-01-19 10:23:07
 */

public class ForkjoinDemo {

    List<String> list = new ArrayList<>();
     {
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }
    }

    @Test
    public void t() throws Exception {
        StopWatch s = new StopWatch();
        s.start();
        // 设置同时执行的 最大线程数量 = 2
        // 该示例,parallelism=2, list.size =10, 所以会出现线程阻塞的情况, 如果需要,可以使用ManagedBlocker来解决
        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
        int i = forkJoinPool.submit(new MyTask(list)).get();
        s.stop();
        System.out.println("总处理条数:" + i);
        System.out.println(s.getTotalTimeSeconds());
    }

    class MyTask extends RecursiveTask<Integer> {
        private final List<String> loans;

        MyTask(List<String> loans) {
            this.loans = loans;
        }

        @Override
        protected Integer compute() {
            if (loans.size() < 2) {
                // 多线程具体的实现
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
                System.out.println("哇咔咔咔咔");

                return loans.size();
            } else {
                // 分流操作
                MyTask myTask = new MyTask(loans.subList(0, loans.size() / 2));
                MyTask myTask1 = new MyTask(loans.subList(loans.size() / 2, loans.size()));
                myTask.fork();
                myTask1.fork();
                return myTask.join() + myTask1.join();
            }
        }
    }

}
