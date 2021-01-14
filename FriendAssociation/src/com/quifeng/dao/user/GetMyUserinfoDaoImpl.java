package com.quifeng.dao.user;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * 获取个人信息DaoImpl
 * 
 * @author 梦伴
 *
 */
public class GetMyUserinfoDaoImpl {
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
	 * uid获取用户发了多少帖子
	 * 
	 * @param uid
	 * @return
	 */
	public String getUserPostCount(String uid) {
		String sql = "select count(*) as postCount from post where uid = ? and display = 0";
		Map<String, Object> data = null;
		try {
			data = dao.executeQueryForMap(sql, new int[] { Types.INTEGER }, new Object[] { uid });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data.get("postCount").toString();
	}

	/**
	 * toid(uid)获取用户有多少粉丝
	 * 
	 * @param uid
	 * @return
	 */
	public String getUserFans(String uid) {
		String sql = "select count(*) as fansCount from fixidez where toid = ? and display = 0";
		Map<String, Object> data = null;
		try {
			data = dao.executeQueryForMap(sql, new int[] { Types.INTEGER }, new Object[] { uid });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data.get("fansCount").toString();
	}

	/**
	 * fromid(uid)获取用户关注了多少人
	 * 
	 * @param uid
	 * @return
	 */
	public String getUserConcernCount(String uid) {
		String sql = "select count(*) as concernCount from fixidez where formid = ? and display = 0";
		Map<String, Object> data = null;
		try {
			data = dao.executeQueryForMap(sql, new int[] { Types.INTEGER }, new Object[] { uid });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data.get("concernCount").toString();
	}

	/**
	 * uid获取用户有多少条聊天记录
	 * 
	 * @param uid
	 * @return
	 */
	public String getUserHistoryCount(String uid) {
		String sql = "select count(*) as historyCount from trilha where uid = ? and display = 0";
		Map<String, Object> data = null;
		try {
			data = dao.executeQueryForMap(sql, new int[] { Types.INTEGER }, new Object[] { uid });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data.get("historyCount").toString();
	}
}
