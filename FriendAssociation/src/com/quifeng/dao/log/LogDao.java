package com.quifeng.dao.log;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;

import com.quifeng.utils.dao.DaoImpl;
import com.quifeng.utils.dao.Dao;


/**
 * @Desc 增加日志
 * @author 语录
 *
 */
public class LogDao {

	Dao dao = new DaoImpl();
	//增加日志
	public int addUserlog(String uid, String time, String content, String ip, String coinchange, String optype) {
		// TODO Auto-generated method stub
		int count=0;
		try {
			count = dao.executeUpdate("INSERT INTO userlog (uid,time,content,ip,coinchange,optype) VALUES(?,?,?,?,? ,?)", new int[]{
				Types.INTEGER,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR
			}, new Object[]{
				uid,time,content,ip,coinchange,optype	
			});
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
}
