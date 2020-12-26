package com.turing.manage.examinee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IExamineeService_out {

	List<Map<String, Object>> queryAll() throws ClassNotFoundException, SQLException;

	void delete(String[] idArray) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException;

	List<Map<String, Object>> queryClasssName(String classs_name) throws ClassNotFoundException, SQLException;

	List<Map<String, Object>> queryExamineeName(String examinee_name) throws ClassNotFoundException, SQLException;

	List<Map<String, Object>> queryCEName(String classs_name, String examinee_name) throws ClassNotFoundException, SQLException;


	
}
