/**
 * 
 */
package htos.business.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;




import htos.business.entity.agreement.Agreement;
import htos.business.entity.bid.Invitation;
import htos.business.entity.bid.TechEvalClarify;
import htos.business.entity.biddingFile.BidFileQuestion;
import htos.business.entity.biddingFile.BiddingFileRelease;
import htos.business.entity.bulletin.BiddingBulletin;
import htos.business.entity.procurement.PurchasePlan;
import htos.coresys.service.CommonService;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author 温勋
 * @ClassName : DataSourceInit
 * @Version V1.1
 * @ModifiedBy 温勋
 * @Copyright 华胜龙腾
 * @date 2018年6月4日 下午2:28:54
 */
public class JobFlowNode extends QuartzJobBean{
	private ApplicationContext ctx;
	private CommonService commonService;
	
	public List job(PurchasePlan plan){
		System.out.println("定时检索新数据源!");
		String planId=plan.getUuid();
		List<List<String>> list=new ArrayList();
		boolean isRtu=true;
		//0-待处理，1-处理中，2-已处理，3-未处理，4-待开标，5-已开标，6-定标
		//PurchasePlan pp=(PurchasePlan) commonFacadeService.getEntityByID("PurchasePlan", planId);
		//招标公告
		List<String> l1=new ArrayList();
		BiddingBulletin bb=(BiddingBulletin) commonService.getEntityByProperty("BiddingBulletin", "purchasePlanUuid", planId);
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
		BiddingFileRelease bfr=(BiddingFileRelease) commonService.getEntityByProperty("BiddingFileRelease", "planId", planId);
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
		List<BidFileQuestion> questions = commonService.getHqlList(" from BidFileQuestion  where  bidFileUUID ='"+bfr.getUuid()+"'  and  feedBack is  null");
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
        List<TechEvalClarify> tecList=commonService.getHqlList("from TechEvalClarify where bidCode='"+bb.getCode()+"' and feedBack is null");
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
    	List<Invitation> iList=commonService.getHqlList(" from Invitation where type=1 and supplierBidUuid in (select uuid from SupplierBid where biddingFileUuid='"+bfr.getUuid()+"')");
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
        List<Invitation> i8List=commonService.getHqlList(" from Invitation where type=2 and supplierBidUuid in (select uuid from SupplierBid where biddingFileUuid='"+bfr.getUuid()+"')");
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
        Agreement agree=(Agreement) commonService.getEntityByProperty("Agreement", "purchasePlanUuid", planId);
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
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try{
			commonService=ctx.getBean(CommonService.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] nodeArr={"招标公告","上传招标文件","招标文件澄清","开标","投标文件质疑","邀请澄清报价","技术评标报告","邀请竞价","综合评审报告","定标","合同档案"};
		List<PurchasePlan> planList=commonService.getHqlList("from PurchasePlan where wfStatus>1 ");
		for(PurchasePlan plan:planList){
			List l=job(plan);
			if(l.size()==0){
				plan.setProjectNode("---");
			}else{
				plan.setProjectNode(nodeArr[l.size()-1]);
			}
			commonService.update(plan);
		}
	}
	
	public ApplicationContext getCtx() {
		return ctx;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {

		this.ctx = applicationContext;

	}

	/**
	 * @return the commonService
	 */
	public CommonService getCommonService() {
		return commonService;
	}

	/**
	 * @param commonService the commonService to set
	 */
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
	
	
}
