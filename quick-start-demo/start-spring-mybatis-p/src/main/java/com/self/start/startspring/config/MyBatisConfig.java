package com.self.start.startspring.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.self.start.startspring.config.autosql.SimppleAutoSQLInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author szy
 * @version 1.0
 * @date 2023-02-21 14:08:55
 * @description
 */

@Configuration
@MapperScan({"com.self.start.startspring.mapper"})
public class MyBatisConfig {




    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        /* xml扫描 */
        // 奇怪的事儿  扫描mappe.xml 放到这里可以,
        // 放到properties里不行:mybatis-plus.mapper-locations=classpath:/mapper/*.xml
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:/mapper/*.xml"));

        SimppleAutoSQLInterceptor simppleAutoSQLInterceptor = new SimppleAutoSQLInterceptor();

//        Interceptor[] interceptors = {simppleAutoSQLInterceptor};
//        sqlSessionFactory.setPlugins(interceptors);


        /* 自动填充插件 */
//        globalConfig.setMetaObjectHandler(new MybatisPMetaObjectHandler());
//        sqlSessionFactory.setGlobalConfig(globalConfig);

        return sqlSessionFactory.getObject();
    }
}
