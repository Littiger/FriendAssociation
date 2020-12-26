<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>网络在线考试――后台管理</title>
		<link href="<%=request.getContextPath() %>/CSS/style.css" rel="stylesheet"></link>
	</head>
	<body>
	
		<%@ include file="/manage/top.jsp"%>
		
		<table width="960" border="0" align="center" cellspacing="0" cellpadding="0">
			<tr>
				<td width="176" align="center" valign="top" bgcolor="#FFFFFF">
				
					<%@ include file="/manage/left.jsp"%>
					
				</td>
				<td align="right" valign="top" bgcolor="#FFFFFF">
					<table width="99%" border="0" cellpadding="0" cellspacing="0" align="center">
						<tr>
							<td height="30" bgcolor="#EEEEEE" class="tableBorder_thin">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="78%" class="word_grey" align="left">
											&nbsp;当前位置：<span class="word_darkGrey">管理员信息管理 &gt;&gt;&gt;</span>
										</td>
										<td align="right">
											<img src="<%=request.getContextPath() %>/Images/m_ico1.gif" width="5" height="9">&nbsp;
											当前管理员：<%=((Map)session.getAttribute("user")).get("manager_name") %>&nbsp;
										</td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td align="center" valign="top">
								<table width="96%" height="40" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td  height="30" align="right">
											<img src="<%=request.getContextPath() %>/Images/add.gif" width="19" height="18">&nbsp;
											<a href="<%=request.getContextPath()%>/manage/manager/add.jsp">添加管理员信息</a>&nbsp;&nbsp;
										</td>
									</tr>
								</table>
								<table width="96%" border="1" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" bordercolordark="#FFFFFF" bordercolorlight="#67A8DB">
									<tr align="center" bgcolor="#A8D8FC">
									 <td width="10%" height="26" bgcolor="#B2D6F1">序号</td>
										<td width="84%" height="26" bgcolor="#B2D6F1">管理员名称</td>
										<td width="40%" height="26" bgcolor="#B2D6F1">管理员头像</td>
										<td width="8%" bgcolor="#B2D6F1">修改</td>
										<td width="8%" bgcolor="#B2D6F1">删除</td>
									</tr>
									
									<%
										List list =(List) request.getAttribute("list");
									
										for (int i = 0 ; i< list.size(); i ++){
											Map map= (Map)list.get(i);
										
									%>

					
									<tr>
									    <td style="padding:5px;"><%=(i+1)%></td>
									    <td style="padding:5px;"><%=map.get("manager_name")%></td>
									    <td style="padding:5px;"><img  style="width:100px;height:100px" src="<%=request.getContextPath()%>/manage/manager.do?method=download&manager_imgpath=<%=map.get("manager_image")%>"/></td>
										<td>&nbsp;
											<a href="<%=request.getContextPath() %>/manage/manager.do?method=editpage&manager_id=<%=map.get("manager_id")%>">修改</a>
										</td>
									    <td >&nbsp;
											<a href="<%=request.getContextPath() %>/manage/manager.do?method=delete&manager_id=<%=map.get("manager_id") %>">删除</a>
										</td>
									</tr>
									 
									<%
									}
									%>

								</table>
							</td>
						</tr>
					</table></td>
			</tr>
		</table>
		<%@ include file="/manage/copyright.jsp"%>
	</body>
</html>
