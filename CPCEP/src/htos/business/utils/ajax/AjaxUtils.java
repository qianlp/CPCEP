package htos.business.utils.ajax;

import htos.coresys.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ajax工具类
 *
 * @author qinj
 * @date 2018-02-05 23:10
 **/
public class AjaxUtils {

	/**
	 * @description 返回错误信息
	 * @author qinj
	 * @date 2018/2/5 23:17
	 */
	public static void error(AjaxCode code) {
		error(code, code.getMessage());
	}

	/**
	 * @description 返回错误信息
	 * @author qinj
	 * @date 2018/2/5 23:17
	 */
	public static void error(AjaxCode code, String message) {
		if(code == null)
			code = AjaxCode.SERVER_ERROR;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code.getCode());
		map.put("message", StringUtils.isNoneBlank(message) ? message : code.getMessage());
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
	}

	/**
	 * @description 返回成功信息
	 * @author qinj
	 * @date 2018/2/5 23:18
	 */
	public static void success() {
		success(null);
	}

	/**
	 * @description 返回成功信息
	 * @author qinj
	 * @date 2018/2/5 23:18
	 */
	public static void success(String message) {
		if(StringUtils.isBlank(message))
			message = AjaxCode.SUCCESS.getMessage();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", AjaxCode.SUCCESS.getCode());
		map.put("message", message);
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
	}

	/**
	 * @description 返回单个对象
	 * @author qinj
	 * @date 2018/2/5 23:19
	 */
	public static <T> void renderData(T data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", AjaxCode.SUCCESS.getCode());
		map.put("message", AjaxCode.SUCCESS.getMessage());
		map.put("data", data);
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
	}

//	public static void renderMap(Object... param) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("code", AjaxCode.SUCCESS.getCode());
//		map.put("message", AjaxCode.SUCCESS.getMessage());
//		if(param == null || param.length == 0) {
//			CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
//			return ;
//		}
//
//		if(param.length % 2 != 0)
//			throw new RuntimeException("参数个数不是偶数");
//		AjaxMap ajaxMap = new AjaxMap();
//		for (int index = 0; index < param.length; index = index + 2) {
//			ajaxMap.set(param[index].toString(), param[index + 1]);
//		}
//		CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
//	}

	/**
	 * @description 返回列表信息
	 * @author qinj
	 * @date 2018/2/5 23:20
	 */
	public static <T> void renderList(List<T> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", AjaxCode.SUCCESS.getCode());
		map.put("message", AjaxCode.SUCCESS.getMessage());
		map.put("dataList", list);
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
	}

	public static class AjaxMap {

		private Map<String, Object> map;

		public <T> AjaxMap set(String key, T value) {
			if(map == null)
				map = new HashMap<String, Object>();
			map.put(key, value);
			return this;
		}

	}
}
