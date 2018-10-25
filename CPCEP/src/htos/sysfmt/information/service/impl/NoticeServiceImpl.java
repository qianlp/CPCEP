package htos.sysfmt.information.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import htos.common.entity.PageInfo;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.sysfmt.information.dao.NoticeDao;
import htos.sysfmt.information.entity.Notice;
import htos.sysfmt.information.service.NoticeService;

public class NoticeServiceImpl implements NoticeService {

	private CommonFacadeService<Notice> commonFacadeService;
	private NoticeDao noticeDao;

	@Override
	public void updateNotice(Notice notice) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String shId = request.getParameter("userId");
		String readId = notice.getReadId()+","+shId;
		notice.setReadId(readId);
		commonFacadeService.saveOrUpdate(notice);
	}


	@Override
	public void addNotice(Notice notice,String meunId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String shId = request.getParameter("userId");
		User user = (User) request.getSession().getAttribute("user");
		String readId = notice.getReadId()+","+user.getUuid()+","+shId;
		notice.setReadId(readId);
		commonFacadeService.save(notice, meunId);
	}


	@Override
	public Map<String, Object> findAllNoticeByPage(PageInfo pageInfo,String orgId) {
		return noticeDao.findAllNoticeByPage(pageInfo,orgId);
	}

	public CommonFacadeService<Notice> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(
			CommonFacadeService<Notice> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}


	public NoticeDao getNoticeDao() {
		return noticeDao;
	}

	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

}
