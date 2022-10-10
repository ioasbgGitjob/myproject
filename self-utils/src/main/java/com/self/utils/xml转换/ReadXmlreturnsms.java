package com.self.utils.xml转换;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.Date;

/**
 * @author szy
 * @version 1.0
 * @description
 * @date 2021-11-22 14:52:22
 */

@Data // 与数据转换无关
@XmlAccessorType(XmlAccessType.FIELD)// 用于 SETTLE_DAY
@XmlRootElement(name = "returnsms")
public class ReadXmlreturnsms {
    private String returnstatus;
    private String message;
    private String remainpoint;
    private Long taskID;
    private Integer successCounts;
    private ReadXmlCount count;
    @XmlElement(name = "SETTLE_DAY")
    private Date settleDay;

}
