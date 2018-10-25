package htos.business.action.procurement;

import htos.business.entity.procurement.PurchaseApply;
import htos.business.entity.procurement.PurchaseCandiate;
import htos.business.entity.procurement.PurchasePlan;
import htos.business.service.material.MaterialAttrService;
import htos.business.service.procurement.PurchaseCandidateService;
import htos.business.service.procurement.PurchasePlanService;
import htos.business.utils.ajax.AjaxUtils;
import htos.common.util.StringUtil;
import htos.coresys.action.BaseAction;
import htos.coresys.entity.Menu;
import htos.coresys.entity.Right;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.CommonService;
import htos.coresys.service.MenuService;
import htos.coresys.util.CommonUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import PluSoft.Utils.JSON;

import com.alibaba.fastjson.JSONObject;

public class PurchasePlanAction extends BaseAction {

    private MenuService menuService;
    private CommonFacadeService commonFacadeService;
    private CommonFacadeService<PurchaseCandiate> commonCandiateService;
    private PurchasePlanService purchasePlanService;
    private PurchasePlan purchasePlan;
    private MaterialAttrService materialAttrService;
    private CommonService<PurchaseApply> commonService;
    private CommonService<PurchaseCandiate> commonPurchasePlanService;
    private PurchaseCandidateService candidateService;

    /**
     * struts2的驱动模型
     * 对实体自动赋值
     * @return
     */
    public String saveUpdatePurchasePlan() throws Exception{

    	String uuid="";
        if(!StringUtil.isEmpty(purchasePlan.getUuid())){//
        	uuid=purchasePlan.getUuid();
           commonFacadeService.saveOrUpdate(purchasePlan);
           PurchaseApply purchaseApply = commonService.get(PurchaseApply.class, purchasePlan.getPurchaseApplyUuid());

           purchaseApply.setWfStatus("6");
           commonService.saveOrUpdate(purchaseApply);
            saveCandiate(purchasePlan.getUuid());
            saveInSupplier(purchasePlan.getUuid());

        }else{//保存

           String menuId=ServletActionContext.getRequest().getParameter("menuId");
            uuid = commonFacadeService.save(purchasePlan, menuId);
            saveCandiate(uuid);
			saveInSupplier(uuid);
            String tempId = request.getParameter("tempId");
            if(StringUtils.isNotBlank(tempId))
                materialAttrService.updateMaterial(tempId, uuid);
        }
        if(StringUtil.isEmpty(purchasePlan.getWfStatus()) || purchasePlan.getWfStatus().equals("2")){
        	if(!uuid.equals("") && !StringUtil.isEmpty(purchasePlan.getTechnologyReviewerUuid())){
        		Right right=new Right();
        		right.setDocId(uuid);
        		right.setModoName("PurchasePlan");
        		right.setOrgId(purchasePlan.getTechnologyReviewerUuid());
        		right.setRightType(2);
        		right.setOperType(1);
        		right.setCreateDate(new Date());
        		right.setCreateBy(purchasePlan.getCreateBy());
        		commonFacadeService.save(right);
        	
//        		User user=(User) commonFacadeService.getEntityByID("User", purchasePlan.getTechnologyReviewerUuid());
//        		Right right1=new Right();
//        		right1.setDocId(uuid);
//        		right1.setModoName("PurchasePlan");
//        		right1.setOrgId(user.getUserDeptId());
//        		right1.setRightType(1);
//        		right1.setOperType(1);
//        		right1.setCreateDate(new Date());
//        		right1.setCreateBy(purchasePlan.getCreateBy());
//        		commonFacadeService.save(right1);
        	}
        }


        return "success";
    }

	private void saveInSupplier(String uuid) {
		if(StringUtil.isEmpty(purchasePlan.getInvSupplier()))
			return;
        candidateService.delPurchaseCandiate(purchasePlan.getUuid(),1);
        List<PurchaseCandiate> list = JSONObject.parseArray(purchasePlan.getInvSupplier(), PurchaseCandiate.class);
		for(int i=0;i<list.size();i++){
			PurchaseCandiate candiate = list.get(i);
			candiate.setPlanId(uuid);
			candiate.setType(1);	// 邀请供应商
            candiate.setUuid(null);
            commonPurchasePlanService.save(candiate);
		}
	}

