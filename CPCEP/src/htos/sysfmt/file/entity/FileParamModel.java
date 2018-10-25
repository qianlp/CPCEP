package htos.sysfmt.file.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "ht_fileparam_info")
public class FileParamModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "uuid", unique = true, nullable = false, length = 64)
	private String uuid;//主键
	
	@Column(name = "upRole",length=1000)
	private String upRole;//可上传角色
	
	@Column(name = "upUser",length=1000)
	private String upUser;//可上传人员
	
	@Column(name = "delRole",length=1000)
	private String delRole;//可删除角色
	
	@Column(name = "delUser",length=1000)
	private String delUser;//可删除人员
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUpRole() {
		return upRole;
	}
	public void setUpRole(String upRole) {
		this.upRole = upRole;
	}
	public String getUpUser() {
		return upUser;
	}
	public void setUpUser(String upUser) {
		this.upUser = upUser;
	}
	public String getDelRole() {
		return delRole;
	}
	public void setDelRole(String delRole) {
		this.delRole = delRole;
	}
	public String getDelUser() {
		return delUser;
	}
	public void setDelUser(String delUser) {
		this.delUser = delUser;
	}
	
	
	
}
