<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%><html>
	<head>
		<title>网络在线考试――后台管理</title>
		<link href="<%=request.getContextPath() %>/CSS/style.css" rel="stylesheet"></link>
		<script type="text/javascript">
		function checkForm(form) {
			if (form.testpaper_name.value==""){
				form.testpaper_name.focus();
				alert("请填入试卷名称");
				return false;
			}
			
			if (form.testpaper_radio_num.value === "" || form.testpaper_radio_num.value ==null){
				form.testpaper_radio_num.focus();
				alert("请输入单选题数量");
			    return false;
			}else{
				var val = form.testpaper_radio_num.value;
				if(parseFloat(val).toString() == "NaN"){　　　　
					form.testpaper_radio_num.focus();
					alert("输入存在非法字符");
	　　　　			return false; 
				} 
			}
			
			if (form.testpaper_check_num.value === "" || form.testpaper_check_num.value ==null){
				form.testpaper_check_num.focus();
				alert("请输入多选题数量");
			    return false;
			}else{
				var val = form.testpaper_check_num.value;
				if(parseFloat(val).toString() == "NaN"){　　　　
					form.testpaper_check_num.focus();
					alert("输入存在非法字符");
	　　　　			return false; 
				} 
			}
			
			if (form.testpaper_time_use.value === "" || form.testpaper_time_use.value ==null){
				form.testpaper_time_use.focus();
				alert("请输入考试时间");
			    return false;
			}else{
				var val = form.testpaper_time_use.value;
				if(parseFloat(val).toString() == "NaN"){　　　　
					form.testpaper_time_use.focus();
					alert("输入存在非法字符");
	　　　　			return false; 
				} 
			}
			
			if (form.classs_id.value=="%"){
				form.classs_id.focus();
				alert("请选择班级");
				return false;
			}
			if (form.course_id.value=="%"){
				form.course_id.focus();
				alert("请选择课程");
				return false;
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
				<td align="right" valign="top" bgcolor="#FFFFFF">
					<table width="99%" border="0" cellpadding="0" cellspacing="0" align="center">
						<tr>
							<td height="30" bgcolor="#EEEEEE" class="tableBorder_thin">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="78%" class="word_grey" align="left">
											&nbsp;当前位置：<span class="word_darkGrey">试卷信息管理 &gt;&gt;修改试卷信息&gt;&gt;</span>
										</td>
										<td align="right">
											<img src="<%=request.getContextPath() %>/Images/m_ico1.gif" width="5" height="9">&nbsp;
											当前管理员：${sessionScope.user.manager_name}&nbsp;
										</td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td align="center" valign="top">
								<table width="100%"  border="0" cellspacing="0" cellpadding="0">
									<tr>
								   		<td width="84%">&nbsp;      </td>
									</tr>
								</table> 
								
								<form name="form1" method="post" action="<%=request.getContextPath() %>/manage/testpaper.do?method=edit" onsubmit="return checkForm(form1)">
									<table width="96%"  border="0" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" bordercolordark="#D2E3E6" bordercolorlight="#FFFFFF">
										<tr align="center">
								    		<td width="17%" height="30" align="left" style="padding:5px;">试卷名称：</td>
								    		<td align="left">
								      			<input type="text" name="testpaper_name" size="30" value="${map.testpaper_name}">
								      			<input type="text" name="testpaper_id" size="30" value="${map.testpaper_id}" style="display: none">
									  		</td>
										</tr>
										<tr align="center">
								    		<td width="17%" height="30" align="left" style="padding:5px;">单选题数量：</td>
								    		<td align="left">
								      			<input type="text" name="testpaper_radio_num" size="30" value="${map.testpaper_radio_num}">
									  		</td>
										</tr>
										<tr align="center">
								    		<td width="17%" height="30" align="left" style="padding:5px;">多选题数量：</td>
								    		<td align="left">
								      			<input type="text" name="testpaper_check_num" size="30" value="${map.testpaper_check_num}">
									  		</td>
										</tr>
										<tr align="center">
								    		<td width="17%" height="30" align="left" style="padding:5px;">考试用时(分钟)：</td>
								    		<td align="left">
								      			<input type="text" name="testpaper_time_use" size="30" value="${map.testpaper_time_use}">
									  		</td>
										</tr>
										<tr align="center">
								    		<td width="17%" height="30" align="left" style="padding:5px;">所属班级：</td>
								    		<td align="left">
												<select name="classs_id">
													<option value="%">---请选择---</option>
													<c:forEach  var="map1"  varStatus="i"  items="${list_classs}">
													<option value="${map1.classs_id}" ${(map1.classs_id eq map.classs_id)? "selected":"" }>${map1.classs_name}</option>
													</c:forEach>
												</select>
									  		</td>
										</tr>
										<tr align="center">
								    		<td width="17%" height="30" align="left" style="padding:5px;">所属套题：</td>
								    		<td align="left">
												<select name="course_id" onchange="F_getTaoTi(this.value)">
													<option value="%">---请选择---</option>
													<c:forEach  var="map1"  varStatus="i"   items="${list_course}">
													<option value="${map1.course_id}" ${map1.course_id}" ${(map1.course_id eq map.course_id)? "selected":"" }>${map1.course_name}</option>
													</c:forEach>
												</select>
									  		</td>
										</tr>
								    	<tr>
								      		<td height="65" align="left" style="padding:5px;">&nbsp;</td>
								      		<td align="left">
								      			<input type="submit" name="submit" value=" 保存 " class="btn_grey">
												&nbsp;
												<input type="button" name="button" value=" 返回 " onclick="window.location.href='<%=request.getContextPath() %>/manage/testpaper/list.jsp'" class="btn_grey">		
											</td>
								    	</tr>
									</table>
								</form>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%@ include file="/manage/copyright.jsp"%>
	</body>
</html>
