package htos.sysfmt.file.service.impl;

import htos.common.util.CopyUtil;
import htos.common.util.JsonUtil;
import htos.common.util.StringUtil;
import htos.coresys.entity.User;
import htos.coresys.service.CommonService;
import htos.sysfmt.file.dao.FileDirectoryDao;
import htos.sysfmt.file.entity.AdenexaModel;
import htos.sysfmt.file.entity.FileDirectory;
import htos.sysfmt.file.entity.FileDirectorynewModel;
import htos.sysfmt.file.entity.PrjFileDirectoryModel;
import htos.sysfmt.file.service.FileDirectoryService;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;


public class FileDirectoryServiceImpl implements FileDirectoryService {
	
	private FileDirectoryDao fileDirectoryDao;
	private CommonService<FileDirectorynewModel> commonService;
	private CommonService<AdenexaModel> commonServiceAden;
	
	public FileDirectoryDao getFileDirectoryDao() {
		return fileDirectoryDao;
	}

	public void setFileDirectoryDao(FileDirectoryDao fileDirectoryDao) {
		this.fileDirectoryDao = fileDirectoryDao;
	}

	@Override
	public FileDirectory fileDirectory(String fileDirectoryCode) {
		return fileDirectoryDao.get(FileDirectory.class, fileDirectoryCode);
	}

	@Override
	public List<FileDirectory> fileDirectoryList() {
		return fileDirectoryDao.fileDirectoryList();
	}


	@Override
	public void updateFileDirectory(FileDirectory fileDirectory) {
		fileDirectoryDao.updateFileDirectory(fileDirectory);
	}

	@Override
	public void deleteFileDirectory(String attr,String value) {
		fileDirectoryDao.deleteFileDirectory(attr,value);
	}

	@Override
	public void addFileDirectory(FileDirectory fileDirectory) {
		fileDirectoryDao.addFileDirectory(fileDirectory);
	}

