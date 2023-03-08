package com.self.start.startspring.aspcet;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.sca.arch.application.common.util.SystemContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 描述:公共字段处理切面
 */
@Component
@Aspect
@Slf4j
public class DBAspect {
    private final PathMatcher pathMatcher = new AntPathMatcher();
    private final List<String> insertPathMethodNames = Arrays.asList("insert*", "save*", "add*", "batchInsert*", "batchSave*", "batchAdd*");
    private final List<String> updatePathMethodNames = Arrays.asList("update*", "batchUpdate*");
    private final String TENANT_ID = "tenantId";
    private final String ID = "id";
    private final String CREATE_TIME = "createTime";
    private final String IS_DELETED = "isDeleted";
    private final String IS_AVALIABLE = "isAvailable";
    private final String VERSION_NO = "versionNo";
    private final String CREATE_USER_ID = "createUserid";
    private final String CREATE_USER_NAME = "createUsername";
    private final String CREATE_USER_IP = "createUserip";
    private final String UPDATE_USER_ID = "updateUserid";
    private final String UPDATE_USER_NAME = "updateUsername";
    private final String UPDATE_USER_IP = "updateUserip";
    private final String UPDATE_TIME = "updateTime";


    @Pointcut("execution(* com.moan.demo.all.mapper..*.*(..))")
    private void cut() {
    }


    @Before(value = "cut()")
    public void before(JoinPoint joinPoint) {
        try {
            String methodName = joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();
            for (Object obj : args) {
                if (obj instanceof Collection) {
                    for (Object o : (Collection) obj) {
                        this.setProperty(methodName, o);
                    }
                } else {
                    this.setProperty(methodName, obj);
                }
            }
        } catch (Throwable e) {
            log.error("setupDefaultValues error.", e);
        }
    }

    private void setProperty(String methodName, Object o) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (o != null) {
            if (o.getClass().getName().contains("Example")) {
                List oredCriterias = (List) PropertyUtils.getProperty(o, "oredCriteria");
                if (oredCriterias != null && oredCriterias.size() > 0) {
                    for (Object oredCriteri : oredCriterias) {
                        MethodUtils.invokeMethod(oredCriteri, "andIsDeletedEqualTo", Boolean.FALSE);// 未删除
                    }
                }
            }
            if (this.insertNameMatch(methodName)) {
                this.setInsertProperty(o);
            } else if (this.updateNameMatch(methodName)) {
                this.setUpdateProperty(o);
            }
        }
    }

    private Long getId() {
        // todo 实现seq分布式id
        return System.currentTimeMillis();
    }

    private boolean insertNameMatch(String methodName) {
        if (!this.insertPathMethodNames.isEmpty()) {
            if (CollectionUtils.isNotEmpty(insertPathMethodNames)) {
                for (String pathMethodName : insertPathMethodNames) {
                    if (pathMatcher.match(pathMethodName, methodName)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    private boolean updateNameMatch(String methodName) {
        if (!this.updatePathMethodNames.isEmpty()) {
            if (CollectionUtils.isNotEmpty(updatePathMethodNames)) {
                for (String pathMethodName : updatePathMethodNames) {
                    if (pathMatcher.match(pathMethodName, methodName)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    private void setInsertProperty(Object o) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        PropertyUtils.setProperty(o, CREATE_USER_IP, getHostIp());
        PropertyUtils.setProperty(o, UPDATE_USER_IP, getHostIp());

        if (PropertyUtils.isReadable(o, ID) && PropertyUtils.getProperty(o, ID) == null) {
            PropertyUtils.setProperty(o, ID, getId());
        }
        if (PropertyUtils.isReadable(o, TENANT_ID) && PropertyUtils.getProperty(o, TENANT_ID) == null) {
            PropertyUtils.setProperty(o, TENANT_ID, SystemContext.getTenantId());
        }
        if (PropertyUtils.isReadable(o, CREATE_TIME) && PropertyUtils.getProperty(o, CREATE_TIME) == null) {
            PropertyUtils.setProperty(o, CREATE_TIME, LocalDateTime.now());
        }
        if (PropertyUtils.isReadable(o, IS_DELETED) && PropertyUtils.getProperty(o, IS_DELETED) == null) {
            PropertyUtils.setProperty(o, IS_DELETED, Boolean.FALSE);
        }
        if (PropertyUtils.isReadable(o, VERSION_NO) && PropertyUtils.getProperty(o, VERSION_NO) == null) {
            PropertyUtils.setProperty(o, VERSION_NO, 0);
        }

    }


    private void setUpdateProperty(Object o) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        PropertyUtils.setProperty(o, UPDATE_USER_IP, getHostIp());

        if (PropertyUtils.isReadable(o, UPDATE_TIME) && PropertyUtils.getProperty(o, UPDATE_TIME) == null) {
            PropertyUtils.setProperty(o, UPDATE_TIME, LocalDateTime.now());
        }

    }

    /**
     * 获取本机ip
     */
    private String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            log.error("获取本机ip失败", e);
            return null;
        }
    }
}
