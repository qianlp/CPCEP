package htos.coresys.entity;

// default package

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ht_right_user")
public class User extends BaseEntity {

	// Fields

	private static final long serialVersionUID = 1L;
	private String userName;
	private String userPerEname;
	private String userNo;
	private String userEthnic;
	private String userSex;
	private String userTelephone;
	private String userMail;
	private String userOtherTel;
	private String userFax;
	/**
	 * 用户编号，排序号
	 */
	private int userPostion;
	private String userPassword;
	private String userDeptId;
	/**
	 * 0 超级管理员；2 供应商；null 普通账号
	 */
	private Integer userType;
	
	private String posId;
	private String posNo;
	private String posName;
	// private OrgMap<User> orgMap;
	private Set<UserToRole> userToRoles = new HashSet<UserToRole>(0);
	private Set<DeptToUser> deptToUsers = new HashSet<DeptToUser>(0);

	// Constructors

	/** default constructor */
	
	private String content;//审核反馈的信息
	public User() {
	}

	/** minimal constructor */
	public User(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_name", nullable = false, length = 32)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_perEName", length = 64)
	public String getUserPerEname() {
		return this.userPerEname;
	}

	public void setUserPerEname(String userPerEname) {
		this.userPerEname = userPerEname;
	}

	@Column(name = "user_no", length = 32)
	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	@Column(name = "user_ethnic", length = 64)
	public String getUserEthnic() {
		return this.userEthnic;
	}

	public void setUserEthnic(String userEthnic) {
		this.userEthnic = userEthnic;
	}

	@Column(name = "user_sex", length = 64)
	public String getUserSex() {
		return this.userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	@Column(name = "user_telephone", length = 64)
	public String getUserTelephone() {
		return this.userTelephone;
	}

	public void setUserTelephone(String userTelephone) {
		this.userTelephone = userTelephone;
	}

	@Column(name = "user_mail", length = 64)
	public String getUserMail() {
		return this.userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	@Column(name = "user_other_tel", length = 64)
	public String getUserOtherTel() {
		return this.userOtherTel;
	}

	public void setUserOtherTel(String userOtherTel) {
		this.userOtherTel = userOtherTel;
	}

	@Column(name = "user_fax", length = 64)
	public String getUserFax() {
		return this.userFax;
	}

	public void setUserFax(String userFax) {
		this.userFax = userFax;
	}

	@Column(name = "user_postion")
	public int getUserPostion() {
		return this.userPostion;
	}

	public void setUserPostion(int userPostion) {
		this.userPostion = userPostion;
	}

	@Column(name = "user_password", length = 128)
	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Column(name = "user_dept_id", length = 64)
	public String getUserDeptId() {
		return this.userDeptId;
	}

	public void setUserDeptId(String userDeptId) {
		this.userDeptId = userDeptId;
	}
	
	
	@Column(name = "userType")
	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	// "user")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	public Set<UserToRole> getUserToRoles() {
		return this.userToRoles;
	}

	public void setUserToRoles(Set<UserToRole> userToRoles) {
		if(userToRoles!=null){
			this.userToRoles = userToRoles;
		}
	}

	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	// "user")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	public Set<DeptToUser> getDeptToUsers() {
		return this.deptToUsers;
	}

	public void setDeptToUsers(Set<DeptToUser> deptToUsers) {
		if(deptToUsers!=null){
			this.deptToUsers = deptToUsers;
		}
	}
	/*
	 * @OneToOne(cascade = CascadeType.ALL)
	 * 
	 * @JoinColumn(name = "orgMapId") public OrgMap<User> getOrgMap() { return
	 * orgMap; }
	 * 
	 * public void setOrgMap(OrgMap<User> orgMap) { this.orgMap = orgMap; }
	 */

	/**
	 * @return the posId
	 */
	public String getPosId() {
		return posId;
	}

	/**
	 * @param posId the posId to set
	 */
	public void setPosId(String posId) {
		this.posId = posId;
	}

	/**
	 * @return the posNo
	 */
	public String getPosNo() {
		return posNo;
	}

	/**
	 * @param posNo the posNo to set
	 */
	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}

	/**
	 * @return the posName
	 */
	public String getPosName() {
		return posName;
	}

	/**
	 * @param posName the posName to set
	 */
	public void setPosName(String posName) {
		this.posName = posName;
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}