package com.yvlu.servlet.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import com.alibaba.fastjson.JSON;
import com.quifeng.utils.generate.TokenUtils;
import com.yvlu.dao.manager.LoginDao;

/**
 * @Desc 登录的控制层
 * @author 语录
 *
 */
public class LoginServicve {

	//获取dao
	private static LoginDao loginDao = new LoginDao();

	
	/**
	 * @Desc 用户登录
	 * @param userName
	 * @param password
	 * @return
	 */
	public String  login(String userName,String password){
		//创建返回的数据 
		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			//查询用户
			Map<String, Object> userMap = loginDao.getRootBy(userName);
			if (userMap==null) { //管理员为空
				return pring(data, "-1","用户名错误");
			}else{
				//获取密码 
				
				String  rootpassword = userMap.get("rootpassword").toString();
				if (password.equals(rootpassword)) {//登录成功
					//用户id  头像
					String rootid = userMap.get("rootid").toString();
					String  avatar = userMap.get("avatar").toString();
					//创建返回的数据 
					Map<String, Object> dataP = new HashMap<String, Object>();
					//更新token
					String newtoken = TokenUtils.getToken(UUID.randomUUID().toString()); 
					loginDao.updateRootTokenByidorToken(rootid, newtoken, System.currentTimeMillis()+"");
					//存入数据
					dataP.put("token", newtoken);
					dataP.put("managername", userName);
					dataP.put("manageravatar", avatar);
					//返回数据
					data.put("data", dataP);

					return pring(data, "200","登录成功");
				}else{
					return pring(data, "-1","密码错误");
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return pring(data, "-5", "查询异常");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return pring(data, "-5","数据更新异常");
		}
		
	}
	
	
	
	public String pring(Map<String,Object> data ,String code,String msg){	
		data.put("code", code);
		data.put("msg", msg);
		return JSON.toJSONString(data);
	}
	
}
