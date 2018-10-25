package htos.sysfmt.file.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 文件目录维护
 * @author ran
 *
 */
@Entity
@Table(name = "ht_file_directory")
public class FileDirectory implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="catalogNo",length=20)
	private String catalogNo;  //目录编号
	
	@Column(name="catalogName",length=200)
	private String catalogName;  //目录名称
	
	@Column(name="parentTaskUID",length=20)
	private String parentTaskUID; //父目录编号
	
	@Column(name="catalogType",length=1000)
	private String catalogType;//关联项目类型
	
	@Column(name="docLink",length=1000)
	private String docLink;//文档要求
	
	@Column(name="relationMenu",length=1000)
	private String relationMenu;//关联菜单
	
	@Column(name="relationKTMenu",length=1000)
	private String relationKTMenu;//关联框图菜单
	
	@Column(name="lookDept",length=1000)
	private String lookDept;//可查看部门
	
	@Column(name="lookRole",length=1000)
	private String lookRole;//可查看角色
	
	@Column(name="lookPerson",length=1000)
	private String lookPerson;//可查看人员
	
	@Column(name="relationMenuID")
	private String relationMenuID;//可查看菜单
	
	@Column(name="lookRolesId")
	private String lookRolesId;//可查看角色id
	
	@Column(name="lookNamesId")
	private String lookNamesId;//可查看人员id
	
	@Column(name="UID",length=20)
	private String UID;//树菜单id
	
	private String uuid;
	private String createBy;
	private Date createDate;
	private String wfStatus;
	private String curUser;
	private String updateBy;
	private Date updateDate;
	private String remark;
	private String delFlag;

	private List<FileDirectory> children;
	
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "uuid", unique = true, nullable = false, length = 64)
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "create_by", length = 64)
	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 10)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "update_by", length = 64)
	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", length = 10)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "del_flag", length = 1)
	public String getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	@Column(name = "wfStatus", length = 1)
	public String getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}

	public String getCurUser() {
		return curUser;
	}

	public void setCurUser(String curUser) {
		this.curUser = curUser;
	}
	

	public String getCatalogNo() {
		return catalogNo;
	}

	public void setCatalogNo(String catalogNo) {
		this.catalogNo = catalogNo;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getCatalogType() {
		return catalogType;
	}

	public void setCatalogType(String catalogType) {
		this.catalogType = catalogType;
	}

	public String getDocLink() {
		return docLink;
	}

	public void setDocLink(String docLink) {
		this.docLink = docLink;
	}

	public String getRelationMenu() {
		return relationMenu;
	}

	public void setRelationMenu(String relationMenu) {
		this.relationMenu = relationMenu;
	}

	public String getRelationKTMenu() {
		return relationKTMenu;
	}

	public void setRelationKTMenu(String relationKTMenu) {
		this.relationKTMenu = relationKTMenu;
	}

	public String getLookDept() {
		return lookDept;
	}

	public void setLookDept(String lookDept) {
		this.lookDept = lookDept;
	}

	public String getLookRole() {
		return lookRole;
	}

	public void setLookRole(String lookRole) {
		this.lookRole = lookRole;
	}

	public String getLookPerson() {
		return lookPerson;
	}

	public void setLookPerson(String lookPerson) {
		this.lookPerson = lookPerson;
	}

	public String getParentTaskUID() {
		return parentTaskUID;
	}

	public void setParentTaskUID(String parentTaskUID) {
		this.parentTaskUID = parentTaskUID;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		this.UID = uID;
	}
	
	/**
	 * @return the relationMenuID
	 */
	public String getRelationMenuID() {
		return relationMenuID;
	}

	/**
	 * @param relationMenuID the relationMenuID to set
	 */
	public void setRelationMenuID(String relationMenuID) {
		this.relationMenuID = relationMenuID;
	}

	/**
	 * @return the lookRolesId
	 */
	public String getLookRolesId() {
		return lookRolesId;
	}

	/**
	 * @param lookRolesId the lookRolesId to set
	 */
	public void setLookRolesId(String lookRolesId) {
		this.lookRolesId = lookRolesId;
	}

	/**
	 * @return the lookNamesId
	 */
	public String getLookNamesId() {
		return lookNamesId;
	}

	/**
	 * @param lookNamesId the lookNamesId to set
	 */
	public void setLookNamesId(String lookNamesId) {
		this.lookNamesId = lookNamesId;
	}

	@Transient
	public List<FileDirectory> getChildren() {
		if (children == null) {
			children = new ArrayList<FileDirectory>();
	     }
	    return this.children;
	}

	public void setChildren(List<FileDirectory> children) {
		this.children = children;
	}
}