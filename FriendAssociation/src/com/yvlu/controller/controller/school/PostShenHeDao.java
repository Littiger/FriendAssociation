package com.yvlu.controller.controller.school;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @desc   控制学校帖子是否需要审核
 * @author JZH
 *
 */
public class PostShenHeDao {
	Dao dao = new DaoImpl();
	
	/**
	 * 通过学校id查询学校
	 * @param schoolid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> querySchoolById(String schoolid) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from school where schoolid=?", new int[]{Types.INTEGER}, new Object[]{Integer.parseInt(schoolid)});
	}
	
	/**
	 * 修改学校状态为不需要审核postshenhe字段改为1
	 * @param schoolid
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public int updateUnShenHe(String schoolid) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("update school set postshenhe=1 where schoolid=?", new int[]{Types.INTEGER}, new Object[]{Integer.parseInt(schoolid)});
	}
	
	/**
	 * 修改学校状态为需要审核postshenhe字段改为0
	 * @param schoolid
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public int updateShenHe(String schoolid) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("update school set postshenhe=0 where schoolid=?", new int[]{Types.INTEGER}, new Object[]{Integer.parseInt(schoolid)});
	}
}
