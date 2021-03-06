package com.skcc.narubatch.core.db;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(value="com.skcc.narubatch.mapper.narudb", sqlSessionFactoryRef="naruSqlSessionFactory")
@EnableTransactionManagement
public class NaruDataSourceConfig {
	
	@Bean(name="naruDataSource")
	@Primary
	@ConfigurationProperties(prefix="spring.naru.datasource")
	public DataSource naruDataSource() {
		return DataSourceBuilder.create().build();
	}
	

	@Bean(name="naruSqlSessionFactory")
	@Primary
	public SqlSessionFactory naruSqlSessionFactory(@Qualifier("naruDataSource") DataSource mainDataSource) throws Exception {
		final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(mainDataSource);
		//sqlSessionFactoryBean.setTypeAliasesPackage("com.skcc.naruinside.user.domain.User");
		
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/narudb/**.xml"));
		
		return sqlSessionFactoryBean.getObject();
		
	}
	
	@Bean(name="naruSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate naruSqlSessionTemplate(@Qualifier("naruSqlSessionFactory") SqlSessionFactory naruSqlSessionFactory) throws Exception{
		final SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(naruSqlSessionFactory);
		return sqlSessionTemplate;
	}
	
	@Bean(name = "naruDataSourceTransactionManager")
	@Primary
	public DataSourceTransactionManager transactionManager (@Qualifier("naruDataSource") DataSource dataSource) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
		return transactionManager;
	}

}
