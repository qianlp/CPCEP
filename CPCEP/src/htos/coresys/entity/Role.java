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
 * Role entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ht_right_role")
public class Role extends BaseEntity {

	// Fields

	private static final long serialVersionUID = 1L;
	private String roleName;
	private String roleDescribe;
	private String listCategory;
	private String oldListCategory;
	private String roleDepts;
	private String oldRoleDepts;
	private String roleMenus;
	private String oldRoleMenus;
	private String roleNo;
	private Set<UserToRole> userToRoles = new HashSet<UserToRole>(0);
	private Integer isHide;
	private String modeName;
	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(String roleName) {
		this.roleName = roleName;
	}


	@Column(name = "role_name", nullable = false, length = 64)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "role_describe", length = 256)
	public String getRoleDescribe() {
		return this.roleDescribe;
	}

	public void setRoleDescribe(String roleDescribe) {
		this.roleDescribe = roleDescribe;
	}

	@Column(name = "role_no", length = 32)
	public String getRoleNo() {
		return this.roleNo;
	}

	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}

	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	// "role")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
	public Set<UserToRole> getUserToRoles() {
		return this.userToRoles;
	}

	public void setUserToRoles(Set<UserToRole> userToRoles) {
		if(userToRoles!=null){
			this.userToRoles = userToRoles;
		}
	}

	/**
	 * @return the isHide
	 */
	@Column(name = "isHide")
	public Integer getIsHide() {
		return isHide;
	}

	/**
	 * @param isHide the isHide to set
	 */
	public void setIsHide(Integer isHide) {
		this.isHide = isHide;
	}

	/**
	 * @return the menuId
	 */
	@Column(name = "modeName")
	public String getModeName() {
		return modeName;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	@Column(name = "listCategory")
	public String getListCategory() {
		return listCategory;
	}

	public void setListCategory(String listCategory) {
		this.listCategory = listCategory;
	}

	@Column(name = "roleDepts")
	public String getRoleDepts() {
		return roleDepts;
	}

	public void setRoleDepts(String roleDepts) {
		this.roleDepts = roleDepts;
	}

	@Column(name = "oldRoleDepts")
	public String getOldRoleDepts() {
		return oldRoleDepts;
	}

	public void setOldRoleDepts(String oldRoleDepts) {
		this.oldRoleDepts = oldRoleDepts;
	}
	
	@Column(name = "oldListCategory")
	public String getOldListCategory() {
		return oldListCategory;
	}

	public void setOldListCategory(String oldListCategory) {
		this.oldListCategory = oldListCategory;
	}

	public String getRoleMenus() {
		return roleMenus;
	}

	public void setRoleMenus(String roleMenus) {
		this.roleMenus = roleMenus;
	}

	public String getOldRoleMenus() {
		return oldRoleMenus;
	}

	public void setOldRoleMenus(String oldRoleMenus) {
		this.oldRoleMenus = oldRoleMenus;
	}

	
}