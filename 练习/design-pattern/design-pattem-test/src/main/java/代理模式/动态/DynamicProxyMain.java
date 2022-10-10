package 代理模式.动态;

import 代理模式.NormalHome;
import 代理模式.ProxyInterface;
import 代理模式.静态.WeddingCompany;

/**
 * 动态代理 调用
 */
public class DynamicProxyMain {


    /**
     * 通过动态代理  实现
     *
     * @param args
     */
    public static void main(String[] args) {
        // 创建被代理类的对象
        ProxyInterface proxyInterface = new NormalHome();
        //创建实现了ProxyInterface的代理类对象，然后调用其中的setObj方法完成两项操作
        //1 将被代理类对象传入，运行时候调用的是被代理类重写的方法
        //2 返回一个类对象，通过代理类对象执行接口中的方法
        DynamicProxyHandle dynamicProxyHandle = new DynamicProxyHandle();
        ProxyInterface normalHome = (ProxyInterface) dynamicProxyHandle.setObj(proxyInterface);
        normalHome.marry();//调用该方法运行时都会转为对DynamicProxyHandler中的invoke方法的调用

    }
}
