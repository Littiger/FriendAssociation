package com.quifeng.dao.search;
/**
 * @desc   搜索界面dao
 * @author JZH
 * @time   2021-01-05
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.quifeng.dao.token.TokenDao;
import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

public class searchDao {
	Dao dao = new DaoImpl();
	TokenDao tokenDao = new TokenDao();
	/**
	 * 查询热词  获取前十个
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryHotWord() throws ClassNotFoundException, SQLException {
		String sql = "SELECT *,count(*) count from search GROUP BY word ORDER BY count desc LIMIT 0,10";
		return dao.executeQueryForList(sql);
	}
	/**
	 * 根据内容查询帖子
	 * @param wd
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Map<String, Object>> queryPostByText(String wd,String page,String size) throws ClassNotFoundException, SQLException {
		
		int size1 = Integer.parseInt(size);
		int page1 = Integer.parseInt(page);
		
		if (page1==0) page1=1;
		page1=(page1-1)*size1;
		
		wd = "%"+wd+"%";
		String sql = "select * from post p "
				+ "LEFT JOIN postinfo p2 on p.postid=p2.postid "
				+ "LEFT JOIN user u on p.uid=u.uid "
				+ "LEFT JOIN postbk p3 on p.placaid=p3.placaid "
				+ "where p.display=0 and p2.isexamina=1 and p.posttext like ?  LIMIT ?,?";
		return dao.executeQueryForList(sql,
				new int[]{
						Types.VARCHAR,
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						wd,
						page1,
						size1
				});
	}
	/**
	 * 查询用户
	 * @param wd
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Map<String, Object>> queryUserByName(String wd,String page,String size) throws ClassNotFoundException, SQLException {
		int size1 = Integer.parseInt(size);
		int page1 = Integer.parseInt(page);
		
		if (page1==0) page1=1;
		page1=(page1-1)*size1;
		
		wd = "%"+wd+"%";
		String sql = "select * from user u where username like ? limit ?,?";
		return dao.executeQueryForList(sql,
				new int[]{
						Types.VARCHAR,
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						wd,
						page1,
						size1
				});
	}
	/**
	 * 根据圈子查询帖子
	 * @param wd
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Map<String, Object>> querybkBynName(String wd,String page,String size) throws ClassNotFoundException, SQLException {
		int size1 = Integer.parseInt(size);
		int page1 = Integer.parseInt(page);
		
		if (page1==0) page1=1;
		page1=(page1-1)*size1;
		wd = "%"+wd+"%";
		String sql = "select * from postbk p "
				+ "LEFT JOIN (select *,count(*) pdynamic from post  GROUP BY placaid ) s on p.placaid=s.placaid "
				+ "LEFT JOIN (select *,count(*)  puser from (select p2.placaid  from post p "
				+ "LEFT JOIN postbk p2 on p.placaid=p2.placaid where p2.placaname like ? GROUP BY uid) z) z1 "
				+ "on z1.placaid=p.placaid "
				+ "where p.placaname like ? limit ?,?";
		return dao.executeQueryForList(sql,
				new int[]{
						Types.VARCHAR,
						Types.VARCHAR,
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						wd,
						wd,
						page1,
						size1
				});
	}
	/**
	 * 查询关注
	 * @param token
	 * @param toid
	 * @return
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> queryGuanZhu(String token,String toid) throws NumberFormatException, ClassNotFoundException, SQLException {
		String uid = tokenDao.queryUidByToken(token);
		return dao.executeQueryForMap("select * from fixidez where formid=? and toid=? and display=0",
				new int[]{
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(uid),
						Integer.parseInt(toid)
				});
	}
	/**
	 * 查询关注和粉丝数量
	 * @param string
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> queryGuanZhu(String id) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from (select count(*) guanzhu from fixidez where display=0 and formid=? GROUP BY formid) g,(select count(*) fensi from fixidez where display=0 and toid=? GROUP BY toid) f ",
				new int[]{
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(id),
						Integer.parseInt(id)
				});
	}
	/**
	 * 添加搜索记录
	 * @param wd
	 * @param token
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public void addSearchJiLu(String wd, String token) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao.executeUpdate("insert into search values(0,?,?,?)",
				new int[]{
						Types.VARCHAR,
						Types.INTEGER,
						Types.VARCHAR
				},
				new Object[]{
						wd,
						Integer.parseInt(tokenDao.queryUidByToken(token)),
						System.currentTimeMillis()
				});
	}
	/**
	 * 根据词找热词
	 * @param wd
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String, Object> queryHotWordByWd(String wd) throws ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from search where word=?",
				new int[]{
						Types.VARCHAR
				},
				new Object[]{
						wd
				});
	}
	
}
