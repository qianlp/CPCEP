package htos.coresys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @describe : 按钮对象实体类
 * @author : pangchengxiang
 * @version : v1.0
 */
@Entity
@Table(name = "ht_right_button")
public class Button extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String btnName;
	private Integer btnIsLook;
	private String btnStyle;
	private String btnIcon;
	private String btnTitle;
	private String btnFunction;
	private String parentId;

	// Constructors

	/** default constructor */
	public Button() {
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


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}