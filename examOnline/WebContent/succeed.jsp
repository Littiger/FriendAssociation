<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>网络在线考试</title>
		<link href="<%=request.getContextPath() %>/CSS/style.css" rel="stylesheet"></link>
		<script language="javascript" src="<%=request.getContextPath() %>/JS/ContentLoader.js"></script>
	</head>
	<script language="javascript">
		function checkForm(form) {
			if (form.name.value == "") {
				alert("请输入考生姓名!");
				form.name.focus();
				return false;
			}
			if (form.password1.value == "") {
				alert("请输入登录密码!");
				form.password1.focus();
				return false;
			}
			if (form.password1.value.length<6 || form.password1.value.length>20) {
				alert("您输入的密码不合法，密码必须大于6位，并且小于等20位!");
				form.password1.focus();
				return false;
			}
			if (form.password2.value == "") {
				alert("请确认登录密码!");
				form.password2.focus();
				return false;
			}
			if (form.password1.value != form.password2.value) {
				alert("您两次输入的登录密码不一致，请重新输入!");
				form.password1.focus();
				return false;
			}
			if (form.question.value == "") {
				alert("请输入提示问题!");
				form.question.focus();
				return false;
			}
			if (form.answer.value == "") {
				alert("请输入问题答案!");
				form.answer.focus();
				return false;
			}
		}
	</script>
	<body>
		<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<img alt="" src="<%=request.getContextPath() %>/Images/top_bg.jpg" width="960" height="131"></td>
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
										<td class="word_grey">
											&nbsp;
											<img src="<%=request.getContextPath() %>/Images/f_ico.gif" width="8" height="8" /> 
											当前位置：→ <span class="word_darkGrey">考生注册 &gt;&gt;&gt;</span>
										</td>
										<td align="right">
											<img src="<%=request.getContextPath() %>/Images/m_ico1.gif" width="5" height="9">&nbsp;
											<a href="<%=request.getContextPath() %>/index.jsp">返回首页</a>&nbsp;
										</td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td align="center" valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="84%">&nbsp;</td>
									</tr>
								</table> 
								
							
								<table width="57%" border="0" height="300" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" bordercolordark="#D2E3E6" bordercolorlight="#FFFFFF">
									<tr>
										<td align="left" style="padding: 5px;">
											注册成功！							<br/>
											您的【准考证号】为：CN20191227000008	<br/>
											您的【登陆密码】为：11111				<br/>
											请记住这个号码，登陆会使用此准考证号。	<br/>
										</td>
									</tr>
									
									<tr>
										<td style="padding: 5px;">
											<input type="button" value=" 返回 登陆页面" class="btn_grey" onclick="window.location.href='<%=request.getContextPath() %>/index.jsp'"/>&nbsp; 
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
