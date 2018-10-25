package htos.coresys.entity;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


/**
 * PrtRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ht_right_prt_role")

public class PrtRole  implements java.io.Serializable {


    // Fields    

     private String uuid;
     private String roleId;
     private String prtRoleId;


    // Constructors

    /** default constructor */
    public PrtRole() {
    }

    
    /** full constructor */
    public PrtRole(String roleId, String prtRoleId) {
        this.roleId = roleId;
        this.prtRoleId = prtRoleId;
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
    
    @Column(name="role_id", length=64)

    public String getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    
    @Column(name="prt_role_id", length=64)

    public String getPrtRoleId() {
        return this.prtRoleId;
    }
    
    public void setPrtRoleId(String prtRoleId) {
        this.prtRoleId = prtRoleId;
    }
   








}