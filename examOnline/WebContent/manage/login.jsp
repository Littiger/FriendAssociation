<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>

<html>
	<head>
		<title>网络在线考试――后台登录</title>
		<link href="<%=request.getContextPath() %>/CSS/style.css" rel="stylesheet"></link>
		<script language="javascript">
			function doForm(){
				var name=document.getElementById("name").value;

		        if(name=="")
		        {
		        	  alert("请输入用户名");
		        	  document.getElementById("name").focus();//重新获取焦点
		        	  return false;
		        }

		        var pass=document.getElementById("pass").value;
		        if(pass=="")
		        {
		        	  alert("请输入密码");
		        	  document.getElementById("pass").focus();//重新获取焦点
		        	  return false;
		        }	
			}
		</script>
	</head>
	<body>
		<form name="managerForm" action="<%=request.getContextPath() %>/manage/login.do?method=login" method="post" focus="name" onsubmit="return doForm()" style="margin: 0px">
		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td bgcolor="#EEEEEE">
					<table width="464" height="294" border="0" align="center" cellpadding="0" cellspacing="0" background="<%=request.getContextPath() %>/Images/m_login.jpg">
						<tr>
							<td width="157" height="153">&nbsp;</td>
							<td width="307">&nbsp;</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td valign="top">

								<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolorlight="#FFFFFF" bordercolordark="#D2E3E6">
									<tr>
										<td width="26%" height="30">管理员名称：</td>
										<td width="74%"><input type="text" name="name" id="name" size="25" /></td>
									</tr>
									<tr>
										<td height="30">管理员密码：</td>
										<td><input type="password" name="pass" id="pass" size="25" /></td>
									</tr>
									<tr>
										<td height="33" colspan="2" align="center">
											<input type="submit" value="确定" class="btn_grey"/>&nbsp;
											<input type="reset" value="重置" class="btn_grey"/>&nbsp;
											<input type="button" value="关闭" class="btn_grey" onclick="window.close();"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</form>
	</body>
</html>
