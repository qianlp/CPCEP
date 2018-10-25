package htos.sysfmt.message.entity;
// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;


/**
 * MsgInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ht_message_msg_info")

public class MsgInfo  implements java.io.Serializable {


    // Fields    

     private String uuid;
     private String msgTitle;
     private String createBy;
     private Date createDate;
     private String userId;
     private String menuId;
     private String docId;//文档ID（如果为空则新建，不为空则查询）
     private Integer msgType;
     private Integer status;
     private String remark;
     private String docBusinessId;
     
     private String startTime;
     private String taskId;
     private String taskName;
     
     private String biddingFileUuid;

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "uuid", unique = true, nullable = false, length = 64)
    public String getUuid() {
        return this.uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    @Column(name="msgTitle", length=200)

    public String getMsgTitle() {
        return this.msgTitle;
    }
    
    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }
    
    @Column(name="create_by", length=50)

    public String getCreateBy() {
        return this.createBy;
    }
    
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_date", length=19)

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    
    @Column(name="menuId", length=64)

    public String getMenuId() {
        return this.menuId;
    }
    
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
    
    @Column(name="docId", length=64)

    public String getDocId() {
        return this.docId;
    }
    
    public void setDocId(String docId) {
        this.docId = docId;
    }
    
    @Column(name="msgType")

    public Integer getMsgType() {
        return this.msgType;
    }
    
    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

	/**
	 * @return the userId
	 */
    @Column(name="userId", length=64)
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="docBusinessId")
	public String getDocBusinessId() {
		return docBusinessId;
	}

	public void setDocBusinessId(String docBusinessId) {
		this.docBusinessId = docBusinessId;
	}

	@Transient
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(name="taskId")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Column(name="taskName")
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	@Column(name="bidding_File_Uuid")
	public String getBiddingFileUuid() {
		return biddingFileUuid;
	}

	public void setBiddingFileUuid(String biddingFileUuid) {
		this.biddingFileUuid = biddingFileUuid;
	}
   
	

}