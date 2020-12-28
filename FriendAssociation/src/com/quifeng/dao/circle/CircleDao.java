package com.quifeng.dao.circle;

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
 * @desc   帖子dao
 * @author JZH
 * @time   2020-12-27
 */
public class CircleDao {
	static Dao dao = new DaoImpl();
	static TokenDao tokenDao = new TokenDao();
	
	/**
	 * 推荐
	 */
	public List<Map<String, Object>> queryAllPost(String hottype, String size,String token) throws ClassNotFoundException, SQLException {
		String uid = tokenDao.queryUidByToken(token);
		System.out.println(uid);
		String sql = "SELECT u.username,p.posttext,p.createtime,p.placaid,p.postid,p.postimg,p.postvideo,u.useravatar,p.schoolid"
				+ " from user u , (select post.* from post,user where not exists (SELECT postid from user , trilha where user.uid=? and user.uid=trilha.uid and post.postid=postid )and user.uid=? and post.schoolid=user.schoolid) p ,"
				+ " postinfo p2 , postbk pb"
				+ " where u.uid=p.uid and p.postid=p2.postid and p.display=0 and p.placaid=pb.placaid  and p2.isexamina=1 and pb.isschool=?"
				+ " ORDER BY RAND() LIMIT ?";
		int temp;
		if(hottype.equals("1")){
			temp=1;
		}
		else{
			temp=0;
		}
		
		return dao.executeQueryForList(sql,
				new int[]{
						Types.INTEGER,
						Types.INTEGER,
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(uid),
						Integer.parseInt(uid),
						temp,
						new Integer(size)
				});
	}
	/**
	 * 推荐广告
	 */
	public List<Map<String, Object>> queryAllPost2(String hottype, String size,String token) throws ClassNotFoundException, SQLException {
		String uid = tokenDao.queryUidByToken(token);
		System.out.println(token);
		String sql = "SELECT u.username,p.posttext,p.createtime,p.placaid,p.postid,p.postimg,p.postvideo,u.useravatar"
				+ " from user u ,"
				+ " (select * from post where not exists (SELECT postid from user , trilha where user.uid=? and user.uid=trilha.uid and post.postid=postid )) p ,"
				+ " postinfo p2 , postbk pb "
				+ "where u.uid=p.uid and p.postid=p2.postid and p.display=0 and p.placaid=pb.placaid  and p.placaid like ? and p2.isexamina=1 and pb.isschool=?"
				+ " ORDER BY RAND() LIMIT ?";
		int temp;
		if(hottype.equals("1")){
			temp=1;
		}
		else{
			temp=0;
		}
		
		return dao.executeQueryForList(sql, new int[]{
				Types.INTEGER,
				Types.INTEGER,
				Types.INTEGER,
				Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(uid),
						new Integer(hottype),
						temp,
						new Integer(size)
				});
	}
	/**
	 * 查找用户是否点赞或收藏
	 * @param postid
	 * @param token
	 * @return
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> queryUserZanAndAos(String postid,String token) throws NumberFormatException, ClassNotFoundException, SQLException{
		String uid = tokenDao.queryUidByToken(token);
		return dao.executeQueryForMap("select z.*,a.* from user"
				+ " LEFT JOIN (select uid zuid from zan where postid=? and display=0 and osid is null) z"
				+ " on user.uid=z.zuid"
				+ " left JOIN (select uid auid from aos where postid=? and display=0 ) a"
				+ " on user.uid=a.auid"
				+ " where user.uid=?",
				new int[]{
						Types.INTEGER,
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(postid),
						Integer.parseInt(postid),
						Integer.parseInt(uid)
				});
	}
	/**
	 * 查看是否点赞评论
	 * @param postid
	 * @param osid
	 * @param uid
	 * @return
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Map<String, Object> queryOsZan(String postid,String osid, String token) throws NumberFormatException, ClassNotFoundException, SQLException{
		String uid = tokenDao.queryUidByToken(token);
		String sql = "select * "
				+ "from zan LEFT JOIN user on zan.uid=user.uid"
				+ " where postid=? and osid=? and user.uid=? and display=0";
		return dao.executeQueryForMap(sql,
				new int[]{
						Types.INTEGER,
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(postid),
						Integer.parseInt(osid),
						Integer.parseInt(uid)
				});
	}
	
	/**
	 * @Desc 对评论点赞的查询
	 * @param osid
	 * @param utoken
	 * @return
	 */
	public static List<Map<String, Object>> queryOsOneZan(String osid, String utoken){
		String uid = tokenDao.queryUidByToken(utoken);
		String sql = "select * FROM zan WHERE osid=? and display=0 and uid in (SELECT uid FROM `user` WHERE uid=?)";
		List<Map<String, Object>> map =null;
		try {
			map = dao.executeQueryForList(sql,
					new int[]{
							Types.INTEGER,
							Types.INTEGER
					},
					new Object[]{
						osid,
						Integer.parseInt(uid)	
					});
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	/**
	 * 评论的评论数量
	 * @param osid
	 * @return
	 */
	public static int queryAll(String osid){
		String sql = "SELECT count(*) count FROM zan  WHERE osid= ?";
		int count = 0;
		try {
			count=Integer.parseInt(dao.executeQueryForMap(sql, new int[]{
					Types.INTEGER
			}, new Object[]{
					osid
			}).get("count").toString());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	// 实现
	public List<Map<String, Object>> getPostMM(String placaid,  String size, String page) {

		if (size==null||page==null) {
			return null;
		}
		
		int size1 = Integer.parseInt(size);
		int page1 = Integer.parseInt(page);
		
		if (page1==0) page1=1;
		page1=(page1-1)*size1;
		String sql="select * from post p LEFT JOIN user u on p.uid=u.uid where p.display=0 and p.placaid=? ORDER BY CAST(p.createtime as SIGNED) desc LIMIT?,?"; 
		List<Map<String, Object>>  list=null;
		try {
			 list = dao.executeQueryForList(sql, new int[]{
					Types.INTEGER,
					Types.INTEGER,
					Types.INTEGER
			}, new Object[]{
					placaid,page1,size1
			});
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return list;
	}  
	/**
	 * 获取热评
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public List<Map<String, Object>> queryHotOs(String postId) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select * "
				+ "from osfirst "
				+ "LEFT JOIN (select zan.osid,count(zan.osid) count from zan where zan.osid is not null and zan.display=0 GROUP BY zan.osid ) z "
				+ "on osfirst.osfirstid=z.osid "
				+ "LEFT JOIN user on osfirst.uid=user.uid "
				+ "where osfirst.postid=? and osfirst.display=0 "
				+ "ORDER BY z.count desc limit 2",
				new int[]{Types.INTEGER},
				new Object[]{new Integer(postId)});
	}
	/**
	 * 获取普通评论
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public List<Map<String, Object>> queryPtOs(String postId) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForList("select osfirst.*,user.username,user.useravatar "
				+ "from osfirst LEFT JOIN user on osfirst.uid=user.uid "
				+ "where display=0 and postid=? "
				+ "ORDER BY CAST(createtime as SIGNED) desc LIMIT 10",
				new int[]{
						Types.INTEGER
				},
				new Object[]{
						new Integer(postId)
				});
	}
	/**
	 * 帖子信息
	 */
	public Map<String, Object> queryPost(String postId) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from postinfo"
				+ " LEFT JOIN post on postinfo.postid=post.postid"
				+ " LEFT JOIN user on user.uid=post.uid"
				+ " LEFT JOIN postbk on post.placaid=postbk.placaid"
				+ " where post.postid=?",
				new int[]{Types.INTEGER},
				new Object[]{new Integer(postId)});
	}
	/**
	 * 一级评论评论量
	 * @param string
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int queryOsCount(String string) throws ClassNotFoundException, SQLException {
		int temp = new Integer(string);
		return dao.executeQueryForInt("select count(*) from osother where osfirstid=? and display=0", new int[]{Types.INTEGER}, new Object[]{temp});
	}
	/**
	 * 添加历史记录
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
	public void addHastory( String postId, String placaid, String schoolid,String uid) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String sql = "insert into trilha values(0,?,?,?,?,0,?)";
		dao.executeUpdate(sql,
				new int[]{
						Types.INTEGER,
						Types.INTEGER,
						Types.INTEGER,
						Types.VARCHAR,
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(uid),
						Integer.parseInt(postId),
						Integer.parseInt(placaid),
						System.currentTimeMillis(),
						Integer.parseInt(schoolid)
				});
	}
	/**
	 * 帖子观看量+1
	 */
	public void addPostSee(String postId) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao.executeUpdate("update postinfo set postsee=postsee+1 where postid=?",
				new int[]{
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(postId)
				});
	}
	/**
	 * 获取评论点赞量
	 * @param object
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public int queryOsZan(String osid) throws NumberFormatException, ClassNotFoundException, SQLException {
		try {
			return Integer.parseInt(
					dao.executeQueryForMap("select count(*) count from zan where zan.osid=? and zan.display=0",
							new int[]{
									Types.INTEGER
							},
							new Object[]{
									Integer.parseInt(osid)
							}).get("count").toString()
					);
		} catch (Exception e) {
			return 0;
		}
		
	}
	/**
	 * 根据id查用户信息
	 * @param uid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> queryUserById(String uid) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from user where uid=?",
				new int[]{
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(uid)
				});
	}
	/**
	 * 获取一级评论
	 * @param postid
	 * @param size
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getOSfir(String postid, String size, String page) {
		
		if (size==null||page==null) {
			return null;
		}
		
		int size1 = Integer.parseInt(size);
		int page1 = Integer.parseInt(page);
		//page1= size1*(page1-1);	
		
		if (page1==0) page1=1;
		page1=(page1-1)*size1;
		
		String sql = "select * from osfirst o LEFT JOIN user u on u.uid=o.uid where o.display=0 and o.postid=? ORDER BY CAST(o.createtime as SIGNED) DESC LIMIT ?,?";
		List<Map<String, Object>> list = null;
		try {
			list = dao.executeQueryForList(sql, new int[]{
					Types.INTEGER,
					Types.INTEGER,
					Types.INTEGER
			}, new Object[]{
					postid,page1,size1
			});
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 获取所有评论
	 * @param postid
	 * @return
	 */
	public int getAllOSF(String postid) {
		String sql = "SELECT count(*) FROM osfirst WHERE postid=?";
		int count =0 ;
		try {
			count = dao.executeQueryForInt(sql, new int[]{
					Types.INTEGER,

			}, new Object[]{
					postid
			});
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public Object getAllZan(String osfirstid) throws Exception{
		String sql = "select count(*) from osother where osfirstid=?";
		int count =0 ;
		try {
			count = dao.executeQueryForInt(sql, new int[]{
					Types.INTEGER,

			}, new Object[]{
					osfirstid
			});
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	
	/**
	 * 发布评论
	 * @param uid
	 * @param postid
	 * @param createtime
	 * @param ostxt
	 * @param schoolid
	 * @return
	 */
	public int addOsfirst(String uid, String postid, String createtime, String ostxt ,String schoolid) {
		String sql = "INSERT INTO  osfirst(postid,uid,createtime,ostext,schoolid) VALUES (?,?,?,?,?)";
		int count = 0 ;
		try {
			count = dao.executeUpdate(sql ,new int[]{
					Types.INTEGER,
					Types.INTEGER,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.INTEGER
			},new Object[]{
				postid,uid,createtime,ostxt,schoolid	
			});
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 根据id获取一级评论详细信息
	 * @param comment
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> queryFiOsMessageById(String comment,String token) throws NumberFormatException, ClassNotFoundException, SQLException {
		String uid = tokenDao.queryUidByToken(token);
		String sql = "select * from osfirst LEFT JOIN user on osfirst.uid=user.uid where osfirst.uid=? and osfirst.osfirstid=?";
		return dao.executeQueryForMap(sql,
				new int[]{
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
					Integer.parseInt(uid),
					Integer.parseInt(comment)
				});
	}
	/**
	 * 查看是否点赞该评论
	 * @param string
	 * @param token
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> queryOsZan(String osid, String token) throws NumberFormatException, ClassNotFoundException, SQLException {
		String uid = tokenDao.queryUidByToken(token);
		return dao.executeQueryForMap("select * from zan where osid=? and uid=? and display=0",
				new int[]{
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(osid),
						Integer.parseInt(uid)
				});
	}
	/**
	 * 添加一级评论
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public void addOsFiOs(String postid, String uid, String comment,String schoolid) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		//osfirst表+一条评论
		dao.executeUpdate("insert into osfirst v	alues(0,?,?,?,?,0,?)",
				new int[]{
						Types.INTEGER,
						Types.INTEGER,
						Types.VARCHAR,
						Types.VARCHAR,
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(postid),
						Integer.parseInt(uid),
						System.currentTimeMillis(),
						comment,
						Integer.parseInt(schoolid)
				});
		addPostOsCount(postid);
		
	}
	/**
	 * 帖子评论数+1
	 * @param postid
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void addPostOsCount(String postid) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		//postinfo postos+1 评论数+1
		dao.executeUpdate("update postinfo set postos=postos+1 where postid=?",
				new int[]{
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(postid)
				});
	}
	/**
	 * 添加二级评论
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public void addSeOs(String osfirstid, String comment, String schoolid,String uid) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		int id = Integer.parseInt(osfirstid);
		//osother表添加评论
		dao.executeUpdate("insert into osother values(0,?,?,?,0,?,?,?)",
				new int[]{
						Types.INTEGER,
						Types.VARCHAR,
						Types.INTEGER,
						Types.VARCHAR,
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						id,
						comment,
						-id,
						System.currentTimeMillis(),
						Integer.parseInt(schoolid),
						Integer.parseInt(uid)
				});
	}
	/**
	 * 添加二级以上评论
	 * @param osfirstid
	 * @param comment
	 * @param string
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void addOthOs(String osfirstid, String comment, String schoolid,String uid) throws NumberFormatException, ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		int idTemp = Integer.parseInt(osfirstid);
		int id=Integer.parseInt(
				dao.executeQueryForMap("select * from osother where osotherid=?",
						new int[]{
								Types.INTEGER
						},
						new Object[]{
								-idTemp
						}).get("osfirstid").toString()
				);
		dao.executeUpdate("insert into osother values(0,?,?,?,0,?,?,?)",
				new int[]{
						Types.INTEGER,
						Types.VARCHAR,
						Types.INTEGER,
						Types.VARCHAR,
						Types.INTEGER,
						Types.INTEGER
				},new Object[]{
						id,
						comment,
						-idTemp,
						System.currentTimeMillis(),
						Integer.parseInt(schoolid),
						Integer.parseInt(uid)
				});
	}
	/**
	 * 帖子点赞
	 * @param postid
	 * @param token
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void addPostZan(String postid, String token) throws NumberFormatException, ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		String uid = tokenDao.queryUidByToken(token);
		//获取display
		String display = dao.executeQueryForMap("select * from zan where postid=? and uid=? and osid is null",
						new int[]{
								Types.INTEGER,
								Types.INTEGER
						},
						new Object[]{
								Integer.parseInt(postid),
								Integer.parseInt(uid)
						}).get("display").toString();
		//点过赞
		if(display != null){
			//取消点赞
			if(display.equals("0")){
				dao.executeUpdate("update zan set display=1 where postid=? and uid=? and osid is null",
						new int[]{
								Types.INTEGER,
								Types.INTEGER
						},
						new Object[]{
								Integer.parseInt(postid),
								Integer.parseInt(uid)	
						});
				//postinfo  postzan-1
				dao.executeUpdate("update postinfo set postzan=postzan-1 where postid=?",
						new int[]{
								Types.INTEGER
						},
						new Object[]{
								Integer.parseInt(postid)
						});
			}
			//点赞
			else if(display.equals("1")){
				dao.executeUpdate("update zan set display=0 where postid=? and uid=? and osid is null",
						new int[]{
								Types.INTEGER,
								Types.INTEGER
						},
						new Object[]{
								Integer.parseInt(postid),
								Integer.parseInt(uid)	
						});
				//postinfo  postzan+1
				dao.executeUpdate("update postinfo set postzan=postzan+1 where postid=?",
						new int[]{
								Types.INTEGER
						},
						new Object[]{
								Integer.parseInt(postid)
						});
			}
		}
		//第一次点赞
		else{
			dao.executeUpdate("insert into zan values(0,?,?,0,?,null)",
					new int[]{
							Types.INTEGER,
							Types.INTEGER,
							Types.INTEGER
					},
					new Object[]{
							Integer.parseInt(postid),
							Integer.parseInt(uid),
							Integer.parseInt(queryUserById(uid).get("schoolid").toString()),
					});
			//postinfo  postzan+1
			dao.executeUpdate("update postinfo set postzan=postzan+1 where postid=?",
					new int[]{
							Types.INTEGER
					},
					new Object[]{
							Integer.parseInt(postid)
					});
		}
		
	}
	/**
	 * 查询点赞情况
	 * @param postid
	 * @param token
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> queryPostZan(String postid, String token) throws NumberFormatException, ClassNotFoundException, SQLException {
		String uid = tokenDao.queryUidByToken(token);
		//获取display
		return dao.executeQueryForMap("select * from zan where postid=? and uid=? and osid is null",
						new int[]{
								Types.INTEGER,
								Types.INTEGER
						},
						new Object[]{
								Integer.parseInt(postid),
								Integer.parseInt(uid)
						});
	}
	/**
	 * 获取用户点赞评论信息
	 * @param commentid
	 * @param token
	 * @return
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> isOsZan(String commentid, String token) throws NumberFormatException, ClassNotFoundException, SQLException {
		String uid = tokenDao.queryUidByToken(token);
		return dao.executeQueryForMap("select * from zan where osid=? and uid=?",
				new int[]{
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(commentid),
						Integer.parseInt(uid)
				});
	}
	/**
	 * 添加评论赞
	 * @param commentid
	 * @param token
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public void addOsZan(String commentid, String token) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String uid = tokenDao.queryUidByToken(token);
		String osfirstid = "";
		String postid="";
		//二级评论 先查osother
		if(Integer.parseInt(commentid) < 0){
			osfirstid = dao.executeQueryForMap("select * from osother where osotherid=?",
					new int[]{
							Types.INTEGER
					},
					new Object[]{
							-Integer.parseInt(commentid)
					}).get("osfirstid").toString();
			postid = dao.executeQueryForMap("select * from osfirst where osfirstid=?",
					new int[]{
							Types.INTEGER
					},
					new Object[]{
							Integer.parseInt(osfirstid)
					}).get("postid").toString();
		}
		//一级评论
		else{
			postid = dao.executeQueryForMap("select * from osfirst where osfirstid=?",
					new int[]{
							Types.INTEGER
					},
					new Object[]{
							Integer.parseInt(commentid)
					}).get("postid").toString();
		}
		//添加赞
		dao.executeUpdate("insert into zan values(0,?,?,0,?,?)",
				new int[]{
						Types.INTEGER,
						Types.INTEGER,
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(postid),
						Integer.parseInt(uid),
						Integer.parseInt(queryUserById(uid).get("schoolid").toString()),
						Integer.parseInt(commentid)
						
				});
	}
	/**
	 * 取消赞
	 * @param commentid
	 * @param token
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public void DelOsZan(String commentid, String token) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String uid = tokenDao.queryUidByToken(token);
		dao.executeUpdate("update zan set display=1 where osid=? and uid=?",
				new int[]{
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(commentid),
						Integer.parseInt(uid)	
				});
	}
	/**
	 * 点赞
	 * @param commentid
	 * @param token
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public void addOsZan2(String commentid, String token) throws NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String uid = tokenDao.queryUidByToken(token);
		dao.executeUpdate("update zan set display=0 where osid=? and uid=?",
				new int[]{
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(commentid),
						Integer.parseInt(uid)	
				});
	}
	/**
	 * 分享表添加数据
	 * @param postid
	 * @param token
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void addShare(String postid, String token) throws NumberFormatException, ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		String uid = tokenDao.queryUidByToken(token);
		String schoolid = queryUserById(uid).get("schoolid").toString();
		
		dao.executeUpdate("insert into share values(0,?,?,0,?)",
				new int[]{
						Types.INTEGER,
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(postid),
						Integer.parseInt(uid),
						Integer.parseInt(schoolid)
				});
	}
	/**
	 * 获取二级和二级以上评论信息
	 * @param comment
	 * @param token
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public List<Map<String, Object>> queryOthOs(String comment) throws NumberFormatException, ClassNotFoundException, SQLException {
		String sql = "select * from osother "
				+ "LEFT JOIN user on osother.uid=user.uid "
				+ "LEFT JOIN (select zan.osid,count(*) zcount from zan GROUP BY osid) z on osother.osotherid=z.osid "
				+ "where osother.osfirstid=? ORDER BY CAST(osother.createtime as SIGNED) desc";
		return dao.executeQueryForList(sql,
				new int[]{
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(comment)
				});
	}
	
	/**
	 * @desc  收藏帖子
	 * @param postid
	 * @param token
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void addPostAos(String postid, String token) throws NumberFormatException, ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		
		//获取用户id
		String uid = tokenDao.queryUidByToken(token);
		
		//获取该条收藏信息
		Map<String, Object> map = dao.executeQueryForMap("select * from aos where postid=? and uid=?",new int[]{Types.INTEGER,Types.INTEGER},new Object[]{Integer.parseInt(postid),Integer.parseInt(uid)});
		//第一次收藏
		if(map==null){
			dao.executeUpdate("insert into aos values(0,?,?,'0',?)",
					new int[]{
							Types.INTEGER,
							Types.INTEGER,
							Types.INTEGER
					},
					new Object[]{
							Integer.parseInt(postid),
							Integer.parseInt(uid),
							Integer.parseInt(queryUserById(uid).get("schoolid").toString()),
					});
			//postinfo  postzan+1
			dao.executeUpdate("update postinfo set postaos=postaos+1 where postid=?",
					new int[]{
							Types.INTEGER
					},
					new Object[]{
							Integer.parseInt(postid)
					});
			
			

			
		}
		//已收藏
		else{
			String display = map.get("display").toString();
			//取消收藏
			if(display.equals("0")){
				dao.executeUpdate("update aos set display='1' where postid=? and uid=?",
						new int[]{
								Types.INTEGER,
								Types.INTEGER
						},
						new Object[]{
								Integer.parseInt(postid),
								Integer.parseInt(uid)	
						});
				//postinfo  postaos-1
				dao.executeUpdate("update postinfo set postaos=postaos-1 where postid=?",
						new int[]{
								Types.INTEGER
						},
						new Object[]{
								Integer.parseInt(postid)
						});
			}
			//收藏
			else if(display.equals("1")){
				dao.executeUpdate("update aos set display='0' where postid=? and uid=?",
						new int[]{
								Types.INTEGER,
								Types.INTEGER
						},
						new Object[]{
								Integer.parseInt(postid),
								Integer.parseInt(uid)	
						});
				//postinfo  postaos+1
				dao.executeUpdate("update postinfo set postaos=postaos+1 where postid=?",
						new int[]{
								Types.INTEGER
						},
						new Object[]{
								Integer.parseInt(postid)
						});
			}
		}
			
	}
	
	/**
	 * 查询收藏
	 * @param postid
	 * @param token
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> queryPostAos(String postid, String token) throws NumberFormatException, ClassNotFoundException, SQLException {
		String uid = tokenDao.queryUidByToken(token);
		//获取display
		return dao.executeQueryForMap("select * from aos where postid=? and uid=?",
						new int[]{
								Types.INTEGER,
								Types.INTEGER
						},
						new Object[]{
								Integer.parseInt(postid),
								Integer.parseInt(uid)
						});
	}
	
	/**
	 * 评论二级评论的三级评论数量
	 * @param string
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public String querySEOs(String osotherid) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select count(*) count from osother where upid=?",
				new int []{
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(osotherid)
				}).get("count").toString();
	}
	/**
	 * 根据id查询评论信息
	 * @param string
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> queryOthOsById(String osid) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from osother"
				+ " LEFT JOIN user on osother.osotherid=user.uid"
				+ " LEFT JOIN (select zan.osid,count(*) zcount from zan GROUP BY osid) z"
				+ " on osother.osotherid=z.osid"
				+ " where osotherid=?",
				new int[]{
					Types.INTEGER	
				},
				new Object[]{
					Integer.parseInt(osid)
				});
				
	}
	/**
	 * 分页获取二级或以上评论
	 * @param comment
	 * @param page
	 * @param count
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public List<Map<String, Object>> queryOthOs(String comment, String page, String count) throws NumberFormatException, ClassNotFoundException, SQLException {
		int count1 = Integer.parseInt(count);
		int page1 = Integer.parseInt(page);
		
		if (page1==0) page1=1;
		page1=(page1-1)*count1;
		String sql = "select * from osother "
				+ "LEFT JOIN user on osother.uid=user.uid "
				+ "LEFT JOIN (select zan.osid,count(*) zcount from zan GROUP BY osid) z on osother.osotherid=z.osid "
				+ "where osother.osfirstid=? ORDER BY CAST(osother.createtime as SIGNED) desc limit ?,?";
		return dao.executeQueryForList(sql,
				new int[]{
						Types.INTEGER,
						Types.INTEGER,
						Types.INTEGER
				},
				new Object[]{
						Integer.parseInt(comment),
						page1,
						count1
				});
	}	
	
}
