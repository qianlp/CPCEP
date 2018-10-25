package htos.coresys.entity;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseModel extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	@Column(name="projectNo",length=64)
	private String projectNo;//项目编号
	
	@Column(name="projectName",length=64)
	private String projectName;//项目名称
	
	@Column(name="status", length=20)
	private String status;//状态
	
	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
