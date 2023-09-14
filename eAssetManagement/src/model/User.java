package model;

public class User {
	private int uid;
	private String uName;
	private String uRole;
	private long uTelephone;
	private String uEmail;
	private String username;
	private String uPassword;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuRole() {
		return uRole;
	}
	public void setuRole(String uRole) {
		this.uRole = uRole;
	}
	public long getuTelephone() {
		return uTelephone;
	}
	public void setuTelephone(long uTelephone) {
		this.uTelephone = uTelephone;
	}
	public String getuEmail() {
		return uEmail;
	}
	public void setuEmail(String uEmail) {
		this.uEmail = uEmail;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getuPassword() {
		return uPassword;
	}
	public void setuPassword(String uPassword) {
		this.uPassword = uPassword;
	}
	
	public User() {
		super();
	}
	
	public User(int uid, String uName, String uRole, long uTelephone, String uEmail, String username,
			String uPassword) {
		super();
		this.uid = uid;
		this.uName = uName;
		this.uRole = uRole;
		this.uTelephone = uTelephone;
		this.uEmail = uEmail;
		this.username = username;
		this.uPassword = uPassword;
	}
	
	
	

}
