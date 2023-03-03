package com.self.start.startspring.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.sca.arch.application.common.util.SystemContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @author szy123111
 * @version 1.0
 * @date 2023-02-21 14:08:55
 * @description
 */

@Configuration
@MapperScan(value = {"com.self.start.startspring.mapper"}
//        ,sqlSessionFactoryRef = "mybatisSqlSessionFactory",
//        sqlSessionTemplateRef = "masterSqlSessionTemplate"
)

public class MyBatisConfig implements EnvironmentAware {

    private Environment environment;


    @Primary
    @Bean(name = "dataSourceConfig")
    public HikariConfig hikariConfig() {
        return new HikariConfig("/config/hikari.properties");
    }

    @Primary
    @Bean(name = "dataSource")
    public HikariDataSource dataSource(@Qualifier("dataSourceConfig") HikariConfig hikariConfig) {

        return new HikariDataSource(hikariConfig);
    }

//    @Primary
//    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(HikariDataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        /* xml扫描 */
        // 奇怪的事儿  扫描mappe.xml 放到这里可以,
        // 放到properties里不行:mybatis-plus.mapper-locations=classpath:/mapper/*.xml
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:/mapper/*.xml"));

//        SimppleAutoSQLInterceptor simppleAutoSQLInterceptor = new SimppleAutoSQLInterceptor();

//        Interceptor[] interceptors = {simppleAutoSQLInterceptor};
//        sqlSessionFactory.setPlugins(interceptors);


        /* 自动填充插件 */
//        globalConfig.setMetaObjectHandler(new MybatisPMetaObjectHandler());
//        sqlSessionFactory.setGlobalConfig(globalConfig);


//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
//            @Override
//            public Expression getTenantId() {
//                return new LongValue(1);
//            }
//
//            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
////            @Override
////            public boolean ignoreTable(String tableName) {
////                return "sys_user".equalsIgnoreCase(tableName);
////            }
//        }));
//        sqlSessionFactory.setPlugins(interceptor);

        return sqlSessionFactory.getObject();
    }
//
//    @Bean(name = "masterTransactionManager")
//    @Primary
//    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") HikariDataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//
//    @Bean(name = "masterSqlSessionTemplate")
//    public SqlSessionTemplate sqlSessionTemplate(
//            @Qualifier("mybatisSqlSessionFactory") SqlSessionFactory sqlSessionFactory)
//            throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    //  多租户 //////////////////////////

    /**
     * 新多租户插件配置,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存万一出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                return new LongValue(SystemContext.getTenantId());
            }

            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
//            @Override
//            public boolean ignoreTable(String tableName) {
//                return !"sys_user".equalsIgnoreCase(tableName);
//            }


        }));
        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        // 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

//    @Bean
//    public ConfigurationCustomizer configurationCustomizer() {
//        return configuration -> configuration.setUseDeprecatedExecutor(false);
//    }

}
