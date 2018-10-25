package htos.sysfmt.file.entity;
// default package

import htos.coresys.entity.BaseEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


/**
 * updateFileMsg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ht_file_update_info"
)

public class UpdateFileMsg  extends BaseEntity  implements java.io.Serializable {


    // Fields    
     private String projectNo;
     private String projectName;
     private String catalogId;
     private String curDocId;
     private String prjID;
     private String userId;
     private String msgStatus;
     private String warnBy;
     private String msgTitle;
     private String curFj;
     private String pid;
     private String fjName;


    // Constructors

    /** default constructor */
    public UpdateFileMsg() {
    }

	

    /**
	 * @return the fjName
	 */
	public String getFjName() {
		return fjName;
	}

	/**
	 * @param fjName the fjName to set
	 */
	public void setFjName(String fjName) {
		this.fjName = fjName;
	}

	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCurFj() {
		return curFj;
	}

	public void setCurFj(String curFj) {
		this.curFj = curFj;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	/**
	 * @return the msgStatus
	 */
	public String getMsgStatus() {
		return msgStatus;
	}

	/**
	 * @param msgStatus the msgStatus to set
	 */
	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}

	
    
    @Column(name="projectNo", nullable=false, length=64)

    public String getProjectNo() {
        return this.projectNo;
    }
    
    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }
    
    @Column(name="projectName", length=64)

    public String getProjectName() {
        return this.projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    @Column(name="catalogId", length=65535)

    public String getCatalogId() {
        return this.catalogId;
    }
    
    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }
    
    @Column(name="curDocId", length=64)

    public String getCurDocId() {
        return this.curDocId;
    }
    
    public void setCurDocId(String curDocId) {
        this.curDocId = curDocId;
    }
    
    @Column(name="prjID", length=64)

    public String getPrjID() {
        return this.prjID;
    }
    
    public void setPrjID(String prjID) {
        this.prjID = prjID;
    }
    
    @Column(name="userId", length=64)

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    
    @Column(name="warnBy", length=200)

    public String getWarnBy() {
        return this.warnBy;
    }
    
    public void setWarnBy(String warnBy) {
        this.warnBy = warnBy;
    }
   








}