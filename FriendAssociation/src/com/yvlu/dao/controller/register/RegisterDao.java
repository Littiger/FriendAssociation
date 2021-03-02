/**
 * 
 */
package com.yvlu.dao.controller.register;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;



import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;


/**
 * @desc 注册控制
 * @author qiufeng
 * @version 1.0
 * @time 2021年2月2日 下午4:16:05
 */
public class RegisterDao {
	Dao dao = new DaoImpl();
	
	/**
	 * @Desc token 验证
	 * @return
	 */
	public Map<String, Object> getRootByToken(String token){
		
			try {
				return dao.executeQueryForMap("SELECT * FROM root WHERE rootid = (SELECT rootid FROM  rootlogin WHERE token=?)", new int[]{
						Types.VARCHAR
				}, new Object[]{
						token
				});
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("asdasddsaasdsadsads");
				e.printStackTrace();
				
			}
			return null;
		
	}

	/**
	 * @desc 获取注册限制状态
	 * @param id  应用id 默认为1
	 * @return 
	 */
	public Map<String, Object> GetStatus(int appid){
		String sql = "SELECT register as status FROM appmessage WHERE appid = ?";
		try {
			return dao.executeQueryForMap(sql, new int[]{Types.INTEGER}, new Object[]{appid});
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * @desc 设置注册限制状态
	 * @return 
	 */
	public  int SetStatus(int appid,String status){
		String sql = "UPDATE appmessage SET register=? WHERE appid = ?";
		try {
			return dao.executeUpdate(sql, new int[]{Types.VARCHAR,Types.INTEGER}, new Object[]{status,appid});
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	
	/**
	 * @return 
	 * @desc 获取登录控制状态
	 */
	public Map<String, Object> GetLoginstatus(int appid){
		String sql = "SELECT login as status FROM appmessage WHERE appid = ?";
		try {
			return dao.executeQueryForMap(sql, new int[]{Types.INTEGER}, new Object[]{appid});
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @desc 设置登录信息
	 * @param appid
	 * @param status
	 * @return 
	 */
	public int SetLoginPoststatus(int appid,String status) {
		String sql = "UPDATE appmessage SET login=? WHERE appid = ?";
		try {
			return dao.executeUpdate(sql, new int[]{Types.VARCHAR,Types.INTEGER}, new Object[]{status,appid});
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * @return 
	 * @desc 获取公告
	 */
	public Map<String, Object> GetannouncementInfo(int appid) {
		try {
			String sql = "SELECT appupdate as info FROM appmessage WHERE appid = ?";
			return dao.executeQueryForMap(sql,new int[]{Types.INTEGER}, new Object[]{appid});
		}
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @desc 写入公告
	 */
	public int SetannouncementInfo(int appid,String date) {
		String sql = "UPDATE appmessage SET appupdate=? WHERE appid = ?";
		try {
			return dao.executeUpdate(sql, new int[]{Types.VARCHAR,Types.INTEGER},new Object[]{date,appid});
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
	/**
	 * @return 
	 * @desc 获取应用版本
	 */
	public Map<String, Object> Getappversion(int appid) {
		String sql = "SELECT appversion as version,appupdate ,downpath as url FROM appmessage WHERE appid =?";
		try {
			return dao.executeQueryForMap(sql, new int[]{Types.INTEGER}, new Object[]{appid});
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * @desc 写入应用版本
	 */
	public int Setappversion(int appid,String info) {
		String sql = "UPDATE appmessage SET appversion=? WHERE appid = ?";
		try {
			return dao.executeUpdate(sql, new int[]{Types.VARCHAR,Types.INTEGER}, new Object[]{info,appid});
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	
	/**
	 * @return 
	 * @desc 获取更新地址
	 */
	public Map<String, Object> GetUpdateInfo(int appid) {
		String sql = "SELECT downpath FROM appmessage WHERE appid = ?";
		try {
			return dao.executeQueryForMap(sql, new int[]{Types.INTEGER}, new Object[]{appid});
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * @return 
	 * @desc 写入更新地址
	 */
	public int SetUpdateInfo(int appid,String info) {
		String sql = "UPDATE appmessage SET downpath=? WHERE appid = ?";
		try {
			return dao.executeUpdate(sql, new int[]{Types.VARCHAR,Types.INTEGER}, new Object[]{info,appid});
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	/**
	 * @return 
	 * @desc 获取学校信息
	 */
	public List<Map<String, Object>> GetSchoolInfo(){
		String sql ="SELECT school.schoolid,a.user,b.posts,c.audits,school.schoolname,school.schoolsede,school.postshenhe "
				+ "FROM school "
				+ "LEFT JOIN "
				+ "(SELECT school.schoolid,count(*) AS user "
				+ "FROM user,school "
				+ "WHERE user.schoolid = school.schoolid GROUP BY user.schoolid) a "
				+ "on school.schoolid=a.schoolid "
				+ "LEFT JOIN "
				+ "(SELECT school.schoolid,count(post.postid) AS posts "
				+ "FROM post,school WHERE post.schoolid = school.schoolid GROUP BY post.schoolid) b "
				+ "on school.schoolid = b.schoolid "
				+ "LEFT JOIN "
				+ "(SELECT school.schoolid,count(post.postid) AS audits "
				+ "FROM postinfo,school,post "
				+ "WHERE post.display = 0 AND postinfo.isexamina = 0 AND post.schoolid = school.schoolid "
				+ "AND post.postid = postinfo.postid AND school.schoolusable = 1 GROUP BY post.schoolid) c "
				+ "on school.schoolid = c.schoolid";
		try {
			return dao.executeQueryForList(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * @desc 新增学校
	 * @param name
	 * 			学校名称
	 * @param site 
	 * 			学校地址
	 * @return 
	 */
	public int addSchool(String name,String site) {
		String sql = "INSERT INTO school (schoolid, schoolname, schoolsede, schoolusable) VALUES (0, ?, ?, 1)";
		try {
			return dao.executeUpdate(sql, new int[]{Types.VARCHAR,Types.VARCHAR},new Object[]{name,site});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	/**
	 * @return 
	 * @desc  删除学校
	 */
	public int DeleteSchool(int Schoolid) {
		String sql = "DELETE FROM school WHERE (schoolid=?)";
		try {
			return dao.executeUpdate(sql, new int[]{Types.INTEGER}, new Object[]{Schoolid});
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
