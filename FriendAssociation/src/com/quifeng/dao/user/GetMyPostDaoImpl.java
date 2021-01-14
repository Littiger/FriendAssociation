package com.quifeng.dao.user;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * 获取我的发帖DaoImpl
 * 
 * @author 梦伴
 *
 */
public class GetMyPostDaoImpl {

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
	 * 获取全部赞的数量
	 * 
	 * @param userid
	 * @return
	 */
	public Map<String, Object> getUserZanSumBy(String userid) {
		// TODO Auto-generated method stub
		Map<String, Object> data = null;

		String sql = "SELECT count(*) count	FROM ZAN WHERE postid in (SELECT postid FROM post WHERE uid=? AND display=0)";
		try {
			data = dao.executeQueryForMap(sql, new int[] { Types.INTEGER }, new Object[] { userid });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

		return data;

	}

	/**
	 * 获取粉丝数
	 * 
	 * @param userid
	 * @return
	 */
	public Map<String, Object> getUserFixidezCountById(String userid) {
		// TODO Auto-generated method stub
		Map<String, Object> data = null;

		String sql = "SELECT  count(*) count FROM fixidez  WHERE  formid = ?  And display = 0";
		try {
			data = dao.executeQueryForMap(sql, new int[] { Types.INTEGER }, new Object[] { userid });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

		return data;
	}

	/**
	 * 根据uid查询用户发帖信息
	 * 
	 * @param uid
	 * @return
	 */
	public List<Map<String, Object>> getMyPost(String uid, String page, String size) {

		String sql = "SELECT\n" + "	*\n" + "FROM\n" + "	post\n"
				+ "	LEFT JOIN (SELECT postid AS historyid, createtime AS historytime FROM trilha WHERE uid = ? ) AS h ON h.historyid = post.postid \n"
				+ "	LEFT JOIN (select username,useravatar,uid as user_uid from `user`) as u on u.user_uid = post.uid\n"
				+ "	LEFT JOIN (select placaid as postbk_placaid,placaname from postbk) as bk on bk.postbk_placaid = post.placaid\n"
				+ "	LEFT JOIN (select * from zan where postid in (select postid from trilha where uid = ? ) and uid = ? and display = 0 GROUP BY postid) as z on z.postid = post.postid\n"
				+ "	LEFT JOIN (select postid,postzan,postshare,postos from postinfo) as p on p.postid = post.postid\n"
				+ "WHERE\n" + "	post.uid = ? order by createtime DESC limit ?,?;";
		List<Map<String, Object>> data = null;
		try {
			data = dao.executeQueryForList(sql, new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
					Types.INTEGER, Types.INTEGER }, new Object[] { uid, uid, uid, uid, page, size });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
}
