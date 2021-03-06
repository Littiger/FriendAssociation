package com.quifeng.dao.circle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.ndktools.javamd5.Mademd5;
import com.quifeng.dao.token.TokenDao;
import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @desc 帖子dao
 * @author JZH
 * @time 2020-12-27
 */
public class CircleDao {
	static Dao dao = new DaoImpl();
	static TokenDao tokenDao = new TokenDao();

	/**
	 * 推荐
	 */
	public List<Map<String, Object>> queryAllPost(String hottype, String size, String token)
			throws ClassNotFoundException, SQLException {
		String uid = tokenDao.queryUidByToken(token);
		String schoolid = dao.executeQueryForMap("select * from user where uid=?",new int[]{Types.INTEGER},new Object[]{Integer.parseInt(uid)}).get("schoolid").toString();
		System.out.println(uid);
		String sql = "SELECT u.username,u.uid,p.posttext,p.createtime,p.placaid,p.postid,p.postimg,p.postvideo,"
				+ "u.useravatar,p.schoolid,p2.postzan,p2.postshare,p2.postaos,p2.postos,p2.postsee "
				+ "from user u , "
				+ "(select post.* from post where post.postid not in "
				+ "(SELECT post.postid from user , trilha,post "
				+ "where user.uid=? and user.uid=trilha.uid and post.postid=trilha.postid) "
				+ "and post.schoolid=?  ) p , "
				+ "postinfo p2 , postbk pb "
				+ "where u.uid=p.uid and p.postid=p2.postid and p.display=0 and p.placaid=pb.placaid  "
				+ "and p2.isexamina=1 and pb.isschool=? ORDER BY RAND() LIMIT ?";
		int temp;
		if (hottype.equals("1")) {
			temp = 1;
		} else {
			temp = 0;
		}

		return dao.executeQueryForList(sql, new int[] { Types.INTEGER,Types.INTEGER, Types.INTEGER, Types.INTEGER },
				new Object[] { Integer.parseInt(uid),Integer.parseInt(schoolid), temp, new Integer(size)});
	}

	/**
	 * 推荐广告
	 */
	public Map<String, Object> queryAllPost2() throws ClassNotFoundException, SQLException {
		String sql = "select * from post "
				+ "LEFT JOIN postinfo on post.postid=postinfo.postid "
				+ "LEFT JOIN user u on u.uid=post.uid where post.placaid=0 and "
				+ "postinfo.isexamina=1 and post.display=0 ORDER BY RAND() LIMIT 1";

		return dao.executeQueryForMap(sql);
	}

