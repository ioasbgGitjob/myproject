import cn.hutool.core.date.StopWatch;
import org.junit.Test;

/**
 * @名字
 * @应用场景说明
 * @任务
 * @结论
 */
public class 模板 {

    @Test
    public void mainTest() throws InterruptedException {
        StopWatch s = new StopWatch();
        Thread.sleep(2000);
        Thread.sleep(2000);
        Thread.sleep(2000);
        Thread.sleep(2000);

        test1(s);
        test1(s);
        test1(s);
        test1(s);

        System.out.println(s.getTotalTimeSeconds());
        System.out.println(s.prettyPrint());
    }

    public void test1(StopWatch s) {
        s.start("测试的名称");
        // TODO
        s.stop();
    }
}
