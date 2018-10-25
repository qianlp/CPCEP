package htos.business.action.supplier;

import PluSoft.Utils.JSON;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import htos.business.entity.bulletin.BiddingBulletin;
import htos.business.entity.material.MaterialCategory;
import htos.business.entity.supplier.GoodsScope;
import htos.business.entity.supplier.SupplierAttachment;
import htos.business.entity.supplier.SupplierExt;
import htos.business.entity.supplier.SupplierSignup;
import htos.business.entity.supplier.view.ViewAttachment;
import htos.business.entity.supplier.view.ViewSupplierTrader;
import htos.business.entity.supplier.view.ViewUserSupplier;
import htos.business.service.bulletin.BiddingBulletinService;
import htos.business.service.material.MaterialCategoryService;
import htos.business.service.sms.SmsVerifyCodeService;
import htos.business.service.supplier.SupplierAttachmentService;
import htos.business.service.supplier.SupplierService;
import htos.business.utils.ajax.AjaxCode;
import htos.business.utils.ajax.AjaxUtils;
import htos.coresys.action.BaseAction;
import htos.coresys.entity.Menu;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.MenuService;
import htos.coresys.service.UserService;
import htos.coresys.util.CommonUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * 供应商
 *
 * @author qinj
 * @date 2018-02-04 23:06
 **/
public class SupplierAction extends BaseAction {

	private Logger log = LoggerFactory.getLogger(SupplierAction.class);

	private UserService userService;
	private SupplierService supplierService;
	private SupplierAttachmentService supplierAttachmentService;
	private CommonFacadeService<SupplierExt> commonFacadeService;
	private CommonFacadeService<GoodsScope> goodsScopeCommonFacadeService;
	private MenuService menuService;
	private BiddingBulletinService biddingBulletinService;
	private SmsVerifyCodeService smsVerifyCodeService;
	private MaterialCategoryService materialCategoryService;

	/**
	 * @description 供应商注册页面
	 * @author qinj
	 * @date 2018/2/4 23:08
	 */
	public String supplierRegisterPage() {
		return ActionSupport.SUCCESS;
	}
	public String pwdForget() {
		return ActionSupport.SUCCESS;
	}
	
