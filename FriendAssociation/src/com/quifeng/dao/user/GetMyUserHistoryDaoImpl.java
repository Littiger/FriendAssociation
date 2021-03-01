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
		
		String sql = "SELECT\n" + "	* \n" + "FROM\n" + "	post\n"
				+ "	LEFT JOIN (SELECT postid AS historyid, createtime AS historytime FROM trilha WHERE uid = ? ) AS h ON h.historyid = post.postid \n"
				+ "	LEFT JOIN (select username,useravatar,uid as user_uid from `user`) as u on u.user_uid = post.uid\n"
				+ "	LEFT JOIN (select placaid as postbk_placaid,placaname from postbk) as bk on bk.postbk_placaid = post.placaid\n"
				+ "	LEFT JOIN (select * from zan where postid in (select postid from trilha where uid = ? ) and uid = ? and display = 0 GROUP BY postid) as z on z.postid = post.postid\n"
				+ "	LEFT JOIN (select postid,postzan,postshare,postos from postinfo) as p on p.postid = post.postid\n"
				+ "WHERE\n" + "	h.historyid IS NOT NULL order by historytime DESC limit ?,?;";
		List<Map<String, Object>> data = null;
		try {
			data = dao.executeQueryForList(sql,
					new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER },
					new Object[] { uid, uid, uid, page1, count1 });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

}
