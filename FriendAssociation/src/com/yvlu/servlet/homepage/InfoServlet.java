package com.yvlu.servlet.homepage;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.quifeng.dao.user.UserDao;
import com.quifeng.utils.generate.TokenUtils;
import com.yvlu.dao.homepage.InfoDao;


/**
 * @Desc 首页数据获取
 * @author 语录
 *
 */
public class InfoServlet {

	//infoDao Dao层
	private static InfoDao infoDao = new InfoDao();
	private static UserDao userDao = new UserDao();
	
	public String info(String token){
		//创建返回的数据 
		Map<String, Object> data = new HashMap<String, Object>();
		//查询token
		try {
			Map<String, Object> rootMap = infoDao.getRootByToken(token);
			if (rootMap==null) {
				return pring(data, "-1", "token验证失败");
			}else {
				//创建返回的数据 
				Map<String, Object> dataP = new HashMap<String, Object>();
				//用户id  头像
				String  avatar = rootMap.get("avatar").toString();
				String  rootname = rootMap.get("rootname").toString();
				//获取新的token
				String newtoken = TokenUtils.getToken(UUID.randomUUID().toString());
				infoDao.updateRootTokenByidorToken(token, newtoken );
				//添加数据
				dataP.put("token", newtoken);
				dataP.put("managername", rootname);
				dataP.put("manageravatar",avatar);
				//服务器数据
				//服务器运行时间
				long dateTime =System.currentTimeMillis()-getServerStqrtTime();
				double total = (Runtime.getRuntime().totalMemory()) / (1024.0 * 1024);
				double max = (Runtime.getRuntime().maxMemory()) / (1024.0 * 1024);
				double free = (Runtime.getRuntime().freeMemory()) / (1024.0 * 1024);
				int freezz = (int) (total-(max-total+free));
				
				dataP.put("serverruntime",getDate(dateTime)+"天");
				dataP.put("servermemory",freezz+"/"+total+" MB"); //服务器内润
				//创建返回的数据 countinfo
				Map<String, Object> countInfo = new HashMap<String, Object>();
				countInfo.put("user", infoDao.getUserCount().get("count"));	
				countInfo.put("check", infoDao.getCheckCount().get("count"));
				countInfo.put("goods", infoDao.getGoodsCount().get("count"));
				countInfo.put("posts", infoDao.getPostsCount().get("count"));
				countInfo.put("audits", infoDao.getAuditsCount().get("count"));
				
				//添加数据
				dataP.put("countinfo", countInfo);
				
				//创建返回的list
				List<Map<String, Object>> userlogs = new ArrayList<Map<String,Object>>();
				
				//获取数据
				 List<Map<String, Object>> log = infoDao.getAllShop();
				for (Map<String, Object> map : log) {
					//创建返回的数据 的容器
					Map<String, Object>  logMap = new HashMap<String, Object>();
					logMap.put("username",userDao.getUserById(map.get("uid").toString()).get("username"));
					logMap.put("ip", map.get("ip"));
					logMap.put("content", map.get("content"));
					logMap.put("time", map.get("time"));
					userlogs.add(logMap);
				}
				
				//添加数据
				dataP.put("userlogs", userlogs);
				
				//添加数据
				data.put("data", dataP);
				return pring(data, "200","请求成功");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return pring(data, "-5", "查询异常");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return pring(data, "-5", "数据更新异常");
		}	
	}
	
	
	public  long getServerStqrtTime(){
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return time;
    }

	public long getDate(long time){
		long days =  time / (1000 * 60 * 60 * 24);
		return days;
	}
	
	public String pring(Map<String,Object> data ,String code,String msg){	
		data.put("code", code);
		data.put("msg", msg);
		return JSON.toJSONString(data);
	}
	
}
