package htos.business.entity.bulletin.view;

import htos.business.entity.procurement.PurchasePlan;
import htos.business.entity.supplier.SupplierAttachment;
import htos.coresys.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

public class ViewBiddingBulletin {

	private String uuid;
	private String wfStatus;
	private String remark;
	private String delFlag;
	private String name;					// 招标名称
	private String code;					// 招标编号
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
	private Short status;					// 开标状态，0 未开标，1 开标中，2 开标结束

	private String fileName;				// 招标文件名称
	private String version;					// 招标文件版本号

	private String supplierName;
	private String userId;
	private String bidUuid;//供应商投标id

	private Boolean invitationClarify;	// 是否邀请澄清报价
	private Boolean invitationBid;		// 是否邀请竞价报价

	private Integer rank;
	private String secondTotalPrice;
	private String thirdTotalPrice;

	private String projectName;

	//在招标文件澄清管理中用到
	private String projCode;
	private String projName;
	private int    clarifyStatus;

	private String supplierBidId;
	private String projectId;
	
	private String contacts;
	private String phon;
   //技术评标人
	private String techStatus;

    /**
	 * @return the projectId
	 */
	public String getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getSupplierBidId() {
        return supplierBidId;
    }

    public void setSupplierBidId(String supplierBidId) {
        this.supplierBidId = supplierBidId;
    }

    public String getSecondTotalPrice() {
		return secondTotalPrice;
	}

	public void setSecondTotalPrice(String secondTotalPrice) {
		this.secondTotalPrice = secondTotalPrice;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Boolean getInvitationClarify() {
		return invitationClarify;
	}

	public void setInvitationClarify(Boolean invitationClarify) {
		this.invitationClarify = invitationClarify;
	}

	public Boolean getInvitationBid() {
		return invitationBid;
	}

	public void setInvitationBid(Boolean invitationBid) {
		this.invitationBid = invitationBid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPurchasePlanUuid() {
		return purchasePlanUuid;
	}

	public void setPurchasePlanUuid(String purchasePlanUuid) {
		this.purchasePlanUuid = purchasePlanUuid;
	}

	public PurchasePlan getPurchasePlan() {
		return purchasePlan;
	}

	public void setPurchasePlan(PurchasePlan purchasePlan) {
		this.purchasePlan = purchasePlan;
	}

	public String getPublishMedia() {
		return publishMedia;
	}

	public void setPublishMedia(String publishMedia) {
		this.publishMedia = publishMedia;
	}

	public String getTemplateUuid() {
		return templateUuid;
	}

	public void setTemplateUuid(String templateUuid) {
		this.templateUuid = templateUuid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegisterStartTime() {
		return registerStartTime;
	}

	public void setRegisterStartTime(Date registerStartTime) {
		this.registerStartTime = registerStartTime;
	}

	public Date getRegisterEndTime() {
		return registerEndTime;
	}

	public void setRegisterEndTime(Date registerEndTime) {
		this.registerEndTime = registerEndTime;
	}

	public Date getBidStartTime() {
		return bidStartTime;
	}

	public void setBidStartTime(Date bidStartTime) {
		this.bidStartTime = bidStartTime;
	}

	public Date getBidEndTime() {
		return bidEndTime;
	}

	public void setBidEndTime(Date bidEndTime) {
		this.bidEndTime = bidEndTime;
	}

	public Date getBidOpenTime() {
		return bidOpenTime;
	}

	public void setBidOpenTime(Date bidOpenTime) {
		this.bidOpenTime = bidOpenTime;
	}

	public String getAttachUuid() {
		return attachUuid;
	}

	public void setAttachUuid(String attachUuid) {
		this.attachUuid = attachUuid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBidUuid() {
		return bidUuid;
	}

	public void setBidUuid(String bidUuid) {
		this.bidUuid = bidUuid;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjCode() {
		return projCode;
	}

	public void setProjCode(String projCode) {
		this.projCode = projCode;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public int getClarifyStatus() {
		return clarifyStatus;
	}

	public void setClarifyStatus(int clarifyStatus) {
		this.clarifyStatus = clarifyStatus;
	}

	public String getThirdTotalPrice() {
		return thirdTotalPrice;
	}

	public void setThirdTotalPrice(String thirdTotalPrice) {
		this.thirdTotalPrice = thirdTotalPrice;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getPhon() {
		return phon;
	}

	public void setPhon(String phon) {
		this.phon = phon;
	}

	public String getTechStatus() {
		return techStatus;
	}

	public void setTechStatus(String techStatus) {
		this.techStatus = techStatus;
	}
	
}
