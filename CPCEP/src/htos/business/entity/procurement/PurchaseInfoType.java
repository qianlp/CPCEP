package htos.business.entity.procurement;

import htos.coresys.entity.BaseEntity;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HtPurchaseInfoType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bs_purchase_info_type")
public class PurchaseInfoType extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String catalogId;
	private String purType;

	@Column(name = "catalogId")
	public String getCatalogId() {
		return this.catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	@Column(name = "purType")
	public String getPurType() {
		return this.purType;
	}

	public void setPurType(String purType) {
		this.purType = purType;
	}

}