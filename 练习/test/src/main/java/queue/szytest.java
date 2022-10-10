package queue;

import java.util.*;
import java.util.concurrent.*;

public class szytest {


    public static void main(String[] args) {
        Queue<String> qq = new ArrayBlockingQueue<String>(1);

        List<String> a = new LinkedList<>();

        ScheduledExecutorService aa = Executors.newScheduledThreadPool(10);
        ExecutorService aaa = Executors.newCachedThreadPool();
        List<String> ss = new ArrayList<>();

    }
}
