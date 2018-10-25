package htos.coresys.service.impl;

import htos.business.utils.BusinessConstants;
import htos.common.util.CopyUtil;
import htos.common.util.ExportExcel;
import htos.common.util.StringUtil;
import htos.coresys.entity.Dept;
import htos.coresys.entity.DeptToUser;
import htos.coresys.entity.ExDeptToUserModel;
import htos.coresys.entity.PrtRole;
import htos.coresys.entity.Role;
import htos.coresys.entity.User;
import htos.coresys.entity.UserToRole;
import htos.coresys.service.DeptService;
import htos.coresys.service.DeptToUserService;
import htos.coresys.service.OrgFacadeService;
import htos.coresys.service.PrtRoleService;
import htos.coresys.service.RoleService;
import htos.coresys.service.UserService;
import htos.coresys.service.UserToRoleService;
import htos.coresys.util.CommonUtil;

import java.beans.IntrospectionException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;


/**
 * 组织外观模型服务层
 * 
 * @author 庞成祥
 * 
 */
public class OrgFacadeServiceImpl implements OrgFacadeService {
	private static final Logger log = Logger.getLogger(OrgFacadeServiceImpl.class);
	private DeptService deptService;
	private UserService userService;
	private DeptToUserService deptToUserService;
	private RoleService roleService;
	private UserToRoleService userToRoleService;
	private PrtRoleService prtRoleService;

	// -----------------部门用户关联操作开始-------------------

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public UserToRoleService getUserToRoleService() {
		return userToRoleService;
	}

	public void setUserToRoleService(UserToRoleService userToRoleService) {
		this.userToRoleService = userToRoleService;
	}

