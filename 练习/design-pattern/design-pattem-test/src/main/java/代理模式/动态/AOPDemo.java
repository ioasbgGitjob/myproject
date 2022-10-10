package 代理模式.动态;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author szy
 * @version 1.0
 * @description 基于JDK的  动态代理APO的实现
 * @date 2022-06-17 14:21:12
 */

public class AOPDemo {

}

//基于jdk的针对接口实现动态代理，要求的接口
interface Target {
    void login();

    void logout();
}

//被代理类
class TargetImpl implements Target {
    @Override
    public void login() {
        System.out.println("log......");
    }

    @Override
    public void logout() {
        System.out.println("logout......");
    }
}

class Util {
    public void printLog() {
        System.out.println("我是记录打印日志功能的方法......");
    }

    public void getProperties() {
        System.out.println("我是获取配置文件信息功能的方法......");
    }
}


//实现了InvocationHandler的统一代理类
class DynamicProxyHandler implements InvocationHandler {
    private Object object;

    /**
     * 参数为obj，是应对对不同的被代理类，都能绑定与该代理类的代理关系
     * 这个方法会将targetOne指向实际实现接口的子类对象,即当前代理类实际要去代理的那个类
     */
    public void setObj(Object obj) {
        this.object = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Util util = new Util();
        util.getProperties();
        Object object = method.invoke(this.object, args); //这个方法是个动态的方法，可以是login,可以是logout，具体在测试调用中调用不同方法
        util.printLog();
        return object;
    }
}


//该类的主要作用就是动态的创建一个代理类的对象，同时需要执行被代理类
class MyDynamicProxyUtil {
    //参数obj表示动态的传递进来被代理类的对象
    public static Object getProxyInstance(Object object) {
        //获取代理类对象
        DynamicProxyHandler dynamicProxyHandler = new DynamicProxyHandler();
        dynamicProxyHandler.setObj(object);
        //设置好代理类与被代理类之间的关系后，返回一个代理类的对象
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), dynamicProxyHandler);
    }
}

class TestAop {
    public static void main(String[] args) {
        //获得被代理类
        Target target = new TargetImpl();
        //通过代理类工具类，设置实际与代理类绑定的被代理类，并返回一个代理类对象执行实际的方法
        Target execute = (Target) MyDynamicProxyUtil.getProxyInstance(target);
        execute.login();
        execute.logout();
    }
}
