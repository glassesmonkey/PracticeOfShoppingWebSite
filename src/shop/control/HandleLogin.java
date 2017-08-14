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
 * ��½����
 * @author Lyons(zhanglei)
 *
 */
//gaochao
public class HandleLogin extends HttpServlet 
{
	private static final long serialVersionUID = 1L; //�������к�
	
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
		request.setCharacterEncoding("UTF-8");//servlet��ҲҪ�������ȡֵ����
		String username = "";
		String userpass = "";
		String cookies  = "";
		username = request.getParameter("username");
		userpass = request.getParameter("userpass");
		cookies = request.getParameter("isCookie");
		LoginStatus login = new LoginStatus();
		request.setAttribute("login", login);
		//�������ݿ⣬�ȶ����ݿ��е��˺�����
		//����˺������ǶԵģ�������cookie��
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
				//��½�ɹ�
				handleCookies(request,response,username,userpass,cookies);//�ڵ�¼�ɹ��󱣴�cookies������cookies��Ϣ
				success(request,response,username,login);
				request.getRequestDispatcher("/jsp/join/landing.jsp").forward(request, response);
			}else{
				String backNews = "�������������";
				fail(request, response, backNews,login);
			}
		}catch (SQLException e){
			String backNews = "��¼ʧ��: "+e;
			fail(request, response, backNews,login);
		}finally{
			DbClose.allClose(pstmt, rs, conn);
		}
		
		
	}
	
	/**
	 * �����û�cookies��Ϣ
	 * @param request
	 * @param response
	 * @param username
	 * @param userpass
	 */
	public void handleCookies(HttpServletRequest request,HttpServletResponse response, 
			String name,String pass,String isCookie)throws ServletException, IOException
	{
		if ("isCookie".equals(isCookie))//�û�ѡ���˼�ס����
		{
			String username = URLEncoder.encode(name,"UTF-8");//���룬���cookie�޷������ַ���������
			String userpass = URLEncoder.encode(pass,"UTF-8");
			System.out.println("username save in cookie: "+username);
			Cookie nameCookie = new Cookie("username",username );//�������½ʱ��name��Ӧ�ļ�ֵ��
			Cookie passCookie = new Cookie("userpass",userpass );
			
			nameCookie.setPath("/");//���õ�cookie�Ĵ洢·������Ҫ����Ȼȡ����ֵ
			passCookie.setPath("/");
			nameCookie.setMaxAge(60*60*24*10); //������������ʮ�� ��λ��
			passCookie.setMaxAge(60*60*24*10);
			response.addCookie(nameCookie); //������Ϣ
			response.addCookie(passCookie); 
		}else 
			{
			//�û�δѡ���ס���룬ɾ��������п��ܴ��ڵ�cookie
				Cookie[] cookies = null;
				cookies = request.getCookies();
				if (cookies!=null&&cookies.length>0)
				{
					for (Cookie c : cookies)
					{
						if ("username".equals(c.getName())||"userpass".equals(c.getName()))
						{
							c.setMaxAge(0);//����cookieʧЧ
							c.setPath("/");//�������---���������������tomcat�����е�app������ʹ�ø�cookie
							response.addCookie(c);
						}
					}
				}
			}
	}
	
	/**
	 * ��½�ɹ��������û���Ϣ
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
			loginBean = (Login) session.getAttribute("loginBean");//���Ի�ȡloginbean�����������Ϣ
			if(loginBean == null){//���session����û����Ϣ���ʹ��������Ϣ��session
				loginBean = new Login();//��loginbean����һ���µ�login���� ���������⣬�ⲻ��һ���µ�login��������Ķ���Ӧ���Ǹճ�ʼ����
				session.setAttribute("loginBean", loginBean);//ע��jsp��ȡʱ��Ҫ�õ���name����������
				session.setMaxInactiveInterval(60*15);//15���ӵĴ���� ��λ����
				loginBean = (Login) session.getAttribute("loginBean");
				String name = loginBean.getUsername();
				System.out.println(name+" :gao handlerlogin name");
				if (username.equals(name))
				{
					loginBean.setBackNews("���ѵ�½�������ٴε�¼");
					loginBean.setUsername(username);
				}else{
					loginBean.setBackNews("��ӭ "+username+" �뾡�鹺���");
					loginBean.setUsername(username);
				}
			}
		} catch (Exception e){
			String backNews = "��¼ʧ��"+e;
			fail(request, response, backNews,login);
		}
	
	}
	
	/**
	 * ��½ʧ��
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
