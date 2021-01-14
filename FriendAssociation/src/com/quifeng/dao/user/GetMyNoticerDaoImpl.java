package com.quifeng.dao.user;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * 获取所有关注的人DaoImpl
 * 
 * @author 梦伴
 *
 */
public class GetMyNoticerDaoImpl {
	Dao dao = new DaoImpl();

	/**
	 * @Desc 根具token获取user
	 * @param token
	 * @return
	 */
	public Map<String, Object> getUserByToken(String token) {

		String sql = "SELECT * FROM `user` as u LEFT JOIN school on u.schoolid = school.schoolid LEFT JOIN userlogin on userlogin.uid = u.uid where utoken = ?";
		Map<String, Object> data = null;
		try {
			data = dao.executeQueryForMap(sql, new int[] { Types.VARCHAR }, new Object[] { token });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * toid(uid)获取用户粉丝详细信息
	 * 
	 * @param uid
	 * @return
	 */
	public List<Map<String, Object>> getUserNoticerinfo(String uid) {
		String sql = "select uid,username,useravatar,useras from user where uid in (select toid from fixidez where formid = ? and display = 0)";
		List<Map<String, Object>> data = null;
		try {
			data = dao.executeQueryForList(sql, new int[] { Types.INTEGER }, new Object[] { uid });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

}
