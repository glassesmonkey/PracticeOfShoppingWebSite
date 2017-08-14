package shop.db;
//gaochao
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConn
{
	public static Connection getConn()
	{
		Connection conn = null;
		
		String user 	= "root";
		String passwd	= "admin";
		String url = "jdbc:mysql://localhost:3306/shop";
		
		//�Ѽ�������
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(url,user,passwd);
			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			
		return conn;
	}
	
}
