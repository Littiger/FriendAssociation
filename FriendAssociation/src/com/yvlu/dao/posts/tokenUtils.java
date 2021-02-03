package com.yvlu.dao.posts;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @desc   token工具类
 * @author JZH
 * @time   2021年2月3日
 */ 
public class tokenUtils {
	static Dao dao = new DaoImpl();
	
	/**
	 * 查询token
	 * @param token
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static Map<String, Object> queryToken(String token) throws ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from rootlogin where token=?",
				new int[]{
						Types.VARCHAR
				},
				new Object[]{
						token
				});
	}
}