	private void saveCandiate(String uuid) {
        if(StringUtil.isEmpty(purchasePlan.getCanSupplier())) return;

        candidateService.delPurchaseCandiate(purchasePlan.getUuid(),2);
        List<PurchaseCandiate> list = JSONObject.parseArray(purchasePlan.getCanSupplier(), PurchaseCandiate.class);
        for(int i=0;i<list.size();i++){
            PurchaseCandiate candiate = list.get(i);
            candiate.setPlanId(uuid);
			candiate.setType(2);	// 候选供应商
            candiate.setUuid(null);
            if(StringUtil.isEmpty(candiate.getUuid())){
                commonPurchasePlanService.save(candiate);
            }else{
                commonPurchasePlanService.update(candiate);

            }
        }

    }

    private void updateCandiate() {

        if(!StringUtils.isEmpty(purchasePlan.getCanSupplier())){
            List<PurchaseCandiate> list = JSONObject.parseArray(purchasePlan.getCanSupplier(), PurchaseCandiate.class);
            for(int i=0;i<list.size();i++){
                PurchaseCandiate candiate = list.get(i);
                candiate.setPlanId(purchasePlan.getUuid());

                if(StringUtil.isEmpty(candiate.getUuid())){
                    commonCandiateService.save(candiate);
                }else{
                    commonCandiateService.update(candiate);

                }
            }
        }


    }
    private void updateInSupplier() {
        if(StringUtil.isEmpty(purchasePlan.getInvSupplier()))
            return;

        candidateService.delPurchaseCandiate(purchasePlan.getUuid(),1);
        List<PurchaseCandiate> list = JSONObject.parseArray(purchasePlan.getInvSupplier(), PurchaseCandiate.class);
        for(int i=0;i<list.size();i++){
            PurchaseCandiate candiate = list.get(i);
            candiate.setPlanId(purchasePlan.getUuid());
            candiate.setType(1);	// 邀请供应商
            candiate.setUuid(null);
            if(StringUtil.isEmpty(candiate.getUuid())){
                commonPurchasePlanService.save(candiate);
            }else{
                commonPurchasePlanService.update(candiate);

            }
        }
    }

    public void  getAmount(){
		String code = purchasePlanService.createCode();
		AjaxUtils.renderData(code);
//        List<PurchasePlan> list = commonFacadeService.loadList(PurchasePlan.class.getSimpleName());
//       CommonUtil.toJsonStr(response, list);
    }

    public void queryPurchasePlanData(){
        String[] purchaseMethods = request.getParameterValues("purchaseMethod[]");
        String wfStatus = request.getParameter("wfStatus");
        String search = request.getParameter("search");
        List searchList=null;
        if (!StringUtils.isEmpty(search)){
            searchList = (List) JSON.Decode(search);
        }
        CommonUtil.toJsonStr(response,purchasePlanService.loadForPage(wfStatus,purchaseMethods,searchList, getpageInfo()),"requestCalibrationTime","requestApproachTime");
    }

    public PurchasePlanService getPurchasePlanService() {
        return purchasePlanService;
    }

    public void setPurchasePlanService(PurchasePlanService purchasePlanService) {
        this.purchasePlanService = purchasePlanService;
    }

    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
	 * @return the commonFacadeService
	 */
	public CommonFacadeService getCommonFacadeService() {
		return commonFacadeService;
	}

	/**
	 * @param commonFacadeService the commonFacadeService to set
	 */
	public void setCommonFacadeService(CommonFacadeService commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	public CommonFacadeService<PurchaseCandiate> getCommonCandiateService() {
        return commonCandiateService;
    }

    public void setCommonCandiateService(CommonFacadeService<PurchaseCandiate> commonCandiateService) {
        this.commonCandiateService = commonCandiateService;
    }

    @Override
    public PurchasePlan getModel() {
        if (purchasePlan == null) {
            purchasePlan = new PurchasePlan();
        }
        return purchasePlan;
    }

    public void setMaterialAttrService(MaterialAttrService materialAttrService) {
        this.materialAttrService = materialAttrService;
    }

	public PurchasePlan getPurchasePlan() {
		return purchasePlan;
	}

	public void setPurchasePlan(PurchasePlan purchasePlan) {
		this.purchasePlan = purchasePlan;
	}

	public CommonService<PurchaseApply> getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService<PurchaseApply> commonService) {
		this.commonService = commonService;
	}

	public MaterialAttrService getMaterialAttrService() {
		return materialAttrService;
	}

	public CommonService<PurchaseCandiate> getCommonPurchasePlanService() {
		return commonPurchasePlanService;
	}

	public void setCommonPurchasePlanService(
			CommonService<PurchaseCandiate> commonPurchasePlanService) {
		this.commonPurchasePlanService = commonPurchasePlanService;
	}

    public PurchaseCandidateService getCandidateService() {
        return candidateService;
    }

    public void setCandidateService(PurchaseCandidateService candidateService) {
        this.candidateService = candidateService;
    }
}
