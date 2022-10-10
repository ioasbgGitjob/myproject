package 代理模式.动态;

import 代理模式.ProxyInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author szy
 * @version 1.0
 * @description  基于JDK的 动态代理类
 * @date 2022-06-17 14:00:01
 */

public class DynamicProxyHandle implements InvocationHandler {

    private ProxyInterface obj;

    public Object setObj(ProxyInterface object) {
        obj = object;
        // 用Proxy类的静态方法newProxyInstance方法，将代理对象伪装成那个被代理的对象
        // 这个方法会将obj指向实际实现接口的子类对象
        // 根据被代理类的信息返回一个代理类对象
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println("做结婚前的准备");
        Object o1 = method.invoke(obj, objects);
        System.out.println("做结婚后的收尾");
        return o1;
    }
}
