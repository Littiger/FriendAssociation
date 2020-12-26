<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>网络在线考试</title>
		<link rel="stylesheet" href="<%=request.getContextPath() %>/CSS/style.css" />

		<script language=javascript>
			function keydown() {
				if (event.keyCode == 8) {
					event.keyCode = 0;
					event.returnValue = false;
					alert("当前设置不允许使用退格键");
				}
				if (event.keyCode == 13) {
					event.keyCode = 0;
					event.returnValue = false;
					alert("当前设置不允许使用回车键");
				}
				if (event.keyCode == 116) {
					event.keyCode = 0;
					event.returnValue = false;
					alert("当前设置不允许使用F5刷新键");
				}
				if ((event.altKey)
						&& ((window.event.keyCode == 37) || (window.event.keyCode == 39))) {
					event.returnValue = false;
					alert("当前设置不允许使用Alt+方向键←或方向键→");
				}
				if ((event.ctrlKey) && (event.keyCode == 78)) {
					event.returnValue = false;
					alert("当前设置不允许使用Ctrl+n新建IE窗口");
				}
				if ((event.shiftKey) && (event.keyCode == 121)) {
					event.returnValue = false;
					alert("当前设置不允许使用shift+F10");
				}
			}
			function click() {
				event.returnValue = false;
				alert("当前设置不允许使用右键！");
			}
			document.oncontextmenu = click;
		</script>
		
	</head>
	
	<body onkeydown="keydown()">
	
		<table width="960" align="center" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td width="40" height="39" background="<%=request.getContextPath() %>/Images/startExam_leftTop.jpg">&nbsp;</td>
				<td align="right" background="<%=request.getContextPath() %>/Images/startExam_top.jpg">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="23%" nowrap height=23 align=right>考试时间：</td>
							<td width="14%" nowrap><font color="#FF0000">20</font>分钟</td>
							<td width="13%" nowrap>计 时：</td>
							<td width="60" nowrap><div id="showStartTimediv">00:00:00</div>
							</td>
							<td width="16%" nowrap>剩余时间：</td>
							<td width="60" align=left nowrap><div id="showRemainTimediv"></div></td>
						</tr>
					</table>
				</td>
				<td width="19" background="<%=request.getContextPath() %>/Images/startExam_top.jpg">&nbsp;</td>
				<td width="44" background="<%=request.getContextPath() %>/Images/startExam_rightTop.jpg">&nbsp;</td>
			</tr>
			<tr>
				<td height="435" rowspan="2" background="<%=request.getContextPath() %>/Images/startExam_left.jpg">&nbsp;</td>
				<td height="43" colspan="2">
					<img src="<%=request.getContextPath() %>/Images/startExam_ico.jpg" width="117" height="43">
				</td>
				<td rowspan="2" background="Images/startExam_right.jpg">&nbsp;</td>
			</tr>
			<tr>
				<td height="600" colspan="2" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="2" align="center" class="title">
								电子商务&nbsp;&nbsp;考试卷
							</td>
						</tr>
						<tr>
							<td width="64%">&nbsp;</td>
							<td width="36%">&nbsp;
								满分<font color="red">100</font>分&nbsp;&nbsp;&nbsp;
								单选题<font color="red">40</font>分&nbsp;&nbsp;&nbsp;
								多选题<font color="red">60</font>分
							</td>
						</tr>
					</table> 
					
					<form action="<%=request.getContextPath() %>/exam/submitPaper.jsp" method="post">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<table id="single" width="90%" border="0" cellspacing="0" cellpadding="0" align=center>
	
										<tr>
											<td colspan="4" height=23 style="font-size: 11pt;">
												一、单选题（<font color=red>每题 5 分，答错不得分</font>）
											</td>
										</tr>
										<%
											for(int i=0;i<10;i++){
										%>
											<tr>
												<td height=23 colspan="4" align=center nowrap>
													<table width="100%" border="0" cellspacing="0"
														cellpadding="0">
														<tr>
															<td width="8%" align=right height=23>[&nbsp;<%=i+1 %>&nbsp;]</td>
															<td width="2%">&nbsp;</td>
															<td width="90%" align=left nowrap style="font-size: 11pt;">
																网络营销的发展经历几个阶段？
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td width="8%" height=23 nowrap>&nbsp;</td>
												<td width="3%" align=center nowrap>
													<input type="radio" name="answer" class="noborder" value="A" />
												</td>
												<td width="3%" align=center nowrap>A.</td>
												<td width="86%" align=left nowrap>2个</td>
											</tr>
											<tr>
												<td width="8%" height=23 nowrap>&nbsp;</td>
												<td width="3%" align=center nowrap>
													<input type="radio" name="answer" class="noborder" value="A" />
												</td>
												<td width="3%" align=center nowrap>B.</td>
												<td width="86%" align=left nowrap>3个</td>
											</tr>
											<tr>
												<td width="8%" height=23 nowrap>&nbsp;</td>
												<td width="3%" align=center nowrap>
													<input type="radio" name="answer" class="noborder" value="A" />
												</td>
												<td width="3%" align=center nowrap>C.</td>
												<td width="86%" align=left nowrap>5个</td>
											</tr>
											<tr>
												<td width="8%" height=23 nowrap>&nbsp;</td>
												<td width="3%" align=center nowrap>
													<input type="radio" name="answer" class="noborder" value="A" />
												</td>
												<td width="3%" align=center nowrap>D.</td>
												<td width="86%" align=left nowrap>6个</td>
											</tr>
										<%} %>
									</table>
	
									<table id="single" width="90%" border="0" cellspacing="0" cellpadding="0" align=center>
	
										<tr>
											<td colspan="4" height=23 style="font-size: 11pt;">
												二、多选题（<font color=red>每题 5 分，答错不得分</font>）
											</td>
										</tr>
										<%
											for(int i=0;i<10;i++){
										%>
											<tr>
												<td height=23 colspan="4" align=center nowrap>
													<table width="100%" border="0" cellspacing="0"
														cellpadding="0">
														<tr>
															<td width="8%" align=right height=23>[&nbsp;<%=i+1 %>&nbsp;]</td>
															<td width="2%">&nbsp;</td>
															<td width="90%" align=left nowrap style="font-size: 11pt;">
																Internet提供的基本服务有哪些？
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td width="8%" height=23 nowrap>&nbsp;</td>
												<td width="3%" align=center nowrap>
													<input type="checkbox" name="answerArr" class="noborder" value="A"/>
												</td>
												<td width="3%" align=center nowrap>A.</td>
												<td width="86%" align=left nowrap>E-mail</td>
											</tr>
											<tr>
												<td width="8%" height=23 nowrap>&nbsp;</td>
												<td width="3%" align=center nowrap>
													<input type="checkbox" name="answerArr" class="noborder" value="B"/>
												</td>
												<td width="3%" align=center nowrap>B.</td>
												<td width="86%" align=left nowrap>FTP</td>
											</tr>
											<tr>
												<td width="8%" height=23 nowrap>&nbsp;</td>
												<td width="3%" align=center nowrap>
													<input type="checkbox" name="answerArr" class="noborder" value="C"/>
												</td>
												<td width="3%" align=center nowrap>C.</td>
												<td width="86%" align=left nowrap>Telnet</td>
											</tr>
											<tr>
												<td width="8%" height=23 nowrap>&nbsp;</td>
												<td width="3%" align=center nowrap>
													<input type="checkbox" name="answerArr" class="noborder" value="D"/>
												</td>
												<td width="3%" align=center nowrap>D.</td>
												<td width="86%" align=left nowrap>WWW</td>
											</tr>
										<%} %>
									</table>
								</td>
							</tr>
							<tr>
								<td align="center">
									<input type="submit" class="btn_grey" value=" 交卷 " />
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td width="40" height="40" background="<%=request.getContextPath() %>/Images/startExam_leftBottom.jpg">&nbsp;</td>
				<td colspan="2" background="<%=request.getContextPath() %>/Images/startExam_bottom.jpg">&nbsp;</td>
				<td background="<%=request.getContextPath() %>/Images/startExam_rightBottom.jpg">&nbsp;</td>
			</tr>
		</table>
	</body>
</html>
