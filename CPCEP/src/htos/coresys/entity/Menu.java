package htos.coresys.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 描述：菜单实体类：Menu
 * 
 * @author 庞成祥
 */
@Entity
@Table(name = "ht_right_menu")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Menu extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String menuIsLook;
	private String menuIsHasWF;//是否关联流程1、关联 0、不关联
	private String menuType;
	private String menuName;
	private String menuIcon;
	private String menuStyle;
	private String menuOpenStyle;//用于是否关联附件
	private String menuHtmlHead;
	private String menuHtmlBody;
	private String menuHtmlSearch;
	private Integer menuOpsition;
	private Integer menuLevel;
	private String menuRootId;

	private String menuPid;
	private String entityClsName;
	private String actionAddress;
	private String pageComAddress;
	private String pageSubAddress;
	private String pageSearchAddress;
	private String dataRightJson;
	private Set<WorkFlow> workFlow;
	private Set<MenuToBtn> menuToBtn;
	private String queryEntityClsname;//视图实体类
	// 是否是供应商的菜单
	private Boolean hasSupplier;

	private String isPrower;
	// Constructors

	/** default constructor */
	public Menu() {
	}

	@Column(name = "menu_is_look", length = 1)
	public String getMenuIsLook() {
		return this.menuIsLook;
	}

	public void setMenuIsLook(String menuIsLook) {
		this.menuIsLook = menuIsLook;
	}

	@Column(name = "menu_is_haswf", length = 1)
	public String getMenuIsHasWF() {
		return this.menuIsHasWF;
	}

	public void setMenuIsHasWF(String menuIsHasWF) {
		this.menuIsHasWF = menuIsHasWF;
	}

	@Column(name = "menu_type", length = 1)
	public String getMenuType() {
		return this.menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	@Column(name = "menu_name", length = 64)
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "menu_icon", length = 64)
	public String getMenuIcon() {
		return this.menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	@Column(name = "menu_style", length = 64)
	public String getMenuStyle() {
		return this.menuStyle;
	}

	public void setMenuStyle(String menuStyle) {
		this.menuStyle = menuStyle;
	}

	@Column(name = "menu_open_style", length = 1)
	public String getMenuOpenStyle() {
		return this.menuOpenStyle;
	}

	public void setMenuOpenStyle(String menuOpenStyle) {
		this.menuOpenStyle = menuOpenStyle;
	}

	@Column(name = "menu_htmlHead")
	public String getMenuHtmlHead() {
		return this.menuHtmlHead;
	}

	public void setMenuHtmlHead(String menuHtmlHead) {
		this.menuHtmlHead = menuHtmlHead;
	}

	@Column(name = "menu_htmlBody")
	public String getMenuHtmlBody() {
		return menuHtmlBody;
	}

	public void setMenuHtmlBody(String menuHtmlBody) {
		this.menuHtmlBody = menuHtmlBody;
	}
	
	
	@Column(name = "menu_htmlSearch")
	public String getMenuHtmlSearch() {
		return menuHtmlSearch;
	}

	public void setMenuHtmlSearch(String menuHtmlSearch) {
		this.menuHtmlSearch = menuHtmlSearch;
	}

	@Column(name = "menu_opsition")
	public Integer getMenuOpsition() {
		return this.menuOpsition;
	}

	public void setMenuOpsition(Integer menuOpsition) {
		this.menuOpsition = menuOpsition;
	}

	@Column(name = "menu_level")
	public Integer getMenuLevel() {
		return this.menuLevel;
	}

	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}

	@Column(name = "menu_pid", length = 64)
	public String getMenuPid() {
		return this.menuPid;
	}

	public void setMenuPid(String menuPid) {
		this.menuPid = menuPid;
	}

	@Column(name = "menu_root_id", length = 64)
	public String getMenuRootId() {
		return menuRootId;
	}

	public void setMenuRootId(String menuRootId) {
		this.menuRootId = menuRootId;
	}

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "menu")
	public Set<WorkFlow> getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(Set<WorkFlow> workFlow) {
		this.workFlow = workFlow;
	}

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "menu")
	public Set<MenuToBtn> getMenuToBtn() {
		return menuToBtn;
	}

	public void setMenuToBtn(Set<MenuToBtn> menuToBtn) {
		this.menuToBtn = menuToBtn;
	}

	@Column(name = "entity_cls_name", length = 64)
	public String getEntityClsName() {
		return entityClsName;
	}

	public void setEntityClsName(String entityClsName) {
		this.entityClsName = entityClsName;
	}
	
	@Column(name = "actionAddress")
	public String getActionAddress() {
		return actionAddress;
	}

	public void setActionAddress(String actionAddress) {
		this.actionAddress = actionAddress;
	}

	/**
	 * @return the pageComAddress
	 */
	@Column(name = "pageComAddress")
	public String getPageComAddress() {
		return pageComAddress;
	}

	/**
	 * @param pageComAddress the pageComAddress to set
	 */
	public void setPageComAddress(String pageComAddress) {
		this.pageComAddress = pageComAddress;
	}

	/**
	 * @return the pageSubAddress
	 */

	@Column(name = "pageSubAddress")
	public String getPageSubAddress() {
		return pageSubAddress;
	}

	/**
	 * @param pageSubAddress the pageSubAddress to set
	 */
	public void setPageSubAddress(String pageSubAddress) {
		this.pageSubAddress = pageSubAddress;
	}

	@Column(name = "dataRightJson", length = 65535)
	public String getDataRightJson() {
		return dataRightJson;
	}

	public void setDataRightJson(String dataRightJson) {
		this.dataRightJson = dataRightJson;
	}
	
	@Column(name = "pageSearchAddress")
	public String getPageSearchAddress() {
		return pageSearchAddress;
	}

	public void setPageSearchAddress(String pageSearchAddress) {
		this.pageSearchAddress = pageSearchAddress;
	}
	
	@Column(name = "query_entity_cls_name", length = 64)
	public String getQueryEntityClsname() {
		return queryEntityClsname;
	}

	public void setQueryEntityClsname(String queryEntityClsname) {
		this.queryEntityClsname = queryEntityClsname;
	}

	/**
	 * @return the isPrower
	 */
	public String getIsPrower() {
		return isPrower;
	}

	/**
	 * @param isPrower the isPrower to set
	 */
	public void setIsPrower(String isPrower) {
		this.isPrower = isPrower;
	}

	@Column(name = "has_supplier")
	public Boolean getHasSupplier() {
		return hasSupplier;
	}

	public void setHasSupplier(Boolean hasSupplier) {
		this.hasSupplier = hasSupplier;
	}
}