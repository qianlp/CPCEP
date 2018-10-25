package htos.business.action.procurement;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import htos.business.entity.procurement.PurchaseExecuteBody;
import htos.common.util.StringUtil;
import htos.coresys.entity.Menu;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.MenuService;
import htos.coresys.util.CommonUtil;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * 采购管理（采购执行主体）
 * @author
 */

public class PurchaseExecuteBodyAction extends ActionSupport implements ModelDriven<PurchaseExecuteBody> {

    private static final long serialVersionUID = 1L;
    private CommonFacadeService<PurchaseExecuteBody> commonFacadeService;

    private MenuService menuService;
    private PurchaseExecuteBody purchaseExecuteBody ;


    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public CommonFacadeService<PurchaseExecuteBody> getCommonFacadeService() {
        return commonFacadeService;
    }

    public void setCommonFacadeService(
            CommonFacadeService<PurchaseExecuteBody> commonFacadeService) {
        this.commonFacadeService = commonFacadeService;
    }

    /**
     * struts2的驱动模型
     * 对实体自动赋值
     * @return
     */
    //批量保存或更新类型
    public String saveUpdateExecuteBody() throws Exception{
        if(!StringUtil.isEmpty(purchaseExecuteBody.getUuid())){//更新
            purchaseExecuteBody.setUpdateBy(purchaseExecuteBody.getCreateBy());
            commonFacadeService.saveOrUpdate(purchaseExecuteBody);
        }else{//保存

            purchaseExecuteBody.setUpdateBy(purchaseExecuteBody.getCreateBy());
            Menu menu = menuService.findOneMenuById("entityClsName", purchaseExecuteBody.getClass().getSimpleName());
            commonFacadeService.save(purchaseExecuteBody, menu.getUuid());
        }
        return "success";
    }
    //查询类型维护
    public String queryExecuteBodyData(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String orgIds = (String) request.getSession().getAttribute("orgIds");
        List<PurchaseExecuteBody> list = commonFacadeService.loadList(PurchaseExecuteBody.class.getSimpleName());
        CommonUtil.toJsonStr(ServletActionContext.getResponse(),list);
        return null;
    }

    @Override
    public PurchaseExecuteBody getModel() {
        if(CommonUtil.isNullOrEmpty(purchaseExecuteBody)){
            purchaseExecuteBody= new PurchaseExecuteBody();
        }
        return purchaseExecuteBody;
    }
}
