package htos.sysfmt.information.dao;

import java.util.List;
import java.util.Map;

import htos.common.entity.PageInfo;
import htos.coresys.dao.BaseDao;
import htos.sysfmt.information.entity.News;

public interface NewsDao extends BaseDao<News> {

	
	//分页显示新闻信息
	public Map<String, Object> findAllNewsByPage(PageInfo pageInfo,String orgId);
	
	//分页显示新闻图片
	public Map<String,Object> findNewsImg(PageInfo pageInfo,String orgId);

}
