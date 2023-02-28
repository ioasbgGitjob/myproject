package com.self.start.startspring.config.autosql;

/**
 * @author szy
 * @version 1.0
 * @date 2023-02-26 17:54:06
 * @description
 */

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.springframework.util.ReflectionUtils;

import java.sql.Connection;

public class CustomMybatisPlusInterceptor extends MybatisPlusInterceptor {

}
