package htos.business.entity.bid;

import htos.business.entity.supplier.SupplierAttachment;
import htos.coresys.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "bs_tech_bid_eval")
public class TechBidEval extends BaseEntity{
	private String catalogId;
	private String biddingFileUuid;
	private String schemeComp;			//投标技术方案比较
	private String schemeCompFileb;		//投标技术方案比较文件id
	private String supplyScopeDescribe;	//供货范围(描述)
	private String supplyScope;			//供货范围(描述)id
	private String businessProb;		//与商务有关的问题
	private String businessProbFilec;	//与商务有关的问题文件id
	private String otherProb;			//其他补充问题
	private String otherProbFiled;		//其他补充问题文件id
	private String conclusion;			//结论
	private String conclusionFilee;		//结论文件id
	private SupplierAttachment supplyScopeFile;
   
	private String curDocId;
	@Basic
	@Column(name = "catalogId")
	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	@Basic
	@Column(name = "bidding_file_uuid")
	public String getBiddingFileUuid() {
		return biddingFileUuid;
	}

	public void setBiddingFileUuid(String biddingFileUuid) {
		this.biddingFileUuid = biddingFileUuid;
	}

	@Basic
	@Column(name = "scheme_comp")
	public String getSchemeComp() {
		return schemeComp;
	}

	public void setSchemeComp(String schemeComp) {
		this.schemeComp = schemeComp;
	}

	@Basic
	@Column(name = "supply_scope")
	public String getSupplyScope() {
		return supplyScope;
	}

	public void setSupplyScope(String supplyScope) {
		this.supplyScope = supplyScope;
	}

	@Basic
	@Column(name = "business_prob")
	public String getBusinessProb() {
		return businessProb;
	}

	public void setBusinessProb(String businessProb) {
		this.businessProb = businessProb;
	}

	@Basic
	@Column(name = "other_prob")
	public String getOtherProb() {
		return otherProb;
	}

	public void setOtherProb(String otherProb) {
		this.otherProb = otherProb;
	}

	@Basic
	@Column(name = "conclusion")
	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	@OneToOne(fetch = FetchType.LAZY,targetEntity = SupplierAttachment.class)
	@JoinColumn(name = "supply_scope",referencedColumnName="uuid" ,insertable = false, updatable = false)
	public SupplierAttachment getSupplyScopeFile() {
		return supplyScopeFile;
	}

	public void setSupplyScopeFile(SupplierAttachment supplyScopeFile) {
		this.supplyScopeFile = supplyScopeFile;
	}

	@Basic
	@Column(name = "supply_scope_describe")
	public String getSupplyScopeDescribe() {
		return supplyScopeDescribe;
	}

	public void setSupplyScopeDescribe(String supplyScopeDescribe) {
		this.supplyScopeDescribe = supplyScopeDescribe;
	}

	
	@Basic
	@Column(name = "scheme_comp_fileb")
	public String getSchemeCompFileb() {
		return schemeCompFileb;
	}

	public void setSchemeCompFileb(String schemeCompFileb) {
		this.schemeCompFileb = schemeCompFileb;
	}

	@Basic
	@Column(name = "business_prob_filec")
	public String getBusinessProbFilec() {
		return businessProbFilec;
	}

	public void setBusinessProbFilec(String businessProbFilec) {
		this.businessProbFilec = businessProbFilec;
	}
	
	@Basic
	@Column(name = "other_prob_filed")
	public String getOtherProbFiled() {
		return otherProbFiled;
	}

	public void setOtherProbFiled(String otherProbFiled) {
		this.otherProbFiled = otherProbFiled;
	}

	@Basic
	@Column(name = "conclusion_filee")
	public String getConclusionFilee() {
		return conclusionFilee;
	}

	public void setConclusionFilee(String conclusionFilee) {
		this.conclusionFilee = conclusionFilee;
	}
	
	@Basic
	@Column(name = "curDocId")
	public String getCurDocId() {
		return curDocId;
	}

	public void setCurDocId(String curDocId) {
		this.curDocId = curDocId;
	}
	
	
}
