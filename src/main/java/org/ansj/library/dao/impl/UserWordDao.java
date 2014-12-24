package org.ansj.library.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ansj.library.dao.IUserWordDao;
import org.ansj.library.dao.entity.UserWord;
import org.ansj.library.dao.entity.UserWordExample;
import org.ansj.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserWordDao implements IUserWordDao {
	private static final Logger log = LoggerFactory.getLogger(UserWordDao.class);
	private SqlSession session;
	
	@Override
	public List<UserWord> query() {
		List<UserWord> resultList = null;
		try {
			this.session = SqlSessionUtil.getSession(true);
			UserWordExample condition = new UserWordExample();
			UserWordExample.Criteria criteria = condition.createCriteria();
			criteria.andStateEqualTo(VALID_STATE);
			resultList = this.session.selectList(NAME_SPACE + ".selectByExample", condition);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			SqlSessionUtil.closeSession(this.session);
		}
		return resultList;
	}

	@Override
	public List<UserWord> query(SqlSession session, int start, int offset) {
		List<UserWord> resultList = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("state", VALID_STATE);
			params.put("start", start);
			params.put("offset", offset);
			
			resultList = session.selectList(NAME_SPACE + ".selectByPage", params);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return resultList;
	}

	@Override
	public int count(SqlSession session) {
		int result = 0;
		try {
			UserWordExample condition = new UserWordExample();
			UserWordExample.Criteria criteria = condition.createCriteria();
			criteria.andStateEqualTo(VALID_STATE);
			result = session.selectOne(NAME_SPACE + ".countByExample", condition);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public int count() {
		int result = 0;
		try {
			this.session = SqlSessionUtil.getSession(true);
			UserWordExample condition = new UserWordExample();
			UserWordExample.Criteria criteria = condition.createCriteria();
			criteria.andStateEqualTo(VALID_STATE);
			result = this.session.selectOne(NAME_SPACE + ".countByExample", condition);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			SqlSessionUtil.closeSession(this.session);
		}
		return result;
	}

}
