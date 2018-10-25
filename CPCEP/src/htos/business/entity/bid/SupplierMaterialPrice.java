package htos.business.entity.bid;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "bs_supplier_material_price")
public class SupplierMaterialPrice {
	private String uuid;
	private String supplierBidUuid;
	private String purchaseMaterialUuid;
	private String firstUnitPrice;
	private String secondUnitPrice;
	private String thirdUnitPrice;
	private String finalUnitPrice;
	private String remark;
	private SupplierBid supplierBid;

	public SupplierMaterialPrice() {
	}

	public SupplierMaterialPrice(String uuid, String supplierBidUuid, String purchaseMaterialUuid, String firstUnitPrice, String secondUnitPrice, String thirdUnitPrice, String finalUnitPrice, String remark) {
		this.uuid = uuid;
		this.supplierBidUuid = supplierBidUuid;
		this.purchaseMaterialUuid = purchaseMaterialUuid;
		this.firstUnitPrice = firstUnitPrice;
		this.secondUnitPrice = secondUnitPrice;
		this.thirdUnitPrice = thirdUnitPrice;
		this.finalUnitPrice = finalUnitPrice;
		this.remark = remark;
	}

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


	@Column(name = "supplier_bid_uuid")
	public String getSupplierBidUuid() {
		return supplierBidUuid;
	}

	public void setSupplierBidUuid(String supplierBidUuid) {
		this.supplierBidUuid = supplierBidUuid;
	}

	@Column(name = "purchase_material_uuid")
	public String getPurchaseMaterialUuid() {
		return purchaseMaterialUuid;
	}

	public void setPurchaseMaterialUuid(String purchaseMaterialUuid) {
		this.purchaseMaterialUuid = purchaseMaterialUuid;
	}

	@Column(name = "first_unit_price")
	public String getFirstUnitPrice() {
		return firstUnitPrice;
	}

	public void setFirstUnitPrice(String firstUnitPrice) {
		this.firstUnitPrice = firstUnitPrice;
	}

	@Column(name = "second_unit_price")
	public String getSecondUnitPrice() {
		return secondUnitPrice;
	}

	public void setSecondUnitPrice(String secondUnitPrice) {
		this.secondUnitPrice = secondUnitPrice;
	}

	@Column(name = "third_unit_price")
	public String getThirdUnitPrice() {
		return thirdUnitPrice;
	}

	public void setThirdUnitPrice(String thirdUnitPrice) {
		this.thirdUnitPrice = thirdUnitPrice;
	}

	@Column(name = "final_unit_price")
	public String getFinalUnitPrice() {
		return finalUnitPrice;
	}

	public void setFinalUnitPrice(String finalUnitPrice) {
		this.finalUnitPrice = finalUnitPrice;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToOne
	@JoinColumn(name = "supplier_bid_uuid", insertable = false, updatable = false)
	public SupplierBid getSupplierBid() {
		return supplierBid;
	}

	public void setSupplierBid(SupplierBid supplierBid) {
		this.supplierBid = supplierBid;
	}
}
