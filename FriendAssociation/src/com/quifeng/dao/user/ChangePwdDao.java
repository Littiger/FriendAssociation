package com.quifeng.dao.user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @desc   修改密码dao层
 * @author JZH
 *
 */
public class ChangePwdDao {
	Dao dao = new DaoImpl();
	
	/**
	 * 根据手机号查询用户
	 * @param phone
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String, Object> queryUserByPhone(String phone) throws ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from user where userphone=?",new int[]{Types.VARCHAR},new Object[]{phone});
	}
	
	/**
	 * 根据uid查询用户是否在黑名单
	 * @param uid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String, Object> queryBlackUserByUid(String uid) throws ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from blackname where uid=?",new int[]{Types.INTEGER},new Object[]{uid});
	}
	
	/**
	 * 修改密码
	 * @param phone
	 * @param newPwd
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public int updateUserPwd(String phone, String newPwd) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("update user set password=? where userphone=?",new int[]{Types.VARCHAR,Types.VARCHAR}, new Object[]{newPwd,phone});
	}
	
}
