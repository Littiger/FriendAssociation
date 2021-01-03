package com.quifeng.dao.chat;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @Desc 对聊天的操作
 * @author 语录
 *
 */
public class ChatDao {

	Dao dao = new DaoImpl();
	
	/**
	 * @Desc 获取自己聊天的人的信息 
	 * @param uid
	 * @return
	 */
	public  List<Map<String, Object>> getChatMess(String uid){
		List<Map<String, Object>> data = null;
		try {
			String sql = "SELECT  * FROM  chat  INNER JOIN user on recipientsid=uid WHERE resserid=? AND display=0   GROUP BY  recipientsid            ";
			data=dao.executeQueryForList(sql , new int[]{
					Types.INTEGER
			}, new Object[]{
					uid
			});
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return data;
	}
	
	/**
	 * @Desc 获取最后一条聊天信息
	 * @param uid
	 * @return
	 */
	public Map<String, Object> getEnd(String resserid,String recipientsid){
		Map<String, Object> data = null;
		try {
			String sql = "SELECT * FROM chat  WHERE resserid =? ANd  recipientsid=? ANd display = 0 ORDER BY  createtime Desc  LIMIT 1";
			data=dao.executeQueryForMap(sql , new int[]{
					Types.INTEGER,
					Types.INTEGER
			}, new Object[]{
					resserid,recipientsid
			});
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return data;
	}
	

	/**
	 * @Desc 获取没有读消息的条数
	 * @param recipientsid
	 * @param resserid
	 * @return
	 */
	public Map<String, Object> getCountZ(String recipientsid,String resserid){
		Map<String, Object> data = null;
		try {
			String sql = "SELECT count(*) count FROM chat WHERE isread=0 And display=0 AND recipientsid=? AND resserid=?";
			data=dao.executeQueryForMap(sql , new int[]{
					Types.INTEGER,
					Types.INTEGER
			}, new Object[]{
					recipientsid,resserid
			});
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return data;
	}
	
	/**
	 * @Desc 相互关注就是好友
	 * @param fixidezid
	 * @param Befixidez
	 * @return
	 */
	public Map<String, Object> isFixidez(String fixidezid,String Befixidez){
		
		Map<String, Object> data = null;
		try {
			String sql = "SELECT *	 FROM fixidez  WHERE fixidezid = ? AND  Befixidez = ? ANd display=0";			
			data=dao.executeQueryForMap(sql , new int[]{
					Types.INTEGER,
					Types.INTEGER
			}, new Object[]{
					fixidezid,Befixidez
			});
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return data;
	}
	

	/**
	 * @Desc 发送消息
	 * @param resserid
	 * @param recipientsid
	 * @param content
	 * @param createtime
	 * @return
	 */
	public int addChat(String chatid,String resserid,String recipientsid,String content ,String createtime){
		int count = 0;
		String sql = "INSERT INTO chat(chatid,resserid,recipientsid,content,createtime) VALUES(?,?,?,?,?)";
		try {
			count=dao.executeUpdate(sql , new int[]{
					Types.VARCHAR,Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.VARCHAR
			}, new Object[]{
					chatid,resserid,recipientsid,content,createtime
			});
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	/**
	 * @Desc 一条消息标记为已读
	 * @param chatid
	 * @return
	 */
	public int updateChatByID(String chatid){
		int count = 0;
		
		try {
			count =dao.executeUpdate("UPDATE chat SET isread=1 WHERE chatid=?",new  int[]{
				Types.VARCHAR
			}, new Object[]{
					chatid
			});
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
	
	/**
	 * @Desc 设置全部
	 * @param resserid
	 * @param recipientsid
	 * @return
	 */
	public int updateChatAll(String resserid,String recipientsid){
		int count = 0;
		
		try {
			count =dao.executeUpdate("UPDATE chat SET isread=1 WHERE resserid=? AND recipientsid=?",new  int[]{
				Types.INTEGER,Types.INTEGER
			}, new Object[]{
				resserid,recipientsid
			});
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}

	/**
	 * @Desc 获取聊天信息
	 * @param resserid
	 * @param recipientsid
	 * @return
	 */
	public List<Map<String, Object>> getThisTory(String resserid,String recipientsid) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> data = null;
		try {
			String sql = "SELECT  *	FROM chat  WHERE resserid=? AND recipientsid=? AND display=0";			
			data=dao.executeQueryForList(sql , new int[]{
					Types.INTEGER,
					Types.INTEGER
			}, new Object[]{
					resserid,recipientsid
			});
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return data;
	}
	
	
	
}
