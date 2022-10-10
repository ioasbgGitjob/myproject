package 模板方法模式;

/**
 * @author szy
 * @version 1.0
 * @description XXX 业务的主流程
 * @date 2022-06-13 15:53:40
 */

public abstract class AbstractXXXHandle implements IHandle {

    boolean flag;

    @Override
    public void handle() {
        // 1
        step1();
        // 2
        boolean boole = step2();

        if (boole) {
            // 3
            step3();
        } else {
            // 4
            step4();
        }

    }

    protected void step1() {
        // 随便做点啥子
        System.out.println("执行第111步" + this.getClass());
    }

    ;

    protected boolean step2() {
        System.out.println("执行第222步" + this.getClass());
        flag = true;
        return flag;
    }

    protected abstract void step3();

    protected abstract void step4();

}
