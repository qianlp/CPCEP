package htos.business.entity.procurement;

import htos.business.entity.material.Material;

import htos.coresys.entity.BaseEntity;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.Date;

@Entity
@Table(name = "bs_purchase_material")
public class PurchaseMaterial  extends BaseEntity{


    private String type;
    private int    num;
    private String purchaseId;
    private String materialCode;
    private String materialName;
    private String categoryUuid;
    private String specModel;
    private String brand;
    private String unit;
    private String materialUUID ;
    private String paramsJson;
    private String haveEditParam;


    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "num")
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Column(name = "purchase_id")
    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }



    @Column(name = "material_code")
    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    @Column(name = "material_name")
    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @Column(name = "category_uuid")
    public String getCategoryUuid() {
        return categoryUuid;
    }

    public void setCategoryUuid(String categoryUuid) {
        this.categoryUuid = categoryUuid;
    }

    @Column(name = "spec_model")
    public String getSpecModel() {
        return specModel;
    }

    public void setSpecModel(String specModel) {
        this.specModel = specModel;
    }

    @Column(name = "brand")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "unit")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Column(name ="material_uuid")
    public String getMaterialUUID() {
        return materialUUID;
    }

    public void setMaterialUUID(String materialUUID) {
        this.materialUUID = materialUUID;
    }

    @Transient
    public String getParamsJson() {
        return paramsJson;
    }

    public void setParamsJson(String paramsJson) {
        this.paramsJson = paramsJson;
    }

    @Transient
    public String getHaveEditParam() {
        return haveEditParam;
    }

    public void setHaveEditParam(String haveEditParam) {
        this.haveEditParam = haveEditParam;
    }
}
