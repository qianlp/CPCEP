package htos.coresys.dao;

import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

/** 
 *  
 *  
 */ 

public interface BaseDao<T>{  
  
    /** 
     * 
     *  
     * @param o 
     * @return 
     */  
    public Serializable save(T o);  
  
    /** 
     * 
     *  
     * @param o 
     */  
    public void delete(T o);  
    
    public void delete(String hql);
  
    /** 
     * 
     *  
     * @param o 
     */  
    public void update(T o);  
  
    /** 
     * 
     *  
     * @param o 
     */  
    public void saveOrUpdate(T o);  
  
    /** 
     *
     *  
     * @param hql 
     * @return 
     */  
    public List<T> find(String hql);  
  
    /** 
     *
     *  
     * @param hql 
     * @param param 
     * @return 
     */  
    public List<T> find(String hql, Object[] param);  
  
    /** 
     *
     *  
     * @param hql 
     * @param param 
     * @return 
     */  
    public List<T> find(String hql, List<Object> param);  
  
    /** 
     *
     *  
     * @param hql 
     * @param param 
     * @param page 
     * @param rows 
     * @return 
     */  
    public List<T> find(String hql, Object[] param, Integer page, Integer rows);  
  
    /** 
     *  
     * @param hql 
     * @param param 
     * @param page 
     * @param rows 
     * @return 
     */  
    public List<T> find(String hql, List<Object> param, Integer page, Integer rows);  
  
    /** 
     *  
     * @param c 
     * @param id 
     * @return Object 
     */  
    public T get(Class<T> c, Serializable id);  
  
    /** 
     *  
     * @param hql 
     * @param param 
     * @return Object 
     */  
    public T get(String hql, Object[] param);  
  
    /** 
     * ���һ������ 
     *  
     * @param hql 
     * @param param 
     * @return 
     */  
    public T get(String hql, List<Object> param);  
  
    /** 
     *  
     * @param hql 
     * @return 
     */  
    public Object findUnique(String hql);  
  
    /** 
     *  
     * @param hql 
     * @param param 
     * @return 
     */  
    public  Object findUnique(String hql, Object[] param);  
  
    /** 
     *  
     * @param hql 
     * @param param 
     * @return 
     */  
    public  Object findUnique(String hql, List<Object> param);  
  
    /** 
     *  
     * @param hql 
     */  
    public Integer executeHql(String hql);  
  
    /** 
     *  
     * @param hql 
     * @param param 
     */  
    public Integer executeHql(String hql, Object[] param);  
  
    /** 
     *  
     * @param hql 
     * @param param 
     * @return 
     */  
    public Integer executeHql(String hql, List<Object> param);
    
    //本地sql统计
    public int countBySql(String sql);

    //本地sql分页查询
	public List<T> queryBySqlPage(String sql,Integer page, Integer rows);
	
	//本地sql不分页查询
	List<T> queryBySql(String sql);
	
	public void merge(T o);
	
	//保存或修改sql
	void saveOrUpdate(String sql);

	Session getCurrentSession();
    
} 