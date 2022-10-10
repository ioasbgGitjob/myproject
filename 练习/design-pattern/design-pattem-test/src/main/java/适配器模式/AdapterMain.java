package 适配器模式;

/**
 * 关联两个不相关的应用（例子：想调用手机的充电接口，放一个适配器进去以适应电压）
 * 应用： 解决接口不舍配的问题
 */
public class AdapterMain {

    /**
     * 把新的充电方式，传入手机类，成为手机的属性，在调用手机的充电功能
     */
    public static void main(String[] args) {
        Phone phone = new Phone();
        VoltageAdapter voltageAdapter  = new VoltageAdapter();
        phone.setAdapter(voltageAdapter);
        phone.charge();
    }


    // 手机类
    public static class Phone {

        public static final int V = 220;// 正常电压220v，是一个常量

        private VoltageAdapter adapter;

        // 充电
        public void charge() {
            adapter.changeVoltage();
        }

        public void setAdapter(VoltageAdapter adapter) {
            this.adapter = adapter;
        }
    }

    // 变压器
    public  static class VoltageAdapter {
        // 改变电压的功能
        public void changeVoltage() {
            System.out.println("正在充电...");
            System.out.println("原始电压：" + Phone.V + "V");
            System.out.println("经过变压器转换之后的电压:" + (Phone.V - 200) + "V");
        }
    }
}
