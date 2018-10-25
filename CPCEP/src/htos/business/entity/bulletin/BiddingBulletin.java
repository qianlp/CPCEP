package htos.business.entity.bulletin;

import htos.business.entity.procurement.PurchasePlan;
import htos.business.entity.supplier.SupplierAttachment;
import htos.coresys.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bs_bidding_bulletin")
public class BiddingBulletin extends BaseEntity{
	private String catalogId;
	private String curDocId;
	private String name;
	private String code;
	private String purchasePlanUuid;
	private PurchasePlan purchasePlan;
	private String publishMedia;
	private String templateUuid;
	private String content;
	private Date registerStartTime;
	private Date registerEndTime;
	private Date bidStartTime;
	private Date bidEndTime;
	private Date bidOpenTime;
	private String attachUuid;
	private SupplierAttachment supplierAttachment;
	private Short status;		// 开标状态，0 未开标，1 开标中，2 开标结束

	@Column(name = "status")
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "catalogId")
	public String getCatalogId() {
		return catalogId;
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

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "purchase_plan_uuid")
	public String getPurchasePlanUuid() {
		return purchasePlanUuid;
	}

	public void setPurchasePlanUuid(String purchasePlanUuid) {
		this.purchasePlanUuid = purchasePlanUuid;
	}

	@Column(name = "publish_media")
	public String getPublishMedia() {
		return publishMedia;
	}

	public void setPublishMedia(String publishMedia) {
		this.publishMedia = publishMedia;
	}

	@Column(name = "template_uuid")
	public String getTemplateUuid() {
		return templateUuid;
	}

	public void setTemplateUuid(String templateUuid) {
		this.templateUuid = templateUuid;
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "register_start_time")
	public Date getRegisterStartTime() {
		return registerStartTime;
	}

	public void setRegisterStartTime(Date registerStartTime) {
		this.registerStartTime = registerStartTime;
	}

	@Column(name = "register_end_time")
	public Date getRegisterEndTime() {
		return registerEndTime;
	}

	public void setRegisterEndTime(Date registerEndTime) {
		this.registerEndTime = registerEndTime;
	}

	@Column(name = "bid_start_time")
	public Date getBidStartTime() {
		return bidStartTime;
	}

	public void setBidStartTime(Date bidStartTime) {
		this.bidStartTime = bidStartTime;
	}

	@Column(name = "bid_end_time")
	public Date getBidEndTime() {
		return bidEndTime;
	}

	public void setBidEndTime(Date bidEndTime) {
		this.bidEndTime = bidEndTime;
	}

	@Column(name = "bid_open_time")
	public Date getBidOpenTime() {
		return bidOpenTime;
	}

	public void setBidOpenTime(Date bidOpenTime) {
		this.bidOpenTime = bidOpenTime;
	}

	@Column(name = "attach_uuid")
	public String getAttachUuid() {
		return attachUuid;
	}

	public void setAttachUuid(String attachUuid) {
		this.attachUuid = attachUuid;
	}

	@ManyToOne(fetch = FetchType.LAZY,targetEntity = PurchasePlan.class)
	@JoinColumn(name = "purchase_plan_uuid",referencedColumnName="uuid" ,insertable = false, updatable = false)
	public PurchasePlan getPurchasePlan() {
		return purchasePlan;
	}

	public void setPurchasePlan(PurchasePlan purchasePlan) {
		this.purchasePlan = purchasePlan;
	}

	@ManyToOne(fetch = FetchType.LAZY,targetEntity = SupplierAttachment.class)
	@JoinColumn(name = "attach_uuid",referencedColumnName="uuid" ,insertable = false, updatable = false)
	public SupplierAttachment getSupplierAttachment() {
		return supplierAttachment;
	}

	public void setSupplierAttachment(SupplierAttachment supplierAttachment) {
		this.supplierAttachment = supplierAttachment;
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//
	//

	private String downExtent;
	private String upExtent;
	private Short type;
	private Date endDate;
	private String payType;
	private Date deliveryDate;
	private String description;
	private Float minAmount;

	@Transient
	public Float getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(Float minAmount) {
		this.minAmount = minAmount;
	}

	@Transient
	public String getDownExtent() {
		return downExtent;
	}

	public void setDownExtent(String downExtent) {
		this.downExtent = downExtent;
	}

	@Transient
	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Transient
	public String getUpExtent() {
		return upExtent;
	}

	public void setUpExtent(String upExtent) {
		this.upExtent = upExtent;
	}

	@Transient
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Transient
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Transient
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@Transient
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
