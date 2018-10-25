package htos.business.action.procurement;

import PluSoft.Utils.JSON;
import htos.business.entity.procurement.PurchaseApply;
import htos.business.entity.procurement.PurchaseCandiate;
import htos.business.entity.procurement.PurchaseMaterial;
import htos.business.entity.procurement.PurchasePlan;
import htos.business.entity.supplier.SupplierExt;
import htos.business.service.procurement.PurchaseCandidateService;
import htos.coresys.action.BaseAction;
import htos.coresys.entity.Menu;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.MenuService;
import htos.coresys.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseCandiateAction extends BaseAction{
    private MenuService menuService;
    private CommonFacadeService<PurchaseCandiate> commonFacadeService;
    private PurchaseCandidateService candidateService;

    public String loadCandiateForPage() {
        request = ServletActionContext.getRequest();;

        String type = request.getParameter("type");
        String planId = request.getParameter("planId");
        CommonUtil.toJsonStr(response, candidateService.loadMaterialForPage(planId, Integer.parseInt(type), getpageInfo()));
        return null;
    }


    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public CommonFacadeService<PurchaseCandiate> getCommonFacadeService() {
        return commonFacadeService;
    }

    public void setCommonFacadeService(CommonFacadeService<PurchaseCandiate> commonFacadeService) {
        this.commonFacadeService = commonFacadeService;
    }

    public PurchaseCandidateService getCandidateService() {
        return candidateService;
    }

    public void setCandidateService(PurchaseCandidateService candidateService) {
        this.candidateService = candidateService;
    }
}
