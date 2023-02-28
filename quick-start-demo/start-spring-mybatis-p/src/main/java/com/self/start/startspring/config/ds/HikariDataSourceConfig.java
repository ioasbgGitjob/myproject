package com.self.start.startspring.config.ds;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author szy
 * @version 1.0
 * @date 2023-02-21 14:01:50
 * @description
 */

@Configuration
public class HikariDataSourceConfig {

    /**
     * 创建一个数据源
     *
     * @return
     */
    @Bean("masterDataSource")
    public HikariDataSource dataSource(@Qualifier("dataSourceConfig") HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

//    /**
//     * 通过配置装配 @application.yml
//     * @return
//     */
//    @Primary
//    @Bean(name = "dataSourceConfig")
//    @ConfigurationProperties(ignoreUnknownFields = false, prefix = "spring.datasource")
//    public HikariConfig hikariConfig() {
//        return new HikariConfig();
//    }

    /**
     * 通过文件装配 HikariConfig @hikari.properties
     *
     * @return
     */
    @Bean(name = "dataSourceConfig")
    public HikariConfig hikariConfigByFile() {
        return new HikariConfig("/config/hikari.properties");
    }

}

class ChangeDataSourceDemo {

    private static final String DB_URL_PARAMETERS = "?characterEncoding=utf-8&useSSL=false&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=Asia/Shanghai";

    public void change(String dataSourceKey) {

        //1 获取数据源
        Map<Object, Object> dataSourceMaps = DynamicDataSource.getInstance().getDataSourceMap();
        if (!dataSourceMaps.containsKey(dataSourceKey)) {
            // 从缓存中获取数据库信息
            DBInfo db = new DBInfo();// todo: 从缓存中获取 dataSourceKey
            // 先读取默认的配置
            HikariConfig hikariConfig = new HikariConfig("/config/hikari.properties");
            hikariConfig.setUsername(db.getDbUser());
            hikariConfig.setPassword(db.getDbPasswd());
            hikariConfig.setJdbcUrl("jdbc:mysql://" + db.getDbIp() + ":" + db.getDbPort() + "/" + db.getDbName() + DB_URL_PARAMETERS);
            hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
            // ..... 一些其他的配置项
            dataSourceMaps.put(dataSourceKey, new HikariDataSource(hikariConfig));
            DynamicDataSource.getInstance().setTargetDataSources(dataSourceMaps);
        }
        //3 设置要使用的数据源
        DataSourceContextHolder.setDBType(dataSourceKey);
    }


    @Data
    public class DBInfo {

        private int id;
        private String dbName;
        private String dbIp;
        private int dbPort;
        private String dbUser;
        private String dbPasswd;
        private String module;
        private String hospitalHost;
        private String hospitalCode;
    }
}

