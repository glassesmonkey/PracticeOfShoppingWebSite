<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="navbar.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<% request.setCharacterEncoding("UTF-8"); %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>尽情购物吧！</title>
    <base href="<%=basePath%>">
    
    <title>首页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
</head>
<body>
 <jsp:useBean id="loginBean" class="shop.entity.Login" scope="session"/>
 		<ul class="user">
    			<li>
    				<%
    				    String str = null;
    				    str = loginBean.getUsername();
    					if(str.equals("userNull")||str==null)
    					{
    					   HttpSession s= request.getSession(true);
                           s.invalidate();
                        %>
	     					<a href="jsp/join/login.jsp">登录</a>or<a href="jsp/join/register.jsp">注册</a>
    					<%
    					   return;
    					}
    				 %>
   						<dl>
   							<dt>
	    						<a>欢迎您,<b><font color="red"><%= str %></font></b></a>
	    						<a href="<%= path %>/control.HandleExit"><font color="#CDC9C9">退出</font></a>
   							</dt>
   						</dl>
    			</li>
    		</ul>
</body>
</html>