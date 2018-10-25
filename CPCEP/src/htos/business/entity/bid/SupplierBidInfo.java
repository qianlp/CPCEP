package htos.business.entity.bid;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "v_supplier_bid_info")
public class SupplierBidInfo {
	private String uuid;
	private String remark;
	private String wfStatus;
	private Date updateDate;
	private String createBy;
	private Timestamp createDate;
	private String updateBy;
	private String curUser;
	private String delFlag;
	private String docTypeId;
	private String docBusId;
	private String catalogId;
	private String biddingFileUuid;
	private String paymentMethod;
	private String phone;
	private String contacts;
	private String deliveryTime;
	private String technicalFileUuid;
	private String businessFileUuid;
	private String priceFileUuid;
	private String deviceFirstPrice;
	private String deviceSecondPrice;
	private String deviceThirdPrice;
	private String devicePriceRemark;
	private String techFirstPrice;
	private String techSecondPrice;
	private String techThirdPrice;
	private String techPriceRemark;
	private String transportFirstPrice;
	private String transportSecondPrice;
	private String transportThirdPrice;
	private String transportPriceRemark;
	private String deviceRemark;
	private String spareRemark;
	private String toolRemark;
	private String createUuid;
	private String biddingFileName;
	private String biddingFileVersion;
	private String biddingFileCode;
	private String biddingBulletinUuid;
	private String biddingBulletinName;
	private String biddingBulletinCode;

