package com.turing.manage.manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.turing.dao.Dao;
import com.turing.dao.DaoImpl;

public class ManagerServiceImpl implements IManagerService{

	Dao dao = new DaoImpl();
	
	@Override
	public List<Map<String, Object>> query() throws ClassNotFoundException, SQLException {
		
		return dao.executeQueryForList(" select * from manager ");
	}

	@Override
	public void save(String manager_name, String manager_pass, String virtualPath) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		
		dao.executeUpdate(" insert into manager values(0,?,?,?) ", new int []{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}, new Object[]{manager_name,manager_pass,virtualPath});
	}

	@Override
	public Map<String,Object> queryOne(String manager_id) throws ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from manager where manager_id='"+manager_id+"'");
	}

	@Override
	public void edit(String manager_id,String manager_name, String manager_pass, String virtualPath) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao.executeUpdate("update manager set manager_name=?,manager_pass=?,manager_image=? where manager_id="+manager_id+"", new int []{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}, new Object[]{manager_name,manager_pass,virtualPath});
	}

	@Override
	public void editLow(String manager_id, String manager_name, String manager_pass, String virtualPath) throws ClassNotFoundException, SQLException {
		dao.executeUpdate("update manager set manager_name='"+manager_name+"',manager_pass='"+manager_pass+"',manager_image='"+virtualPath+"' where manager_id="+manager_id+"");
	}

	@Override
	public void delete(String manager_id) throws ClassNotFoundException, SQLException {
		dao.executeUpdate(" delete from manager where manager_id= '"+manager_id+"'");
	}
	
	

}
