package shop.control;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;

import shop.entity.Register;
import shop.entity.Login;
import shop.db.*;
public class HandleRegister extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public HandleRegister(){
		super();
	}
	public void destroy()
	{
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		Register userBean = new Register();
		request.setAttribute("userBean", userBean);
		
		
		
		String username = "";
		String userpass = "";
		String again_userpass = "";
		String phone = "";
		String address = "";
		String realname = "";
		int MIN_PASSWORD = 6;
		int MAX_PASSWORD = 16;
		username = request.getParameter("username").trim();
		userpass = request.getParameter("userpass").trim();
		again_userpass = request.getParameter("again_userpass").trim();
		phone = request.getParameter("phone").trim();
		address = request.getParameter("address").trim();
		realname = request.getParameter("realname").trim();
		if(username ==null){
			username = "";
		}
		if (userpass==""|userpass==null)
		{
			userpass = "error";
		}
		String regex = "[\\d]{11}";
		if(!(userpass.equals(again_userpass))){
			userBean.setBackNews("两次密码不一致");
			request.getRequestDispatcher("/jsp/join/register.jsp").forward(request, response);
		}else if(phone != null & phone.length()>0 & !(phone.matches(regex))){
			userBean.setBackNews("请输入正确的11位号码");
			request.getRequestDispatcher("/jsp/join/register.jsp").forward(request, response);
			return;
		}else{
			String backNews ="";
			if(userpass.length() >= MIN_PASSWORD & userpass.length() <= MAX_PASSWORD){
				//打开数据库 保存内容 检查用户名是否重复
				Connection conn = null;
				PreparedStatement pstmt = null;
				
				conn = (Connection) DbConn.getConn();
				String sql ="INSERT INTO vip (username,userpass,phone,address,realname) VALUES (?,?,?,?,?)";
				try{
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1,username); 
					pstmt.setString(2,userpass); 
					pstmt.setString(3,phone);
					pstmt.setString(4,address);
					pstmt.setString(5,realname);
					int rs = pstmt.executeUpdate();
					if (rs > 0)
					{
						backNews = "注册成功";
						userBean.setBackNews(backNews);
						Login loginBean = null;
						HttpSession session = request.getSession(true);
						loginBean = (Login) session.getAttribute("loginBean");
						loginBean.setUsername(username);
						request.getRequestDispatcher("/jsp/join/registerSuccess.jsp").forward(request, response);
					}
				}catch(SQLException e){
					backNews = "该用户名已被注册";
					userBean.setBackNews(backNews);
					System.out.println(e);
					request.getRequestDispatcher("/jsp/join/register.jsp").forward(request, response);
				}finally{
					DbClose.close(pstmt, conn);
				}
				
			}else{
				backNews = "请输入6~16位长度密码";
				userBean.setBackNews(backNews);
			
				request.getRequestDispatcher("/jsp/join/register.jsp").forward(request, response);
			}
		}
		
		
	}
}
