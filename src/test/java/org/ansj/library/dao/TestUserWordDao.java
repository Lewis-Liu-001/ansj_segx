package org.ansj.library.dao;

import java.util.List;

import org.ansj.library.dao.entity.UserWord;
import org.ansj.library.dao.impl.UserWordDao;
import org.ansj.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

public class TestUserWordDao {
	private IUserWordDao dao;
	
	private SqlSession sqlSession;
	
	private int pagesize = 400000;
	
	@Before
	public void init() {
		dao = new UserWordDao();
		sqlSession = SqlSessionUtil.getSession(true);
	}
	
	@Test
	public void query() {
		long start = System.currentTimeMillis();
		List<UserWord> list = dao.query();
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		if(null != list && list.size() > 0) {
			System.out.println(list.size());
		}
	}
	
	@Test
	public void queryByPage() {
		long start = System.currentTimeMillis();
		int total = dao.count(this.sqlSession);
		List<UserWord> list = null;
		int sum = 0;
		if(total > pagesize) {
			int page = total / pagesize + 1;
			for(int i = 0; i < page; i++) {
				list = dao.query(sqlSession, i * pagesize, pagesize);
				System.out.println("size=" + list.size());
				sum += list.size();
			}
		} else {
			int page = total / pagesize + 1;
			for(int i = 0; i < page; i++) {
				list = dao.query(sqlSession, i * pagesize, pagesize);
				System.out.println("size=" + list.size());
				sum += list.size();
			}
		}
		System.out.println("sum = " + sum);
		SqlSessionUtil.closeSession(sqlSession);
		long end = System.currentTimeMillis();
		
		System.out.println(end - start);
		
	}
}
