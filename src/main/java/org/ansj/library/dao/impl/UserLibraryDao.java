package org.ansj.library.dao.impl;

import java.util.List;

import org.ansj.library.dao.IUserLibraryDao;
import org.ansj.library.dao.entity.UserLibrary;
import org.ansj.library.dao.entity.UserLibraryExample;
import org.ansj.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserLibraryDao implements IUserLibraryDao{
	private static final Logger log = LoggerFactory.getLogger(UserLibraryDao.class);
	private SqlSession session;
	
	@Override
	public List<UserLibrary> query() {
		List<UserLibrary> resultList = null;
		try {
			this.session = SqlSessionUtil.getSession(true);
			UserLibraryExample condition = new UserLibraryExample();
			UserLibraryExample.Criteria criteria = condition.createCriteria();
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

}
