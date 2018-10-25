package htos.business.flow.action;

import htos.business.entity.agreement.Agreement;
import htos.business.entity.bid.Invitation;
import htos.business.entity.bid.TechBidEval;
import htos.business.entity.bid.TechEvalClarify;
import htos.business.entity.biddingFile.BidFileQuestion;
import htos.business.entity.biddingFile.BiddingFileRelease;
import htos.business.entity.bulletin.BiddingBulletin;
import htos.business.entity.procurement.PurchasePlan;
import htos.business.flow.entity.FlowItemMaintain;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.util.CommonUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import PluSoft.Utils.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class FlowItemMaintainAction extends ActionSupport implements ModelDriven<FlowItemMaintain> {

	private static final long serialVersionUID = 1L;

	private FlowItemMaintain fim;
	private CommonFacadeService commonFacadeService;

	/**
	 * 保存和更新
	 * @return
	 */
	public String fimOperate() {
		if (!StringUtils.isEmpty(fim.getUuid())) {
			commonFacadeService.update(fim);
		} else {
			commonFacadeService.save(fim);
		}
		return "success";
	}
	
	public void findFirstFim() {
		String planId=ServletActionContext.getRequest().getParameter("planId");
		fim=(FlowItemMaintain) commonFacadeService.getHqlObject(" from FlowItemMaintain ");
		ServletActionContext.getRequest().setAttribute("fim", fim);
		if(!StringUtils.isEmpty(planId)){
			PurchasePlan pp=(PurchasePlan) commonFacadeService.getEntityByID("PurchasePlan", planId);
			ServletActionContext.getRequest().setAttribute("plan", pp);
			BiddingBulletin bb=(BiddingBulletin) commonFacadeService.getEntityByProperty("BiddingBulletin", "purchasePlanUuid", planId);
			ServletActionContext.getRequest().setAttribute("bBull", bb);
			BiddingFileRelease bfr=(BiddingFileRelease) commonFacadeService.getEntityByProperty("BiddingFileRelease", "planId", planId);
			ServletActionContext.getRequest().setAttribute("bFile", bfr);
			//邀请竞价
			String hql="select count(*) from Invitation a , SupplierBid b where b.uuid=a.supplierBidUuid and b.biddingFileUuid='"+bfr.getUuid()+"' and a.type='2'";
			Object number = commonFacadeService.getHqlObject(hql);
			int count = Integer.parseInt(number.toString());
			if(count == 0)
				ServletActionContext.getRequest().setAttribute("invStruts", false);
            else
            	ServletActionContext.getRequest().setAttribute("invStruts", true);
			//邀请澄清
			String sql="select count(*) from Invitation a , SupplierBid b where b.uuid=a.supplierBidUuid and b.biddingFileUuid='"+bfr.getUuid()+"' and a.type='1'";
		    Object claNumber = commonFacadeService.getHqlObject(sql);
			int claCount = Integer.parseInt(claNumber.toString());
			if(claCount == 0)
				ServletActionContext.getRequest().setAttribute("clarifyStruts", false);
            else
            	ServletActionContext.getRequest().setAttribute("clarifyStruts", true);
		}
	}
	
	public void findBidReturn(){
		String code=ServletActionContext.getRequest().getParameter("code");
		List<TechEvalClarify> tecList=commonFacadeService.getHqlList("from TechEvalClarify where bidCode='"+code+"' order by feedBack desc");
		CommonUtil.toString(ServletActionContext.getResponse(), JSON.Encode(tecList));
	}
	
	public void findSupSignupList(){
		String bid=ServletActionContext.getRequest().getParameter("bid");
		List<Map<String,Object>> mList=commonFacadeService.getHqlList("select new map(uuid as uuid,contact as contact,position as position,phone as phone,mobile as mobile,email as email,createBy as createBy,createDate as createDate) from SupplierSignup where bidding.uuid='"+bid+"' order by createDate asc");
//		List<Map<String,Object>> mList=new ArrayList();
//		for(SupplierSignup s:ssList){
//			Map<String,Object> map=new HashMap();
//			map.put("uuid", s.getUuid());
//			map.put("contact", s.getContact());
//			map.put("position", s.getPosition());
//			map.put("phone", s.getPhone());
//			map.put("mobile", s.getMobile());
//			map.put("email", s.getEmail());
//			map.put("createBy", s.getCreateBy());
//			map.put("createDate", s.getCreateDate());
//			//map.put("supId", s.getSupplier().getUuid());
//			mList.add(map);
//		}
		CommonUtil.toString(ServletActionContext.getResponse(), JSON.Encode(mList));
	}
	
	public void findSupScopeList(){
		String bFileId=ServletActionContext.getRequest().getParameter("bFileId");
		TechBidEval tbe=(TechBidEval) commonFacadeService.getEntityByProperty("TechBidEval", "biddingFileUuid", bFileId);
		if(tbe==null){
			CommonUtil.toString(ServletActionContext.getResponse(), "[]");
			return;
		}
		List<Map<String,String>> list=commonFacadeService.getHqlList("select new map(a.uuid as uuid,b.createBy as supName,a.score as score) from SupplierScore a,SupplierBid b where a.techBidEvalUuid='"+tbe.getUuid()+"' and a.supplierBidUuid=b.uuid order by a.score desc ");
		CommonUtil.toString(ServletActionContext.getResponse(), JSON.Encode(list));
	}
	
	public void findNodeStatus(){
		String planId=ServletActionContext.getRequest().getParameter("planId");
		CommonUtil.toString(ServletActionContext.getResponse(), JSON.Encode(getNodeStatus(planId)));
	}
	
	private List getNodeStatus(String planId){
		List<List<String>> list=new ArrayList();
		boolean isRtu=true;
		//0-待处理，1-处理中，2-已处理，3-未处理，4-待开标，5-已开标，6-定标
		//PurchasePlan pp=(PurchasePlan) commonFacadeService.getEntityByID("PurchasePlan", planId);
		//招标公告
		List<String> l1=new ArrayList();
		BiddingBulletin bb=(BiddingBulletin) commonFacadeService.getEntityByProperty("BiddingBulletin", "purchasePlanUuid", planId);
		if(bb!=null){
			if(!bb.getWfStatus().equals("2") && !bb.getWfStatus().equals("9")){
				isRtu=false;
				l1.add(bb.getWfStatus());
				l1.add(bb.getCurUser());
			}else{
				l1.add("2");
				l1.add("---");
			}
		}else{
			isRtu=false;
			l1.add("---");
			l1.add("---");
		}
		list.add(l1);
		
		if(!isRtu){
			return list;
		}
		
		//招标文件上传
		List<String> l2=new ArrayList();
		BiddingFileRelease bfr=(BiddingFileRelease) commonFacadeService.getEntityByProperty("BiddingFileRelease", "planId", planId);
		if(bfr!=null){
			l2.add("2");
			l2.add(bfr.getCreateBy());
		}else{
			isRtu=false;
			l2.add("---");
			l2.add("---");
		}
		list.add(l2);
		if(!isRtu){
			return list;
		}
		
		//招标文件澄清
		List<String> l3=new ArrayList();
		List<BidFileQuestion> questions = commonFacadeService.getHqlList(" from BidFileQuestion  where  bidFileUUID ='"+bfr.getUuid()+"'  and  feedBack is  null");
        if(questions !=null&&questions.size()>0){
        	isRtu=false;
        	l3.add("3");
			l3.add("---");
        }else{
        	l3.add("2");
        	l3.add("---");
        }
        list.add(l3);
        if(!isRtu){
			return list;
		}
        
        //开标
        List<String> l4=new ArrayList();
        if(bfr.getStatus()==0){
        	isRtu=false;
        	l4.add("4");
        	l4.add("---");
        }else if(bfr.getStatus()>=1){
        	l4.add("5");
        	l4.add("---");
        }
        list.add(l4);
        if(!isRtu){
			return list;
		}
        
        //投标文件质疑
        List<TechEvalClarify> tecList=commonFacadeService.getHqlList("from TechEvalClarify where bidCode='"+bb.getCode()+"' and feedBack is null");
        List<String> l5=new ArrayList();
        if(tecList!=null&&tecList.size()>0){
        	isRtu=false;
        	l5.add("0");
            l5.add("---");
        }else{
        	l5.add("2");
            l5.add("---");
        }
    	list.add(l5);
    	if(!isRtu){
			return list;
		}
    	
    	//邀请澄清报价
    	List<Invitation> iList=commonFacadeService.getHqlList(" from Invitation where type=1 and supplierBidUuid in (select uuid from SupplierBid where biddingFileUuid='"+bfr.getUuid()+"')");
    	List<String> l6=new ArrayList();
    	if(iList==null || iList.size()==0){
    		isRtu=false;
    		l6.add("0");
    		l6.add("---");
    	}else{
    		Invitation i=iList.get(0);
    		if(i.getEndDate().getTime()<(new Date()).getTime()){
    			l6.add("2");
        		l6.add("---");
    		}else{
    			isRtu=false;
    			l6.add("1");
        		l6.add("---");
    		}
    	}
    	list.add(l6);
    	if(!isRtu){
			return list;
		}
        
    	//技术评标报告
        List<String> l7=new ArrayList();
        if(bfr.getStatus()>=2){
        	l7.add("2");
        	l7.add("---");
        }else{
        	isRtu=false;
        	l7.add("0");
        	l7.add("---");
        }
        list.add(l7);
        if(!isRtu){
			return list;
		}
        
        //邀请竞价
        List<Invitation> i8List=commonFacadeService.getHqlList(" from Invitation where type=2 and supplierBidUuid in (select uuid from SupplierBid where biddingFileUuid='"+bfr.getUuid()+"')");
        List<String> l8=new ArrayList();
        if(i8List==null || i8List.size()==0){
    		isRtu=false;
    		l8.add("0");
    		l8.add("---");
    	}else{
    		Invitation i=i8List.get(0);
    		if(i.getEndDate().getTime()<(new Date()).getTime()){
    			l8.add("2");
    			l8.add("---");
    		}else{
    			isRtu=false;
    			l8.add("1");
    			l8.add("---");
    		}
    	}
    	list.add(l8);
    	if(!isRtu){
			return list;
		}
    	
    	//综合评审报告
        List<String> l9=new ArrayList();
        if(bfr.getStatus()>=3){
        	l9.add("2");
        	l9.add("---");
        }else{
        	isRtu=false;
        	l9.add("0");
        	l9.add("---");
        }
        list.add(l9);
        if(!isRtu){
			return list;
		}
        
        //定标
        List<String> l10=new ArrayList();
        if(bfr.getStatus()>=4){
        	l10.add("6");
        	l10.add("---");
        }else{
        	isRtu=false;
        	l10.add("0");
        	l10.add("---");
        }
        list.add(l10);
        if(!isRtu){
			return list;
		}
        
        //合同档案
        List<String> l11=new ArrayList();
        Agreement agree=(Agreement) commonFacadeService.getEntityByProperty("Agreement", "purchasePlanUuid", planId);
        if(agree!=null){
        	l11.add("2");
        	l11.add(agree.getCreateBy());
		}else{
			l11.add("0");
			l11.add("---");
		}
		list.add(l11);
        return list;
	}


	@Override
	public FlowItemMaintain getModel() {
		if (CommonUtil.isNullOrEmpty(fim)) {
			fim = new FlowItemMaintain();
		}
		return fim;
	}


	/**
	 * @return the fim
	 */
	public FlowItemMaintain getFim() {
		return fim;
	}


	/**
	 * @param fim the fim to set
	 */
	public void setFim(FlowItemMaintain fim) {
		this.fim = fim;
	}


	/**
	 * @return the commonFacadeService
	 */
	public CommonFacadeService getCommonFacadeService() {
		return commonFacadeService;
	}


	/**
	 * @param commonFacadeService the commonFacadeService to set
	 */
	public void setCommonFacadeService(CommonFacadeService commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}
	
	

}
