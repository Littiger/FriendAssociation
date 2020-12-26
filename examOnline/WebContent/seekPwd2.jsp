<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>网络在线考试</title>
		<link href="<%=request.getContextPath() %>/CSS/style.css" rel="stylesheet"></link>
	</head>
	<script language="javascript">
		function checkForm(form) {
			if (form.answer.value == "") {
				alert("请输入密码提示问题答案!");
				form.answer.focus();
				return false;
			}
		}
	</script>
	<body>
	
		<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td >
					<img alt="" src="<%=request.getContextPath() %>/Images/top_bg.jpg" width="960" height="131"></td>
			</tr>
		</table>
		
		<table width="960" border="0" align="center" cellspacing="0" cellpadding="0">
			<tr>
				<td valign="top" bgcolor="#FFFFFF">
					<table width="950" height="474" border="0" cellpadding="0" cellspacing="0" align="center">
						<tr>
							<td height="30" bgcolor="#EEEEEE" class="tableBorder_thin">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td class="word_grey">
											&nbsp;
											<img src="<%=request.getContextPath() %>/Images/f_ico.gif" width="8" height="8" /> 
											当前位置：→ <span class="word_darkGrey">找回密码 &gt;&gt;&gt;</span>
										</td>
										<td align="right">
											<img src="<%=request.getContextPath() %>/Images/m_ico1.gif" width="5" height="9" />&nbsp;
											<a href="<%=request.getContextPath() %>/index.jsp">返回首页</a>&nbsp;
										</td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td height="257" align="center" valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="84%">&nbsp;</td>
									</tr>
								</table> 
								
								<form name="studentForm" action="<%=request.getContextPath() %>/seekPwd3.jsp" method="post" onsubmit="return checkForm(studentForm)">
									<table width="57%" height="69" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="17%">
												<img src="<%=request.getContextPath() %>/Images/step2.gif" width="73" height="30" />
											</td>
											<td width="83%" class="word_orange1" align="left">
												<span style="padding: 5px;">输入密码提示问题答案</span>
											</td>
										</tr>
									</table>
									<table width="57%" border="0" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" bordercolordark="#D2E3E6" bordercolorlight="#FFFFFF">
										<tr align="center">
											<td width="22%" height="30" align="left" style="padding: 5px;">密码提示问题：</td>
											<td width="78%" align="left">
												<input type="hidden" name="ID" value="111" />
												<input type="text" name="question" value="rrr" readonly />
	 											<span class="word_orange1"> （只读）</span>
	 										</td>
										</tr>
										<tr align="center">
											<td width="22%" height="30" align="left" style="padding: 5px;">密码提示答案：</td>
											<td width="78%" align="left">
												<input type="text" name="answer" value="rrr" size="40" />
												<span class="word_orange1"> *</span>
											</td>
										</tr>
										<tr>
											<td height="65" align="left" style="padding: 5px;">&nbsp;</td>
											<td align="left">
												<input type="submit" value=" 下一步 " class="btn_grey" />&nbsp;
												<input type="reset" value=" 取消 " class="btn_grey" />&nbsp;
											</td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
						<tr>
							<td height="141" align="right" valign="top">
								<img src="<%=request.getContextPath() %>/Images/seedPwd.gif" width="139" height="153">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%@ include file="/copyright.jsp"%>
	</body>
</html>
