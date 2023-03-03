package com.self.start.startspring.config;

import org.sca.arch.application.common.util.SystemContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author szy
 * @version 1.0
 * @date 2023-03-03 15:45:18
 * @description
 */
public class AIntercepttor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Integer random = (int) (Math.random() * 1000);
        SystemContext.setTenantId(random);
        System.out.println("-------------------------------------------------:" + random);

        return true;
    }
}