	@Override
	public List<Map<String, String>> findFileDirectoryTree() {
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		List<FileDirectory> dirList = fileDirectoryDao.fileDirectoryList();
		for (FileDirectory d : dirList) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("id", d.getUID());
			m.put("text", d.getCatalogName());
			m.put("catalogNo", d.getUuid());
			m.put("pid", d.getParentTaskUID());
			dataList.add(m);
		}
		return dataList;
	}

	@Override
	public List<FileDirectory> fileDirectoryList(FileDirectory fileDirectory) {
		return fileDirectoryDao.fileDirectoryList(fileDirectory);
	}

	/* (non-Javadoc)
	 * @see htos.sysfmt.file.service.FileDirectoryService#fileDirectoryList(java.lang.String)
	 */
	@Override
	public List<FileDirectory> fileDirectoryList(String orgIds) {
		
		return fileDirectoryDao.fileDirectoryList(orgIds);
	}

	@Override
	public List<Map<String, String>> findFileDirectoryTree(String orgIds,String prjID) {
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		String hql="from FileDirectorynewModel where userId in ('"+orgIds.replace(",", "','")+"')  and prjID='"+prjID+"' ";//获取创建人自己的数据
			List<FileDirectorynewModel> dirList = commonService.getHqlList(hql);
			//if(dirList.size()>0){
				for (FileDirectorynewModel d : dirList) {
					Map<String, String> m = new HashMap<String, String>();
					m.put("id", d.getUID());
					m.put("text", d.getCatalogName());
					m.put("catalogNo", d.getUuid());
					m.put("pid", d.getParentTaskUID());
					dataList.add(m);
				}
//			}else{
//				List<FileDirectory> dList = fileDirectoryDao.fileDirectoryList(orgIds);
//				for (FileDirectory d : dList) {
//					Map<String, String> m = new HashMap<String, String>();
//					m.put("id", d.getUID());
//					m.put("text", d.getCatalogName());
//					m.put("catalogNo", d.getUuid());
//					m.put("pid", d.getParentTaskUID());
//					dataList.add(m);
//				}
//			}
		return dataList;
	}

	public CommonService<FileDirectorynewModel> getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService<FileDirectorynewModel> commonService) {
		this.commonService = commonService;
	}

	@Override
	public List<FileDirectorynewModel> fileDirectoryProList(String prjID) throws IntrospectionException {
		String hql="from FileDirectorynewModel where  prjID='"+prjID+"' ";//获取项目的数据
		List<FileDirectorynewModel> list= commonService.getHqlList(hql);
		return list;
	}
	
	//查询新建文件目录中是否包含关联的菜单
	@Override
	public List<FileDirectorynewModel> findFileDirectoryMenu(String orgIds,String prjID,String menuId) {
		List<FileDirectorynewModel> dataList = new ArrayList<FileDirectorynewModel>();
		String hql="from FileDirectorynewModel where userId in ('"+orgIds.replace(",", "','")+"')  and prjID='"+prjID+"' and relationMenuID like '%"+menuId+"%' ";//获取创建人自己的数据
		dataList = commonService.getHqlList(hql);
		return dataList;
	}
	
	//通过脚本查询文件目录，并返回对象
	@Override
	public String findFileDirectoryNo() {
		Integer catalogNo=111111111;
		String hql="from FileDirectorynewModel order by  catalogNo desc";
		FileDirectorynewModel fileDirectorynewModel = commonService.getHqlObject(hql);
		if(fileDirectorynewModel!=null && !StringUtil.isEmpty(fileDirectorynewModel.getCatalogNo())){
			catalogNo =Integer.parseInt(fileDirectorynewModel.getCatalogNo())+1;
		}
		return catalogNo.toString();
	}
	//通过脚本查询文件目录，并返回对象
	@Override
	public String findFileDirectoryUID() {
		Integer uid=111111111;
		String hql="from FileDirectorynewModel order by  UID desc";
		FileDirectorynewModel fileDirectorynewModel = commonService.getHqlObject(hql);
		if(fileDirectorynewModel!=null && !StringUtil.isEmpty(fileDirectorynewModel.getUID())){
			uid =Integer.parseInt(fileDirectorynewModel.getUID())+1;
		}
		return uid.toString();
	}
	
	//按项目查找项目目录树文件
	@Override
	public List<PrjFileDirectoryModel> prjFileDirectoryProList(String prjID) throws IntrospectionException {
		String hql="from FileDirectorynewModel where  prjID='"+prjID+"' ";//获取项目的数据
		List<PrjFileDirectoryModel> lists=new ArrayList<PrjFileDirectoryModel>();
		List<FileDirectorynewModel> list= commonService.getHqlList(hql);
		for (FileDirectorynewModel fileDirectorynewModel : list) {
			PrjFileDirectoryModel modeldir = new PrjFileDirectoryModel();
			//查找目录树
			CopyUtil.copyToObj(modeldir, fileDirectorynewModel);
			modeldir.setType("1");
			lists.add(modeldir);
			//查找目录文件
			String hqlfile="from AdenexaModel where  prjID like '%"+prjID+"%' and catalogId like '%"+fileDirectorynewModel.getUuid()+"%' and wfStatus='2' ";
			List<AdenexaModel> listfile= commonServiceAden.getHqlList(hqlfile);
			for (AdenexaModel adenexaModel : listfile) {
				PrjFileDirectoryModel modelfile = new PrjFileDirectoryModel();
				modelfile.setUuid(adenexaModel.getUuid());
				modelfile.setUID(adenexaModel.getUuid());
				modelfile.setCatalogName(adenexaModel.getFilename());
				modelfile.setLookNamesId(adenexaModel.getOrgId());
				modelfile.setLookPerson(adenexaModel.getLookPerson());
				modelfile.setLookRole(adenexaModel.getLookRole());
				modelfile.setLookRolesId(adenexaModel.getLookRolesId());
				modelfile.setParentTaskUID(modeldir.getUID());
				modelfile.setSize(adenexaModel.getFilesize());
				modelfile.setType("2");
				lists.add(modelfile);
			}
		}
		return lists;
	}

	public CommonService<AdenexaModel> getCommonServiceAden() {
		return commonServiceAden;
	}

	public void setCommonServiceAden(CommonService<AdenexaModel> commonServiceAden) {
		this.commonServiceAden = commonServiceAden;
	}

	//项目文件分配权限批量更新新目录和文件权限
	@Override
	public void updatePrjDirFileUpdate(String data) throws IntrospectionException {
		HttpServletRequest request = ServletActionContext.getRequest();
		User user=(User)request.getSession().getAttribute("user");
		List<PrjFileDirectoryModel> lists = JsonUtil.getJsonList(data,new PrjFileDirectoryModel());
		for (PrjFileDirectoryModel prjfileDir : lists) {
			if(!StringUtils.isEmpty(prjfileDir.getUuid())){//更新目录和文件权限
				if("1".equals(prjfileDir.getType())){//更新目录
					FileDirectorynewModel fileDirectorynewModel =commonService.get(FileDirectorynewModel.class, prjfileDir.getUuid());
					if(fileDirectorynewModel!=null){
						fileDirectorynewModel.setLookRole(prjfileDir.getLookRole());
						fileDirectorynewModel.setLookRolesId(prjfileDir.getLookRolesId());
						fileDirectorynewModel.setLookPerson(prjfileDir.getLookPerson());
						fileDirectorynewModel.setLookNamesId(prjfileDir.getLookNamesId());
						fileDirectorynewModel.setUpdateBy(user.getUserName());
						fileDirectorynewModel.setUpdateDate(new Date());
						commonService.update(fileDirectorynewModel);
					}
				}else{//更新文件
					AdenexaModel adenexaModel =	commonServiceAden.get(AdenexaModel.class, prjfileDir.getUuid());
					if(adenexaModel!=null){
						adenexaModel.setLookRole(prjfileDir.getLookRole());
						adenexaModel.setLookRolesId(prjfileDir.getLookRolesId());
						adenexaModel.setLookPerson(prjfileDir.getLookPerson());
						adenexaModel.setOrgId(prjfileDir.getLookNamesId());
						commonServiceAden.update(adenexaModel);
					}
				}
			}
		}
	}

	@Override
	public List<FileDirectorynewModel> fileNewDirOrgListByPower() {
		List<FileDirectorynewModel> list = new ArrayList<FileDirectorynewModel>();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");
		String hql="from FileDirectorynewModel where 1=1 ";//获取创建人自己的数据
		String[] ids=orgIds.split(",");
		for(int k=0;k<ids.length;k++){
			if(k==0){
				hql+=" and (lookNamesId like '%"+ids[k]+"%' or userId ='"+ids[k]+"' ";
			}else{
				hql+=" or lookRolesId like '%"+ids[k]+"%' ";
			}
			if((k+1)==ids.length){
				hql+=")";
			}
		}
		String prjID = request.getParameter("prjID");
		if(!StringUtil.isEmpty(prjID)){
			hql+=" and prjID='"+prjID+"' ";
		}
		hql+="  order by uuid asc ";
		list = commonService.getHqlList(hql);
		return list;
	}
	
}
