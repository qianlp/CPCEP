package htos.common.util;

import java.util.List;

import com.alibaba.fastjson.JSON;

import htos.common.entity.BaseGrid;
import htos.coresys.dao.CommonDao;

public  class  BaseGridUtil {

	/**
	 * flag:是否做为新数据进行保存
	* @param @param listStr
	* @param @param pid
	* @param @param dao
	* @param @param c
	* @param @param flag
	* @return void
	* @throws 
	* @author willkiy
	* @date 2016年11月15日 下午3:16:35
	 */
	public static <T extends BaseGrid>  List<T>  saveEntityGrid(String listStr,String pid, CommonDao<T> dao,Class<T> c,boolean flag){
		List<T> list = null;
		if(null!=listStr && !"".equalsIgnoreCase(listStr)){
			list = (List<T>) JSON.parseArray(listStr,c);
			if(null!=list && !list.isEmpty()){
				for(int i=0;i<list.size();i++){
					T t = (T) list.get(i);
					t.setPid(pid);
					if(flag){
						dao.save(t);
					}else{
						if(t.get_state().equalsIgnoreCase("added")){
							dao.save(t);
						}else if(t.get_state().equalsIgnoreCase("removed")){
							dao.delete(t);
						}else if(t.get_state().equalsIgnoreCase("modified")){
							dao.update(t);
						}
					}
					
				}
			}
		}
		return list;
	}
	
	public static <T extends BaseGrid> List<T>  getGridDataByPid(Class<T> c,String pid,CommonDao<T> dao){
		List<T> list = dao.find("from "+c.getSimpleName()+" where pid='"+pid+"'");
		return list;
	}
	
	public static <T> void  deleteGridDataByPid(Class<T> c,String pid,CommonDao<T> dao){
		dao.delete("delete from "+c.getSimpleName()+" where pid='"+pid+"'");
	}
}
