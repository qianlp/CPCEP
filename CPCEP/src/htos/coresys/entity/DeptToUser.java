package htos.coresys.entity;

// default package

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * DeptToUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ht_right_dept_user")
public class DeptToUser extends BaseEntity {

	// Fields

	private static final long serialVersionUID = 1L;
	private Dept dept;
	private User user;
	private String deptRole;
	private String isParttimeDept;

	/** default constructor */
	public DeptToUser() {
	}

	// @ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "dept_id")
	public Dept getDept() {
		return this.dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	// @ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "dept_role", length = 1)
	public String getDeptRole() {
		return this.deptRole;
	}

	public void setDeptRole(String deptRole) {
		this.deptRole = deptRole;
	}

	@Column(name = "is_parttime_dept", length = 1)
	public String getIsParttimeDept() {
		return this.isParttimeDept;
	}

	public void setIsParttimeDept(String isParttimeDept) {
		this.isParttimeDept = isParttimeDept;
	}

}