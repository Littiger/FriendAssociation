<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>网络在线考试</title>
		<link href="<%=request.getContextPath() %>/CSS/style.css" rel="stylesheet">
	</head>
	<body>
	
		<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<img alt="" src="<%=request.getContextPath() %>/Images/top_bg.jpg" width="960" height="131" /></td>
			</tr>
		</table>
		
		<table width="960" border="0" align="center" cellspacing="0" cellpadding="0">
			<tr>
				<td valign="top" bgcolor="#FFFFFF">
					<table width="950" border="0" cellpadding="0" cellspacing="0" align="center">
						<tr>
							<td height="30" bgcolor="#EEEEEE" class="tableBorder_thin">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="76%" class="word_grey">
											<img src="<%=request.getContextPath() %>/Images/f_ico.gif" width="8" height="8"> 
											当前位置：→ <span class="word_darkGrey">在线考试 → 选择考试课程 &gt;&gt;&gt;</span>
										</td>
										<td width="24%" align="right">
											<img src="<%=request.getContextPath() %>/Images/m_ico1.gif" width="5" height="9" /> 
											<a href="<%=request.getContextPath() %>/exam/main.jsp">返回首页</a>&nbsp;
										</td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td align="center" valign="top"  height="400">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="84%">&nbsp;</td>
									</tr>
								</table>
								<table width="98%" border="1" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" bordercolordark="#FFFFFF" bordercolorlight="#67A8DB">
									<tr align="center">
										<td width="10%" bgcolor="#B2D6F1">所属课程</td>
										<td width="22%" bgcolor="#B2D6F1">所属套题</td>
										<td width="22%" bgcolor="#B2D6F1">试卷名称</td>
										<td width="11%" bgcolor="#B2D6F1">考试时间</td>
										<td width="9%" bgcolor="#B2D6F1">操作</td>
									</tr>
									
									<tr>
										<td style="padding:5px;">计算机专业英语</td>
										<td align="center">2018年期末考试题A</td>
										<td align="center">2018年期末考试题A</td>
										<td align="center">40</td>
									    <td align="center">
									    	<a href="<%=request.getContextPath() %>/exam/ready.jsp">考试</a>
									    </td>
									</tr>

								</table>

							</td>
						</tr>
					</table></td>
			</tr>
		</table>
		<%@ include file="/copyright.jsp"%>
	</body>
</html>
