package com.example.jdkdemo.jdk14;

/**
 * @author szy
 * @version 1.0
 * @description  实例匹配
 * @date 2022-06-07 14:18:54
 */


public class InstanceofDemo {

    Object obj = null;

    public void java8() {
        if (obj instanceof MyObject) {
            MyObject myObject = (MyObject) obj;
            // TODO
        }
    }


    public void java17() {
        if (obj instanceof MyObject myObject) {
            // TODO
        }

        if (obj instanceof MyObject myObject && myObject.isValid()) {
            // TODO
        }

    }


    // 需要转换的对象
    class MyObject {
        boolean valid;

        public boolean isValid() {
            return valid;
        }
    }
}
