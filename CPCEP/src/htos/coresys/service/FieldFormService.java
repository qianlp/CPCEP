package htos.coresys.service;

import htos.coresys.entity.FieldForm;


public interface FieldFormService {

	public void addFieldForm(FieldForm fieldForm, String menuId);

	public FieldForm getFieldFormById(String id);
	
	public FieldForm getFieldFormByMenuId(String menuId);

	public void updateFieldForm(FieldForm fieldForm, String menuId);

}
