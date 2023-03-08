package com.self.start.startspring.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@MapperScan(basePackages = "${mybatis.mapper-scan:com.**.mapper}")// 先从配置文件中读取，如果没有则使用默认值
public class DataSourceConfig implements EnvironmentAware {
    private Environment environment;
    static final String MAPPER_LOCATION = "classpath:mapper/**/*.xml";

    /**
     * 通过配置装配 @application.yml
     *
     * @return
     */
    @Primary
    @Bean(name = "hikariDataSourceConfig")
    @ConfigurationProperties(ignoreUnknownFields = false, prefix = "spring.datasource")
    public HikariConfig hikariConfigBySpring() {
        return new HikariConfig();
    }

    @Primary
    @Bean(name = "dataSource")
    public DynamicDataSource dynamicDataSource(HikariConfig hikariConfig) {
        DynamicDataSource dynamicDataSource = DynamicDataSource.getInstance();
        if (hikariConfig == null || StringUtils.isBlank(hikariConfig.getJdbcUrl())) {
            hikariConfig = new HikariConfig("/config/hikari.properties");
        }
        HikariDataSource mDataSource = new HikariDataSource(hikariConfig);
        dynamicDataSource.setTargetDataSources(Map.of("master", mDataSource));
        return dynamicDataSource;
    }

    @Primary
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(environment.getProperty("", MAPPER_LOCATION)));
        sqlSessionFactory.setPlugins(getPluGins());
        return sqlSessionFactory.getObject();
    }

    private Interceptor[] getPluGins() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 设置多租户
        interceptor.addInnerInterceptor(tenantLineInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        return new Interceptor[]{interceptor};
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    /**
     * 新多租户插件配置,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存万一出现问题
     */
    private TenantLineInnerInterceptor tenantLineInnerInterceptor() {
        return new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                // 这里可以从 ThreadLocal 中获取当前登录用户的租户信息
                return new LongValue("110");
            }

            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
//            @Override
//            public boolean ignoreTable(String tableName) {
//                return !"sys_user".equalsIgnoreCase(tableName);
//            }


        });
    }

}
