package com.turing.manage.course;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ICourseService {

	List<Map<String, Object>> queryAll() throws ClassNotFoundException, SQLException;

	void add(String course_name) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException;

	void delete(String[] delIdArray) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException;

}
