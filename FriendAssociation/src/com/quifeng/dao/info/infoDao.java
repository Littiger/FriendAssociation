package com.quifeng.dao.info;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;

import com.quifeng.dao.token.TokenDao;
import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

public class infoDao {
	Dao dao = new DaoImpl();
	TokenDao tokenDao = new TokenDao();
	public int updateHeadImage(String uid, String useravatar) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("update user set useravatar=? where uid=?",
				new int[]{
						Types.VARCHAR,
						Types.INTEGER
				},
				new Object[]{
						useravatar,
						Integer.parseInt(uid)
				});
	}
	
	
}
