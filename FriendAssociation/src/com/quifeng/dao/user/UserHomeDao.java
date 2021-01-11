package com.quifeng.dao.user;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @Desc 这个是防止在userdao类中提交 的问题 
 * @author 语录
 *
 */
public class UserHomeDao {
	Dao dao =new DaoImpl();
	/**
	 * @Desc 获取是否关注的信息
	 * @param myId
	 * @param tid
	 * @return
	 */
	public Map<String, Object>  getFixidezByID(String myId, String tid){
		
		Map<String, Object>  data = null;
		
		String sql = "SELECT *	 FROM fixidez  WHERE formid = ? AND  toid = ? ANd display=0";
		try {
			data = dao.executeQueryForMap(sql,new int[]{
					Types.INTEGER,
					Types.INTEGER
			}, new Object[]{
					myId,tid
			});
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		return data;
		
	}
	
	
	//判断是否关注
	public	boolean	isFixidez(String myId, String tid){
		Map<String, Object> data = getFixidezByID(myId, tid);
		if (data==null) {
			return false;
		}else{
			return true;
		}
	}


	/**
	 * @Desc 获取全部赞的数量
	 * @param userid
	 * @return
	 */
	public Map<String, Object> getUserZanSumBy(String userid) {
		// TODO Auto-generated method stub
		Map<String, Object>  data = null;
		
		String sql = "SELECT count(*) count	FROM ZAN WHERE postid in (SELECT postid FROM post WHERE uid=? AND display=0)";
		try {
			data = dao.executeQueryForMap(sql,new int[]{
					Types.INTEGER
			}, new Object[]{
					userid
			});
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		return data;
	
	}


	/**
	 * @Desc 获取粉丝数
	 * @param userid
	 * @return
	 */
	public Map<String, Object> getUserFixidezCountById(String userid) {
		// TODO Auto-generated method stub
		Map<String, Object>  data = null;
				
		String sql = "SELECT  count(*) count FROM fixidez  WHERE  formid = ?  And display = 0";
		try {
			data = dao.executeQueryForMap(sql,new int[]{
					Types.INTEGER
				}, new Object[]{
					userid
				});
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
				
				return data;		
	}
	
	/**
	 * @Desc 获取我关注的人
	 * @param userid
	 * @return
	 */
	public Map<String, Object> getFixideById(String userid) {
		// TODO Auto-generated method stub
		Map<String, Object>  data = null;
		String sql = "SELECT count(*) count FROM fixidez  WHERE  toid = ?  And display = 0";
			try {
				data = dao.executeQueryForMap(sql,new int[]{
						Types.INTEGER
				}, new Object[]{
						userid
				});
			} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
				System.out.println(e.getMessage());
		}
				
		return data;		
	}


	/**
	 * @Desc 获取贴子
	 * @param userid
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Map<String, Object>> getUserPostById(String userid,String page ,String size) {
		// TODO Auto-generated method stub
		
		//分页的page的处理
		int pageSize = Integer.parseInt(size.trim());
		int curPage = Integer.parseInt(page.trim());
		
		//这里防止出错
		if (pageSize==0) return null;
		
		//这里是第几页
		int countPage = (curPage-1)*pageSize;
		
		
		List<Map<String, Object>> data =  null;
		String sql = "SELECT *	FROM post WHERE uid = ? AND display=0  GROUP BY  createtime   DESC  limit ?,?";
		try {
			data = dao.executeQueryForList(sql,new int[]{
					Types.INTEGER,
					Types.INTEGER,
					Types.INTEGER
			}, new Object[]{
					userid,countPage,size
			});
		} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
			
		return data;	

	}


	/**
	 * @Desc 获取赞
	 * @param postid
	 * @param myUid
	 * @return
	 */
	public Map<String, Object> getZan(String postid, String myUid){
		// TODO Auto-generated method stub
		Map<String, Object>  data = null;
		String sql = "SELECT *  FROM  zan WHERE postid = ? AND uid= ? ";
			try {
					data = dao.executeQueryForMap(sql,new int[]{
							Types.INTEGER,Types.INTEGER
				}, new Object[]{
						postid,myUid
				});
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println(e.getMessage());
		}
						
				return data;				
	}
	/**
	 * @Desc 是否点赞
	 * @param postid
	 * @param myUid
	 * @return
	 */
	public boolean isZan(String postid, String myUid) {
		// TODO Auto-generated method stub
		if (getZan(postid, myUid)==null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * @Desc 获取赞的数量
	 * @param postid
	 * @return
	 */
	public Map<String, Object> getPostZanCount(String postid){
		Map<String, Object>  data = null;
		String sql = "SELECT count(*) count	 FROM zan  WHERE  postid=?  And display = 0";
			try {
				data = dao.executeQueryForMap(sql,new int[]{
						Types.INTEGER
				}, new Object[]{
						postid
				});
			} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
				System.out.println(e.getMessage());
		}
				
		return data;				
	}


	/**
	 * @Desc 获取评论的数量
	 * @param postid
	 * @return
	 */
	public Map<String, Object> getPostFiCount(String postid) {
		Map<String, Object>  data = null;
		String sql = "SELECT count(*) count FROM  osfirst WHERE postid = ? AND display=0 ";
			try {
				data = dao.executeQueryForMap(sql,new int[]{
						Types.INTEGER
				}, new Object[]{
						postid
				});
			} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
				System.out.println(e.getMessage());
		}
				
		return data;			

	}


	/**
	 * @Desc 分享的数量
	 * @param postid
	 * @return
	 */
	public Map<String, Object> getShareCount(String postid) {
		Map<String, Object>  data = null;
		String sql = "SELECT count(*) count  FROM  share  WHERE postid = ? AND display=0  ";
			try {
				data = dao.executeQueryForMap(sql,new int[]{
						Types.INTEGER
				}, new Object[]{
						postid
				});
			} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
				System.out.println(e.getMessage());
		}
				
		return data;				
	}


	/**
	 * @Desc 查询板块信息
	 * @param plackid
	 * @return
	 */
	public Map<String, Object> getPlacMap(String plackid) {
		Map<String, Object>  data = null;
		String sql = " SELECT  *	FROM postbk  WHERE placaid=?";
			try {
				data = dao.executeQueryForMap(sql,new int[]{
						Types.INTEGER
				}, new Object[]{
						plackid
				});
			} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
				System.out.println(e.getMessage());
		}
				
		return data;	
	}
	
}
