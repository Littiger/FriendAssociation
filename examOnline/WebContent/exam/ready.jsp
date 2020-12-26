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
										<td width="76%" class="word_grey">&nbsp;
											<img src="<%=request.getContextPath() %>/Images/f_ico.gif" width="8" height="8"> 
											当前位置：→ <span class="word_darkGrey">在线考试 → 准备考试 &gt;&gt;&gt;</span>
										</td>
										<td width="24%" align="right">
											<img src="<%=request.getContextPath() %>/Images/m_ico1.gif" width="5" height="9"> 
											<a href="<%=request.getContextPath() %>/exam/main.jsp">返回首页</a>&nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center" valign="top">
								<table width="100%" height="265" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td height="93" colspan="3" align="center" class="word_orange1">&nbsp;</td>
									</tr>
									<tr>
										<td width="12%">&nbsp;</td>
										<td width="77%" align="center" valign="top">
											准备好了吗？<br> <br>
											考生单击“开始考试”按钮，系统将立即记录本次考试并登记考试成绩！
										</td>
										<td width="11%">&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td align="center" valign="top">
											<input type="button" class="btn_grey" value=" 开始考试 " onclick="window.opener=null;window.close();window.open('<%=request.getContextPath() %>/exam/startExam.jsp','','width=977,height=600,scrollbars=1');" />
											<input type="button" class="btn_grey" value=" 返回 " onclick="history.back(-1)" />
										</td>
										<td>&nbsp;</td>
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
