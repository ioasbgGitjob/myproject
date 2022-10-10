package com.self.utils.xml转换;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author szy
 * @version 1.0
 * @description
 * @date 2021-11-22 14:52:22
 */

@Data
@XmlRootElement(name = "count")
public class ReadXmlCount {
    private String name;
}
