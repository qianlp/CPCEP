package htos.coresys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 描述：数据展现配置实体类：Menu
	* @author 温勋
	* @ClassName : Menu
	* @Version V1.1
	* @ModifiedBy 温勋
	* @Copyright 华胜龙腾
	* @date 2016年8月19日 下午5:26:56
	*/
@Entity
@Table(name = "ht_right_exhibit_param_info")
@DynamicUpdate(true)
@DynamicInsert(true)
public class ExhibitParamModel extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@Column(name = "exhibitHtmlHead")
	private String exhibitHtmlHead;
	
	@Column(name = "exhibitHtmlBody")
	private String exhibitHtmlBody;
	

	@Column(name = "exhibitHtmlSearch")
	private String exhibitHtmlSearch;
	
	@Column(name = "modularName")
	private String modularName;
	
	@Column(name = "tableName")
	private String tableName;
	
	@Column(name = "exhibitPath")
	private String exhibitPath;
	
	@Column(name = "exhibitPid")
	private String exhibitPid;
	
	@Column(name = "menuId")
	private String menuId;
	
	private String pageSearchAddress;
	
	private String entityClsName;
	
	private String isPower;
	
	public String getExhibitHtmlHead() {
		return exhibitHtmlHead;
	}
	public void setExhibitHtmlHead(String exhibitHtmlHead) {
		this.exhibitHtmlHead = exhibitHtmlHead;
	}
	public String getExhibitHtmlBody() {
		return exhibitHtmlBody;
	}
	public void setExhibitHtmlBody(String exhibitHtmlBody) {
		this.exhibitHtmlBody = exhibitHtmlBody;
	}
	public String getModularName() {
		return modularName;
	}
	public void setModularName(String modularName) {
		this.modularName = modularName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getExhibitPath() {
		return exhibitPath;
	}
	public void setExhibitPath(String exhibitPath) {
		this.exhibitPath = exhibitPath;
	}
	public String getExhibitPid() {
		return exhibitPid;
	}
	public void setExhibitPid(String exhibitPid) {
		this.exhibitPid = exhibitPid;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	public String getPageSearchAddress() {
		return pageSearchAddress;
	}
	public void setPageSearchAddress(String pageSearchAddress) {
		this.pageSearchAddress = pageSearchAddress;
	}
	
	@Transient
	public String getEntityClsName() {
		return entityClsName;
	}
	public void setEntityClsName(String entityClsName) {
		this.entityClsName = entityClsName;
	}
	public String getIsPower() {
		return isPower;
	}
	public void setIsPower(String isPower) {
		this.isPower = isPower;
	}
	public String getExhibitHtmlSearch() {
		return exhibitHtmlSearch;
	}
	public void setExhibitHtmlSearch(String exhibitHtmlSearch) {
		this.exhibitHtmlSearch = exhibitHtmlSearch;
	}
	
}