	@Override
	public Map<String, Object> login(User user) {
		User u = userService.userLogin(user);
		if (CommonUtil.isNullOrEmpty(u)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", u);
		if(u.getUserType() != null && u.getUserType() == BusinessConstants.USER_TYPE_SUPPLIER) {
			// 如果是是供应商账号，则只有审核通过后才能登录
			if(StringUtils.equals(u.getWfStatus(), BusinessConstants.ACCOUNT_CHECK_WAITING)) {
				// 等待审核
				map.put("status", BusinessConstants.ACCOUNT_CHECK_WAITING);
				return map;
			} else if(StringUtils.equalsIgnoreCase(u.getWfStatus(), BusinessConstants.ACCOUNT_CHECK_FAILED)) {
				// 审核失败
				map.put("status", BusinessConstants.ACCOUNT_CHECK_FAILED);
				return map;
			}
		}
		map.put("status", BusinessConstants.ACCOUNT_CHECK_SUCCESS);
		Set<DeptToUser> s = u.getDeptToUsers();
		StringBuffer sb = new StringBuffer(u.getUuid());
		for (DeptToUser dt : s) {
			sb.append(",");
			sb.append(dt.getDept().getUuid());
		}
		Set<UserToRole> s1 = u.getUserToRoles();
		StringBuffer roleIds = new StringBuffer("---");
		for (UserToRole ut : s1) {
			sb.append(",");
			sb.append(ut.getRole().getUuid());

			roleIds.append(",");
			roleIds.append(ut.getRole().getUuid());
		}
		List<PrtRole> prtList=prtRoleService.findPrtRoles(roleIds.toString());
		if(prtList!=null && prtList.size()>0){
			for (PrtRole pt : prtList) {
				sb.append(",");
				sb.append(pt.getPrtRoleId());
			}
		}
		map.put("orgIds", sb.toString());
		return map;
	}

	/**
	 * 添加员工,总共有如下三步 1、根据jsp传人员工信息及所选部门id，将user保存 2、通过部门id查找出相应的部门，及该部门对应的所有父节点部门
	 * 3、按照List<Dept> - user的关系生成 用户->部门 的多条数据
	 * 
	 * @return
	 */
	@Override
	public void addUser(User user, String sameLevelNodeId) {
		int tempNo = 0;
		if (CommonUtil.isNullOrEmpty(sameLevelNodeId)) {
			Object obj = userService.findMaxUserPosition(user);
			if (null == obj) {
			} else {
				tempNo = Integer.parseInt(obj.toString()) + 1;
			}
		} else {
			User laterUser = userService.findUserById(sameLevelNodeId);
			tempNo = laterUser.getUserPostion();
			userService.updateLaterUserPosition(laterUser);
		}
		user.setUserPostion(tempNo);
		userService.saveUser(user);
		// -----------------------------------------
		String deptId = user.getUserDeptId();
		List<Dept> list = new ArrayList<Dept>();
		if (!CommonUtil.isNullOrEmpty(deptId)) {
			Dept d = deptService.findDeptById(deptId);
			list.add(d);
			while ("-1".equals(d.getDeptPid()) == false) {
				d = deptService.findDeptById(d.getDeptPid());
				list.add(d);
			}
		}
		// ----------------------------------------
		DeptToUser deptToUser = null;
		for (Dept d : list) {
			deptToUser = new DeptToUser();
			deptToUser.setDept(d);
			deptToUser.setUser(user);
			deptToUser.setDeptRole("1");
			deptToUser.setIsParttimeDept("1");
			deptToUser.setCreateBy("admin");
			deptToUser.setCreateDate(new Date());
			deptToUser.setUpdateBy("admin");
			deptToUser.setUpdateDate(new Date());
			deptToUser.setDelFlag("1");
			deptToUserService.saveDeptToUser(deptToUser);
		}
		// -----------向orgMap映射表中添加记录-------
		/*
		 * OrgMap orgMap = new OrgMap(); orgMap.setOrgId(user.getUserId());
		 * //1、用户 2、部门 3、角色 orgMap.setOrgTypt(1); orgMap.setCreateBy("admin");
		 * orgMap.setCreateDate(new Date()); orgMap.setUpdateBy("admin");
		 * orgMap.setUpdateDate(new Date()); orgMap.setDelFlag("1");
		 * orgMapService.addOrgMap(orgMap);
		 */
	}

	// 删除部门级联删除用户
	@Override
	public void deleteDept(String deptId) {
		// 删除部门信息
		deptService.deleteDeptById(deptId);
		// 删除用户信息
		userService.deleteUserByDeptId(deptId);
	}

	// 创建组织机构树
	@Override
	public List<Map<String, String>> findOrgTree() {
		List<User> userList = userService.findAllList();
		Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>();
		Map<String, String> subMap = null;
		for (User user : userList) {
			subMap = new HashMap<String, String>();
			subMap.put("id", user.getUuid());
			subMap.put("text", user.getUserName());
			subMap.put("pid", user.getUserDeptId());
			subMap.put("type", "user");
			if (map.containsKey(user.getUserDeptId())) {
				map.get(user.getUserDeptId()).add(subMap);
			} else {
				List<Map<String, String>> t = new ArrayList<Map<String, String>>();
				t.add(subMap);
				map.put(user.getUserDeptId(), t);
			}
		}

		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		List<Dept> deptList = deptService.findAllDeptList();
		for (Dept d : deptList) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("id", d.getUuid());
			m.put("text", d.getDeptName());
			m.put("pid", d.getDeptPid());
			m.put("type", "dept");
			dataList.add(m);
			if (map.containsKey(d.getUuid())) {
				dataList.addAll(map.get(d.getUuid()));
			}
		}
		return dataList;
	}

	// -----------------部门用户关联操作结束-------------------

	// -----------------角色用户关联操作开始-------------------

	@SuppressWarnings("null")
	public void saveUserToRole(Role role, String userList) {
		roleService.saveRole(role);
		if (!(userList == null && userList.equals(""))) {
			String[] id = userList.split(",");
			for (int i = 0; i < id.length; i++) {
				User user = new User();
				user.setUuid(id[i]);
				UserToRole userToRole = new UserToRole();
				userToRole.setUser(user);
				userToRole.setRole(role);
				userToRole.setRoleType("1");
				userToRole.setCreateBy("admin");
				userToRole.setCreateDate(new Date());
				userToRole.setUpdateBy("admin");
				userToRole.setUpdateDate(new Date());
				userToRole.setDelFlag("1");
				userToRoleService.saveUserToRole(userToRole);
			}
		}
	}
	
	//根据角色查询用户
	public List<Map<String, String>> findUserIdByRole(Role role) {
		UserToRole userToRole = new UserToRole();
		Map<String, String> map = null;
		userToRole.setRole(role);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<UserToRole> listUser = userToRoleService.findUserIdByRole(userToRole);
		for (UserToRole user : listUser) {
			map = new HashMap<String, String>();
			map.put("id", user.getUser().getUuid());
			map.put("text", user.getUser().getUserName());
			list.add(map);
		}
		return list;
	}

