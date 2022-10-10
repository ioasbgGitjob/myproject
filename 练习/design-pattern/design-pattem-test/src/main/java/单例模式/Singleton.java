package 单例模式;

/**
 * 单例模式  饿汉实现
 */

public class Singleton {

    private Singleton() {
    }

    private static Singleton singleton = new Singleton();

    public static Singleton getInstance() {
        return singleton;
    }

}

/**
 * 懒汉 实现
 */
class SingletonLazy {
    private SingletonLazy() {}
    private static SingletonLazy singleton;

    public static SingletonLazy getInstance() {
        if (null != singleton) {
            singleton = new SingletonLazy();
        }
        return singleton;
    }
}

/**
 * 静态内部类 实现
 */
class SingletonInner {
    private static class SingletonHolder {
        private static SingletonInner instance = new SingletonInner();
    }

    /**
     * 私有的构造函数
     */
    private SingletonInner() {
    }

    public static SingletonInner getInstance() {
        return SingletonHolder.instance;
    }

    protected void method() {
        System.out.println("SingletonInner");
    }
}


/**
 * 枚举 实现
 */
enum SingletonEnum {
    /**
     * 1.从Java1.5开始支持;
     * 2.无偿提供序列化机制;
     * 3.绝对防止多次实例化，即使在面对复杂的序列化或者反射攻击的时候;
     */

    instance;

    private String others;

    SingletonEnum() {

    }

    public void method() {
        System.out.println("SingletonEnum");
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }
}