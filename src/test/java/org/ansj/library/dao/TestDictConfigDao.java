package org.ansj.library.dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.ansj.library.dao.entity.DictConfig;
import org.ansj.library.dao.impl.DictConfigDao;
import org.ansj.util.StringUtil;
import org.junit.Before;
import org.junit.Test;

public class TestDictConfigDao {
	private IDictConfigDao dao;
	
	@Before
	public void init() {
		dao = new DictConfigDao();
	}
	
	@Test
	public void testMachineName() throws UnknownHostException {
		String str = InetAddress.getLocalHost().getHostName();
		System.out.println(str);
	}
	
	@Test
	public void query() {
		String tableName = "t_analyze_ansj_user_library";
		DictConfig config = dao.isUpdate(tableName);
		if(null != config) {
			System.out.println(config.getTableName() + ", " + config.getIsUpdate() + ", " + config.getOperator());
			String operator = config.getOperator();
			if(StringUtil.isBlank(operator)) {
				config.setOperator(dao.getMachineName());
				config.setIsUpdate(config.getIsUpdate() + 1);
			} else {
				operator = operator + "," + dao.getMachineName();
				String[] operators = operator.split(",");
				config.setOperator(operator);
				if(config.getNodeNumber() == operators.length) {
					config.setIsUpdate(0);
				} else {
					config.setIsUpdate(config.getIsUpdate() + 1);
				}
			}
			System.out.println(config.getTableName() + ", " + config.getIsUpdate() + ", " + config.getOperator());
			config.setUpdateTime(new Date());
			dao.update(config);
		}
	}
}
