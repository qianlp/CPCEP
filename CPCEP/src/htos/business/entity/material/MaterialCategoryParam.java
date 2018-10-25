package htos.business.entity.material;

import htos.coresys.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bs_material_category_param")//参数项表
public class MaterialCategoryParam extends BaseEntity {
	private String categoryUuid;
	private String paramName;
	private String paramUnit;
	private String requiredValue;
	private String isKeyword;

	@Column(name = "category_uuid")
	public String getCategoryUuid() {
		return categoryUuid;
	}

	public void setCategoryUuid(String categoryUuid) {
		this.categoryUuid = categoryUuid;
	}

	@Column(name = "param_name")
	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	@Column(name = "param_unit")
	public String getParamUnit() {
		return paramUnit;
	}

	public void setParamUnit(String paramUnit) {
		this.paramUnit = paramUnit;
	}

	@Column(name = "required_value")
	public String getRequiredValue() {
		return requiredValue;
	}

	public void setRequiredValue(String requiredValue) {
		this.requiredValue = requiredValue;
	}


	@Column(name = "is_keyword")
	public String getIsKeyword() {
		return isKeyword;
	}

	public void setIsKeyword(String isKeyword) {
		this.isKeyword = isKeyword;
	}
}
