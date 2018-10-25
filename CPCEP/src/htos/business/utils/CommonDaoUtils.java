package htos.business.utils;

import htos.common.entity.PageInfo;
import htos.coresys.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommonDaoUtils {
	/**
	 * [{dataType:"text",operator:"",name:"",value:""}]
	 *
	 * @param searchList
	 * @param paramList
	 * @return
	 */
	//条件查询参数
	public static String createSearch(List searchList, List paramList) {
		StringBuffer hql = new StringBuffer(" ");
		if (paramList == null) paramList = new ArrayList();
		if (searchList != null && searchList.size() > 0) {
			for (int i = 0; i < searchList.size(); i++) {
				Map map = (Map) searchList.get(i);
//				String dataType = map.get("dataType").toString();
				String operator = map.get("operator").toString();
				String name = map.get("name").toString();
				Object value = map.get("value");
				if (operator.equals("@")) {
					hql.append(" and " + name + " like ? ");
					paramList.add("%" + value + "%");
				} else {
					hql.append(" and " + name + operator + " ? ");
					paramList.add(value);
				}
			}
		}
		return hql.toString();
	}

	public static String createSort(PageInfo pageInfo) {
		StringBuffer hql = new StringBuffer();
		if (CommonUtil.isNullOrEmpty(pageInfo.getSortField())) {
		} else {
			hql.append(" order by ");
			hql.append(pageInfo.getSortField());
		}
		if (CommonUtil.isNullOrEmpty(pageInfo.getSortOrder())) {
		} else {
			hql.append(" ");
			hql.append(pageInfo.getSortOrder());
		}
		return hql.toString();
	}
}
