package htos.coresys.service.impl;

import htos.common.entity.PageInfo;
import htos.coresys.dao.RoleDao;
import htos.coresys.entity.Menu;
import htos.coresys.entity.Right;
import htos.coresys.entity.Role;
import htos.coresys.entity.UserToRole;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.CommonService;
import htos.coresys.service.RoleService;
import htos.sysfmt.file.entity.AdenexaModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import PluSoft.Utils.JSON;


public class RoleServiceImpl implements RoleService {
	private RoleDao roleDao;
	private CommonService<AdenexaModel> commonService;
	private CommonService<Object> objService;
	private CommonFacadeService<Right> comRightService;

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public CommonService<AdenexaModel> getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService<AdenexaModel> commonService) {
		this.commonService = commonService;
	}

	public CommonService<Object> getObjService() {
		return objService;
	}

	public void setObjService(CommonService<Object> objService) {
		this.objService = objService;
	}
	
	public CommonFacadeService<Right> getComRightService() {
		return comRightService;
	}

	public void setComRightService(CommonFacadeService<Right> comRightService) {
		this.comRightService = comRightService;
	}

	@Override
	public void saveRole(Role role) {
		String uuid=(String) roleDao.save(role);
		role.setUuid(uuid);
		RoleToPrower(role,1);
	}

	@Override
	public void updateRole(Role role,String userPerEname) {
		role.setUpdateBy(userPerEname);
		role.setUpdateDate(new Date());
		
		RoleToPrower(role,2);
		roleDao.update(role);
	}

	@Override
	public Role findRoleById(Role role) {
		return roleDao.findRoleById(role);
	}

	@Override
	public void deleteRole(Role role) {
		roleDao.delete(role);
	}

	@Override
	public void deleteRoles(String roles) {
		String[] ids=roles.split(",");
		for(String id:ids){
			List<AdenexaModel> adenexList=commonService.getHqlList("from AdenexaModel where defaultRoleId!=null and defaultRoleId!='' and defaultRoleId like '%"+id+"%'");
			if(adenexList!=null && adenexList.size()>0){
				for(AdenexaModel adenex:adenexList){
					String defaultRoleId=adenex.getDefaultRoleId();
					if(defaultRoleId!=null && !defaultRoleId.equals("")){
						adenex.setDefaultRoleId(rtuDefaultRoleId(defaultRoleId,id));
						commonService.update(adenex);
					}
				}
			}
			
			List<Object> result=objService.getHqlList("from Right where orgId='"+id+"'");
			for(Object o:result){
				if(o!=null){
					objService.delete(o);
				}
			}
		}
		roleDao.deleteRoles(roles);
	}

	@Override
	public List<Map<String, String>> findAllRoleJson() {
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<Role> listRole = roleDao.findAllList();
		for (Role r : listRole) {
			map = new HashMap<String, String>();
			map.put("id", r.getUuid());
			map.put("text", r.getRoleName());
			map.put("type", "role");
			list.add(map);
		}
		return list;
	}

	@Override
	public Role get(Class<Role> class1, String uuid) {
		return roleDao.get(class1, uuid);
	}

	@Override
	public List<Role> findAllList() {
		return roleDao.findAllList();
	}

	/* (non-Javadoc)
	 * @see htos.coresys.service.RoleService#findRoleListPage(htos.common.entity.PageInfo, java.lang.String)
	 */
	@Override
	public List<Map<String, String>> findRoleListPage(PageInfo pageInfo, String cat,String deptIds) {
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<Role> listRole = roleDao.findRoleListPage(pageInfo, cat,deptIds);
		for (Role r : listRole) {
			map = new HashMap<String, String>();
			map.put("uuid", r.getUuid());
			map.put("roleName", r.getRoleName());
			map.put("type", "role");
			map.put("depts", r.getRoleDepts());
			map.put("menus", r.getRoleMenus());
			list.add(map);
		}
		return list;
	}
	
	
	@Override
	public List<Map<String, String>> findRoleListJsonPage(PageInfo pageInfo, String cat,String deptIds) {
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<Role> listRole = roleDao.findRoleListPage(pageInfo, cat,deptIds);
		for (Role r : listRole) {
			map = new HashMap<String, String>();
			map.put("id", r.getUuid());
			map.put("text", r.getRoleName());
			map.put("type", "role");
			list.add(map);
		}
		return list;
	}
	
	//--------------------------------
	/*
	 * type:1新  2旧
	 */
	public void RoleToPrower(Role role,int type){
		//是否文件权限
		boolean isFile=false;
		//旧的是否文件权限
		boolean isOldFile=false;
		//是否数据权限
		boolean isData=false;
		//旧的是否数据权限
		boolean isOldData=false;
		List<Object> result=new ArrayList<Object>();
		
		//判断当前角色是否包含数据权限及文件权限
		if(type==1 && role.getListCategory()!=null){
			if(role.getListCategory().indexOf("1")>-1){
				isFile=true;
			}
			if(role.getListCategory().indexOf("4")>-1){
				isData=true;
			}
		}else if(type==2 && role.getListCategory()!=null){
			if(role.getListCategory().indexOf("1")>-1){
				isFile=true;
			}
			if(role.getOldListCategory().indexOf("1")>-1){
				isOldFile=true;
			}
			
			if(role.getListCategory().indexOf("4")>-1){
				isData=true;
			}
			if(role.getOldListCategory().indexOf("4")>-1){
				isOldData=true;
			}
		}

		if(type==1){
			//文件权限
			if(isFile){
				addFilePrower(role);
			}
			//数据权限
			if(isData){
				addDataPrower(role);
			}
		}else if(type==2){
			boolean isMenuChange=false;
			boolean isDeptChange=false;
			String[] oldDeptArr=null;
			String oldMenuIds="";
			if(isOldFile){
				String hqlS="";
				String hqlE="";
				
				if(isFile && !rtuStr(role.getRoleDepts()).equals(rtuStr(role.getOldRoleDepts()))){
					isDeptChange=true;
				}
				if(isFile && !rtuStr(role.getRoleMenus()).equals(rtuStr(role.getOldRoleMenus()))){
					isMenuChange=true;
				}
				
				//如果当前角色不包含文件权限--清除旧权限
				if(!isFile || isDeptChange || isMenuChange){
					if(rtuBool(role.getOldRoleDepts()) && !rtuBool(role.getOldRoleMenus())){
						oldDeptArr=role.getOldRoleDepts().split(",");
						hqlS="from AdenexaModel where createDeptId!='' and createDeptId!=null and createDeptId like '%";
						hqlE="%'";
					}
					
					if(!rtuBool(role.getOldRoleDepts()) && rtuBool(role.getOldRoleMenus())){
						oldMenuIds=role.getOldRoleMenus().replace(",", "','");
						hqlS="from AdenexaModel where menuId in ('"+oldMenuIds+"')";
					}
					
					if(rtuBool(role.getOldRoleDepts()) && rtuBool(role.getOldRoleMenus())){
						oldDeptArr=role.getOldRoleDepts().split(",");
						oldMenuIds=role.getOldRoleMenus().replace(",", "','");
						hqlS="from AdenexaModel where createDeptId!='' and createDeptId!=null and createDeptId like '%";
						hqlE="%' and menuId in ('"+oldMenuIds+"')";
					}
					
					clearFilePrower(hqlS,hqlE,oldDeptArr,role);
				}
				
				if(isFile && (isDeptChange || isMenuChange)){
					addFilePrower(role);
				}
			}else if(isFile){
				addFilePrower(role);
			}
			
			//更新数据权限处理
			if(isOldData){
				isMenuChange=false;
				isDeptChange=false;
				oldDeptArr=null;
				String[] menuIds=null;
				String enCls="";

				if(isData && !rtuStr(role.getRoleDepts()).equals(rtuStr(role.getOldRoleDepts()))){
					isDeptChange=true;
				}
				if(isData && !rtuStr(role.getRoleMenus()).equals(rtuStr(role.getOldRoleMenus()))){
					isMenuChange=true;
				}
				
				if(!isData || isDeptChange || isMenuChange){
					if(rtuBool(role.getOldRoleDepts()) && !rtuBool(role.getOldRoleMenus())){
						oldDeptArr=role.getRoleDepts().split(",");
					}
					
					if(!rtuBool(role.getOldRoleDepts()) && rtuBool(role.getOldRoleMenus())){
						menuIds=role.getOldRoleMenus().split(",");
					}
					
					if(rtuBool(role.getOldRoleDepts()) && rtuBool(role.getOldRoleMenus())){
						oldDeptArr=role.getOldRoleDepts().split(",");
						menuIds=role.getOldRoleMenus().split(",");
					}
					clearDataPrower(oldDeptArr,menuIds,role);
				}
				if(isData && (isDeptChange || isMenuChange)){
					addDataPrower(role);
				}
			}else if(isData){
				addDataPrower(role);
			}
		}
	}
	
	// 清除数据权限
	private void clearDataPrower(String[] oldDeptArr,String[] menuIds,Role role) {
		String enCls="";
		if(oldDeptArr==null && menuIds==null){
			List<Object> linArr=objService.getHqlList("from Right where orgId='"+role.getUuid()+"' and modoName!='Menu' and modoName!='MenuToBtn'");
			if(linArr!=null && linArr.size()>0){
				for(Object o:linArr){
					if(o!=null){
						objService.delete(o);
					}
				}
			}
			return;
		}
		
		if(oldDeptArr!=null && menuIds!=null){
			for(String id:menuIds){
				Menu menu=(Menu)objService.getEntityByID("Menu", id);
				if(menu!=null && menu.getEntityClsName()!=null && !menu.getEntityClsName().equals("")){
					if(!enCls.equals("")){ enCls+=","; }
					enCls+=menu.getEntityClsName();
				}
			}
			enCls=enCls.replace(",", "','");
			for(String b:oldDeptArr){
				List<Object> linArr=objService.getHqlList("from Right where orgId='"+role.getUuid()+"' and createDeptId!='' and createDeptId!=null and createDeptId like '%"+b+"%' and modoName!='Menu' and modoName!='MenuToBtn' and modoName in ('"+enCls+"')");
				if(linArr!=null && linArr.size()>0){
					for(Object o:linArr){
						if(o!=null){
							objService.delete(o);
						}
					}
				}
			}
			
			return;
		}
		if(oldDeptArr!=null){
			for(String b:oldDeptArr){
				List<Object> linArr=objService.getHqlList("from Right where orgId='"+role.getUuid()+"' and createDeptId!='' and createDeptId!=null and createDeptId like '%"+b+"%' and modoName!='Menu' and modoName!='MenuToBtn'");
				if(linArr!=null && linArr.size()>0){
					for(Object o:linArr){
						if(o!=null){
							objService.delete(o);
						}
					}
				}
			}
			return;
		}
		
		
		if(menuIds!=null){
			for(String id:menuIds){
				Menu menu=(Menu)objService.getEntityByID("Menu", id);
				if(menu.getEntityClsName()!=null && !menu.getEntityClsName().equals("")){
					if(!enCls.equals("")){ enCls+=","; }
					enCls+=menu.getEntityClsName();
				}
			}
			enCls=enCls.replace(",", "','");
			List<Object> linArr=objService.getHqlList("from Right where orgId='"+role.getUuid()+"' and modoName!='Menu' and modoName!='MenuToBtn' and modoName in ('"+enCls+"')");
			if(linArr!=null && linArr.size()>0){
				for(Object o:linArr){
					if(o!=null){
						objService.delete(o);
					}
				}
			}
		}
	}
	
	//清除文件权限
	private void clearFilePrower(String hqlS,String hqlE,String[] oldDeptArr,Role role){
		List<AdenexaModel> adenexList=new ArrayList<AdenexaModel>();
		if(!hqlS.equals("")){
			if(oldDeptArr!=null){
				for(String od:oldDeptArr){
					if(role.getRoleDepts().indexOf(od)==-1){
						adenexList=commonService.getHqlList(hqlS+od+hqlE);
						for(AdenexaModel adenex:adenexList){
							String defaultRoleId=adenex.getDefaultRoleId();
							if(defaultRoleId!=null && !defaultRoleId.equals("")){
								adenex.setDefaultRoleId(rtuDefaultRoleId(defaultRoleId,role.getUuid()));
								commonService.update(adenex);
							}
						}
					}
				}
			}else{
				adenexList=commonService.getHqlList(hqlS+hqlE);
				for(AdenexaModel adenex:adenexList){
					String defaultRoleId=adenex.getDefaultRoleId();
					if(defaultRoleId!=null && !defaultRoleId.equals("")){
						adenex.setDefaultRoleId(rtuDefaultRoleId(defaultRoleId,role.getUuid()));
						commonService.update(adenex);
					}
				}
			}
		}else{
			adenexList=commonService.getHqlList("from AdenexaModel");
			for(AdenexaModel adenex:adenexList){
				String defaultRoleId=adenex.getDefaultRoleId();
				if(defaultRoleId!=null && !defaultRoleId.equals("")){
					adenex.setDefaultRoleId(rtuDefaultRoleId(defaultRoleId,role.getUuid()));
					commonService.update(adenex);
				}
			}
		}
		adenexList=null;
	}
	
	//添加文件权限
	private void addFilePrower(Role role){
		List<AdenexaModel> adenexList=new ArrayList<AdenexaModel>();
		if(role.getRoleDepts()!=null && !role.getRoleDepts().equals("") && (role.getRoleMenus()==null || role.getRoleMenus().equals(""))){
			String[] deptArr=role.getRoleDepts().split(",");
			for(String b:deptArr){
				List<AdenexaModel> linArr=commonService.getHqlList("from AdenexaModel where createDeptId!='' and createDeptId!=null and createDeptId like '%"+b+"%'");
				if(linArr!=null && linArr.size()>0){
					adenexList.addAll(linArr);
				}
			}
		}
		
		if(role.getRoleMenus()!=null && !role.getRoleMenus().equals("") && (role.getRoleDepts()==null || role.getRoleDepts().equals(""))){
			String menuIds=role.getRoleMenus().replace(",", "','");
			List<AdenexaModel> linArr=commonService.getHqlList("from AdenexaModel where menuId in ('"+menuIds+"')");
			if(linArr!=null && linArr.size()>0){
				adenexList.addAll(linArr);
			}
		}
		
		if(role.getRoleMenus()!=null && !role.getRoleMenus().equals("") && role.getRoleDepts()!=null && !role.getRoleDepts().equals("")){
			String menuIds=role.getRoleMenus().replace(",", "','");
			String[] deptArr=role.getRoleDepts().split(",");
			for(String b:deptArr){
				List<AdenexaModel> linArr=commonService.getHqlList("from AdenexaModel where createDeptId!='' and createDeptId!=null and createDeptId like '%"+b+"%' and menuId in ('"+menuIds+"')");
				if(linArr!=null && linArr.size()>0){
					adenexList.addAll(linArr);
				}
			}
		}
		
		if((role.getRoleMenus()==null || role.getRoleMenus().equals("")) && (role.getRoleDepts()==null || role.getRoleDepts().equals(""))){
			List<AdenexaModel> linArr=commonService.getHqlList("from AdenexaModel");
			if(linArr!=null && linArr.size()>0){
				adenexList.addAll(linArr);
			}
		}
		
		if(adenexList.size()>0){
			for(AdenexaModel adenex:adenexList){
				String defaultRoleId=adenex.getDefaultRoleId();
				if(defaultRoleId!=null && !defaultRoleId.equals("")){
					if(defaultRoleId.indexOf(role.getUuid())==-1){
						adenex.setDefaultRoleId(defaultRoleId+","+role.getUuid());
						commonService.update(adenex);
					}
				}else{
					adenex.setDefaultRoleId(role.getUuid());
					commonService.update(adenex);
				}
			}
		}
		
		adenexList.clear();
		adenexList=null;
	}
	
	private void addDataPrower(Role role){
		Set<String> allId=new HashSet<String>();
		//关联部门数据权限
		if(role.getRoleDepts()!=null && !role.getRoleDepts().equals("") && (role.getRoleMenus()==null || role.getRoleMenus().equals(""))){
			String[] deptArr=role.getRoleDepts().split(",");
			for(String b:deptArr){
				List<Object> linArr=objService.getHqlList("select docId,modoName from Right where createDeptId!='' and createDeptId!=null and createDeptId like '%"+b+"%' and modoName!='Menu' and modoName!='MenuToBtn'");
				if(linArr!=null && linArr.size()>0){
					for(Object o:linArr){
						if(o!=null){
							Object[] prt=(Object[])o;
							if(!checkObject(prt[0]).equals("")){
								allId.add(checkObject(prt[0])+","+checkObject(prt[1])+","+b);
							}
						}
					}
				}
			}
		}
		//关联菜单数据
		if(role.getRoleMenus()!=null && !role.getRoleMenus().equals("") && (role.getRoleDepts()==null || role.getRoleDepts().equals(""))){
			String[] menuIds=role.getRoleMenus().split(",");
			String enCls="";
			for(String id:menuIds){
				Menu menu=(Menu)objService.getEntityByID("Menu", id);
				if(menu.getEntityClsName()!=null && !menu.getEntityClsName().equals("")){
					if(!enCls.equals("")){ enCls+=","; }
					enCls+=menu.getEntityClsName();
				}
			}
			enCls=enCls.replace(",", "','");
			List<Object> linArr=objService.getHqlList("select docId,modoName from Right where modoName!='Menu' and modoName!='MenuToBtn' and modoName in ('"+enCls+"')");
			if(linArr!=null && linArr.size()>0){
				for(Object o:linArr){
					if(o!=null){
						Object[] prt=(Object[])o;
						if(!checkObject(prt[0]).equals("")){
							allId.add(checkObject(prt[0])+","+checkObject(prt[1])+",-");
						}
					}
				}
			}
		}
		
		//关联部门在菜单下的数据
		if(role.getRoleMenus()!=null && !role.getRoleMenus().equals("") && role.getRoleDepts()!=null && !role.getRoleDepts().equals("")){
			String[] deptArr=role.getRoleDepts().split(",");
			String[] menuIds=role.getRoleMenus().split(",");
			String enCls="";
			for(String id:menuIds){
				Menu menu=(Menu)objService.getEntityByID("Menu", id);
				if(menu.getEntityClsName()!=null && !menu.getEntityClsName().equals("")){
					if(!enCls.equals("")){ enCls+=","; }
					enCls+=menu.getEntityClsName();
				}
			}
			enCls=enCls.replace(",", "','");
			
			for(String b:deptArr){
				List<Object> linArr=objService.getHqlList("select docId,modoName from Right where createDeptId!='' and createDeptId!=null and createDeptId like '%"+b+"%' and modoName!='Menu' and modoName!='MenuToBtn' and modoName in ('"+enCls+"')");
				if(linArr!=null && linArr.size()>0){
					for(Object o:linArr){
						if(o!=null){
							Object[] prt=(Object[])o;
							if(!checkObject(prt[0]).equals("")){
								allId.add(checkObject(prt[0])+","+checkObject(prt[1])+","+b);
							}
						}
					}
				}
			}
		}
		
		//全局菜单下的数据
		if((role.getRoleMenus()==null || role.getRoleMenus().equals("")) && (role.getRoleDepts()==null || role.getRoleDepts().equals(""))){
			List<Object> linArr=objService.getHqlList("select docId,modoName from Right where modoName!='Menu' and modoName!='MenuToBtn'");
			if(linArr!=null && linArr.size()>0){
				for(Object o:linArr){
					if(o!=null){
						Object[] prt=(Object[])o;
						if(!checkObject(prt[0]).equals("")){
							allId.add(checkObject(prt[0])+","+checkObject(prt[1])+",-");
						}
					}
				}
			}
		}
		
		//为所有合格文档添加权限
		for(String cid:allId){
			String[] prt=cid.split(",");
			comRightService.addDocRight(prt[1], prt[0], role.getUuid(), 3, prt[2]);
		}
		allId.clear();
		allId=null;
	}
	
	private String rtuDefaultRoleId(String allId,String roleId){
		String[] rids=allId.split(",");
		String newId="";
		for(int a=0;a<rids.length;a++){
			if(!rids[a].equals(roleId)){
				if(newId.equals("")){ newId+=","; }
				newId+=rids[a];
			}
		}
		return newId;
	}
	
	private String checkObject(Object o){
		if(o==null){
			return "-";
		}
		return o.toString();
	}

	private String rtuStr(Object o){
		if(o==null){
			return "";
		}
		return o.toString();
	}
	
	
	private boolean rtuBool(Object o){
		if(o==null){
			return false;
		}
		if(o.toString().equals("")){
			return false;
		}
		return true;
	}
}
