package htos.coresys.entity;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * UserToRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ht_right_user_role")
public class UserToRole  extends BaseEntity {

	// Fields

	private static final long serialVersionUID = 1L;
	private User user;
	private Role role;
	private String roleType;

	// Constructors

	/** default constructor */
	public UserToRole() {
	}

	//@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne
	@JoinColumn(name = "user_id")
	@NotFound(action=NotFoundAction.IGNORE)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	//@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne
	@JoinColumn(name = "role_id")
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(name = "role_type", length = 1)
	public String getRoleType() {
		return this.roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

}