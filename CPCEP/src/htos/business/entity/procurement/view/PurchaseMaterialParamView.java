package htos.business.entity.procurement.view;

import htos.business.entity.procurement.PurchaseMaterialParam;
import org.apache.commons.lang3.StringUtils;

public class PurchaseMaterialParamView {
	private String pmpUuid;
	private String pmUuid;
	private String paramName;
	private String paramUnit;
	private String requiredValue;
	private String isKeyword;
	private String remark;

	public PurchaseMaterialParamView() {
	}

	public PurchaseMaterialParamView(String pmpUuid, String pmUuid, String paramName, String paramUnit, String requiredValue, String isKeyword, String remark) {
		this.pmpUuid = pmpUuid;
		this.pmUuid = pmUuid;
		this.paramName = paramName;
		this.paramUnit = paramUnit;
		this.requiredValue = requiredValue;
		this.isKeyword = isKeyword;
		this.remark = remark;
	}

	public String getPmpUuid() {
		return pmpUuid;
	}

	public void setPmpUuid(String pmpUuid) {
		this.pmpUuid = pmpUuid;
	}

	public String getPmUuid() {
		return pmUuid;
	}

	public void setPmUuid(String pmUuid) {
		this.pmUuid = pmUuid;
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

	public PurchaseMaterialParam toEntity(String pmUuid) {
		return new PurchaseMaterialParam(
				StringUtils.isNotBlank(this.getPmpUuid()) ? this.getPmpUuid() : null,
				StringUtils.isNotBlank(this.getPmUuid()) ? this.getPmUuid() : pmUuid,
				paramName,
				paramUnit,
				requiredValue,
				isKeyword,
				remark
		);
	}
}
