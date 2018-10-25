package htos.coresys.dao;

import htos.coresys.entity.Dept;

import java.util.List;


/** 
 *  
 *  
 */

public interface DeptDao extends BaseDao<Dept> {
	//获取所有部门list
	public List<Dept> findAllDeptList();
	//根据某个部门获取该部门下的所有的子部门
	public List<Dept> findChildDeptList(Dept dept);
	//使某个同层级部门下的所有部门编号+1（新建部门，选择指定的位置的时候使用）
	public int updateLaterDeptNo(Dept laterDept);
	//获取同层级最大的编号
	public Object findMaxDeptNo(Dept dept);
	//根据指定的dept，更新当前dept的位置
	public int updatePosition(Dept dept, Dept laterDept);
	//根据条件查询
	List<Dept> findDept(Dept dept);
	//递归查询此部门下所有子部门，包含当前部门
	List<Dept> findRecurChildDeptList(Dept dept);
	public List<Dept> findTwoDeptList();
}