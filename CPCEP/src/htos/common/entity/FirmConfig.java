package htos.common.entity;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


/**
 * FirmConfig entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ht_firm_config")

public class FirmConfig  implements java.io.Serializable {
    // Fields    

     private String uuid;
     private String firmName;
     private String cssType;


    // Constructors

    /** default constructor */
    public FirmConfig() {
    }

    
    /** full constructor */
    public FirmConfig(String firmName, String cssType) {
        this.firmName = firmName;
        this.cssType = cssType;
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
    
    @Column(name="firmName", length=200)

    public String getFirmName() {
        return this.firmName;
    }
    
    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }
    
    @Column(name="cssType", length=2)

    public String getCssType() {
        return this.cssType;
    }
    
    public void setCssType(String cssType) {
        this.cssType = cssType;
    }
   








}