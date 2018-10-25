package htos.business.action.material;

import PluSoft.Utils.JSON;
import htos.business.service.material.MaterialCategoryParamService;
import htos.coresys.action.BaseAction;
import htos.coresys.util.CommonUtil;

import java.util.List;

public class MaterialCategoryParamAction extends BaseAction {
	private MaterialCategoryParamService materialCategoryParamService;

	public String loadCategoryParamForPage() {
		String categoryId = request.getParameter("categoryId");
		CommonUtil.toJsonStr(response, materialCategoryParamService.loadCategoryParamForPage(categoryId, getpageInfo()));
		return null;
	}

	public void loadCategoryParam(){
		String categoryId = request.getParameter("categoryId");
		CommonUtil.toJsonStr(response, materialCategoryParamService.loadCategoryParam(categoryId));
	}

	public String categoryParamOperation(){
		String categoryId = request.getParameter("categoryId");
		String data = request.getParameter("data");
		List rows = (List) JSON.Decode(data);
		materialCategoryParamService.addOrModified(rows,categoryId);
		return null;
	}

	public String deleteCategoryParamByIds(){
		String uuid = request.getParameter("uuid");
		String[] ids = uuid.split("\\^");
		materialCategoryParamService.updateDelFlagByIds(ids);
		return null;
	}

	public MaterialCategoryParamService getMaterialCategoryParamService() {
		return materialCategoryParamService;
	}

	public void setMaterialCategoryParamService(MaterialCategoryParamService materialCategoryParamService) {
		this.materialCategoryParamService = materialCategoryParamService;
	}
}
