package com.self.utils.发邮件;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.InputStream;

/**
 * @author szy
 * @version 1.0
 * @description 邮件附件类
 * @date 2021-11-25 15:19:58
 */

@Data
@AllArgsConstructor
public class MailAttachment {
    private String name;
    private InputStream inputStream;
}
