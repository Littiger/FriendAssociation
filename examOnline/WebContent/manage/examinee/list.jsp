<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>网络在线考试――后台管理</title>
		<link href="<%=request.getContextPath() %>/CSS/style.css" rel="stylesheet"></link>
		<script language="javascript">
			function CheckAll(elementsA, elementsB) {
				for (i = 0; i < elementsA.length; i++) {
					elementsA[i].checked = true;
				}
				if (elementsB.checked == false) {
					for (j = 0; j < elementsA.length; j++) {
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
			
			//导出
			function  exportExcel()
			{
            // alert("导出excel。。。。");				
		     var  key=document.getElementById("key").value;
             //alert("key:"+key);
             var  sect=document.getElementById("sect");        
             //alert("sect:"+sect);	
             var index=sect.selectedIndex;
             //alert("index:"+index);
             var classs_id;//定义一个变量用来承接下拉框的option选项的value值
             if (index==0)
             {
				classs_id="";
			 } 
             else
             {
                 var   selectedIndex=sect.options[index].value;
			   //  alert("value:"+selectedIndex);
			     classs_id=selectedIndex;
             }
             var url="<%=request.getContextPath()%>/manage/examinee.do?method=doExportExcel&clasId="+classs_id+"&ky="+key+"";
             //alert(encodeURI(url));
			
             window.location.href=encodeURI(url);
             
			}
			
			//导出
			function importExcel() 
			{
				alert("导入excel....");
			    var fed=document.getElementById("fed").value;
			    alert("fed:"+fed);
			    if (fed.length<=0) 
			    {
					alert("请选择一个excel文件进行导入");
					return false;
				}
			    var url="<%=request.getContextPath()%>/manage/examinee.do?method=doImportExcel&fed="+fed+"";
			    alert("url:"+encodeURI(url));
			    window.location.href=encodeURI(url);
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
											&nbsp;当前位置：
											<span class="word_darkGrey">考生信息管理 &gt;&gt;&gt;</span>
										</td>
										<td align="right">
											<img src="<%=request.getContextPath() %>/Images/m_ico1.gif" width="5" height="9">&nbsp;
											当前管理员：${sessionScope.user.manager_name}&nbsp;
											<font color="red"><%=application.getAttribute("numSessions") %>个人</font>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center" valign="top">
								<form action="<%=request.getContextPath() %>/manage/examinee.do?method=query" method="post" style="margin: 0px">
								<table width="96%" height="40" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td align="left" valign="middle">&nbsp;
											所在班级： 
											<select id="sect" name="classs_id">
												<option value="">查询所有</option>
												<c:forEach var="map" items="${list_classs}">
												<option value="${map.classs_id}" ${(map.classs_id eq requestScope.classs_id)?"selected":""}> ${map.classs_name}</option>
												</c:forEach>
											</select>
											&nbsp;考生姓名： 
											<input type="text" id="key" name="key"  value="${key == null?'': key}"    />&nbsp;&nbsp;  
											<input type="submit" class="btn_grey" value=" 查询 " />
										</td>
										</td>
										<td>
										  <input type="button" value="导出学生的信息" onclick="exportExcel()"/>
										</td>
									</tr>
									</tr>
										<tr>
									<td>导入学生的信息：<input id="fed" type="file"  name="fe"/></td>
									<td><input type="button" value="导入学生的信息" onclick="importExcel()"/></td>
									</tr>
								</table>
								</form>
								<form name="studentForm" action="<%=request.getContextPath() %>/manage/examinee.do?method=delete" method="post" style="margin: 0px">
									<table width="96%" border="1" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" bordercolordark="#FFFFFF" bordercolorlight="#67A8DB">
										<tr align="center" bgcolor="#A8D8FC">
											<td width="19%" height="26" bgcolor="#B2D6F1">准考证号</td>
											<td width="10%" bgcolor="#B2D6F1">考生姓名</td>
											<td width="7%" bgcolor="#B2D6F1">性别</td>
											<td width="26%" bgcolor="#B2D6F1">加入时间</td>
											<td width="13%" bgcolor="#B2D6F1">密码问题</td>
											<td width="18%" bgcolor="#B2D6F1">身份证号</td>
											<td width="7%" bgcolor="#B2D6F1">选项</td>
										</tr>
										
										 <c:forEach var="map_examinee" items="${list_examinee}">
											<tr>
											 	<td align="center">${map_examinee.examinee_id}</td> 
											    <td style="padding:5px;">${map_examinee.examinee_name}</td>
												<td align="center">${map_examinee.examinee_sex}</td>
												<td align="center">${map_examinee.examinee_time}</td>
											    <td align="center">${map_examinee.examinee_question}</td>	
											    <td align="center">${map_examinee.examinee_identity}</td>
											    <td align="center"><input type="checkbox" name="delIdArray" value="${map_examinee.examinee_id}" class="noborder"></td>
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
