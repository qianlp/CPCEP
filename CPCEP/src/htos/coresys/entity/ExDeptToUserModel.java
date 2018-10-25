package htos.coresys.entity;
/**
 * 导出组织用户信息
 * DeptToUser entity. @author MyEclipse Persistence Tools
 */
public class ExDeptToUserModel{
	private String userName;//中文名
	private String userPerEname;//英文名
	private String deptFullName;//部门
	private String userSex;;//性别
	private String userMail;//电子邮件
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPerEname() {
		return userPerEname;
	}
	public void setUserPerEname(String userPerEname) {
		this.userPerEname = userPerEname;
	}
	public String getDeptFullName() {
		return deptFullName;
	}
	public void setDeptFullName(String deptFullName) {
		this.deptFullName = deptFullName;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	
}