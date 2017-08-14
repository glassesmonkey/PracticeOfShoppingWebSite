package shop.goods;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.rowset.CachedRowSetImpl;

import shop.db.DbClose;
import shop.db.DbConn;
import shop.entity.Goods;
import shop.entity.Login;
public class PayGoods extends HttpServlet{
    public PayGoods() {
	    // TODO Auto-generated constructor stub
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	 response.setContentType("text/html;charset=UTF-8");
         request.setCharacterEncoding("UTF-8");
         PrintWriter out = response.getWriter();
         //从模型中直接拿取购物车信息
         HttpSession session = request.getSession(true);
         Login loginBean = (Login)session.getAttribute("loginBean");
         String userName = "myNull";
         userName = loginBean.getUsername();
         LinkedList<String> car = null;
         car = loginBean.getCar();
         
         if(car.size() != 0){
        	 boolean falg = false;
             Connection        conn  = null;
             PreparedStatement pstmtCommodity = null;
             PreparedStatement pstmtOrder = null;
             
             
         }else{
        	 out.print("<br>");
        	 out.print("<br>");
        	 out.print("<p>购物车为空</p>");
         }
         return;
    }
}
