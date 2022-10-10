package queue;


import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * java中常用的所有队列
 */

public class AllQueue {

    @Test
    public void test() throws Exception {
        for (int i = 0; i < 10; i++) {
            concurrentLinkedQueue();
        }
    }

    // ConcurrentLinkedQueue
    @Test
    public void concurrentLinkedQueue() throws  Exception{
        ConcurrentLinkedDeque<String> queue = new ConcurrentLinkedDeque<>();
        queue.add("1");
        queue.add("2");
        queue.add("3");
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();


        for (int i = 0; i < 1000000; i++) {
            queue.add(i+"");
        }

//        Thread.sleep(3000);
        Long l1 = System.currentTimeMillis();
        int i = queue.size();
        for (String s: queue) {
            set1.add(s);
        }
        System.out.println("set1："+set1.size()+"_"+(System.currentTimeMillis()-l1));
//        Thread.sleep(3000);
        l1 = System.currentTimeMillis();
        int j = queue.size();
        while (j>0){
            set2.add(queue.poll());
            j--;
        }
        System.out.println("set2："+set2.size()+"_"+(System.currentTimeMillis()-l1));
    }
}

