package htos.sysfmt.information.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import htos.common.entity.PageInfo;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.util.CommonUtil;
import htos.sysfmt.information.entity.News;
import htos.sysfmt.information.service.NewsService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class NewsAction extends ActionSupport implements ModelDriven<News> {

	private static final long serialVersionUID = 1L;

	private News news;
	private NewsService newsService;
	private CommonFacadeService<News> commonFacadeService;

	public CommonFacadeService<News> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(
			CommonFacadeService<News> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	/**
	 * 查看新闻
	 * 首页调用
	 * @return
	 */
	public String readNews() {
		String uuid = ServletActionContext.getRequest().getParameter("uuid");
		news = commonFacadeService.getEntityByID(news.getClass()
				.getSimpleName(), uuid);
		ServletActionContext.getRequest().setAttribute("news", news);
		return "success";
	}

	/**
	 * 新闻保存和更新
	 * @return
	 */
	public String newsOperate() {
		String menuId=ServletActionContext.getRequest().getParameter("menuId");
		if (!StringUtils.isEmpty(news.getUuid())) {
			newsService.updateNews(news);
		} else {
			newsService.addNews(news,menuId);
		}
		return "success";
	}


	/**
	 * 分页查询所有新闻信息返回JSON对象
	 * 首页调用
	 * @return
	 */
	public void findNewJsonPage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");
		PageInfo pageInfo = new PageInfo(pageIndex, pageSize);
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),newsService.findAllNewsByPage(pageInfo,orgIds));
	}
	
	/**
	 * 查询图片新闻
	 * 首页调用
	 * @return
	 */
	public void findNewsImg(){
		HttpServletRequest request = ServletActionContext.getRequest();
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");
		
		PageInfo pageInfo = new PageInfo(pageIndex, pageSize);
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),
				newsService.findNewsImg(pageInfo,orgIds));
	}

	public News getnews() {
		return news;
	}

	public void setnews(News news) {
		this.news = news;
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	@Override
	public News getModel() {
		if (CommonUtil.isNullOrEmpty(news)) {
			news = new News();
		}
		return news;
	}

}
