package htos.business.entity.supplier;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author qinj
 * @date 2018-02-05 23:02
 **/
@Entity
@Table(name = "bs_supplier_billing")
public class SupplierBilling {

	protected String uuid;

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

	private String billingName;
	private String bankAccount;
	private String bankName;
	private String taxpayerId;
	private String address;
	private String phone;
	private SupplierExt supplier;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "supplier")
	public SupplierExt getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierExt supplier) {
		this.supplier = supplier;
	}

	@Basic
	@Column(name = "BILLING_NAME")
	public String getBillingName() {
		return billingName;
	}

	public void setBillingName(String billingName) {
		this.billingName = billingName;
	}

	@Basic
	@Column(name = "BANK_ACCOUNT")
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Basic
	@Column(name = "BANK_NAME")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Basic
	@Column(name = "TAXPAYER_ID")
	public String getTaxpayerId() {
		return taxpayerId;
	}

	public void setTaxpayerId(String taxpayerId) {
		this.taxpayerId = taxpayerId;
	}

	@Basic
	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Basic
	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
