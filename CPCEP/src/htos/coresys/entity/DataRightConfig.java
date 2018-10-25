package htos.coresys.entity;
// default package

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * DocNumber entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ht_right_data_config")

public class DataRightConfig  implements java.io.Serializable {


    // Fields    

     private String uuid;
     private String menuId;
     private String diyRight;
     private String rightType;
     private String diyPos;
     private String diyRole;
     private String diyUser;
     private String allPos;
     private String allRole;
     private String allUser;
	
     
    @GenericGenerator(name = "generator", strategy = "uuid.hex")
 	@Id
 	@GeneratedValue(generator = "generator")
 	@Column(name = "uuid", unique = true, nullable = false, length = 64)
 	public String getUuid() {
 		return uuid;
 	}

 	public void setUuid(String uuid) {
 		this.uuid = uuid;
 	}
	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}
	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	/**
	 * @return the diyRight
	 */
	public String getDiyRight() {
		return diyRight;
	}
	/**
	 * @param diyRight the diyRight to set
	 */
	public void setDiyRight(String diyRight) {
		this.diyRight = diyRight;
	}
	/**
	 * @return the diyPos
	 */
	public String getDiyPos() {
		return diyPos;
	}
	/**
	 * @param diyPos the diyPos to set
	 */
	public void setDiyPos(String diyPos) {
		this.diyPos = diyPos;
	}
	/**
	 * @return the diyRole
	 */
	public String getDiyRole() {
		return diyRole;
	}
	/**
	 * @param diyRole the diyRole to set
	 */
	public void setDiyRole(String diyRole) {
		this.diyRole = diyRole;
	}
	/**
	 * @return the diyUser
	 */
	public String getDiyUser() {
		return diyUser;
	}
	/**
	 * @param diyUser the diyUser to set
	 */
	public void setDiyUser(String diyUser) {
		this.diyUser = diyUser;
	}
	/**
	 * @return the allPos
	 */
	public String getAllPos() {
		return allPos;
	}
	/**
	 * @param allPos the allPos to set
	 */
	public void setAllPos(String allPos) {
		this.allPos = allPos;
	}
	/**
	 * @return the allRole
	 */
	public String getAllRole() {
		return allRole;
	}
	/**
	 * @param allRole the allRole to set
	 */
	public void setAllRole(String allRole) {
		this.allRole = allRole;
	}
	/**
	 * @return the allUser
	 */
	public String getAllUser() {
		return allUser;
	}
	/**
	 * @param allUser the allUser to set
	 */
	public void setAllUser(String allUser) {
		this.allUser = allUser;
	}

	/**
	 * @return the rightType
	 */
	public String getRightType() {
		return rightType;
	}

	/**
	 * @param rightType the rightType to set
	 */
	public void setRightType(String rightType) {
		this.rightType = rightType;
	}


     
}