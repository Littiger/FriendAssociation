package com.turing.manage.login;

import java.sql.SQLException;
import java.util.Map;

public interface ILoginService {

	Map<String, Object> login(String name, String pass) throws ClassNotFoundException, SQLException;
	
}
