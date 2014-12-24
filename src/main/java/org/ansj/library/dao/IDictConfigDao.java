package org.ansj.library.dao;

import org.ansj.library.dao.entity.DictConfig;

public interface IDictConfigDao {
	static final String NAME_SPACE = "org.ansj.library.dao.mapper.DictConfigMapper";
	static final int VALID_STATE = 0;
	
	/**
	 * 表名查询
	 * @param tableName
	 * @return
	 */
	public DictConfig query(String tableName);
	
	/**
	 * 更新记录
	 * @param config
	 */
	public void update(DictConfig config);
	
	/**
	 * 判断指定表名是否更新
	 * @param tableName
	 * @return
	 */
	public DictConfig isUpdate(String tableName);
	
	/**
	 * 获取本机机器名
	 * @return
	 */
	public String getMachineName();
}
