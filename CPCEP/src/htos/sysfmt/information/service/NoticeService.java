package htos.sysfmt.information.service;

import htos.common.entity.PageInfo;
import htos.sysfmt.information.entity.Notice;

import java.util.List;
import java.util.Map;

public interface NoticeService {
	// 更新新闻信息
	public void updateNotice(Notice notice);

	// 新增新闻信息
	public void addNotice(Notice notice,String menuId);

	// 分页显示新闻信息
	public Map<String, Object> findAllNoticeByPage(PageInfo pageInfo,String orgId);
}
