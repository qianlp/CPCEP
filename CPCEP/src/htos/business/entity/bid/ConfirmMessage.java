package htos.business.entity.bid;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import htos.coresys.entity.BaseEntity;

@Entity
@Table(name = "bs_supplier_confirm_message")
public class ConfirmMessage extends BaseEntity {
	private String biddingBulletinCode;
	private String biddingBulletinName;
	private String confirmTime;
	private String biddingFileReleaseVersion;
	private String message;
	private String suppilerId;
	
	public ConfirmMessage(String biddingBulletinCode,
			String biddingBulletinName, String confirmTime,
			String biddingFileReleaseVersion, String message, String suppilerId) {
		super();
		this.biddingBulletinCode = biddingBulletinCode;
		this.biddingBulletinName = biddingBulletinName;
		this.confirmTime = confirmTime;
		this.biddingFileReleaseVersion = biddingFileReleaseVersion;
		this.message = message;
		this.suppilerId = suppilerId;
	}

	public ConfirmMessage() {
		super();
	}

	@Column(name = "biddingBulletinCode")
	public String getBiddingBulletinCode() {
		return biddingBulletinCode;
	}

	public void setBiddingBulletinCode(String biddingBulletinCode) {
		this.biddingBulletinCode = biddingBulletinCode;
	}
	@Column(name = "biddingBulletinName")
	public String getBiddingBulletinName() {
		return biddingBulletinName;
	}

	public void setBiddingBulletinName(String biddingBulletinName) {
		this.biddingBulletinName = biddingBulletinName;
	}
	@Column(name = "confirmTime")
	public String getConfirmTime() {
		return confirmTime;
	}
 
	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}
	@Column(name = "biddingFileReleaseVersion")
	public String getBiddingFileReleaseVersion() {
		return biddingFileReleaseVersion;
	}

	public void setBiddingFileReleaseVersion(String biddingFileReleaseVersion) {
		this.biddingFileReleaseVersion = biddingFileReleaseVersion;
	}
	@Column(name = "message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Column(name = "suppilerId")
	public String getSuppilerId() {
		return suppilerId;
	}

	public void setSuppilerId(String suppilerId) {
		this.suppilerId = suppilerId;
	}
   
}
