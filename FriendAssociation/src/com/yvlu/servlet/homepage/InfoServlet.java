package com.yvlu.servlet.homepage;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.quifeng.dao.user.UserDao;
import com.sun.management.OperatingSystemMXBean;
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
				String  managerlevel = rootMap.get("managerlevel").toString(); //管理员

				//添加数据
	
				dataP.put("managername", rootname);
				dataP.put("manageravatar",avatar);
				dataP.put("managerlevel",managerlevel);
				//服务器数据
				//服务器运行时间
				long dateTime =System.currentTimeMillis()-getServerStqrtTime();
				dataP.put("serverruntime",getDate(dateTime)+"小时");
			
				 //服务器内存
				OperatingSystemMXBean osmxb = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean(); 	
				 // 总的物理内存
		        long totalvirtualMemory = osmxb.getTotalSwapSpaceSize(); //单位是字节数，除以1024是K
		        //剩余的内存
		        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize(); 
		        //使用的内存 
		         long compare = totalvirtualMemory-freePhysicalMemorySize;
				dataP.put("servermemory",totalvirtualMemory/1024/1024+"mb/"+(compare/1024/1204)+"mb");
				
				//cpu核数
				dataP.put("servercores", Runtime.getRuntime().availableProcessors());
				//cpu线程数
				
                // 获得线程总数
                ThreadGroup parentThread;
                for (parentThread = Thread.currentThread().getThreadGroup(); parentThread
                        .getParent() != null; parentThread = parentThread.getParent()) {

                }

                int totalThread = parentThread.activeCount();
				dataP.put("serverthreads",totalThread);
				
				//服务器操作系统
				dataP.put("serveros",System.getProperty("os.name"));
				//获取java的版本
				dataP.put("serverjavaversion",System.getProperty("java.version"));
				
				//创建返回的数据 countinfo
				Map<String, Object> countInfo = new HashMap<String, Object>();
				countInfo.put("user", infoDao.getUserCount().get("count"));	
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
				
				
				//热词
				List<String> hotwords = new ArrayList<String>();
				//获取5个热词
				List<Map<String, Object>> hots = infoDao.getAllSearchFrom5();
				for (Map<String, Object> map : hots) {
					hotwords.add(map.get("word").toString());
				}
				dataP.put("hotwords", hotwords);
				
				//添加数据
				data.put("data", dataP);
				return pring(data, "200","请求成功");
				
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return pring(data, "-5", "查询异常");
		} catch (Exception e) {
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
		long days =  time / (1000 * 60 * 60 );
		return days;
	}
	
	public String pring(Map<String,Object> data ,String code,String msg){	
		data.put("code", code);
		data.put("msg", msg);
		return JSON.toJSONString(data);
	}
	
}
