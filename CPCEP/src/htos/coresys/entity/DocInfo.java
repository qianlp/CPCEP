package htos.coresys.entity;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


/**
 * DocInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ht_right_doc_info")

public class DocInfo  implements java.io.Serializable {


    // Fields    

	 private static final long serialVersionUID = 1L;
     private String uuid;
     private String docId;
     private String userId;
     private String createDeptId;
     private String createDeptName;
     private String posNo;
     private String posName;


    // Constructors

    /** default constructor */
    public DocInfo() {
    }

    
    /** full constructor */
    public DocInfo(String docId, String userId, String createDeptId, String createDeptName) {
        this.docId = docId;
        this.userId = userId;
        this.createDeptId = createDeptId;
        this.createDeptName = createDeptName;
    }

   
    // Property accessors
    @GenericGenerator(name="generator", strategy="uuid.hex")@Id @GeneratedValue(generator="generator")
    
    @Column(name="uuid", unique=true, nullable=false, length=64)

    public String getUuid() {
        return this.uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    @Column(name="docId", length=64)

    public String getDocId() {
        return this.docId;
    }
    
    public void setDocId(String docId) {
        this.docId = docId;
    }
    
    @Column(name="userId", length=64)

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    @Column(name="createDeptId", length=64)

    public String getCreateDeptId() {
        return this.createDeptId;
    }
    
    public void setCreateDeptId(String createDeptId) {
        this.createDeptId = createDeptId;
    }
    
    @Column(name="createDeptName", length=500)

    public String getCreateDeptName() {
        return this.createDeptName;
    }
    
    public void setCreateDeptName(String createDeptName) {
        this.createDeptName = createDeptName;
    }


	public String getPosNo() {
		return posNo;
	}


	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}


	public String getPosName() {
		return posName;
	}


	public void setPosName(String posName) {
		this.posName = posName;
	}
   








}