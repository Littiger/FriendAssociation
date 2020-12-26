<%@page import="java.util.Map"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>




<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>网络在线考试――后台管理</title>
		<link href="<%=request.getContextPath() %>/CSS/style.css" rel="stylesheet"></link>
	</head>
	<body>
	
		<%@ include file="/manage/top.jsp"%>
		
		<table width="960" border="0" align="center" cellspacing="0" cellpadding="0">
			<tr>
				<td width="176" align="center" valign="top" bgcolor="#FFFFFF">
					<%@ include file="/manage/left.jsp"%>
				</td>
				<td valign="top" bgcolor="#FFFFFF">
					<table width="99%" border="0" cellpadding="0" cellspacing="0" align="center">
						<tr>
							<td height="30" bgcolor="#EEEEEE" class="tableBorder_thin">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="78%" class="word_grey">
											&nbsp;当前位置：
											<span class="word_darkGrey">考生成绩查询 &gt;&gt;&gt;</span>
										</td>
										<td align="right">
											<img src="<%=request.getContextPath() %>/Images/m_ico1.gif" width="5" height="9" />&nbsp;
											当前管理员：${sessionScope.user.manager_name} &nbsp;
										</td>
									</tr>
								</table></td>
						</tr>
						
						<tr>
							<td align="center" valign="top">
								<form action="<%=request.getContextPath() %>/manage/grade.do?method=conditionquery" method="post">
								<table width="96%" height="40" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td align="left" valign="middle">&nbsp;
											查询条件： 
											<select name="condition">
												<option value="examinee_id" ${("examinee_id" eq condition_out)?"selected":"" }>准考证号</option>
												<option value="course_name"${("course_name" eq condition_out)?"selected":"" }>考试课程</option>
												<option value="grade_minute_use"${("grade_minute_use" eq condition_out)?"selected":"" }>考试时间</option>
											</select>
											&nbsp;关键字： 
											<input type="text" name="key" value="${key_out}"/>&nbsp;&nbsp; 
											<input type="submit" class="btn_grey" value=" 查询 " />
										</td>
									</tr>
								</table>
								</form>
								
								<table width="96%" border="1" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" bordercolordark="#FFFFFF" bordercolorlight="#67A8DB">
									<tr align="center">
										<td width="21%" height="27" bgcolor="#B2D6F1">准考证号</td>
										<td width="26%" bgcolor="#B2D6F1">所属课程</td>
										<td width="22%" bgcolor="#B2D6F1">考试时间(交卷)</td>
										<td width="11%" bgcolor="#B2D6F1">单选题分数</td>
										<td width="11%" bgcolor="#B2D6F1">多选题分数</td>
										<td width="9%" bgcolor="#B2D6F1">合计分数</td>
									</tr>

									<c:forEach var="map" items="${list}">

									<tr>
									    <td style="padding:5px;">${map.examinee_id }</td>
										<td style="padding:5px;">${map.course_name }</td>
										<td align="center">${map.grade_minute_use }</td>
										<td align="center">${map.grade_radio_mark }</td>
									    <td align="center">${map.grade_check_mark }</td>
									    <td align="center">${map.grade_sum }</td>
									</tr>
									  
									</c:forEach>
								
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%@ include file="/manage/copyright.jsp"%>
	</body>
</html>
