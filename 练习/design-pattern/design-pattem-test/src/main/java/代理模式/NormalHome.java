package 代理模式;

/**
 * 结婚的家庭（需要被代理去做一些事的对象）
 */
public class NormalHome implements ProxyInterface {

    @Override
    public void marry() {
        System.out.println("我们结婚啦～");
    }

}