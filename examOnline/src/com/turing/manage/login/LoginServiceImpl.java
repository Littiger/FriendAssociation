package com.turing.manage.login;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import com.turing.dao.Dao;
import com.turing.dao.DaoImpl;

public class LoginServiceImpl implements ILoginService {

	Dao dao = new DaoImpl();
	
	@Override
	public Map<String, Object> login(String name, String pass) throws ClassNotFoundException, SQLException {
		
		String sql = "select * from manager where manager_name= ? and manager_pass=?";
		
		int[] types= new int[2];
		types[0] = Types.VARCHAR;
		types[1] = Types.VARCHAR;
		
		Object[] values = new Object[2];
		values[0] = name;
		values[1] = pass;
		
		return dao.executeQueryForMap(sql, types, values);
		
	}
	
}
