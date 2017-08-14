package shop.entity;

public class LoginStatus {
	private static final long serialVersionUID = -1465928336863533888L;
	private String backNews = "";
	public void setBackNews(String backNews){
		this.backNews = backNews;
		
	}
	public String getBackNews(){
		return (this.backNews);
	}
	public void Initialize(){
		backNews = "";
	}
}
