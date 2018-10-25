package htos.business.dto;

import htos.business.entity.bid.SupplierScore;

public class SupplierScoreInfo {
	private String uuid;
	private String techBidEvalUuid;
	private String supplierUuid;
	private String supplierName;
	private String thirdTotalPrice;
	private String supplierBidUuid;
	private String isFeasible;
	private String score;
	private String remark;

	public SupplierScoreInfo() {
	}

	public SupplierScoreInfo(String uuid, String techBidEvalUuid, String supplierUuid, String supplierName, String thirdTotalPrice, String supplierBidUuid, String isFeasible, String score, String remark) {
		this.uuid = uuid;
		this.techBidEvalUuid = techBidEvalUuid;
		this.supplierUuid = supplierUuid;
		this.supplierName = supplierName;
		this.thirdTotalPrice = thirdTotalPrice;
		this.supplierBidUuid = supplierBidUuid;
		this.isFeasible = isFeasible;
		this.score = score;
		this.remark = remark;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTechBidEvalUuid() {
		return techBidEvalUuid;
	}

	public void setTechBidEvalUuid(String techBidEvalUuid) {
		this.techBidEvalUuid = techBidEvalUuid;
	}

	public String getSupplierUuid() {
		return supplierUuid;
	}

	public void setSupplierUuid(String supplierUuid) {
		this.supplierUuid = supplierUuid;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierBidUuid() {
		return supplierBidUuid;
	}

	public void setSupplierBidUuid(String supplierBidUuid) {
		this.supplierBidUuid = supplierBidUuid;
	}

	public String getIsFeasible() {
		return isFeasible;
	}

	public void setIsFeasible(String isFeasible) {
		this.isFeasible = isFeasible;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public SupplierScore intoEntityWith(String techBidEvalUuid) {
		return new SupplierScore(
				this.uuid,
				techBidEvalUuid,
				this.supplierBidUuid,
				this.isFeasible,
				this.score,
				this.remark
		);
	}

	public String getThirdTotalPrice() {
		return thirdTotalPrice;
	}

	public void setThirdTotalPrice(String thirdTotalPrice) {
		this.thirdTotalPrice = thirdTotalPrice;
	}
}
