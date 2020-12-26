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
							<td height="30" bgcolor="#EEEEEE" class="tableBorder_thin"><table
									width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="76%" class="word_grey">&nbsp;
											<img src="<%=request.getContextPath() %>/Images/f_ico.gif" width="8" height="8"> 
											当前位置：→ <span class="word_darkGrey">在线考试 → 考试规则 &gt;&gt;&gt;</span>
										</td>
										<td width="24%" align="right">
											<img src="<%=request.getContextPath() %>/Images/m_ico1.gif" width="5" height="9"> 
											<a href="<%=request.getContextPath() %>/exam/main.jsp">返回首页</a>&nbsp;
										</td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td align="center" valign="top">
								<form action="<%=request.getContextPath() %>/exam/selectLesson.jsp" method="post">
									<table width="100%" height="253" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td height="81" colspan="3" align="center"
												class="word_orange1">考试规则</td>
										</tr>
										<tr>
											<td width="12%">&nbsp;</td>
											<td width="77%" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;网络在线考试系统不允许对网页进行刷新、后退等操作，否则后果自负。如果在规定的考试时间内没有交卷，系统将自动提交试卷。每位考生同一个课程只能考一次；系统会及时通知考试的具体时间；请考生关注考试课程以及考试时间！<br>
												&nbsp;&nbsp;&nbsp;&nbsp;只有同意以上规则才可以进行考试。如果出现问题或者未找到相关的考试课程，请与管理员联系。
											</td>
											<td width="11%">&nbsp;</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td align="center" valign="top">
												<input type="submit" value=" 同意 " class="btn_grey"/>
											</td>
											<td>&nbsp;</td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%@ include file="/copyright.jsp"%>
	</body>
</html>
