package htos.coresys.entity;
// default package

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
 * DocNumber entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ht_right_number")

public class DocNumber  implements java.io.Serializable {


    // Fields    

     private String uuid;
     private Date createDate;
     private String modeName;
     private String year;
     private Integer docNo;


    // Constructors

    /** default constructor */
    public DocNumber() {
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
    
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	public Date getCreateDate() {
		return this.createDate;
	}
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    @Column(name="modeName", length=100)

    public String getModeName() {
        return this.modeName;
    }
    
    public void setModeName(String modeName) {
        this.modeName = modeName;
    }
    
    @Column(name="docNo")

    public Integer getDocNo() {
        return this.docNo;
    }
    
    public void setDocNo(Integer docNo) {
        this.docNo = docNo;
    }

    @Column(name="year")
	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}
   








}