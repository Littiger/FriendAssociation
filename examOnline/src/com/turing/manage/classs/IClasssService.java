package com.turing.manage.classs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IClasssService {

	List<Map<String, Object>> queryAll() throws ClassNotFoundException, SQLException;

	void add(String name) throws ClassNotFoundException, SQLException;

	Map<String, Object> queryOne(String classs_id) throws ClassNotFoundException, SQLException;

	void edit(String classs_id, String classs_name) throws ClassNotFoundException, SQLException;

	void delete(String[] idArray) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException;

	
}


