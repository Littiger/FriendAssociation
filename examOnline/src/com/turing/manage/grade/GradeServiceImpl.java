package com.turing.manage.grade;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.turing.dao.Dao;
import com.turing.dao.DaoImpl;

public class GradeServiceImpl implements IGradeService {

	Dao dao = new DaoImpl();
	
	@Override
	public List<Map<String, Object>> queryAll() throws ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select * from grade,course where course.course_id = grade.course_id");
	}

	@Override
	public List<Map<String, Object>> queryCondition(String condition, String key) throws ClassNotFoundException, SQLException {
		
		if(key==null){
			key="";
		}
		
		String sql = "SELECT * FROM grade,course WHERE course.course_id = grade.course_id AND "+condition+" LIKE ?";
		int[] types = new int[]{Types.VARCHAR};
		Object[] values = new Object[] {"%"+key.trim()+"%"};
		return dao.executeQueryForList(sql, types,values );
	}

}
