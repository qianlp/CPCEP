package htos.business.dto;

public class SupplierBidInfo {
	private String supplierBidUuid;
	private String paymentMethod;
	private String deliveryTime;
	private String supplierId;
	private String supplierName;
	private String supplierContacts;
	private String supplierPhone;
	private String supplierFax;
	private String supplierMobile;
	private String supplierEmail;

	public SupplierBidInfo() {
	}

	public SupplierBidInfo(String supplierBidUuid, String paymentMethod, String deliveryTime, String supplierId, String supplierName, String supplierContacts, String supplierPhone, String supplierFax, String supplierMobile, String supplierEmail) {
		this.supplierBidUuid = supplierBidUuid;
		this.paymentMethod = paymentMethod;
		this.deliveryTime = deliveryTime;
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.supplierContacts = supplierContacts;
		this.supplierPhone = supplierPhone;
		this.supplierFax = supplierFax;
		this.supplierMobile = supplierMobile;
		this.supplierEmail = supplierEmail;
	}

	public String getSupplierBidUuid() {
		return supplierBidUuid;
	}

	public void setSupplierBidUuid(String supplierBidUuid) {
		this.supplierBidUuid = supplierBidUuid;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierContacts() {
		return supplierContacts;
	}

	public void setSupplierContacts(String supplierContacts) {
		this.supplierContacts = supplierContacts;
	}

	public String getSupplierPhone() {
		return supplierPhone;
	}

	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
	}

	public String getSupplierFax() {
		return supplierFax;
	}

	public void setSupplierFax(String supplierFax) {
		this.supplierFax = supplierFax;
	}

	public String getSupplierMobile() {
		return supplierMobile;
	}

	public void setSupplierMobile(String supplierMobile) {
		this.supplierMobile = supplierMobile;
	}

	public String getSupplierEmail() {
		return supplierEmail;
	}

	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}
}