	// 更新角色信息
	public void updateUserToRole(Role role2, UserToRole userToRole, String addUserList,String userPerEname) throws IntrospectionException {
		Role role =roleService.get(Role.class, role2.getUuid());
		CopyUtil.copyToObj(role, role2);
		role.setUpdateBy(userPerEname);
		role.setUpdateDate(new Date());
		role.setRoleDepts(role2.getRoleDepts());
		role.setRoleMenus(role2.getRoleMenus());
		role.setOldRoleDepts(role2.getOldRoleDepts());
		role.setOldRoleMenus(role2.getOldRoleMenus());
		role.setRoleName(role2.getRoleName());
		role.setRoleDescribe(role2.getRoleDescribe());
		roleService.updateRole(role,userPerEname);
		List<UserToRole> listUser = userToRoleService.findUserIdByRole(userToRole);
		for (UserToRole userRole : listUser) {
			userToRoleService.deleteUserToRole(userRole);
		}
		String[] ids = addUserList.split(",");
		for (int i = 0; i < ids.length; i++) {
			User user = new User();
			user.setUuid(ids[i]);
			UserToRole newUserToRole = new UserToRole();
			newUserToRole.setUser(user);
			newUserToRole.setRole(role);
			newUserToRole.setRoleType("1");
			newUserToRole.setCreateBy("admin2");
			newUserToRole.setCreateDate(new Date());
			newUserToRole.setUpdateBy("admin2");
			newUserToRole.setUpdateDate(new Date());
			newUserToRole.setDelFlag("1");
			userToRoleService.saveUserToRole(newUserToRole);
		}
	}
	
	//查询所有角色
	@Override
	public List<Map<String, String>> findAllRoleUserJson() {
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<Role> listRole = roleService.findAllList();
		for (Role r : listRole) {
			map = new HashMap<String, String>();
			map.put("roleId", r.getUuid());
			map.put("roleName", r.getRoleName());
			map.put("type", "role");
			//map.put("data", findUserIdByRole(r).toString());
			list.add(map);
		}
		return list;
	}
	
	// -----------------角色用户关联操作结束-------------------

	public DeptToUserService getDeptToUserService() {
		return deptToUserService;
	}

	public void setDeptToUserService(DeptToUserService deptToUserService) {
		this.deptToUserService = deptToUserService;
	}

