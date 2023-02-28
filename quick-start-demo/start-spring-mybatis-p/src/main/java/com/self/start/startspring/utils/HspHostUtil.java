package com.self.start.startspring.utils;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.self.start.startspring.config.ds.DataSourceContextHolder;
import com.self.start.startspring.config.ds.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class HspHostUtil {
    //医院代码
    private static final ThreadLocal<String> HSP_HSOT = new ThreadLocal<String>();
    //站点域名
    private static final ThreadLocal<String> WEB_DOMAIN = new ThreadLocal<String>();

    public static String getHspHost() {
        return HSP_HSOT.get();
    }

    public static void setHspHost(String hspHost) {
        HSP_HSOT.set(hspHost);
    }

    public static String getWebDomain(){
        return WEB_DOMAIN.get();
    }

    public static void setWebDomain(String domain){
        WEB_DOMAIN.set(domain);
    }

    /**
     * 跟进域名和模块切库
     * @param host
     */
    public static void switchDB(String host,String module,StringRedisTemplate redisTemplate){
        String dataSourceKey = "dynamic-slave"+"_"+host;
        Map<Object, Object> dataSourceMap = DynamicDataSource.getInstance().getDataSourceMap();
        if (!dataSourceMap.containsKey(dataSourceKey)) {
            DBInfo dbInfo = RedisUtil.getDBInfoByHostAndModule(redisTemplate,host,module);
            if (UtilTools.isEmpty(dbInfo)){
                throw new RuntimeException("暂无查到数据库信息");
            }

            DruidDataSource dynamicDataSource = new DruidDataSource();
            dynamicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dynamicDataSource.setUrl("jdbc:mysql://" + dbInfo.getDbIp() + ":" + dbInfo.getDbPort() + "/" + dbInfo.getDbName() + "?characterEncoding=utf-8&useSSL=false&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=Asia/Shanghai");
            dynamicDataSource.setUsername(dbInfo.getDbUser());
            dynamicDataSource.setPassword(dbInfo.getDbPasswd());

            dynamicDataSource.setTestOnBorrow(Boolean.parseBoolean(EnvironmentConfig.getProperty("master.datasource.testOnBorrow")));
            dynamicDataSource.setTestWhileIdle(Boolean.parseBoolean(EnvironmentConfig.getProperty("master.datasource.testWhileIdle")));
            if(StringUtils.isNotBlank(EnvironmentConfig.getProperty("master.datasource.maxWait"))){
                dynamicDataSource.setMaxWait(Integer.parseInt(EnvironmentConfig.getProperty("master.datasource.maxWait")));
            }
            if(StringUtils.isNotBlank(EnvironmentConfig.getProperty("master.datasource.maxActive"))){
                dynamicDataSource.setMaxActive(Integer.parseInt(EnvironmentConfig.getProperty("master.datasource.maxActive")));
            }
            dynamicDataSource.setValidationQuery(EnvironmentConfig.getProperty("master.datasource.validationQuery"));
            if(StringUtils.isNotBlank(EnvironmentConfig.getProperty("master.datasource.minIdle"))){
                dynamicDataSource.setMinIdle(Integer.parseInt(EnvironmentConfig.getProperty("master.datasource.minIdle")));
            }
            if(StringUtils.isNotBlank(EnvironmentConfig.getProperty("master.datasource.minIdle"))){
                dynamicDataSource.setInitialSize(Integer.parseInt(EnvironmentConfig.getProperty("master.datasource.minIdle")));
            }

            log.info("maxActive : " + EnvironmentConfig.getProperty("master.datasource.maxActive"));
            log.info("minIdle : " + EnvironmentConfig.getProperty("master.datasource.minIdle"));
            log.info("maxWait : " + EnvironmentConfig.getProperty("master.datasource.maxWait"));


//            dynamicDataSource.setBreakAfterAcquireFailure(true);//失败之后中断
//            dynamicDataSource.setConnectionErrorRetryAttempts(3);//失败后重试次数
//            dynamicDataSource.setMaxWait(3000);//超时时间
            dataSourceMap.put(dataSourceKey, dynamicDataSource);
            DynamicDataSource.getInstance().setTargetDataSources(dataSourceMap);
        }
        DruidDataSource druidDataSource = (DruidDataSource) dataSourceMap.get(dataSourceKey);
        log.info("host:{},module:{},url:{}",host,module,druidDataSource.getUrl());
        DataSourceContextHolder.setDBType(dataSourceKey);
//        if(RedisUtil.getWholeDomainMap(redisTemplate).containsKey(host)){
//            //传递域名
//            HspHostUtil.setHspHost(host);
//        } else{
//            //传递域名
//            HspHostUtil.setHspHost("xxx."+host);
//        }
        //传递域名
        HspHostUtil.setHspHost(host);
    }

    /**
     * 根据db信息切库
     * @param dbInfo
     */
    public static void switchDB(DBInfo dbInfo,StringRedisTemplate redisTemplate){
        String host = dbInfo.getHospitalCode();
        String dataSourceKey = "dynamic-slave"+"_"+host;
        Map<Object, Object> dataSourceMap = DynamicDataSource.getInstance().getDataSourceMap();
        if (!dataSourceMap.containsKey(dataSourceKey)) {
            DruidDataSource dynamicDataSource = new DruidDataSource();
            dynamicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dynamicDataSource.setUrl("jdbc:mysql://" + dbInfo.getDbIp() + ":" + dbInfo.getDbPort() + "/" + dbInfo.getDbName() + "?characterEncoding=utf-8&useSSL=false&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=Asia/Shanghai");
            dynamicDataSource.setUsername(dbInfo.getDbUser());
            dynamicDataSource.setPassword(dbInfo.getDbPasswd());
//            dynamicDataSource.setBreakAfterAcquireFailure(true);//失败之后中断
//            dynamicDataSource.setConnectionErrorRetryAttempts(3);//失败后重试次数
//            dynamicDataSource.setMaxWait(3000);//超时时间
            dataSourceMap.put(dataSourceKey, dynamicDataSource);
            DynamicDataSource.getInstance().setTargetDataSources(dataSourceMap);
        }
        DataSourceContextHolder.setDBType(dataSourceKey);
//        if(RedisUtil.getWholeDomainMap(redisTemplate).containsKey(host)){
//            //传递域名
//            HspHostUtil.setHspHost(host);
//        } else{
//            //传递域名
//            HspHostUtil.setHspHost("xxx."+host);
//        }
        //传递域名
        HspHostUtil.setHspHost(host);
    }

    /**
     * 获取登录地址
     * @param isLoginVue
     * @param websiteDomain
     * @return
     */
    public static String getLoginPath(String isLoginVue,String websiteDomain,StringRedisTemplate redisTemplate){
        String loginPath = "";
        String webInfoStr = redisTemplate.opsForValue().get("hoe:website:info:"+websiteDomain);
        if(UtilTools.isNotEmpty(webInfoStr)){
            WebsiteInfoBean websiteInfo = JSONObject.parseObject(webInfoStr,WebsiteInfoBean.class);
            if(UtilTools.isNotEmpty(isLoginVue) && "true".equals(isLoginVue)){
                loginPath = websiteInfo.getFrontDomain()+"vue/";
            }else{
                loginPath = websiteInfo.getGatewayDomain()+"common/";
            }
        }
        return loginPath;
    }

    /**
     * 过滤全域名配置获取域名
     * @param hospitalHost
     * @param stringRedisTemplate
     * @return
     */
    public static String getWholeHost(String hospitalHost,StringRedisTemplate stringRedisTemplate){
        String domainStr = stringRedisTemplate.opsForValue().get("HOE_WHOLE_DOMAIN_REDIS_KEY");
        Map<String,String> domainMap =  JSON.parseObject(domainStr,Map.class);
        if(domainMap == null || !domainMap.containsKey(hospitalHost)){
            hospitalHost = hospitalHost.substring(hospitalHost.indexOf(".") + 1);
        }
        return hospitalHost;
    }
}
