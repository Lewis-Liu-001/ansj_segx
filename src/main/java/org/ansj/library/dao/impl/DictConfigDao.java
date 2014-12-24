package org.ansj.library.dao.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.ansj.library.dao.IDictConfigDao;
import org.ansj.library.dao.entity.DictConfig;
import org.ansj.util.SqlSessionUtil;
import org.ansj.util.StringUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DictConfigDao implements IDictConfigDao{
	private static final Logger log = LoggerFactory.getLogger(DictConfigDao.class);
	private static String machineName;
	private SqlSession session;
	
	@Override
	public DictConfig query(String tableName) {
		DictConfig config = null;
		try {
			this.session = SqlSessionUtil.getSession(true);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("state", VALID_STATE);
			params.put("tableName", tableName);
			
			config = this.session.selectOne(NAME_SPACE + ".selectByTableName", params);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			SqlSessionUtil.closeSession(this.session);
		}
		return config;
	}

	@Override
	public void update(DictConfig config) {
		try {
			this.session = SqlSessionUtil.getSession(true);
			if(null != config && config.getIdPk() > 0) {
				this.session.update(NAME_SPACE + ".updateByPrimaryKeySelective", config);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			SqlSessionUtil.closeSession(this.session);
		}
	}

	@Override
	public DictConfig isUpdate(String tableName) {
		DictConfig config = query(tableName);
		if(null == config) {
			return null;
		}
		try {
			String machineName = getMachineName();
			String operator = config.getOperator();
			if(null == operator) {
				return config;
			}
			if(operator.indexOf(machineName) < 0) {
				return config;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} 
		return null;
	}
	
	public synchronized String getMachineName() {
		if(StringUtil.isBlank(machineName)) {
			try {
				machineName = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		return machineName;
	}
	
}
