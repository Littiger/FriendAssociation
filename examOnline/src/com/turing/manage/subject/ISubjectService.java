package com.turing.manage.subject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ISubjectService {

	List<Map<String, Object>> queryAll() throws ClassNotFoundException, SQLException;

	Map<String, Object> query(String subject_id) throws ClassNotFoundException, SQLException;

	List<Map<String, Object>> queryCourse() throws ClassNotFoundException, SQLException;

	void edit(String lessonId, String subject_name, String subject_type, String subject_A, String subject_B,
			String subject_C, String subject_D, String[] answerArr, String subject_remark ,String subject_id) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException;

	void add(String course_id, String subject_name, String subject_type, String subject_A, String subject_B,
			String subject_C, String subject_D, String[] answerArr, String subject_remark) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException;

	List<Map<String, Object>> queryCondition(String condition, String key) throws ClassNotFoundException, SQLException;

	void delete(String[] delIdArray) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException;

	
}
