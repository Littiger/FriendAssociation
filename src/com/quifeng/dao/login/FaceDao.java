package com.quifeng.dao.login;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @Desc 对人脸的插入 对人脸的认证
 * @author 语录
 *
 */
public class FaceDao {

	Dao dao = new DaoImpl();

	/**
	 * @Desc 增加
	 * @param uid
	 * @param facebase
	 * @param createtime
	 * @return
	 */
	public int addFace(String uid, String facebase, String createtime) {
		int count = 0;
		String sql = " INSERT INTO face (uid,facebase,createtime) VALUES (?,?,?)";
		try {
			count = dao.executeUpdate(sql, new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR },
					new Object[] { uid, facebase, createtime });
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * @Desc 根据token获取uid
	 * @param token
	 * @return
	 */
	public Map<String, Object> getUIdByToken(String token) {
		Map<String, Object> data = null;
		String sql = " SELECT *	FROM userlogin  WHERE utoken=?";
		try {
			dao.executeQueryForMap(sql, new int[] { Types.VARCHAR }, new Object[] { token });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * @Desc 查询有多少张人脸
	 * @param uid
	 * @return
	 */
	public int queryCountById(String uid) {
		// TODO Auto-generated method stub
		int count = 0;
		try {
			count = dao.executeQueryForInt("SELECT count(*) count FROM face WHERE uid = ?", new int[] { Types.INTEGER },
					new Object[] { uid });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public List<Map<String, Object>> queryFaceByUid(String uid) {
		String sql = " SELECT *	 FROM  face where uid=?";
		List<Map<String, Object>> data = null;
		try {
			data = dao.executeQueryForList(sql, new int[] { Types.INTEGER }, new Object[] { uid });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

}
