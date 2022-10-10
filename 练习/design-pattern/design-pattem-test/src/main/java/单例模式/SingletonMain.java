package 单例模式;

/**
 * 构造方法私有化，静态方法构造单例资源
 *
 * 单例的主要实现
 * 饿汉模式
 * 懒汉模式（线程不安全）
 * 静态内部类
 * 枚举实现
 */
public class SingletonMain {

    public static void main(String[] args) {

        Singleton singleton = Singleton.getInstance();
        Singleton singleton1 = Singleton.getInstance();
        System.out.println(System.identityHashCode(singleton));
        System.out.println(System.identityHashCode(singleton1));

        System.out.println(singleton1.equals(singleton));
        System.out.println(singleton==singleton1);
        System.out.println("————————————————————————————————");

        T1 t1 = new T1();
        T1 t2 = new T1();
        System.out.println(System.identityHashCode(t1));
        System.out.println(System.identityHashCode(t2));
        System.out.println(t1.equals(t2));
        System.out.println(t1 == t2);

    }

}