	public DeptService getDeptService() {
		return deptService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	
	public PrtRoleService getPrtRoleService() {
		return prtRoleService;
	}

	public void setPrtRoleService(PrtRoleService prtRoleService) {
		this.prtRoleService = prtRoleService;
	}

	@Override
	public Map<String, Object> saveImportOrgUser(List<String> list,String userPerEname) {
		boolean flag = true;
		Map<String, Object> map = new ConcurrentHashMap<String, Object>(2);
		Map<Integer, User> maps = new ConcurrentHashMap<Integer, User>(2);
		int n=1;
		if(list.size()>1){
			for (int i = 1; i < list.size(); i++) {
				//先校验数据的准确性后再保存数据（只要有一项数据不完整则不允许上传
				String str = list.get(i);
				String[] array = str.split(",");
				if(array.length<6){
					n=1+i;
					flag = false;
					break;
				}/*else if(StringUtil.isEmpty(array[0])){
					n=i;
					flag = false;
					break;
				}else if(StringUtil.isEmpty(array[1])){
					flag = false;
					break;
				}else if(StringUtil.isEmpty(array[2])){
					flag = false;
					break;
				}*/else{
					User user = new User();
					user.setUserPerEname(array[0]);
					user.setUserName(array[1]);
					user.setCreateBy(userPerEname);
					user.setUserMail(array[3]);
					user.setUserSex(array[4]);
					user.setUserPassword(array[5]);
					user.setCreateDate(new Date());
					user.setUserPostion(0);
					user.setRemark("数据导入");
					//校验部门存不存在
					Dept dept = new Dept();
					dept.setDeptFullName(array[2]);
					dept.setDeptName(array[2].split("/")[0]);
					List<Dept> lists = deptService.findDept(dept);
					if(lists.size()!=0){
						Dept dept2=lists.get(0);
						//保存数据
						user.setUserDeptId(dept2.getUuid());
						maps.put(i, user);
					}else{
						flag = false;
						break;
					}
				}
			}
		}else{
			flag=false;
		}
		if(flag){
			for (Map.Entry<Integer, User> entry:maps.entrySet()) {
				userService.saveUser(entry.getValue());
				Dept dept = deptService.findDeptById(entry.getValue().getUserDeptId());
				saveDeptToUser(entry.getValue(), dept);
			}
			map.put("success", true);
			map.put("msg", "组织人员数据导入成功！");
		}else{
			map.put("success", false);
			map.put("msg", "数据第"+n+"行校验导入失败，数据不允许为空！");
		}
		return map;
	}
	
	//递归新增deptToUser
	@Override
	public void saveDeptToUser(User user,Dept dept){
		//先保存导入的节点,如果有上级节点则递归保存上级节点
		saveDeptUser(user, dept);
		Dept dept2=deptService.findDeptById(dept.getDeptPid());
		if(dept2!=null){
			saveDeptToUser(user, dept2);
		}
	}
	private void saveDeptUser(User user,Dept dept){
		DeptToUser deptToUser = new DeptToUser();
		deptToUser.setCreateBy(user.getCreateBy());
		deptToUser.setCreateDate(user.getCreateDate());
		deptToUser.setUpdateBy("admin");
		deptToUser.setUpdateDate(new Date());
		deptToUser.setDelFlag("1");
		deptToUser.setRemark(user.getRemark());
		deptToUser.setUser(user);
		deptToUser.setDeptRole("1");
		deptToUser.setIsParttimeDept("1");
		deptToUser.setUpdateBy("admin");
		deptToUser.setUpdateDate(new Date());
		deptToUser.setDept(dept);
		deptToUserService.saveDeptToUser(deptToUser);
	}
	
	@Override
	public void exportOrgUser(HttpServletResponse response) {
		log.info("==================exportOrgUser=====导出开始================");
		List<ExDeptToUserModel> gse = new ArrayList<ExDeptToUserModel>();
		List<User> list = userService.findAllList();
		try {
			for (User user : list) {
				ExDeptToUserModel exModel = new ExDeptToUserModel();
				CopyUtil.copyToObj(exModel, user);
				if(!StringUtil.isEmpty(user.getUserDeptId())){
					Dept dept = deptService.findDeptById(user.getUserDeptId());
					if(dept!=null){
						exModel.setDeptFullName(dept.getDeptFullName());
					}
				}
				gse.add(exModel);
			}
			 response.setContentType("octets/stream");
	    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddH24mmss");
	    	 String datestr = sdf.format(new Date());
	    	 String filename ="DeptUserAll"+datestr;
	         response.addHeader("Content-Disposition", "attachment;filename="+filename+".xls"); 
	         ExportExcel<ExDeptToUserModel> ex = new ExportExcel<ExDeptToUserModel>();
	         String[] headers = {"中文名称","英文名称", "部门", "性别","电子邮件"};
			OutputStream out = response.getOutputStream();
			ex.exportExcel("人员信息", gse, headers, out);
	        out.close();
	        log.info("==================exportOrgUser=====导出结束================");
		} catch (Exception e) {
			 log.error("==================exportOrgUser=====导出异常===============",e);
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String, String>> findUserIdByDept(Dept dept) {
		DeptToUser deptToUser = new DeptToUser();
		Map<String, String> map = null;
		deptToUser.setDept(dept);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<DeptToUser> listUser = deptToUserService.findUserIdByDept(deptToUser);
		for (DeptToUser user : listUser) {
			map = new HashMap<String, String>();
			map.put("id", user.getUser().getUuid());
			map.put("text", user.getUser().getUserName());
			list.add(map);
		}
		return list;
	}
	@Override
	public List<Map<String, String>> findOrgTreeWithoutUser() {
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		List<Dept> deptList = deptService.findAllDeptList();
		for (Dept d : deptList) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("id", d.getUuid());
			m.put("text", d.getDeptName());
			m.put("pid", d.getDeptPid());
			m.put("type", "dept");
			dataList.add(m);
		}
		JSON.toJSONString(dataList);
		return dataList;
	}
	@Override
	public List<Map<String, String>> findUserRoleJson(User user) {
		UserToRole userToRole = new UserToRole();
		Map<String, String> map = null;
		userToRole.setUser(user);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<UserToRole> listUser = userToRoleService.findUserRole(userToRole);
		for (UserToRole role : listUser) {
			map = new HashMap<String, String>();
			map.put("id", role.getRole().getUuid());
			map.put("text", role.getRole().getRoleName());
			list.add(map);
		}
		return list;
	}
}
