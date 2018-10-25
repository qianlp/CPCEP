package htos.business.entity.bid;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "bs_supplier_material_param")
public class SupplierMaterialParam {
	private String uuid;
	private String supplierBidUuid;
	private String purchaseMaterialUuid;
	private String purchaseParamUuid;
	private String responseValue;
	private String clarifyValue;

	public SupplierMaterialParam() {
	}

	public SupplierMaterialParam(String uuid, String supplierBidUuid, String purchaseMaterialUuid, String purchaseParamUuid, String responseValue, String clarifyValue) {
		this.uuid = uuid;
		this.supplierBidUuid = supplierBidUuid;
		this.purchaseMaterialUuid = purchaseMaterialUuid;
		this.purchaseParamUuid = purchaseParamUuid;
		this.responseValue = responseValue;
		this.clarifyValue = clarifyValue;
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

	@Column(name = "response_value")
	public String getResponseValue() {
		return responseValue;
	}

	public void setResponseValue(String responseValue) {
		this.responseValue = responseValue;
	}

	@Column(name = "clarify_value")
	public String getClarifyValue() {
		return clarifyValue;
	}

	public void setClarifyValue(String clarifyValue) {
		this.clarifyValue = clarifyValue;
	}

	@Column(name = "purchase_param_uuid")
	public String getPurchaseParamUuid() {
		return purchaseParamUuid;
	}

	public void setPurchaseParamUuid(String purchaseParamUuid) {
		this.purchaseParamUuid = purchaseParamUuid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SupplierMaterialParam that = (SupplierMaterialParam) o;
		return Objects.equals(uuid, that.uuid) &&
				Objects.equals(supplierBidUuid, that.supplierBidUuid) &&
				Objects.equals(purchaseMaterialUuid, that.purchaseMaterialUuid) &&
				Objects.equals(purchaseParamUuid, that.purchaseParamUuid) &&
				Objects.equals(responseValue, that.responseValue);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid, supplierBidUuid, purchaseMaterialUuid, purchaseParamUuid, responseValue);
	}
}
