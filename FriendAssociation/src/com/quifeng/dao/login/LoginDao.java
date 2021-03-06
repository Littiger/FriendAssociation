package com.quifeng.dao.login;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.bytedeco.javacpp.presets.opencv_core.Str;

import com.alibaba.fastjson.asm.Type;
import com.ndktools.javamd5.Mademd5;
import com.quifeng.utils.dao.Dao;
import com.quifeng.utils.dao.DaoImpl;

/**
 * @Desc 登录的查询
 * @author 语录
 *
 */
public class LoginDao {

	Dao dao = new DaoImpl();

	/**
	 * @Desc 第一次注册 对应接口 http：//127.0.0.1/api/user/sign 注册完成后用户状态为(注册没有验证) 对用户密码经行加密
	 * @param phone
	 * @param userName
	 * @param userpwd
	 * @return
	 */
	public int addUser(String phone, String userName, String userpwd) {
		int count = 0;
		// 对密码经行加密
		String userpwd1 = new Mademd5().toMd5(userpwd);
		String sql = "INSERT INTO user(userphone,username, password,userzt) VALUES (?,?,?,6)";
		try {
			count = dao.executeUpdate(sql, new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR },
					new Object[] { phone, userName, userpwd1 });
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		return count;
	}

	/**
	 * @Desc 验证手机号
	 * @param phone
	 * @return
	 */
	public Map<String, Object> isPhone(String phone) {
		Map<String, Object> data = null;

		String sql = "SELECT *	 FROM	 `user` WHERE userphone=? ";
		try {
			data = dao.executeQueryForMap(sql, new int[] { Types.VARCHAR }, new Object[] { phone });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * @Desc username查suer
	 * @param username
	 * @return
	 */
	public Map<String, Object> getUserByUserName(String username) {
		Map<String, Object> data = null;

		String sql = "SELECT *	 FROM	 `user` WHERE userphone=? ";
		try {
			data = dao.executeQueryForMap(sql, new int[] { Types.VARCHAR }, new Object[] { username });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}

	public int addCode(String uid, String code, String count) {
		String sql = "INSERT INTO code(uid,code,createtime,count,display) VALUES (?,?,?,?,0)";
		int cod = 0;

		try {
			cod = dao.executeUpdate(sql, new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, },
					new Object[] { uid, code, System.currentTimeMillis() + "", count });
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cod;
	}

	/**
	 * @Desc 传入phone 、uid 获取code信息
	 * @param phone
	 * @return
	 */
	public List<Map<String, Object>> queryCodeByU(String user) {

		String sql = " SELECT * FROM code WHERE uid = (SELECT uid FROM user WHERE userphone=?) AND display=0";
		List<Map<String, Object>> data = null;
		try {
			data = dao.executeQueryForList(sql, new int[] { Types.VARCHAR }, new Object[] { user });
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	// 超过六次 认证失败 display 设置为 1 请求超时成为1 这里的参数是uid或者phone

	public int updateCode(String phone) {

		int count = 0;
		String sql = " UPDATE code SET  display=1 WHERE uid = (SELECT uid FROM user WHERE userphone =?)";
		try {
			count = dao.executeUpdate(sql, new int[] { Types.VARCHAR }, new Object[] { phone });
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * @Desc 更新code
	 * @param codeid
	 * @param cou
	 * @return
	 */
	public int uadateCodeByCount(String phone) {
		int count = 0;
		String sql = " UPDATE code SET count=count+1 WHERE uid=(SELECT uid FROM user WHERE userphone =?) and display=0";
		try {
			count = dao.executeUpdate(sql, new int[] { Types.VARCHAR }, new Object[] { phone });
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}
	
	/**
	 * 根据手机号和验证码删除验证码
	 * @param phone
	 * @param code
	 */
	public int updateCode(String phone, String code) {
		int count = 0;
		String sql = " UPDATE code SET  display=1 WHERE uid = (SELECT uid FROM user WHERE userphone =?) and code=?";
		try {
			count = dao.executeUpdate(sql, new int[] { Types.VARCHAR,Types.VARCHAR }, new Object[] { phone,code });
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 获取黑名单
	 * @param string
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> queryBlackUser(String uid) throws NumberFormatException, ClassNotFoundException, SQLException {
		return dao.executeQueryForMap("select * from blackname where uid=?", new int[]{Types.INTEGER}, new Object[]{Integer.parseInt(uid)});
	}

}