	@Id
	@Column(name = "uuid")
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "wfStatus")
	public String getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}

	@Column(name = "update_date")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "create_by")
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Column(name = "create_date")
	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "update_by")
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Column(name = "curUser")
	public String getCurUser() {
		return curUser;
	}

	public void setCurUser(String curUser) {
		this.curUser = curUser;
	}

	@Column(name = "del_flag")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name = "docTypeId")
	public String getDocTypeId() {
		return docTypeId;
	}

	public void setDocTypeId(String docTypeId) {
		this.docTypeId = docTypeId;
	}

	@Column(name = "docBusId")
	public String getDocBusId() {
		return docBusId;
	}

	public void setDocBusId(String docBusId) {
		this.docBusId = docBusId;
	}

	@Column(name = "catalogId")
	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	@Column(name = "bidding_file_uuid")
	public String getBiddingFileUuid() {
		return biddingFileUuid;
	}

	public void setBiddingFileUuid(String biddingFileUuid) {
		this.biddingFileUuid = biddingFileUuid;
	}

	@Column(name = "payment_method")
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "contacts")
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@Column(name = "delivery_time")
	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	@Column(name = "technical_file_uuid")
	public String getTechnicalFileUuid() {
		return technicalFileUuid;
	}

	public void setTechnicalFileUuid(String technicalFileUuid) {
		this.technicalFileUuid = technicalFileUuid;
	}

	@Column(name = "business_file_uuid")
	public String getBusinessFileUuid() {
		return businessFileUuid;
	}

	public void setBusinessFileUuid(String businessFileUuid) {
		this.businessFileUuid = businessFileUuid;
	}

	@Column(name = "price_file_uuid")
	public String getPriceFileUuid() {
		return priceFileUuid;
	}

	public void setPriceFileUuid(String priceFileUuid) {
		this.priceFileUuid = priceFileUuid;
	}

	@Column(name = "device_first_price")
	public String getDeviceFirstPrice() {
		return deviceFirstPrice;
	}

	public void setDeviceFirstPrice(String deviceFirstPrice) {
		this.deviceFirstPrice = deviceFirstPrice;
	}

	@Column(name = "device_second_price")
	public String getDeviceSecondPrice() {
		return deviceSecondPrice;
	}

	public void setDeviceSecondPrice(String deviceSecondPrice) {
		this.deviceSecondPrice = deviceSecondPrice;
	}

	@Column(name = "device_third_price")
	public String getDeviceThirdPrice() {
		return deviceThirdPrice;
	}

	public void setDeviceThirdPrice(String deviceThirdPrice) {
		this.deviceThirdPrice = deviceThirdPrice;
	}

	@Column(name = "device_price_remark")
	public String getDevicePriceRemark() {
		return devicePriceRemark;
	}

	public void setDevicePriceRemark(String devicePriceRemark) {
		this.devicePriceRemark = devicePriceRemark;
	}

	@Column(name = "tech_first_price")
	public String getTechFirstPrice() {
		return techFirstPrice;
	}

	public void setTechFirstPrice(String techFirstPrice) {
		this.techFirstPrice = techFirstPrice;
	}

	@Column(name = "tech_second_price")
	public String getTechSecondPrice() {
		return techSecondPrice;
	}

	public void setTechSecondPrice(String techSecondPrice) {
		this.techSecondPrice = techSecondPrice;
	}

	@Column(name = "tech_third_price")
	public String getTechThirdPrice() {
		return techThirdPrice;
	}

	public void setTechThirdPrice(String techThirdPrice) {
		this.techThirdPrice = techThirdPrice;
	}

	@Column(name = "tech_price_remark")
	public String getTechPriceRemark() {
		return techPriceRemark;
	}

	public void setTechPriceRemark(String techPriceRemark) {
		this.techPriceRemark = techPriceRemark;
	}

	@Column(name = "transport_first_price")
	public String getTransportFirstPrice() {
		return transportFirstPrice;
	}

	public void setTransportFirstPrice(String transportFirstPrice) {
		this.transportFirstPrice = transportFirstPrice;
	}

	@Column(name = "transport_second_price")
	public String getTransportSecondPrice() {
		return transportSecondPrice;
	}

	public void setTransportSecondPrice(String transportSecondPrice) {
		this.transportSecondPrice = transportSecondPrice;
	}

	@Column(name = "transport_third_price")
	public String getTransportThirdPrice() {
		return transportThirdPrice;
	}

	public void setTransportThirdPrice(String transportThirdPrice) {
		this.transportThirdPrice = transportThirdPrice;
	}

	@Column(name = "transport_price_remark")
	public String getTransportPriceRemark() {
		return transportPriceRemark;
	}

	public void setTransportPriceRemark(String transportPriceRemark) {
		this.transportPriceRemark = transportPriceRemark;
	}

	@Column(name = "device_remark")
	public String getDeviceRemark() {
		return deviceRemark;
	}

	public void setDeviceRemark(String deviceRemark) {
		this.deviceRemark = deviceRemark;
	}

	@Column(name = "spare_remark")
	public String getSpareRemark() {
		return spareRemark;
	}

	public void setSpareRemark(String spareRemark) {
		this.spareRemark = spareRemark;
	}

	@Column(name = "tool_remark")
	public String getToolRemark() {
		return toolRemark;
	}

	public void setToolRemark(String toolRemark) {
		this.toolRemark = toolRemark;
	}

	@Column(name = "create_uuid")
	public String getCreateUuid() {
		return createUuid;
	}

	public void setCreateUuid(String createUuid) {
		this.createUuid = createUuid;
	}

	@Column(name = "bidding_file_name")
	public String getBiddingFileName() {
		return biddingFileName;
	}

	public void setBiddingFileName(String biddingFileName) {
		this.biddingFileName = biddingFileName;
	}

	@Column(name = "bidding_file_version")
	public String getBiddingFileVersion() {
		return biddingFileVersion;
	}

	public void setBiddingFileVersion(String biddingFileVersion) {
		this.biddingFileVersion = biddingFileVersion;
	}

	@Column(name = "bidding_file_code")
	public String getBiddingFileCode() {
		return biddingFileCode;
	}

	public void setBiddingFileCode(String biddingFileCode) {
		this.biddingFileCode = biddingFileCode;
	}

	@Column(name = "bidding_bulletin_uuid")
	public String getBiddingBulletinUuid() {
		return biddingBulletinUuid;
	}

	public void setBiddingBulletinUuid(String biddingBulletinUuid) {
		this.biddingBulletinUuid = biddingBulletinUuid;
	}

	@Column(name = "bidding_bulletin_name")
	public String getBiddingBulletinName() {
		return biddingBulletinName;
	}

	public void setBiddingBulletinName(String biddingBulletinName) {
		this.biddingBulletinName = biddingBulletinName;
	}

	@Column(name = "bidding_bulletin_code")
	public String getBiddingBulletinCode() {
		return biddingBulletinCode;
	}

	public void setBiddingBulletinCode(String biddingBulletinCode) {
		this.biddingBulletinCode = biddingBulletinCode;
	}

}