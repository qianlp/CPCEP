/**
 * 
 */
package htos.coresys.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import PluSoft.Utils.JSON;
import htos.common.util.StringUtil;
import htos.coresys.entity.DataRightConfig;
import htos.coresys.entity.User;
import htos.coresys.service.CommonService;
import htos.coresys.service.DataRightConfigService;
import htos.coresys.service.DeptService;

/**
 * @author 温勋
 * @ClassName : dataRightConfigServiceImpl
 * @Version V1.1
 * @ModifiedBy 温勋
 * @Copyright 华胜龙腾
 * @date 2017年9月25日 上午10:34:41
 */
public class DataRightConfigServiceImpl implements DataRightConfigService {
	private CommonService commonService;
	private DeptService deptService;
	
	@Override
	public String findDataRightByMenu(String menuId) {
		Set<String> ids=new HashSet();
		boolean isBreak=false;
		HttpServletRequest request = ServletActionContext.getRequest();
		User user=(User)request.getSession().getAttribute("user");
		String orgId=(String)request.getSession().getAttribute("orgIds");
		DataRightConfig dataRightConfig=(DataRightConfig) commonService.getEntityByProperty("DataRightConfig", "menuId", menuId);
		
		if(dataRightConfig==null){
			return "";
		}
		
		//全局权限
		List allPos=(List) JSON.Decode(dataRightConfig.getAllPos());
		List allRole=(List) JSON.Decode(dataRightConfig.getAllRole());
		List allUser=(List) JSON.Decode(dataRightConfig.getAllUser());
		
		//判断是否有符合的职能
		if(!StringUtil.isEmpty(user.getPosNo())){
			for(Object o:allPos){
				Map m=(Map)o;
				if(user.getPosNo().indexOf(m.get("id").toString())>-1){
					isBreak=true;
					break;
				}
			}
		}
		if(isBreak)return "-";
		
		//判断是否有符合的角色
		if(!StringUtil.isEmpty(orgId)){
			for(Object o:allRole){
				Map m=(Map)o;
				if(orgId.indexOf(m.get("id").toString())>-1){
					isBreak=true;
					break;
				}
			}
		}
		if(isBreak)return "-";
		
		//判断是否有符合的人员
		if(!StringUtil.isEmpty(user.getUuid())){
			for(Object o:allUser){
				Map m=(Map)o;
				if(user.getUuid().endsWith(m.get("id").toString())){
					isBreak=true;
					break;
				}
			}
		}
		if(isBreak)return "-";
		
		//-------------------------------
		//人员不再全局中才会走下边
		//-------------------------------
		//本部门
		if(dataRightConfig.getRightType().equals("1")){
			ids.add(user.getUserDeptId());
		}else{
			List diyPos=(List) JSON.Decode(dataRightConfig.getDiyPos());
			List diyRole=(List) JSON.Decode(dataRightConfig.getDiyRole());
			List diyUser=(List) JSON.Decode(dataRightConfig.getDiyUser());
			
			//判断是否有符合的职能
			if(!StringUtil.isEmpty(user.getPosNo())){
				for(Object o:diyPos){
					Map m=(Map)o;
					if(user.getPosNo().indexOf(m.get("id").toString())>-1){
						ids.add(user.getUserDeptId());
						break;
					}
				}
			}
			
			//判断是否有符合的角色
			if(!StringUtil.isEmpty(orgId)){
				for(Object o:diyRole){
					Map m=(Map)o;
					if(orgId.indexOf(m.get("id").toString())>-1){
						ids.add(user.getUserDeptId());
						break;
					}
				}
			}
			
			//判断是否有符合的人员
			if(!StringUtil.isEmpty(user.getUuid())){
				for(Object o:diyUser){
					Map m=(Map)o;
					if(user.getUuid().endsWith(m.get("id").toString())){
						ids.add(user.getUserDeptId());
						break;
					}
				}
			}
		}
		
		//自定义权限
		List diyRight=(List) JSON.Decode(dataRightConfig.getDiyRight());
		for(Object o:diyRight){
			Map m=(Map)o;
			if(rtu(m.get("type")).equals("0")){
				if(rtu(m.get("name")).indexOf(user.getUuid())>-1){
					ids.addAll(getIds(rtu(m.get("dept"))));
				}
			}else if(rtu(m.get("type")).equals("1")){
				List<String> posIds=getIds(rtu(m.get("name")));
				for(String s:posIds){
					if(user.getPosNo().indexOf(s)>-1){
						ids.addAll(getIds(rtu(m.get("dept"))));
						break;
					}
				}
			}else if(rtu(m.get("type")).equals("2")){
				List<String> roleIds=getIds(rtu(m.get("name")));
				for(String s:roleIds){
					if(orgId.indexOf(s)>-1){
						ids.addAll(getIds(rtu(m.get("dept"))));
						break;
					}
				}
			}
		}
		
		List<String> subDeptIds=new ArrayList();
		for(String id:ids){
			List<String> d=deptService.getDeptIdsByPid(id);
			if(d.size()>0){
				subDeptIds.addAll(d);
			}
		}
		if(subDeptIds.size()>0){
			ids.addAll(subDeptIds);
		}
		return StringUtils.join(ids, "','");
	}
	
	private String rtu(Object o){
		if(o==null){ return ""; }
		return o.toString();
	}
	
	private List<String> getIds(String deptIds){
		if(StringUtil.isEmpty(deptIds)){
			return new ArrayList();
		}
		
		return java.util.Arrays.asList(deptIds.split(","));
	}
	
	public CommonService getCommonService() {
		return commonService;
	}
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public DeptService getDeptService() {
		return deptService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
	
}