	/**
	 * 查找用户是否点赞或收藏
	 * 
	 * @param postid
	 * @param token
	 * @return
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> queryUserZanAndAos(String postid, String token)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		String uid = tokenDao.queryUidByToken(token);
		return dao.executeQueryForMap("select z.*,a.* from user"
				+ " LEFT JOIN (select uid zuid from zan where postid=? and display=0 and osid is null) z"
				+ " on user.uid=z.zuid" + " left JOIN (select uid auid from aos where postid=? and display=0 ) a"
				+ " on user.uid=a.auid" + " where user.uid=?",
				new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER },
				new Object[] { Integer.parseInt(postid), Integer.parseInt(postid), Integer.parseInt(uid) });
	}

	/**
	 * 查看是否点赞评论
	 * 
	 * @param postid
	 * @param osid
	 * @param uid
	 * @return
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Map<String, Object> queryOsZan(String postid, String osid, String token)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		String uid = tokenDao.queryUidByToken(token);
		String sql = "select * " + "from zan LEFT JOIN user on zan.uid=user.uid"
				+ " where postid=? and osid=? and user.uid=? and display=0";
		return dao.executeQueryForMap(sql, new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER },
				new Object[] { Integer.parseInt(postid), Integer.parseInt(osid), Integer.parseInt(uid) });
	}

	/**
	 * @Desc 对评论点赞的查询
	 * @param osid
	 * @param utoken
	 * @return
	 */
	public static List<Map<String, Object>> queryOsOneZan(String osid, String utoken) {
		String uid = tokenDao.queryUidByToken(utoken);
		String sql = "select * FROM zan WHERE osid=? and display=0 and uid in (SELECT uid FROM `user` WHERE uid=?)";
		List<Map<String, Object>> map = null;
		try {
			map = dao.executeQueryForList(sql, new int[] { Types.INTEGER, Types.INTEGER },
					new Object[] { osid, Integer.parseInt(uid) });
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 评论的评论数量
	 * 
	 * @param osid
	 * @return
	 */
	public static int queryAll(String osid) {
		String sql = "SELECT count(*) count FROM zan  WHERE osid= ?";
		int count = 0;
		try {
			count = Integer.parseInt(dao.executeQueryForMap(sql, new int[] { Types.INTEGER }, new Object[] { osid })
					.get("count").toString());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	// 实现
	public List<Map<String, Object>> getPostMM(String uid,String placaid, String size, String page) throws NumberFormatException, ClassNotFoundException, SQLException {
		
		if (size == null || page == null) {
			return null;
		}
		//参数转换为int类型
		int size1 = Integer.parseInt(size);
		int page1 = Integer.parseInt(page);
		//获取学校id
		String schoolid = dao.executeQueryForMap("select * from user where uid=?", new int[]{Types.INTEGER}, new Object[]{Integer.parseInt(uid)}).get("schoolid").toString();
		
		//翻页
		if (page1 == 0)
			page1 = 1;
		page1 = (page1 - 1) * size1;
		
		String sql = "select * from post p LEFT JOIN user u on p.uid=u.uid left join postinfo p2 on p2.postid=p.postid where p.display=0 and p.placaid=? and p2.isexamina=1 and p.schoolid=? ORDER BY CAST(p.createtime as SIGNED) desc LIMIT?,?";
		List<Map<String, Object>> list = null;
		try {
			list = dao.executeQueryForList(sql, new int[] { Types.INTEGER,Types.INTEGER, Types.INTEGER, Types.INTEGER },
					new Object[] { placaid,Integer.parseInt(schoolid), page1, size1 });
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取热评
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public List<Map<String, Object>> queryHotOs(String postId)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select * " + "from osfirst "
				+ "LEFT JOIN (select zan.osid,count(zan.osid) count from zan where zan.osid is not null and zan.display=0 GROUP BY zan.osid ) z "
				+ "on osfirst.osfirstid=z.osid " + "LEFT JOIN user on osfirst.uid=user.uid "
				+ "where osfirst.postid=? and osfirst.display=0 " + "ORDER BY z.count desc limit 2",
				new int[] { Types.INTEGER }, new Object[] { new Integer(postId) });
	}

	/**
	 * 获取普通评论
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public List<Map<String, Object>> queryPtOs(String postId)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select osfirst.*,user.username,user.useravatar "
				+ "from osfirst LEFT JOIN user on osfirst.uid=user.uid "
				+ "where osfirst.display=0 and osfirst.postid=? " + "ORDER BY CAST(createtime as SIGNED) desc LIMIT 10",
				new int[] { Types.INTEGER }, new Object[] { new Integer(postId) });
	}

	/**
	 * 帖子信息
	 */
	public Map<String, Object> queryPost(String postId)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from postinfo" + " LEFT JOIN post on postinfo.postid=post.postid"
				+ " LEFT JOIN user on user.uid=post.uid" + " LEFT JOIN postbk on post.placaid=postbk.placaid"
				+ " where post.postid=?", new int[] { Types.INTEGER }, new Object[] { new Integer(postId) });
	}

	/**
	 * 一级评论评论量
	 * 
	 * @param string
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int queryOsCount(String string) throws ClassNotFoundException, SQLException {
		int temp = new Integer(string);
		return dao.executeQueryForInt("select count(*) from osother where osfirstid=? and display=0",
				new int[] { Types.INTEGER }, new Object[] { temp });
	}

	/**
	 * 添加历史记录
	 * 
	 * @param postId
	 * @param placaid
	 * @param schoolid
	 * @param uid
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void addHastory(String postId, String placaid, String schoolid, String uid)
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String sql = "insert into trilha values(0,?,?,?,?,0,?,0)";
		dao.executeUpdate(sql, new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.INTEGER },
				new Object[] { Integer.parseInt(uid), Integer.parseInt(postId), Integer.parseInt(placaid),
						System.currentTimeMillis(), Integer.parseInt(schoolid) });
	}

	/**
	 * 帖子观看量+1
	 */
	public void addPostSee(String postId)
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao.executeUpdate("update postinfo set postsee=postsee+1 where postid=?", new int[] { Types.INTEGER },
				new Object[] { Integer.parseInt(postId) });
	}

	/**
	 * 获取评论点赞量
	 * 
	 * @param object
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public int queryOsZan(String osid) throws NumberFormatException, ClassNotFoundException, SQLException {
		try {
			return Integer.parseInt(dao
					.executeQueryForMap("select count(*) count from zan where zan.osid=? and zan.display=0",
							new int[] { Types.INTEGER }, new Object[] { Integer.parseInt(osid) })
					.get("count").toString());
		} catch (Exception e) {
			return 0;
		}

	}

	/**
	 * 根据id查用户信息
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
	 * 获取一级评论
	 * 
	 * @param postid
	 * @param size
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getOSfir(String postid, String size, String page) {

		if (size == null || page == null) {
			return null;
		}

		int size1 = Integer.parseInt(size);
		int page1 = Integer.parseInt(page);
		// page1= size1*(page1-1);

		if (page1 == 0)
			page1 = 1;
		page1 = (page1 - 1) * size1;

		String sql = "select * from osfirst o LEFT JOIN user u on u.uid=o.uid where o.display=0 and o.postid=? ORDER BY CAST(o.createtime as SIGNED) DESC LIMIT ?,?";
		List<Map<String, Object>> list = null;
		try {
			list = dao.executeQueryForList(sql, new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER },
					new Object[] { postid, page1, size1 });
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取所有评论
	 * 
	 * @param postid
	 * @return
	 */
	public int getAllOSF(String postid) {
		String sql = "SELECT count(*) FROM osfirst WHERE postid=?";
		int count = 0;
		try {
			count = dao.executeQueryForInt(sql, new int[] { Types.INTEGER,

			}, new Object[] { postid });
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public Object getAllZan(String osfirstid) throws Exception {
		String sql = "select count(*) from osother where osfirstid=?";
		int count = 0;
		try {
			count = dao.executeQueryForInt(sql, new int[] { Types.INTEGER,

			}, new Object[] { osfirstid });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 发布评论
	 * 
	 * @param uid
	 * @param postid
	 * @param createtime
	 * @param ostxt
	 * @param schoolid
	 * @return
	 */
	public int addOsfirst(String uid, String postid, String createtime, String ostxt, String schoolid) {
		String sql = "INSERT INTO  osfirst(postid,uid,createtime,ostext,schoolid) VALUES (?,?,?,?,?)";
		int count = 0;
		try {
			count = dao.executeUpdate(sql,
					new int[] { Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER },
					new Object[] { postid, uid, createtime, ostxt, schoolid });
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 根据id获取一级评论详细信息
	 * 
	 * @param comment
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public Map<String, Object> queryFiOsMessageById(String comment, String token)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		String uid = tokenDao.queryUidByToken(token);
		String sql = "select * from osfirst LEFT JOIN user on osfirst.uid=user.uid where osfirst.uid=? and osfirst.osfirstid=?";
		return dao.executeQueryForMap(sql, new int[] { Types.INTEGER, Types.INTEGER },
				new Object[] { Integer.parseInt(uid), Integer.parseInt(comment) });
	}

	/**
	 * 根据id获取一级评论详细信息
	 * 
	 * @param comment
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public Map<String, Object> queryFiOsMessageById(String comment)
			throws NumberFormatException, ClassNotFoundException, SQLException {

		String sql = "select * from osfirst LEFT JOIN user on osfirst.uid=user.uid where osfirst.osfirstid=?";
		return dao.executeQueryForMap(sql, new int[] { Types.INTEGER

		}, new Object[] { Integer.parseInt(comment) });
	}

	/**
	 * 查看是否点赞该评论
	 * 
	 * @param string
	 * @param token
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public Map<String, Object> queryOsZan(String osid, String token)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		System.out.println(osid);
		String uid = tokenDao.queryUidByToken(token);
		return dao.executeQueryForMap("select * from zan where osid=? and uid=? and display=0",
				new int[] { Types.INTEGER, Types.INTEGER },
				new Object[] { Integer.parseInt(osid), Integer.parseInt(uid) });
	}

	/**
	 * 添加一级评论
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public long addOsFiOs(String postid, String uid, String comment, String schoolid)
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		long time = System.currentTimeMillis();
		// osfirst表+一条评论
		dao.executeUpdate("insert into osfirst values(0,?,?,?,?,0,?,0)",
				new int[] { Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER }, new Object[] {
						Integer.parseInt(postid), Integer.parseInt(uid), time, comment, Integer.parseInt(schoolid) });
		return time;

	}

	/**
	 * 帖子评论数+1
	 * 
	 * @param postid
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void addPostOsCount(String postid)
			throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		// postinfo postos+1 评论数+1
		dao.executeUpdate("update postinfo set postos=postos+1 where postid=?", new int[] { Types.INTEGER },
				new Object[] { Integer.parseInt(postid) });
	}

	/**
	 * 添加二级评论
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public long addSeOs(String osfirstid, String comment, String schoolid, String uid)
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		int id = Integer.parseInt(osfirstid);
		long time = System.currentTimeMillis();
		// osother表添加评论
		dao.executeUpdate("insert into osother values(0,?,?,?,0,?,?,?,0)",
				new int[] { Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.INTEGER },
				new Object[] { id, comment, -id, time, Integer.parseInt(schoolid), Integer.parseInt(uid) });
		return time;
	}

	/**
	 * 添加二级以上评论
	 * 
	 * @param osfirstid
	 * @param comment
	 * @param string
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public long addOthOs(String osfirstid, String comment, String schoolid, String uid)
			throws NumberFormatException, ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		int idTemp = Integer.parseInt(osfirstid);
		long time = System.currentTimeMillis();
		int id = Integer.parseInt(dao.executeQueryForMap("select * from osother where osotherid=?",
				new int[] { Types.INTEGER }, new Object[] { -idTemp }).get("osfirstid").toString());
		dao.executeUpdate("insert into osother values(0,?,?,?,0,?,?,?)",
				new int[] { Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.INTEGER },
				new Object[] { id, comment, -idTemp, time + "", Integer.parseInt(schoolid), Integer.parseInt(uid) });
		return time;
	}

	/**
	 * 帖子点赞
	 * 
	 * @param postid
	 * @param token
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void addPostZan(String postid, String token)
			throws NumberFormatException, ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		String uid = tokenDao.queryUidByToken(token);
		// 获取display
		Map<String, Object> display = dao.executeQueryForMap(
				"select * from zan where postid=? and uid=? and osid is null",
				new int[] { Types.INTEGER, Types.INTEGER },
				new Object[] { Integer.parseInt(postid), Integer.parseInt(uid) });
		// 点过赞
		if (display != null) {
			// 取消点赞
			if (display.get("display").toString().equals("0")) {
				dao.executeUpdate("update zan set display=1 where postid=? and uid=? and osid is null",
						new int[] { Types.INTEGER, Types.INTEGER },
						new Object[] { Integer.parseInt(postid), Integer.parseInt(uid) });
				// postinfo postzan-1
				dao.executeUpdate("update postinfo set postzan=postzan-1 where postid=?", new int[] { Types.INTEGER },
						new Object[] { Integer.parseInt(postid) });
			}
			// 点赞
			else if (display.get("display").toString().equals("1")) {
				dao.executeUpdate("update zan set display=0 where postid=? and uid=? and osid is null",
						new int[] { Types.INTEGER, Types.INTEGER },
						new Object[] { Integer.parseInt(postid), Integer.parseInt(uid) });
				// postinfo postzan+1
				dao.executeUpdate("update postinfo set postzan=postzan+1 where postid=?", new int[] { Types.INTEGER },
						new Object[] { Integer.parseInt(postid) });
			}
		}
		// 第一次点赞
		else {
			dao.executeUpdate("insert into zan values(0,?,?,0,?,null,0)",
					new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER }, new Object[] { Integer.parseInt(postid),
							Integer.parseInt(uid), Integer.parseInt(queryUserById(uid).get("schoolid").toString()), });
			// postinfo postzan+1
			dao.executeUpdate("update postinfo set postzan=postzan+1 where postid=?", new int[] { Types.INTEGER },
					new Object[] { Integer.parseInt(postid) });
		}

	}

	/**
	 * 查询点赞情况
	 * 
	 * @param postid
	 * @param token
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public Map<String, Object> queryPostZan(String postid, String token)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		String uid = tokenDao.queryUidByToken(token);
		// 获取display
		return dao.executeQueryForMap("select * from zan where postid=? and uid=? and osid is null",
				new int[] { Types.INTEGER, Types.INTEGER },
				new Object[] { Integer.parseInt(postid), Integer.parseInt(uid) });
	}

	/**
	 * 获取用户点赞评论信息
	 * 
	 * @param commentid
	 * @param token
	 * @return
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> isOsZan(String commentid, String token)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		String uid = tokenDao.queryUidByToken(token);
		return dao.executeQueryForMap("select * from zan where osid=? and uid=?",
				new int[] { Types.INTEGER, Types.INTEGER },
				new Object[] { Integer.parseInt(commentid), Integer.parseInt(uid) });
	}

	/**
	 * 添加评论赞
	 * 
	 * @param commentid
	 * @param token
	 * @throws IOException
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public void addOsZan(String commentid, String token)
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String uid = tokenDao.queryUidByToken(token);
		String osfirstid = "";
		String postid = "";
		// 二级评论 先查osother
		if (Integer.parseInt(commentid) < 0) {
			osfirstid = dao.executeQueryForMap("select * from osother where osotherid=?", new int[] { Types.INTEGER },
					new Object[] { -Integer.parseInt(commentid) }).get("osfirstid").toString();
			postid = dao.executeQueryForMap("select * from osfirst where osfirstid=?", new int[] { Types.INTEGER },
					new Object[] { Integer.parseInt(osfirstid) }).get("postid").toString();
		}
		// 一级评论
		else {
			postid = dao.executeQueryForMap("select * from osfirst where osfirstid=?", new int[] { Types.INTEGER },
					new Object[] { Integer.parseInt(commentid) }).get("postid").toString();
		}
		// 添加赞
		dao.executeUpdate("insert into zan values(0,?,?,0,?,?,0)",
				new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER },
				new Object[] { Integer.parseInt(postid), Integer.parseInt(uid),
						Integer.parseInt(queryUserById(uid).get("schoolid").toString()), Integer.parseInt(commentid)

				});
	}

	/**
	 * 取消赞
	 * 
	 * @param commentid
	 * @param token
	 * @throws IOException
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public void DelOsZan(String commentid, String token)
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String uid = tokenDao.queryUidByToken(token);
		dao.executeUpdate("update zan set display=1 where osid=? and uid=?", new int[] { Types.INTEGER, Types.INTEGER },
				new Object[] { Integer.parseInt(commentid), Integer.parseInt(uid) });
	}

	/**
	 * 点赞
	 * 
	 * @param commentid
	 * @param token
	 * @throws IOException
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public void addOsZan2(String commentid, String token)
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String uid = tokenDao.queryUidByToken(token);
		dao.executeUpdate("update zan set display=0 where osid=? and uid=?", new int[] { Types.INTEGER, Types.INTEGER },
				new Object[] { Integer.parseInt(commentid), Integer.parseInt(uid) });
	}

	/**
	 * 分享表添加数据(返回秘钥)
	 * 
	 * @param postid
	 * @param token
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public String addShare(String postid, String token)
			throws NumberFormatException, ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		String uid = tokenDao.queryUidByToken(token);
		String schoolid = queryUserById(uid).get("schoolid").toString();
		//分享时间
		String time = System.currentTimeMillis()+"";
		//生成加密秘钥
		String key =new Mademd5().toMd5(uid+time);
		
		dao.executeUpdate("insert into share values(0,?,?,0,?,?,?,0)",
				new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER,Types.VARCHAR,Types.VARCHAR},
				new Object[] { Integer.parseInt(postid), Integer.parseInt(uid), Integer.parseInt(schoolid),time,key});
		
		return key;
	}

	/**
	 * 获取二级和二级以上评论信息
	 * 
	 * @param comment
	 * @param token
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public List<Map<String, Object>> queryOthOs(String comment)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		String sql = "select * from osother " + "LEFT JOIN user on osother.uid=user.uid "
				+ "LEFT JOIN (select zan.osid,count(*) zcount from zan GROUP BY osid) z on osother.osotherid=z.osid "
				+ "where osother.osfirstid=? and osother.display=0 ORDER BY CAST(osother.createtime as SIGNED) desc";
		return dao.executeQueryForList(sql, new int[] { Types.INTEGER }, new Object[] { Integer.parseInt(comment) });
	}

	/**
	 * @desc 收藏帖子
	 * @param postid
	 * @param token
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void addPostAos(String postid, String token)
			throws NumberFormatException, ClassNotFoundException, SQLException, FileNotFoundException, IOException {

		// 获取用户id
		String uid = tokenDao.queryUidByToken(token);

		// 获取该条收藏信息
		Map<String, Object> map = dao.executeQueryForMap("select * from aos where postid=? and uid=?",
				new int[] { Types.INTEGER, Types.INTEGER },
				new Object[] { Integer.parseInt(postid), Integer.parseInt(uid) });
		// 第一次收藏
		if (map == null) {
			dao.executeUpdate("insert into aos values(0,?,?,'0',?,0)",
					new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER }, new Object[] { Integer.parseInt(postid),
							Integer.parseInt(uid), Integer.parseInt(queryUserById(uid).get("schoolid").toString()), });
			// postinfo postzan+1
			dao.executeUpdate("update postinfo set postaos=postaos+1 where postid=?", new int[] { Types.INTEGER },
					new Object[] { Integer.parseInt(postid) });

		}
		// 已收藏
		else {
			String display = map.get("display").toString();
			// 取消收藏
			if (display.equals("0")) {
				dao.executeUpdate("update aos set display='1' where postid=? and uid=?",
						new int[] { Types.INTEGER, Types.INTEGER },
						new Object[] { Integer.parseInt(postid), Integer.parseInt(uid) });
				// postinfo postaos-1
				dao.executeUpdate("update postinfo set postaos=postaos-1 where postid=?", new int[] { Types.INTEGER },
						new Object[] { Integer.parseInt(postid) });
			}
			//  收藏
			else if (display.equals("1")) {
				dao.executeUpdate("update aos set display='0' where postid=? and uid=?",
						new int[] { Types.INTEGER, Types.INTEGER },
						new Object[] { Integer.parseInt(postid), Integer.parseInt(uid) });
				// postinfo postaos+1
				dao.executeUpdate("update postinfo set postaos=postaos+1 where postid=?", new int[] { Types.INTEGER },
						new Object[] { Integer.parseInt(postid) });
			}
		}

	}

	/**
	 * 查询收藏
	 * 
	 * @param postid
	 * @param token
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public Map<String, Object> queryPostAos(String postid, String token)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		String uid = tokenDao.queryUidByToken(token);
		// 获取display
		return dao.executeQueryForMap("select * from aos where postid=? and uid=?",
				new int[] { Types.INTEGER, Types.INTEGER },
				new Object[] { Integer.parseInt(postid), Integer.parseInt(uid) });
	}

	/**
	 * 评论二级评论的三级评论数量
	 * 
	 * @param string
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public String querySEOs(String osotherid) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select count(*) count from osother where upid=?", new int[] { Types.INTEGER },
				new Object[] { Integer.parseInt(osotherid) }).get("count").toString();
	}

	/**
	 * 根据id查询评论信息
	 * 
	 * @param string
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public Map<String, Object> queryOthOsById(String osid)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap(
				"select * from osother" + " LEFT JOIN user on osother.uid=user.uid"
						+ " LEFT JOIN (select zan.osid,count(*) zcount from zan GROUP BY osid) z"
						+ " on osother.osotherid=z.osid" + " where osotherid=?",
				new int[] { Types.INTEGER }, new Object[] { Integer.parseInt(osid) });

	}

	/**
	 * 分页获取二级或以上评论
	 * 
	 * @param comment
	 * @param page
	 * @param count
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public List<Map<String, Object>> queryOthOs(String comment, String page, String count)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		int count1 = Integer.parseInt(count);
		int page1 = Integer.parseInt(page);

		if (page1 == 0)
			page1 = 1;
		page1 = (page1 - 1) * count1;
		String sql = "select * from osother " + "LEFT JOIN user on osother.uid=user.uid "
				+ "LEFT JOIN (select zan.osid,count(*) zcount from zan GROUP BY osid) z on osother.osotherid=z.osid "
				+ "where osother.osfirstid=? and osother.display=0 ORDER BY CAST(osother.createtime as SIGNED) desc limit ?,?";
		return dao.executeQueryForList(sql, new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER },
				new Object[] { Integer.parseInt(comment), page1, count1 });
	}

	/**
	 * 增加分享量
	 * 
	 * @param postid
	 * @throws IOException
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public void updateShare(String postid)
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao.executeUpdate("update postinfo set postshare=postshare+1 where postid=?", new int[] { Types.INTEGER },
				new Object[] { Integer.parseInt(postid) });
	}

	/**
	 * 根据id获取板块信息
	 * 
	 * @param string
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public Map<String, Object> queryPlacaById(String id)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from postbk where placaid=?", new int[] { Types.INTEGER },
				new Object[] { Integer.parseInt(id) });
	}

	/**
	 * 根据id和时间查询评论信息
	 */
	public Map<String, Object> queryFiOsMessageById(String uid, long time)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		String sql = "select * from osfirst LEFT JOIN user on osfirst.uid=user.uid where osfirst.uid=? and osfirst.createtime=?";
		return dao.executeQueryForMap(sql, new int[] { Types.INTEGER, Types.VARCHAR },
				new Object[] { Integer.parseInt(uid), time + "" });
	}

	public Map<String, Object> queryOthOsMessageById(String uid, long time)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		String sql = "select * from osother LEFT JOIN user on osother.uid=user.uid where osother.uid=? and osother.createtime=?";
		return dao.executeQueryForMap(sql, new int[] { Types.INTEGER, Types.VARCHAR },
				new Object[] { Integer.parseInt(uid), time + "" });
	}

	/**
	 * 根据上级评论id查看是否有该一级评论
	 * 
	 * @param osfirstid
	 * @param postid
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public Map<String, Object> queryFiOs(String osfirstid, String postid)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from osfirst where osfirstid=? and postid=?",
				new int[] { Types.INTEGER, Types.INTEGER },
				new Object[] { Integer.parseInt(osfirstid), Integer.parseInt(postid) });
	}

	/**
	 * 通过分享秘钥查询信息
	 * @param id
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String, Object> querySharePostInfo(String id) throws ClassNotFoundException, SQLException {
		String sql = "select share.uid shareuid,share.postid,"
				+ "postinfo.postzan,postinfo.postos,postinfo.postshare,"
				+ "post.createtime,post.posttext,post.postimg,post.postvideo,"
				+ "u.username uname,u.useravatar,"
				+ "postbk.placaid,postbk.placaname from share "
				+ "LEFT JOIN post on post.postid = share.postid "
				+ "LEFT JOIN postinfo on post.postid=postinfo.postid "
				+ "LEFT JOIN postbk on post.placaid=postbk.placaid "
				+ "LEFT JOIN user u on post.uid=u.uid "
				+ "where share.key=?";
		return dao.executeQueryForMap(sql, new int[]{Types.VARCHAR}, new Object[]{id});
	}
	
	/**
	 * 根据id查询帖子
	 * @param postid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> queryPostById(String postid) throws NumberFormatException, ClassNotFoundException, SQLException {
		String sql = "select * from post LEFT JOIN postinfo on post.postid=postinfo.postid where post.display=0 and postinfo.isexamina=1 and post.postid=?";
		return dao.executeQueryForMap(sql, new int[]{Types.INTEGER}, new Object[]{Integer.parseInt(postid)});
	}

}
