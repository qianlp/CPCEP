package htos.business.dto;

public class InviteSupplier {
	private String uuid;
	private String firstTotalPrice;
	private String secondTotalPrice;
	private String thirdTotalPrice;
	private String supplierUuid;
	private String supplierName;
	private String contactAddress;
	private String corporations;
	private String contact;
	private String mobile;
	private int rank;


	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public InviteSupplier() {
	}

	public InviteSupplier(String uuid, String firstTotalPrice, String secondTotalPrice, String thirdTotalPrice, String supplierUuid, String supplierName, String contactAddress, String corporations, String contact, String mobile) {
		this.uuid = uuid;
		this.firstTotalPrice = firstTotalPrice;
		this.secondTotalPrice = secondTotalPrice;
		this.thirdTotalPrice = thirdTotalPrice;
		this.supplierUuid = supplierUuid;
		this.supplierName = supplierName;
		this.contactAddress = contactAddress;
		this.corporations = corporations;
		this.contact = contact;
		this.mobile = mobile;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFirstTotalPrice() {
		return firstTotalPrice;
	}

	public void setFirstTotalPrice(String firstTotalPrice) {
		this.firstTotalPrice = firstTotalPrice;
	}

	public String getSecondTotalPrice() {
		return secondTotalPrice;
	}

	public void setSecondTotalPrice(String secondTotalPrice) {
		this.secondTotalPrice = secondTotalPrice;
	}

	public String getThirdTotalPrice() {
		return thirdTotalPrice;
	}

	public void setThirdTotalPrice(String thirdTotalPrice) {
		this.thirdTotalPrice = thirdTotalPrice;
	}

	public String getSupplierUuid() {
		return supplierUuid;
	}

	public void setSupplierUuid(String supplierUuid) {
		this.supplierUuid = supplierUuid;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getCorporations() {
		return corporations;
	}

	public void setCorporations(String corporations) {
		this.corporations = corporations;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
