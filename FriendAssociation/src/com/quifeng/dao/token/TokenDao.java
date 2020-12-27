package com.quifeng.dao.token;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @Desc token验证、修改
 * @author 语录
 *
 */
public class TokenDao {
	
	Dao dao =new DaoImpl();
	
	/**
	 * @Desc 查询token
	 * @param token
	 * @return
	 */
	public Map<String, Object> queryToken(String token){
		
		String sql = "SELECT  * FROM userlogin WHERE utoken=?";
		Map<String, Object> data=null;
		try {
			data = dao.executeQueryForMap(sql , new int[]{
					Types.VARCHAR
			}, new Object[]{
					token
			});
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	
	/**
	 * @Desc  查询uid 根据token
	 * @param token
	 * @return
	 */
	public String queryUidByToken(String token){
		String sql = "SELECT  * FROM userlogin WHERE utoken=?";
		Map<String, Object> data=null;
		try {
			data = dao.executeQueryForMap(sql , new int[]{
					Types.VARCHAR
			}, new Object[]{
					token
			});
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return data.get("uid").toString();
	}
	
	/**
	 * @Desc 增加token
	 * @param uid
	 * @param utoken
	 * @param logintime
	 * @param userip
	 * @param lasttime
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public int addToken(String uid, String utoken,String logintime,String userip,String lasttime ) {
		String sql="INSERT INTO userlogin(uid,utoken,logintime,userip,lasttime) VALUES (?,?,?,?,?)";
		int count=0;
		try {
			count = dao.executeUpdate(sql, new int[]{
					Types.INTEGER,Types.VARCHAR,Types.VARBINARY,Types.VARCHAR,Types.VARCHAR
			}, new Object[]{
					uid,utoken,logintime,userip,lasttime
			});
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	};
	
	
	/**
	 * @Desc 更新token
	 * @param utoen
	 * @param nuoken
	 * @return
	 */
	public int updateToken(String utoen,String nuoken){
		int count =0;
		String sql="UPDATE userlogin SET utoken=? WHERE utoken=? ";
		try {
			count =dao.executeUpdate(sql, new int[]{	
					Types .VARCHAR,Types.VARCHAR
			}, new Object[]{
					nuoken,utoen
			});
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
	
	
	
	
}
