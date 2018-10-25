package htos.coresys.entity;
// default package

import htos.coresys.entity.BaseEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**
 * BaseWorkFlow entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ht_workflow_info"
)

public class BaseWorkFlow extends BaseEntity implements java.io.Serializable {
     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String wfTacheName;
    private String wfInitiator;
     private String wfCurNodeId;
     private String wfPreNodeId;
     private String wfPreUser;
     private String wfPreUserId;
     private String wfMsgUser;
     private String isReject;
     private String wfWaitApproval;
     private String wfFinishApproval;
     private String allUser;
     private String wfRouterId;
     private String wfDocLink;
     private String wfFlowLogXml;
     private String wfProcessXml;
     private String wfGraphXml;
     private Date subTime;
     private String subPerson;
     private String tempUserName;
     private String docId;
     private String userId;
     private String msgTitle;
     private String workFlowId;
     private Integer approvalOrder;
     private String alreadyUser;
     private String curReadyUser;


    // Constructors

    /** default constructor */
    public BaseWorkFlow() {
    }
    
    
    
    @Column(name="wfInitiator", length=64)
    public String getWfInitiator() {
		return wfInitiator;
	}




	public void setWfInitiator(String wfInitiator) {
		this.wfInitiator = wfInitiator;
	}
    
    @Column(name="wfTacheName", length=64)
    public String getWfTacheName() {
        return this.wfTacheName;
    }
    
    public void setWfTacheName(String wfTacheName) {
        this.wfTacheName = wfTacheName;
    }
    
    @Column(name="wfCurNodeID", length=64)

    public String getWfCurNodeId() {
        return this.wfCurNodeId;
    }
    
    public void setWfCurNodeId(String wfCurNodeId) {
        this.wfCurNodeId = wfCurNodeId;
    }
    
    @Column(name="wfPreNodeID", length=64)

    public String getWfPreNodeId() {
        return this.wfPreNodeId;
    }
    
    public void setWfPreNodeId(String wfPreNodeId) {
        this.wfPreNodeId = wfPreNodeId;
    }
    
    @Column(name="wfPreUser", length=64)

    public String getWfPreUser() {
        return this.wfPreUser;
    }
    
    public void setWfPreUser(String wfPreUser) {
        this.wfPreUser = wfPreUser;
    }
    
    @Column(name="wfWaitApproval", length=256)

    public String getWfWaitApproval() {
        return this.wfWaitApproval;
    }
    
    public void setWfWaitApproval(String wfWaitApproval) {
        this.wfWaitApproval = wfWaitApproval;
    }
    
    @Column(name="wfFinishApproval", length=256)

    public String getWfFinishApproval() {
        return this.wfFinishApproval;
    }
    
    public void setWfFinishApproval(String wfFinishApproval) {
        this.wfFinishApproval = wfFinishApproval;
    }
    
    @Column(name="allUser", length=256)

    public String getAllUser() {
        return this.allUser;
    }
    
    public void setAllUser(String allUser) {
        this.allUser = allUser;
    }
    
    @Column(name="wfRouterID", length=256)

    public String getWfRouterId() {
        return this.wfRouterId;
    }
    
    public void setWfRouterId(String wfRouterId) {
        this.wfRouterId = wfRouterId;
    }
    
    @Column(name="wfDocLink", length=64)

    public String getWfDocLink() {
        return this.wfDocLink;
    }
    
    public void setWfDocLink(String wfDocLink) {
        this.wfDocLink = wfDocLink;
    }
    
    @Column(name="wfFlowLogXML", length=65535)

    public String getWfFlowLogXml() {
        return this.wfFlowLogXml;
    }
    
    public void setWfFlowLogXml(String wfFlowLogXml) {
        this.wfFlowLogXml = wfFlowLogXml;
    }
    
    @Column(name="wfProcessXML", length=65535)

    public String getWfProcessXml() {
        return this.wfProcessXml;
    }
    
    public void setWfProcessXml(String wfProcessXml) {
        this.wfProcessXml = wfProcessXml;
    }
    
    @Column(name="wfGraphXML", length=65535)

    public String getWfGraphXml() {
        return this.wfGraphXml;
    }
    
    public void setWfGraphXml(String wfGraphXml) {
        this.wfGraphXml = wfGraphXml;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="subTime", length=10)

    public Date getSubTime() {
        return this.subTime;
    }
    
    public void setSubTime(Date subTime) {
        this.subTime = subTime;
    }
    
    @Column(name="subPerson", length=64)

    public String getSubPerson() {
        return this.subPerson;
    }
    
    public void setSubPerson(String subPerson) {
        this.subPerson = subPerson;
    }
    
    @Column(name="tempUserName", length=64)

    public String getTempUserName() {
        return this.tempUserName;
    }
    
    public void setTempUserName(String tempUserName) {
        this.tempUserName = tempUserName;
    }
    
    @Column(name="docId", length=64)

    public String getDocId() {
        return this.docId;
    }
    
    public void setDocId(String docId) {
        this.docId = docId;
    }
    
    @Column(name="userId", length=1000)

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    @Column(name="msgTitle", length=500)

    public String getMsgTitle() {
        return this.msgTitle;
    }
    
    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }
    
    @Column(name="workFlowId", length=64)

    public String getWorkFlowId() {
        return this.workFlowId;
    }
    
    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }



	public String getWfPreUserId() {
		return wfPreUserId;
	}



	public void setWfPreUserId(String wfPreUserId) {
		this.wfPreUserId = wfPreUserId;
	}



	public String getWfMsgUser() {
		return wfMsgUser;
	}



	public void setWfMsgUser(String wfMsgUser) {
		this.wfMsgUser = wfMsgUser;
	}



	public String getIsReject() {
		return isReject;
	}



	public void setIsReject(String isReject) {
		this.isReject = isReject;
	}


	public Integer getApprovalOrder() {
		return approvalOrder;
	}



	public void setApprovalOrder(Integer approvalOrder) {
		this.approvalOrder = approvalOrder;
	}



	public String getAlreadyUser() {
		return alreadyUser;
	}



	public void setAlreadyUser(String alreadyUser) {
		this.alreadyUser = alreadyUser;
	}



	public String getCurReadyUser() {
		return curReadyUser;
	}



	public void setCurReadyUser(String curReadyUser) {
		this.curReadyUser = curReadyUser;
	}

	
}