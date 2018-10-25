package htos.business.entity.bid;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author qinj
 * @date 2018-05-27 15:44
 **/
@Entity
@Table(name = "bs_bidding_confirm")
public class BiddingConfirm {
	private String uuid;
	private String biddingFile;
	private String biddingId;
	private Timestamp confirmTime;
	private String supplierId;
	private String message;

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

	@Basic
	@Column(name = "bidding_file")
	public String getBiddingFile() {
		return biddingFile;
	}

	public void setBiddingFile(String biddingFile) {
		this.biddingFile = biddingFile;
	}

	@Basic
	@Column(name = "bidding_id")
	public String getBiddingId() {
		return biddingId;
	}

	public void setBiddingId(String biddingId) {
		this.biddingId = biddingId;
	}

	@Basic
	@Column(name = "confirm_time")
	public Timestamp getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Timestamp confirmTime) {
		this.confirmTime = confirmTime;
	}

	@Basic
	@Column(name = "supplier_id")
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	@Basic
	@Column(name = "message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BiddingConfirm that = (BiddingConfirm) o;

		if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
		if (biddingFile != null ? !biddingFile.equals(that.biddingFile) : that.biddingFile != null) return false;
		if (biddingId != null ? !biddingId.equals(that.biddingId) : that.biddingId != null) return false;
		if (confirmTime != null ? !confirmTime.equals(that.confirmTime) : that.confirmTime != null) return false;
		if (supplierId != null ? !supplierId.equals(that.supplierId) : that.supplierId != null) return false;
		if (message != null ? !message.equals(that.message) : that.message != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = uuid != null ? uuid.hashCode() : 0;
		result = 31 * result + (biddingFile != null ? biddingFile.hashCode() : 0);
		result = 31 * result + (biddingId != null ? biddingId.hashCode() : 0);
		result = 31 * result + (confirmTime != null ? confirmTime.hashCode() : 0);
		result = 31 * result + (supplierId != null ? supplierId.hashCode() : 0);
		result = 31 * result + (message != null ? message.hashCode() : 0);
		return result;
	}
}
