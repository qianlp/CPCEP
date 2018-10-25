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
 * Dept entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ht_right_dept")
public class Dept extends BaseEntity {

	// Fields

	private static final long serialVersionUID = 1L;
	private String deptName;
	private String sortDeptName;
	private String daiziCn;
	private String daiziEn;
	private Integer deptNo;
	private Integer nodeLevel;
	private String deptFullName;
	private String deptPid;
	private String deptUserId;//部门负责人
	private Set<DeptToUser> deptToUsers = new HashSet<DeptToUser>(0);

	// Constructors

	/** default constructor */
	public Dept() {
	}

	/** minimal constructor */
	public Dept(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "dept_name", nullable = false, length = 64)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "sort_dept_name", length = 64)
	public String getSortDeptName() {
		return this.sortDeptName;
	}

	public void setSortDeptName(String sortDeptName) {
		this.sortDeptName = sortDeptName;
	}

	@Column(name = "daizi_CN", length = 64)
	public String getDaiziCn() {
		return this.daiziCn;
	}

	public void setDaiziCn(String daiziCn) {
		this.daiziCn = daiziCn;
	}

	@Column(name = "daizi_EN", length = 64)
	public String getDaiziEn() {
		return this.daiziEn;
	}

	public void setDaiziEn(String daiziEn) {
		this.daiziEn = daiziEn;
	}

	@Column(name = "dept_no")
	public Integer getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(Integer deptNo) {
		this.deptNo = deptNo;
	}

	@Column(name = "node_level")
	public Integer getNodeLevel() {
		return this.nodeLevel;
	}

	public void setNodeLevel(Integer nodeLevel) {
		this.nodeLevel = nodeLevel;
	}
	
	@Column(name = "dept_full_name", length = 64)
	public String getDeptFullName() {
		return this.deptFullName;
	}

	public void setDeptFullName(String deptFullName) {
		this.deptFullName = deptFullName;
	}
	
	@Column(name = "dept_pid", length = 64)
	public String getDeptPid() {
		return this.deptPid;
	}

	public void setDeptPid(String deptPid) {
		this.deptPid = deptPid;
	}

	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	// "dept")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "dept")
	public Set<DeptToUser> getDeptToUsers() {
		return this.deptToUsers;
	}

	public void setDeptToUsers(Set<DeptToUser> deptToUsers) {
		this.deptToUsers = deptToUsers;
	}

	@Column(name = "deptUserId", length = 64)
	public String getDeptUserId() {
		return deptUserId;
	}

	public void setDeptUserId(String deptUserId) {
		this.deptUserId = deptUserId;
	}
	
}