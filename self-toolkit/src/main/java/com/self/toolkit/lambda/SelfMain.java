package com.self.toolkit.lambda;

import com.self.toolkit.aspect.dataProtection.AspectTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author szy
 * @version 1.0
 * @description
 * @date 2021-12-13 15:03:53
 */

public class SelfMain {

    // 接口函数gogogo()的具体实现
    SelfLambda selfLambda = () -> {
        return "1";
    };

    @Test
    public  void testSelfLambda() {

        // 调用函数的接口
        System.out.println(selfLambda.gogogo());

    }

    /**
     * lambda在线程中的应用
     */
    @Test
    public void testCompare() {
        List<AspectTest.TUser> s = new ArrayList<>();
        new Thread(()->{
            // 这里返回一个 Runnable 接口
            // 这里写的内容相当于直接写了一个类  继承了Runnable 接口
        }).start();
    }

}
