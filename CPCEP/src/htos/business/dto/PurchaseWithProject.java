package htos.business.dto;

import java.util.Date;

public class PurchaseWithProject {
	private String projectUuid;
	private String projectCode;
	private String projectName;
	private String owner;
	private String purchasePlanUuid;
	private String purchasePlanCode;
	private String purchasePlanName;
	private String purchaseMethod;
	private Date  createDate;
	private String supplierId;
	private String supplierName;
	private String confirmUuid;
	private String finalOffer;

	public PurchaseWithProject(String projectUuid, String projectCode, String projectName, String owner, String purchasePlanUuid, String purchasePlanCode, String purchasePlanName, String purchaseMethod, Date createDate, String supplierId, String supplierName, String confirmUuid, String finalOffer) {
		this.projectUuid = projectUuid;
		this.projectCode = projectCode;
		this.projectName = projectName;
		this.owner = owner;
		this.purchasePlanUuid = purchasePlanUuid;
		this.purchasePlanCode = purchasePlanCode;
		this.purchasePlanName = purchasePlanName;
		this.purchaseMethod = purchaseMethod;
		this.createDate = createDate;
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.confirmUuid = confirmUuid;
		this.finalOffer = finalOffer;
	}

	public PurchaseWithProject() {
	}

	public String getConfirmUuid() {
		return confirmUuid;
	}

	public void setConfirmUuid(String confirmUuid) {
		this.confirmUuid = confirmUuid;
	}

	public String getProjectUuid() {
		return projectUuid;
	}

	public void setProjectUuid(String projectUuid) {
		this.projectUuid = projectUuid;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPurchasePlanUuid() {
		return purchasePlanUuid;
	}

	public void setPurchasePlanUuid(String purchasePlanUuid) {
		this.purchasePlanUuid = purchasePlanUuid;
	}

	public String getPurchasePlanCode() {
		return purchasePlanCode;
	}

	public void setPurchasePlanCode(String purchasePlanCode) {
		this.purchasePlanCode = purchasePlanCode;
	}

	public String getPurchasePlanName() {
		return purchasePlanName;
	}

	public void setPurchasePlanName(String purchasePlanName) {
		this.purchasePlanName = purchasePlanName;
	}

	public String getPurchaseMethod() {
		return purchaseMethod;
	}

	public void setPurchaseMethod(String purchaseMethod) {
		this.purchaseMethod = purchaseMethod;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public String getFinalOffer() {
		return finalOffer;
	}

	public void setFinalOffer(String finalOffer) {
		this.finalOffer = finalOffer;
	}
}
