package htos.business.entity.bid;

import htos.business.entity.supplier.SupplierAttachment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "bs_teach_clarify")
public class TechEvalClarify {

    private String uuid;
    private String supName;
    private String supUUID;
    private String contact;
    private String issueType;
    private String bidCode;
    private String bidName;
    private String version;
    private String feedBack;
    private String remark;
    private String clarifyFileUUID;
    private String feedBackFileUUID;
    //项目ID
    private String bidFileUUID;
    
  /*  private SupplierAttachment feedBackFileAttachment;
    private SupplierAttachment clarifyFileAttachment;*/
    public TechEvalClarify() {
    }

    public TechEvalClarify(String uuid, String supName, String supUUID, String contact, String issueType, String bidCode, String bidName, String version, String feedBack, String remark, String clarifyFileUUID) {
        this.uuid = uuid;
        this.supName = supName;
        this.supUUID = supUUID;
        this.contact = contact;
        this.issueType = issueType;
        this.bidCode = bidCode;
        this.bidName = bidName;
        this.version = version;
        this.feedBack = feedBack;
        this.remark = remark;
        this.clarifyFileUUID = clarifyFileUUID;
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

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "sup_name")
    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    @Column(name = "sup_uuid")
    public String getSupUUID() {
        return supUUID;
    }

    public void setSupUUID(String supUUID) {
        this.supUUID = supUUID;
    }

    @Column(name = "contact")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Column(name = "issue_type")
    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    @Column(name = "bid_code")
    public String getBidCode() {
        return bidCode;
    }

    public void setBidCode(String bidCode) {
        this.bidCode = bidCode;
    }

    @Column(name = "bid_name")
    public String getBidName() {
        return bidName;
    }

    public void setBidName(String bidName) {
        this.bidName = bidName;
    }

    @Column(name = "version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Column(name = "feed_back")
    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    @Column(name = "clarify_file_uuid")
    public String getClarifyFileUUID() {
        return clarifyFileUUID;
    }

    public void setClarifyFileUUID(String clarifyFileUUID) {
        this.clarifyFileUUID = clarifyFileUUID;
    }

    @Column(name = "feed_back_file_uuid")
    public String getFeedBackFileUUID() {
        return feedBackFileUUID;
    }

    public void setFeedBackFileUUID(String feedBackFileUUID) {
        this.feedBackFileUUID = feedBackFileUUID;
    }
    @Column(name = "bid_file_uuid")
	public String getBidFileUUID() {
		return bidFileUUID;
	}

	public void setBidFileUUID(String bidFileUUID) {
		this.bidFileUUID = bidFileUUID;
	}
	  /*  @ManyToOne(fetch = FetchType.LAZY, targetEntity = SupplierAttachment.class)
	    @JoinColumn(name = "feed_back_file_uuid", referencedColumnName = "uuid", insertable = false, updatable = false)
	    public SupplierAttachment getFeedBackFileAttachment() {
	        return feedBackFileAttachment;
	    }

	    public void setFeedBackFileAttachment(SupplierAttachment feedBackFileAttachment) {
	        this.feedBackFileAttachment = feedBackFileAttachment;
	    }
	    @ManyToOne(fetch = FetchType.LAZY, targetEntity = SupplierAttachment.class)
	    @JoinColumn(name = "clarify_file_uuid", referencedColumnName = "uuid", insertable = false, updatable = false)
		public SupplierAttachment getClarifyFileAttachment() {
			return clarifyFileAttachment;
		}

		public void setClarifyFileAttachment(SupplierAttachment clarifyFileAttachment) {
			this.clarifyFileAttachment = clarifyFileAttachment;
		}*/

	    
}
