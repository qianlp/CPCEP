package htos.sysfmt.information.service;

import htos.common.entity.PageInfo;
import htos.sysfmt.information.entity.News;

import java.util.List;
import java.util.Map;

public interface NewsService {

	// 更新新闻信息
	public void updateNews(News news);

	// 新增新闻信息
	public void addNews(News news,String menuId);

	// 分页显示新闻信息
	public Map<String, Object> findAllNewsByPage(PageInfo pageInfo,String orgId);

	// 分页显示新闻图片
	public Map<String, Object> findNewsImg(PageInfo pageInfo,String orgId);
}
