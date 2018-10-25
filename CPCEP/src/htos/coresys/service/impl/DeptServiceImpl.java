package htos.coresys.service.impl;

import htos.common.util.CopyUtil;
import htos.coresys.dao.DeptDao;
import htos.coresys.entity.Dept;
import htos.coresys.service.DeptService;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeptServiceImpl implements DeptService {
	private DeptDao deptDao;
	public DeptDao getDeptDao() {
		return deptDao;
	}
	public void setDeptDao(DeptDao deptDao) {
		this.deptDao = deptDao;
	}
	
	@Override
	public Dept findDeptById(String deptId){
		return deptDao.get(Dept.class, deptId);
	}
	@Override
	public List<Dept> findAllDeptList(){
		return deptDao.findAllDeptList();
	}
	@Override
	public List<Dept> findChildDeptList(Dept dept){
		return deptDao.findChildDeptList(dept);
		
	}
	@Override
	public void updateDept(Dept dept2,String sameLevelNodeId,String userPerEname) throws IntrospectionException{
		Dept dept =deptDao.get(Dept.class, dept2.getUuid());
		String deptFullName = dept.getDeptName();
		CopyUtil.copyToObj(dept, dept2);
		if(!deptFullName.equals(dept2.getDeptName())){
			String[] arr=dept.getDeptFullName().split("/");
			String dName="";
			for(String s:arr){
				if(s.equals(deptFullName))s=dept.getDeptName();
				if(!dName.equals(""))dName+="/";
				dName+=s;
			}
			dept.setDeptFullName(dName);
		}
		
		if(sameLevelNodeId!=null && sameLevelNodeId.length()>0){
			Dept laterDept = this.findDeptById(sameLevelNodeId);
			int deptNo=0;
			if(dept.getDeptNo()>laterDept.getDeptNo()){
				deptNo=laterDept.getDeptNo();
				deptDao.updatePosition(dept,laterDept);
			}else if(dept.getDeptNo()<laterDept.getDeptNo()){
				deptNo=laterDept.getDeptNo()-1;
				deptDao.updatePosition(dept,laterDept);
			}else{
				deptNo = dept.getDeptNo();
			}
			dept.setDeptNo(deptNo);
		}
		dept.setUpdateBy(userPerEname);
		dept.setUpdateDate(new Date());
		deptDao.update(dept);
		//更新子部门的数据
		if(!deptFullName.equals(dept2.getDeptName())){
			Dept det = new Dept();
			det.setDeptPid(dept.getUuid());
			List<Dept> list = deptDao.findChildDeptList(det);
			for (Dept dept3 : list) {
				String[] arr=dept3.getDeptFullName().split("/");
				String dName="";
				for(String s:arr){
					if(s.equals(deptFullName))s=dept.getDeptName();
					if(!dName.equals(""))dName+="/";
					dName+=s;
				}
				dept3.setDeptFullName(dName);
				deptDao.update(dept3);
			}
		}
	}
	
	@Override
	public List<Map<String,String>> findAllDeptsJson(){
		return getList(deptDao.findAllDeptList());
	}
	@Override
	public List<Map<String, String>> findTwoDeptsJson() {
		// TODO Auto-generated method stub
		return getList(deptDao.findTwoDeptList());
	}
	@Override
	public List<Map<String,String>> findChildDeptsJson(Dept dept) {
		return getList(deptDao.findChildDeptList(dept));
	}
	
	//根据部门ID获取所有子部门ID
	@Override
	public List<String> getDeptIdsByPid(String deptId){
		List<String> ids=new ArrayList();
		Dept dept=new Dept();
		dept.setDeptPid(deptId);
		List<Dept> list=deptDao.findChildDeptList(dept);
		if(list!=null && list.size()>0){
			for(Dept d:list){
				ids.add(d.getUuid());
				List<String> subList=getDeptIdsByPid(d.getUuid());
				if(subList.size()>0){
					ids.addAll(subList);
				}
			}
		}
		return ids;
	}

	@Override
	public void addDept(Dept dept,String sameLevelNodeId) {
		// TODO Auto-generated method stub
		int tempNo=0;		//部门编号（系统自己维护，用于部门排序）
		int nodeLevel=0;	//部门层级
		String deptFullName="";
		if(sameLevelNodeId!=null && sameLevelNodeId.length()>0){
			Dept laterDept = this.findDeptById(sameLevelNodeId);
			tempNo=laterDept.getDeptNo();
			nodeLevel=laterDept.getNodeLevel();
			deptFullName = laterDept.getDeptFullName().replace(laterDept.getDeptName(), dept.getDeptName());
			deptDao.updateLaterDeptNo(laterDept);
			
		}else{
			Object obj = deptDao.findMaxDeptNo(dept);
			if(null == obj){
				tempNo=0;
			}else{
				tempNo = Integer.parseInt(obj.toString());
			}
			tempNo=tempNo+1;
			
			if("-1".equals(dept.getDeptPid())){
				nodeLevel = 1;
				deptFullName = dept.getDeptName();
			}else{
				Dept pDept = this.findDeptById(dept.getDeptPid());
				if(pDept!=null){
					nodeLevel=pDept.getNodeLevel()+1;
					deptFullName = dept.getDeptName()+"/"+pDept.getDeptFullName();
				}
			}
			
		}
		
		dept.setDeptNo(tempNo);
		dept.setNodeLevel(nodeLevel);
		dept.setDeptFullName(deptFullName);
		deptDao.save(dept);
	} 
	
	private List<Map<String,String>> getList(List<Dept> deptList){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(Dept dept : deptList){
			Map<String,String> map = new HashMap<String,String>();
			map.put("id", dept.getUuid());
			map.put("text", dept.getDeptName());
			map.put("pid", dept.getDeptPid());
			map.put("type", "dept");
			list.add(map);
		}
		return list;
	}
	
	@Override
	public void deleteDept(Dept dept) {
		// TODO Auto-generated method stub
		deptDao.delete(dept);
	}
	
	@Override
	public void deleteDeptById(String deptId) {
		// TODO Auto-generated method stub
		deptDao.delete(deptDao.get(Dept.class, deptId));
	}
	/**
	* 保存菜单权限
	* @param 
	* 	deptId：部门唯一标识

	* @return
	* 	所有父部门
	* @exception
	* 	
	* @author bitwise
	* @Time 2016-06-22
	*/
	@Override
	public List<Dept> getAncestors(String deptId){
		List<Dept> list = new ArrayList<Dept>();
		Dept d = this.findDeptById(deptId);
		while ("-1".equals(d.getDeptPid()) == false) {
			d = this.findDeptById(d.getDeptPid());
			list.add(d);
		}
		return list;
	}
	@Override
	public List<Dept> findDept(Dept dept) {
		return deptDao.findDept(dept);
	}
	@Override
	public List<Dept> findRecurChildDeptList(Dept dept) {
		return deptDao.findRecurChildDeptList(dept);
	}
	
}
