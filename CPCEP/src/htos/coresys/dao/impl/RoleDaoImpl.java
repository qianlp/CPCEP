package htos.coresys.dao.impl;

import htos.common.entity.PageInfo;
import htos.coresys.dao.RoleDao;
import htos.coresys.entity.Role;
import htos.coresys.util.CommonUtil;

import java.util.Date;
import java.util.List;


public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

	@Override
	public void saveRole(Role role) {
		// TODO Auto-generated method stub
		role.setUpdateBy("admin");
		role.setUpdateDate(new Date());
		role.setCreateBy("admin");
		role.setCreateDate(new Date());
		role.setDelFlag("1");
		super.save(role);
	}

	@Override
	public void updateRole(Role role) {
		// TODO Auto-generated method stub
		super.update(role);
	}

	@Override
	public Role findRoleById(Role role) {
		// TODO Auto-generated method stub
		return super.get(Role.class, role.getUuid());
	}

	@Override
	public void deleteRole(Role role) {
		// TODO Auto-generated method stub
		super.delete(role);
	}

	@Override
	public void deleteRoles(String roles) {
		// TODO Auto-generated method stub
		roles = roles.replaceAll(",", "','");
		String hql = "from Role r where r.uuid in ('"+ roles +"')";
		List<Role> list = super.find(hql);
		for (int i = 0; i < list.size(); i++) {
			super.delete(list.get(i));
		}
	}

	@Override
	public List<Role> findAllList() {
		String hql = "from Role where isHide='' or isHide is null ";
		return super.find(hql);
	}

	@Override
	public List<Role> findRoleListPage(PageInfo pageInfo, String cat,String deptIds) {
		StringBuffer hql = new StringBuffer("from Role");
		hql.append("  where (isHide='' or isHide is null)");
		if(cat!=null && !cat.equals("")){
			hql.append("  and listCategory like '%"+cat+"%'");
		}
		
		if(deptIds!=null && !deptIds.equals("") && !deptIds.equals("1")){
			String[] strArr=deptIds.split(",");
			hql.append("  and (");
			for(int b=0;b<strArr.length;b++){
				if(b>0){
					hql.append("  or");
				}
				hql.append("  roleDepts like '%"+strArr[b]+"%'");
			}
			hql.append("  )");
		}else{
			if(deptIds!=null && !deptIds.equals("1")){
				hql.append("  and roleDepts='' ");
			}
		}
		if(CommonUtil.isNullOrEmpty(pageInfo)){
			return super.find(hql.toString());
		}else{
			if (CommonUtil.isNullOrEmpty(pageInfo.getSortField())) {
			} else {
				hql.append(" order by ");
				hql.append(pageInfo.getSortField());
			}
			if (CommonUtil.isNullOrEmpty(pageInfo.getSortOrder())) {
			} else {
				hql.append(" ");
				hql.append(pageInfo.getSortOrder());
			}
		}
		return super.find(hql.toString(), new String[] {}, pageInfo.getpageIndex(), pageInfo.getPageSize());
		
	}

}
