<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="isLogin.jsp" %>
<%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>跳转</title>
</head>
<body>
<div align="center">
  		<jsp:useBean id="loginBean" class="shop.entity.Login" scope="session"/>
   		<% request.setCharacterEncoding("UTF-8"); %>
        <%
            if(loginBean.getBackNews()=="未登录"||loginBean.getBackNews()==null){
        %>
    		登录失败，请<a href="<%= path %>/jsp/join/login.jsp">重新登录</a>or<a href="<%= path %>/jsp/join/register.jsp">注册</a>
        <% 
            }else{ 
        %>
              <meta http-equiv="refresh" content="3; url=index.jsp">
              <font color="black"><%=loginBean.getBackNews() %></font><br/>
                            三秒后跳转到<a href="index.jsp"><span style="color:blue">首页</span></a>   
                            
        <%
            }
        %>
</div>
</body>
</html>