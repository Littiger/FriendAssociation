package com.turing.manage.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @desc   过滤器:对访问者IP进行限制访问
 * @author Littiger
 * 
 */
public class IpFilter implements Filter{

	//用来存放允许访问的ip
	private List<String> allowList = new ArrayList<String>();
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		try {
			System.out.println("过滤器IpFilter开始初始化，功能：IP访问限制");
			initConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		//获取访问的IP地址
		String remoteAddr = request.getRemoteAddr();
		System.out.println("过滤器获取地址：" + remoteAddr);
		//System.out.println("===============" + remoteAddr);
		//如果allowList为空,则认为没做限制,不为空则检查是否限制
		if(allowList.size() == 0 || allowList == null) {
			filterChain.doFilter(request, response);
		} else {
			Boolean flag = false;  //访问标志，默认为false，限制访问
			//进行逐个检查
			for(String regex : allowList){
				if(remoteAddr.matches(regex)){
					//ip没被限制，正常访问
					filterChain.doFilter(request, response);
					flag = true;  //置为true，表示不限制访问
					break;
				}
			}
			if(!flag) {
				System.out.println("被限制");
				//ip被限制，跳到指定页面
				request.getRequestDispatcher("/404error.jsp").forward(request, response);
			}
		}
		
	}
	
	@Override
	public void destroy() {
		System.out.println("过滤器IpFilter结束。");
	}

	/**
	 * 对配置文件进行初始化并校验
	 * @throws IOException
	 */
	public void initConfig() throws IOException {
		//将文件转化成流
		InputStream inputStream = IpFilter.class.getResourceAsStream("ipConfig.properties");
		
		Properties properties = new Properties();
		
		//通过Properties对象实例加载流
		properties.load(inputStream);
		
		//获取配置的值
		String allowIP = properties.getProperty("allowIP");
		
		//把配置放到allowList中
		if(null != allowIP && !"".equals(allowIP.trim())) {
			String[] allowIPs = allowIP.split(",|;");
			for(String ip : allowIPs) {
				allowList.add(ip);
			}
		}
		
		//打印输出allowList
		for(String str : allowList) {
			System.out.println(str);
		}
		
	}
	
	
	
}

