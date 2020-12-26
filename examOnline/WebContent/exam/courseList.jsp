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
											当前位置：→ <span class="word_darkGrey">考生成绩查询 &gt;&gt;&gt;</span>
										</td>
										<td width="24%" align="right">
											<img src="<%=request.getContextPath() %>/Images/m_ico1.gif" width="5" height="9"> 
											<a href="<%=request.getContextPath() %>/exam/main.jsp">返回首页</a>&nbsp;
										</td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td align="center" valign="top" height="487">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="84%">&nbsp;</td>
									</tr>
								</table>
								<table width="98%" border="1" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" bordercolordark="#FFFFFF" bordercolorlight="#67A8DB">
									<tr align="center">
										<td width="21%" height="27" bgcolor="#B2D6F1">准考证号</td>
										<td width="26%" bgcolor="#B2D6F1">所属课程</td>
										<td width="22%" bgcolor="#B2D6F1">考试时间</td>
										<td width="11%" bgcolor="#B2D6F1">单选题分数</td>
										<td width="11%" bgcolor="#B2D6F1">多选题分数</td>
										<td width="9%" bgcolor="#B2D6F1">合计分数</td>
									</tr>
									<tr>
									    <td style="padding:5px;">CN20191227000008</td>
										<td style="padding:5px;">计算机专业英语</td>
										<td align="center">2019-12-27 15:25:44</td>
										<td align="center">40</td>
									    <td align="center">0</td>
									    <td align="center">40</td>
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
