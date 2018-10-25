package htos.sysfmt.information.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import htos.common.entity.PageInfo;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.sysfmt.information.dao.NewsDao;
import htos.sysfmt.information.entity.News;
import htos.sysfmt.information.service.NewsService;

public class NewsServiceImpl implements NewsService {

	private NewsDao newsDao;
	private CommonFacadeService<News> commonFacadeService;
	public NewsDao getNewsDao() {
		return newsDao;
	}

	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}
	
	@Override
	public void updateNews(News news) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String shId = request.getParameter("userId");
		String readId = news.getReadId()+","+shId;
		news.setReadId(readId);
		//公共更新方法
		commonFacadeService.saveOrUpdate(news);
	}

	@Override
	public void addNews(News news,String menuId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String shId = request.getParameter("userId");
		User user = (User) request.getSession().getAttribute("user");
		String readId = news.getReadId()+","+user.getUuid()+","+shId;
		news.setReadId(readId);
		//公共保存方法
		commonFacadeService.save(news, menuId);
	}

	/**
	 * @return the commonFacadeService
	 */
	public CommonFacadeService<News> getCommonFacadeService() {
		return commonFacadeService;
	}

	/**
	 * @param commonFacadeService
	 *            the commonFacadeService to set
	 */
	public void setCommonFacadeService(
			CommonFacadeService<News> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	@Override
	public Map<String, Object> findAllNewsByPage(PageInfo pageInfo,String orgId) {
		return newsDao.findAllNewsByPage(pageInfo,orgId);
	}

	@Override
	public Map<String, Object> findNewsImg(PageInfo pageInfo,String orgId) {
		return newsDao.findNewsImg(pageInfo,orgId);
	}

}
