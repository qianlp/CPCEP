package htos.coresys.action;

import htos.common.util.CopyUtil;
import htos.common.util.StringUtil;
import htos.coresys.entity.Button;
import htos.coresys.entity.User;
import htos.coresys.service.ButtonService;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.util.CommonUtil;

import java.beans.IntrospectionException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 按钮配置
 * @author zcl
 */
public class ButtonAction extends ActionSupport implements ModelDriven<Button>{
	private static final Logger log = Logger.getLogger(ButtonAction.class);
	private static final long serialVersionUID = 1L;
	private Button buttonModel;
	private CommonFacadeService<Button> commonFacadeService;
	private ButtonService buttonService;
	
	//查询所有表中数据
	public String findButtonListJson(){
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),commonFacadeService.loadList(Button.class.getSimpleName()));
		return null;
	}
	
	//保存或者更新
	public String saveButtonJson() throws IntrospectionException{
		Map<String, Object> map = new HashMap<String, Object>(2);
		if(buttonModel!=null && !StringUtil.isEmpty(buttonModel.getUuid())){
			User user =	(User)ServletActionContext.getRequest().getSession().getAttribute("user");
			//更新
			Button buttonMode = commonFacadeService.get(Button.class, buttonModel.getUuid());
			CopyUtil.copyToObj(buttonMode, buttonModel);
			buttonMode.setUpdateBy(user.getUserPerEname());
			buttonMode.setUpdateDate(new Date());
			buttonMode.setParentId("-1");
			buttonService.updateButtonJson(buttonMode);
			log.info("==========exhibitParamJson=====更新成功==========");
			map.put("success",true);
			map.put("msg", "更新成功");
		}else{
			//新增
			buttonModel.setParentId("-1");
			buttonService.saveButton(buttonModel);
			log.info("==========exhibitParamJson=====保存成功==========");
			map.put("success",true);
			map.put("msg", "保存成功");
		}
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),map);
		return null;
	}
	
	// 删除按钮
	public String deleteButton() {
		commonFacadeService.deleteId("Button", "uuid", buttonModel.getUuid());
		log.info("delMenu=======>删除操作成功！uuid=" + buttonModel.getUuid());
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("success", true);
		map.put("msg", "删除按钮成功！");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), map);
		return null;
	}

	
	//新增或修改前查询
	public String queryButtonById(){
		HttpServletRequest request = ServletActionContext.getRequest();
		if(!StringUtil.isEmpty(request.getParameter("uuid"))){
			buttonModel = commonFacadeService.getEntityByID(Button.class.getSimpleName(), request.getParameter("uuid"));
		}
		return "success";
	}

	@Override
	public Button getModel() {
		if(CommonUtil.isNullOrEmpty(buttonModel)){
			buttonModel = new Button();
		}
		return buttonModel;
	}

	public Button getButtonModel() {
		return buttonModel;
	}

	public void setButtonModel(Button buttonModel) {
		this.buttonModel = buttonModel;
	}

	public CommonFacadeService<Button> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(CommonFacadeService<Button> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	public ButtonService getButtonService() {
		return buttonService;
	}

	public void setButtonService(ButtonService buttonService) {
		this.buttonService = buttonService;
	}

	
}
