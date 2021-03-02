package com.quifeng.dao.issue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.quifeng.dao.token.TokenDao;
import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

public class issueDao {
	Dao dao = new DaoImpl();
	TokenDao tokenDao = new TokenDao();

	public Map<String, Object> queryplacaById(String placaid)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from postbk where placaid=?", new int[] { Types.INTEGER },
				new Object[] { Integer.parseInt(placaid) });
	}

	/**
	 * 添加帖子
	 * 
	 * @param uid
	 * @param placaid
	 * @param content
	 * @param video
	 * @param string
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public Map<String, Object> addPost(String uid, String placaid, String content, String video, String image)
			throws NumberFormatException, ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		// 时间
		String time = System.currentTimeMillis() + "";
		// schoolid
		String schoolid = querySchoolidByUid(uid);
		// 添加帖子
		// 有文本有图片
		if (content != null && !image.equals("[]") && video == null) {
			dao.executeUpdate("insert into post values(0,?,?,?,0,?,?,null,?,0,0)",
					new int[] { Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.INTEGER },
					new Object[] { Integer.parseInt(uid), Integer.parseInt(placaid), time, content, image,
							Integer.parseInt(schoolid) });
		}
		// 有文本有视频
		else if (content != null && video != null && image.equals("[]")) {
			dao.executeUpdate("insert into post values(0,?,?,?,0,?,null,?,?,0,0)",
					new int[] { Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.INTEGER },
					new Object[] { Integer.parseInt(uid), Integer.parseInt(placaid), time, content, video,
							Integer.parseInt(schoolid) });
		}
		// 只有视频
		else if ((content == null || content.equals("")) && video != null && image.equals("[]")) {
			dao.executeUpdate("insert into post values(0,?,?,?,0,null,null,?,?,0,0)",
					new int[] { Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER },
					new Object[] { Integer.parseInt(uid), Integer.parseInt(placaid), time, video,
							Integer.parseInt(schoolid) });
		}
		// 只有图片
		else if ((content == null || content.equals("")) && video == null && !image.equals("[]")) {
			dao.executeUpdate("insert into post values(0,?,?,?,0,null,?,null,?,0,0)",
					new int[] { Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER },
					new Object[] { Integer.parseInt(uid), Integer.parseInt(placaid), time, image,
							Integer.parseInt(schoolid) });
		}
		// 只有文本
		else if (content != null && video == null && image.equals("[]")) {
			dao.executeUpdate("insert into post values(0,?,?,?,0,?,null,null,?,0,0)",
					new int[] { Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER },
					new Object[] { Integer.parseInt(uid), Integer.parseInt(placaid), time, content,
							Integer.parseInt(schoolid) });
		}

		// 获取添加完的帖子信息
		return dao.executeQueryForMap("select * from post where uid=? and createtime=?",
				new int[] { Types.INTEGER, Types.VARCHAR }, new Object[] { Integer.parseInt(uid), time });

	}

	/**
	 * 根据id查询学校id
	 * 
	 * @param uid
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	private String querySchoolidByUid(String uid) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from user where uid=?", new int[] { Types.INTEGER },
				new Object[] { Integer.parseInt(uid) }).get("schoolid").toString();
	}

	/**
	 * 添加帖子信息
	 * 
	 * @param postid
	 * @param schoolid
	 * @param postshenhe 
	 * @return
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public int addPostInfo(String postid, String schoolid, String postshenhe)
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("insert into postinfo values(0,?,0,0,0,0,0,?,0,?,0)",
				new int[] { Types.INTEGER,Types.INTEGER, Types.INTEGER },
				new Object[] { Integer.parseInt(postid),Integer.parseInt(postshenhe), Integer.parseInt(schoolid) });
	}

	/**
	 * 获取所有圈子信息 去除广告板块
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Map<String, Object>> queryAllCircle() throws ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select * from postbk where placaid!=0");
	}

	/**
	 * 根据uid查用户信息
	 * 
	 * @param uid
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public Map<String, Object> queryUserById(String uid)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from user where uid=?", new int[] { Types.INTEGER },
				new Object[] { Integer.parseInt(uid) });
	}
	
	/**
	 * 根据uid查询学校信息
	 * @param uid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> querySchoolByUid(String uid) throws NumberFormatException, ClassNotFoundException, SQLException {
		String sql = "select * from school where schoolid=(select schoolid from user where uid=?)";
		return dao.executeQueryForMap(sql, new int[]{Types.INTEGER}, new Object[]{Integer.parseInt(uid)});
	}
}
