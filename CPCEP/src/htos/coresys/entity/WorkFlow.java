package htos.coresys.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * HtRightWorkflow entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ht_right_workflow")
public class WorkFlow extends BaseEntity {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String wfName;
	private Integer wfVersion;
	private String wfProcessXml;
	private String wfGraphXml;
	private Menu menu;

	// Constructors

	/** default constructor */
	public WorkFlow() {
	}

	@Column(name = "wf_name", length = 64)
	public String getWfName() {
		return this.wfName;
	}

	public void setWfName(String wfName) {
		this.wfName = wfName;
	}

	@Column(name = "wf_version")
	public Integer getWfVersion() {
		return this.wfVersion;
	}

	public void setWfVersion(Integer wfVersion) {
		this.wfVersion = wfVersion;
	}

	@Column(name = "wf_processXML", length = 300000)
	public String getWfProcessXml() {
		return this.wfProcessXml;
	}

	public void setWfProcessXml(String wfProcessXml) {
		this.wfProcessXml = wfProcessXml;
	}

	@Column(name = "wf_graphXML", length = 300000)
	public String getWfGraphXml() {
		return this.wfGraphXml;
	}

	public void setWfGraphXml(String wfGraphXml) {
		this.wfGraphXml = wfGraphXml;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "menu_id")
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}