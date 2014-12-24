package org.ansj.library.dao;

import java.util.List;

import org.ansj.library.dao.entity.UserWord;
import org.apache.ibatis.session.SqlSession;

public interface IUserWordDao {
	static final String NAME_SPACE = "org.ansj.library.dao.mapper.UserWordMapper";
	static final int VALID_STATE = 0;
	
	/**
	 * 查询全部
	 * @return
	 */
	public List<UserWord> query();
	
	/**
	 * 分页查询
	 * @param start
	 * @param offset
	 * @return
	 */
	public List<UserWord> query(SqlSession session, int start, int offset);
	
	/**
	 * 记录总数
	 * @return
	 */
	public int count();
	
	/**
	 * 记录总数
	 * @param session
	 * @return
	 */
	public int count(SqlSession session);
}
