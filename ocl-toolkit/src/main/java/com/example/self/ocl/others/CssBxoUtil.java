package com.example.self.ocl.others;

import org.fit.cssbox.demo.ImageRenderer;
import org.xml.sax.SAXException;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author szy
 * @version 1.0
 * @description 根据url把网站存为图片
 * 参考资料: https://my.oschina.net/u/1264788/blog/1630604
 * @date 2022-01-07 16:03:49
 */

public class CssBxoUtil {

    public static void main(String[] args) {
        getImg();
    }

    /**
     * 中文乱码问题：集成到系统部署到Linux环境后会存在中文字符编译异常，都显示成“□”；
     * 解决方法是修改源码数据：找到文件“VisualContext.java” 所属：package org.fit.cssbox.layout，
     * 将该段修改为：font = new Font("宋体", Font.PLAIN, (int) CSSUnits.medium_font); 即可
     */
    public static void getImg() {
        try {
            ImageRenderer render = new ImageRenderer();
            System.out.println("start...");
            String url = "https://blog.csdn.net/chibugou0216/article/details/100795006";
            FileOutputStream out = new FileOutputStream(new File("D:\\myHtmlcssbox.png"));
            render.setWindowSize(new Dimension(1900, 1000), false);
            render.renderURL(url, out, ImageRenderer.Type.PNG);
            out.close();
            System.out.println("OK");
        } catch (IOException | SAXException exception) {
            exception.printStackTrace();
        }

    }


}
