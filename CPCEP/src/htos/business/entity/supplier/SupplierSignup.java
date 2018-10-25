package htos.business.entity.supplier;

import htos.business.entity.bulletin.BiddingBulletin;
import htos.business.utils.BusinessConstants;
import htos.coresys.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author qinj
 * @date 2018-03-07 22:06
 **/
@Entity
@Table(name = "bs_supplier_signup")
public class SupplierSignup extends BaseEntity {

	private SupplierExt supplier;
	private BiddingBulletin bidding;
	private String contact;
	private String position;
	private String phone;
	private String mobile;
	private String email;
	private Boolean canWrite;
	private Boolean canCheck;

	@Column(name = "contact")
	public String getContact() {
		if(StringUtils.isBlank(contact))
			return "";
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "position")
	public String getPosition() {
		if(StringUtils.isBlank(position))
			return "";
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "phone")
	public String getPhone() {
		if(StringUtils.isBlank(phone))
			return "";
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "mobile")
	public String getMobile() {
		if(StringUtils.isBlank(mobile))
			return "";
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "email")
	public String getEmail() {
		if(StringUtils.isBlank(email))
			return "";
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "supplier")
	public SupplierExt getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierExt supplier) {
		this.supplier = supplier;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "bidding")
	public BiddingBulletin getBidding() {
		return bidding;
	}

	public void setBidding(BiddingBulletin bidding) {
		this.bidding = bidding;
	}

	@Transient
	public boolean getCanWrite() {
		if(canWrite != null)
			return canWrite;
		if(getWfStatus() == null)
			return true;
		Short status = Short.parseShort(getWfStatus());
		if(status == BusinessConstants.SUPPLIER_TRADER_DRAFT || status == BusinessConstants.SUPPLIER_TRADER_UNSIGNUP)
			return true;
		return false;
	}

	public void setCanWrite(boolean canWrite) {
		this.canWrite = canWrite;
	}

	public void setCanWrite(Boolean canWrite) {
		this.canWrite = canWrite;
	}

	@Transient
	public Boolean getCanCheck() {
		return canCheck;
	}

	public void setCanCheck(Boolean canCheck) {
		this.canCheck = canCheck;
	}
}
