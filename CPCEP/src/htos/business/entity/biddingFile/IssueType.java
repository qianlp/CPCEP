package htos.business.entity.biddingFile;

import htos.coresys.entity.BaseEntity;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 */
@Entity
@Table(name = "bs_bidding_issue_type")
public class IssueType extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String catalogId;
    private String issueType;

    @Column(name = "catalogId")
    public String getCatalogId() {
        return this.catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    @Column(name = "issue_type")
    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }
}