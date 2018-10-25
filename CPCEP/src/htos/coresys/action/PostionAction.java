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

import htos.coresys.entity.Postion;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.util.CommonUtil;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @author 温勋
 * @ClassName : 业务类型处理
 * @Version V1.1
 * @ModifiedBy 温勋
 * @Copyright 华胜龙腾
 * @date 2016年12月12日 上午11:37:19
 */
public class PostionAction extends ActionSupport implements
		ModelDriven<Postion> {
	private Postion pos;
	private CommonFacadeService<Postion> commonFacadeService;
	private CommonFacadeService<User> commonFacadeUser;
	
	public void findPersonByPos(){
		String posNo=ServletActionContext.getRequest().getParameter("posNo");
		List<User> uList=commonFacadeUser.getHqlList("from User where posNo like '%["+posNo+"]%'");
		List<Map<String, String>> newList=new ArrayList<Map<String, String>>();
		if(uList!=null && uList.size()>0){
			for(User u:uList){
				Map<String, String> nu=new HashMap<String, String>();
				nu.put("id", u.getUuid());
				nu.put("name", u.getUserName());
				newList.add(nu);
			}
		}
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),newList);
	}
	
	public void findPersonByDeptOrPos(){
		String posNo=ServletActionContext.getRequest().getParameter("posNo");
		String deptId=ServletActionContext.getRequest().getParameter("deptId");
		List<User> uList=commonFacadeUser.getHqlList("from User where userDeptId='"+deptId+"' and posNo like '%["+posNo+"]%'");
		List<Map<String, String>> newList=new ArrayList<Map<String, String>>();
		if(uList!=null && uList.size()>0){
			for(User u:uList){
				Map<String, String> nu=new HashMap<String, String>();
				nu.put("id", u.getUuid());
				nu.put("name", u.getUserName());
				newList.add(nu);
			}
		}
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),newList);
	}
	
	public String posOperate(){
		if(!StringUtils.isEmpty(pos.getUuid())){
			System.out.println(ServletActionContext.getRequest().getParameter("oldPosName"));
			commonFacadeService.update(pos);
			updateUser(2);
		}else{
			Postion lp=commonFacadeService.getHqlObject(" from Postion order by posNo desc");
			Integer no=Integer.parseInt((lp==null?"0":lp.getPosNo()))+1;
			pos.setPosNo(no.toString());
			String uuid=commonFacadeService.save(pos);
			pos.setUuid(uuid);
		}
		String addIds=ServletActionContext.getRequest().getParameter("addAllId");
		String delIds=ServletActionContext.getRequest().getParameter("delAllId");
		if(addIds!=null && !addIds.equals("")){
			posUserOperate(2,addIds);
		}
		if(delIds!=null && !delIds.equals("")){
			posUserOperate(1,delIds);
		}
		CommonUtil.toString(ServletActionContext.getResponse(), "<script>parent.goReload();</script>");
		return null;
	}
	
	public String findEditPosById(){
		String uuid=ServletActionContext.getRequest().getParameter("uuid");
		pos=commonFacadeService.getEntityByID("Postion", uuid);
		ServletActionContext.getRequest().setAttribute("pos", pos);
		return "success";
	}
	
	public void delPostion(){
		String uuid=ServletActionContext.getRequest().getParameter("uuid");
		pos.setUuid(uuid);
		updateUser(1);
		commonFacadeService.delete(pos);
		
	}
	
	private void updateUser(int type){
		List<User> uList=commonFacadeUser.getHqlList("from User where posId like '%"+pos.getUuid()+"%'");
		if(uList==null || uList.size()==0){
			return;
		}
		if(type==1){
			pos=commonFacadeService.getEntityByID("Postion", pos.getUuid());
			for(User u:uList){
				u.setPosId(u.getPosId().replace(pos.getUuid(), "").replace(",,",","));
				u.setPosName(u.getPosName().replace("["+pos.getPosName()+"]", "").replace(",,",","));
				u.setPosNo(u.getPosNo().replace("["+pos.getPosNo()+"]", "").replace(",,",","));
				commonFacadeUser.update(u);
			}
			return;
		}
		for(User u:uList){
			u.setPosName(u.getPosName().replace("["+pos.getOldPosName()+"]", "["+pos.getPosName()+"]"));
			u.setPosNo(u.getPosNo().replace("["+pos.getOldPosNo()+"]", "["+pos.getPosNo()+"]"));
			commonFacadeUser.update(u);
		}
	}
	
	private void posUserOperate(int type,String ids){
		String hql="from User where ";
		ids=ids.replace(",", "','");
		hql+=" uuid in ('"+ids+"')";
		if(type==1){
			hql+=" and posId like '%"+pos.getUuid()+"%'";
		}
		List<User> uList=commonFacadeUser.getHqlList(hql);
		if(uList==null || uList.size()==0){
			return;
		}
		if(type==1){
			for(User u:uList){
				u.setPosId(u.getPosId().replace(pos.getUuid(), "").replace(",,",","));
				u.setPosName(u.getPosName().replaceAll("["+pos.getOldPosName()+"]", "").replace(",,",","));
				u.setPosNo(u.getPosNo().replaceAll("["+pos.getOldPosNo()+"]", "").replace(",,",","));
				commonFacadeUser.update(u);
			}
			return;
		}
		for(User u:uList){
			if(u.getPosId()!=null && !u.getPosId().equals("")){
				u.setPosId(u.getPosId()+","+pos.getUuid());
			}else{
				u.setPosId(pos.getUuid());
			}
			
			if(u.getPosName()!=null && !u.getPosName().equals("")){
				u.setPosName(u.getPosName()+",["+pos.getPosName()+"]");
			}else{
				u.setPosName("["+pos.getPosName()+"]");
			}
			
			if(u.getPosNo()!=null && !u.getPosNo().equals("")){
				u.setPosNo(u.getPosNo()+",["+pos.getPosNo()+"]");
			}else{
				u.setPosNo("["+pos.getPosNo()+"]");
			}
			commonFacadeUser.update(u);
		}
	}
	
	public void findAllJson(){
		List<Postion> posList=commonFacadeService.getHqlList("from Postion");
		for(Postion p:posList){
			p.setPosNo("["+p.getPosNo()+"]");
			p.setPosName("["+p.getPosName()+"]");
		}
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),posList);
	}
	
	public void findJsonPosAll(){
		List<Postion> posList=commonFacadeService.getHqlList("from Postion");
		List<Map> list=new ArrayList();
		for(Postion p:posList){
			Map map=new HashMap();
			map.put("id", "["+p.getPosNo()+"]");
			map.put("text", "["+p.getPosName()+"]");
			list.add(map);
		}
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),list);
	}

	/**
	 * @return the pos
	 */
	public Postion getPos() {
		return pos;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(Postion pos) {
		this.pos = pos;
	}

	/**
	 * @return the commonFacadeService
	 */
	public CommonFacadeService<Postion> getCommonFacadeService() {
		return commonFacadeService;
	}

	/**
	 * @param commonFacadeService the commonFacadeService to set
	 */
	public void setCommonFacadeService(
			CommonFacadeService<Postion> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}
	
	

	/**
	 * @return the commonFacadeUser
	 */
	public CommonFacadeService<User> getCommonFacadeUser() {
		return commonFacadeUser;
	}

	/**
	 * @param commonFacadeUser the commonFacadeUser to set
	 */
	public void setCommonFacadeUser(CommonFacadeService<User> commonFacadeUser) {
		this.commonFacadeUser = commonFacadeUser;
	}

	@Override
	public Postion getModel() {
		if(pos==null){
			pos=new Postion();
		}
		return pos;
	}

}
