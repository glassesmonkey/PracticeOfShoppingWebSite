package shop.goods;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
import shop.entity.Login;

public class PutGoodsToCar extends HttpServlet{
    public PutGoodsToCar(){
    	
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	doGet(req, resp);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	// TODO Auto-generated method stub
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String goods = null;
        goods = request.getParameter("GoodsCar");
        if(goods==null){
        	response.sendRedirect("/Shopping/index.jsp");
        }else{
        	String[] details = null;
        	details = goods.split(",");
        	HttpSession session = request.getSession(true);
        	Login loginBean = (Login)session.getAttribute("loginBean");
        	LinkedList<String>car = null;
        	car = loginBean.getCar();
        	car.add(goods);
        	loginBean.setCar(car);
        	backNews(request, response, details[1]);
        }
    	

    	
    }
    
	private void backNews(HttpServletRequest request,
			HttpServletResponse response, String goodsName) throws IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		out.print("<br><br><br>");
        out.print("<center><font size=5 color=red><B>"+goodsName+"</B></font>&nbsp;已成功添加购物车");
        out.print("<br><br><br>");
        out.print("<a href=/Shopping/jsp/browse/showGoods.jsp>返回继续购物</a>");
        out.print("&nbsp;或&nbsp;");
        out.print("<a href=/Shopping/jsp/shoppingCar/lookShoppingCar.jsp>查看购物车</a></center>");
		
	}
	
    public void init()
            throws ServletException
        {
            // Put your code here
        }
}
