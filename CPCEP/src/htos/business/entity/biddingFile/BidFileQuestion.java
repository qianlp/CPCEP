package htos.business.entity.biddingFile;

import htos.business.entity.supplier.SupplierAttachment;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bs_bid_file_question")
public class BidFileQuestion {

    private String uuid;
    private String bidCode;
    private String bidName;
    private String version;
    private String issueType;
    private String supName;
    private String supUUID;
    private String contact;
    private String remark;
    private String bidFileUUID;
    private Date createDate;
    private String feedBack;
    private String status;
    private String questionFileUUID;
    private SupplierAttachment questionFileAttachment;
    private String questionFileName;


    private String feedBackFileUUID;
    private SupplierAttachment feedBackFileAttachment;
    private String feedBackFileName;

    private String     isPublic ;
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

    @Column(name = "file_version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Column(name = "issue_type")
    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
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

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "bid_file_uuid")
    public String getBidFileUUID() {
        return bidFileUUID;
    }

    public void setBidFileUUID(String bidFileUUID) {
        this.bidFileUUID = bidFileUUID;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "feed_back")
    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "question_file_uuid")
    public String getQuestionFileUUID() {
        return questionFileUUID;
    }

    public void setQuestionFileUUID(String questionFileUUID) {
        this.questionFileUUID = questionFileUUID;
    }

    @Column(name = "feed_back_file_uuid")
    public String getFeedBackFileUUID() {
        return feedBackFileUUID;
    }

    public void setFeedBackFileUUID(String feedBackFileUUID) {
        this.feedBackFileUUID = feedBackFileUUID;
    }

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = SupplierAttachment.class)
    @JoinColumn(name = "question_file_uuid", referencedColumnName = "uuid", insertable = false, updatable = false)
    public SupplierAttachment getQuestionFileAttachment() {
        return questionFileAttachment;
    }

    public void setQuestionFileAttachment(SupplierAttachment questionFileAttachment) {
        this.questionFileAttachment = questionFileAttachment;
    }

    @Transient
    public String getQuestionFileName() {
        return questionFileName;
    }

    public void setQuestionFileName(String questionFileName) {
        this.questionFileName = questionFileName;
    }

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = SupplierAttachment.class)
    @JoinColumn(name = "feed_back_file_uuid", referencedColumnName = "uuid", insertable = false, updatable = false)
    public SupplierAttachment getFeedBackFileAttachment() {
        return feedBackFileAttachment;
    }

    public void setFeedBackFileAttachment(SupplierAttachment feedBackFileAttachment) {
        this.feedBackFileAttachment = feedBackFileAttachment;
    }

    @Transient
    public String getFeedBackFileName() {
        return feedBackFileName;
    }

    public void setFeedBackFileName(String feedBackFileName) {
        this.feedBackFileName = feedBackFileName;
    }

    @Column(name = "is_public")
    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }
}
