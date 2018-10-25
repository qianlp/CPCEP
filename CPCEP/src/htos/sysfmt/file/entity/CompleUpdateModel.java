package htos.sysfmt.file.entity;

import htos.coresys.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 项目文件补充更新
 * @author zcl
 */
@Entity
@Table(name = "ht_comple_update_info")
public class CompleUpdateModel extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	@Column(name="pid",nullable = false,length=255)
	private String pid;//关联项目立项表数据
	
	@Column(name="profilename",nullable = false,length=255)
	private String profilename;
	
	@Column(name="profilesize",nullable = false,length=255)
	private String profilesize;//项目文件大小

	@Column(name="projectNo",nullable = false,length=255)
	private String projectNo;//项目编号
	
	@Column(name="projectName",nullable = false,length=255)
	private String projectName;//项目名称
	
	@Column(name="Status",nullable = false,length=255)
	private String Status;//状态
	
	@Column(name="profilenameNew",nullable = false,length=255)
	private String profilenameNew;//项目文件文档编号（新名称）
	
	@Column(name="profilepath",nullable = false,length=255)
	private String profilepath;//项目文件路径
	
	@Column(name="proversion",nullable = false,length=255)
	private String proversion;//项目文件版本号
	
	@Column(name="warnBy",nullable = false,length=255)
	private String warnBy;//提醒人
	
	@Column(name="fid",length=64)
	private String fid;//文件目录id
	
	@Column(name="parentTaskUID",length=64)
	private String parentTaskUID;//父code
	
	@Column(name="comeTo",length=20)
	private String comeTo;//跟踪标记
	
	@Column(name="cuNo",length=64)
	private String cuNo;//纸质文档编号
	
	@Column(name="warnContent",length=1000)
	private String warnContent;//提醒内容
	
	private String catalogName;//文件目录
	

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getProfilename() {
		return profilename;
	}

	public void setProfilename(String profilename) {
		this.profilename = profilename;
	}

	public String getProfilesize() {
		return profilesize;
	}

	public void setProfilesize(String profilesize) {
		this.profilesize = profilesize;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getProfilenameNew() {
		return profilenameNew;
	}

	public void setProfilenameNew(String profilenameNew) {
		this.profilenameNew = profilenameNew;
	}

	public String getProfilepath() {
		return profilepath;
	}

	public void setProfilepath(String profilepath) {
		this.profilepath = profilepath;
	}

	public String getProversion() {
		return proversion;
	}

	public void setProversion(String proversion) {
		this.proversion = proversion;
	}

	public String getWarnBy() {
		return warnBy;
	}

	public void setWarnBy(String warnBy) {
		this.warnBy = warnBy;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getParentTaskUID() {
		return parentTaskUID;
	}

	public void setParentTaskUID(String parentTaskUID) {
		this.parentTaskUID = parentTaskUID;
	}

	public String getComeTo() {
		return comeTo;
	}

	public void setComeTo(String comeTo) {
		this.comeTo = comeTo;
	}

	public String getCuNo() {
		return cuNo;
	}

	public void setCuNo(String cuNo) {
		this.cuNo = cuNo;
	}

	public String getWarnContent() {
		return warnContent;
	}

	public void setWarnContent(String warnContent) {
		this.warnContent = warnContent;
	}

	@Transient
	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	
	
}
