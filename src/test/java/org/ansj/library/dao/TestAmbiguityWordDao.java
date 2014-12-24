package org.ansj.library.dao;

import java.util.List;

import org.ansj.lang.tire.domain.Value;
import org.ansj.library.dao.impl.AmbiguityWordDao;
import org.junit.Before;
import org.junit.Test;

public class TestAmbiguityWordDao {
	private IAmbiguityWordDao dao;
	
	@Before
	public void init() {
		this.dao = new AmbiguityWordDao();
	}
	
	@Test
	public void testQuery() {
		List<Value> list = this.dao.query();
		if(null != list && list.size() > 0) {
			for(Value value : list) {
				System.out.println(value);
			}
		}
	}
	
	@Test
	public void testQueryByPage() {
		List<Value> list = this.dao.query(0, 80);
		if(null != list && list.size() > 0) {
			for(Value value : list) {
				System.out.println(value);
			}
		}
	}
}
