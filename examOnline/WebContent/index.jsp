<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>网络在线考试</title>
		<link href="<%=request.getContextPath() %>/CSS/style.css" rel="stylesheet"></link>
		<script language="javascript">
			function check(form) {
				if (form.name.value == "") {
					alert("请输入准考证号!");
					form.name.focus();
					return false;
				}
				if (form.pwd.value == "") {
					alert("请输入密码!");
					form.pwd.focus();
					return false;
				}
			}
		</script>
	</head>
	<body>

		<table width="956" height="266" border="0" align="center" cellpadding="0" cellspacing="0" >
			<tr>
				<td width="118" colspan="2">
					<img src="<%=request.getContextPath() %>/Images/login_top.jpg" width="956" height="266" border="0"></td>
			</tr>
		</table>
		
		<form action="<%=request.getContextPath() %>/exam/main.jsp" method="post" focus="ID" onsubmit="return check(studentForm)" style="margin: 0px">
			<table width="956" height="158" border="0" align="center" cellpadding="0" cellspacing="0" background="<%=request.getContextPath() %>/Images/login_mid.jpg">
	
				<tr>
					<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolorlight="#FFFFFF" bordercolordark="#D2E3E6">
							<tr>
								<td>&nbsp;</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td width="35%" height="30">&nbsp;</td>
								<td width="9%" height="30">准考证号：</td>
								<td width="27%">
									<input type="text" name="ID" class="logininput" size="25"/>
								</td>
								<td width="29%">&nbsp;</td>
							</tr>
							<tr>
								<td height="30">&nbsp;</td>
								<td height="30">密&emsp;&emsp;码：</td>
								<td>
									<input type="text" name="pwd" class="logininput" size="25"/>
								</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td height="31">&nbsp;</td>
								<td height="31" colspan="2" align="left">
									<input type="submit" value=" 登录 " class="btn_grey"/>&nbsp;
									<input type="reset" value=" 重置 " class="btn_grey"/>&nbsp;
									<input type="button" value=" 注册 " class="btn_grey" onclick="window.location.href='register.jsp'"/>&nbsp;
									<input type="button" value=" 找回密码 " class="btn_grey" onclick="window.location.href='seekPwd1.jsp'"/>

								</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td height="40">&nbsp;</td>
								<td height="31" colspan="2" align="right" valign="bottom">
									<a href="<%=request.getContextPath() %>/manage/login.jsp" class="word_orange">进入后台</a>
								</td>
								<td>&nbsp;</td>
							</tr>
							
						</table>
					</td>
				</tr>
			</table>
		</form>
		<table width="956" height="196" border="0" align="center" cellpadding="0" cellspacing="0" background="<%=request.getContextPath() %>/Images/login_top.gif">
			<tr>
				<td>
					<img src="<%=request.getContextPath() %>/Images/login_bottom.jpg" width="956" height="196" border="0">
				</td>
			</tr>
		</table>
	</body>
</html>
