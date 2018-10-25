package htos.business.dto;

import htos.business.entity.bid.SupplierMaterialParam;
import htos.business.entity.material.MaterialCategoryParam;

public class SupplierMaterialParamInfo {
	private String uuid;
	private String supplierBidUuid;
	private String purchaseMaterialUuid;
	private String purchaseParamUuid;
	private String responseValue;
	private String clarifyValue;
	private String paramName;
	private String paramUnit;
	private String requiredValue;
	private String isKeyword;
	private String remark;
	private String supplierUuid;

	public SupplierMaterialParamInfo() {
	}

	public SupplierMaterialParamInfo(String purchaseMaterialUuid, String purchaseParamUuid, String paramName, String paramUnit, String requiredValue, String isKeyword, String remark) {
		this.purchaseMaterialUuid = purchaseMaterialUuid;
		this.purchaseParamUuid = purchaseParamUuid;
		this.paramName = paramName;
		this.paramUnit = paramUnit;
		this.requiredValue = requiredValue;
		this.isKeyword = isKeyword;
		this.remark = remark;
	}

	public SupplierMaterialParamInfo(String uuid, String supplierBidUuid, String purchaseMaterialUuid, String purchaseParamUuid, String responseValue, String clarifyValue, String paramName, String paramUnit, String requiredValue, String isKeyword, String remark, String supplierUuid) {
		this.uuid = uuid;
		this.supplierBidUuid = supplierBidUuid;
		this.purchaseMaterialUuid = purchaseMaterialUuid;
		this.purchaseParamUuid = purchaseParamUuid;
		this.responseValue = responseValue;
		this.clarifyValue = clarifyValue;
		this.paramName = paramName;
		this.paramUnit = paramUnit;
		this.requiredValue = requiredValue;
		this.isKeyword = isKeyword;
		this.remark = remark;
		this.supplierUuid = supplierUuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSupplierBidUuid() {
		return supplierBidUuid;
	}

	public void setSupplierBidUuid(String supplierBidUuid) {
		this.supplierBidUuid = supplierBidUuid;
	}

	public String getPurchaseMaterialUuid() {
		return purchaseMaterialUuid;
	}

	public void setPurchaseMaterialUuid(String purchaseMaterialUuid) {
		this.purchaseMaterialUuid = purchaseMaterialUuid;
	}

	public String getResponseValue() {
		return responseValue;
	}

	public void setResponseValue(String responseValue) {
		this.responseValue = responseValue;
	}

	public String getClarifyValue() {
		return clarifyValue;
	}

	public void setClarifyValue(String clarifyValue) {
		this.clarifyValue = clarifyValue;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamUnit() {
		return paramUnit;
	}

	public void setParamUnit(String paramUnit) {
		this.paramUnit = paramUnit;
	}

	public String getRequiredValue() {
		return requiredValue;
	}

	public void setRequiredValue(String requiredValue) {
		this.requiredValue = requiredValue;
	}

	public String getIsKeyword() {
		return isKeyword;
	}

	public void setIsKeyword(String isKeyword) {
		this.isKeyword = isKeyword;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public SupplierMaterialParam intoParam(String supplierBidUuid, String purchaseMaterialUuid) {
		return new SupplierMaterialParam(
				this.uuid,
				supplierBidUuid,
				purchaseMaterialUuid,
				this.getPurchaseParamUuid(),
				this.getResponseValue(),
				this.getClarifyValue()
		);
	}

	public String getSupplierUuid() {
		return supplierUuid;
	}

	public void setSupplierUuid(String supplierUuid) {
		this.supplierUuid = supplierUuid;
	}

	public String getPurchaseParamUuid() {
		return purchaseParamUuid;
	}

	public void setPurchaseParamUuid(String purchaseParamUuid) {
		this.purchaseParamUuid = purchaseParamUuid;
	}
}