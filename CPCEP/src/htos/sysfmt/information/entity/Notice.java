package htos.sysfmt.information.entity;

// default package

import htos.coresys.entity.BaseEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * Notice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ht_information_notice")
public class Notice extends BaseEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;// 标题
	private String accordingScopeOf;// 发送范围
	private String scopeOfUser;// 范围人员名称
	private String dept;// 所属部门
	private String isNoticePicture;// 是否是公告图片
	private String pictureAddress;// 图片地址
	private String checkUser;// 审批人
	private String state;// 流程状态
	private String noticeMessage;// 公告信息
	private String newImgAddress;// 图片链接地址
	private String readId;
	private String catalogId;//
	private String curDocId;// 文档ID

	// Constructors

	/** default constructor */
	public Notice() {
	}



	@Column(name = "name", length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	@Column(name = "according_scope_of", length = 200)
	public String getAccordingScopeOf() {
		return this.accordingScopeOf;
	}

	public void setAccordingScopeOf(String accordingScopeOf) {
		this.accordingScopeOf = accordingScopeOf;
	}

	@Column(name = "scope_of_user", length = 65535)
	public String getScopeOfUser() {
		return this.scopeOfUser;
	}

	public void setScopeOfUser(String scopeOfUser) {
		this.scopeOfUser = scopeOfUser;
	}

	@Column(name = "dept", length = 64)
	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Column(name = "is_notice_picture", length = 1)
	public String getIsNoticePicture() {
		return this.isNoticePicture;
	}

	public void setIsNoticePicture(String isNoticePicture) {
		this.isNoticePicture = isNoticePicture;
	}

	@Column(name = "picture_address")
	public String getPictureAddress() {
		return this.pictureAddress;
	}

	public void setPictureAddress(String pictureAddress) {
		this.pictureAddress = pictureAddress;
	}

	

	@Column(name = "check_user", length = 64)
	public String getCheckUser() {
		return this.checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	@Column(name = "state", length = 1)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "notice_message")
	public String getNoticeMessage() {
		return this.noticeMessage;
	}

	public void setNoticeMessage(String noticeMessage) {
		this.noticeMessage = noticeMessage;
	}

	@Column(name = "new_img_address", length = 65535)
	public String getNewImgAddress() {
		return this.newImgAddress;
	}

	public void setNewImgAddress(String newImgAddress) {
		this.newImgAddress = newImgAddress;
	}

	
	@Column(name = "catalog_id", length = 64)
	public String getCatalogId() {
		return this.catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	@Column(name = "curDocId", length = 64)
	public String getCurDocId() {
		return this.curDocId;
	}

	public void setCurDocId(String curDocId) {
		this.curDocId = curDocId;
	}

	
	public String getReadId() {
		return readId;
	}

	public void setReadId(String readId) {
		this.readId = readId;
	}

}