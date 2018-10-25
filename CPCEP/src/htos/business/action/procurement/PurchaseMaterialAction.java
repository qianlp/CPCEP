package htos.business.action.procurement;

import PluSoft.Utils.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import htos.business.entity.procurement.PurchaseMaterial;
import htos.business.entity.supplier.SupplierExt;
import htos.business.service.material.MaterialCategoryParamService;
import htos.business.service.procurement.PurchaseMaterialService;
import htos.common.util.JsonUtil;
import htos.common.util.StringUtil;
import htos.coresys.action.BaseAction;
import htos.coresys.entity.Menu;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.MenuService;
import htos.coresys.util.CommonUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
/**
 * 采购管理（采购执行主体）
 * @author
 */

public class PurchaseMaterialAction extends BaseAction {

    private PurchaseMaterialService purchaseMaterialService;
    private PurchaseMaterial material;
    private MenuService menuService;
    private CommonFacadeService<PurchaseMaterial> commonFacadeService;


    public String loadMaterialForPage() {

        request =ServletActionContext.getRequest();
        String type = request.getParameter("deviceType");
        String purchaseId = request.getParameter("purchaseId");
        CommonUtil.toJsonStr(response, purchaseMaterialService.loadMaterialForPage(purchaseId,type, getpageInfo()));
        return null;
    }


    public String loadMaterialsForPage() {
        request =ServletActionContext.getRequest();;
        String search = request.getParameter("search");
        List searchList = new ArrayList();
        if (!StringUtils.isEmpty(search)){
            searchList = (List) JSON.Decode(search);
        }
        Menu menu = menuService.findOneMenuById("entityClsName", SupplierExt.class.getSimpleName());

        CommonUtil.toJsonStr(response, commonFacadeService.loadMapListForNotOrgPageSearch(menu.getUuid(),getpageInfo(), searchList));
        return null;
    }


    public PurchaseMaterialService getPurchaseMaterialService() {
        return purchaseMaterialService;
    }

    public void setPurchaseMaterialService(PurchaseMaterialService purchaseMaterialService) {
        this.purchaseMaterialService = purchaseMaterialService;
    }

    public PurchaseMaterial getMaterial() {
        return material;
    }

    public void setMaterial(PurchaseMaterial material) {
        this.material = material;
    }

    @Override
    public PurchaseMaterial getModel() {
        if (material == null) {
            material = new PurchaseMaterial();
        }
        return material;
    }

    public CommonFacadeService<PurchaseMaterial> getCommonFacadeService() {
        return commonFacadeService;
    }

    public void setCommonFacadeService(CommonFacadeService<PurchaseMaterial> commonFacadeService) {
        this.commonFacadeService = commonFacadeService;
    }

    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }
}