	/**
	 * @description 供货范围
	 * @author hzl
	 * @date 2018/7/16 13:20
	 */
	public String queryScopeList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String materialid = request.getParameter("materialid");
		List<MaterialCategory> list =  materialCategoryService.getHqlList(materialid);
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		for (MaterialCategory materialCategory : list) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("id", materialCategory.getUuid());
			m.put("text", materialCategory.getCategoryName());
			m.put("pid", "-1");
			m.put("type", "root");
			dataList.add(m);
		}
		CommonUtil.toJsonStr( ServletActionContext.getResponse(), dataList);
		return null;
	}
	
	public String qualityLibrary(){
		return ActionSupport.SUCCESS;
	}

	/**
	 * @description 校验用户名
	 * @author qinj
	 * @date 2018/2/5 22:11
	 */
	public void checkUsername() {
		// TODO
		if(StringUtils.isBlank(model.getUsername())) {
			AjaxUtils.error(AjaxCode.SERVER_ERROR, "用户名不能为空!");
			return ;
		}
		User user = userService.findUserByUserName(model.getUsername());
		if(user != null) {
			AjaxUtils.error(AjaxCode.REGISTER_USERNAME_EXIST);
			return ;
		}
		AjaxUtils.success();
	}

	/**
	 * @description 注册
	 * @author qinj
	 * @date 2018/2/5 22:08
	 */
	public void register() {
		// TODO
		if(StringUtils.isBlank(model.getUsername())) {
			AjaxUtils.error(AjaxCode.SERVER_ERROR, "用户名不能为空");
			return ;
		}
	/*	String verifyCode = request.getParameter("verifyCode");
		if(StringUtils.isBlank(verifyCode)) {
			AjaxUtils.error(AjaxCode.SERVER_ERROR, "请填写正确的验证码!");
			return ;
		}
		boolean check = smsVerifyCodeService.checkVerifyCode(model.getMobile(), verifyCode);
		if(!check) {
			AjaxUtils.error(AjaxCode.SERVER_ERROR, "请填写正确的验证码!");
			return ;
		}*/
		supplierService.register(model);
		AjaxUtils.success("注册成功!");
	}

	/**
	 * 供应商审核
	 */
	public void check() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String content = request.getParameter("content");
		User checkUser = (User) request.getSession().getAttribute("user");
		supplierService.check(model.getId(), model.getCheckStatus(), checkUser,content);
		AjaxUtils.success("审核成功!");
	}

	/**
	 * 详情
	 * @return
	 */
	public String detail() {
		ViewUserSupplier view = supplierService.findById(model.getUuid());
		ServletActionContext.getRequest().setAttribute("view", view);
		Menu menu = new Menu();
		menu.setMenuOpenStyle("0");
		ServletActionContext.getRequest().setAttribute("menu", menu);
		return ActionSupport.SUCCESS;
	}

	/**
	 * 分页列表
	 * @return
	 */

	public String loadSupplierForPager(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String search = request.getParameter("search");
		List searchList = new ArrayList();
		if (!StringUtils.isEmpty(search)){
			searchList = (List) JSON.Decode(search);
		}
		Menu menu = menuService.findOneMenuById("entityClsName", SupplierExt.class.getSimpleName());

		CommonUtil.toJsonStr(response,commonFacadeService.loadMapListForNotOrgPageSearch(menu.getUuid(),getpageInfo(),searchList));

		return null;
	}


	/**
	 * 获取附件
	 */
	public void getAttachments() {
		List<ViewAttachment> attachments = supplierService.findAttachments(model.getUuid());
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), attachments);
	}

	public void downloadAttachment() throws Exception {
		SupplierAttachment attachment = supplierAttachmentService.getAttachment(model.getId());
		String path = attachment.getFilePath();
		File file = new File(path);
		if(!file.exists()) {
			// TODO 文件不存在
			return ;
		}
		ServletActionContext.getResponse().setContentType( "application/x-msdownload");
		String fileName = URLEncoder. encode(attachment.getFileName(), "UTF-8");
		ServletActionContext.getResponse().setHeader( "Content-Disposition", "attachment;filename=" + fileName);
		FileInputStream inputStream = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		while (true) {
			int len = inputStream.read(buffer);
			if(len == -1)
				break ;
			ServletActionContext.getResponse().getOutputStream().write(buffer, 0, len);
		}
		inputStream.close();
	}
	
	/**
	*
	* @description 获取供应商所能报名的招标
	* @author qinj
	* @date 2018/3/6 23:19
	*/
	public void findTender() {
		// TODO
		User user = (User) ActionContext.getContext().getSession().get("user");
		htos.business.entity.supplier.view.Page<ViewSupplierTrader> page = getPage();
		supplierService.findTender(page, user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("data", page.getResult());
		CommonUtil.toJsonStr(response, map);
	}
	
	//获取邀请招标
	public void findInvite(){
		User user = (User) ActionContext.getContext().getSession().get("user");
		htos.business.entity.supplier.view.Page<ViewSupplierTrader> page = getPage();
		supplierService.findInvite(page, user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("data", page.getResult());
		CommonUtil.toJsonStr(response, map);
	}

	/**
	 * @description 招标公告详情
	 * @author qinj
	 * @date 2018/3/8 22:58
	 */
	public String getTradeDetail() {
		// TODO
		String id = request.getParameter("id");
		BiddingBulletin view = biddingBulletinService.findById(id);
		request.setAttribute("view", view);
		return ActionSupport.SUCCESS;
	}
	
	/**
	 * 
	 * @description 报名页面
	 * @author qinj
	 * @date 2018/3/9 23:06
	 */
	public String signup() {
		// TODO
		String supplierId = this.request.getParameter("supplier");
		String type = this.request.getParameter("type");
		String check = this.request.getParameter("check");
		if(StringUtils.isBlank(supplierId)) {
			// 供应商查看
			User user = (User) ActionContext.getContext().getSession().get("user");
			SupplierExt ext = supplierService.findByUser(user.getUuid());
			ViewUserSupplier view = supplierService.findById(ext.getUuid());
			ServletActionContext.getRequest().setAttribute("view", view);
			String id = request.getParameter("id");
			BiddingBulletin trader = biddingBulletinService.findById(id);
			request.setAttribute("trader", trader);
			SupplierSignup signup = supplierService.findMySignupByTrader(id, ext.getUuid());
			if(signup == null) {
				signup = new SupplierSignup();
			}
			signup.setCanCheck(false);
			ServletActionContext.getRequest().setAttribute("signup", signup);
		} else {
			// 管理员查看
			ViewUserSupplier view = supplierService.findById(supplierId);
			ServletActionContext.getRequest().setAttribute("view", view);
			String id = request.getParameter("id");
			BiddingBulletin trader = biddingBulletinService.findById(id);
			request.setAttribute("trader", trader);
			SupplierSignup signup = supplierService.findMySignupByTrader(id, supplierId);
			if(signup == null) {
				signup = new SupplierSignup();
			}

			if(StringUtils.equalsIgnoreCase("read", type)) {
				signup.setCanWrite(false);
			} else {
				signup.setCanWrite(true);
			}
			if(StringUtils.equalsIgnoreCase("check", check)) {
				signup.setCanCheck(true);
			} else {
				signup.setCanCheck(false);
			}

			ServletActionContext.getRequest().setAttribute("signup", signup);
		}

		Menu menu = new Menu();
		menu.setMenuOpenStyle("0");
		ServletActionContext.getRequest().setAttribute("menu", menu);


		return ActionSupport.SUCCESS;
	}

	/**
	 * @description 招标报名
	 * @author qinj
	 * @date 2018/3/8 23:28
	 */
	public void traderSignup() {
		// TODO
		User user = (User) ActionContext.getContext().getSession().get("user");
		String tabType = request.getParameter("tabType");
		try {
			supplierService.signup(model, user,tabType);
			AjaxUtils.success("报名成功!");
		} catch (RuntimeException e) {
			AjaxUtils.error(AjaxCode.SERVER_ERROR, e.getMessage());
		}
	}

	public void findNeedCheckSignup() {
		User user = (User) ActionContext.getContext().getSession().get("user");
		String query = request.getParameter("search");
		String supplierName = null;
		if(StringUtils.isNotBlank(query)) {
			JSONArray jsonArray = JSONArray.parseArray(query);
			if(!jsonArray.isEmpty()) {
				JSONObject jsonObject = jsonArray.getJSONObject(0);
				supplierName = jsonObject.getString("value");
			}
		}
//		String supplierName = request.getParameter("name");
		htos.business.entity.supplier.view.Page<ViewSupplierTrader> page = getPage();
		supplierService.findNeedCheckSignup(page, user, supplierName);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("data", page.getResult());
		CommonUtil.toJsonStr(response, map);
	}

	public void signCheck() {
		HttpServletRequest request = ServletActionContext.getRequest();
		User checkUser = (User) request.getSession().getAttribute("user");
		supplierService.signCheck(model.getId(), model.getCheckStatus(), checkUser);
		AjaxUtils.success("审核成功!");
	}

	public void enable() {
		String id = request.getParameter("id");
		supplierService.enable(id);
		AjaxUtils.success("设置成功!");
	}

	public void update() {
		String verifyCode = request.getParameter("verifyCode");
		/*if(StringUtils.isBlank(verifyCode)) {
			AjaxUtils.error(AjaxCode.SERVER_ERROR, "请填写正确的验证码!");
			return ;
		}
		boolean check = smsVerifyCodeService.checkVerifyCode(model.getMobile(), verifyCode);
		if(!check) {
			AjaxUtils.error(AjaxCode.SERVER_ERROR, "请填写正确的验证码!");
			return ;
		}*/
		try {
			supplierService.update(model);
			AjaxUtils.success("修改成功，请等待管理员审核!");
		} catch (RuntimeException e) {
			e.printStackTrace();
			log.error("", e);
			AjaxUtils.error(AjaxCode.SERVER_ERROR, e.getMessage());
		}
	}

	public void saveScop() {
		User user = (User) request.getSession().getAttribute("user");
		if (!StringUtils.isEmpty(model.getUuid())) {
			//更新
			GoodsScope scope = goodsScopeCommonFacadeService.getEntityByID(GoodsScope.class.getSimpleName(), model.getUuid());
			scope.setName(model.getName());
			scope.setUpdateDate(new Date());
			scope.setUpdateBy(user.getUuid());
			goodsScopeCommonFacadeService.update(scope);
		} else {
			//添加
			GoodsScope scope = new GoodsScope();
			scope.setCreateBy(user.getUuid());
			scope.setCreateDate(new Date());
			scope.setDelFlag("1");
			scope.setName(model.getName());
			goodsScopeCommonFacadeService.save(scope);
		}
		CommonUtil.toString(ServletActionContext.getResponse(), "<script>parent.goReload();</script>");
	}

	public String scopeDetail() {
		GoodsScope goodsScope = supplierService.getGoodsScope(model.getUuid());
		ServletActionContext.getRequest().setAttribute("entity", goodsScope);
		return ActionSupport.SUCCESS;
	}

	public void deleteScope() {
		supplierService.deleteScope(model.getUuid());
		AjaxUtils.success();
	}

	public void findScope() {
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), supplierService.findScope());
	}

	public void findPage() {
		String name = request.getParameter("name");
		String scopeId = request.getParameter("scopeId");
		String pass = request.getParameter("pass");
		String wfStatus =request.getParameter("wfStatus");
		htos.business.entity.supplier.view.Page<ViewUserSupplier> page = getPage();
		supplierService.findPage(page, name, scopeId,pass,wfStatus);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("data", page.getResult());
		CommonUtil.toJsonStr(response, map);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//
	//


	private ViewUserSupplier model;

	@Override
	public ViewUserSupplier getModel() {
		if(model == null)
			model = new ViewUserSupplier();
		return model;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public void setSupplierAttachmentService(SupplierAttachmentService supplierAttachmentService) {
		this.supplierAttachmentService = supplierAttachmentService;
	}

	public void setCommonFacadeService(CommonFacadeService<SupplierExt> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public void setBiddingBulletinService(BiddingBulletinService biddingBulletinService) {
		this.biddingBulletinService = biddingBulletinService;
	}

	public void setSmsVerifyCodeService(SmsVerifyCodeService smsVerifyCodeService) {
		this.smsVerifyCodeService = smsVerifyCodeService;
	}

	public void setGoodsScopeCommonFacadeService(CommonFacadeService<GoodsScope> goodsScopeCommonFacadeService) {
		this.goodsScopeCommonFacadeService = goodsScopeCommonFacadeService;
	}

	public MaterialCategoryService getMaterialCategoryService() {
		return materialCategoryService;
	}

	public void setMaterialCategoryService(
			MaterialCategoryService materialCategoryService) {
		this.materialCategoryService = materialCategoryService;
	}

	
}
