package htos.business.entity.supplier;

import htos.coresys.entity.BaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * @author qinj
 * @date 2018-05-12 20:04
 **/
@Entity
@Table(name = "bs_goods_scope")
public class GoodsScope extends BaseEntity{

	private String name;
	private Set<SupplierGoodsScope> scopes = new HashSet<SupplierGoodsScope>();

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "scope")
	public Set<SupplierGoodsScope> getScopes() {
		return scopes;
	}

	public void setScopes(Set<SupplierGoodsScope> scopes) {
		this.scopes = scopes;
	}
}
