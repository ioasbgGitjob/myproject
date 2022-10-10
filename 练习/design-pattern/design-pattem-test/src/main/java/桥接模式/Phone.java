package 桥接模式;

/**
 * 手机，抽象类
 */
public abstract class Phone {

    public Implementor.Memory phoneMemory;

    public void setMemory(Implementor.Memory memory) {
        this.phoneMemory = memory;
    }

    public abstract void buyPhone();
}