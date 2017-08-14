<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/index.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品细节</title>
</head>
<body>
       <center><br><br>
         <table border="1" bordercolor="#4EEE94" cellpadding="10" cellspacing="0" width="400" height="80">
             <tr bgcolor=#458B74>
                <th>编号</th>
                <th>名称</th>
                <th>产地</th>
                <th>价格(￥)</th>
                <th>余量</th>
                <th>添加到购物车</th>
            </tr>
<% String details = request.getParameter("detail");
   String[] detail = details.split(",");
   String mButton = "<form action='goods.PutGoodsToCar' method='post'>"+
                    "<input type='hidden' name='GoodsCar' value="+details+">"+
                    "<input type='submit' value='加入购物车'></form>";
%><tr bgcolor=#43CD80><%
	            for(int i=0;i<5;++i)
	            {%>
		               <td><%= detail[i]%></td>
	            <%}%>
	                   <td><%= mButton%></td>
             </tr>
         </table>
         <br>
         <img src="<%= path %>/image/goods/<%= detail[5] %>" width="360" height="300"></img>
        </center>
</body>
</html>