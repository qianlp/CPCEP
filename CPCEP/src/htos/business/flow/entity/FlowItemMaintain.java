package htos.business.flow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "bs_flow_item_maintain")
public class FlowItemMaintain implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    private String uuid;
    private String dataList;
    private String htmlBody;
	
    @GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "uuid", unique = true, nullable = false, length = 64)
	public String getUuid() {
		return uuid;
	}
	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/**
	 * @return the dataList
	 */
	public String getDataList() {
		return dataList;
	}
	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(String dataList) {
		this.dataList = dataList;
	}
	/**
	 * @return the htmlBody
	 */
	public String getHtmlBody() {
		return htmlBody;
	}
	/**
	 * @param htmlBody the htmlBody to set
	 */
	public void setHtmlBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}

    
}
