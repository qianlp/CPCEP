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
 * News entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ht_information_news")
public class News extends BaseEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String accordingScopeOf;
	private String scopeOfUser;
	private String readId;
	private String dept;
	private String isNewsPicture;
	private String pictureAddress;
	private String checkUser;
	private String state;
	private String newMessage;
	private String newImgAddress;
	private String catalogId;
	private String curDocId;
	
	// Constructors

	/** default constructor */
	public News() {
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

	@Column(name = "is_news_picture", length = 1)
	public String getIsNewsPicture() {
		return this.isNewsPicture;
	}

	public void setIsNewsPicture(String isNewsPicture) {
		this.isNewsPicture = isNewsPicture;
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

	@Column(name = "new_message")
	public String getNewMessage() {
		return this.newMessage;
	}

	public void setNewMessage(String newMessage) {
		this.newMessage = newMessage;
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