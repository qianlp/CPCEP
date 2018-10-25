package htos.business.action.material;

import PluSoft.Utils.JSON;
import htos.business.entity.material.MaterialCategory;
import htos.business.service.material.MaterialCategoryService;
import htos.coresys.action.BaseAction;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import java.util.Date;
import java.util.List;

public class MaterialCategoryAction extends BaseAction {
	private MaterialCategory materialCategory;
	private MaterialCategoryService materialCategoryService;
	private CommonFacadeService<MaterialCategory> commonFacadeService;

	/**
	 * 更新或者添加
	 *
	 * @return
	 */
	public String categoryOperate() {
		User user = (User) request.getSession().getAttribute("user");
		if (!StringUtils.isEmpty(materialCategory.getUuid())) {
			//更新
			MaterialCategory localMaterialCategory = commonFacadeService.getEntityByID(MaterialCategory.class.getSimpleName(), materialCategory.getUuid());
			localMaterialCategory.setCategoryCode(materialCategory.getCategoryCode());
			localMaterialCategory.setCategoryName(materialCategory.getCategoryName());
			localMaterialCategory.setUpdateDate(new Date());
			localMaterialCategory.setUpdateBy(user.getUuid());
			commonFacadeService.update(localMaterialCategory);
		} else {
			//添加
			materialCategory.setCreateBy(user.getUuid());
			materialCategory.setCreateDate(new Date());
			materialCategory.setDelFlag("1");
			String uuid = commonFacadeService.save(materialCategory);
			materialCategory.setUuid(uuid);
		}
		CommonUtil.toString(ServletActionContext.getResponse(), "<script>parent.goReload();</script>");
		return null;
	}

	/**
	 * 材料分类树
	 *
	 * @return
	 */
	public String findCategoryTree() {
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),
				materialCategoryService.findCategoryTreeJson());
		return null;
	}
	
	/**
	 * 材料分类树(未登录可读取)
	 *
	 * @return
	 */
	public String findCategoryTreeOpen() {
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),
				materialCategoryService.findCategoryTreeJson());
		return null;
	}
	

	/**
	 * 分页加载子分类列表
	 * @return
	 */
	public String loadCategoriesForPage() {
		String pid = request.getParameter("pid");
		String search = request.getParameter("search");
		List searchList=null;
		if (!StringUtils.isEmpty(search)){
			searchList = (List) JSON.Decode(search);
		}
		CommonUtil.toJsonStr(response,materialCategoryService.loadCategoriesForPage(pid,searchList, getpageInfo()));
		return null;
	}

	public String findCategoryById(){
		String uuid = request.getParameter("uuid");
		materialCategory = commonFacadeService.getEntityByID(MaterialCategory.class.getSimpleName(),uuid);
		return "success";
	}

	public String deleteCategoryByIds(){
		String uuid = request.getParameter("uuid");
		String[] ids = uuid.split("\\^");
		materialCategoryService.updateDelFlagByIds(ids);
		return null;
	}
	public MaterialCategory getMaterialCategory() {
		return materialCategory;
	}

	public void setMaterialCategory(MaterialCategory materialCategory) {
		this.materialCategory = materialCategory;
	}

	public MaterialCategoryService getMaterialCategoryService() {
		return materialCategoryService;
	}

	public void setMaterialCategoryService(MaterialCategoryService materialCategoryService) {
		this.materialCategoryService = materialCategoryService;
	}

	public CommonFacadeService<MaterialCategory> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(CommonFacadeService<MaterialCategory> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	@Override
	public MaterialCategory getModel() {
		if (materialCategory == null) {
			materialCategory = new MaterialCategory();
		}
		return materialCategory;
	}
}
