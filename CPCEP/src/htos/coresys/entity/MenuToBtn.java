package htos.coresys.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * HtRightMenuButton entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ht_right_menu_button")
@DynamicUpdate(true)
@DynamicInsert(true)
public class MenuToBtn  extends BaseEntity  {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String btnId;
	private String btnName;
	private Integer btnIsLook;
	private String btnStyle;
	private String btnIcon;
	private String btnTitle;
	private String btnFunction;
	private Menu menu;

	// Constructors

	/** default constructor */
	public MenuToBtn() {
	}



	@Column(name = "btn_id", length = 64)
	public String getBtnId() {
		return this.btnId;
	}

	public void setBtnId(String btnId) {
		this.btnId = btnId;
	}


	@Column(name = "btn_name", length = 64)
	public String getBtnName() {
		return this.btnName;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}

	@Column(name = "btn_isLook")
	public Integer getBtnIsLook() {
		return this.btnIsLook;
	}

	public void setBtnIsLook(Integer btnIsLook) {
		this.btnIsLook = btnIsLook;
	}

	@Column(name = "btn_style", length = 100)
	public String getBtnStyle() {
		return this.btnStyle;
	}

	public void setBtnStyle(String btnStyle) {
		this.btnStyle = btnStyle;
	}

	@Column(name = "btn_icon", length = 64)
	public String getBtnIcon() {
		return this.btnIcon;
	}

	public void setBtnIcon(String btnIcon) {
		this.btnIcon = btnIcon;
	}

	@Column(name = "btn_title", length = 64)
	public String getBtnTitle() {
		return this.btnTitle;
	}

	public void setBtnTitle(String btnTitle) {
		this.btnTitle = btnTitle;
	}

	@Column(name = "btn_function", length = 1000)
	public String getBtnFunction() {
		return this.btnFunction;
	}

	public void setBtnFunction(String btnFunction) {
		this.btnFunction = btnFunction;
	}
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name = "menu_id")
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
}