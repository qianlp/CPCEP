package htos.sysfmt.file.entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

/**
 *项目目录文件权限分配
 */

public class PrjFileDirectoryModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String catalogNo;  //目录编号
	
	private String catalogName;  //目录名称
	
	private String parentTaskUID; //父目录编号
	
	private String UID;//树菜单id
	
	private String uuid;
	
	private String lookRole;//可查看角色
	
	private String lookPerson;//可查看人员
	
	private String lookRolesId;//可查看人员ID
	
	private String lookNamesId;//可查看人员ID
	
	private String size;//文件大小
	
	private String type;//1目录2文件

	private List<PrjFileDirectoryModel> children;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	@Transient
	public List<PrjFileDirectoryModel> getChildren() {
		if (children == null) {
			children = new ArrayList<PrjFileDirectoryModel>();
	     }
	    return this.children;
	}

	public void setChildren(List<PrjFileDirectoryModel> children) {
		this.children = children;
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
		UID = uID;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
}