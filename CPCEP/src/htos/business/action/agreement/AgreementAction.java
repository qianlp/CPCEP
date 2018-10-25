package htos.business.action.agreement;

import com.opensymphony.xwork2.ActionSupport;
import htos.business.entity.agreement.Agreement;
import htos.business.entity.project.Project;
import htos.business.service.agreement.AgreementService;
import htos.common.util.StringUtil;
import htos.coresys.action.BaseAction;
import htos.coresys.entity.Menu;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.MenuService;
import htos.coresys.util.CommonUtil;
import org.apache.struts2.ServletActionContext;

import java.util.HashMap;
import java.util.Map;

public class AgreementAction extends BaseAction {
	private Agreement agreement;
	private CommonFacadeService<Project> projectCommonFacadeService;
	private AgreementService agreementService;
	private CommonFacadeService<Agreement> commonFacadeService;
	private MenuService menuService;

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public Agreement getAgreement() {
		return agreement;
	}

	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}

	public CommonFacadeService<Agreement> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(CommonFacadeService<Agreement> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	public CommonFacadeService<Project> getProjectCommonFacadeService() {
		return projectCommonFacadeService;
	}

	public void setProjectCommonFacadeService(CommonFacadeService<Project> projectCommonFacadeService) {
		this.projectCommonFacadeService = projectCommonFacadeService;
	}

	public AgreementService getAgreementService() {
		return agreementService;
	}

	public void setAgreementService(AgreementService agreementService) {
		this.agreementService = agreementService;
	}

	public void queryPurchaseWithProject() {
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), agreementService.queryPurchaseWithProject(getpageInfo()));
	}

	public void loadProjectInfo() {
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), projectCommonFacadeService.getEntityByID(Project.class.getSimpleName(), request.getParameter("projectUuid")));
	}

	public void loadSupplierBidInfo() {
		String confirmUuid = request.getParameter("confirmUuid");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), agreementService.loadSupplierBidInfo(confirmUuid));
	}

	public void loadSupplierInfo() {
		String supplierId = request.getParameter("supplierId");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), agreementService.loadSupplierInfo(supplierId));
	}

	public String saveOrUpdate() {
		if(!StringUtil.isEmpty(agreement.getUuid())){//更新
			commonFacadeService.saveOrUpdate(agreement);
		}else{//保存
			Menu menu = menuService.findOneMenuById("entityClsName", agreement.getClass().getSimpleName());
			commonFacadeService.save(agreement, menu.getUuid());
		}
		return ActionSupport.SUCCESS;
	}
	public void loadPurchaseDeviceInfo(){
		Map<String,String> map = new HashMap<>();
		String deviceInfo = agreementService.loadPurchaseDeviceInfo(request.getParameter("purchasePlanUuid"));
		map.put("deviceInfo",deviceInfo);
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
	}

	@Override
	public Agreement getModel() {
		if (CommonUtil.isNullOrEmpty(agreement)) {
			agreement = new Agreement();
		}
		return agreement;
	}
}
