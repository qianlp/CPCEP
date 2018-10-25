package htos.coresys.action;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import htos.business.entity.supplier.*;
import htos.business.entity.supplier.view.ViewScope;
import htos.business.service.supplier.SupplierAttachmentService;
import htos.business.service.supplier.SupplierService;
import htos.business.utils.BusinessConstants;
import htos.common.util.CSVUtils;
import htos.common.util.FileCommonUtil;
import htos.coresys.entity.*;
import htos.coresys.service.CommonService;
import htos.coresys.service.OrgFacadeService;
import htos.coresys.util.CommonUtil;
import htos.coresys.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class OrgFacadeAction extends ActionSupport {
	private static final Logger log = Logger.getLogger(OrgFacadeAction.class);
	private static final long serialVersionUID = 1L;
	private OrgFacadeService orgFacadeService;
	private CommonService<UserImgConfig> commonService;
	private CommonService<UserToRole> roleComService;
	private User user;
	private Role role;
	private UserToRole userToRole;
	private File file;
	private String errorMessage;
	private Dept dept;
	private CommonService<Role> commonServicerole;
	private SupplierService supplierService;
	private SupplierAttachmentService supplierAttachmentService;
	
	
	//-----------------部门用户关联操作开始-------------------
	public String addUser(){
		String html =null;
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			String sameLevelNodeId = request.getParameter("sameLevelNodeId");
			user.setUserPassword(MD5Util.EncoderByMd5(user.getUserPassword()));
			orgFacadeService.addUser(user,sameLevelNodeId);
			html = "<script>alert('添加用户成功！');parent.goCloseDlg(\"oWinDlg\");</script>";
		}catch(Exception e){
			html = "<script>alert('添加用户失败！');parent.goCloseDlg(\"oWinDlg\");</script>";
			e.printStackTrace();
		}
		CommonUtil.toString(ServletActionContext.getResponse(), html);
		return null;
		
	}
	
	/**
	 * 登录验证
	 * @return
	 */
	public String login(){
		user.setUserPassword(MD5Util.EncoderByMd5(user.getUserPassword()));
		Map<String,Object> map =orgFacadeService.login(user);
		if(CommonUtil.isNullOrEmpty(map)) {
			errorMessage = "用户名或密码不正确";
			addFieldError("errorMessage", errorMessage);
			return "login";
		}
		user=(User)map.get("user");
		String status = map.get("status").toString();
		// TODO 供应商账号登录成功后跳转页面
		if(StringUtils.equals(status, BusinessConstants.ACCOUNT_CHECK_WAITING)) {
			// 等待审核
			addFieldError("errorMessage", "账号审核中!");
//			ServletActionContext.getRequest().setAttribute("status", status);
//			findSupplierInfo();
			return "login";
		} else if(StringUtils.equalsIgnoreCase(status, BusinessConstants.ACCOUNT_CHECK_FAILED)) {
			// 审核失败，返回信息修改页面
			addFieldError("errorMessage", "账号审核失败!");
			ServletActionContext.getRequest().setAttribute("status", status);
			findSupplierInfo();
			return "edit";
		}
		ActionContext.getContext().getSession().putAll(map);
		ActionContext.getContext().getSession().put("imgPath", "");

		List<UserImgConfig> imgList=commonService.getListByProperty("UserImgConfig", "userId", user.getUuid());
		for(UserImgConfig img:imgList){
			if(img.getStatus().equals("1")){
				ActionContext.getContext().getSession().put("imgPath", img.getImgConfig());
				break;
			}
		}

		Integer userType = user.getUserType();
		if(userType != null && userType == BusinessConstants.USER_TYPE_SUPPLIER)
			return "supplier";
		return ActionSupport.SUCCESS;
	}

	private void findSupplierInfo() {
		SupplierExt ext = supplierService.findByUser(user.getUuid());
		ServletActionContext.getRequest().setAttribute("ext", ext);
		SupplierBilling billing = supplierService.findBillig(ext.getUuid());
		ServletActionContext.getRequest().setAttribute("billing", billing);
		// 附件信息
		List<SupplierAttachment> attachments = supplierAttachmentService.findBySupplier(ext.getUuid());
		List<SupplierAttachment> otherFiles = new ArrayList<SupplierAttachment>();
		ServletActionContext.getRequest().setAttribute("otherFiles", otherFiles);
		List<SupplierAttachment> performanceProveFiles = new ArrayList<SupplierAttachment>();
		ServletActionContext.getRequest().setAttribute("performanceProveFiles", performanceProveFiles);
		List<SupplierAttachment> performanceFiles = new ArrayList<SupplierAttachment>();
		ServletActionContext.getRequest().setAttribute("performanceFiles", performanceFiles);
		if(attachments != null && !attachments.isEmpty()) {
			for (SupplierAttachment attachment : attachments) {
				if(attachment.getType() == BusinessConstants.SUPPLIER_ATTACHEMENT_PERFORMANCE) {
					performanceFiles.add(attachment);
				} else if(attachment.getType() == BusinessConstants.SUPPLIER_ATTACHEMENT_PERFORMANCE_PROVE) {
					performanceProveFiles.add(attachment);
				} else if(attachment.getType() == BusinessConstants.SUPPLIER_ATTACHEMENT_OTHER) {
					otherFiles.add(attachment);
				} else if(attachment.getType() == BusinessConstants.SUPPLIER_ATTACHEMENT_LICENSE) {
					ServletActionContext.getRequest().setAttribute("licenseFile", attachment);
				} 
				
//				else if(attachment.getType() == BusinessConstants.SUPPLIER_ATTACHEMENT_TAX_CERTIFICATE) {
//					ServletActionContext.getRequest().setAttribute("taxCertificateFile", attachment);
//				} else if(attachment.getType() == BusinessConstants.SUPPLIER_ATTACHEMENT_ORG_CODE) {
//					ServletActionContext.getRequest().setAttribute("orgCodeFile", attachment);
//				}
				
				else if(attachment.getType() == BusinessConstants.SUPPLIER_ATTACHEMENT_CERTIFICATE) {
					ServletActionContext.getRequest().setAttribute("certificateFile", attachment);
				}
			}
		}

		// 供货范围
		List<GoodsScope> scopes = supplierService.findGoodsScope();
		List<SupplierGoodsScope> useScopes = supplierService.findMyScope(ext.getUuid());
		Map<String, SupplierGoodsScope> map = new HashMap<String, SupplierGoodsScope>();
		if(useScopes != null && !useScopes.isEmpty()) {
			for (SupplierGoodsScope useScope : useScopes) {
				map.put(useScope.getScope().getUuid(), useScope);
			}
		}
		List<ViewScope> list = new ArrayList<ViewScope>();
		for (GoodsScope scope : scopes) {
			ViewScope view = null;
			if(map.containsKey(scope.getUuid()))
				view = new ViewScope(scope.getUuid(), scope.getName(), true);
			else
				view = new ViewScope(scope.getUuid(), scope.getName(), false);
			list.add(view);
		}
		ServletActionContext.getRequest().setAttribute("scopes", list);
		ServletActionContext.getRequest().setAttribute("content", user.getContent());
	}

	/**
	 * 登录验证
	 * @return
	 */
	public void loginApp(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String userName = request.getParameter("userName");
		String userPassWord = request.getParameter("userPassWord");
		if(user==null){
			user=new User();
			user.setUserName(userName);
			user.setUserPassword(userPassWord);
		}
		Map<String,Object> map =orgFacadeService.login(user);
		CommonUtil.toJsonStrData(ServletActionContext.getResponse(), map);
	}
	

	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public UserToRole getUserToRole() {
		return userToRole;
	}

	public void setUserToRole(UserToRole userToRole) {
		this.userToRole = userToRole;
	}

	public String deleteDept(){
		String deptId = ServletActionContext.getRequest().getParameter("deptId");
		orgFacadeService.deleteDept(deptId);
		return null;
		
	}
	
	public String findOrgTree(){
		CommonUtil.toJsonStr( ServletActionContext.getResponse(), orgFacadeService.findOrgTree());
		return null;
		
	}
	
	//根据部门查询用户
	public String findUserIdByDept(){
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), orgFacadeService.findUserIdByDept(dept));
		return null;
	}
	//-----------------部门用户关联操作结束-------------------
	
	// -----------------角色用户关联操作开始-------------------
	
		public String saveUserToRole(){
			HttpServletRequest request = ServletActionContext.getRequest();
			String userList = request.getParameter("Members");
			orgFacadeService.saveUserToRole(role,userList);
			List<UserToRole> rtList=roleComService.getHqlList("from UserToRole where user.uuid='' or user.uuid=null ");
			for(UserToRole u:rtList){
				roleComService.delete(u);
			}
			String html = "<script>parent.roleLoad();parent.CloseWindow();</script>";
			CommonUtil.toString(ServletActionContext.getResponse(), html);
			return null;
		}
		
		//根据角色查询用户
		public String findUserIdByRole(){
			CommonUtil.toJsonStr(ServletActionContext.getResponse(), orgFacadeService.findUserIdByRole(role));
			return null;
		}
		
		//查询角色后查询用户
		public String findRoleByUserId() throws UnsupportedEncodingException{
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Role rolet = commonServicerole.getEntityByProperty("Role", "roleName", URLDecoder.decode(role.getRoleName(), "utf-8"));
			if(rolet!=null){
				list=orgFacadeService.findUserIdByRole(rolet);
			}
			CommonUtil.toJsonStr(ServletActionContext.getResponse(), list);
			return null;
		}
		
		public String updateUserToRole() throws Exception{
			HttpServletRequest request = ServletActionContext.getRequest();
			User user = (User) request.getSession().getAttribute("user");
			String addUserList = request.getParameter("Members");
			userToRole= new UserToRole();
			userToRole.setRole(role);
			orgFacadeService.updateUserToRole(role,userToRole,addUserList,user.getUserPerEname());
			List<UserToRole> rtList=roleComService.getHqlList("from UserToRole where user.uuid='' or user.uuid=null ");
			for(UserToRole u:rtList){
				roleComService.delete(u);
			}
			String html = "<script>parent.roleLoad();parent.CloseWindow();</script>";
			CommonUtil.toString(ServletActionContext.getResponse(), html);
			return null;
		}
		
		//查询所有的角色
		public String findAllRoleUserJson(){
			CommonUtil.toJsonStr(ServletActionContext.getResponse(), orgFacadeService.findAllRoleUserJson());
			return null;
		}
		
		//根据用户查询角色
		public String findUserRoleJson(){
			CommonUtil.toJsonStr(ServletActionContext.getResponse(), orgFacadeService.findUserRoleJson(user));
			return null;
		}
		// -----------------角色用户关联操结束-------------------
	/**
	 * 用户组织模板下载
	 */
	public void downloadFile(){
		String path=ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/")+"downloadFile/组织机构模版.csv";
		FileCommonUtil.dowloadFile("组织机构模版.csv",path, ServletActionContext.getResponse());
	}
	
	/**
	 * 导入用户组织数据
	 * @return
	 */
	public String saveImportOrgUser(){
		HttpServletRequest request =ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute("user");
		Map<String, Object> map = new ConcurrentHashMap<String, Object>(2);
		List<String> list  = CSVUtils.importCsv(file);
		map = orgFacadeService.saveImportOrgUser(list,user.getUserPerEname());
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
		log.info("======importOrgUser===========组织人员数据导入=====");
		return null;
	}
	/**
	 * 导出用户组织数据
	 * @return
	 */
	public String exportOrgUser(){
		HttpServletResponse response =ServletActionContext.getResponse();
		orgFacadeService.exportOrgUser(response);
		return null;
	}
	
	public OrgFacadeService getOrgFacadeService() {
		return orgFacadeService;
	}

	public void setOrgFacadeService(OrgFacadeService orgFacadeService) {
		this.orgFacadeService = orgFacadeService;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public CommonService<UserImgConfig> getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService<UserImgConfig> commonService) {
		this.commonService = commonService;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public CommonService<Role> getCommonServicerole() {
		return commonServicerole;
	}

	public void setCommonServicerole(CommonService<Role> commonServicerole) {
		this.commonServicerole = commonServicerole;
	}

	public CommonService<UserToRole> getRoleComService() {
		return roleComService;
	}

	public void setRoleComService(CommonService<UserToRole> roleComService) {
		this.roleComService = roleComService;
	}
	public String findOrgTreeWithoutUser(){
		CommonUtil.toJsonStr( ServletActionContext.getResponse(), orgFacadeService.findOrgTreeWithoutUser());
		return null;
		
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public void setSupplierAttachmentService(SupplierAttachmentService supplierAttachmentService) {
		this.supplierAttachmentService = supplierAttachmentService;
	}
}
