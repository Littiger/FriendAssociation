package com.quifeng.dao.login;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import com.ndktools.javamd5.Mademd5;
import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @Desc 登录的查询
 * @author 语录
 *
 */
public class LoginDao {

	Dao dao = new DaoImpl();

	/**
	 * @Desc 第一次注册  对应接口 http：//127.0.0.1/api/user/sign  注册完成后用户状态为4(注册没有验证)  对用户密码经行加密
	 * @param phone
	 * @param userName
	 * @param userpwd
	 * @return
	 */
	public int addUser(String phone,String userName,String userpwd){
		int count =0;
		//对密码经行加密
		userpwd=new Mademd5().toMd5(userpwd);
		String sql ="INSERT INTO user(userphone,username, password,userzt) VALUES (?,?,?,4)";
		try {
			count = dao.executeUpdate(sql, new int[]{
					Types.VARCHAR,Types.VARCHAR,Types.VARCHAR
			}, new Object[]{
					phone,userName,userpwd
			});
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	
	/**
	 * @Desc 验证手机号
	 * @param phone
	 * @return
	 */
	public Map<String, Object> isPhone(String phone){		
		Map<String, Object> data = null;
		
		String sql = "SELECT *	 FROM	 `user` WHERE userphone=?";
		try {
			data = dao.executeQueryForMap(sql , new int[]{
					Types.VARCHAR
			}, new Object[]{
				phone	
			});
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return data;
	}
	
	
}
