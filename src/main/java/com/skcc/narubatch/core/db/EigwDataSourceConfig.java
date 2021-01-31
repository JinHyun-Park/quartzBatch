package com.skcc.narubatch.core.db;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(value="com.skcc.narubatch.mapper.eigwdb", sqlSessionFactoryRef="eigwSqlSessionFactory")
@EnableTransactionManagement
public class EigwDataSourceConfig {
	
	@Bean(name="eigwDataSource")
	@ConfigurationProperties(prefix="spring.eigw.datasource")
	public DataSource eigwDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name="eigwSqlSessionFactory")
	public SqlSessionFactory eigwSqlSessionFactory(@Qualifier("eigwDataSource") DataSource mainDataSource) throws Exception {
//		final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(mainDataSource);
		//sqlSessionFactoryBean.setTypeAliasesPackage("com.skcc.naruinside.user.domain.User");
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		
		sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/eigwdb/**.xml"));
		
		return sqlSessionFactoryBean.getObject();
		
	}
	
	@Bean(name="eigwSqlSessionTemplate")
	public SqlSessionTemplate eigwSqlSessionTemplate(@Qualifier("eigwSqlSessionFactory") SqlSessionFactory eigwSqlSessionFactory) throws Exception {
		final SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(eigwSqlSessionFactory);
		return sqlSessionTemplate;
	}

}
