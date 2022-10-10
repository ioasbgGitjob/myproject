package 模板方法模式;

/**
 * @author szy
 * @version 1.0
 * @description 重写父类的方法, 完成完整的流程
 * @date 2022-06-13 15:57:40
 */

public class XXXHandle extends AbstractXXXHandle {

    @Override
    protected boolean step2() {
        System.out.println("执行第222步" + this.getClass());
        flag = false;
        return flag;
    }

    @Override
    protected void step3() {
        System.out.println("执行第333步" + this.getClass());
    }

    @Override
    protected void step4() {
        System.out.println("执行第444步" + this.getClass());
    }
}
