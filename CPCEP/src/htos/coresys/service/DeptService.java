package htos.coresys.service;

import htos.coresys.entity.Dept;

import java.beans.IntrospectionException;
import java.util.List;
import java.util.Map;

public interface DeptService {

	// 添加部门
	public void addDept(Dept dept, String sameLevelNodeId);

	// 通过部门id获取部门
	public Dept findDeptById(String deptId);

	// 获取全部的部门的list集合
	public List<Dept> findAllDeptList();

	/**
	 * 参数：dept.deptPid
	 * 用途：获取deptPid=dept.deptPid部门下子部门或同级部门集合
	 */
	public List<Dept> findChildDeptList(Dept dept);

	// 获取全部的部门json
	public List<Map<String, String>> findAllDeptsJson();

	// 获取某个部门下的所有子部门list集合
	public List<Map<String, String>> findChildDeptsJson(Dept dept);

	// 根据指定的位置，更新部门
	public void updateDept(Dept dept, String sameLevelNodeId,String userPerEname) throws IntrospectionException;

	// 删除部门
	public void deleteDept(Dept dept);

	// 根据部门id删除部门
	public void deleteDeptById(String deptId);

	/**
	 * 通过部门ID找到其所有的父节点
	 * 
	 * @param deptId：部门唯一标识
	 * 
	 * @return 所有父部门
	 * @exception
	 * 
	 * @author bitwise
	 * @Time 2016-06-21
	 */
	public List<Dept> getAncestors(String deptId);

	// 条件查询
	List<Dept> findDept(Dept dept);

	//递归查询此部门下所有子部门，包含当前部门
	List<Dept> findRecurChildDeptList(Dept dept);
	//获取全部二级部门
	public List<Map<String, String>> findTwoDeptsJson();

	/**
	 * @param deptId
	 * @return
	 */
	List<String> getDeptIdsByPid(String deptId);
}