package com.quifeng.dao.user;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * 获取浏览历史记录DaoImpl
 * 
 * @author 梦伴
 *
 */
public class GetMyUserHistoryDaoImpl {
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
	 * 根据uid查询浏览历史记录
	 * 
	 * @param uid
	 * @return
	 */
	public List<Map<String, Object>> getHistoryinfo(String uid, String page, String size) {
		//用于分页的算法
		int count1 = Integer.parseInt(size);
		int page1 = Integer.parseInt(page);

		if (page1 == 0)
			page1 = 1;
		page1 = (page1 - 1) * count1;
		
		String sql = " select * from trilha "
				+ "left join post on trilha.postid=post.postid "
				+ "left join user on user.uid=post.uid "
				+ "LEFT JOIN postinfo on postinfo.postid=trilha.postid "
				+ "where trilha.uid=? GROUP BY trilha.postid ORDER BY trilha.trilhaid desc limit ?,?";
		List<Map<String, Object>> data = null;
		try {
			data = dao.executeQueryForList(sql,
					new int[] { Types.INTEGER,  Types.INTEGER, Types.INTEGER },
					new Object[] { Integer.parseInt(uid) , page1, count1 });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

}
