package htos.business.dto;

public class BidFileWithProject {
	private String projectName;
	private String projectCode;
	private String purchaseName;
	private String purchaseMethod;

	public BidFileWithProject() {
	}

	public BidFileWithProject(String projectName, String projectCode, String purchaseName, String purchaseMethod) {
		this.projectName = projectName;
		this.projectCode = projectCode;
		this.purchaseName = purchaseName;
		this.purchaseMethod = purchaseMethod;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getPurchaseName() {
		return purchaseName;
	}

	public void setPurchaseName(String purchaseName) {
		this.purchaseName = purchaseName;
	}

	public String getPurchaseMethod() {
		return purchaseMethod;
	}

	public void setPurchaseMethod(String purchaseMethod) {
		this.purchaseMethod = purchaseMethod;
	}
}
