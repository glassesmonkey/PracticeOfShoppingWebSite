package shop.goods;

import java.io.IOException;
import java.util.LinkedList;

import javax.naming.LinkLoopException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import shop.entity.Login;

public class DeleteGoodsFromCar extends HttpServlet{
    public DeleteGoodsFromCar() {
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
    	Login loginBean = null;
    	HttpSession session = request.getSession(true);
    	loginBean = (Login) session.getAttribute("loginBean");
    	
    	String mDeleteNum = request.getParameter("ID");
        int num = Integer.parseInt(mDeleteNum);
        
        LinkedList<String> car = loginBean.getCar();
        car.remove(num);
        loginBean.setCar(car);
        request.getRequestDispatcher("/jsp/shoppingCar/lookShoppingCar.jsp").forward(request, response);
    }
}
