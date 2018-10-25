package htos.business.dto;

public class PurchaseMaterialParamInfo {
	private String purchaseMaterialUuid;
	private String materialUuid;
	private String materialName;
	private String paramUuid;
	private String paramName;
	private String requiredValue;

	public PurchaseMaterialParamInfo() {
	}

	public PurchaseMaterialParamInfo(String purchaseMaterialUuid, String materialUuid, String materialName, String paramUuid, String paramName, String requiredValue) {
		this.purchaseMaterialUuid = purchaseMaterialUuid;
		this.materialUuid = materialUuid;
		this.materialName = materialName;
		this.paramUuid = paramUuid;
		this.paramName = paramName;
		this.requiredValue = requiredValue;
	}

	public String getPurchaseMaterialUuid() {
		return purchaseMaterialUuid;
	}

	public void setPurchaseMaterialUuid(String purchaseMaterialUuid) {
		this.purchaseMaterialUuid = purchaseMaterialUuid;
	}

	public String getMaterialUuid() {
		return materialUuid;
	}

	public void setMaterialUuid(String materialUuid) {
		this.materialUuid = materialUuid;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getRequiredValue() {
		return requiredValue;
	}

	public void setRequiredValue(String requiredValue) {
		this.requiredValue = requiredValue;
	}

	public String getParamUuid() {
		return paramUuid;
	}

	public void setParamUuid(String paramUuid) {
		this.paramUuid = paramUuid;
	}
}
