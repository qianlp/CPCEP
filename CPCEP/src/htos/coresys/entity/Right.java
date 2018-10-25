package htos.coresys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HtRightRight entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ht_right_right")
public class Right extends BaseEntity {

	// Fields
	private static final long serialVersionUID = 1L;
	private String docId;
	private String orgId;
	private Integer rightType;
	private Integer operType;
	private String modoName;
	private String createDeptId;

	// Constructors

	/** default constructor */
	public Right() {
	}
	//记录唯一标识
	@Column(name = "doc_id", length = 64)
	public String getDocId() {
		return this.docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}
	//组织唯一标识
	@Column(name = "org_id", length = 64)
	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	//权限类型（1、部门权限 2、用户权限 3、角色权限）
	@Column(name = "right_type")
	public Integer getRightType() {
		return this.rightType;
	}

	public void setRightType(Integer rightType) {
		this.rightType = rightType;
	}
	//只读/可编辑(1/2)
	@Column(name = "oper_type")
	public Integer getOperType() {
		return this.operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}
	//模块类型
	@Column(name = "modo_name", length = 64)
	public String getModoName() {
		return this.modoName;
	}

	public void setModoName(String modoName) {
		this.modoName = modoName;
	}
	
	public String getCreateDeptId() {
		return createDeptId;
	}
	public void setCreateDeptId(String createDeptId) {
		this.createDeptId = createDeptId;
	}
	
	
}