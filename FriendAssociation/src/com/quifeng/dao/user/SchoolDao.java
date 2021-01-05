package com.quifeng.dao.user;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @Desc 查询school
 * @author 语录
 *
 */
public class SchoolDao {

	Dao dao =new DaoImpl();

	/**
	 * @Desc 获取所有的学校
	 * @return
	 */
	public List<Map<String, Object>> getSchool(){
		List<Map<String, Object>> schoolList =  null;
		String sql = "SELECT * FROM	 school";
		try {
			schoolList =dao.executeQueryForList(sql);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return schoolList;
	}

	
	public int upadateSchool(String schoolid ,String auth,String utoken ){
		int count = 0;
		String sql = "	UPDATE user SET  schoolid=? , auth=? WHERE uid = (SELECT uid FROM userlogin  WHERE utoken=?)";
		try {
			count=dao.executeUpdate(sql , new int[]{
					Types.INTEGER,
					Types.VARCHAR,
					Types.VARCHAR
			}, new Object[]{
					schoolid,auth,utoken
			});
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}

	/**
	 * @Desc 根据学校id获取学校
	 * @param schoolId
	 * @return
	 */
	public Map<String, Object> getSchoolById(String schoolId){
		Map<String, Object> data = new HashMap<String, Object>();
		String sql = "	SELECT *FROM	 school  WHERE schoolid=?";
		try {
			data=dao.executeQueryForMap(sql , new int[]{
					Types.INTEGER
			}, new Object[]{
					schoolId
			});
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
}
