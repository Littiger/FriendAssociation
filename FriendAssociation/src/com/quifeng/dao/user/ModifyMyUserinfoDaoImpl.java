package com.quifeng.dao.user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

public class ModifyMyUserinfoDaoImpl {
	Dao dao = new DaoImpl();

	/**
	 * 根据token获取user
	 * 
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
	 * 根据uid修改用户个人信息
	 * 
	 * @param uname
	 * @param usigntext
	 * @param usex
	 * @param umajor
	 * @param ubir
	 * @param ugraduate
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public int modify(String uid, String uname, String usigntext, String usex, String umajor, String ubir,
			String ugraduate) throws FileNotFoundException, IOException {
		String sql = "UPDATE user set username=?,useras=?,usersex=?,usermajor=?,userbirth=?,auth=? where uid = ?";
		try {
			dao.executeUpdate(sql,
					new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.VARCHAR, Types.INTEGER },
					new Object[] { uname, usigntext, usex, umajor, ubir, ugraduate, uid });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			return 0;
		}
		return 1;
	}
}
