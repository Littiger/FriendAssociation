package com.quifeng.dao.user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * 删除我的发帖DaoImpl
 * 
 * @author 梦伴
 *
 */
public class DeleteMyPostDaoImpl {

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
	 * @Desc 根具uid postid 删除帖子
	 * @param token
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public int deletePost(String uid, String postid) throws FileNotFoundException, IOException {

		String sql = "update post set display = 1 where uid = ? and postid = ?";
		int result = 0;
		try {
			result = dao.executeUpdate(sql, new int[] { Types.INTEGER, Types.INTEGER }, new Object[] { uid, postid });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
