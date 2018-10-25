package htos.business.entity.bid;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author qinj
 * @date 2018-04-15 15:41
 * 邀请竞价中招标办提交信息
 **/
@Entity
@Table(name = "bs_invitation")
public class Invitation {

	private String uuid;
	private String supplierBidUuid;
	private String downExtent;
	private String upExtent;
	private Date endDate;
	private String payType;
	private Date deliveryDate;
	private String description;
	private Short type;
	private Float minAmount;
	private Float price;
	private String supplierName;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "uuid", unique = true, nullable = false, length = 32)
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Basic
	@Column(name = "supplier_bid_uuid")
	public String getSupplierBidUuid() {
		return supplierBidUuid;
	}

	public void setSupplierBidUuid(String supplierBidUuid) {
		this.supplierBidUuid = supplierBidUuid;
	}

	@Basic
	@Column(name = "up_extent")
	public String getUpExtent() {
		return upExtent;
	}

	public void setUpExtent(String upExtent) {
		this.upExtent = upExtent;
	}

	@Basic
	@Column(name = "type")
	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Basic
	@Column(name = "down_extent")
	public String getDownExtent() {
		return downExtent;
	}

	public void setDownExtent(String downExtent) {
		this.downExtent = downExtent;
	}

	@Basic
	@Column(name = "end_date")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Basic
	@Column(name = "pay_type")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Basic
	@Column(name = "delivery_date")
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "min_amount")
	public Float getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(Float minAmount) {
		this.minAmount = minAmount;
	}

	@Column(name = "price")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//
	//

	private String name;
	private String invitationId;
	private String id;

	@Transient
	public String getInvitationId() {
		return invitationId;
	}

	public void setInvitationId(String invitationId) {
		this.invitationId = invitationId;
	}

	@Transient
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Transient
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
    @Transient
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
}
