package shop.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import shop.entity.Goods;
import shop.entity.Login;

import com.mysql.jdbc.Connection;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
import com.sun.rowset.CachedRowSetImpl;

import shop.db.*;
public class GoodsDao extends HttpServlet{
	public GoodsDao(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost( req,  resp);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;chartset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String value = "";
		value = request.getParameter("key");
		int key = Integer.parseInt(value);
		System.out.println("检测是否有key:"+key);
		
		String keyWord = "";
		keyWord = request.getParameter("keyWord");
		System.out.println(keyWord);
		queryGoods(request, response, key,keyWord);
	}
	private void queryGoods(HttpServletRequest request,
			HttpServletResponse response, int key, String keyWord) throws IOException, ServletException {
		// TODO Auto-generated method stub
	    response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        CachedRowSetImpl rowSet = null;//行集对象
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Goods goods = null;
		Login username = null;
//		OrderForm orderForm = null;
		
		HttpSession session = request.getSession(true);
		username = (Login)session.getAttribute("loginBean");
		goods = (Goods)session.getAttribute("goods");
//		orderForm = (OrderForm)session.getAttribute("orderForm");
//		ArrayList<Goods> goodsList = new ArrayList<Goods>();
		if (goods==null)
		{
			goods = new Goods();
			session.setAttribute("goods", goods);
		}
		if (username==null)
		{
		    username = new Login();
		    session.setAttribute("username", username);
		}
//		if (orderForm==null)
//		{
//		    orderForm = new OrderForm();
//		    session.setAttribute("orderForm", orderForm);
//		}
		  //判断用户是否登陆
		  String user = "";
          user = username.getUsername();//登陆者的用户名
          System.out.println("我是用户："+user);
      
          if (user.equals("userNull"))
          {
              out.print("<br>");
              out.print("<center><font color=#008B8B> 登陆之后才能看订单哦  </font>");
              out.print("<a href=/Shopping/jsp/join/login.jsp><font color=red size=6>登陆</font></a></center>");
              return;
          }
		
		conn = (Connection) DbConn.getConn();
		
		switch(key){
		case 1:
			
			break;
		case 2:
			
			break;
		case 3:
            //key=3 按照登录人查询订单 商品名字+数量
		      
            String sqlOrder= 
            "select commodity_name,sum(sum) from orderform where username=? group by commodity_name having sum(sum)>0";
            try
            {
                pstmt = conn.prepareStatement(sqlOrder);
                pstmt.setString(1, user);
                rs = pstmt.executeQuery();
                System.out.println("--查看订单执行数据库操作--");
                if(rs.next())
                {
                    rs = pstmt.executeQuery();//重新查询的原因是rs.next时光标偏移后，丢掉记录。
                    rowSet = new CachedRowSetImpl();
                    rowSet.populate(rs); 
                    goods.setRowSet(rowSet);
                    System.out.println("3已经从数据库中获取到值，并塞进行集");
                    request.getRequestDispatcher("/jsp/order/lookOrderForm.jsp").forward(request, response);
                }else 
                    {
                        out.print("<br><br><br><center>");
                        out.print("<font color=green> 亲,订单是空的呢 </font>");
                        out.print("<a href=/Shopping/dao.GoodsDao?key=4><font color=red size=6>Go Shopping</font></a>");
                        out.print("</center>");		
                    }
            } catch (SQLException e)
            {
                System.out.println("key=3查看订单异常："+e);
                
            }finally
                    {
                        System.out.println("查看订单执行关闭流");
                        DbClose.allClose(pstmt, rs, conn);
                    }
            break;
		case 4:
			String sql = "select*from commodity";
			StringBuffer url =request.getRequestURL();
			try{
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				System.out.println("查询商品");

				if(rs.next()){
					rs = pstmt.executeQuery();
					rowSet= new CachedRowSetImpl();
					rowSet.populate(rs);
					goods.setRowSet(rowSet);
					System.out.println("4浏览商品已经从数据库中获取到值，并塞进行集");
					request.getRequestDispatcher("/jsp/browse/showGoods.jsp").forward(request, response);
				}else{
                    out.print("<br><br><br><center>");
                    out.print("<font color=green> 亲,卖家还没上货呢 </font>");
                    out.print("<a href=index.jsp><font color=red size=6>进入首页</font></a>");
                    out.print("</center>"); 
				}
			}catch(SQLException e){
				e.printStackTrace();
				response.sendRedirect("Shopping/jsp/browse/showGoods.jsp");
			}finally{
				DbClose.allClose(pstmt, rs, conn);
			}
			
			break;
		default:
			break;
		}
	}

}
