package com.self.start.startspring.config.autosql;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.tomcat.util.buf.StringUtils;

/**
 * @author szy
 * @version 1.0
 * @date 2023-02-26 16:02:29
 * @description
 */

public class ComplexAutoSQLInterceptor extends MybatisPlusInterceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        String sql = boundSql.getSql();

        // Add the constraint to the WHERE clause of the SQL statement for table1
        String table1Alias = "t1";
        String table1WhereClause = table1Alias + ".isDelete=false";
        int table1WhereIndex = sql.indexOf(table1Alias + ".") - "SELECT ".length();
        sql = new StringBuilder(sql).insert(table1WhereIndex, table1WhereClause + " AND ").toString();

        // Add the constraint to the WHERE clause of the SQL statement for table2
        String table2Alias = "t2";
        String table2WhereClause = table2Alias + ".isDelete=false";
        int table2WhereIndex = sql.indexOf(table2Alias + ".") - "SELECT ".length();
        sql = new StringBuilder(sql).insert(table2WhereIndex, table2WhereClause + " AND ").toString();

        // Set the modified SQL statement back to the BoundSql object
        BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), sql,
                boundSql.getParameterMappings(), boundSql.getParameterObject());
        MappedStatement newMappedStatement = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
        invocation.getArgs()[0] = newMappedStatement;

        return invocation.proceed();
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.keyProperty(StringUtils.join(ms.getKeyProperties()));
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.cache(ms.getCache());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    private static class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
