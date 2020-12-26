<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>


<html>
	<head>
		<title>错误提示</title>
		<link href="<%=request.getContextPath() %>/CSS/style.css" rel="stylesheet">
	</head>
	
	<body>
		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#EEEEEE">
			<tr>
				<td align="center">
					<table width="350" height="192" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="center" background="<%=request.getContextPath() %>/Images/error.jpg">
								&nbsp;&nbsp;错误提示信息：
								您输入的准考证号不存在！ <br> <br> <input name="Submit"
								type="submit" class="btn_grey" value="返回"
								onClick="history.back(-1)">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<center></center>
	</body>
</html>

