package htos.business.action.procurement;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import htos.business.entity.procurement.PurchaseCheckWay;
import htos.common.util.StringUtil;
import htos.coresys.entity.Menu;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.MenuService;
import htos.coresys.util.CommonUtil;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * 资格审核方式
 * @author
 */

public class PurchaseCheckWayAction extends ActionSupport implements ModelDriven<PurchaseCheckWay> {

    private static final long serialVersionUID = 1L;
    private CommonFacadeService<PurchaseCheckWay> commonFacadeService;

    private MenuService menuService;
    private PurchaseCheckWay purchaseCheckWay ;


    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public CommonFacadeService<PurchaseCheckWay> getCommonFacadeService() {
        return commonFacadeService;
    }

    public void setCommonFacadeService(
            CommonFacadeService<PurchaseCheckWay> commonFacadeService) {
        this.commonFacadeService = commonFacadeService;
    }

    /**
     * struts2的驱动模型
     * 对实体自动赋值
     * @return
     */
    //批量保存或更新
    public String saveUpdateCheckWay() throws Exception{
        if(!StringUtil.isEmpty(purchaseCheckWay.getUuid())){//更新
            purchaseCheckWay.setUpdateBy(purchaseCheckWay.getCreateBy());
            commonFacadeService.saveOrUpdate(purchaseCheckWay);
        }else{//保存
            purchaseCheckWay.setUpdateBy(purchaseCheckWay.getCreateBy());
            Menu menu = menuService.findOneMenuById("entityClsName", purchaseCheckWay.getClass().getSimpleName());
            commonFacadeService.save(purchaseCheckWay, menu.getUuid());
        }
        return "success";
    }
    //查询
    public String queryCheckWayData(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String orgIds = (String) request.getSession().getAttribute("orgIds");
        List<PurchaseCheckWay> list = commonFacadeService.loadList(PurchaseCheckWay.class.getSimpleName());
        CommonUtil.toJsonStr(ServletActionContext.getResponse(),list);
        return null;
    }

    @Override
    public PurchaseCheckWay getModel() {
        if(CommonUtil.isNullOrEmpty(purchaseCheckWay)){
            purchaseCheckWay= new PurchaseCheckWay();
        }
        return purchaseCheckWay;
    }
}
