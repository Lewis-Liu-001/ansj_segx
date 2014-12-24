package org.ansj.library.dao;

import java.util.List;

import org.ansj.library.dao.entity.UserLibrary;

public interface IUserLibraryDao {
	static final String NAME_SPACE = "org.ansj.library.dao.mapper.UserLibraryMapper";
	static final int VALID_STATE = 0;
	
	/**
	 * 查询全部
	 * @return
	 */
	public List<UserLibrary> query();
}
