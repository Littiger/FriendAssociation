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
				<td height="208">
					<img src="<%=request.getContextPath() %>/Images/default_top.jpg" width="960" height="208" border="0"></td>
			</tr>
			<tr>
				<td>
					<img src="<%=request.getContextPath() %>/Images/default_mid.JPG" width="960" height="254" border="0" usemap="#Map" /></td>
			</tr>
			<tr>
				<td height="158">
					<img src="<%=request.getContextPath() %>/Images/default_bottom.JPG" width="960" height="158" border="0" /></td>
			</tr>
		</table>
		<map name="Map">
			<area shape="poly" coords="240,65,240,65,280,82,240,100,125,113,123,81" href="<%=request.getContextPath() %>/exam/examRule.jsp">
			<area shape="poly" coords="393,59,492,45,515,56,486,78,381,90,383,58" href="<%=request.getContextPath() %>/exam/courseList.jsp">
			<area shape="poly" coords="470,141,610,125,661,139,610,159,475,176" href="<%=request.getContextPath() %>/exam/studentModify.jsp">
			<area shape="poly" coords="742,58,850,46,885,63,856,76,742,91" href="<%=request.getContextPath() %>/index.jsp">
		</map>
	</body>
</html>
