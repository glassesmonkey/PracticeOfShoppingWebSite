package shop.entity;

import java.util.LinkedList;

//gaochao
public class Login {
	private static final long serialVersionUID = -69203680249861342L;
	private String username = "";
	private String backNews = "未登录";
	private LinkedList<String> car = null;      //购物车、定单
	
	
	public Login()
	{
		car = new LinkedList<String>();
	}
	
	public LinkedList<String> getCar()
	{
		return car;
	}
	public void setCar(LinkedList<String> car)
	{
		this.car = car;
	}
	public String getUsername()
	{
	    if (username.trim()=="")
        {
            return "userNull";
        }
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getBackNews()
	{
		return backNews;
	}
	public void setBackNews(String backNews)
	{
		this.backNews = backNews;
	}
	public void Initialize(){
		String username = "";
		String backNews = "未登录";
		LinkedList<String> car = null;      //购物车、定单
	}
}
