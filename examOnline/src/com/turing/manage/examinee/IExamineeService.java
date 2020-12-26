package com.turing.manage.examinee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @desc   考生模块的M层接口
 * @author Littiger
 * @time   2019年12月2日 下午5:47:34
 */
public interface IExamineeService {

	List<Map<String, Object>> queryExaminee(String classs_id,String key) throws ClassNotFoundException, SQLException;

	void save(String classs_name) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException;

	Map<String, Object> queryClasssById(String classs_id) throws ClassNotFoundException, SQLException;

	void update(String classs_id, String classs_name) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException;

	void deleteByIdArray(String[] delIdArray) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException;

	List<Map<String, Object>> queryClasss() throws ClassNotFoundException, SQLException;

	void saveByImportExcel(List<String> columnValuesList) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException;

}
