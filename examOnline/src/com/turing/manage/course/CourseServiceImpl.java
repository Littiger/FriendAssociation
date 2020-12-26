package com.turing.manage.course;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.turing.dao.Dao;
import com.turing.dao.DaoImpl;
import com.turing.utils.Dates;

public class CourseServiceImpl implements ICourseService {

	Dao dao = new DaoImpl();

	@Override
	public List<Map<String, Object>> queryAll() throws ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select * from course");
	}

	@Override
	public void add(String course_name) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao.executeUpdate("insert into course values('"+UUID.randomUUID().toString()+"',?,'"+Dates.CurrentYMDHSMTime()+"')", new int[]{Types.VARCHAR}, new Object[]{course_name});
	}

	@Override
	public void delete(String[] delIdArray) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		for (String str : delIdArray) {
			
			String sql01 = "delete from testpaper_list where testpaper_id in (select testpaper_id from testpaper where course_id=?)";
			String sql02 = "delete from testpaper where course_id=?";
			String sql03 = "delete from subject where course_id=?";
			String sql04 = "delete from course where course_id=?";
			
			int[] types = new int[]{Types.VARCHAR};
			Object[] values = new Object[]{str};
			dao.executeUpdate(sql01, types, values);
			dao.executeUpdate(sql02, types, values);
			dao.executeUpdate(sql03, types, values);
			dao.executeUpdate(sql04, types, values);
			
			
		}
	}
	
}
