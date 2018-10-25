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
 * 新文件目录维护
 * @author ran
 *
 */
@Entity
@Table(name = "ht_newfile_directory")
public class FileDirectorynewModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="catalogNo",length=20)
	private String catalogNo;  //目录编号
	
	@Column(name="catalogName",length=200)
	private String catalogName;  //目录名称
	
	@Column(name="parentTaskUID",length=20)
	private String parentTaskUID; //父目录编号
	
	@Column(name="docLink",length=1000)
	private String docLink;//文档要求
	
	private String userId;//用户ID
	
	@Column(name="UID",length=20)
	private String UID;//树菜单id
	
	private String uuid;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	private String remark;
	private String delFlag;
	
	private String prjID;
	
	@Column(name="lookRole",length=1000)
	private String lookRole;//可查看角色
	
	@Column(name="lookPerson",length=1000)
	private String lookPerson;//可查看人员
	
	@Column(name="relationMenuID")
	private String relationMenuID;//关联菜单ID
	
	@Column(name="lookRolesId")
	private String lookRolesId;//可查看角色ID
	
	@Column(name="lookNamesId")
	private String lookNamesId;//可查看人员ID
	
	@Column(name="relationMenu",length=1000)
	private String relationMenu;//关联菜单
	
	@Column(name="curDocId")
	private String curDocId;

	private List<FileDirectorynewModel> children;
	
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


	public String getDocLink() {
		return docLink;
	}

	public void setDocLink(String docLink) {
		this.docLink = docLink;
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
	
	public String getPrjID() {
		return prjID;
	}

	public void setPrjID(String prjID) {
		this.prjID = prjID;
	}

	@Transient
	public List<FileDirectorynewModel> getChildren() {
		if (children == null) {
			children = new ArrayList<FileDirectorynewModel>();
	     }
	    return this.children;
	}

	public void setChildren(List<FileDirectorynewModel> children) {
		this.children = children;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getRelationMenuID() {
		return relationMenuID;
	}

	public void setRelationMenuID(String relationMenuID) {
		this.relationMenuID = relationMenuID;
	}

	public String getLookRolesId() {
		return lookRolesId;
	}

	public void setLookRolesId(String lookRolesId) {
		this.lookRolesId = lookRolesId;
	}

	public String getLookNamesId() {
		return lookNamesId;
	}

	public void setLookNamesId(String lookNamesId) {
		this.lookNamesId = lookNamesId;
	}

	public String getRelationMenu() {
		return relationMenu;
	}

	public void setRelationMenu(String relationMenu) {
		this.relationMenu = relationMenu;
	}

	public String getCurDocId() {
		return curDocId;
	}

	public void setCurDocId(String curDocId) {
		this.curDocId = curDocId;
	}
	
}