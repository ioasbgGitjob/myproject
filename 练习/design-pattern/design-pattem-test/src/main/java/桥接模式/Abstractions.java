package 桥接模式;

/**
 * 抽象类和具体类 ， 手机
 */
public class Abstractions {

    /**
     * 抽象类-Phone的具体实现 ， 小米手机
     */
    public static class MiPhone extends Phone {
        @Override
        public void buyPhone() {
            phoneMemory.addMemory();
            System.out.println("买y了小米手机");
        }
    }

    /**
     * 抽象类-Phone的具体实现 ， 华为手机
     */
    public static class HuaWeiPhone extends Phone {
        @Override
        public void buyPhone() {
            System.out.println("买了华为手机");
        }
    }

}
