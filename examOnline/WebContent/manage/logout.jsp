<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Untitled Document</title>
	</head>
	
	<body>
		<%
			session.invalidate();
			out.println("<script language='javascript'>");
			out.println("window.location.href='../index.jsp'");
			out.println("</script>");
		%>
	</body>
</html>
