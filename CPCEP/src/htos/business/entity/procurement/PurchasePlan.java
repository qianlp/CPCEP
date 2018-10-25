package htos.business.entity.procurement;

import htos.coresys.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Date;

@Entity
@Table(name = "bs_purchase_plan")
public class PurchasePlan extends BaseEntity {
	private String projectUuid;
	private String projectCode;
	private String projectName;
	private String purchaseApplyUuid;
	private String purchaseApplyCode;
	private String purchaseApplyName;

	private String purchasePlanCode;
	private String purchasePlanName;
	private String purchaseExecuteUuid;
	private String executePeoName;

	private String technologyReviewerName;
	private String technologyReviewerUuid;

	private String purchaseType;
    private String reviewMethod;
	private String purchaseMethod;
	private String bidEvaluationMethod;
	private String checkWay;
	private Date requestCalibrationTime;
	private String catalogId;
	private String curDocId;
	private String invSupplier;
	private String canSupplier;
	//wfp新增要求进场时间

    private Date requestApproachTime;
    private String projectNode;

	@Column(name = "project_uuid")
	public String getProjectUuid() {
		return projectUuid;
	}

	public void setProjectUuid(String projectUuid) {
		this.projectUuid = projectUuid;
	}


	@Column(name = "purchase_apply_uuid")
	public String getPurchaseApplyUuid() {
		return purchaseApplyUuid;
	}

	public void setPurchaseApplyUuid(String purchaseApplyUuid) {
		this.purchaseApplyUuid = purchaseApplyUuid;
	}


	@Column(name = "purchase_plan_code")
	public String getPurchasePlanCode() {
		return purchasePlanCode;
	}

	public void setPurchasePlanCode(String purchasePlanCode) {
		this.purchasePlanCode = purchasePlanCode;
	}


	@Column(name = "purchase_plan_name")
	public String getPurchasePlanName() {
		return purchasePlanName;
	}

	public void setPurchasePlanName(String purchasePlanName) {
		this.purchasePlanName = purchasePlanName;
	}


	@Column(name = "purchase_execute_uuid")
	public String getPurchaseExecuteUuid() {
		return purchaseExecuteUuid;
	}

	public void setPurchaseExecuteUuid(String purchaseExecuteUuid) {
		this.purchaseExecuteUuid = purchaseExecuteUuid;
	}


	@Column(name = "purchase_method")
	public String getPurchaseMethod() {
		return purchaseMethod;
	}

	public void setPurchaseMethod(String purchaseMethod) {
		this.purchaseMethod = purchaseMethod;
	}


	@Column(name = "bid_evaluation_method")
	public String getBidEvaluationMethod() {
		return bidEvaluationMethod;
	}

	public void setBidEvaluationMethod(String bidEvaluationMethod) {
		this.bidEvaluationMethod = bidEvaluationMethod;
	}


	@Column(name = "check_way")
	public String getCheckWay() {
		return checkWay;
	}

	public void setCheckWay(String checkWay) {
		this.checkWay = checkWay;
	}




	@Column(name = "request_calibration_time")
	public Date getRequestCalibrationTime() {
		return requestCalibrationTime;
	}

	public void setRequestCalibrationTime(Date requestCalibrationTime) {
		this.requestCalibrationTime = requestCalibrationTime;
	}

	@Column(name = "inv_supplier")
	public String getInvSupplier() {
		return invSupplier;
	}

	public void setInvSupplier(String invSupplier) {
		this.invSupplier = invSupplier;
	}

	@Transient
	public String getCanSupplier() {
		return canSupplier;
	}

	public void setCanSupplier(String canSupplier) {
		this.canSupplier = canSupplier;
	}
    @Column(name = "catalogid")
	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	@Column(name = "curDocId", length = 64)
	public String getCurDocId() {
		return this.curDocId;
	}

	public void setCurDocId(String curDocId) {
		this.curDocId = curDocId;
	}


	@Column(name = "project_code")
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	@Column(name = "project_name")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "purchase_apply_code")
	public String getPurchaseApplyCode() {
		return purchaseApplyCode;
	}

	public void setPurchaseApplyCode(String purchaseApplyCode) {
		this.purchaseApplyCode = purchaseApplyCode;
	}

	@Column(name = "purchase_apply_name")
	public String getPurchaseApplyName() {
		return purchaseApplyName;
	}

	public void setPurchaseApplyName(String purchaseApplyName) {
		this.purchaseApplyName = purchaseApplyName;
	}

	@Column(name = "purchase_execute_name")
	public String getExecutePeoName() {
		return executePeoName;
	}

	public void setExecutePeoName(String executePeoName) {
		this.executePeoName = executePeoName;
	}

	@Column(name = "tec_review_name")
	public String getTechnologyReviewerName() {
		return technologyReviewerName;
	}

	public void setTechnologyReviewerName(String technologyReviewerName) {
		this.technologyReviewerName = technologyReviewerName;
	}

	@Column(name = "tec_review_uuid")
	public String getTechnologyReviewerUuid() {
		return technologyReviewerUuid;
	}

	public void setTechnologyReviewerUuid(String technologyReviewerUuid) {
		this.technologyReviewerUuid = technologyReviewerUuid;
	}

	@Column(name = "purchase_type")
	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	@Column(name = "review_method")
	public String getReviewMethod() {
		return reviewMethod;
	}

	public void setReviewMethod(String reviewMethod) {
		this.reviewMethod = reviewMethod;
	}
	@Column(name = "request_Approach_Time")
	public Date getRequestApproachTime() {
		return requestApproachTime;
	}

	public void setRequestApproachTime(Date requestApproachTime) {
		this.requestApproachTime = requestApproachTime;
	}

	/**
	 * @return the projectNode
	 */
	public String getProjectNode() {
		return projectNode;
	}

	/**
	 * @param projectNode the projectNode to set
	 */
	public void setProjectNode(String projectNode) {
		this.projectNode = projectNode;
	}

	
}
