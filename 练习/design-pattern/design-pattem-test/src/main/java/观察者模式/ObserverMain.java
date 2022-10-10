package 观察者模式;

/**
 * Subject 观察主题对象，也可以叫被观察或者被订阅对象
 * Observer 观察者或者订阅者对象，当Subject有变动，就会通知到每一个Observer
 *
 * 作用： 构建发布订阅模型
 */
public class ObserverMain {

    /**
     * 发布-订阅模型
     * 发布消息给 两个订阅者
     *
     * @param args
     */
    public static void main(String[] args) {
        // 创建消息发布者
        UserSubject subject = new UserSubject();
        // 创建两个消息订阅者
        Observer observer = new UserObserver();
        Observer observer1 = new UserObserver1();
        // 把订阅者传入 发布者里面
        subject.addObserver(observer);
        subject.addObserver(observer1);

        // 向所有订阅者 发送消息
        subject.attach(null);
        // 给指定订阅者  发送消息
        subject.attach(observer);

    }

    /**
     * 订阅者
     */
    static class UserObserver implements Observer {
        @Override
        public void updateMessage(Object obj) {
            System.out.println("我是UserObserver:收到：" + obj.toString());
        }
    }

    static class UserObserver1 implements Observer {
        @Override
        public void updateMessage(Object obj) {
            System.out.println("我是UserObserver1:收到：" + obj.toString());
        }
    }
}
