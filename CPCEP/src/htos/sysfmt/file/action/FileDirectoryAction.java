package htos.sysfmt.file.action;


import htos.common.util.CopyUtil;
import htos.common.util.JsonUtil;
import htos.common.util.StringUtil;
import htos.coresys.entity.User;
import htos.coresys.service.CommonService;
import htos.coresys.util.CommonUtil;
import htos.sysfmt.file.entity.FileDirectory;
import htos.sysfmt.file.entity.FileDirectorynewModel;
import htos.sysfmt.file.service.FileDirectoryService;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class FileDirectoryAction extends ActionSupport implements ModelDriven<FileDirectory>{

	private static final long serialVersionUID = 1L;
	
	private FileDirectoryService fileDirectoryService;
	private FileDirectory fileDirectory;
	private FileDirectorynewModel fileDirectorynewModel;
	private String data;
	private CommonService<FileDirectorynewModel > commonService;

	//获取所有模板目录文件
	public String fileDirectoryList() {
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),fileDirectoryService.fileDirectoryList());
		return null;
	}
	
	//根据权限获取模板目录文件
	public String fileDirectoryListByPower() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),fileDirectoryService.fileDirectoryList(orgIds));
		return null;
	}
	
	//批量模板保存或更新
	public String fileDirectoryUpdate(){
		if(data.isEmpty()){
			return null;
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		User user=(User)request.getSession().getAttribute("user");
		List<FileDirectory> lists = JsonUtil.getJsonList(data,fileDirectory);
		for (FileDirectory fileDirectory : lists) {
			if(!StringUtils.isEmpty(fileDirectory.getUuid())){//更新菜单
				if(fileDirectory.getCreateDate()==null){
					fileDirectory.setCreateBy(user.getUserName());
					fileDirectory.setCreateDate(new Date());
				}
				fileDirectory.setUpdateBy(user.getUserName());
				fileDirectory.setUpdateDate(new Date());
				fileDirectoryService.updateFileDirectory(fileDirectory);
			}else{
				fileDirectory.setCreateBy(user.getUserName());
				fileDirectory.setCreateDate(new Date());
				fileDirectoryService.addFileDirectory(fileDirectory);
			}
		}
		/*JSONObject jobt =new JSONObject().fromObject(JsonUtil.getjsonObject(data));
		fileDirectory = (FileDirectory) JSONObject.toBean(jobt, FileDirectory.class);
		
		if(!StringUtils.isEmpty(fileDirectory.getUuid())){//更新主菜单
			fileDirectoryService.updateFileDirectory(fileDirectory);
		}else{
			fileDirectoryService.addFileDirectory(fileDirectory);
		}
		
		List<FileDirectory> list = fileDirectory.getChildren();
		if(list.size()>0){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object obj =  iterator.next();
				try {
					CopyUtil.copyToObj(fileDirectory, obj);
					if(!StringUtils.isEmpty(fileDirectory.getUuid())){//更新子菜单
						fileDirectoryService.updateFileDirectory(fileDirectory);
					}else{
						fileDirectoryService.addFileDirectory(fileDirectory);
					}
				} catch (IntrospectionException e) {
					e.printStackTrace();
				}
			}
		}*/
		return null;
	}
	
	/**
	 * 批量模板删除菜单
	 * @return
	 */
	public String fileDirectoryDelete(){
		String uuid = ServletActionContext.getRequest().getParameter("uuid");
		if(!StringUtils.isEmpty(uuid)){
			String[] str = uuid.split("\\^");
			for (String struuid : str) {
				fileDirectory =	fileDirectoryService.fileDirectory(struuid);
				if(fileDirectory!=null){
					fileDirectoryService.deleteFileDirectory("uuid",fileDirectory.getUuid());
					fileDirectoryService.deleteFileDirectory("parentTaskUID",fileDirectory.getUID());
				}
			}
		}
		return null;
	}
	
	//查询模板文件目录树
	public String findFileDirectoryTree(){
		CommonUtil.toJsonStr( ServletActionContext.getResponse(), fileDirectoryService.findFileDirectoryTree());
		return null;
	}
	
	
	//根据新建项目获取项目目录
	public String fileDirectoryProList() throws IntrospectionException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String prjID = request.getParameter("prjID");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),fileDirectoryService.fileDirectoryProList(prjID));
		return null;
	}
	
	//根据权限获取新目录文件(如果没传递项目则加载自己所有的，如果传递项目则只加载项目的)
	public String fileNewDirectoryListByPower() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");
		String hql="from FileDirectorynewModel where userId in ('"+orgIds.replace(",", "','")+"') ";//获取创建人自己的数据
		String prjID = request.getParameter("prjID");
		if(!StringUtil.isEmpty(prjID)){
			hql+=" and prjID='"+prjID+"' ";
		}
		hql+="  order by uuid asc ";
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),commonService.getHqlList(hql));
		return null;
	}
	
	//根据权限项目获取自己和按人员角色分配的新目录文件
	public String fileNewDirOrgListByPower() {
		List<FileDirectorynewModel> list = fileDirectoryService.fileNewDirOrgListByPower();
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),list);
		return null;
	}
	
	//批量保存或更新新目录
	public String fileNewDirectoryUpdate() throws Exception{
		if(data.isEmpty()){
			return null;
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String prjID = request.getParameter("prjID");
		User user=(User)request.getSession().getAttribute("user");
		List<FileDirectorynewModel> lists = JsonUtil.getJsonList(data,new FileDirectorynewModel());
		for (FileDirectorynewModel fileDirectory : lists) {
			fileDirectory.setPrjID(prjID);
			if(!StringUtils.isEmpty(fileDirectory.getUuid())){//更新菜单
				fileDirectorynewModel =	commonService.get(FileDirectorynewModel.class, fileDirectory.getUuid());
				if(fileDirectorynewModel!=null){
					CopyUtil.copyToObj(fileDirectorynewModel, fileDirectory);
					fileDirectorynewModel.setLookRole(fileDirectory.getLookRole());
					fileDirectorynewModel.setLookRolesId(fileDirectory.getLookRolesId());
					fileDirectorynewModel.setLookPerson(fileDirectory.getLookPerson());
					fileDirectorynewModel.setLookNamesId(fileDirectory.getLookNamesId());
					fileDirectorynewModel.setDocLink(fileDirectory.getDocLink());
					fileDirectorynewModel.setRemark(fileDirectory.getRemark());
					fileDirectorynewModel.setRelationMenu(fileDirectory.getRelationMenu());
					fileDirectorynewModel.setRelationMenuID(fileDirectory.getRelationMenuID());
					fileDirectorynewModel.setUpdateBy(user.getUserName());
					fileDirectorynewModel.setUpdateDate(new Date());
					commonService.update(fileDirectorynewModel);
				}else{
					fileDirectory.setCreateBy(user.getUserName());
					fileDirectory.setCreateDate(new Date());
					fileDirectory.setUserId(user.getUuid());
					commonService.save(fileDirectory);
				}
			}else{
				fileDirectory.setCreateBy(user.getUserName());
				fileDirectory.setCreateDate(new Date());
				fileDirectory.setUserId(user.getUuid());
				commonService.save(fileDirectory);
			}
		}
		return null;
	}
	
	/**
	 * 批量删除新目录
	 * @return
	 */
	public String fileDirectoryNewDelete(){
		String uuid = ServletActionContext.getRequest().getParameter("uuid");
		if(!StringUtils.isEmpty(uuid)){
			String[] str = uuid.split("\\^");
			for (String struuid : str) {
				fileDirectorynewModel =	commonService.get(FileDirectorynewModel.class, struuid);
				if(fileDirectorynewModel!=null){
					commonService.delete(fileDirectorynewModel);
					commonService.deleteId(fileDirectorynewModel.getClass().getSimpleName(),"parentTaskUID",fileDirectorynewModel.getUID());
				}
			}
		}
		return null;
	}
	
	//根据项目和权限查询目录
	public String getFileDirectoryTree(){
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");
		String prjID = request.getParameter("prjID");
		if(!StringUtil.isEmpty(prjID)){
			dataList= fileDirectoryService.findFileDirectoryTree(orgIds,prjID);
		}
		CommonUtil.toJsonStr( ServletActionContext.getResponse(),dataList);
		return null;
	}
	
	//项目文件分配权限
	public String getPrjFileTree() throws IntrospectionException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String prjID = request.getParameter("prjID");
		CommonUtil.toJsonStr( ServletActionContext.getResponse(),fileDirectoryService.prjFileDirectoryProList(prjID));
		return null;
	}
	
	//项目文件分配权限批量更新新目录和文件权限
	public String prjDirFileUpdate() throws Exception{
		if(data.isEmpty()){
			return null;
		}
		fileDirectoryService.updatePrjDirFileUpdate(data);
		return null;
	}
	
	public FileDirectoryService getFileDirectoryService() {
		return fileDirectoryService;
	}

	public void setFileDirectoryService(FileDirectoryService fileDirectoryService) {
		this.fileDirectoryService = fileDirectoryService;
	}

	public FileDirectory getFileDirectory() {
		return fileDirectory;
	}

	public void setFileDirectory(FileDirectory fileDirectory) {
		this.fileDirectory = fileDirectory;
	}
	@Override
	public FileDirectory getModel() {
		if(CommonUtil.isNullOrEmpty(fileDirectory)){
			fileDirectory = new FileDirectory();
		}
		return fileDirectory;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

	public FileDirectorynewModel getFileDirectorynewModel() {
		return fileDirectorynewModel;
	}

	public void setFileDirectorynewModel(FileDirectorynewModel fileDirectorynewModel) {
		this.fileDirectorynewModel = fileDirectorynewModel;
	}

	public CommonService<FileDirectorynewModel> getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService<FileDirectorynewModel> commonService) {
		this.commonService = commonService;
	}
	
}
