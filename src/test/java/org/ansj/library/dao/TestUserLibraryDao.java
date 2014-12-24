package org.ansj.library.dao;

import java.util.List;

import org.ansj.library.dao.entity.UserLibrary;
import org.ansj.library.dao.impl.UserLibraryDao;
import org.junit.Before;
import org.junit.Test;

public class TestUserLibraryDao {
	private IUserLibraryDao dao;
	
	@Before
	public void init() {
		this.dao = new UserLibraryDao();
	}
	
	@Test
	public void testQuery() {
		List<UserLibrary> list = this.dao.query();
		for(UserLibrary ul : list) {
			System.out.println(ul.getKeyword() + ", " + ul.getSpeech() + ", " + ul.getWordFrequency());
		}
	}
}
