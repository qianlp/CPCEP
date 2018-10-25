package htos.sysfmt.file.action;

import htos.common.entity.PageInfo;
import htos.common.util.ClassModelUtil;
import htos.common.util.CopyUtil;
import htos.common.util.DateUtil;
import htos.common.util.FileCommonUtil;
import htos.common.util.FileUtilBetas;
import htos.common.util.StringUtil;
import htos.common.util.swftools.SwfToolsUtil;
import htos.coresys.entity.Menu;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.CommonService;
import htos.coresys.service.MenuService;
import htos.coresys.service.RoleService;
import htos.coresys.util.CommonUtil;
import htos.sysfmt.file.entity.AdenexaModel;
import htos.sysfmt.file.entity.FileDirectory;
import htos.sysfmt.file.entity.FileDirectorynewModel;
import htos.sysfmt.file.service.AdenexaService;
import htos.sysfmt.file.service.FileDirectoryService;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
public class AdenexaAction extends ActionSupport implements ModelDriven<AdenexaModel> {
	private final static Logger log = Logger.getLogger(AdenexaAction.class);
	private static final long serialVersionUID = 8023822012891157011L;
	private AdenexaService adenexaService;
	private AdenexaModel adenexaModel;
	private CommonFacadeService<AdenexaModel> commonFacadeService;
	private CommonFacadeService<Object> commonObjService;
	private FileDirectoryService fileDirectoryService;
	private CommonService<FileDirectorynewModel> commonServicenewFile;
	private MenuService menuService;
	private RoleService roleService;
	private File file;// 文件临时路径
	// 提交过来的file的名字
	private String fileFileName;

	// 提交过来的file的MIME类型
	private String fileContentType;
	
	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("UploadPath");
	public final static  String FILE_PATH=getSessionattachmenttitle("FILE_PATH");//文件上传路径
	public final static  String CAD_DWG_PATH=getSessionattachmenttitle("CAD_DWG_PATH");
	public final static  String CAD_OCF_PATH=getSessionattachmenttitle("CAD_OCF_PATH");
	public final static  String CAD_PATH=getSessionattachmenttitle("CAD_PATH");//CAD文件存储位置
	
