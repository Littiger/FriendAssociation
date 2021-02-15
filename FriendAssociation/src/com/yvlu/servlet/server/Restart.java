package com.yvlu.servlet.server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


import com.alibaba.fastjson.JSON;
import com.yvlu.dao.homepage.InfoDao;

/**
 * @Desc 重启服务器
 * @author 语录
 *
 */
public class Restart {
	
	private static InfoDao infoDao = new InfoDao();
	
	
	public String restart(String token){
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			Map<String, Object> userMap = infoDao.getRootByToken(token);
			if(userMap==null){
				return pring(data, "-1", "请重新登录");
			}else{
				String  managerlevel = userMap.get("managerlevel").toString();
				if (managerlevel.equals("0")) {
					String strcmd ="shutdown -r -t 0";  //重启服务器
					return run_cmd(strcmd);
				}else{
					return pring(data, "-2", "权限不足");
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return pring(data, "-3", "重启失败");
		}
	
	}
	

	public  String run_cmd(String strcmd)  {
		Map<String, Object> data = new HashMap<String, Object>();
		
		        Runtime rt = Runtime.getRuntime(); //Runtime.getRuntime()返回当前应用程序的Runtime对象
		        Process ps = null;  //Process可以控制该子进程的执行或获取该子进程的信息。
		       
		        try {
					ps = rt.exec(strcmd);   //该对象的exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例。
					ps.waitFor();
		        } catch (InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return pring(data, "-1", "执行失败");
				}  //等待子进程完成再往下执行。
		        int i = ps.exitValue();  //接收执行完毕的返回值
		        if (i == 0) {
			        ps.destroy();  //销毁子进程
			        ps = null; 
		            return pring(data, "200", "重启成功");
		        } else {
			        ps.destroy();  //销毁子进程
			        ps = null; 
		            return pring(data, "-1", "执行失败");
		        }

  
		    }
	
	
	public String pring(Map<String,Object> data ,String code,String msg){	
		data.put("code", code);
		data.put("msg", msg);
		return JSON.toJSONString(data);
	}

}
