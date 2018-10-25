package htos.coresys.dao.impl;

import htos.coresys.dao.FieldFormDao;
import htos.coresys.entity.FieldForm;

public class FieldFormDaoImpl extends BaseDaoImpl<FieldForm>  implements FieldFormDao {

	@Override
	public FieldForm findFieldFormByMenuId(String menuId) {
		String hql=" from FieldForm fm where fm.menu.uuid='"+menuId+"'";
		return super.get(hql, new String[]{});
	}
}
