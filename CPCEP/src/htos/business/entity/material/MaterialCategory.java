package htos.business.entity.material;

import java.util.HashSet;
import java.util.Set;

import htos.business.entity.supplier.SupplierGoodsScope;
import htos.coresys.entity.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bs_material_category")//父设备材料表总表
public class MaterialCategory extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String categoryCode;
	private String categoryName;
	private String pid;
	private Set<SupplierGoodsScope> scopes = new HashSet<SupplierGoodsScope>();

	@Column(name = "category_code")
	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	@Column(name = "category_name")
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Column(name = "pid")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "scope")
	public Set<SupplierGoodsScope> getScopes() {
		return scopes;
	}

	public void setScopes(Set<SupplierGoodsScope> scopes) {
		this.scopes = scopes;
	}
	
	
}
