<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/navbar.jsp" %>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="shop.entity.Login"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<% request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>请登录</title>
</head>
<body>
	<% 
		/* 从本地磁盘中获取已保存的cookie */
		request.setCharacterEncoding("UTF-8");
		String username = "";
		String userpass = "";
		Cookie[] cookies = null;
		cookies = request.getCookies();
		if(cookies!=null && cookies.length>0)
		{
			for(Cookie c:cookies)
			{
				if("username".equals(c.getName()))
				{
					username = URLDecoder.decode(c.getValue(),"UTF-8");//解码并取值、赋值
				}
				if("userpass".equals(c.getName()))
				{
					userpass = URLDecoder.decode(c.getValue(),"UTF-8");
				}
			}
		}
	%>
	<jsp:useBean class="shop.entity.LoginStatus" id="login" scope="request">
	<jsp:setProperty name="login" property="backNews" value=" "/>
	</jsp:useBean>

	<div align="center">
		<form action="<%= path %>/control.HandleLogin" method="post">
		    </br>
			<table border="0" cellpadding="15" cellspacing="1">
				<tr>
					<td colspan="2">
						<input name="username" value="<%= username %>" placeholder="请输入用户名"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="password" name="userpass" value="<%= userpass %>" placeholder="请输入密码"/>
					</td>
				</tr>
				<tr>
					<td>
						<FONT color=red>&nbsp;<jsp:getProperty name="login" property="backNews"/></FONT>
					</td>
				</tr>
				<tr>

					<td>
						<input type="checkbox" name="isCookie" value="isCookie" checked="checked">记我十天
					</td>

					<td>
						<input type="submit" value="登陆"/>
					</td>					

				</tr>
			</table>
			
		</form>
	</div>
</body>
</html>