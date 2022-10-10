package 观察者模式;

import java.util.ArrayList;
import java.util.List;

/**
 * 我是小美，他们有有我电话
 */
public class UserSubject implements Subject {
    public UserSubject() {

    }

    List<Observer> list = new ArrayList<>();

    public void addObserver(Observer observer) {
        list.add(observer);
    }

    @Override
    public void attach(Observer observer) {
        // 给所有订阅者发送消息
        if (null == observer) {
            for (Observer o : list) {
                o.updateMessage("我是小美，快来找我啊");
            }
        }
        // 给指定订阅者发送
        else {
            observer.updateMessage("我是小美，快来找我啊");
        }

    }

    @Override
    public void detach(Observer observer) {

    }

    @Override
    public void notifyChanged() {

    }
}
