package com.turing.manage.classs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.turing.dao.Dao;
import com.turing.dao.DaoImpl;
import com.turing.utils.Dates;

public class ClassServiceImpl implements IClasssService {

	Dao dao = new DaoImpl();
	
	@Override
	public List<Map<String, Object>> queryAll() throws ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select * from classs");
	}

	@Override
	public void add(String name) throws ClassNotFoundException, SQLException {
		dao.executeUpdate("insert into classs values('"+UUID.randomUUID().toString()+"','"+name+"','"+Dates.CurrentYMDHSMTime()+"')");
	}

	@Override
	public Map<String, Object> queryOne(String classs_id) throws ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from classs where classs_id='"+classs_id+"'");
	}

	@Override
	public void edit(String classs_id, String classs_name) throws ClassNotFoundException, SQLException {
		dao.executeUpdate(" update classs set classs_name='"+classs_name+"' where classs_id='"+classs_id+"'");
	}

	@Override
	public void delete(String[] idArray) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		
		for (int i = 0; i < idArray.length; i++) {
			
			String sql1=" delete from grade where examinee_id in (select examinee_id from examinee where classs_id=? ) ";
			String sql2="  delete from examinee where classs_id=?  ";
			String sql3=" delete from testpaper_list where testpaper_id in ( select testpaper_id from testpaper where classs_id=?  )";
			String  sql4=" delete from testpaper where classs_id=? ";
			String sql5=" delete from classs  where classs_id=?  ";
			
			dao.executeUpdate(sql1, new int[]{Types.VARCHAR}, new Object[]{idArray[i]});
			dao.executeUpdate(sql2, new int[]{Types.VARCHAR}, new Object[]{idArray[i]});
			dao.executeUpdate(sql3, new int[]{Types.VARCHAR}, new Object[]{idArray[i]});
			dao.executeUpdate(sql4, new int[]{Types.VARCHAR}, new Object[]{idArray[i]});
			dao.executeUpdate(sql5, new int[]{Types.VARCHAR}, new Object[]{idArray[i]});
			
		}
		
		
		
		
	}

}
