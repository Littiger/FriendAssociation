package com.yvlu.dao.homepage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @Desc 获取首页数据
 * @author 语录
 *
 */
public class InfoDao {

	Dao dao = new DaoImpl();
	
	/**
	 * @Desc 获取用户更具token
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String, Object> getRootByToken(String token) throws ClassNotFoundException, SQLException{
		Map<String, Object> data = dao.executeQueryForMap("SELECT * FROM `root` WHERE rootid = (SELECT rootid FROM  rootlogin WHERE token=?)", new int[]{
				Types.VARCHAR
		}, new Object[]{
				token	
		});
		return data;	
	}
	
	/**
	 * @Desc 获取用户数
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> getUserCount() throws ClassNotFoundException, SQLException{
		Map<String, Object> data = dao.executeQueryForMap(" SELECT count(*) count FROM user");
		return data;
	}
	
	/**
	 * @Desc 获取签到数
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> getCheckCount() throws ClassNotFoundException, SQLException{
		Map<String, Object> data = dao.executeQueryForMap(" SELECT count(*) count FROM `check` WHERE checkday!=0");
		return data;
	}
	
	/**
	 * @Desc 获取订单数
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> getGoodsCount() throws ClassNotFoundException, SQLException{
		Map<String, Object> data = dao.executeQueryForMap(" SELECT count(*) count FROM ordem");
		return data;
	}
	
	
	/**
	 * @Desc 待审核帖子数
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> getAuditsCount() throws ClassNotFoundException, SQLException{
		Map<String, Object> data = dao.executeQueryForMap("SELECT count(*) count FROM postinfo WHERE isexamina=0");
		return data;
	}
	
	/**
	 * @Desc 帖子数
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> getPostsCount() throws ClassNotFoundException, SQLException{
		Map<String, Object> data = dao.executeQueryForMap("SELECT count(*) count FROM post");
		return data;
	}
	
	/**
	 * @Desc 获取前5个日志
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Map<String,Object>> getAllShop() throws ClassNotFoundException, SQLException{	
		List<Map<String, Object>> data = dao.executeQueryForList("SELECT * FROM userlog ORDER BY userlogid  DESC LIMIT 5");
		return data;
	}
	
	
	
	/**
	 * @Desc 更新token 不更新时间  每次操作后更新
	 * @param data rootid or token
	 * @param newtoken 新的 token
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public int updateRootTokenByidorToken(String token,String newtoken) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException{
		String sql = "UPDATE rootlogin SET token=? WHERE token=?";
		int count = dao.executeUpdate(sql, new int[]{
				Types.VARCHAR,
				Types.VARCHAR
		}, new Object[]{
				newtoken,token
		});
		return count;
		
	}
	
	
}
