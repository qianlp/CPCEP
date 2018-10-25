package htos.sysfmt.file.entity;

import htos.coresys.entity.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 项目文件上传
 * @author zcl
 */
@Entity
@Table(name = "ht_profile_upload_info")
public class ProFileploadModel extends BaseModel{
	private static final long serialVersionUID = 1L;
	private String catalogId;//归档目录id
	private String curDocId;//附件ID
	private String prjID;//项目立项id
	private String billNo;//单据编号
	private String catalogName;//归档名称
	public String getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public String getCurDocId() {
		return curDocId;
	}
	public void setCurDocId(String curDocId) {
		this.curDocId = curDocId;
	}
	public String getPrjID() {
		return prjID;
	}
	public void setPrjID(String prjID) {
		this.prjID = prjID;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getCatalogName() {
		return catalogName;
	}
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	
}
