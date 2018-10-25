package htos.business.entity.supplier;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author qinj
 * @date 2018-02-05 23:02
 **/
@Entity
@Table(name = "bs_supplier_ext")//供应商信息
public class SupplierExt {

	protected String uuid;

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

	private String account;
	private String name;
	private String registerAddress;
	private String taxpayerNo;
	private String corporations;
	private String nature;
	private String registerCapital;
	private Date registerTime;
	private String contacts;
	private String contactAddress;
	private String phon;
	private String mobile;
	private String fax;
	private String email;
	private String license;
	private String taxCertificate;
	private String orgCode;
	private String certificate;
	private String wfStatus;
	private String curUser;
	private Date createDate;
	private Boolean enableFlag;
	private Set<SupplierBilling> billings = new HashSet<SupplierBilling>();
	private Set<SupplierAttachment> attachements = new HashSet<SupplierAttachment>();
	private Set<SupplierGoodsScope> scopes = new HashSet<SupplierGoodsScope>();

	private String licenseFile;
	private String taxCertificateFile;
	private String orgCodeFile;
	private String certificateFile;

	private Date updateDate;
	private String materialName;
	private String materialId;

	@Column(name = "UPDATE_DATE")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "LICENSE_FILE")
	public String getLicenseFile() {
		return licenseFile;
	}

	public void setLicenseFile(String licenseFile) {
		this.licenseFile = licenseFile;
	}

	@Column(name = "CERTIFICATE_FILE")
	public String getCertificateFile() {
		return certificateFile;
	}

	public void setCertificateFile(String certificateFile) {
		this.certificateFile = certificateFile;
	}

	@Column(name = "ORG_CODE_FILE")
	public String getOrgCodeFile() {
		return orgCodeFile;
	}

	public void setOrgCodeFile(String orgCodeFile) {
		this.orgCodeFile = orgCodeFile;
	}

	@Column(name = "TAX_CERTIFICATE_FILE")
	public String getTaxCertificateFile() {
		return taxCertificateFile;
	}

	public void setTaxCertificateFile(String taxCertificateFile) {
		this.taxCertificateFile = taxCertificateFile;
	}

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "supplier")
	public Set<SupplierBilling> getBillings() {
		return billings;
	}

	public void setBillings(Set<SupplierBilling> billings) {
		this.billings = billings;
	}

	@Basic
	@Column(name = "ACCOUNT")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Basic
	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "REGISTER_ADDRESS")
	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

	@Basic
	@Column(name = "TAXPAYER_NO")
	public String getTaxpayerNo() {
		return taxpayerNo;
	}

	public void setTaxpayerNo(String taxpayerNo) {
		this.taxpayerNo = taxpayerNo;
	}

	@Basic
	@Column(name = "CORPORATIONS")
	public String getCorporations() {
		return corporations;
	}

	public void setCorporations(String corporations) {
		this.corporations = corporations;
	}

	@Basic
	@Column(name = "NATURE")
	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	@Basic
	@Column(name = "REGISTER_CAPITAL")
	public String getRegisterCapital() {
		return registerCapital;
	}

	public void setRegisterCapital(String registerCapital) {
		this.registerCapital = registerCapital;
	}

	@Basic
	@Column(name = "REGISTER_TIME")
	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	@Basic
	@Column(name = "CONTACTS")
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@Basic
	@Column(name = "CONTACT_ADDRESS")
	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	@Basic
	@Column(name = "PHON")
	public String getPhon() {
		return phon;
	}

	public void setPhon(String phon) {
		this.phon = phon;
	}

	@Basic
	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Basic
	@Column(name = "FAX")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Basic
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Basic
	@Column(name = "LICENSE")
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	@Basic
	@Column(name = "TAX_CERTIFICATE")
	public String getTaxCertificate() {
		return taxCertificate;
	}

	public void setTaxCertificate(String taxCertificate) {
		this.taxCertificate = taxCertificate;
	}

	@Basic
	@Column(name = "ORG_CODE")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Basic
	@Column(name = "CERTIFICATE")
	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	@Column(name = "wfStatus")
	public String getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}
	@Column(name = "curUser")
	public String getCurUser() {
		return curUser;
	}

	public void setCurUser(String curUser) {
		this.curUser = curUser;
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "supplier")
	public Set<SupplierAttachment> getAttachements() {
		return attachements;
	}

	public void setAttachements(Set<SupplierAttachment> attachements) {
		this.attachements = attachements;
	}

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "supplier")
	public Set<SupplierGoodsScope> getScopes() {
		return scopes;
	}

	public void setScopes(Set<SupplierGoodsScope> scopes) {
		this.scopes = scopes;
	}

	@Column(name = "enable_flag")
	public Boolean getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(Boolean enableFlag) {
		this.enableFlag = enableFlag;
	}

	@Column(name = "material_name")
	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	@Column(name = "material_id")
	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	

	
}
