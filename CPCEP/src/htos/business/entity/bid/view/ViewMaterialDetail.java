package htos.business.entity.bid.view;

public class ViewMaterialDetail {
	private String uuid;
	private String materialName;
	private String materialAttrUuid;
	private String materialAttrName;
	private String norms;
	private String brand;
	private String count;
	private String price;
	private String finalUnitPrice;
	private String rowId;
	private String type;

	public ViewMaterialDetail() {
	}

	public ViewMaterialDetail(String uuid, String materialName, String materialAttrUuid, String materialAttrName, String norms, String brand, String count, String price, String finalUnitPrice,String rowId,String type) {
		this.uuid = uuid;
		this.materialName = materialName;
		this.materialAttrUuid = materialAttrUuid;
		this.materialAttrName = materialAttrName;
		this.norms = norms;
		this.brand = brand;
		this.count = count;
		this.price = price;
		this.finalUnitPrice = finalUnitPrice;
		this.rowId = rowId;
		this.type = type;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialAttrUuid() {
		return materialAttrUuid;
	}

	public void setMaterialAttrUuid(String materialAttrUuid) {
		this.materialAttrUuid = materialAttrUuid;
	}

	public String getMaterialAttrName() {
		return materialAttrName;
	}

	public void setMaterialAttrName(String materialAttrName) {
		this.materialAttrName = materialAttrName;
	}

	public String getNorms() {
		return norms;
	}

	public void setNorms(String norms) {
		this.norms = norms;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getFinalUnitPrice() {
		return finalUnitPrice;
	}

	public void setFinalUnitPrice(String finalUnitPrice) {
		this.finalUnitPrice = finalUnitPrice;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
