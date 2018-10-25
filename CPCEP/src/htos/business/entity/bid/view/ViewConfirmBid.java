package htos.business.entity.bid.view;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author qinj
 * @date 2018-05-27 19:13
 **/
public class ViewConfirmBid {

	private String name;		// 招标名称
	private String code;		// 招标编号
	private String bidId;		// 所属招标文件
	private Date bidStartTime;	// 投标开始时间
	private Date bidEndTime;	// 投标结束时间
	private Date bidOpenTime;	// 开标时间
	private String confirmId;
	private String version;		// 招标文件版本号
	private String thirdTotalPrice;
	private String status;
	private int rank;
	private String biddingId;//投标id
	private Timestamp confirmTime;//定标时间
	private String supplierId;//供应商id
	private String message;//中标通知信息
	private String purchaseApplyUuid;
	private String finalPrice;

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getThirdTotalPrice() {
		return thirdTotalPrice;
	}

	public void setThirdTotalPrice(String thirdTotalPrice) {
		this.thirdTotalPrice = thirdTotalPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getBidStartTime() {
		return bidStartTime;
	}

	public void setBidStartTime(Date bidStartTime) {
		this.bidStartTime = bidStartTime;
	}

	public Date getBidEndTime() {
		return bidEndTime;
	}

	public void setBidEndTime(Date bidEndTime) {
		this.bidEndTime = bidEndTime;
	}

	public Date getBidOpenTime() {
		return bidOpenTime;
	}

	public void setBidOpenTime(Date bidOpenTime) {
		this.bidOpenTime = bidOpenTime;
	}

	public String getConfirmId() {
		return confirmId;
	}

	public void setConfirmId(String confirmId) {
		this.confirmId = confirmId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBidId() {
		return bidId;
	}

	public void setBidId(String bidId) {
		this.bidId = bidId;
	}

	/**
	 * 是否定标
	 */
	public Boolean getConfirm() {
		return StringUtils.isNotBlank(this.confirmId);
	}

	public String getBiddingId() {
		return biddingId;
	}

	public void setBiddingId(String biddingId) {
		this.biddingId = biddingId;
	}

	public Timestamp getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Timestamp confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPurchaseApplyUuid() {
		return purchaseApplyUuid;
	}

	public void setPurchaseApplyUuid(String purchaseApplyUuid) {
		this.purchaseApplyUuid = purchaseApplyUuid;
	}

	public String getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(String finalPrice) {
		this.finalPrice = finalPrice;
	}
}
