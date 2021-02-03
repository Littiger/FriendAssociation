package com.yvlu.dao.circle;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @desc   圈子信息Dao层
 * @author JZH
 * @time   2021年2月3日
 */
public class circleDao {
	Dao dao = new DaoImpl();

	/**
	 * 获取所有圈子信息 去除广告板块
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Map<String, Object>> queryAllCircle() throws ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select * from postbk where placaid!=0");
	}
	
	
}
