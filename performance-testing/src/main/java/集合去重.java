import pojo.TestEntity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @名字 集合去重
 * @应用场景说明 A, B两个对象集合，根据对象内固定的n列（n>=2），求交集,并集,差集
 * @任务 1 拿到重复过的元素； 2 拿到没重复过的元素
 * @结论 treeset 效率最高  map 次之，removeAll效率最低
 * @结论 Arraylsit.removAll()  效率极低
 *
 */
public class 集合去重 {
    public static void main(String[] args) throws InterruptedException {
        List<TestEntity> listA = new ArrayList<>();
        List<TestEntity> listB = new ArrayList<>();
        //添加相同对象
        for (int i = 1; i <= 100000; i++) {
            TestEntity p = TestEntity.builder().name(String.valueOf(i)).num(i).build();
            TestEntity p1 = TestEntity.builder().name(String.valueOf(i)).num(i).build();
            listA.add(p);
            listB.add(p1);
        }
        //添加不相同对象
        for (int i = 1; i <= 4; i++) {
            TestEntity p = TestEntity.builder().name("a" + i).num(i).build();
            TestEntity p1 = TestEntity.builder().name("b" + i).num(i).build();
            listA.add(p);
            listB.add(p1);
        }
        int time = 10 * 1000;//秒
        System.out.println("开始");

        Thread.sleep(time);
        test3(listA, listB);
        Thread.sleep(time);
        test3(listA, listB);
        Thread.sleep(time);
        test3(listA, listB);
        Thread.sleep(time);
        test3(listA, listB);
        Thread.sleep(time);

        Thread.sleep(time);
        test1(listA, listB);
        Thread.sleep(time);
        test1(listA, listB);
        Thread.sleep(time);
        test1(listA, listB);
        Thread.sleep(time);
        test1(listA, listB);

//            Thread.sleep(time);
//            test2(listA,listB);
    }

    /**
     * stream流 + treeSet操作
     */
    public static void test1(List<TestEntity> listA, List<TestEntity> listB) {
        String memo = "重复treeSet+非重复treeSet";
        Long l1 = System.currentTimeMillis();
        // TODO
        Set<String> setB = new HashSet<>(listB.stream().collect(Collectors.toMap(e -> e.getName() + e.getNum(), entity -> entity)).keySet());
        // listA对比listB  重复的元素
        List<TestEntity> repeat = listA.stream().filter(e -> !setB.add(e.getName() + e.getNum())).collect(Collectors.toList());
        System.out.println("重复元素size:" + repeat.size());
        Set<String> treeset = new HashSet<>(repeat.stream().collect(Collectors.toMap(e -> e.getName() + e.getNum(), entity -> entity)).keySet());
        List<TestEntity> listAll = new ArrayList<>();
        listAll.addAll(listA);
        listAll.addAll(listB);
        listAll = listAll.stream().filter(e -> treeset.add(e.getName() + e.getNum())).collect(Collectors.toList());
        System.out.println("非重复元素size:" + listAll.size());
        System.out.println("-----------" + memo + "用时" + (System.currentTimeMillis() - l1));
    }

    /**
     * stream流 + removeAll 操作
     */
    public static void test2(List<TestEntity> listA, List<TestEntity> listB) {
        String memo = "重复treeSet+非重复removeAll";
        Long l1 = System.currentTimeMillis();
        // TODO
        Set<String> setB = new HashSet<>(listB.stream().collect(Collectors.toMap(e -> e.getName() + e.getNum(), entity -> entity)).keySet());
        // listA对比listB  重复的元素
        List<TestEntity> repeat = listA.stream().filter(e -> !setB.add(e.getName() + e.getNum())).collect(Collectors.toList());
        System.out.println("重复元素size:" + repeat.size());
        Set<String> treeset = new HashSet<>(repeat.stream().collect(Collectors.toMap(e -> e.getName() + e.getNum(), entity -> entity)).keySet());
        List<TestEntity> listAll = new ArrayList<>();
        listAll.addAll(listA);
        listAll.addAll(listB);
        listAll.removeAll(repeat);
        System.out.println("非重复元素size:" + listAll.size());
        System.out.println("-----------" + memo + "用时" + (System.currentTimeMillis() - l1));
    }

    /**
     * stream流 + map.iterator操作
     */
    public static void test3(List<TestEntity> listA, List<TestEntity> listB) {
        String memo = "map keySet ";
        Long l1 = System.currentTimeMillis();
        // TODO
        Map<String, TestEntity> collectA = listA.stream().collect(Collectors.toMap(e -> e.getName() + e.getNum(), item -> item));
        Map<String, TestEntity> collectB = listB.stream().collect(Collectors.toMap(e -> e.getName() + e.getNum(), item -> item));
        Set<String> setA = new HashSet<>(collectA.keySet());
        Set<String> setB = new HashSet<>(collectB.keySet());

        // AB的交集
        Set<String> collect = setA.stream().filter(e -> !setB.add(e)).collect(Collectors.toSet());
        List<TestEntity> diff = new ArrayList<>();
        Iterator<Map.Entry<String, TestEntity>> iteratorA = collectA.entrySet().iterator();
        Iterator<Map.Entry<String, TestEntity>> iteratorB = collectB.entrySet().iterator();
        while (iteratorA.hasNext()) {
            Map.Entry<String, TestEntity> entry = iteratorA.next();
            if (!collect.contains(entry.getKey())) {
                diff.add(entry.getValue());
            }
        }
        while (iteratorB.hasNext()) {
            Map.Entry<String, TestEntity> entry = iteratorB.next();
            if (!collect.contains(entry.getKey())) {
                diff.add(entry.getValue());
            }
        }
        List<TestEntity> collect1 = listA.stream().filter(e -> !collect.add(e.getName() + e.getNum())).collect(Collectors.toList());
        System.out.println("重复元素size:" + collect1.size());
        System.out.println("非重复元素size:" + diff.size());
        System.out.println("-----------" + memo + "用时" + (System.currentTimeMillis() - l1));
    }


}

