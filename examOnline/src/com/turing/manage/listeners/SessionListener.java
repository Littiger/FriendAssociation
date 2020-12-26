package com.turing.manage.listeners;
import javax.servlet.ServletContext;
/**
 * @desc   统计在线人数
 * @author WYH
 * @time   2020-12-14
 */
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
	
	public SessionListener() {
		System.out.println("SessionListener加载了");
	}


	@Override
	public void sessionCreated(HttpSessionEvent se) {
     System.out.println("session已经创建，且sessionId="+se.getSession().getId());
		
	 ServletContext ctx = se.getSession().getServletContext();
	 Integer numSessions = (Integer) ctx.getAttribute("numSessions");
		
	 if (numSessions==null) 
	 {
	   numSessions=new Integer(1);	
	 }
	 else
	 {
		   int count=numSessions.intValue();
		   numSessions=new Integer(count+1);
	  }
		
	 ctx.setAttribute("numSessions", numSessions);
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
     System.out.println("session销毁");
     ServletContext ctx = se.getSession().getServletContext();
	 Integer numSessions = (Integer) ctx.getAttribute("numSessions");
		
	 if (numSessions==null) 
	 {
	   numSessions=new Integer(0);	
	 }
	 else
	 {
		   int count=numSessions.intValue();
		   numSessions=new Integer(count-1);
	  }
		
	 ctx.setAttribute("numSessions", numSessions);
	}

}
