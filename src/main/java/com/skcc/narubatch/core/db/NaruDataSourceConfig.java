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
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(value="com.skcc.narubatch.mapper.narudb", sqlSessionFactoryRef="naruSqlSessionFactory")
@EnableTransactionManagement
public class NaruDataSourceConfig {
	
	@Primary
	@Bean(name="naruDataSource")
	@ConfigurationProperties(prefix="spring.naru.datasource")
	public DataSource naruDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean(name="naruSqlSessionFactory")
	public SqlSessionFactory naruSqlSessionFactory(@Qualifier("naruDataSource") DataSource mainDataSource,
			ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(mainDataSource);
		//sqlSessionFactoryBean.setTypeAliasesPackage("com.skcc.naruinside.user.domain.User");
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/narudb/**.xml"));
		return sqlSessionFactoryBean.getObject();
		
	}
	
	@Primary
	@Bean(name="naruSqlSessionTemplate")
	public SqlSessionTemplate naruSqlSessionTemplate(@Qualifier("naruSqlSessionFactory") SqlSessionFactory naruSqlSessionFactory) {
		return new SqlSessionTemplate(naruSqlSessionFactory);
	}
	

}
