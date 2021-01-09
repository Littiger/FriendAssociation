package com.quifeng.dao.chat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.quifeng.dao.token.TokenDao;
import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;
/**
 * @desc   聊天dao层
 * @author JZH
 * @time   2021-01-02
 */
public class ChatDao {
	Dao dao = new DaoImpl();
	TokenDao tokenDao = new TokenDao();
	
	/**
	 * 根据token获取聊天列表
	 * @param token
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public List<Map<String, Object>> queryMessListByToken(String token) throws NumberFormatException, ClassNotFoundException, SQLException {
		//获取id
		String uid = tokenDao.queryUidByToken(token);
		String sql = "select * from (select * FROM news where resserid=? or recipients=? ORDER BY createtime desc ) n1 ORDER BY n1.createtime desc";
		return dao.executeQueryForList(sql,
				new int[]{
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(uid),
						Integer.parseInt(uid)
				});
	}
	/**
	 * 根据id获取用户信息
	 * @param string
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> queryUserById(String id) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from user where uid=?",
				new int[]{
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(id)
				});
	}
	/**
	 * 根据自己id和对方id获取未读消息数量
	 * @param token
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public int queryUnReadMessCount(String id,String toId) throws NumberFormatException, ClassNotFoundException, SQLException {
		String sql = "select count(*) count "
				+ "from news where display=0 and isread=0 and "
				+ "(resserid=? and recipients=?) or (resserid=? and recipients=?) ";
		return Integer.parseInt(
					dao.executeQueryForMap(sql,
							new int[]{
									Types.INTEGER,
									Types.INTEGER,
									Types.INTEGER,
									Types.INTEGER
							},
							new Object[]{
									Integer.parseInt(id),
									Integer.parseInt(toId),
									Integer.parseInt(toId),
									Integer.parseInt(id)
							}).get("count").toString()
				);
	}
	/**
	 * 查看是否为好友
	 * @param token
	 * @param targetid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public List<Map<String, Object>> queryFixById(String formid, String toid) throws NumberFormatException, ClassNotFoundException, SQLException {
		String sql = "select * from fixidez where (formid=? and toid=?) or (formid=? and toid=?)";
		return dao.executeQueryForList(sql,
				new int[]{
						Types.INTEGER,
						Types.INTEGER,
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(formid),
						Integer.parseInt(toid),
						Integer.parseInt(toid),
						Integer.parseInt(formid),
				});
	}
	/**
	 * 添加消息 返回此条消息信息
	 * @param uid
	 * @param targetid
	 * @param string
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> addMess(String uid, String targetid, String mess) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String mill = System.currentTimeMillis()+"";
		dao.executeUpdate("insert into news values(0,?,?,?,0,?,0)",
				new int[]{
						Types.INTEGER,
						Types.INTEGER,
						Types.VARCHAR,
						Types.VARCHAR
				},
				new Object[]{
						Integer.parseInt(uid),
						Integer.parseInt(targetid),
						mess,
						mill
				});
		return dao.executeQueryForMap("select * from news where resserid=? and recipients=? and createtime=?",
				new int[]{
						Types.INTEGER,
						Types.INTEGER,
						Types.VARCHAR
				},
				new Object[]{
						Integer.parseInt(uid),
						Integer.parseInt(targetid),
						mill	
				});
	}
	/**
	 * 消息全部设为已读
	 * @param uid
	 * @param targetid
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public int updateIsRead(String uid, String targetid) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("update news set isread=1 where resserid=? and recipients=?",
				new int[]{
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(targetid),
						Integer.parseInt(uid)
				});
	}
	/**
	 * 单条消息已读
	 * @param chatid
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public int updateIsReadById(String chatid) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("update news set isread=1 where chatid=?",
				new int[]{
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(chatid)
				});
	}
	/**
	 * 根据id查询消息
	 * @param uid
	 * @param targetid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public List<Map<String, Object>> queryNewsById(String uid, String targetid) throws NumberFormatException, ClassNotFoundException, SQLException {
		String sql="select * from news "
				+ "where display=0 and (resserid=? and recipients=?) or (resserid=? and recipients=?) "
				+ "ORDER BY createtime desc ";
		return dao.executeQueryForList(sql,
				new int[]{
						Types.INTEGER,
						Types.INTEGER,
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(uid),
						Integer.parseInt(targetid),
						Integer.parseInt(targetid),
						Integer.parseInt(uid)
				});
	}
	/**
	 * 查询是否有这条消息
	 * @param chatid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> queryNewsById(String chatid) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from news where chatid=?",
				new int[]{
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(chatid)
				});
	}
	
}
