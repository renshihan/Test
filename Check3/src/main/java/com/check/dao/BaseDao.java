package com.check.dao;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
public class BaseDao extends SqlSessionDaoSupport{
	@Resource(name="sqlSessionFactory")
	private SqlSessionFactory sqlSessionFactory;
	//在bean创建之前初始化
	@PostConstruct
	public void SqlSessionFactory(){
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
}
