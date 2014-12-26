package org.ansj.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlSessionUtil {
	private static final Logger LOG = LoggerFactory.getLogger(SqlSessionUtil.class);
	private static SqlSessionFactory instance ;
	
	static{
		String resource = "mybatis-config-ansj.xml";
		InputStream in = null;
		try {
			in = Resources.getResourceAsStream(SqlSessionUtil.class.getClassLoader(), resource);
			instance = new SqlSessionFactoryBuilder().build(in);
		} catch (IOException e) {
			LOG.warn(e.getMessage());
			e.printStackTrace();
		} finally{
			if(null != in){
				try {
					in.close();
				} catch (IOException e) {
					LOG.warn(e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}
	
	public static SqlSession getSession(){
		
		return instance.openSession();
	}
	public static SqlSession getSession(boolean autoCommit){
		if(autoCommit) {
			return instance.openSession(true);
		}
		return instance.openSession();
	}
	
	public static synchronized void closeSession(SqlSession session){
		if(null != session){
			session.close();
		}
	}
}
