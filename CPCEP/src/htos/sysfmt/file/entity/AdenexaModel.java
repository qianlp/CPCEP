package htos.sysfmt.file.entity;

import java.io.Serializable;
import java.util.Date;

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
 * 附件表
 * @author zcl
 */
@Entity
@Table(name = "ht_adenexa_info")
public class AdenexaModel implements Serializable {
	private static final long serialVersionUID = 1792319386920175541L;
	
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "uuid", unique = true, nullable = false, length = 64)
	private String uuid;
	
	@Column(name="filename",length=255)
	private String filename;//文件名
	
	@Column(name="createBy",length=64)
	private String createBy;//创建人
	
	@Temporal(TemporalType.DATE)
	@Column(name="createDate")
	private Date createDate;//创建日期
	
	@Column(name="remark",length=1000)
	private String remark;//备注
	
	@Column(name="pid",length=64)
	private String pid;//关联补充更新表
	
	@Column(name="filesize",length=20)
	private String filesize;//文件大小
	
	@Column(name="filenameNew",length=255)
	private String filenameNew;//新生成的文件名
	
	@Column(name="filepath",length=1000)
	private String filepath;//文件路径
	
	@Column(name="prjID",length=64)
	private String prjID;//项目ID
	
	@Column(name="parentDocId",length=64)
	private String parentDocId;//父文档ID
	
	@Column(name="area",length=20)
	private String area;//文档上传区域

	@Column(name="catalogId")
	private String catalogId;//文档目录ID
	
	@Column(name="menuId")
	private String menuId;//文档目录ID
	
	private String cuNo;
	private String version;//归档版本号
	private String status;
	private String address;
	
	private String orgId;//可查看人员ID
	private String wfStatus;//文件审核状态
	
	private String curDocId;//单据关联附件id
	
	@Column(name="lookRole")
	private String lookRole;//可查看角色
	
	@Column(name="lookPerson")
	private String lookPerson;//可查看人员

	@Column(name="lookRolesId")
	private String lookRolesId;//可查看角色ID
	
	@Column(name="defaultRoleId")
	private String defaultRoleId;//默认查看角色ID
	
	@Column(name="createDeptId")
	private String createDeptId;//上传部门ID
	
	@Transient
	private String type;//1借阅
	
	public String getCuNo() {
		return cuNo;
	}

	public void setCuNo(String cuNo) {
		this.cuNo = cuNo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Transient
	private String className;//使用的该表的类名

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public String getFilenameNew() {
		return filenameNew;
	}

	public void setFilenameNew(String filenameNew) {
		this.filenameNew = filenameNew;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	/**
	 * @return the prjID
	 */
	public String getPrjID() {
		return prjID;
	}

	/**
	 * @param prjID the prjID to set
	 */
	public void setPrjID(String prjID) {
		this.prjID = prjID;
	}

	/**
	 * @return the parentDocId
	 */
	public String getParentDocId() {
		return parentDocId;
	}

	/**
	 * @param parentDocId the parentDocId to set
	 */
	public void setParentDocId(String parentDocId) {
		this.parentDocId = parentDocId;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}
	
	
	/**
	 * @return the catalogId
	 */
	public String getCatalogId() {
		return catalogId;
	}

	/**
	 * @param catalogId the catalogId to set
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	@Transient
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}

	public String getCurDocId() {
		return curDocId;
	}

	public void setCurDocId(String curDocId) {
		this.curDocId = curDocId;
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

	@Transient
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDefaultRoleId() {
		return defaultRoleId;
	}

	public void setDefaultRoleId(String defaultRoleId) {
		this.defaultRoleId = defaultRoleId;
	}

	public String getCreateDeptId() {
		return createDeptId;
	}

	public void setCreateDeptId(String createDeptId) {
		this.createDeptId = createDeptId;
	}
	
	
}
