package htos.business.entity.agreement;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "v_agreement_info")
public class AgreementInfo {
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
	private String projectUuid;
	private String purchasePlanUuid;
	private String confirmUuid;
	private String supplierId;
	private String name;
	private String code;
	private String deliveryAddress;
	private String techFileUuid;
	private String curDocId;
	private String projectCode;
	private String projectName;
	private String owner;
	private String purchaseExecuteName;
	private String purchaseMethod;
	private String purchasePlanCode;
	private String purchasePlanName;
	private String finalOffer;
	private String supplierName;
	private String contacts;
	private String contactsMobile;

	@Basic
	@Id
	@Column(name = "uuid")
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Basic
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Basic
	@Column(name = "wfStatus")
	public String getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}

	@Basic
	@Column(name = "update_date")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Basic
	@Column(name = "create_by")
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Basic
	@Column(name = "create_date")
	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Basic
	@Column(name = "update_by")
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Basic
	@Column(name = "curUser")
	public String getCurUser() {
		return curUser;
	}

	public void setCurUser(String curUser) {
		this.curUser = curUser;
	}

	@Basic
	@Column(name = "del_flag")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	@Basic
	@Column(name = "docTypeId")
	public String getDocTypeId() {
		return docTypeId;
	}

	public void setDocTypeId(String docTypeId) {
		this.docTypeId = docTypeId;
	}

	@Basic
	@Column(name = "docBusId")
	public String getDocBusId() {
		return docBusId;
	}

	public void setDocBusId(String docBusId) {
		this.docBusId = docBusId;
	}

	@Basic
	@Column(name = "catalogId")
	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	@Basic
	@Column(name = "project_uuid")
	public String getProjectUuid() {
		return projectUuid;
	}

	public void setProjectUuid(String projectUuid) {
		this.projectUuid = projectUuid;
	}

	@Basic
	@Column(name = "purchase_plan_uuid")
	public String getPurchasePlanUuid() {
		return purchasePlanUuid;
	}

	public void setPurchasePlanUuid(String purchasePlanUuid) {
		this.purchasePlanUuid = purchasePlanUuid;
	}

	@Basic
	@Column(name = "confirm_uuid")
	public String getConfirmUuid() {
		return confirmUuid;
	}

	public void setConfirmUuid(String confirmUuid) {
		this.confirmUuid = confirmUuid;
	}

	@Basic
	@Column(name = "supplier_id")
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Basic
	@Column(name = "delivery_address")
	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	@Basic
	@Column(name = "tech_file_uuid")
	public String getTechFileUuid() {
		return techFileUuid;
	}

	public void setTechFileUuid(String techFileUuid) {
		this.techFileUuid = techFileUuid;
	}

	@Basic
	@Column(name = "curDocId")
	public String getCurDocId() {
		return curDocId;
	}

	public void setCurDocId(String curDocId) {
		this.curDocId = curDocId;
	}

	@Basic
	@Column(name = "project_code")
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	@Basic
	@Column(name = "project_name")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Basic
	@Column(name = "owner")
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Basic
	@Column(name = "purchase_execute_name")
	public String getPurchaseExecuteName() {
		return purchaseExecuteName;
	}

	public void setPurchaseExecuteName(String purchaseExecuteName) {
		this.purchaseExecuteName = purchaseExecuteName;
	}

	@Basic
	@Column(name = "purchase_method")
	public String getPurchaseMethod() {
		return purchaseMethod;
	}

	public void setPurchaseMethod(String purchaseMethod) {
		this.purchaseMethod = purchaseMethod;
	}

	@Basic
	@Column(name = "purchase_plan_code")
	public String getPurchasePlanCode() {
		return purchasePlanCode;
	}

	public void setPurchasePlanCode(String purchasePlanCode) {
		this.purchasePlanCode = purchasePlanCode;
	}

	@Basic
	@Column(name = "purchase_plan_name")
	public String getPurchasePlanName() {
		return purchasePlanName;
	}

	public void setPurchasePlanName(String purchasePlanName) {
		this.purchasePlanName = purchasePlanName;
	}

	@Basic
	@Column(name = "final_offer")
	public String getFinalOffer() {
		return finalOffer;
	}

	public void setFinalOffer(String finalOffer) {
		this.finalOffer = finalOffer;
	}

	@Basic
	@Column(name = "supplier_name")
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Basic
	@Column(name = "contacts")
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@Basic
	@Column(name = "contacts_mobile")
	public String getContactsMobile() {
		return contactsMobile;
	}

	public void setContactsMobile(String contactsMobile) {
		this.contactsMobile = contactsMobile;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AgreementInfo that = (AgreementInfo) o;
		return Objects.equals(uuid, that.uuid) &&
				Objects.equals(remark, that.remark) &&
				Objects.equals(wfStatus, that.wfStatus) &&
				Objects.equals(updateDate, that.updateDate) &&
				Objects.equals(createBy, that.createBy) &&
				Objects.equals(createDate, that.createDate) &&
				Objects.equals(updateBy, that.updateBy) &&
				Objects.equals(curUser, that.curUser) &&
				Objects.equals(delFlag, that.delFlag) &&
				Objects.equals(docTypeId, that.docTypeId) &&
				Objects.equals(docBusId, that.docBusId) &&
				Objects.equals(catalogId, that.catalogId) &&
				Objects.equals(projectUuid, that.projectUuid) &&
				Objects.equals(purchasePlanUuid, that.purchasePlanUuid) &&
				Objects.equals(confirmUuid, that.confirmUuid) &&
				Objects.equals(supplierId, that.supplierId) &&
				Objects.equals(name, that.name) &&
				Objects.equals(code, that.code) &&
				Objects.equals(deliveryAddress, that.deliveryAddress) &&
				Objects.equals(techFileUuid, that.techFileUuid) &&
				Objects.equals(curDocId, that.curDocId) &&
				Objects.equals(projectCode, that.projectCode) &&
				Objects.equals(projectName, that.projectName) &&
				Objects.equals(owner, that.owner) &&
				Objects.equals(purchaseExecuteName, that.purchaseExecuteName) &&
				Objects.equals(purchaseMethod, that.purchaseMethod) &&
				Objects.equals(finalOffer, that.finalOffer) &&
				Objects.equals(supplierName, that.supplierName) &&
				Objects.equals(contacts, that.contacts) &&
				Objects.equals(contactsMobile, that.contactsMobile);
	}

	@Override
	public int hashCode() {

		return Objects.hash(uuid, remark, wfStatus, updateDate, createBy, createDate, updateBy, curUser, delFlag, docTypeId, docBusId, catalogId, projectUuid, purchasePlanUuid, confirmUuid, supplierId, name, code, deliveryAddress, techFileUuid, curDocId, projectCode, projectName, owner, purchaseExecuteName, purchaseMethod, finalOffer, supplierName, contacts, contactsMobile);
	}
}
