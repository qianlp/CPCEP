package htos.business.entity.bid;

import htos.coresys.entity.BaseEntity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * BsNatureEnterprise entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bs_nature_enterprise", catalog = "cpcep")
public class BsNatureEnterprise extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1799765274514232033L;
	// Fields

	private String enterpriseName;

	// Constructors

	/** default constructor */
	public BsNatureEnterprise() {
	}

	/** full constructor */
	public BsNatureEnterprise( String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	@Column(name = "enterprise_name", length = 64)
	public String getEnterpriseName() {
		return this.enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

}