<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<base href="<%=basePath%>">
<title>注册</title>
</head>
<body>
		<% request.setCharacterEncoding("UTF-8"); %>
		<jsp:useBean class="shop.entity.Register" id="userBean" scope="request">
		</jsp:useBean>
		
		<jsp:useBean class="shop.entity.Login" id="loginBean" scope="session">
		</jsp:useBean>
		<div align="center">
			<form action="<%=path %>/control.HandleRegister" method="post">
				<table border="1" cellpadding="10" cellspacing="1">
					<tr>
						<td>用户姓名:<input name="username" placeholder="*必填"/></td>
						<td>用户密码：<input type="password" name="userpass" placeholder="*必填(6-16字符之间)"/></td>
					</tr>
					<tr>
						<td>重复密码：<input type="password" name="again_userpass" placeholder="*必填"/></td>
						<td>联系电话：<input type="text" name="phone" placeholder="*选填"/></td>
					</tr>
					<tr>
						<td>邮寄地址：<input type="text" name="address" placeholder="*选填"/></td>
						<td>真实姓名：<input type="text" name="realname" placeholder="选填"/></td>
					</tr>
						<tr>
							<td>
								<FONT color=red><jsp:getProperty name="userBean" property="backNews"/></FONT>
							</td>
							<td>
								<input type="image" src="<%= path %>/image/page/submit.png" alt="submit" height="40" width="100"/>
							</td>
						</tr>
					</table>
				</form>
			</div>
</body>
</html>