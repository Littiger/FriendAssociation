<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>网络在线考试――后台管理</title>
<link href="<%=request.getContextPath()%>/CSS/style.css"
	rel="stylesheet"></link>
<script type="text/javascript">
	
	function show(value) {
		
		var sOp = document.getElementById("sOption");
		var mOp = document.getElementById("mOption");
		
		if (value == "单选题"){
			
			sOp.style.display= "block";
			mOp.style.display= "none";
			
		}else if (value == "多选题"){
			
			sOp.style.display= "none";
			mOp.style.display= "block";
		}
	}
	
	 function checkForm(form) {
			if (form.course_id.value=="%"){
				form.course_id.focus();
				alert("请选择课程");
				return false;
			}
			if (form.subject_name.value==""){
				form.subject_name.focus();
				alert("请填入题目名称");
				return false;
			}
			if (form.subject_type.value=="%"){
				form.subject_type.focus();
				alert("请选择题目类型");
				return false;
			}
			if (form.subject_A.value==""){
				form.subject_A.focus();
				alert("请填入A的选项内容");
				return false;
			}
			if (form.subject_B.value==""){
				form.subject_B.focus();
				alert("请填入B的选项内容");
				return false;
			}
			if (form.subject_C.value==""){
				form.subject_C.focus();
				alert("请填入C的选项内容");
				return false;
			}
			if (form.subject_D.value==""){
				form.subject_D.focus();
				alert("请填入D的选项内容");
				return false;
			}
			if (form.answer.value=="%"){
				form.answer.focus();
				alert("请选择正确答案");
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
										&nbsp;当前位置：<span class="word_darkGrey">考试题目管理&gt;&gt;添加考试题目&gt;&gt;</span>
									</td>
									<td align="right">
										<img src="<%=request.getContextPath()%>/Images/m_ico1.gif" width="5" height="9">&nbsp; 
										当前管理员：${sessionScope.user.manager_name}&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td align="center" valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="84%">&nbsp;</td>
								</tr>
							</table>

							<form name="questionsForm" method="post" action="<%=request.getContextPath()%>/manage/subject.do?method=add" onsubmit="return checkForm(questionsForm)" style="margin: 0px">
								<table width="85%" border="0" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" bordercolordark="#D2E3E6" bordercolorlight="#FFFFFF">

									<tr>
										<td height="30" align="left" style="padding: 5px;">所属课程：</td>
										<td align="left" id="whichTaoTi">
											<select name="course_id" onchange="F_getTaoTi(this.value)">
												<option value="%">---请选择---</option>
												<c:forEach var="map" items="${list}">
												<option value="${map.course_id}" ${(map.course_id eq select_map.course_id)?"selected":""} >${map.course_name} </option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr align="center">
										<td width="15%" height="30" align="left" style="padding: 5px;">考试题目：</td>
										<td width="85%" align="left">
											<input type="text" name="subject_name" size="40" value=""> *
										</td>
									<tr>
									<tr>
										<td height="30" align="left" style="padding: 5px;">试题类型：</td>
										<td align="left">
											<select name="subject_type" onchange="show(this.value)">
												<option value="%">---请选择---</option>
												<option value="单选题">单选题</option>
												<option value="多选题">多选题</option>
											</select>
										</td>
									</tr>
									<tr align="center">
										<td width="15%" height="30" align="left" style="padding: 5px;">选项A：</td>
										<td width="85%" align="left">
											<textarea name="subject_A" rows="2" cols="40"></textarea>*
										</td>
									</tr>
									<tr align="center">
										<td width="15%" height="30" align="left" style="padding: 5px;">选项B：</td>
										<td width="85%" align="left">
											<textarea name="subject_B" rows="2" cols="40"></textarea>*
										</td>
									</tr>
									<tr align="center">
										<td width="15%" height="30" align="left" style="padding: 5px;">选项C：</td>
										<td width="85%" align="left">
											<textarea name="subject_C" rows="2" cols="40"></textarea>*
										</td>
									</tr>
									<tr align="center">
										<td width="15%" height="30" align="left" style="padding: 5px;">选项D：</td>
										<td width="85%" align="left">
											<textarea name="subject_D" rows="2" cols="40"></textarea>*
										</td>
									</tr>
									<tr align="center">
										<td width="15%" height="30" align="left" style="padding: 5px;">正确答案：</td>
										<td width="85%" align="left" id="sOption">
											<select name="answer">
												<option value="%">---请选择---</option>
												<option value="A">A</option>
												<option value="B">B</option>
												<option value="C">C</option>
												<option value="D">D</option>
											</select>
										</td>
										<td width="85%" align="left" id="mOption" style="display: none">
											<input type="checkbox" name="answerArr" value="A" class="noborder">A 
											<input type="checkbox" name="answerArr" value="B" class="noborder">B
											<input type="checkbox" name="answerArr" value="C" class="noborder">C 
											<input type="checkbox" name="answerArr" value="D" class="noborder">D
										</td>
									</tr>

									<tr align="center">
										<td width="15%" height="30" align="left" style="padding: 5px;">备注：</td>
										<td width="85%" align="left">
											<input type="text" name="subject_remark" size="40" value="">
										</td>
									</tr>
									<tr>
										<td height="65" align="left" style="padding: 5px;">&nbsp;</td>
										<td align="left">
											<input type="submit" name="submit" value=" 保存 " class="btn_grey"> &nbsp; 
											<input type="button" name="button" value=" 返回 " onclick="window.location.href='<%=request.getContextPath() %>/manage/subject.do?method=query'" class="btn_grey">
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
