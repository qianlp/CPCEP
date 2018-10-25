package htos.business.entity.supplier;

import htos.business.entity.material.MaterialCategory;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author qinj
 * @date 2018-05-12 20:04
 **/
@Entity
@Table(name = "bs_supplier_goods_scope")
public class SupplierGoodsScope {

	private String id;
	private SupplierExt supplier;
	private MaterialCategory scope;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 64)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "supplier_id")
	public SupplierExt getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierExt supplier) {
		this.supplier = supplier;
	}

	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "scope_id")
	public MaterialCategory getScope() {
		return scope;
	}

	public void setScope(MaterialCategory scope) {
		this.scope = scope;
	}

}
