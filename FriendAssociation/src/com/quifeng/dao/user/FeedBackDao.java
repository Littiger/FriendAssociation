package com.quifeng.dao.user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @desc   反馈意见dao层
 * @author JZH
 *
 */
public class FeedBackDao {
	Dao dao = new DaoImpl();
	
	/**
	 * 添加意见
	 * @param message
	 * @param contact
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public int addFeedBack(String message, String contact) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if(contact == null || contact.equals("")){
			contact = "null";
		}
		return dao.executeUpdate("insert into feedback values(0,?,?,?,0)", new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}, new Object[]{message,contact,System.currentTimeMillis()});
	}
}
