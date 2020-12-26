package com.turing.manage.manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IManagerService {

	List<Map<String, Object>> query() throws ClassNotFoundException, SQLException;

	void save(String manager_name, String manager_pass, String virtualPath) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException;

	Map<String, Object> queryOne(String manager_id) throws ClassNotFoundException, SQLException;

	void edit(String manager_name, String manager_pass, String virtualPath, String manager_id) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException;

	void editLow(String manager_id, String manager_name, String manager_pass, String virtualPath) throws ClassNotFoundException, SQLException;

	void delete(String manager_id) throws ClassNotFoundException, SQLException;

}
