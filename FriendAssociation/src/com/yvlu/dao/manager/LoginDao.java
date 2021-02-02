package com.yvlu.dao.manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @Desc 登录
 * @author 语录
 *
 */
public class LoginDao {
	
	Dao dao = new DaoImpl();
	
	/**
	 * @Desc 获取root管理员 使用name
	 * @param userName
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String, Object> getRootBy(String userName) throws ClassNotFoundException, SQLException{
		Map<String, Object> data = dao.executeQueryForMap(" SELECT * FROM  root WHERE rootname=?", new int[]{
				Types.VARCHAR
		}, new Object[]{
				userName	
		});
		return data;
	}
	
	
	/**
	 * @Desc 获取管理员的token
	 * @param rootid root的id
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String, Object> getRootLogin(String rootid) throws ClassNotFoundException, SQLException{
		Map<String, Object> data = dao.executeQueryForMap(" SELECT * FROM  rootlogin WHERE rootid=?", new int[]{
				Types.INTEGER
		}, new Object[]{
				rootid	
		});
		return data;
	}
	
	/**
	 * @Desc 更新token 在登录的时间更新
	 * @param data rootid or token
	 * @param newtoken 新的token
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public int updateRootTokenByidorToken(String data,String newtoken,String logintime) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException{
		String sql = "UPDATE rootlogin SET token=?,logintime=? WHERE rootid =? or token=?";
		int count = dao.executeUpdate(sql, new int[]{
				Types.VARCHAR,
				Types.VARCHAR,
				Types.INTEGER,
				Types.VARCHAR
		}, new Object[]{
				newtoken,logintime ,data,data
		});
		return count;
		
	}
	
	/**
	 * @Desc 增加 rootlogin 
	 * @param rootid
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public int addRootLogin(String rootid,String logintime,String rootip ,String token) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException{
		String sql = "INSERT INTO rootlogin(rootid,logintime,rootip,token) VALUES(?,?,?,?)";
		int count = dao.executeUpdate(sql, new int[]{
				Types.INTEGER,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR
		}, new Object[]{
				rootid,logintime,rootip,token
		});
		return count;
		
	}
	
}
