package htos.business.entity.material;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author qinj
 * @date 2018-05-22 23:04
 **/
@Entity
@Table(name = "bs_material_attr")
public class MaterialAttr {
	private String id;
	private String name;
	private String norms;
	private String brand;
	private Integer count;
	private String purchaseMaterial;
	private String purchasePlan;
	private Integer price;
	private String userId;
	private Integer finalPrice;

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

	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "price")
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Column(name = "final_price")
	public Integer getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Integer finalPrice) {
		this.finalPrice = finalPrice;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "norms")
	public String getNorms() {
		return norms;
	}

	public void setNorms(String norms) {
		this.norms = norms;
	}

	@Basic
	@Column(name = "brand")
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Basic
	@Column(name = "count")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Basic
	@Column(name = "purchase_material")
	public String getPurchaseMaterial() {
		return purchaseMaterial;
	}

	public void setPurchaseMaterial(String purchaseMaterial) {
		this.purchaseMaterial = purchaseMaterial;
	}

	@Basic
	@Column(name = "purchase_plan")
	public String getPurchasePlan() {
		return purchasePlan;
	}

	public void setPurchasePlan(String purchasePlan) {
		this.purchasePlan = purchasePlan;
	}

}
