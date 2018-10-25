package htos.coresys.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 职能
 */
@Entity
@Table(name = "ht_right_postion")
public class Postion extends BaseEntity implements java.io.Serializable {

	// Fields

	private String posName;
	private String posNo;
	private String oldPosName;
	private String oldPosNo;
	

	// Constructors

	/** default constructor */
	public Postion() {
	}

	/** minimal constructor */
	public Postion(String posName) {
		this.posName = posName;
	}

	


	@Column(name = "pos_name", length = 500)
	public String getPosName() {
		return this.posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}
	
	
	@Column(name = "pos_no")
	public String getPosNo() {
		return posNo;
	}

	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}

	@Transient
	public String getOldPosName() {
		return oldPosName;
	}

	public void setOldPosName(String oldPosName) {
		this.oldPosName = oldPosName;
	}

	@Transient
	public String getOldPosNo() {
		return oldPosNo;
	}

	public void setOldPosNo(String oldPosNo) {
		this.oldPosNo = oldPosNo;
	}

	
	
}