	public void updateAdenexa(){
		adenexaModel=commonFacadeService.getEntityByID(adenexaModel.getClass().getSimpleName(), ServletActionContext.getRequest().getParameter("uuid"));
		adenexaModel.setStatus(ServletActionContext.getRequest().getParameter("status"));
		adenexaModel.setCuNo(ServletActionContext.getRequest().getParameter("cuNo"));
		adenexaModel.setVersion(ServletActionContext.getRequest().getParameter("version"));
		commonFacadeService.update(adenexaModel);
	}
	public void updateAdenexaVersion(){
		String listStr = ServletActionContext.getRequest().getParameter("param");
		List<AdenexaModel> list = JSON.parseArray(listStr, AdenexaModel.class);
		if(list!=null){
			for(AdenexaModel model: list){
				adenexaService.updateAdenexaVersion(model.getUuid(),model.getVersion());
			}
		}
	}
	public void findAllAdenexaJson() {
		String prjID = ServletActionContext.getRequest().getParameter("prjID");
		String parentDocId = ServletActionContext.getRequest().getParameter("parentDocId");
		String area = ServletActionContext.getRequest().getParameter("area");
		adenexaModel=new AdenexaModel();
		adenexaModel.setPrjID(prjID);
		adenexaModel.setParentDocId(parentDocId);
		adenexaModel.setArea(area);
		List<AdenexaModel> list = adenexaService.findAllAdenexaListJson(adenexaModel);
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), list);
		//return "success";
	}

	// 附件预览
	public String findViewAdenexaById() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		adenexaModel = commonFacadeService.getEntityByID("AdenexaModel",
				request.getParameter("uuid"));
		String path = adenexaModel.getFilepath();// 路径
		String postfix = path.substring(path.lastIndexOf(".") + 1);
		String success = "";
		if ("pdf".equals(postfix) || "doc".equals(postfix)
				|| "docx".equals(postfix) || "xls".equals(postfix)
				|| "pptx".equals(postfix) || "xlsx".equals(postfix)
				|| "ppt".equals(postfix) || "txt".equals(postfix)
				|| "odt".equals(postfix)) {
			path = FileUtilBetas.getFilePrefix2(path) + ".swf";
			success = "swfView";
		} else {
			success = "imageView";
		}
		session.setAttribute("swfpath", request.getContextPath()+"/profile/fileViewOut.action?uuid="+request.getParameter("uuid"));
		return success;
	}

	// 附件下载
	public String findDowloadAdenexaById() {
		String uuid = ServletActionContext.getRequest().getParameter("uuid");
		adenexaModel = adenexaService.findViewAdenexaById(uuid);
		FileCommonUtil.dowloadFile(adenexaModel.getFilename(),
				FILE_PATH+"\\"+adenexaModel.getFilepath(), ServletActionContext.getResponse());
		return null;
	}
	
	public void fileViewOut(){
		HttpServletRequest request = ServletActionContext.getRequest();
		adenexaModel = commonFacadeService.getEntityByID("AdenexaModel",
				request.getParameter("uuid"));
		String path = adenexaModel.getFilepath();// 路径
		String postfix = path.substring(path.lastIndexOf(".") + 1);
		if ("pdf".equals(postfix) || "doc".equals(postfix)
				|| "docx".equals(postfix) || "xls".equals(postfix)
				|| "pptx".equals(postfix) || "xlsx".equals(postfix)
				|| "ppt".equals(postfix) || "txt".equals(postfix)
				|| "odt".equals(postfix)) {
			path = FileUtilBetas.getFilePrefix2(path) + ".swf";
		}
		FileCommonUtil.dowloadFile(adenexaModel.getFilename(),FILE_PATH+"\\"+path, ServletActionContext.getResponse());
	}
	
	//WebCAD在线查看
	public void webCADLook(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		
		String uuid = request.getParameter("uuid");
		adenexaModel = adenexaService.findViewAdenexaById(uuid);
		File oFile = new File(adenexaModel.getFilepath());
	  //  File tFile = new File("c:\\WebCAD\\files\\dwg");
	    File tFile = new File(CAD_DWG_PATH);
	    try {
	    	if(!tFile.exists()){
	    		tFile.mkdirs();
	    	}
			FileUtilBetas.copyFile(FILE_PATH+"\\"+adenexaModel.getFilepath(), CAD_DWG_PATH+"/"+adenexaModel.getFilenameNew());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		JsonObject objectInput = new JsonObject();
		objectInput.addProperty("path", ""); // 设置图纸目录。如果在WebCADManager.exe里面设置的文件位置是C:\test,那么图纸目录应是C:\test\dwg\1001。
		objectInput.addProperty("name", adenexaModel.getFilenameNew()); // test.dwg:位于path指定的路径中
		objectInput.addProperty("ocf", adenexaModel.getFilenameNew()+".ocf"); // 生成ocf文件名称。依据上面path的例子，应生成到C:\test\ocf\1001\test.ocf.
		objectInput.addProperty("utoken", ""); // 暂时不用
		objectInput.addProperty("taskclass", "MakeOcf"); // 任务名称：参见文档
		objectInput.addProperty("id", new Integer((int)(Math.random()*100001)));
		
		JsonObject object = new JsonObject();
		object.addProperty("status", new Integer(0)); // 状态号：返回的执行结果状态号
		object.addProperty("host", "MakeOcf"); // MakeOcf：应用名，自己在webcadcommandmanager.exe中配置。
		object.addProperty("input", objectInput.toString());// 任务内容：这里是MakeOcf的例子，更多见文档
		object.addProperty("ouput", ""); // 返回的信息：具体参见文档。
		
		int nPort = 3181;
		Socket Client=null;
		try {
			Client = new Socket("127.0.0.1",nPort);
			if (!Client.isConnected())
				return;

			Writer writer = new OutputStreamWriter(Client.getOutputStream());

			// 获取json任务数据，并发送任务。字节流要用utf8编码并用0x0结束。
			String sutf8 = new String(object.toString().getBytes("utf8"));
			writer.write(sutf8);
			writer.write(0x0); // ！！！写一个结束标识，一起发送给服务器。
			writer.flush();

			// 等待并读取服务器返回数据
			int nLen;
			StringBuffer strbuf = new StringBuffer();
			byte bbuf[] = new byte[1024];

			// bbuf = Client.getInputStream().read();
			if ((nLen = Client.getInputStream().read(bbuf)) != -1) {
				byte btemp[] = new byte[nLen];
				for (int i = 0; i < nLen; i++)
					btemp[i] = bbuf[i];
				String strres = new String(btemp, "utf8");
				strbuf.append(strres);
			}

			//
			System.out.println("下面是接收到的服务器返回数据");
			System.out.print(strbuf);
			
			//tFile = new File("c:\\WebCAD\\files\\ocf\\"+adenexaModel.getFilenameNew()+".ocf");
			tFile = new File(CAD_OCF_PATH+"/"+adenexaModel.getFilenameNew()+".ocf");
			
			//文件存储的路径  
            String storePath =session.getServletContext().getRealPath("/upload/cad");
            //String storePath =CAD_PATH;
            System.out.println("-----------------------------");
            System.out.println(storePath);
            FileUtilBetas.copyFile(CAD_OCF_PATH+"/"+adenexaModel.getFilenameNew()+".ocf", storePath+"/"+adenexaModel.getFilenameNew()+".ocf");
			JsonObject object1 = new JsonObject();
			object1.addProperty("taskId", String.valueOf(Math.random()*100001));
			object1.addProperty("status", new Integer(1));
			object1.addProperty("msg", "OK");
			object1.addProperty("resurl", request.getContextPath()+"/upload/cad/"+adenexaModel.getFilenameNew()+".ocf");
			//object1.addProperty("resurl", CAD_PATH+"/"+adenexaModel.getFilenameNew()+".ocf");
			
			object1.addProperty("waterurl", "");
			
			CommonUtil.toString(ServletActionContext.getResponse(), object1.toString());
			// 结束任务
			writer.close();
			Client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 上传附件文件
	public void adenexUploadFile() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("user");
		String pid = request.getParameter("ID");
		
		try {
			String subPath=DateUtil.DateConversion8String(new Date());
			String path = FILE_PATH+"/" + subPath;// 目标路径
			String filenameNew = DateUtil.DateConversion14String(new Date())
					+ DateUtil.getDatecurrentTime()
					+ fileFileName.substring(fileFileName.lastIndexOf("."));// 文件名
			
			File savefile = new File(new File(path), filenameNew);
			if (!savefile.getParentFile().exists()){
                savefile.getParentFile().mkdirs();
			}
			FileUtils.copyFile(file, savefile);
			String size = convertFileSize(FileUtils.sizeOf(file));

			// 保存上传信息
			AdenexaModel adenexaModel = new AdenexaModel();
			adenexaModel.setArea(request.getParameter("ID"));
			adenexaModel.setPrjID(request.getParameter("prjID"));
			adenexaModel.setMenuId(request.getParameter("menuId"));
			adenexaModel.setParentDocId(request.getParameter("parentDocId"));
			adenexaModel.setCatalogId(request.getParameter("catalogId"));
			adenexaModel.setFilename(fileFileName);
			adenexaModel.setFilenameNew(filenameNew);
			adenexaModel.setFilepath(subPath+"/"+filenameNew);
			adenexaModel.setFilesize(size);
			
			String userId=request.getParameter("userIds");
			user=(User)commonObjService.getEntityByID("User", userId);
			adenexaModel.setCreateDeptId(user.getUserDeptId());
			//获取默认查看角色
			List<Map<String, String>> roleList=roleService.findRoleListPage(null, "1", "");
			String defaultRoleId="";
			if(roleList!=null && roleList.size()>0){
				for(Map m:roleList){
					if(!defaultRoleId.equals("")){
						defaultRoleId+=",";
					}
					defaultRoleId+=m.get("uuid").toString();
				}
			}
			
			//获取默认关联部门角色
			roleList=roleService.findRoleListPage(null, "1", user.getUserDeptId());
			if(roleList!=null && roleList.size()>0){
				for(Map m:roleList){
					if(!defaultRoleId.equals("")){
						defaultRoleId+=",";
					}
					defaultRoleId+=m.get("uuid").toString();
				}
			}
			adenexaModel.setDefaultRoleId(defaultRoleId);
			
			if(request.getParameter("pid")!=null){
				adenexaModel.setPid(request.getParameter("pid"));
			}
			if(user!=null){
				adenexaModel.setCreateBy(user.getUserName());
			}else{
				adenexaModel.setCreateBy(request.getParameter("userName"));
			}
			adenexaModel.setCreateDate(new Date());
			adenexaModel.setOrgId(request.getParameter("userIds"));//可查看人员id
			adenexaModel.setLookPerson(request.getParameter("userName"));//可查看人员
			adenexaModel.setCurDocId(request.getParameter("curDocId"));
			//根据菜单menuId查看文件是否为流程是流程则初始记录为0，流程审核通过则记录为2，非流程则记录为2
			Menu menu = menuService.findMenuById(request.getParameter("menuId"));
			if("1".equals(menu.getMenuIsHasWF())){//判断是否流程
				adenexaModel.setWfStatus("1");//暂存
			}else{
				adenexaModel.setWfStatus("2");//通过
			}
			//查询按项目模板来分配文档目录
			if("1".equals(menu.getMenuIsLook())){//1是，判断是否关联文档目录，如果关联则查询文档目录，如果文档目录值存在则不去查询文档目录
				//分项目可能存在多个项目对应不同的目录
				String catalogId=adenexaModel.getCatalogId();//已经存在文件目录手动归档
				if(StringUtil.isEmpty(catalogId) || "null".equals(catalogId) || "undefined".equals(catalogId)){
					catalogId = getCatalogId(request, menu, catalogId);//获取文件目录id(自动归档)
				}
				adenexaModel.setCatalogId(catalogId);
			}
			adenexaService.saveAdenexUploadFile(adenexaModel);
			SwfToolsUtil.convert2SWF(path+"/"+filenameNew);
			log.info("=============上传adenexUploadFile成功=============");
		} catch (Exception e) {
			log.error("=============上传adenexUploadFile失败=============",e);
			e.printStackTrace();
		}
		//return "success";
	}
	
	private String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
 
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

	
	//文件目录号处理(自动加载文件目录)
	private String getCatalogId(HttpServletRequest request,Menu menu,String catalogId) throws IntrospectionException{
		if(!StringUtil.isEmpty(request.getParameter("prjID"))){
			String[] prjID  = request.getParameter("prjID").split(",");
			for (String str : prjID) {
				List<FileDirectorynewModel> list = fileDirectoryService.findFileDirectoryMenu(request.getParameter("userIds"), str, menu.getUuid());
				for (FileDirectorynewModel fileDirectorynewModel : list) {
					catalogId+=","+fileDirectorynewModel.getUuid();
				}
			}
		}else{
			//从模板中加载自己能看到的菜单文件目录放入新的对应项目的目录中,若没有则创建一个新的文件目录归档,项目立项中处理
			if(ClassModelUtil.STATUS_PROAMANAGEHEADMODEL.equals(menu.getEntityClsName())){//先按用户查询，如果用户没查到则按角色查询，角色没有则新建
				String orgIds = request.getParameter("orgIds");
				List<FileDirectory> list = fileDirectoryService.fileDirectoryList(orgIds);
				String catalogIdTmp="";
				for (FileDirectory fileDirectory : list) {
					//读取目录数据保存在新建按项目分配的表中
					FileDirectorynewModel newFile = new FileDirectorynewModel();
					CopyUtil.copyToObj(newFile, fileDirectory);
					newFile.setUserId(request.getParameter("userIds"));
					newFile.setUuid(null);
					newFile.setCurDocId(request.getParameter("curDocId"));
					Serializable uuid =commonServicenewFile.save(newFile);
					if(fileDirectory.getRelationMenuID().contains(menu.getUuid())){//更新子目录中关联的菜单
						catalogIdTmp=catalogIdTmp+","+uuid;
					}
				}
				//如果没查询到这新建一条记录
				if(StringUtil.isEmpty(catalogIdTmp)){
					String catalogNo =fileDirectoryService.findFileDirectoryNo();
					String uid =fileDirectoryService.findFileDirectoryUID();
					FileDirectorynewModel newFile = new FileDirectorynewModel();
					newFile.setCatalogName("临时文件目录");
					newFile.setCatalogNo(catalogNo);
					newFile.setCreateBy(adenexaModel.getCreateBy());
					newFile.setCreateDate(new Date());
					newFile.setCurDocId(request.getParameter("curDocId"));
					newFile.setUserId(request.getParameter("userIds"));
					newFile.setUID(uid);
					newFile.setParentTaskUID("-1");
					Serializable uuid =commonServicenewFile.save(newFile);
					catalogId=catalogId+","+uuid;
				}else{
					catalogId=catalogId+","+catalogIdTmp;
				}
			}
		}
		
		return catalogId;
	}
	
	
	
	// 删除附件(先删文件再删数据)
	public String adenexDeleteFile() {
		Map<String, Object> map = new HashMap<String, Object>(2);
		String uuid = ServletActionContext.getRequest().getParameter("uuid");
		String[] ids=uuid.split("\\^");
		for(String s:ids){
			adenexaModel = commonFacadeService.getEntityByID("AdenexaModel", s);
			FileCommonUtil.getDelete(FILE_PATH+"/"+adenexaModel.getFilepath());
			commonFacadeService.deleteId("AdenexaModel", "uuid", s);
		}
		
		map.put("success", true);
		map.put("data", "删除成功!");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
		return null;
	}
	
	public String loadListAdenexaForPage() {
		Map<String, Object> map = new HashMap<String, Object>(2);
		HttpServletRequest request = ServletActionContext.getRequest();
		String model = "AdenexaModel";
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		PageInfo pageInfo = new PageInfo(pageIndex, pageSize, sortField, sortOrder);
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds"); 
		String prjID = request.getParameter("prjID");
		String catalogId = request.getParameter("catalogId");
		String version = request.getParameter("version");
		String wfStatus = request.getParameter("wfStatus");
		String type = request.getParameter("type");
		adenexaModel.setPrjID(prjID);
		adenexaModel.setUuid(catalogId);
		adenexaModel.setVersion(version);
		adenexaModel.setWfStatus(wfStatus);
		adenexaModel.setType(type);
		if(!StringUtils.isEmpty(prjID) || !StringUtils.isEmpty(catalogId)){
			map = adenexaService.loadListForPage(model, pageInfo,orgIds,adenexaModel);
		}
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),map);
		return null;
	}

	//路径获取
	public static final String getSessionattachmenttitle(String sessionName) {
		return bundle.getString(sessionName);
	}
	
	public AdenexaModel getAdenexaModel() {
		return adenexaModel;
	}

	public void setAdenexaModel(AdenexaModel adenexaModel) {
		this.adenexaModel = adenexaModel;
	}

	public AdenexaService getAdenexaService() {
		return adenexaService;
	}

	public void setAdenexaService(AdenexaService adenexaService) {
		this.adenexaService = adenexaService;
	}

	@Override
	public AdenexaModel getModel() {
		if (CommonUtil.isNullOrEmpty(adenexaModel)) {
			adenexaModel = new AdenexaModel();
		}
		return adenexaModel;
	}

	public CommonFacadeService<AdenexaModel> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(
			CommonFacadeService<AdenexaModel> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public void setFileDirectoryService(FileDirectoryService fileDirectoryService) {
		this.fileDirectoryService = fileDirectoryService;
	}
	public MenuService getMenuService() {
		return menuService;
	}
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	public CommonService<FileDirectorynewModel> getCommonServicenewFile() {
		return commonServicenewFile;
	}
	public void setCommonServicenewFile(CommonService<FileDirectorynewModel> commonServicenewFile) {
		this.commonServicenewFile = commonServicenewFile;
	}
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public CommonFacadeService<Object> getCommonObjService() {
		return commonObjService;
	}
	public void setCommonObjService(CommonFacadeService<Object> commonObjService) {
		this.commonObjService = commonObjService;
	}
	
}
