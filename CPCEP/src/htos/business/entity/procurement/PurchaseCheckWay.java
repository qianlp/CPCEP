package htos.business.entity.procurement;

import htos.coresys.entity.BaseEntity;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 */
@Entity
@Table(name = "bs_purchase_check_way")
public class PurchaseCheckWay extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String catalogId;
    private String method;

    @Column(name = "catalogId")
    public String getCatalogId() {
        return this.catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    @Column(name = "method")
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}