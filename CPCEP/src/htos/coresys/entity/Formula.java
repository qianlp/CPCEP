package htos.coresys.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 公式
 */
@Entity
@Table(name = "ht_right_formula")
public class Formula extends BaseEntity implements java.io.Serializable {

	
	private String typeName;
	private String address;

	// Constructors

	/** default constructor */
	public Formula() {
	}

	

	

	@Column(name = "typeName", length = 200)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "address", length = 500)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
}