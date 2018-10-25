package htos.business.dto;

import htos.business.entity.bid.SupplierMaterialPrice;
import htos.business.entity.procurement.PurchaseMaterial;

public class SupplierMaterialPriceInfo {
	private String uuid;
	private String materialPriceUuid;
	private String type;
	private String materialCode;
	private String materialName;
	private String brand;
	private String specModel;
	private String unit;
	private int num;
	private String firstUnitPrice;
	private String secondUnitPrice;
	private String thirdUnitPrice;
	private String finalUnitPrice;
	private String priceRemark;
	private String paramsJson;

	public SupplierMaterialPriceInfo() {
	}

	public SupplierMaterialPriceInfo(String uuid, String type, String materialCode, String materialName, String brand, String specModel, String unit, int num) {
		this.uuid = uuid;
		this.type = type;
		this.materialCode = materialCode;
		this.materialName = materialName;
		this.brand = brand;
		this.specModel = specModel;
		this.unit = unit;
		this.num = num;
	}

	public SupplierMaterialPriceInfo(String uuid, String materialPriceUuid, String type, String materialCode, String materialName, String brand, String specModel, String unit, int num, String firstUnitPrice, String secondUnitPrice, String thirdUnitPrice,String finalUnitPrice, String priceRemark) {
		this.uuid = uuid;
		this.materialPriceUuid = materialPriceUuid;
		this.type = type;
		this.materialCode = materialCode;
		this.materialName = materialName;
		this.brand = brand;
		this.specModel = specModel;
		this.unit = unit;
		this.num = num;
		this.firstUnitPrice = firstUnitPrice;
		this.secondUnitPrice = secondUnitPrice;
		this.thirdUnitPrice = thirdUnitPrice;
		this.finalUnitPrice = finalUnitPrice;
		this.priceRemark = priceRemark;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMaterialPriceUuid() {
		return materialPriceUuid;
	}

	public void setMaterialPriceUuid(String materialPriceUuid) {
		this.materialPriceUuid = materialPriceUuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSpecModel() {
		return specModel;
	}

	public void setSpecModel(String specModel) {
		this.specModel = specModel;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getFirstUnitPrice() {
		return firstUnitPrice;
	}

	public void setFirstUnitPrice(String firstUnitPrice) {
		this.firstUnitPrice = firstUnitPrice;
	}

	public String getSecondUnitPrice() {
		return secondUnitPrice;
	}

	public void setSecondUnitPrice(String secondUnitPrice) {
		this.secondUnitPrice = secondUnitPrice;
	}

	public String getThirdUnitPrice() {
		return thirdUnitPrice;
	}

	public void setThirdUnitPrice(String thirdUnitPrice) {
		this.thirdUnitPrice = thirdUnitPrice;
	}

	public String getPriceRemark() {
		return priceRemark;
	}

	public void setPriceRemark(String priceRemark) {
		this.priceRemark = priceRemark;
	}

	public String getParamsJson() {
		return paramsJson;
	}

	public void setParamsJson(String paramsJson) {
		this.paramsJson = paramsJson;
	}

	public String getFinalUnitPrice() {
		return finalUnitPrice;
	}

	public void setFinalUnitPrice(String finalUnitPrice) {
		this.finalUnitPrice = finalUnitPrice;
	}

	public static SupplierMaterialPriceInfo from(PurchaseMaterial purchaseMaterial, SupplierMaterialPrice supplierMaterialPrice) {
		return new SupplierMaterialPriceInfo(
				purchaseMaterial.getUuid(),
				supplierMaterialPrice.getUuid(),
				purchaseMaterial.getType(),
				purchaseMaterial.getMaterialCode(),
				purchaseMaterial.getMaterialName(),
				purchaseMaterial.getBrand(),
				purchaseMaterial.getSpecModel(),
				purchaseMaterial.getUnit(),
				purchaseMaterial.getNum(),
				supplierMaterialPrice.getFirstUnitPrice(),
				supplierMaterialPrice.getSecondUnitPrice(),
				supplierMaterialPrice.getThirdUnitPrice(),
				supplierMaterialPrice.getFinalUnitPrice(),
				supplierMaterialPrice.getRemark()
		);
	}

	public static SupplierMaterialPriceInfo from(PurchaseMaterial purchaseMaterial) {
		return new SupplierMaterialPriceInfo(
				purchaseMaterial.getUuid(),
				purchaseMaterial.getType(),
				purchaseMaterial.getMaterialCode(),
				purchaseMaterial.getMaterialName(),
				purchaseMaterial.getBrand(),
				purchaseMaterial.getSpecModel(),
				purchaseMaterial.getUnit(),
				purchaseMaterial.getNum()
		);
	}

	public SupplierMaterialPrice intoSupplierMaterialPrice(String supplierBidUuid) {
		return new SupplierMaterialPrice(
				materialPriceUuid,
				supplierBidUuid,
				uuid,
				firstUnitPrice,
				secondUnitPrice,
				thirdUnitPrice,
				finalUnitPrice,
				priceRemark
		);
	}
}
