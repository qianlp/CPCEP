package htos.business.entity.bid;

import htos.coresys.entity.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "bs_supplier_bid")
public class SupplierBid extends BaseEntity {
	private String catalogId;
	private String biddingFileUuid;
	private String paymentMethod;
	private String phone;
	private String contacts;
	private String deliveryTime;
	private String technicalFileUuid;
	private String businessFileUuid;
	private String priceFileUuid;
	private String clarifyFileUuid;
	private String competeFileUuid;
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
	private Set<SupplierMaterialPrice> supplierMaterialPrices;
	private String createUuid;
	private String firstTotalPrice;
	private String secondTotalPrice;
	private String thirdTotalPrice;
	private String supplierName;
	private Boolean invitationClarify;	// 是否邀请澄清报价
	private Boolean invitationBid;		// 是否邀请竞价报价

	private String finalTotalPrice;
	private String transportFinalPrice;
	private String techFinalPrice;

	private Integer invitationTimes;  //邀请竞价次数

	@Column(name = "invitation_clarify")
	public Boolean getInvitationClarify() {
		return invitationClarify;
	}

	public void setInvitationClarify(Boolean invitationClarify) {
		this.invitationClarify = invitationClarify;
	}

	@Column(name = "invitation_bid")
	public Boolean getInvitationBid() {
		return invitationBid;
	}

	public void setInvitationBid(Boolean invitationBid) {
		this.invitationBid = invitationBid;
	}

	@Column(name = "create_uuid")
	public String getCreateUuid() {
		return createUuid;
	}

	public void setCreateUuid(String createUuid) {
		this.createUuid = createUuid;
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

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "supplierBid")
	public Set<SupplierMaterialPrice> getSupplierMaterialPrices() {
		return supplierMaterialPrices;
	}

	public void setSupplierMaterialPrices(Set<SupplierMaterialPrice> supplierMaterialPrices) {
		this.supplierMaterialPrices = supplierMaterialPrices;
	}

	@Column(name = "clarify_file_uuid")
	public String getClarifyFileUuid() {
		return clarifyFileUuid;
	}

	public void setClarifyFileUuid(String clarifyFileUuid) {
		this.clarifyFileUuid = clarifyFileUuid;
	}

	@Column(name = "compete_file_uuid")
	public String getCompeteFileUuid() {
		return competeFileUuid;
	}

	public void setCompeteFileUuid(String competeFileUuid) {
		this.competeFileUuid = competeFileUuid;
	}

	@Column(name = "first_total_price")
	public String getFirstTotalPrice() {
		return firstTotalPrice;
	}

	public void setFirstTotalPrice(String firstTotalPrice) {
		this.firstTotalPrice = firstTotalPrice;
	}

	@Column(name = "second_total_price")
	public String getSecondTotalPrice() {
		return secondTotalPrice;
	}

	public void setSecondTotalPrice(String secondTotalPrice) {
		this.secondTotalPrice = secondTotalPrice;
	}

	@Column(name = "third_total_price")
	public String getThirdTotalPrice() {
		return thirdTotalPrice;
	}

	public void setThirdTotalPrice(String thirdTotalPrice) {
		this.thirdTotalPrice = thirdTotalPrice;
	}

	@Transient
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name = "final_total_price")
	public String getFinalTotalPrice() {
		return finalTotalPrice;
	}

	public void setFinalTotalPrice(String finalTotalPrice) {
		this.finalTotalPrice = finalTotalPrice;
	}

	@Column(name = "transport_final_price")
	public String getTransportFinalPrice() {
		return transportFinalPrice;
	}

	public void setTransportFinalPrice(String transportFinalPrice) {
		this.transportFinalPrice = transportFinalPrice;
	}

	@Column(name = "tech_final_price")
	public String getTechFinalPrice() {
		return techFinalPrice;
	}

	public void setTechFinalPrice(String techFinalPrice) {
		this.techFinalPrice = techFinalPrice;
	}

	@Column(name = "invitation_times")
    public Integer getInvitationTimes() {
        return invitationTimes;
    }

    public void setInvitationTimes(Integer invitationTimes) {
        this.invitationTimes = invitationTimes;
    }
}
