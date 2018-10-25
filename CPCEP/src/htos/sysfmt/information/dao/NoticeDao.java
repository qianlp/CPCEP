package htos.sysfmt.information.dao;

import htos.common.entity.PageInfo;
import htos.coresys.dao.BaseDao;
import htos.sysfmt.information.entity.Notice;

import java.util.List;
import java.util.Map;

public interface NoticeDao extends BaseDao<Notice> {
		
		//分页显示新闻信息
		public Map<String, Object> findAllNoticeByPage(PageInfo pageInfo,String orgId);
}
