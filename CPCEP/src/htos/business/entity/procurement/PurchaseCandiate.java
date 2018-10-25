package htos.business.entity.procurement;

import htos.coresys.entity.BaseEntity;
import javax.persistence.*;


@Entity
@Table(name = "bs_purchase_plan_candiate")
public class PurchaseCandiate extends BaseEntity implements java.io.Serializable{
    private static final long serialVersionUID = 1L;

    private String supplierId;
    private String supplierName;
    private String tecOffer;
    private String planId;
    private String busOffer;
    private String finalOffer;
    private String rank;
    private int    isWin;
    private Integer type;	 // 类型，1 邀请供应商；2 候选供应商

	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "supplier_id")
    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Column(name = "supplier_name")
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Column(name = "tec_offer")
    public String getTecOffer() {
        return tecOffer;
    }

    public void setTecOffer(String tecOffer) {
        this.tecOffer = tecOffer;
    }

    @Column(name = "bus_offer")
    public String getBusOffer() {
        return busOffer;
    }

    public void setBusOffer(String busOffer) {
        this.busOffer = busOffer;
    }

    @Column(name = "final_offer")
    public String getFinalOffer() {
        return finalOffer;
    }

    public void setFinalOffer(String finalOffer) {
        this.finalOffer = finalOffer;
    }

    @Column(name = "rank")
    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }



    @Column(name = "is_win")
    public int getIsWin() {
        return isWin;
    }

    public void setIsWin(int isWin) {
        this.isWin = isWin;
    }

    @Column(name = "plan_id")
    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    private String name;
	private String contacts;
	private String contactAddress;
	private String corporations;
	private String phon;

	@Transient
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@Transient
	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	@Transient
	public String getCorporations() {
		return corporations;
	}

	public void setCorporations(String corporations) {
		this.corporations = corporations;
	}

	@Transient
	public String getPhon() {
		return phon;
	}

	public void setPhon(String phon) {
		this.phon = phon;
	}
}
