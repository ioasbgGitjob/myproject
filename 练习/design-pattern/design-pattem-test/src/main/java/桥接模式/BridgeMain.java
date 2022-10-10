package 桥接模式;

/**
 * 将抽象部分与它实现部分分离，使它们都可以独立地变化。
 * 使用：JDK中的JDBC，
 */
public class BridgeMain {

    /**
     * 买一个 6G的小米手机和8G的华为
     * @param args
     */
    public static void main(String[] args) {
        Phone miPhone = new Abstractions.MiPhone();
        miPhone.setMemory(new Implementor.Memory6G());
        miPhone.buyPhone();

        Phone huaWeiPhone = new Abstractions.HuaWeiPhone();
        huaWeiPhone.setMemory(new Implementor.Memory8G());
        huaWeiPhone.buyPhone();

    }
}
