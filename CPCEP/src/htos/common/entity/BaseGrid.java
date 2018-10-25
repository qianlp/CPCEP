package htos.common.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseGrid implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="uuid")
	private String uuid;
	@Column(name="pid")
	private String pid;
	@Column(name="_id")
	private String _id;
	
	@Column(name="_uid")
	private String _uid;
	
	@Column(name="remark")
	private String remark;
	@Transient
	private String _state;
	
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	@JSONField(name="remark")
	public String getRemark() {
		return remark;
	}
	@JSONField(name="remark")
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@JSONField(name="uuid")
	public String getUuid() {
		return uuid;
	}
	@JSONField(name="uuid")
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	@JSONField(name="_id")
	public String get_id() {
		return _id;
	}
	@JSONField(name="_id")
	public void set_id(String _id) {
		this._id = _id;
	}
	@JSONField(name="_uid")
	public String get_uid() {
		return _uid;
	}
	@JSONField(name="_uid")
	public void set_uid(String _uid) {
		this._uid = _uid;
	}
	
	@JSONField(name="_state")
	public String get_state() {
		return _state;
	}
	@JSONField(name="_state")
	public void set_state(String _state) {
		this._state = _state;
	}

	

	
}
