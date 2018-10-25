package htos.business.action.material;

import htos.business.entity.material.Material;
import htos.business.entity.material.MaterialCategory;
import htos.business.service.material.MaterialService;
import htos.common.util.ImportExcelUtil;
import htos.coresys.action.BaseAction;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.util.CommonUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import PluSoft.Utils.JSON;

public class MaterialAction extends BaseAction{
	private Material material;
	private MaterialCategory matcat;
	private MaterialService materialService;
	private CommonFacadeService<MaterialCategory> matCatCommonFacadeService;
	private CommonFacadeService<Material> commonFacadeService;
	private File file;

	public String loadMaterialForPage(){
		String categoryUuid = request.getParameter("categoryUuid");
		String search = request.getParameter("search");
		List searchList = null;
		if (!StringUtils.isEmpty(search)){
			searchList = (List) JSON.Decode(search);
		}
		CommonUtil.toJsonStr(response,materialService.loadMaterialForPage(categoryUuid,searchList, getpageInfo()));
		return null;
	}
	//导入材价明细
	public void ImportExcel(){
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User)request.getSession().getAttribute("user");
		Map<String, String> mapp =new HashMap<String, String>();
		Map<String, Object> map = new ConcurrentHashMap<String, Object>(2);
		List<Map<Integer,String>> list  = ImportExcelUtil.imExcel(file);
		String uuid=null;
		String pid=null;
		System.out.println(list);
		for(int i=0;i<list.size();i++){
			Map<Integer, String> map2 = list.get(i);
			if(map2.get(1).length()>10){
				continue;
			}
			if(map2.get(1).length()==2){
				matcat=new MaterialCategory();
				matcat.setPid("-1");
				matcat.setCategoryCode(map2.get(1));
				matcat.setCategoryName(map2.get(2));
				matcat.setDelFlag("1");
				matcat.setCreateBy(user.getUuid());
		       pid = matCatCommonFacadeService.save(matcat);
		       uuid= pid;
			}
			if(map2.get(1).length()==5){
				matcat=new MaterialCategory();
				matcat.setPid(uuid);
				matcat.setCategoryCode(map2.get(1));
				matcat.setCategoryName(map2.get(2));
				matcat.setDelFlag("1");
			   	matcat.setCreateBy(user.getUuid());
			   	pid = matCatCommonFacadeService.save(matcat);
			}
			if(map2.get(1).length()==8){
				matcat=new MaterialCategory();
				matcat.setPid(pid);
				matcat.setCategoryCode(map2.get(1));
				matcat.setCategoryName(map2.get(2));
				matcat.setDelFlag("1");
				matcat.setCreateBy(user.getUuid());
				matCatCommonFacadeService.save(matcat);
				mapp.put("msg", "数据导入成功");
			}
		 }
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), mapp);
	}

	public String materialOperation(){
		User user = (User) request.getSession().getAttribute("user");
		if (!StringUtils.isEmpty(material.getUuid())) {
			//更新
			Material localMaterial = commonFacadeService.getEntityByID(Material.class.getSimpleName(), material.getUuid());
			localMaterial.setMaterialCode(material.getMaterialCode());
			localMaterial.setMaterialName(material.getMaterialName());
			localMaterial.setSpecModel(material.getSpecModel());
			localMaterial.setBrand(material.getBrand());
			localMaterial.setUnit(material.getUnit());
			localMaterial.setUpdateDate(new Date());
			localMaterial.setUpdateBy(user.getUuid());
			commonFacadeService.update(localMaterial);
		} else {
			//添加
			material.setCreateBy(user.getUuid());
			material.setCreateDate(new Date());
			material.setDelFlag("1");
			String uuid = commonFacadeService.save(material);
			material.setUuid(uuid);
		}
		CommonUtil.toString(ServletActionContext.getResponse(), "<script>parent.goReload();</script>");
		return null;
	}

	public String findMaterialById(){
		String uuid = request.getParameter("uuid");
		material = commonFacadeService.getEntityByID(Material.class.getSimpleName(),uuid);
		return "success";
	}

	public String deleteMaterialByIds(){
		String uuid = request.getParameter("uuid");
		String[] ids = uuid.split("\\^");
		materialService.updateDelFlagByIds(ids);
		return null;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public MaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}

	public CommonFacadeService<Material> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(CommonFacadeService<Material> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	@Override
	public Material getModel() {
		if (material == null) {
			material = new Material();
		}
		return material;
	}
	
	public CommonFacadeService<MaterialCategory> getMatCatCommonFacadeService() {
		return matCatCommonFacadeService;
	}
	public void setMatCatCommonFacadeService(
			CommonFacadeService<MaterialCategory> matCatCommonFacadeService) {
		this.matCatCommonFacadeService = matCatCommonFacadeService;
	}

}
