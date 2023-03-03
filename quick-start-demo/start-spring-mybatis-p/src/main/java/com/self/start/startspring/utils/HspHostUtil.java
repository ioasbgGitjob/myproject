//package com.self.start.startspring.utils;
//
//
//import com.self.start.startspring.config.ds.DataSourceContextHolder;
//import com.self.start.startspring.config.ds.DynamicDataSource;
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//public class HspHostUtil {
//
//    private static final String DB_URL_STR = "jdbc:mysql://%s:%s/%s?characterEncoding=utf-8&useSSL=false&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
//
//    //医院代码
//    private static final ThreadLocal<String> HSP_HSOT = new ThreadLocal<String>();
//    //站点域名
//    private static final ThreadLocal<String> WEB_DOMAIN = new ThreadLocal<String>();
//
//    public static String getHspHost() {
//        return HSP_HSOT.get();
//    }
//
//    public static void setHspHost(String hspHost) {
//        HSP_HSOT.set(hspHost);
//    }
//
//    public static String getWebDomain() {
//        return WEB_DOMAIN.get();
//    }
//
//    public static void setWebDomain(String domain) {
//        WEB_DOMAIN.set(domain);
//    }
//
//    /**
//     * 跟进域名和模块切库
//     *
//     * @param host
//     */
//    public static void switchDB(String host, String module, Object redisTemplate) {
//        String dataSourceKey = "dynamic-slave" + "_" + host;
//        Map<Object, Object> dataSourceMap = DynamicDataSource.getInstance().getDataSourceMap();
//        if (!dataSourceMap.containsKey(dataSourceKey)) {
////            DBInfo dbInfo = redis .get "hoe:hospital:dbInfo:" + host + "_" + module;
//            DBInfo dbInfo = getDbInfo();
//            if (null == dbInfo) {
//                throw new RuntimeException("暂无查到数据库信息");
//            }
//
//            HikariConfig hikariConfig = new HikariConfig("/config/hikari.properties");
//            hikariConfig.setUsername(dbInfo.getDbUser());
//            hikariConfig.setPassword(dbInfo.getDbPasswd());
//            hikariConfig.setJdbcUrl(DB_URL_STR.formatted(dbInfo.getDbIp(), dbInfo.getDbPort(), dbInfo.getDbName()));
//            hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
//            dataSourceMap.put(dataSourceKey, new HikariDataSource(hikariConfig));
//            DynamicDataSource.getInstance().setTargetDataSources(dataSourceMap);
//        }
//        HikariDataSource druidDataSource = (HikariDataSource) dataSourceMap.get(dataSourceKey);
//        log.info("host:{},module:{},url:{}", host, module, druidDataSource.getJdbcUrl());
//        DataSourceContextHolder.setDBType(dataSourceKey);
//
//        HspHostUtil.setHspHost(host);
//    }
//
//    private static DBInfo getDbInfo() {
//        DBInfo dbInfo = new DBInfo();
//        DriverManagerDataSource dataSourc1e = new DriverManagerDataSource();
//        dataSourc1e.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSourc1e.setUrl("jdbc:mysql://192.168.1.234:3306/hoe_hospital?useUnicode=true&characterEncoding=UTF8&useSSL=false&serverTimezone=UTC");
//        dataSourc1e.setUsername("root");
//        dataSourc1e.setPassword("MDBlZDc2M2I3NDA2");
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourc1e);
//        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("SELECT * FROM hoe_hospital.db_info WHERE module = 'common' and hospital_code = 'TEST006' ");
//        for (Map<String, Object> m : mapList) {
//            dbInfo.setDbIp(String.valueOf((m.get("db_ip").toString())));
//            dbInfo.setDbName(m.get("db_name") + "");
//            dbInfo.setDbPort(Integer.parseInt(m.get("db_port").toString()));
//            dbInfo.setDbPasswd(m.get("db_passwd") + "");
//            dbInfo.setDbUser(m.get("db_user") + "");
//        }
//        return dbInfo;
//    }
//
//    /**
//     * 根据db信息切库
//     *
//     * @param dbInfo
//     */
//    public static void switchDB(DBInfo dbInfo) {
//        String host = dbInfo.getHospitalCode();
//        String dataSourceKey = "dynamic-slave" + "_" + host;
//        Map<Object, Object> dataSourceMap = DynamicDataSource.getInstance().getDataSourceMap();
//        if (!dataSourceMap.containsKey(dataSourceKey)) {
//            HikariConfig hikariConfig = new HikariConfig("/config/hikari.properties");
//            hikariConfig.setUsername(dbInfo.getDbUser());
//            hikariConfig.setPassword(dbInfo.getDbPasswd());
//            hikariConfig.setJdbcUrl(DB_URL_STR.formatted(dbInfo.getDbIp(), dbInfo.getDbPort(), dbInfo.getDbName()));
//            hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
//            dataSourceMap.put(dataSourceKey, new HikariDataSource(hikariConfig));
//            DynamicDataSource.getInstance().setTargetDataSources(dataSourceMap);
//        }
//        DataSourceContextHolder.setDBType(dataSourceKey);
//        //传递域名
//        HspHostUtil.setHspHost(host);
//    }
//
//    /**
//     * 获取登录地址
//     *
//     * @param isLoginVue
//     * @param websiteDomain
//     * @return
//     */
//    public static String getLoginPath(String isLoginVue, String websiteDomain, Object redisTemplate) {
//        String loginPath = "";
////        String webInfoStr = redisTemplate.opsForValue().get("hoe:website:info:" + websiteDomain);
////        String webInfoStr = redisTemplate.opsForValue().get("hoe:website:info:" + websiteDomain);
////        if (UtilTools.isNotEmpty(webInfoStr)) {
////            WebsiteInfoBean websiteInfo = JSONObject.parseObject(webInfoStr, WebsiteInfoBean.class);
////            if (UtilTools.isNotEmpty(isLoginVue) && "true".equals(isLoginVue)) {
////                loginPath = websiteInfo.getFrontDomain() + "vue/";
////            } else {
////                loginPath = websiteInfo.getGatewayDomain() + "common/";
////            }
////        }
//        return loginPath;
//    }
//
//    /**
//     * 过滤全域名配置获取域名
//     *
//     * @param hospitalHost
//     * @param stringRedisTemplate
//     * @return
//     */
//    public static String getWholeHost(String hospitalHost, Object stringRedisTemplate) {
////        String domainStr = stringRedisTemplate.opsForValue().get("HOE_WHOLE_DOMAIN_REDIS_KEY");
////        Map<String, String> domainMap = JSON.parseObject(domainStr, Map.class);
////        if (domainMap == null || !domainMap.containsKey(hospitalHost)) {
////            hospitalHost = hospitalHost.substring(hospitalHost.indexOf(".") + 1);
////        }
//        return hospitalHost;
//    }
//
//    @Data
//    public static class DBInfo {
//
//        private int id;
//        private String dbName;
//        private String dbIp;
//        private int dbPort;
//        private String dbUser;
//        private String dbPasswd;
//        private String module;
//        private String hospitalHost;
//        private String hospitalCode;
//    }
//}
