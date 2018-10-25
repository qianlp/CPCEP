package htos.business.entity.agreement;

import htos.business.entity.supplier.SupplierAttachment;
import htos.coresys.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "bs_agreement")
public class Agreement extends BaseEntity {
	private String catalogId;
	private String curDocId;
	private String projectUuid;
	private String purchasePlanUuid;
	private String confirmUuid;
	private String supplierId;
	private String name;
	private String code;
	private String deliveryAddress;
	private String techFileUuid;
	private SupplierAttachment techFile;

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

	@Column(name = "curDocId")
	public String getCurDocId() {
		return curDocId;
	}

	public void setCurDocId(String curDocId) {
		this.curDocId = curDocId;
	}

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = SupplierAttachment.class)
	@JoinColumn(name = "tech_file_uuid", referencedColumnName = "uuid", insertable = false, updatable = false)
	public SupplierAttachment getTechFile() {
		return techFile;
	}

	public void setTechFile(SupplierAttachment techFile) {
		this.techFile = techFile;
	}
}
