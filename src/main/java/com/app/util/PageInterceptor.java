package com.app.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Intercepts({@Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class})} )  
public class PageInterceptor implements Interceptor {
	private static Logger logger = LoggerFactory.getLogger(PageInterceptor.class);

	private String databaseType;
	
	public Object intercept(Invocation invocation) throws Throwable {
		final RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        final StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");  
		final BoundSql boundSql = delegate.getBoundSql();
        final Object obj = boundSql.getParameterObject();

        //如果参数类型为自定义的PageUtil类型则执行分页处理
        if (obj instanceof PageUtil) {
            final PageUtil page = (PageUtil) obj;
            logger.info(":::::::::"+page.toString());
            MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");  
            Connection connection = (Connection) invocation.getArgs()[0];
            final String sql = boundSql.getSql();//获取原sql
            final String pageSql = this.getPageSql(page, sql);//转换为分页SQL
           // logger.info(":::::::::"+pageSql);
            //获取总记录数
            int rowCount = getRowCount(page, mappedStatement, connection);
            page.setTotal(rowCount);
            ReflectUtil.setFieldValue(boundSql, "sql", pageSql);  
        }
        return invocation.proceed();
	}

	private String getCountSql(String sql) {
		return "select count(*) from (" + sql+") a";
	}
	
	/**
	 * 生成分页数据sql
	 * @param page
	 * @param sql
	 * @return
	 */
	private String getPageSql(PageUtil page, String sql) {
        final StringBuffer sqlBuffer = new StringBuffer(sql);
        if ("mysql".equalsIgnoreCase(databaseType.trim())) {
            return getMysqlPageSql(page, sqlBuffer);
        } else if ("oracle".equalsIgnoreCase(databaseType.trim())) {
            return getOraclePageSql(page, sqlBuffer);
        }
        return sqlBuffer.toString();
    }
	
	/**
	 * 生产mysql数据分页sql
	 * @param page
	 * @param sqlBuffer
	 * @return
	 */
	private String getMysqlPageSql(PageUtil page, StringBuffer sqlBuffer) {
		int offset = (page.getPage() - 1) * page.getPageSize() ;
        sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
        return sqlBuffer.toString();
    }
	

    /**
     * 获取Oracle数据库的分页查询语句
     */
    private String getOraclePageSql(PageUtil page, StringBuffer sqlBuffer) {
    	//System.out.println(page);
        int offset = (page.getPage() - 1) * page.getPageSize() + 1;
       // System.out.println(offset);
        sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ")
                .append(offset + page.getPageSize());
        sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);
        return sqlBuffer.toString();
    }
    
	/**
	 * 获取总数据量
	 * @param page
	 * @param mappedStatement
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	private int getRowCount(PageUtil page, MappedStatement mappedStatement, Connection connection) throws SQLException {
		int totalRecord = 0;
		BoundSql boundSql = mappedStatement.getBoundSql(page);
		String sql = boundSql.getSql();
		String countSql = this.getCountSql(sql);
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page);
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, countBoundSql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		   pstmt = connection.prepareStatement(countSql);
		   parameterHandler.setParameters(pstmt);
		   rs = pstmt.executeQuery();
		   if (rs.next()) {
			   totalRecord = rs.getInt(1);
		   }
		}  finally {
		   try {
		      if (rs != null)
		          rs.close();
		       
		   } finally{
			   if (pstmt != null)
		        pstmt.close();
		   }
		}
		return totalRecord;
    }
	
    
    public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}
    
    
	public void setProperties(Properties properties) {
		this.databaseType = properties.getProperty("databaseType");
	}
}

