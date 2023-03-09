package com.self.start.startspring;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

/**
 * @author szy
 * @version 1.0
 * @date 2023-03-08 20:25:43
 * @description
 */

public class MyXxlJobExecutor extends XxlJobSpringExecutor {

    private static final Logger logger = LoggerFactory.getLogger(XxlJobExecutor.class);

    private int maxRetryTimes = 3; // maximum number of retry times
    private static ApplicationContext applicationContext;



}
