package htos.coresys.entity;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * RoleType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ht_right_role_type"
    
)

public class RoleType extends BaseEntity {


    // Fields    

	private static final long serialVersionUID = 1L;
     private String roleTypeName;

    public RoleType() {
    }

	/** minimal constructor */
    public RoleType(String roleTypeName) {
        this.roleTypeName = roleTypeName;
    }
    
   
    @Column(name="role_type_name", nullable=false, length=32)

    public String getRoleTypeName() {
        return this.roleTypeName;
    }
    
    public void setRoleTypeName(String roleTypeName) {
        this.roleTypeName = roleTypeName;
    }


}