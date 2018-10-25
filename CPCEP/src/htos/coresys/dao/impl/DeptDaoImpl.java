package htos.coresys.dao.impl;

import htos.common.util.StringUtil;
import htos.coresys.dao.DeptDao;
import htos.coresys.entity.Dept;
import htos.coresys.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;


public class DeptDaoImpl extends BaseDaoImpl<Dept> implements DeptDao {
	
	@Override
	public List<Dept> findAllDeptList(){
		String hql="from Dept d order by d.deptNo asc";
		return super.find(hql);
	}
	@Override
	public List<Dept> findChildDeptList(Dept dept){
		String hql = "from Dept d where d.deptPid='"+dept.getDeptPid()+"' order by d.deptNo asc";
		return super.find(hql);
	}

	@Override
	public int updateLaterDeptNo(Dept dept) {
		String hql = "update from Dept d set d.deptNo=(d.deptNo+1) where d.deptPid='"+dept.getDeptPid()+"' and d.deptNo>="+dept.getDeptNo();
		return super.executeHql(hql);
	}

	@Override
	public Object findMaxDeptNo(Dept dept) {
		String hql = "select max(d.deptNo) from Dept d where d.deptPid='"+dept.getDeptPid()+"' ";
		return super.findUnique(hql);
	}
	
	@Override
	public int updatePosition(Dept curDept,Dept laterDept) {
		String hql="";
		if(curDept.getDeptNo()>laterDept.getDeptNo()){
			hql = "update from Dept d set d.deptNo=(d.deptNo+1) where d.deptPid='"+curDept.getDeptPid()+"' and d.deptNo>="+laterDept.getDeptNo()+"and d.deptNo<"+curDept.getDeptNo();
		}else if(curDept.getDeptNo()<laterDept.getDeptNo()){
			hql = "update from Dept d set d.deptNo=(d.deptNo-1) where d.deptPid='"+curDept.getDeptPid()+"' and d.deptNo>"+curDept.getDeptNo()+"and d.deptNo<"+laterDept.getDeptNo();
			
		}
		return super.executeHql(hql);
	}
	@Override
	public List<Dept> findDept(Dept dept){
		String hql = "from Dept d where 1=1 "+getContent(dept);
		return super.find(hql);
	}
	private String getContent(Dept dept) {
		String content="";
		if(CommonUtil.isNullOrEmpty(dept)) return content;
		if(!StringUtil.isEmpty(dept.getDeptFullName())){
			content+="and deptFullName='"+dept.getDeptFullName()+"'";
		}
		if(!StringUtil.isEmpty(dept.getDeptName())){
			content+="and deptName='"+dept.getDeptName()+"'";
		}

		return content;
	}
	@Override
	public List<Dept> findRecurChildDeptList(Dept dept){
		List<Dept> list = new ArrayList<Dept>();
		return recurDeptList(dept,list);
	}
	
	private List<Dept> recurDeptList(Dept dept,List<Dept> list){
		List<Dept> newlist = new ArrayList<Dept>();
		newlist.add(dept);
		String chql = "from Dept d where d.deptPid='"+dept.getUuid()+"' order by d.deptNo asc";
		List<Dept> clist = super.find(chql);
		if(clist.size()>0){
			for(Dept deptment:clist){
				list.addAll(recurDeptList(deptment,newlist));
			}
		}else{
			return newlist;
		}
		return list;
	}
	@Override
	public List<Dept> findTwoDeptList() {
		String hql="from Dept d where d.nodeLevel='2' order by d.deptNo asc";
		return super.find(hql);
	}
}