package htos.business.entity.project;

import htos.coresys.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bs_project_base")
public class Project extends BaseEntity implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    private String projectCode;
    private String projectName;
    private String sceneContact;
    private String contact;
    private String owner;
    private String address;
    private String managerCompany;
    private String catalogId;
    private String curDocId;
    private String designManagerUUID;
    private String purchaseManagerUUID;
    private String projectManagerUUID;
    private String designManagerName;
    private String purchaseManagerName;
    private String projectManagerName;


    @Column(name = "project_code")
    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
    @Column(name = "project_name")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }



    @Column(name = "scene_contact")
    public String getSceneContact() {
        return sceneContact;
    }

    public void setSceneContact(String sceneContact) {
        this.sceneContact = sceneContact;
    }

    @Column(name = "contact")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "manager_company")
    public String getManagerCompany() {
        return managerCompany;
    }

    public void setManagerCompany(String managerCompany) {
        this.managerCompany = managerCompany;
    }


    @Column(name = "catalog_id", length = 64)
    public String getCatalogId() {
        return this.catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    @Column(name = "curDocId", length = 64)
    public String getCurDocId() {
        return this.curDocId;
    }

    public void setCurDocId(String curDocId) {
        this.curDocId = curDocId;
    }

    @Column(name = "designManagerUUID")
    public String getDesignManagerUUID() {
        return designManagerUUID;
    }

    public void setDesignManagerUUID(String designManagerUUID) {
        this.designManagerUUID = designManagerUUID;
    }

    @Column(name = "purchaseManagerUUID")
    public String getPurchaseManagerUUID() {
        return purchaseManagerUUID;
    }

    public void setPurchaseManagerUUID(String purchaseManagerUUID) {
        this.purchaseManagerUUID = purchaseManagerUUID;
    }

    @Column(name = "projectManagerUUID")
    public String getProjectManagerUUID() {
        return projectManagerUUID;
    }

    public void setProjectManagerUUID(String projectManagerUUID) {
        this.projectManagerUUID = projectManagerUUID;
    }

    @Column(name = "designManagerName")
    public String getDesignManagerName() {
        return designManagerName;
    }

    public void setDesignManagerName(String designManagerName) {
        this.designManagerName = designManagerName;
    }

    @Column(name = "purchaseManagerName")
    public String getPurchaseManagerName() {
        return purchaseManagerName;
    }

    public void setPurchaseManagerName(String purchaseManagerName) {
        this.purchaseManagerName = purchaseManagerName;
    }

    @Column(name = "projectManagerName")
    public String getProjectManagerName() {
        return projectManagerName;
    }

    public void setProjectManagerName(String projectManagerName) {
        this.projectManagerName = projectManagerName;
    }
}
