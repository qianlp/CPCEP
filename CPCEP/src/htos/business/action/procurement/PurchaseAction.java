package htos.business.action.procurement;

import PluSoft.Utils.JSON;
import com.alibaba.fastjson.JSONObject;
import htos.business.entity.material.MaterialAttr;
import htos.business.entity.procurement.PurchaseApply;
import htos.business.entity.procurement.PurchaseMaterial;
import htos.business.entity.procurement.view.PurchaseMaterialParamView;
import htos.business.entity.project.Project;
import htos.business.service.material.MaterialAttrService;
import htos.business.service.procurement.PurchaseApplyService;
import htos.business.service.procurement.PurchaseMaterialService;
import htos.business.utils.ajax.AjaxCode;
import htos.business.utils.ajax.AjaxUtils;
import htos.common.util.StringUtil;
import htos.coresys.action.BaseAction;
import htos.coresys.entity.Menu;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.MenuService;
import htos.coresys.service.UserService;
import htos.coresys.util.CommonUtil;
import htos.sysfmt.message.entity.MsgInfo;
import htos.sysfmt.message.service.MsgInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 采购管理（采购执行主体）
 * @author
 */

public class PurchaseAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private CommonFacadeService<PurchaseApply> commonFacadeService;

	private CommonFacadeService<PurchaseMaterial> commonFacadeMaterialService;
	private CommonFacadeService<Project> projectCommonFacadeService;

	private MenuService menuService;
	private PurchaseApply purchaseApply ;
	private MaterialAttrService materialAttrService;
	private MsgInfoService msgInfoService;
	private UserService userService;
	private PurchaseApplyService purchaseApplyService;
	private PurchaseMaterialService purchaseMaterialService;

	public void setPurchaseApplyService(PurchaseApplyService purchaseApplyService) {
		this.purchaseApplyService = purchaseApplyService;
	}

	public void setProjectCommonFacadeService(CommonFacadeService<Project> projectCommonFacadeService) {
		this.projectCommonFacadeService = projectCommonFacadeService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setMsgInfoService(MsgInfoService msgInfoService) {
		this.msgInfoService = msgInfoService;
	}

	public void setMaterialAttrService(MaterialAttrService materialAttrService) {
		this.materialAttrService = materialAttrService;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public CommonFacadeService<PurchaseApply> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(
			CommonFacadeService<PurchaseApply> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	public CommonFacadeService<PurchaseMaterial> getCommonFacadeMaterialService() {
		return commonFacadeMaterialService;
	}

	public void setCommonFacadeMaterialService(CommonFacadeService<PurchaseMaterial> commonFacadeMaterialService) {
		this.commonFacadeMaterialService = commonFacadeMaterialService;
	}

	/**
	 * struts2的驱动模型
	 * 对实体自动赋值
	 * @return
	 */
	//批量保存或更新类型
	public String saveUpdatePurchase() throws Exception{
		User user = (User) request.getSession().getAttribute("user");
		Menu menu = menuService.findOneMenuById("entityClsName", purchaseApply.getClass().getSimpleName());
		if(!StringUtil.isEmpty(purchaseApply.getUuid())){//
		    commonFacadeService.saveOrUpdate(purchaseApply);
			UpdatePurchaseMaterial();
		}else{//保存
			String uuid = commonFacadeService.save(purchaseApply, menu.getUuid());
			savePurchaseMaterial(uuid);
			// 发送消息
			sendMessage(user, purchaseApply.getUuid(), purchaseApply.getExecutePeoId(), menu.getUuid());	// 采购执行人
			// 项目设计经理
			Project project = projectCommonFacadeService.get(Project.class, purchaseApply.getPrjID());
			sendMessage(user, purchaseApply.getUuid(), project.getDesignManagerUUID(), menu.getUuid());
			// 发起人分管领导
			List<User> leaders = userService.findLeaders(user.getUserPostion(), user.getUserDeptId());
			if(leaders != null && !leaders.isEmpty()) {
				for (User leader : leaders) {
					sendMessage(user, purchaseApply.getUuid(), leader.getUuid(), menu.getUuid());
				}

			}
		}

		if(!StringUtil.isEmpty(purchaseApply.getDelIds())){
			//commonFacadeMaterialService.deleteIdList();
			purchaseMaterialService.deletePurchaseMaterials(purchaseApply.getDelIds());
		}
		return "success";
	}

	private void sendMessage(User user, String uuid, String targetUser, String menuId) {
		MsgInfo message = new MsgInfo();
		message.setDocId(uuid);
		message.setCreateBy(user.getUserName());
		message.setCreateDate(new Date());
		message.setMsgTitle(user.getUserName() + "创建了新的采购申请");
		message.setUserId(targetUser);
		message.setMenuId(menuId);
		message.setMsgType(2);	// 作为通知发送
		msgInfoService.saveMsg(message);
	}


	@Override
	public PurchaseApply getModel() {
		if(CommonUtil.isNullOrEmpty(purchaseApply)){
			purchaseApply= new PurchaseApply();
		}
		return purchaseApply;
	}

	public boolean savePurchaseMaterial(String  uuid){

		try {

			List<PurchaseMaterial> list1 = JSONObject.parseArray(purchaseApply.getDeviceString1(), PurchaseMaterial.class);

			for(int i=0;i<list1.size();i++){
				PurchaseMaterial material =list1.get(i);
				material.setPurchaseId(uuid);
				material.setType("1");
				String pmUuid = commonFacadeMaterialService.save(material);
				List<PurchaseMaterialParamView> purchaseMaterialParamViews = JSONObject.parseArray(material.getParamsJson(), PurchaseMaterialParamView.class);
				purchaseApplyService.saveUpdatePurchaseMaterialParam(purchaseMaterialParamViews,material.getHaveEditParam(),pmUuid);
			}

			List<PurchaseMaterial> list2 = JSONObject.parseArray(purchaseApply.getDeviceString2(), PurchaseMaterial.class);


			for(int i=0;i<list2.size();i++){
				PurchaseMaterial material =list2.get(i);
				material.setPurchaseId(uuid);
				material.setType("2");
				commonFacadeMaterialService.save(material);
			}

			List<PurchaseMaterial> list3 = JSONObject.parseArray(purchaseApply.getDeviceString3(), PurchaseMaterial.class);

			for(int i=0;i<list3.size();i++){
				PurchaseMaterial material =list3.get(i);
				material.setPurchaseId(uuid);
				material.setType("3");
				commonFacadeMaterialService.save(material);
			}

		}catch (Exception e){
			return false;
		}

		return true;
	}

	public boolean UpdatePurchaseMaterial(){

		try {

			List<PurchaseMaterial> list1 = JSONObject.parseArray(purchaseApply.getDeviceString1(), PurchaseMaterial.class);
			purchaseApply.setDeviceArr1(list1);

			for(int i=0;i<list1.size();i++){
				PurchaseMaterial material =list1.get(i);
				material.setPurchaseId(purchaseApply.getUuid());
				material.setType("1");
				String pmUuid = material.getUuid();
				if (StringUtils.isNotBlank(material.getUuid())){
					commonFacadeMaterialService.update(material);
				}else{
					pmUuid = commonFacadeMaterialService.save(material);
				}
				if(!StringUtil.isEmpty(material.getParamsJson())){
					List<PurchaseMaterialParamView> purchaseMaterialParamViews = JSONObject.parseArray(material.getParamsJson(), PurchaseMaterialParamView.class);
					purchaseApplyService.saveUpdatePurchaseMaterialParam(purchaseMaterialParamViews,material.getHaveEditParam(),pmUuid);
				}
				if(material.getParamsJson()!=null&&material.getParamsJson().isEmpty()&&StringUtils.isNotBlank(material.getHaveEditParam())&&material.getHaveEditParam().equals("1")){
					//操作之后为空
					purchaseApplyService.deletePurchaseMaterialParam(pmUuid);
				}

			}

			List<PurchaseMaterial> list2 = JSONObject.parseArray(purchaseApply.getDeviceString2(), PurchaseMaterial.class);
			purchaseApply.setDeviceArr2(list2);


			for(int i=0;i<list2.size();i++){
				PurchaseMaterial material =list2.get(i);
				material.setPurchaseId(purchaseApply.getUuid());
				material.setType("2");
				commonFacadeMaterialService.saveOrUpdate(material);
			}

			List<PurchaseMaterial> list3 = JSONObject.parseArray(purchaseApply.getDeviceString3(), PurchaseMaterial.class);
			purchaseApply.setDeviceArr3(list3);

			for(int i=0;i<list3.size();i++){
				PurchaseMaterial material =list3.get(i);
				material.setPurchaseId(purchaseApply.getUuid());
				material.setType("3");
				commonFacadeMaterialService.saveOrUpdate(material);
			}

		}catch (Exception e){
			return false;
		}

		return true;
	}

	public String queryPurchaseApplyData(){
		HttpServletRequest request = ServletActionContext.getRequest();
		User loginUser = (User) request.getSession().getAttribute("user");
		String wfStatus = request.getParameter("wfStatus");
//		List<PurchaseApply> list = commonFacadeService.getListByProperty(PurchaseApply.class.getSimpleName(),"wfStatus",wfStatus);
		List searchList= new ArrayList();
		if(request.getParameter("search")!=null){
			searchList = (List) JSON.Decode(request.getParameter("search"));
		}
		List<PurchaseApply> list = purchaseApplyService.findMyApply(loginUser.getUuid(), wfStatus,searchList);
		if(list != null && !list.isEmpty()) {
			for (PurchaseApply apply : list) {
				apply.setSameExecutePeo(StringUtils.equals(apply.getExecutePeoId(), loginUser.getUuid()));
			}
		}
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),list);
		return null;
	}

	/**
	 * 保存设备分项信息
	 */
	public void saveDeviceAttr() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String planId = request.getParameter("planId");
		String materialId = request.getParameter("materialId");
		String deviceAttr = request.getParameter("deviceAttr");
		String supplier = request.getParameter("supplier");
		String userId = request.getParameter("userId");
		if (StringUtils.isBlank(userId)){
			User user = (User) request.getSession().getAttribute("user");
			userId = user.getUuid();
		}
		try {
			materialAttrService.saveDeviceAttr(planId, materialId, deviceAttr, userId, supplier);
			AjaxUtils.success();
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtils.error(AjaxCode.SERVER_ERROR, e.getMessage());
		}
	}

	public void getDeviceAttr() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String planId = request.getParameter("planId");
		String materialId = request.getParameter("materialId");
		htos.business.entity.supplier.view.Page<MaterialAttr> page = getPage();
		materialAttrService.findDeviceAttr(page, planId, materialId, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("data", page.getResult());
		CommonUtil.toJsonStr(response, map);
	}

	public String deleteDeviceAttr(){
		String uuid = request.getParameter("uuid");
		String[] ids = uuid.split("\\^");
		materialAttrService.deleteDeviceAttr(ids);
		return null;
	}

	public void getSupplierDeviceAttr() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String planId = request.getParameter("planId");
		String materialId = request.getParameter("materialId");
		String userId = request.getParameter("userId");
		List<MaterialAttr> materialAttrs = materialAttrService.findDeviceAttrNoPage(planId, materialId, userId);
		for(MaterialAttr attr:materialAttrs){
			if(attr.getFinalPrice()==null){
				attr.setFinalPrice(attr.getPrice());
			}
		}
		CommonUtil.toJsonStr(response, materialAttrs);
	}

	public void loadParam(){
		String pmUuid = request.getParameter("pmUuid");//采购材料
		CommonUtil.toJsonStr(response, purchaseApplyService.findMaterialParam(pmUuid));
	}

	public PurchaseMaterialService getPurchaseMaterialService() {
		return purchaseMaterialService;
	}

	public void setPurchaseMaterialService(PurchaseMaterialService purchaseMaterialService) {
		this.purchaseMaterialService = purchaseMaterialService;
	}
}
