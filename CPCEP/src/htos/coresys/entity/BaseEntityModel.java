package htos.coresys.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseEntityModel extends BaseModel{
	private static final long serialVersionUID = 1L;
	
	@Column(name="projectType",length=20)
	private String projectType;//项目类型
	
	private Date expectStartDate;//预计开始时间
	
	private Date expectEndDate;//预计结束时间
	
	@Column(name="expectDay", length=10)
	private String expectDay;//预计工期天数
	
	@Column(name="content", length=1000)
	private String content;//内容简介

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="expectStartDate",length=10)
	public Date getExpectStartDate() {
		return expectStartDate;
	}

	public void setExpectStartDate(Date expectStartDate) {
		this.expectStartDate = expectStartDate;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="expectEndDate",length=10)
	public Date getExpectEndDate() {
		return expectEndDate;
	}

	public void setExpectEndDate(Date expectEndDate) {
		this.expectEndDate = expectEndDate;
	}

	public String getExpectDay() {
		return expectDay;
	}

	public void setExpectDay(String expectDay) {
		this.expectDay = expectDay;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


}
