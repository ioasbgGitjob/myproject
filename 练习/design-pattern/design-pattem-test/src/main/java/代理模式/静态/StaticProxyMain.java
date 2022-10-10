package 代理模式.静态;

import 代理模式.NormalHome;
import 代理模式.ProxyInterface;

/**
 * 代理类帮忙执行 一段逻辑
 * 作用：
 */
public class StaticProxyMain {

    /**
     * NormanHome 结婚了，找到 Wedding代理举办婚礼
     * @param args
     */
    public static void main(String[] args) {
        ProxyInterface proxyInterface = new WeddingCompany(new NormalHome());
        proxyInterface.marry();
    }
}
