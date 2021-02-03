package com.yvlu.dao.posts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @desc   帖子相关后台Dao
 * @author JZH
 * @time   2021年2月3日
 */
public class postsDao {
	Dao dao = new DaoImpl();
	
	/**
	 * 获取所有审核过的帖子信息
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getAllEdPosts() throws ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select *,post.createtime ptime from post"
				+ " LEFT JOIN postinfo on post.postid=postinfo.postid"
				+ " LEFT JOIN user on post.uid=user.uid"
				+ " LEFT JOIN postbk on post.placaid=postbk.placaid"
				+ " where postinfo.isexamina=1 and post.display=0");
	}
	
	/**
	 * 模糊搜索审核过的帖子
	 * @param word
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Map<String, Object>> getPartPosts(String word) throws ClassNotFoundException, SQLException {
		if(word == null || word.equals("")){
			word = "%%";
		}
		else{
			word = "%"+word+"%";
		}
		
		String sql = "select *,post.createtime ptime from post "
				+ "LEFT JOIN postinfo on post.postid=postinfo.postid "
				+ "LEFT JOIN user on post.uid=user.uid "
				+ "LEFT JOIN postbk on post.placaid=postbk.placaid "
				+ "where postinfo.isexamina=1 and post.display=0 and post.posttext like ?";
		
		return dao.executeQueryForList(sql,
				new int[]{
						Types.VARCHAR
				},
				new Object[]{
						word
				});
	}
	
	/**
	 * 获取所有未审核帖子
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Map<String, Object>> getAllPosts() throws ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select *,post.createtime ptime from post"
				+ " LEFT JOIN postinfo on post.postid=postinfo.postid"
				+ " LEFT JOIN user on post.uid=user.uid"
				+ " LEFT JOIN postbk on post.placaid=postbk.placaid"
				+ " where postinfo.isexamina=0 and post.display=0");
	}
	
	/**
	 * 模糊查询所有未审核帖子
	 * @param word
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Map<String, Object>> getPosts(String word) throws ClassNotFoundException, SQLException {
		if(word == null || word.equals("")){
			word = "%%";
		}
		else{
			word = "%"+word+"%";
		}
		
		String sql = "select *,post.createtime ptime from post "
				+ "LEFT JOIN postinfo on post.postid=postinfo.postid "
				+ "LEFT JOIN user on post.uid=user.uid "
				+ "LEFT JOIN postbk on post.placaid=postbk.placaid "
				+ "where postinfo.isexamina=0 and post.display=0 and post.posttext like ?";
		
		return dao.executeQueryForList(sql,
				new int[]{
						Types.VARCHAR
				},
				new Object[]{
						word
				});
	}

	
	/**
	 * 删除帖子
	 * @param postid
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public int delPost(String postid) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("update post set display=1 where postid=?",
				new int[]{
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(postid)
				});
	}

	/**
	 * 通过审核
	 * @param postid
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public int auditPost(String postid) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return dao.executeUpdate("update postinfo set isexamina=1 where postid=?",
				new int[]{
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(postid)
				});
	}
	
	
}
