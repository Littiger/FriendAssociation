package com.quifeng.dao.user;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @Desc 对用户状态的修改
 * @author 语录
 *
 */
public class UserStateDao {

	Dao dao = new DaoImpl();

	/**
	 * 传入的是user的token 或者 uId 或 手机号
	 */
	public int updateUserState(String user, String type) {
		int count = 0;
		try {
			count = dao.executeUpdate("UPDATE `user` SET userzt=? WHERE uid=?  or userphone=?",
					new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, }, new Object[] { type, user, user });
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

}
