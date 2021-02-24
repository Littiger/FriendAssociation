package com.yvlu.dao.user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @desc   用户管理dao
 * @author JZH
 * @time   2021年2月3日
 */
public class userDao {
	Dao dao = new DaoImpl();
	
	/**
	 * 获取所有用户
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Map<String, Object>> getAllUser() throws ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select * from user "
				+ "LEFT JOIN (select toid,count(*) fcount from fixidez GROUP BY toid) fans on user.uid=fans.toid "
				+ "LEFT JOIN(select formid,count(*) ccount from fixidez GROUP BY formid) con on user.uid=con.formid "
				+ "LEFT JOIN (select uid,count(*) tcount from trilha GROUP BY uid) history on user.uid=history.uid "
				+ "LEFT JOIN (select uid,count(*) pcount from post GROUP BY uid) post on user.uid=post.uid "
				+ "LEFT JOIN school on user.schoolid=school.schoolid");
	}
	
	/**
	 * 模糊查询用户
	 * @param word
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Map<String, Object>> getPart(String word) throws ClassNotFoundException, SQLException {
		if(word == null || word.equals("")){
			word = "%%";
		}
		else {
			word = "%"+word+"%";
		}
		
		return dao.executeQueryForList("select * from user "
				+ "LEFT JOIN (select toid,count(*) fcount from fixidez GROUP BY toid) fans on user.uid=fans.toid "
				+ "LEFT JOIN(select formid,count(*) ccount from fixidez GROUP BY formid) con on user.uid=con.formid "
				+ "LEFT JOIN (select uid,count(*) tcount from trilha GROUP BY uid) history on user.uid=history.uid "
				+ "LEFT JOIN (select uid,count(*) pcount from post GROUP BY uid) post on user.uid=post.uid "
				+ "LEFT JOIN school on user.schoolid=school.schoolid where username like ?",
				new int[]{
						Types.VARCHAR
				},
				new Object[]{
						word
				});
	}
	
	/**
	 * 根据id查找用户
	 * @param userid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> queryUserById(String userid) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from user where uid=?",
				new int[]{
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(userid)
				});
	}

	/**
	 * 修改密码
	 * @param userid
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public int updatePasswordById(String userid,String password) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("update user set password=? where uid=?",
				new int[]{
						Types.VARCHAR,
						Types.INTEGER
				},
				new Object[]{
						password,
						Integer.parseInt(userid)
				});
	}
	
	/**
	 * 查询黑名单
	 * @param userid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> queryBlackById(String userid) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from blackname where uid=?",
				new int[]{
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(userid)
				});
	}

	/**
	 * 添加黑名单
	 * @param userid
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public int addBlcakName(String userid) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("insert into blackname values(0,?,?,0,0)",
				new int[]{
						Types.INTEGER,
						Types.VARCHAR
				},
				new Object[]{
						Integer.parseInt(userid),
						System.currentTimeMillis()
				});
	}
	
	/**
	 * 修改黑名单
	 * @param userid
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public int updateBlackName(String userid,int display) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("update blackname set display=? where uid=?",
				new int[]{Types.INTEGER,Types.INTEGER},
				new Object[]{display,Integer.parseInt(userid)});
	}
	
}
