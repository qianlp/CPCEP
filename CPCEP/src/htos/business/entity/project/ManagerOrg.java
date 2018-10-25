package htos.business.entity.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import htos.coresys.entity.BaseEntity;
import htos.coresys.entity.BaseModel;

@Entity
@Table(name = "bs_manager_org")
public class ManagerOrg extends BaseEntity implements java.io.Serializable{
    private static final long serialVersionUID = 1L;

    private String companyName;

    @Column(name="company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
