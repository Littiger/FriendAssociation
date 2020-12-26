package com.turing.manage.listeners;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import com.turing.dao.Dao;
import com.turing.dao.DaoImpl;

public class MyServletRequestListener implements ServletRequestListener {

	Dao dao = new DaoImpl();
	
	public List<Map<String,Object>> queryIp() throws ClassNotFoundException, SQLException{
		return dao.executeQueryForList("select * from ipbase");
	}
	
	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		ServletRequest request = sre.getServletRequest();
	    System.out.println("requestDestroyed:remoteIp:"+request.getRemoteAddr());
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
       ServletRequest request = sre.getServletRequest();
       String ip = request.getRemoteAddr();
       
       System.out.println("requestInitialized:remoteIp:"+request.getRemoteAddr());
	}

}
