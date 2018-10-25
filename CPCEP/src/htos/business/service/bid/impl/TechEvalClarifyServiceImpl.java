package htos.business.service.bid.impl;

import htos.business.dao.bid.TechEvalClarifyDao;
import htos.business.entity.bid.TechEvalClarify;
import htos.business.entity.biddingFile.BidFileQuestion;
import htos.business.entity.supplier.view.Page;
import htos.business.service.bid.TechEvalClarifyService;
import htos.coresys.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class TechEvalClarifyServiceImpl implements TechEvalClarifyService {

    private TechEvalClarifyDao dao;

    @Override
    public void findPage(User user, Page<TechEvalClarify> page) {
        String hql = "select count(*) from TechEvalClarify mc ";
        if(user.getUserType() != null && user.getUserType() != 0) {
            hql = hql+ " where  mc.supUUID='"+user.getUuid()+"'";
        }
        Number count = (Number) dao.getCurrentSession().createQuery(hql).uniqueResult();
        if(count == null) {
            page.setTotalCount(0);
            return ;
        }
        page.setTotalCount(count.intValue());
        if(count.intValue() == 0)
            return ;

        String sql = "  from TechEvalClarify mc  ";
        if(user.getUserType() != null && user.getUserType() != 0) {
            sql =sql+ " where  mc.supUUID='"+user.getUuid()+"'";
        }

        int from = (page.getpageIndex() ) * page.getPageSize();
        Session session = dao.getCurrentSession();
        Query query = session.createQuery(sql);
        query.setMaxResults(page.getPageSize());
        query .setFirstResult(from);
        List<TechEvalClarify> list =  query .list();
        page.setResult(list);
//        page.setTotalCount(list.size());
    }


    public TechEvalClarifyDao getDao() {
        return dao;
    }

    public void setDao(TechEvalClarifyDao dao) {
        this.dao = dao;
    }
}
