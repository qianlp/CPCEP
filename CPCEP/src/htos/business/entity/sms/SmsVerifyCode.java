package htos.business.entity.sms;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author qinj
 * @date 2018-05-09 23:19
 **/
@Entity
@Table(name = "bs_sms_verify_code")
public class SmsVerifyCode {

	private String id;
	private String code;
	private Date sendTime;
	private String mobile;
	private Date validTime;
	private boolean useFlag;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 64)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Basic
	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Basic
	@Column(name = "send_time")
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@Basic
	@Column(name = "mobile")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Basic
	@Column(name = "valid_time")
	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	@Basic
	@Column(name = "use_flag")
	public boolean isUseFlag() {
		return useFlag;
	}

	public void setUseFlag(boolean useFlag) {
		this.useFlag = useFlag;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SmsVerifyCode that = (SmsVerifyCode) o;

		if (useFlag != that.useFlag) return false;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (code != null ? !code.equals(that.code) : that.code != null) return false;
		if (sendTime != null ? !sendTime.equals(that.sendTime) : that.sendTime != null) return false;
		if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
		if (validTime != null ? !validTime.equals(that.validTime) : that.validTime != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (code != null ? code.hashCode() : 0);
		result = 31 * result + (sendTime != null ? sendTime.hashCode() : 0);
		result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
		result = 31 * result + (validTime != null ? validTime.hashCode() : 0);
		result = 31 * result + (useFlag ? 1 : 0);
		return result;
	}
}
