package com.example.jdkdemo.jdk8;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author szy
 * @version 1.0
 * @description stream
 * 最详细的介绍:  http://www.mydlq.club/article/90/#1distinct
 * Stream实现原理:  https://blog.csdn.net/li0978/article/details/113921324
 *
 *
 *     中间操作
 *         有状态:distinct  sorted  skip  limit
 *         无状态:map peek filter mapToInt mapToDouble
 *     终端操作:
 *         短路操作: anyMatch  allMatch  noneMatch  findFirst  findAny
 *         非短路操作:max  min  count  reduce  forEach  forEachOrdered  toArray  collect
 *
 * @date 2022-06-13 10:55:25
 */

public class StreamDemo {

    List<Dish> dishes = new ArrayList<>();

    {
        dishes.add(new Dish("张三",10));
        dishes.add(new Dish("张四",11230));
        dishes.add(new Dish("张五",10000));
        dishes.add(new Dish("张六",220));
        dishes.add(new Dish("张七",20));
        dishes.add(new Dish("张八",20));
    }

    public void demo() {
        Map<Integer, List<String>> lowCaloricDishesNameGroup =
                dishes.parallelStream() // 开启并行处理
                        .filter(d -> d.getCalories() < 400) // 按照热量值进行筛选
                        .sorted(Comparator.comparing(Dish::getCalories)) // 按照热量进行排序
                        .collect(Collectors.groupingBy( // 将菜品名按照热量进行分组
                                Dish::getCalories,
                                Collectors.mapping(Dish::getName, Collectors.toList())
                        ));

    }

    /**
     * map和flatMap的区别
     */
    @Test
    public void test3() {
        List<String> list = new ArrayList<String>(Arrays.asList("rock", "pop", "jazz", "reggae"));
//        对单个值进行处理
        list.stream().map(x -> x.toUpperCase()).forEach(x -> System.out.println("map:" + x));
//        拆分为多个值分别处理
        list.stream().flatMap(x -> Arrays.stream(x.toUpperCase().split("C"))).forEach(x -> System.out.println("flatMap:" + x));
    }

    /**
     * 数据转List
     */
    @Test
    public void testArrayToList() {
        int[] arr = {1, 2, 3, 4, 5};
        List<Integer> list = IntStream.of(arr).boxed().collect(Collectors.toList());
        System.out.println(list.toString());

        String[] arr2 = {"a", "b", "c"};
        List<String> list2 = Stream.of(arr2).collect(Collectors.toList());
        System.out.println(list2.toString());
    }



    @Data
    @AllArgsConstructor
    class Dish{
        String name;
        int calories;
    }

}
