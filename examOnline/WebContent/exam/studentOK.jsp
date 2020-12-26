<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>


<html>
	<head>
		<title>信息提示</title>
		<link href="<%=request.getContextPath() %>/CSS/style.css" rel="stylesheet">
	</head>
	
	<body>
		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#EEEEEE">
			<tr>
				<td align="center">
					<table width="350" height="192" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="center" background="<%=request.getContextPath() %>/Images/error.jpg">
								&nbsp;&nbsp;提示信息：
								个人资料修改成功！ <br> <br> <input name="Submit"
								type="submit" class="btn_grey" value="返回"
								onclick="window.location.href='<%=request.getContextPath() %>/exam/main.jsp'">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<center></center>
	</body>
</html>

