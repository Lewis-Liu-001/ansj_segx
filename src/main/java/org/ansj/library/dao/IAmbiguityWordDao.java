package org.ansj.library.dao;

import java.util.List;

import org.ansj.lang.tire.domain.Value;

public interface IAmbiguityWordDao {
	static final String SENTENCE_NAMESPACE = "org.ansj.library.dao.mapper.AmbiguitySentenceMapper";
	static final String WORD_NAMESPACE = "org.ansj.library.dao.mapper.AmbiguitySentenceWordMapper";
	static final int VALID_STATE = 0;
	static final int PAGE_SIZE = 80;
	
	/**
	 * 分页查询
	 * @param start
	 * @param offset
	 * @return
	 */
	public List<Value> query(int start, int offset);
	
	/**
	 * 查询全部
	 * @return
	 */
	public List<Value> query();
	
	/**
	 * 记录总数
	 * @return
	 */
	public int count();
}
