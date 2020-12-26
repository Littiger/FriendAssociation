package com.turing.manage.grade;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IGradeService {

	List<Map<String, Object>> queryAll() throws ClassNotFoundException, SQLException;

	List<Map<String, Object>> queryCondition(String condition, String key) throws ClassNotFoundException, SQLException;


}
