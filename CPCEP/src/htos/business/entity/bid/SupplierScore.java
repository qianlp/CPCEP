package htos.business.entity.bid;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "bs_supplier_score")
public class SupplierScore {
	private String uuid;
	private String techBidEvalUuid;
	private String supplierBidUuid;
	private String isFeasible;
	private String score;
	private String remark;

	public SupplierScore() {
	}

	public SupplierScore(String uuid, String techBidEvalUuid, String supplierBidUuid, String isFeasible, String score, String remark) {
		this.uuid = uuid;
		this.techBidEvalUuid = techBidEvalUuid;
		this.supplierBidUuid = supplierBidUuid;
		this.isFeasible = isFeasible;
		this.score = score;
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
	@Column(name = "tech_bid_eval_uuid")
	public String getTechBidEvalUuid() {
		return techBidEvalUuid;
	}

	public void setTechBidEvalUuid(String techBidEvalUuid) {
		this.techBidEvalUuid = techBidEvalUuid;
	}

	@Basic
	@Column(name = "supplier_bid_uuid")
	public String getSupplierBidUuid() {
		return supplierBidUuid;
	}

	public void setSupplierBidUuid(String supplierBidUuid) {
		this.supplierBidUuid = supplierBidUuid;
	}

	@Basic
	@Column(name = "is_feasible")
	public String getIsFeasible() {
		return isFeasible;
	}

	public void setIsFeasible(String isFeasible) {
		this.isFeasible = isFeasible;
	}

	@Basic
	@Column(name = "score")
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Basic
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
