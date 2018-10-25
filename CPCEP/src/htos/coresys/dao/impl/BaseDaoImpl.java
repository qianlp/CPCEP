package htos.coresys.dao.impl;

import htos.coresys.dao.BaseDao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


  

public class BaseDaoImpl<T> implements BaseDao<T>{  
		private SessionFactory sessionFactory;  
	    
	    public SessionFactory getCurrentSessionFactory() {  
	        return sessionFactory;  
	    }  
	  
	    public void setSessionFactory(SessionFactory sessionFactory) {  
	        this.sessionFactory = sessionFactory;  
	    }  
	  
	    public Session getCurrentSession() {
	    
	        return this.sessionFactory.getCurrentSession();  
	    } 
	
    public Serializable save(T o) {
    	 return this.getCurrentSession().save(o);
    }  
  
    public void delete(T o) {  
        this.getCurrentSession().delete(o);  
    }  
    
    public void delete(String hql){
    	this.getCurrentSession().createQuery(hql).executeUpdate();
    }
    
    public void update(T o) {  
        this.getCurrentSession().update(o);  
    }  
    public void saveOrUpdate(T o) {  
        this.getCurrentSession().saveOrUpdate(o);  
    }  
    
    public void merge(T o) {  
        this.getCurrentSession().merge(o);  
    } 
    
	@SuppressWarnings("unchecked")
	public List<T> find(String hql) {
        return this.getCurrentSession().createQuery(hql).list();  
    }  
  
    @SuppressWarnings("unchecked")
	public List<T> find(String hql, Object[] param) {  
        Query q = this.getCurrentSession().createQuery(hql);  
        if (param != null && param.length > 0) {  
            for (int i = 0; i < param.length; i++) {  
                q.setParameter(i, param[i]);  
            }  
        }  
        return q.list();  
    }  
  
    @SuppressWarnings("unchecked")
	public List<T> find(String hql, List<Object> param) {  
        Query q = this.getCurrentSession().createQuery(hql);  
        if (param != null && param.size() > 0) {  
            for (int i = 0; i < param.size(); i++) {  
                q.setParameter(i, param.get(i));  
            }  
        }  
        return q.list();  
    }  
  
    @SuppressWarnings("unchecked")
	public List<T> find(String hql, Object[] param, Integer page, Integer rows) {  
        if (page == null || page < 0) {  
            page = 0;  
        }  
        if (rows == null || rows < 1) {  
            rows = 10;  
        }  
        Query q = this.getCurrentSession().createQuery(hql);  
        if (param != null && param.length > 0) {  
            for (int i = 0; i < param.length; i++) {  
                q.setParameter(i, param[i]);  
            }  
        }  
        return q.setFirstResult(page * rows).setMaxResults(rows).list();  
    }  
  
    @SuppressWarnings("unchecked")
	public List<T> find(String hql, List<Object> param, Integer page, Integer rows) {  
    	 if (page == null || page < 0) {  
             page = 0;  
         }  
         if (rows == null || rows < 1) {  
             rows = 10;  
         }  
        Query q = this.getCurrentSession().createQuery(hql);  
        if (param != null && param.size() > 0) {  
            for (int i = 0; i < param.size(); i++) {  
            	
                q.setParameter(i, param.get(i));  
            }  
        }  
        return q.setFirstResult(page * rows).setMaxResults(rows).list();  
    }  
  
    @SuppressWarnings("unchecked")
	public T get(Class<T> c, Serializable id) {  
        return (T) this.getCurrentSession().get(c, id);  
    }  
  
    public T get(String hql, Object[] param) {  
        List<T> l = this.find(hql, param);  
        if (l != null && l.size() > 0) {  
            return l.get(0);  
        } else {  
            return null;  
        }  
    }  
  
    public T get(String hql, List<Object> param) {  
        List<T> l = this.find(hql, param);  
        if (l != null && l.size() > 0) {  
            return l.get(0);  
        } else {  
            return null;  
        }  
    }  
  
    
    public Object findUnique(String hql) {  
        return this.getCurrentSession().createQuery(hql).uniqueResult();  
    }
    
    public Object findUnique(String hql, Object[] param) {  
        Query q = this.getCurrentSession().createQuery(hql);  
        if (param != null && param.length > 0) {  
            for (int i = 0; i < param.length; i++) {  
                q.setParameter(i, param[i]);  
            }  
        }  
        return q.uniqueResult();  
    }  
  
    public Object findUnique(String hql, List<Object> param) {  
        Query q = this.getCurrentSession().createQuery(hql);  
        if (param != null && param.size() > 0) {  
            for (int i = 0; i < param.size(); i++) {  
                q.setParameter(i, param.get(i));  
            }  
        }  
        return q.uniqueResult();  
    }  
  
    public Integer executeHql(String hql) {  
        return this.getCurrentSession().createQuery(hql).executeUpdate();  
    }  
  
    public Integer executeHql(String hql, Object[] param) {  
        Query q = this.getCurrentSession().createQuery(hql);  
        if (param != null && param.length > 0) {  
            for (int i = 0; i < param.length; i++) {  
                q.setParameter(i, param[i]);  
            }  
        }  
        return q.executeUpdate();  
    }  
  
    public Integer executeHql(String hql, List<Object> param) {  
        Query q = this.getCurrentSession().createQuery(hql);  
        if (param != null && param.size() > 0) {  
            for (int i = 0; i < param.size(); i++) {  
                q.setParameter(i, param.get(i));  
            }  
        }  
        return q.executeUpdate();  
    }

	@Override
	public int countBySql(String sql) {
		Query query = this.getCurrentSession().createSQLQuery("select count(*) "+sql);
		return ((Number)query.uniqueResult()).intValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryBySqlPage(String sql, Integer page, Integer rows) {
		if (page == null || page < 0) {  
            page = 0;  
        }  
        if (rows == null || rows < 1) {  
            rows = 10;  
        }  
        Query q = this.getCurrentSession().createSQLQuery(sql);  
        return q.setFirstResult(page * rows).setMaxResults(rows).list();  
	}
	
	@Override
	public List<T> queryBySql(String sql) {
        Query q = this.getCurrentSession().createSQLQuery(sql);  
        return q.list();  
	}

	@Override
	public void saveOrUpdate(String sql) {
		 Query q =this.getCurrentSession().createSQLQuery(sql);
		 q.executeUpdate();
	}
	
  
}  