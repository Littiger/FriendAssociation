<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>网络在线考试――后台管理</title>
		<link href="<%=request.getContextPath() %>/CSS/style.css" rel="stylesheet"></link>
		<script language="javascript">
			function CheckAll(elementsA, elementsB) {
				for (var i = 0; i < elementsA.length; i++) {
					elementsA[i].checked = true;
				}
				if (elementsB.checked == false) {
					for (var j = 0; j < elementsA.length; j++) {
						elementsA[j].checked = false;
					}
				}
			}
			//判断用户是否选择了要删除的记录，如果是，则提示“是否删除”；否则提示“请选择要删除的记录”
			function checkdel(delid, formname) {
				var flag = false;
				for (var i = 0; i < delid.length; i++) {
					if (delid[i].checked) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					alert("请选择要删除的记录！");
					return false;
				} else {
					if (confirm("确定要删除吗？")) {
						formname.submit();
					}
				}
			}
		</script>
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
											&nbsp;当前位置：<span class="word_darkGrey">考试题目管理 &gt;&gt;&gt;</span>
										</td>
										<td align="right">
											<img src="<%=request.getContextPath() %>/Images/m_ico1.gif" width="5" height="9" />&nbsp;
											当前管理员：${sessionScope.user.manager_name}&nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center" valign="top">
								<form action="<%=request.getContextPath() %>/manage/subject.do?method=conditionquery" method="post">
								<table width="96%" height="40" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td align="left" valign="middle">&nbsp;
											查询条件： 
											<select name="condition">
												<option value="%">查询所有</option>
												<option value="course_name">课程名称</option>
												<option value="subject_name">试题名称</option>
											</select>
											&nbsp;关键字： 
											<input type="text" name="key"/>&nbsp;&nbsp; 
											<input type="submit" class="btn_grey" value=" 查询 " />
										</td>
										<td width="23%" align="right">
											<img src="<%=request.getContextPath() %>/Images/add.gif" width="19" height="18">&nbsp;
											<a href="<%=request.getContextPath()%>/manage/subject.do?method=addpage">添加考试题目</a>&nbsp;&nbsp;
										</td>
									</tr>
								</table>
								</form>

								<form name="studentForm" action="<%=request.getContextPath()%>/manage/subject.do?method=delete" method="post">
								<table width="96%" border="1" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" bordercolordark="#FFFFFF" bordercolorlight="#67A8DB">
									<tr align="center" bgcolor="#A8D8FC">
										<td  height="30" bgcolor="#B2D6F1">所属课程</td>
										<td width="33%" bgcolor="#B2D6F1">考试题目</td>
										<td width="9%" bgcolor="#B2D6F1">试题类型</td>
										<td width="6%" bgcolor="#B2D6F1">修改</td>
										<td width="6%" bgcolor="#B2D6F1">选项</td>
									</tr>
	
									<c:forEach var="map" items="${list}">
										
									<tr>
									  	<td style="padding:5px;">${map.course_name}</td>
										<td align="center">${map.subject_name}</td>
										<td align="center">${map.subject_type}</td>	
										<td align="center"><a href="<%=request.getContextPath() %>/manage/subject.do?method=editpage&id=${map.subject_id}">修改</a></td>
									    <td align="center"><input type="checkbox" name="delIdArray" value="${map.subject_id}" class="noborder"></td>
									</tr>
									  
									</c:forEach>		
									

								</table>
								<table width="94%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="60%" height="24">&nbsp;</td>
										<td width="40%" align="right">
											<input name="checkbox" type="checkbox" class="noborder" onClick="CheckAll(studentForm.delIdArray,studentForm.checkbox)" />
											[全选/反选] 
											[<a style="color: red; cursor: hand;" onClick="checkdel(studentForm.delIdArray,studentForm)">删除</a>]
											<!--层ch用于放置隐藏的checkbox控件，因为当表单中只是一个checkbox控件时，应用javascript获得其length属性值为undefine-->		
											<div id="ch" style="display:none">
												<input name="delid" type="checkbox" class="noborder" value="0" />
											</div>
										</td>
									</tr>
								</table>
								</form>
							</td>
						</tr>
					</table></td>
			</tr>
		</table>
		<%@ include file="/manage/copyright.jsp"%>
	</body>
</html>
