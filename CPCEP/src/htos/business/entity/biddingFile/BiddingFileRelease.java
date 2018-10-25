package htos.business.entity.biddingFile;

import htos.business.entity.bulletin.BiddingBulletin;
import htos.business.entity.procurement.PurchasePlan;
import htos.coresys.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "bs_bidding_file")
public class BiddingFileRelease extends BaseEntity {

    private String catalogId;
    private String name;
    private String code;
    private String fileName;
    private String version;
    private String bulletinUuid;
    private BiddingBulletin biddingBulletin;
    private String teachFile;
    private String businessFile;
    private String overallFile;
	private Short status;		// 开标状态，0 未开标，1 开标中，2 开标结束
	private String planId;
   
	private String clarifyStatus;
	@Column(name = "status")
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

    @Column(name = "catalogId")
    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @Column(name = "fileName")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    @Column(name = "version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Column(name = "bulletin_uuid")
    public String getBulletinUuid() {
        return bulletinUuid;
    }

    public void setBulletinUuid(String bulletinUuid) {
        this.bulletinUuid = bulletinUuid;
    }

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = BiddingBulletin.class)
    @JoinColumn(name = "bulletin_uuid",referencedColumnName="uuid" ,insertable = false, updatable = false)
    public BiddingBulletin getBiddingBulletin() {
        return biddingBulletin;
    }

    public void setBiddingBulletin(BiddingBulletin biddingBulletin) {
        this.biddingBulletin = biddingBulletin;
    }


    @Column(name = "teach_file")
    public String getTeachFile() {
        return teachFile;
    }

    public void setTeachFile(String teachFile) {
        this.teachFile = teachFile;
    }

    @Column(name = "business_file")
    public String getBusinessFile() {
        return businessFile;
    }

    public void setBusinessFile(String businessFile) {
        this.businessFile = businessFile;
    }

    @Column(name = "overall_file")
    public String getOverallFile() {
        return overallFile;
    }

    public void setOverallFile(String overallFile) {
        this.overallFile = overallFile;
    }

	/**
	 * @return the planId
	 */
	public String getPlanId() {
		return planId;
	}

	/**
	 * @param planId the planId to set
	 */
	public void setPlanId(String planId) {
		this.planId = planId;
	}
     
	@Transient
	public String getClarifyStatus() {
		return clarifyStatus;
	}

	public void setClarifyStatus(String clarifyStatus) {
		this.clarifyStatus = clarifyStatus;
	}
    
    
}
