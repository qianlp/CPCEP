package htos.business.entity.procurement;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "bs_purchase_material_param")
public class PurchaseMaterialParam {
	private String uuid;
	private String pmUuid;
	private String paramName;
	private String paramUnit;
	private String requiredValue;
	private String isKeyword;
	private String remark;

	public PurchaseMaterialParam() {
	}

	public PurchaseMaterialParam(String uuid, String pmUuid, String paramName, String paramUnit, String requiredValue, String isKeyword, String remark) {
		this.uuid = uuid;
		this.pmUuid = pmUuid;
		this.paramName = paramName;
		this.paramUnit = paramUnit;
		this.requiredValue = requiredValue;
		this.isKeyword = isKeyword;
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

	@Basic
	@Column(name = "pm_uuid")
	public String getPmUuid() {
		return pmUuid;
	}

	public void setPmUuid(String pmUuid) {
		this.pmUuid = pmUuid;
	}

	@Basic
	@Column(name = "param_name")
	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	@Basic
	@Column(name = "param_unit")
	public String getParamUnit() {
		return paramUnit;
	}

	public void setParamUnit(String paramUnit) {
		this.paramUnit = paramUnit;
	}

	@Basic
	@Column(name = "required_value")
	public String getRequiredValue() {
		return requiredValue;
	}

	public void setRequiredValue(String requiredValue) {
		this.requiredValue = requiredValue;
	}

	@Basic
	@Column(name = "is_keyword")
	public String getIsKeyword() {
		return isKeyword;
	}

	public void setIsKeyword(String isKeyword) {
		this.isKeyword = isKeyword;
	}

	@Basic
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PurchaseMaterialParam that = (PurchaseMaterialParam) o;
		return Objects.equals(uuid, that.uuid) &&
				Objects.equals(pmUuid, that.pmUuid) &&
				Objects.equals(paramName, that.paramName) &&
				Objects.equals(paramUnit, that.paramUnit) &&
				Objects.equals(requiredValue, that.requiredValue) &&
				Objects.equals(isKeyword, that.isKeyword) &&
				Objects.equals(remark, that.remark);
	}

	@Override
	public int hashCode() {

		return Objects.hash(uuid, pmUuid, paramName, paramUnit, requiredValue, isKeyword, remark);
	}
}
