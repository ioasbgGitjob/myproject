package 桥接模式;

/**
 * 实现类
 */
public class Implementor {

    /**
     * 手机内存的接口类
     */
    public interface Memory{
        public  void  addMemory();
    }

    /**
     * 内存的具体实现类
     */
    public static class Memory6G implements Memory{
        @Override
        public void addMemory() {
            System.out.println("6G内存");
        }
    }
    public static class Memory8G implements Memory{
        @Override
        public void addMemory() {
            System.out.println("8G内存");
        }
    }


}
