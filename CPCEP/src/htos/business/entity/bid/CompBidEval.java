package htos.business.entity.bid;

import htos.coresys.entity.BaseEntity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "bs_comp_bid_eval")
public class CompBidEval extends BaseEntity{
	private String catalogId;
	private String biddingFileUuid;
	private String suggestSupplierBid;
	private String content;
	private String curDocId;
	
	@Basic
	@Column(name = "curDocId")
	public String getCurDocId() {
		return curDocId;
	}

	public void setCurDocId(String curDocId) {
		this.curDocId = curDocId;
	}

	@Basic
	@Column(name = "catalogId")
	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	@Basic
	@Column(name = "bidding_file_uuid")
	public String getBiddingFileUuid() {
		return biddingFileUuid;
	}

	public void setBiddingFileUuid(String biddingFileUuid) {
		this.biddingFileUuid = biddingFileUuid;
	}

	@Basic
	@Column(name = "suggest_supplier_bid")
	public String getSuggestSupplierBid() {
		return suggestSupplierBid;
	}

	public void setSuggestSupplierBid(String suggestSupplierBid) {
		this.suggestSupplierBid = suggestSupplierBid;
	}

	public String getContent() {
		return content;
	}
	@Column(name = "content")
	public void setContent(String content) {
		this.content = content;
	}
   
}
