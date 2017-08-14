package shop.control;



import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import shop.db.DbClose;
import shop.db.DbConn;
import shop.entity.Login;
import shop.entity.LoginStatus;




/**
 * 登陆处理
 * @author Lyons(zhanglei)
 *
 */
//gaochao
public class HandleLogin extends HttpServlet 
{
	private static final long serialVersionUID = 1L; //设置序列号
	
	public HandleLogin()
	{
		super();
	}
	public void init() throws ServletException
	{
	}
	public void destroy()
	{
		super.destroy(); // Just puts "destroy" string in log
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
		request.setCharacterEncoding("UTF-8");//servlet中也要此项，否则取值乱码
		String username = "";
		String userpass = "";
		String cookies  = "";
		username = request.getParameter("username");
		userpass = request.getParameter("userpass");
		cookies = request.getParameter("isCookie");
		LoginStatus login = new LoginStatus();
		request.setAttribute("login", login);
		//连接数据库，比对数据库中的账号密码
		//如果账号密码是对的，保存在cookie中
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		conn = DbConn.getConn();
		String sql = "SELECT * FROM vip WHERE username = ? and userpass = ?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, userpass);
			rs = pstmt.executeQuery();
			if(rs.next()){
				//登陆成功
				handleCookies(request,response,username,userpass,cookies);//在登录成功后保存cookies，处理cookies信息
				success(request,response,username,login);
				request.getRequestDispatcher("/jsp/join/landing.jsp").forward(request, response);
			}else{
				String backNews = "密码错误，请重试";
				fail(request, response, backNews,login);
			}
		}catch (SQLException e){
			String backNews = "登录失败: "+e;
			fail(request, response, backNews,login);
		}finally{
			DbClose.allClose(pstmt, rs, conn);
		}
		
		
	}
	
	/**
	 * 处理用户cookies信息
	 * @param request
	 * @param response
	 * @param username
	 * @param userpass
	 */
	public void handleCookies(HttpServletRequest request,HttpServletResponse response, 
			String name,String pass,String isCookie)throws ServletException, IOException
	{
		if ("isCookie".equals(isCookie))//用户选择了记住密码
		{
			String username = URLEncoder.encode(name,"UTF-8");//编码，解决cookie无法保存字符串的问题
			String userpass = URLEncoder.encode(pass,"UTF-8");
			System.out.println("username save in cookie: "+username);
			Cookie nameCookie = new Cookie("username",username );//设置与登陆时的name对应的键值对
			Cookie passCookie = new Cookie("userpass",userpass );
			
			nameCookie.setPath("/");//设置的cookie的存储路径很重要，不然取不到值
			passCookie.setPath("/");
			nameCookie.setMaxAge(60*60*24*10); //设置生命期限十天 单位秒
			passCookie.setMaxAge(60*60*24*10);
			response.addCookie(nameCookie); //保存信息
			response.addCookie(passCookie); 
		}else 
			{
			//用户未选择记住密码，删除浏览器中可能存在的cookie
				Cookie[] cookies = null;
				cookies = request.getCookies();
				if (cookies!=null&&cookies.length>0)
				{
					for (Cookie c : cookies)
					{
						if ("username".equals(c.getName())||"userpass".equals(c.getName()))
						{
							c.setMaxAge(0);//设置cookie失效
							c.setPath("/");//务必设置---设置这个是让整个tomcat上运行的app都可以使用该cookie
							response.addCookie(c);
						}
					}
				}
			}
	}
	
	/**
	 * 登陆成功，储存用户信息
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void success(HttpServletRequest request,
			HttpServletResponse response, String username,LoginStatus login) throws ServletException, IOException
	{
		Login loginBean = null;
		HttpSession session = request.getSession(true);
		
		try
		{
			loginBean = (Login) session.getAttribute("loginBean");//尝试获取loginbean对象里面的信息
			if(loginBean == null){//如果session里面没存信息，就存入对象信息到session
				loginBean = new Login();//让loginbean引用一个新的login对象 这里有问题，这不是一个新的login类吗，里面的东西应该是刚初始化啊
				session.setAttribute("loginBean", loginBean);//注意jsp获取时需要用到该name的属性名字
				session.setMaxInactiveInterval(60*15);//15分钟的存活期 单位：秒
				loginBean = (Login) session.getAttribute("loginBean");
				String name = loginBean.getUsername();
				System.out.println(name+" :gao handlerlogin name");
				if (username.equals(name))
				{
					loginBean.setBackNews("您已登陆，无需再次登录");
					loginBean.setUsername(username);
				}else{
					loginBean.setBackNews("欢迎 "+username+" 请尽情购物吧");
					loginBean.setUsername(username);
				}
			}
		} catch (Exception e){
			String backNews = "登录失败"+e;
			fail(request, response, backNews,login);
		}
	
	}
	
	/**
	 * 登陆失败
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void fail(HttpServletRequest request,
			HttpServletResponse response,String backNews,LoginStatus login) throws ServletException, IOException
	{
			System.out.println("login fail"+backNews);
			login.setBackNews(backNews);
			request.getRequestDispatcher("/jsp/join/login.jsp").forward(request, response);
	}
}
