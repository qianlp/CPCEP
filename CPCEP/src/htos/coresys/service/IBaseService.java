package htos.coresys.service;


import htos.common.entity.PageInfo;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

public interface IBaseService<M> {
    
    public M save(M model);

    public void saveOrUpdate(M model);
    
    public void update(M model);
    
    public void merge(M model);

    public void deleteObject(M model);

    public M get(String id);
    
    public int countAll();
    
    public int countByExample(M model);
    
    public List<M> listAll();
    
    public List<M> findByExample(M model);
    
    public List<M> findByExampleD(M model);
    
    public List<M> findByExampleA(M model);
    
    public M findOneByExample(M m);
    
    public List<M> getPageList(M model, PageInfo dgm);
    
    public List<M> getPageList(M model, List<Criterion> list, PageInfo dgm);
    
    public Map<String, Object> getMap(M model, PageInfo dgm);
    
    public Map<String, Object> getMapSql(M model, PageInfo dgm);
    
    public String getQueryString(M model);

	int countByExample(M model, List<Criterion> list);

}
