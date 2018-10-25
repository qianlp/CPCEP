package htos.coresys.service.impl;

import htos.coresys.dao.RightDao;
import htos.coresys.entity.Right;
import htos.coresys.entity.User;
import htos.coresys.service.RightService;
import htos.coresys.util.CommonUtil;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

//import com.ht.right.entity.Menu;

public class RightServiceImpl implements RightService {
	private RightDao rightDao;
	@Override
	public String convertToStr(Collection<String> c){
		if(CommonUtil.isNullOrEmpty(c)){
			return null;
		}else{
			return convertToStr(c.toArray());
		}
	}
	@Override
	public String convertToStr(Object[] arr){
		if(CommonUtil.isNullOrEmpty(arr)){
			return null;
		}else{
			String docIds = "'"+StringUtils.join(arr, "','")+"'";
			return docIds;
		}
	}
	
	@Override
	public void addRight(String docId,String modeName,String strJson) {
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute("user");
		JSONArray jsonArr = JSONArray.fromObject(strJson);
		Right r=null;
		for(int i=0 ;i<jsonArr.size();i++){
			JSONObject jsonObject = jsonArr.getJSONObject(i);
			r = new Right();
			r.setDocId(docId);
			r.setOrgId(jsonObject.getString("id"));
			r.setOperType(1);
			if("user".equals(jsonObject.getString("type"))){
				r.setRightType(2);
			}else if("dept".equals(jsonObject.getString("type"))){
				r.setRightType(1);
			}else if("role".equals(jsonObject.getString("type"))){
				r.setRightType(3);
			}
			r.setModoName(modeName);
			r.setCreateDate(new Date());
			if(user!=null){
				r.setCreateBy(user.getUserPerEname());
			}
			rightDao.save(r);
		}
	}
	
	@Override
	public void saveRight(String docId,String modeName,String userId,String type) {
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute("user");
		Right r= new Right();
			r.setDocId(docId);
			r.setOrgId(userId);
			r.setOperType(1);
			if("user".equals(type)){
				r.setRightType(2);
			}else if("dept".equals(type)){
				r.setRightType(1);
			}else if("role".equals(type)){
				r.setRightType(3);
			}
			r.setModoName(modeName);
			r.setCreateDate(new Date());
			if(user!=null){
				r.setCreateBy(user.getUserPerEname());
			}
			rightDao.save(r);
	}
	
	@Override
	public void updateRight(String docId,String modeName,String strJson) {
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute("user");
		List<Right> rightList = rightDao.getRightsByDocId(docId, modeName);
		Map<String,Right> map = new HashMap<String,Right>();
		for(Right r:rightList){
			map.put(r.getOrgId(), r);
		}
		//System.out.println(strJson);
		JSONArray jsonArr = JSONArray.fromObject(strJson);
		Right r=null;
		for(int i=0 ;i<jsonArr.size();i++){
			JSONObject jsonObject = jsonArr.getJSONObject(i);
			if(map.containsKey(jsonObject.getString("id"))){
			}else{
				r = new Right();
				r.setDocId(docId);
				r.setOrgId(jsonObject.getString("id"));
				r.setOperType(1);
				if("user".equals(jsonObject.getString("type"))){
					r.setRightType(2);
				}else if("dept".equals(jsonObject.getString("type"))){
					r.setRightType(1);
				}else if("role".equals(jsonObject.getString("type"))){
					r.setRightType(3);
				}else{
					r.setRightType(0);
				}
				r.setModoName(modeName);
				r.setCreateDate(new Date());
				if(user!=null){
					r.setCreateBy(user.getUserPerEname());
				}
				rightDao.save(r);
			}
			map.remove(jsonObject.getString("id"));
		}
		for(String id:map.keySet()){
			rightDao.delete(map.get(id));
		}
	}
	
	@Override
	public Set<String> getOrgIdsByDocId(Right right){
		if(CommonUtil.isNullOrEmpty(right) || CommonUtil.isNullOrEmpty(right.getDocId())){
			return null;
		}
		List<Right> rightList = rightDao.getRightsByDocId(right.getDocId(),right.getModoName());
		Set<String> set = new HashSet<String>();
		for(Right r:rightList){
			set.add(r.getOrgId());
		};
		return set;
	}

	@Override
	public Set<String> getDocIds(String orgIds,String modoName){

		Set<String> set = null;
		if(!CommonUtil.isNullOrEmpty(orgIds)){
			orgIds = "'"+orgIds.replace(",", "','")+"'";
			List<Right> list =  rightDao.getRightByOrgIds(orgIds,modoName);
			set = new HashSet<String>();
			for(Right r : list){
				set.add(r.getDocId());
			}
		}
		return set;
	}
	public RightDao getRightDao() {
		return rightDao;
	}
	public void setRightDao(RightDao rightDao) {
		this.rightDao = rightDao;
	}
	/* (non-Javadoc)
	 * @see htos.coresys.service.RightService#saveRight(htos.coresys.entity.Right)
	 */
	@Override
	public void saveRight(Right right) {
		rightDao.save(right);
	}
	/* (non-Javadoc)
	 * @see htos.coresys.service.RightService#getRightsByProprty(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Right> getRightsByProprty(String modoName, String orgIds,String docId) {
		return rightDao.getRightsByProprty(modoName, orgIds, docId);
	}
	@Override
	public List<Right> getRightsList(Right right) {
		return rightDao.getRightsList(right);
	}
	@Override
	public void delete(Right right) {
		rightDao.delete(right);
	}

}
