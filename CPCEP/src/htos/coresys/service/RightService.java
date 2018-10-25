package htos.coresys.service;

import htos.coresys.dao.RightDao;
import htos.coresys.entity.Right;

import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
 * @author Administrator
 *
 */
public interface RightService {

	public void addRight(String docId, String modeName, String strJson);
	
	public void saveRight(Right right);

	public Set<String> getDocIds(String orgIds, String modoName);

	public Set<String> getOrgIdsByDocId(Right right);
	
	public List<Right> getRightsByProprty(String modoName,String orgIds,String docId);
	
	public List<Right> getRightsList(Right right);
	
	public void updateRight(String docId, String modeName, String strJson);
	
	public void delete(Right right);
	/**
	 * @ 将集合转换为字符串，主要用于在hql查询的in条件中使用。例如：
	 * Set<String> s = new HashSet<String>(); s.add(a);s.add(b); 则转换后返回   'a','b' 
	 * @param c ：string类型的集合
	 * @return 返回字符串
	 * @version V1.0
	 * @author pangchengxiang
	 */
	public String convertToStr(Collection<String> c);
	/**
	 * @ 将字符串转换为字符串，主要用于在hql查询的in条件中使用。例如：
	 * String[] s = {"a","b"};  则转换后返回   'a','b' 
	 * @param arr ：string类型的数组
	 * @return 返回字符串
	 * @version V1.0
	 * @author pangchengxiang
	 */
	public String convertToStr(Object[] arr);
	/**
	 * @ 获取权限DAO对象
	 * 获取菜单权限处使用
	 * @version V1.0
	 * @author bitwise
	 */	
	public RightDao getRightDao();
	
	void saveRight(String docId, String modeName, String userId, String type);
}