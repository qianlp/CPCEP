package htos.coresys.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import htos.coresys.entity.FieldForm;
import htos.coresys.service.FieldFormService;
import htos.coresys.util.CommonUtil;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class FieldFormAction extends ActionSupport  implements ModelDriven<FieldForm>{

	private static final long serialVersionUID = 1L;
	private FieldFormService fieldFormService;
	private FieldForm fieldForm;
	/**
	 * @return the fieldFormService
	 */
	public FieldFormService getFieldFormService() {
		return fieldFormService;
	}

	/**
	 * @param fieldFormService the fieldFormService to set
	 */
	public void setFieldFormService(FieldFormService fieldFormService) {
		this.fieldFormService = fieldFormService;
	}

	public FieldForm getFieldForm() {
		return fieldForm;
	}

	public void setFieldForm(FieldForm fieldForm) {
		this.fieldForm = fieldForm;
	}
	
	public String fieldFormOperate(){
		if(!StringUtils.isEmpty(fieldForm.getUuid())){
			return updateFieldForm();
		}else{
			return addFieldForm();
		}
	}

	public String addFieldForm(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuId = request.getParameter("formId");
		if(CommonUtil.isNullOrEmpty(menuId)){
			menuId = request.getParameter("menuId");
		}
		fieldFormService.addFieldForm(fieldForm,menuId);
		//return "success";
		return "success";
	}
	
	public String updateFieldForm(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuId = request.getParameter("formId");
		if(CommonUtil.isNullOrEmpty(menuId)){
			menuId = request.getParameter("menuId");
		}
		fieldFormService.updateFieldForm(fieldForm,menuId);
		//return "success";
		return "success";
	}
	public String getFieldList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuId = request.getParameter("menuId");
		if(menuId!=""){
			fieldForm = fieldFormService.getFieldFormByMenuId(menuId);
			CommonUtil.toString(ServletActionContext.getResponse(), fieldForm.getFieldList());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	@Override
	public FieldForm getModel() {
		if(CommonUtil.isNullOrEmpty(fieldForm)){
			fieldForm = new FieldForm();
		}
		return fieldForm;
	}
}
