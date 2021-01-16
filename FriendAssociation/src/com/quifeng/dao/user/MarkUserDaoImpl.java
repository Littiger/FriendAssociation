package com.quifeng.dao.user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * 关注用户DaoImpl
 * 
 * @author 梦伴
 *
 */
public class MarkUserDaoImpl {
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
	 * @Desc 根具uid获取user
	 * @param token
	 * @return
	 */
	public Map<String, Object> getUserByUid(String userid) {
		// TODO Auto-generated method stub
		String sql = "select * from `user` where uid = ?";
		Map<String, Object> data = null;
		try {
			data = dao.executeQueryForMap(sql, new int[] { Types.VARCHAR }, new Object[] { userid });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 获取两个用户的关注状态
	 * 
	 * @param string
	 * @param userid
	 */
	public Map<String, Object> getEachStatus(String userid, String useredid) {
		// TODO Auto-generated method stub
		String sql = "select * from fixidez where formid = ? and toid = ?";
		Map<String, Object> data = null;
		try {
			data = dao.executeQueryForMap(sql, new int[] { Types.VARCHAR, Types.VARCHAR },
					new Object[] { userid, useredid });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 关注用户
	 * 
	 * @param string
	 * @param userid
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public int markUser(String userid, String useredid) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO fixidez VALUES(0,?,?,?,?)";
		int data = 0;
		try {
			data = dao.executeUpdate(sql, new int[] { Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR },
					new Object[] { userid, useredid, 0, new Date().getTime() });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 取消关注/重新关注
	 * 
	 * @param string
	 * @param userid
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public int cancel(String userid, String useredid, String status) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		String sql = "UPDATE fixidez SET display = ?,createtime = ? WHERE formid = ? and toid = ?";
		int data = 0;
		try {
			data = dao.executeUpdate(sql, new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER},
					new Object[] { status, new Date().getTime(), userid, useredid });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

}
