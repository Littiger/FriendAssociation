package com.quifeng.dao.user;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

public class UserDao {
	Dao dao =new DaoImpl();
	/**
	 * @Desc 查询用户根据User
	 * @param phone
	 * @return
	 */
	public Map<String, Object> getUserByPhone(String phone){
		String sql ="SELECT * FROM user WHERE  userphone=? or email=? ";
		Map<String, Object> data =null;
		try {
			data=dao.executeQueryForMap(sql, new int[]{
					Types.VARCHAR,
					Types.VARCHAR
			}, new Object[]{
					phone,phone
			});
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
		
	}
	
	
	
	
	/**
	 * @Desc 根具token获取user
	 * @param token
	 * @return
	 */
	public Map<String, Object> getUserByToken(String token){
		
		String sql =" SELECT * FROM `user` WHERE uid=( SELECT	uid	FROM userlogin  WHERE utoken=?) ";
		Map<String, Object> data =null;
		try {
			data=dao.executeQueryForMap(sql, new int[]{
					Types.VARCHAR
			}, new Object[]{
					token
			});
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	
	/**
	 * @Desc 验证登录
	 * @param user
	 * @return
	 */
	public Map<String, Object> getUserByU(String user){
		String sql =" SELECT *	 FROM	 `user` WHERE userphone=?  or email=? ";
		Map<String, Object> data = null;	
		try {
			data=dao.executeQueryForMap(sql,new int[]{
					Types.VARCHAR,
					Types.VARCHAR
			},new Object[]{
					user,user
			});
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;	
	}
	
	
}
