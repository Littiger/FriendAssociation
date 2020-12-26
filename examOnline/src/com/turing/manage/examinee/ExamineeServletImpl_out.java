package com.turing.manage.examinee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.turing.dao.Dao;
import com.turing.dao.DaoImpl;

public class ExamineeServletImpl_out implements IExamineeService_out {

	Dao dao = new DaoImpl();
	
	@Override
	public List<Map<String, Object>> queryAll() throws ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select * from examinee");
	}

	@Override
	public void delete(String[] idArray) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		
		for (int i = 0; i < idArray.length; i++) {
			
			String sql1 = "delete from grade where examinee_id=?";
			String sql2 = "delete from examinee where examinee_id=?";
			
			dao.executeUpdate(sql1, new int[]{Types.VARCHAR}, new Object[]{idArray[i]});
			dao.executeUpdate(sql2, new int[]{Types.VARCHAR}, new Object[]{idArray[i]});
			
		}
	}

	@Override
	public List<Map<String, Object>> queryClasssName(String classs_name) throws ClassNotFoundException, SQLException {
		
		String sql = "select * from examinee where classs_id = (select classs_id from classs where classs_name = ?)";
		
		return dao.executeQueryForList(sql, new int[]{Types.VARCHAR}, new Object[]{classs_name});
	}

	@Override
	public List<Map<String, Object>> queryExamineeName(String examinee_name) throws ClassNotFoundException, SQLException {
		String sql = "select * from examinee where examinee_name=?";
		
		return dao.executeQueryForList(sql, new int[]{Types.VARCHAR}, new Object[]{examinee_name});
	}

	@Override
	public List<Map<String, Object>> queryCEName(String classs_name, String examinee_name) throws ClassNotFoundException, SQLException {
		String sql = "select * from examinee where classs_id = (select classs_id from classs where classs_name = ?) and examinee_name = ?";
		
		return dao.executeQueryForList(sql,new int[]{Types.VARCHAR,Types.VARCHAR} , new Object[]{classs_name,examinee_name});
	}


}
