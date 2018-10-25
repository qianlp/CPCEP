package htos.business.entity.procurement;


import htos.coresys.entity.BaseEntity;
import htos.coresys.entity.BaseModel;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "bs_purchase_apply")
public class PurchaseApply extends BaseEntity implements java.io.Serializable{

	private String purchaseCode;
	private String purchaseName;
	private String prjID;
	private String projectCode;
	private String projectName;
	private String executePeoName;
	private String executePeoId;
	private String technologyReviewerId;
	private String technologyReviewerName;
	private String catalogId;
	private String curDocId;
	private String purchaseType;
	private String requiredTime;

	private String deviceString1;
	private String deviceString2;
	private String deviceString3;

	private List<PurchaseMaterial> deviceArr1 =new ArrayList<PurchaseMaterial>();
	private List<PurchaseMaterial> deviceArr2 =new ArrayList<PurchaseMaterial>();
	private List<PurchaseMaterial> deviceArr3 =new ArrayList<PurchaseMaterial>();

	private String delIds;

	@Column(name = "purchase_code")
	public String getPurchaseCode() {
		return purchaseCode;
	}

	public void setPurchaseCode(String purchaseCode) {
		this.purchaseCode = purchaseCode;
	}

	@Column(name = "purchase_name")
	public String getPurchaseName() {
		return purchaseName;
	}

	public void setPurchaseName(String purchaseName) {
		this.purchaseName = purchaseName;
	}

	@Column (name="project_uuid")
	public String getPrjID() {
		return prjID;
	}

	public void setPrjID(String prjID) {
		this.prjID = prjID;
	}

	@Column (name="project_code")
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	@Column (name="project_name")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column (name="execute_peo_name")
	public String getExecutePeoName() {
		return executePeoName;
	}

	public void setExecutePeoName(String executePeoName) {
		this.executePeoName = executePeoName;
	}

	@Column (name="execute_peo_id")
	public String getExecutePeoId() {
		return executePeoId;
	}

	public void setExecutePeoId(String executePeoId) {
		this.executePeoId = executePeoId;
	}

	@Column (name="technology_reviewer_id")
	public String getTechnologyReviewerId() {
		return technologyReviewerId;
	}

	public void setTechnologyReviewerId(String technologyReviewerId) {
		this.technologyReviewerId = technologyReviewerId;
	}

	@Column (name="technology_reviewer_name")
	public String getTechnologyReviewerName() {
		return technologyReviewerName;
	}

	public void setTechnologyReviewerName(String technologyReviewerName) {
		this.technologyReviewerName = technologyReviewerName;
	}

	@Column(name = "catalog_id")
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


	@Column(name = "purchase_info_type")
	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	@Transient
	public String getDeviceString1() {
		return deviceString1;
	}

	public void setDeviceString1(String deviceString1) {
		this.deviceString1 = deviceString1;
	}

	@Transient
	public String getDeviceString2() {
		return deviceString2;
	}

	public void setDeviceString2(String deviceString2) {
		this.deviceString2 = deviceString2;
	}

	@Transient
	public String getDeviceString3() {
		return deviceString3;
	}

	public void setDeviceString3(String deviceString3) {
		this.deviceString3 = deviceString3;
	}

	@Transient
	public List<PurchaseMaterial> getDeviceArr1() {
		return deviceArr1;
	}

	public void setDeviceArr1(List<PurchaseMaterial> deviceArr1) {
		this.deviceArr1 = deviceArr1;
	}

	@Transient
	public List<PurchaseMaterial> getDeviceArr2() {
		return deviceArr2;
	}

	public void setDeviceArr2(List<PurchaseMaterial> deviceArr2) {
		this.deviceArr2 = deviceArr2;
	}

	@Transient
	public List<PurchaseMaterial> getDeviceArr3() {
		return deviceArr3;
	}

	public void setDeviceArr3(List<PurchaseMaterial> deviceArr3) {
		this.deviceArr3 = deviceArr3;
	}

	@Column(name = "required_time")
	public String getRequiredTime() {
		return requiredTime;
	}

	public void setRequiredTime(String requiredTime) {
		this.requiredTime = requiredTime;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//
	//

	private boolean sameExecutePeo;

	@Transient
	public boolean isSameExecutePeo() {
		return sameExecutePeo;
	}

	public void setSameExecutePeo(boolean sameExecutePeo) {
		this.sameExecutePeo = sameExecutePeo;
	}

	@Transient
	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}
}
