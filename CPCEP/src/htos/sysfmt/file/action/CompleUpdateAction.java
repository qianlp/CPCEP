package htos.sysfmt.file.action;

import htos.common.entity.PageInfo;
import htos.common.util.DateUtil;
import htos.common.util.FileCommonUtil;
import htos.common.util.FileUtilBetas;
import htos.common.util.swftools.ConStant;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.util.CommonUtil;
import htos.sysfmt.file.entity.CompleUpdateModel;
import htos.sysfmt.file.service.CompleUpdateService;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class CompleUpdateAction extends ActionSupport implements ModelDriven<CompleUpdateModel>{
	private static final long serialVersionUID = 7815600244972772163L;
	private CompleUpdateService compleUpdateService;
	private CompleUpdateModel compleUpdateModel;
	private CommonFacadeService<CompleUpdateModel> commonFacadeService;
	
	public String findCompleUpdateById(){
		compleUpdateModel = commonFacadeService.getEntityByID("CompleUpdateModel", ServletActionContext.getRequest().getParameter("uuid"));
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), compleUpdateModel);
		return null;
	}
	
	//下载附件
	public String findDowloadCompleUpdateById(){
		compleUpdateModel = commonFacadeService.getEntityByID("CompleUpdateModel", ServletActionContext.getRequest().getParameter("uuid"));
		String path = compleUpdateModel.getProfilepath();
		String fileName = compleUpdateModel.getProfilename();
		FileCommonUtil.dowloadFile(fileName, path, ServletActionContext.getResponse());
		return null;
	}
	
	public String findAllParentTaskUIDJson(){
		String parentTaskUID = ServletActionContext.getRequest().getParameter("parentTaskUID");
		List<CompleUpdateModel> list = compleUpdateService.findAllParentTaskUIDJson(parentTaskUID);
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), list);
		return null;
	}
	
	public String loadListCompleForPage() {
		Map<String, Object> map = new HashMap<String, Object>(2);
		HttpServletRequest request = ServletActionContext.getRequest();
		String model = "CompleUpdateModel";
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		PageInfo pageInfo = new PageInfo(pageIndex, pageSize, sortField, sortOrder);
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds"); 
		String fid = request.getParameter("fid");
		String pid = request.getParameter("pid");
		compleUpdateModel.setPid(pid);
		compleUpdateModel.setFid(fid);
		if(!StringUtils.isEmpty(fid) || !StringUtils.isEmpty(pid)){
			map = compleUpdateService.loadListForPage(model, pageInfo,orgIds,compleUpdateModel);
		}
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),map);
		return null;
	}
	
	//上传文件
	public String compleUploadFile(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String parentTaskUID = request.getParameter("ID");
		try {
			String filename = request.getParameter("Filename");//文件名
			MultiPartRequestWrapper mw = (MultiPartRequestWrapper) request;
			File temppath = mw.getFiles("Filedata")[0];//临时路径
			String path = session.getServletContext().getRealPath("/")+ConStant.UPLOAD_BASE_DIR+"/"+DateUtil.DateConversion8String(new Date());//目标路径
			String filenameNew = DateUtil.DateConversion14String(new Date())+DateUtil.getDatecurrentTime()+filename.substring(filename.lastIndexOf("."));//文件名
			String size = FileCommonUtil.getUpload(path, filenameNew, temppath);
			//保存上传信息
			compleUpdateService.saveCompleUploadFile(parentTaskUID,filename,path,filenameNew,size,user.getUserName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//删除附件(先删文件再删数据)
	public String deleteFile(){
		Map<String, Object> map = new HashMap<String, Object>(2);
		String parentTaskUID = ServletActionContext.getRequest().getParameter("parentTaskUID");
		compleUpdateModel = commonFacadeService.getEntityByID("CompleUpdateModel",parentTaskUID);
		FileCommonUtil.getDelete(compleUpdateModel.getProfilepath());
		commonFacadeService.deleteId("CompleUpdateModel", "uuid",parentTaskUID );
		map.put("success", true);
		map.put("data", "删除成功!");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),map);
		return null;
	}
	
	//预览上传附件
	public String getViewUploadSwf(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		compleUpdateModel = commonFacadeService.getEntityByID("CompleUpdateModel", request.getParameter("uuid"));
		String path =  compleUpdateModel.getProfilepath();//路径
		String pathname = path.substring(path.indexOf(ConStant.UPLOAD_BASE_DIR));//取得目录位置
		String postfix = pathname.substring(pathname.indexOf(".")+1);
		String success="";
		if("pdf".equals(postfix) || "doc".equals(postfix) || "docx".equals(postfix) || "xls".equals(postfix) 
				|| "pptx".equals(postfix) || "xlsx".equals(postfix) || "ppt".equals(postfix) || 
				"txt".equals(postfix) ||  "odt".equals(postfix)){
			path = FileUtilBetas.getFilePrefix2(path)+".swf";
			success ="swfView";
		}else{
			success ="imageView";
		}
		String pathrep = path.replace("\\", "/");
		String swfpath = pathrep.substring(pathrep.indexOf(session.getServletContext().getRealPath("/")));
		session.setAttribute("swfpath", swfpath);
		return success;
	}
	
	//更新归档
	public String updateCompleFile() throws Exception{
		compleUpdateModel = commonFacadeService.getEntityByID("CompleUpdateModel", ServletActionContext.getRequest().getParameter("uuid"));
		HttpSession session = ServletActionContext.getRequest().getSession();
		User user = (User) session.getAttribute("user");
		compleUpdateModel.setUpdateBy(user.getUserName());
		compleUpdateModel.setUpdateDate(new Date());
		compleUpdateModel.setCuNo(ServletActionContext.getRequest().getParameter("cuNo"));
		compleUpdateModel.setProversion(ServletActionContext.getRequest().getParameter("proversion"));
		compleUpdateModel.setStatus(ServletActionContext.getRequest().getParameter("status"));
		compleUpdateModel.setDelFlag("1");
		compleUpdateService.updateCompleFile(compleUpdateModel);
		return null;
	}
	
	//更新发送
	public String updateCompleUpdate() throws Exception{
		compleUpdateService.updateCompleFile(compleUpdateModel);
		return "success";
	}
	
	//查询分页
	public String loadMapListForPageHead() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String model = "CompleUpdateModel";
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		PageInfo pageInfo = new PageInfo(pageIndex, pageSize, sortField, sortOrder);
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), compleUpdateService.loadMapListForPageHead(model, pageInfo,orgIds));
		return null;
	}
	
	public CompleUpdateService getCompleUpdateService() {
		return compleUpdateService;
	}
	public void setCompleUpdateService(CompleUpdateService compleUpdateService) {
		this.compleUpdateService = compleUpdateService;
	}

	public CompleUpdateModel getCompleUpdateModel() {
		return compleUpdateModel;
	}

	public void setCompleUpdateModel(CompleUpdateModel compleUpdateModel) {
		this.compleUpdateModel = compleUpdateModel;
	}

	@Override
	public CompleUpdateModel getModel() {
		if(CommonUtil.isNullOrEmpty(compleUpdateModel)){
			compleUpdateModel = new CompleUpdateModel();
		}
		return compleUpdateModel;
	}

	public CommonFacadeService<CompleUpdateModel> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(CommonFacadeService<CompleUpdateModel> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	
}
