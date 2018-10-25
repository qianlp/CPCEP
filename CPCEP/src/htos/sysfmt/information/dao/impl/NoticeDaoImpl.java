package htos.sysfmt.information.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import htos.common.entity.PageInfo;
import htos.coresys.dao.impl.BaseDaoImpl;
import htos.sysfmt.information.dao.NoticeDao;
import htos.sysfmt.information.entity.Notice;

public class NoticeDaoImpl extends BaseDaoImpl<Notice> implements NoticeDao {

	@Override
	public Map<String, Object> findAllNoticeByPage(PageInfo pageInfo,String orgId) {
		StringBuffer hql = new StringBuffer("from Notice where (wfStatus IS NULL OR wfStatus='' or wfStatus='2') and (accordingScopeOf='所有人员' ");
		String[] ids=orgId.split(",");
		for(String s:ids){
			hql.append(" or readId like '%"+s+"%'");
		}
		hql.append(") order by createDate desc");
		StringBuffer hqlCount = new StringBuffer("select count(*) from Notice");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("total", super.findUnique(hqlCount.toString()));
		dataMap.put(
				"data",
				super.find(hql.toString(), new String[] {},
						pageInfo.getpageIndex(), pageInfo.getPageSize()));
		return dataMap;
	}

}
