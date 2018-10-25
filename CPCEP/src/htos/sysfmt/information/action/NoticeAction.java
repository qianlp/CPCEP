package htos.sysfmt.information.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import htos.common.entity.PageInfo;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.util.CommonUtil;
import htos.sysfmt.information.entity.Notice;
import htos.sysfmt.information.service.NoticeService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class NoticeAction extends ActionSupport implements ModelDriven<Notice> {

	private static final long serialVersionUID = 1L;

	private Notice notice;
	private CommonFacadeService<Notice> commonFacadeService;
	private NoticeService noticeService;

	/**
	 * 查看公告
	 * @return
	 */
	public String readNotice() {
		String uuid = ServletActionContext.getRequest().getParameter("uuid");
		notice = commonFacadeService.getEntityByID(notice.getClass()
				.getSimpleName(), uuid);
		ServletActionContext.getRequest().setAttribute("notice", notice);
		return "success";
	}

	/**
	 * 公告保存和更新
	 * @return
	 */
	public String noticeOperate() {
		String menuId=ServletActionContext.getRequest().getParameter("menuId");
		if (!StringUtils.isEmpty(notice.getUuid())) {
			noticeService.updateNotice(notice);
		} else {
			noticeService.addNotice(notice,menuId);
		}
		return "success";
	}

	@Override
	public Notice getModel() {
		if (CommonUtil.isNullOrEmpty(notice)) {
			notice = new Notice();
		}
		return notice;
	}

	
	/**
	 * 分页查询所有公告信息返回JSON对象
	 * 首页调用
	 * @return
	 */
	public void findNoticeJsonPage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		PageInfo pageInfo = new PageInfo(pageIndex, pageSize, sortField,
				sortOrder);
		HttpSession session = request.getSession();
		String orgIds = (String) session.getAttribute("orgIds");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),
				noticeService.findAllNoticeByPage(pageInfo,orgIds));
	}

	
	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public CommonFacadeService<Notice> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(
			CommonFacadeService<Notice> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	public NoticeService getNoticeService() {
		return noticeService;
	}

	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}


}
