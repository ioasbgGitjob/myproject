package queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author szy
 * @version 1.0
 * @description 线程池使用阻塞队列
 * @date 2022-07-13 12:53:07
 */

public class ThreadPollQueue {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        for (int i = 0; i < 50; i++) {
            final int localI = i;
            queue.add(new Runnable() {
                public void run() {
                    doExpensiveOperation(localI);
                }
            });
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 1000,
                TimeUnit.MILLISECONDS, queue);
        executor.prestartAllCoreThreads();
        executor.shutdown();
        executor.awaitTermination(100000, TimeUnit.SECONDS);
    }
    private static void doExpensiveOperation(int index) {
        System.out.println("Starting expensive operation " + index);
        try {
            Thread.sleep(index * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Ending   expensive operation " + index);
    }
}
