package com.self.utils.xml转换;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.XML;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * @author szy
 * @version 1.0
 * @description xml转换工具;
 * @date 2021-11-22 15:02:42
 */

public class XmlUtils {

    /**
     * readXmlFile.xml 对应两个java对象
     */
    @Test
    public void test() throws Exception {
        String filePath = getClass().getClassLoader().getResource("file/readXmlFile.xml").getPath();
        ReadXmlreturnsms bean = XmlUtils.xmlFileToObj(ReadXmlreturnsms.class, filePath, "GBK");
        System.out.println("xml转java: " + bean);

        System.out.println("xml转JSON: " + xmlFileToJsonObj(filePath, "GBK"));
    }

    /**
     * xml字符串转为java对象
     *
     * @param clazz
     * @param xmlString
     */
    public static <T> T xmlStrToObj(Class<?> clazz, String xmlString) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xmlString);
            T message = (T) unmarshaller.unmarshal(reader);
            return message;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将file类型的xml转换成对象
     *
     * @param clazz
     * @param xmlPath
     */
    public static <T> T xmlFileToObj(Class<?> clazz, String xmlPath) {
        return xmlFileToObj(clazz, xmlPath, "GBK");
    }

    /**
     * 将file类型的xml转换成对象
     *
     * @param clazz
     * @param xmlPath
     */
    public static <T> T xmlFileToObj(Class<?> clazz, String xmlPath, String charset) {
        T xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            InputStreamReader isr = new InputStreamReader(new FileInputStream(xmlPath), charset);
            xmlObject = (T) unmarshaller.unmarshal(isr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlObject;
    }

    /**
     * 把xml转换为json
     * 依赖于 hutools
     *
     * @param filePath
     * @param charset
     * @return JSONObject
     */
    public static JSONObject xmlFileToJsonObj(String filePath, String charset) {
        return XML.toJSONObject(FileUtil.readString(filePath, CharsetUtil.charset(charset)));
    }

    /**
     * 把xml转换为json
     * 依赖于 hutools
     *
     * @param xmlStr
     * @return JSONObject
     */
    public static JSONObject xmlStrToJsonObj(String xmlStr) {
        return XML.toJSONObject(xmlStr);
    }

}
