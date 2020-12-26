<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td height="29" bgcolor="#5898C8"><span class="word_white">&nbsp;网站后台管理</span></td>
		</tr>
		<tr>
			<td height="4"></td>
		</tr>
	</table>
	
	<table width="100%" height="441" border="0" cellpadding="0" cellspacing="0" class="tableBorder">
		<tr>
			<td width="25%" height="32" align="right" class="tableBorder_B">
				<img src="<%=request.getContextPath() %>/Images/m_ico.gif" width="11" height="11" />&nbsp;
			</td>
			<td width="75%" align="left" class="tableBorder_B">
				<a href="<%=request.getContextPath() %>/manage/manager.do?method=query">管理员信息管理</a>
			</td>
		</tr>
		<tr>
			<td width="25%" height="32" align="right" class="tableBorder_B">
				<img src="<%=request.getContextPath() %>/Images/m_ico.gif" width="11" height="11" />&nbsp;
			</td>
			<td height="32" colspan="2" align="left" class="tableBorder_B">
				<a href="<%=request.getContextPath() %>/manage/classs.do?method=query">班级信息管理</a>
			</td>
		</tr>
		<tr>
			<td width="25%" height="32" align="right" class="tableBorder_B">
				<img src="<%=request.getContextPath() %>/Images/m_ico.gif" width="11" height="11" />&nbsp;
			</td>
			<td height="32" colspan="2" align="left" class="tableBorder_B">
				<a href="<%=request.getContextPath() %>/manage/examinee.do?method=query">考生信息管理</a>
			</td>
		</tr>
		<tr>
			<td width="25%" height="32" align="right" class="tableBorder_B">
				<img src="<%=request.getContextPath() %>/Images/m_ico.gif" width="11" height="11" />&nbsp;
			</td>
			<td height="32" colspan="2" align="left" class="tableBorder_B">
				<a href="<%=request.getContextPath() %>/manage/grade.do?method=query">考生成绩查询</a>
			</td>
		</tr>
		<tr>
			<td width="25%" height="32" align="right" class="tableBorder_B">
				<img src="<%=request.getContextPath() %>/Images/m_ico.gif" width="11" height="11" />&nbsp;
			</td>
			<td height="32" colspan="2" align="left" class="tableBorder_B">
				<a href="<%=request.getContextPath() %>/manage/course.do?method=query">课程信息管理</a>
			</td>
		</tr>
		<tr>
			<td width="25%" height="32" align="right" class="tableBorder_B">
				<img src="<%=request.getContextPath() %>/Images/m_ico.gif" width="11" height="11" />&nbsp;
			</td>
			<td height="32" colspan="2" align="left" class="tableBorder_B">
				<a href="<%=request.getContextPath() %>/manage/subject.do?method=query">考试题目管理</a>
			</td>
		</tr>
		<tr>
			<td width="25%" height="32" align="right" class="tableBorder_B">
				<img src="<%=request.getContextPath() %>/Images/m_ico.gif" width="11" height="11" />&nbsp;
			</td>
			<td height="32" colspan="2" align="left" class="tableBorder_B">
				<a href="<%=request.getContextPath() %>/manage/testpaper.do?method=query">试卷管理</a>
			</td>
		</tr>
		<tr>
			<td width="25%" height="32" align="right" class="tableBorder_B">
				<img src="<%=request.getContextPath() %>/Images/m_ico.gif" width="11" height="11" />&nbsp;
			</td>
			<td height="32" colspan="2" align="left" class="tableBorder_B">
				<a href="<%=request.getContextPath() %>/manage/login.do?method=logout">退出后台管理</a>
			</td>
		</tr>
		<tr>
			<td height="217" align="right">&nbsp;</td>
			<td height="217" colspan="2" align="left">&nbsp;</td>
		</tr>
	</table>
