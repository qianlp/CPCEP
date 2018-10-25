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
@Table(name = "ht_right_fieldform")
public class FieldForm extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String formEName;
	private String formCName;
	private String fieldList;
	private Menu menu;
	
	// Constructors

	/** default constructor */
	public FieldForm() {
	}
	
	@Column(name = "form_ename", length = 64)
	/**
	 * @return the formEName
	 */
	public String getFormEName() {
		return this.formEName;
	}
	
	/**
	 * @param formEName the formEName to set
	 */
	
	public void setFormEName(String formEName) {
		this.formEName = formEName;
	}

	@Column(name = "form_cname", length = 64)
	/**
	 * @return the formCName
	 */
	public String getFormCName() {
		return this.formCName;
	}

	/**
	 * @param formCName the formCName to set
	 */	
	public void setFormCName(String formCName) {
		this.formCName = formCName;
	}

	/**
	 * @return the fieldList
	 */
	@Column(name = "fieldlist", length = 65535)
	public String getFieldList() {
		return this.fieldList;
	}

	/**
	 * @param fieldList the fieldList to set
	 */
	public void setFieldList(String fieldList) {
		this.fieldList = fieldList;
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