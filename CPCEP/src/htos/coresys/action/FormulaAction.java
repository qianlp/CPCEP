/**
 * 
 */
package htos.coresys.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import htos.coresys.entity.Formula;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.util.CommonUtil;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @author 温勋
 * @ClassName : 公式处理
 * @Version V1.1
 * @ModifiedBy 温勋
 * @Copyright 华胜龙腾
 * @date 2016年12月12日 上午11:37:19
 */
public class FormulaAction extends ActionSupport implements
		ModelDriven<Formula> {
	private Formula bt;
	private CommonFacadeService<Formula> commonFacadeService;
	
	public String docOperate(){
		if(!StringUtils.isEmpty(bt.getUuid())){
			commonFacadeService.saveOrUpdate(bt);
		}else{
			commonFacadeService.save(bt,ServletActionContext.getRequest().getParameter("menuId"));
		}
		return "success";
	}
	
	public void findAllJson(){
		List<Map<String,String>> nList=new ArrayList<Map<String, String>>();
		List<Formula> fList=commonFacadeService.loadList("Formula");
		for(Formula f:fList){
			Map<String,String> p=new HashMap<String, String>();
			p.put("id", f.getUuid());
			p.put("text", f.getTypeName());
			p.put("address", f.getAddress());
			nList.add(p);
		}
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),nList);
	}
	
	

	public Formula getBt() {
		return bt;
	}

	public void setBt(Formula bt) {
		this.bt = bt;
	}

	public CommonFacadeService<Formula> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(
			CommonFacadeService<Formula> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	@Override
	public Formula getModel() {
		if(bt==null){
			bt=new Formula();
		}
		return bt;
	}

}
