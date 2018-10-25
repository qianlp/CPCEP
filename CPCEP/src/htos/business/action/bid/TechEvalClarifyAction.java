package htos.business.action.bid;

import htos.business.entity.bid.TechEvalClarify;
import htos.business.entity.biddingFile.BiddingFileRelease;
import htos.business.entity.bulletin.view.ViewBiddingBulletin;
import htos.business.entity.supplier.SupplierAttachment;
import htos.business.entity.supplier.view.Page;
import htos.business.service.bid.TechEvalClarifyService;
import htos.common.entity.PageInfo;
import htos.common.util.StringUtil;
import htos.coresys.action.BaseAction;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.util.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import PluSoft.Utils.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class TechEvalClarifyAction extends ActionSupport implements ModelDriven<TechEvalClarify> {

    private CommonFacadeService<BiddingFileRelease> commonFacadeService;
    private CommonFacadeService<SupplierAttachment> attachmentCommonFacadeService;
    private CommonFacadeService<TechEvalClarify> techEvalClarifyCommonFacadeService;
    private TechEvalClarify techEvalClarify;
    private TechEvalClarifyService service;



    public void saveUpdateTechEvalClarify() throws Exception{

        HttpServletRequest request = ServletActionContext.getRequest();
        String data= request.getParameter("submitData");
        JSONObject jsonObject=JSONObject.fromObject(data);
        TechEvalClarify techEvalClarify=(TechEvalClarify)JSONObject.toBean(jsonObject, TechEvalClarify.class);

        if(!StringUtil.isEmpty(techEvalClarify.getUuid())){//更新
            techEvalClarifyCommonFacadeService.update(techEvalClarify);
        }else{//保存
            techEvalClarifyCommonFacadeService.save(techEvalClarify);
        }
    }
    //取已开标的项目
   public void bidFileClearList(){
	   HttpServletResponse response = ServletActionContext.getResponse();
	   List<BiddingFileRelease> list = commonFacadeService.getListByNotProperty("BiddingFileRelease", "status", "0");
	   for(BiddingFileRelease bid:list){
		  if(bid.getStatus()==1){
			  List<TechEvalClarify> list2 = techEvalClarifyCommonFacadeService.getListProperty("TechEvalClarify", "bidFileUUID", bid.getUuid());
			  for(TechEvalClarify tech:list2){
				  if(StringUtil.isEmpty(tech.getFeedBack())){
					  bid.setClarifyStatus("1");//未处理
				  }
			  }
		  }
	   }
	   Page<BiddingFileRelease> page = getPage();
       Map<String, Object> map = new HashMap<String, Object>();
       map.put("total", list.size());
       if(page.getpageIndex()==0){
    	   map.put("data", list.subList(0, page.getPageSize()));
       }else if(page.getpageIndex()==1){
    	   map.put("data", list.subList(page.getPageSize(), (page.getpageIndex()+1)*page.getPageSize()));
       }else{
    	   map.put("data", list.subList(page.getpageIndex()*page.getPageSize(), (page.getpageIndex()+1)*page.getPageSize()));
       }
       
	   //CommonUtil.toJsonStr(response, list,"biddingBulletin","createDate");
       CommonUtil.toJsonStr(response,map,"biddingBulletin","createDate");

   }
    //详情页
    public String clearFeedBackDetail(){
    	  HttpServletRequest request = ServletActionContext.getRequest();
    	  User user = (User)request.getSession().getAttribute("user");
          String uuid= request.getParameter("bidFileId");
          BiddingFileRelease biddingFileRelease = commonFacadeService.get(BiddingFileRelease.class, uuid);
          TechEvalClarify techEvalClarify=new TechEvalClarify();
          techEvalClarify.setBidCode(biddingFileRelease.getCode());
          techEvalClarify.setBidName(biddingFileRelease.getName());
          techEvalClarify.setVersion(biddingFileRelease.getVersion());
          techEvalClarify.setUuid("");
          request.setAttribute("techClarify", techEvalClarify);
         /* if(StringUtil.isEmpty(user.getUserType().toString())){
        	  request.setAttribute("userType", "userType");
          }else{
        	 
          }*/
          request.setAttribute("type", "type");
          if(user.getUserType()==null){
        	  request.setAttribute("userType", "userType");
          }
         return "success";
    }
    public String checkClarify(){
    	  HttpServletRequest request = ServletActionContext.getRequest();
    	  User user = (User)request.getSession().getAttribute("user");
          String uuid= request.getParameter("uuid");
        //  TechEvalClarify techEvalClarify =new TechEvalClarify();
          TechEvalClarify techEvalClarify = techEvalClarifyCommonFacadeService.get(TechEvalClarify.class, uuid);
           request.setAttribute("techClarify", techEvalClarify);
           request.setAttribute("ClarifyName", attachmentCommonFacadeService.get(SupplierAttachment.class, techEvalClarify.getClarifyFileUUID()).getFileName());
           if(techEvalClarify.getFeedBackFileUUID()!=null){
        	   request.setAttribute("FeedBackName", attachmentCommonFacadeService.get(SupplierAttachment.class, techEvalClarify.getFeedBackFileUUID()).getFileName());   
           }
           if(user.getUserType()!=null){
        	  request.setAttribute("type", "type");
           }
           request.setAttribute("techEvalClarify", techEvalClarify);
           return "success";
    }
    public String checkClarifySup(){
    	  HttpServletRequest request = ServletActionContext.getRequest();
    	  User user = (User)request.getSession().getAttribute("user");
          String uuid= request.getParameter("uuid");
          TechEvalClarify techEvalClarify =new TechEvalClarify();
           techEvalClarify = techEvalClarifyCommonFacadeService.get(TechEvalClarify.class, uuid);
           request.setAttribute("techClarify", techEvalClarify);
           request.setAttribute("ClarifyName", attachmentCommonFacadeService.get(SupplierAttachment.class, techEvalClarify.getClarifyFileUUID()).getFileName());
           if(techEvalClarify.getFeedBackFileUUID()!=null){
        	   request.setAttribute("FeedBackName", attachmentCommonFacadeService.get(SupplierAttachment.class, techEvalClarify.getFeedBackFileUUID()).getFileName());   
           }
           if(user.getUserType()!=null){
        	  request.setAttribute("type", "type");
           }
           request.setAttribute("techEvalClarify", techEvalClarify);
           return "success";
    }
     public String saveClear(){
    	 if(StringUtil.isEmpty(techEvalClarify.getUuid())){
    		 techEvalClarifyCommonFacadeService.save(techEvalClarify);
    	 }else{
    		 techEvalClarifyCommonFacadeService.update(techEvalClarify);
    	 }
    	 return "success";
     }
    //反馈澄清列表
    public void clearFeedList(){
    	  List<TechEvalClarify> techClarifyList=new ArrayList<>();
    	  HttpServletRequest request = ServletActionContext.getRequest();
    	  HttpServletResponse response = ServletActionContext.getResponse();
          User user = (User) request.getSession().getAttribute("user");
          String uuid= request.getParameter("bidFileId");
          //techEvalClarifyCommonFacadeService.getEntityByProperty("TechEvalClarify", "bidFileUUID", uuid); 
          List<TechEvalClarify> clarifyList = techEvalClarifyCommonFacadeService.getListByProperty("TechEvalClarify", "bidFileUUID", uuid);
          if(user.getUserType()==null){
        	  techClarifyList.addAll(clarifyList);
          }
          if(user.getUserType()!=null){
        	  for(TechEvalClarify tt:clarifyList){
        		  if(user.getUuid().equals(tt.getSupUUID())){
        			  techClarifyList.add(tt);
        		  }
        	  }
          }
          CommonUtil.toJsonStr(response, techClarifyList);
    }

    public String techEvalClarify() {
        HttpServletRequest request = ServletActionContext.getRequest();

        String name = request.getParameter("name");
        String code= request.getParameter("code");
        String version = request.getParameter("version");
        String supUUID = request.getParameter("supUUID");
        String supName = request.getParameter("supName");

        request.setAttribute("name",name);
        request.setAttribute("code",code);
        request.setAttribute("version",version);
        request.setAttribute("supName",supName);
        request.setAttribute("supUUID",supUUID);

        return ActionSupport.SUCCESS;
    }


    public String getTechEvalClarifyList(){

    	HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        User user = (User) request.getSession().getAttribute("user");
        Page<TechEvalClarify> page =getPage();
        service.findPage(user,page);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", page.getTotalCount());
        map.put("data", page.getResult());
        CommonUtil.toJsonStr(response, map);
        return null;
    }

    public String showClarifyPage(){
   HttpServletRequest request = ServletActionContext.getRequest();
        String uuid = request.getParameter("uuid");
        
        TechEvalClarify techEvalClarify = techEvalClarifyCommonFacadeService.getEntityByID(TechEvalClarify.class.getSimpleName(),uuid);
        request.setAttribute("bidCode",techEvalClarify.getBidCode());
        request.setAttribute("bidName",techEvalClarify.getBidName());
        request.setAttribute("supName",techEvalClarify.getSupName());
        request.setAttribute("supUUID",techEvalClarify.getSupUUID());
        request.setAttribute("version",techEvalClarify.getVersion());
        request.setAttribute("remark",techEvalClarify.getRemark());
        request.setAttribute("contact",techEvalClarify.getContact());
        request.setAttribute("issueType",techEvalClarify.getIssueType());
        request.setAttribute("feedBack",techEvalClarify.getFeedBack());
        request.setAttribute("techEvalClarify", techEvalClarify);

        return ActionSupport.SUCCESS;

    }


    public String showClarifyDetail(){
    	   HttpServletRequest request = ServletActionContext.getRequest();
        String uuid = request.getParameter("uuid");
        TechEvalClarify techEvalClarify = techEvalClarifyCommonFacadeService.getEntityByID(TechEvalClarify.class.getSimpleName(),uuid);
        request.setAttribute("bidCode",techEvalClarify.getBidCode());
        request.setAttribute("bidName",techEvalClarify.getBidName());
        request.setAttribute("supName",techEvalClarify.getSupName());
        request.setAttribute("supUUID",techEvalClarify.getSupUUID());
        request.setAttribute("version",techEvalClarify.getVersion());
        request.setAttribute("remark",techEvalClarify.getRemark());
        request.setAttribute("contact",techEvalClarify.getContact());
        request.setAttribute("issueType",techEvalClarify.getIssueType());
        request.setAttribute("feedBack",techEvalClarify.getFeedBack());
        request.setAttribute("techEvalClarify", techEvalClarify);

        return ActionSupport.SUCCESS;

    }

    // 供应商获取澄清反馈列表
//    public String getFeedBackList(){
//        HttpServletRequest request = ServletActionContext.getRequest();
//        User user = (User) request.getSession().getAttribute("user");
//
//        List<BidFileQuestion> list= commonFacadeService.getListByProperty(BidFileQuestion.class.getSimpleName(),"supUUID",user.getUuid());
//
//        for(BidFileQuestion bidFileQuestion:list){
//            if(bidFileQuestion.getQuestionFileAttachment()!=null&& !StringUtil.isEmpty(bidFileQuestion.getQuestionFileAttachment().getFileName())){
//                bidFileQuestion.setQuestionFileName(bidFileQuestion.getQuestionFileAttachment().getFileName());
//            }
//            if(bidFileQuestion.getFeedBackFileAttachment()!=null && !StringUtil.isEmpty(bidFileQuestion.getFeedBackFileAttachment().getFileName())){
//                bidFileQuestion.setFeedBackFileName(bidFileQuestion.getFeedBackFileAttachment().getFileName());
//            }
//        }
//        CommonUtil.toJsonStr(response, list,"questionFileAttachment","feedBackFileAttachment");
//        return null;
//    }


    @Override
    public TechEvalClarify getModel() {
        if(CommonUtil.isNullOrEmpty(techEvalClarify)){
            techEvalClarify= new TechEvalClarify();
        }
        return techEvalClarify;
    }

    public CommonFacadeService<TechEvalClarify> getTechEvalClarifyCommonFacadeService() {
        return techEvalClarifyCommonFacadeService;
    }

    public void setTechEvalClarifyCommonFacadeService(CommonFacadeService<TechEvalClarify> techEvalClarifyCommonFacadeService) {
        this.techEvalClarifyCommonFacadeService = techEvalClarifyCommonFacadeService;
    }

    public CommonFacadeService<BiddingFileRelease> getCommonFacadeService() {
        return commonFacadeService;
    }

    public void setCommonFacadeService(CommonFacadeService<BiddingFileRelease> commonFacadeService) {
        this.commonFacadeService = commonFacadeService;
    }

    public CommonFacadeService<SupplierAttachment> getAttachmentCommonFacadeService() {
        return attachmentCommonFacadeService;
    }

    public void setAttachmentCommonFacadeService(CommonFacadeService<SupplierAttachment> attachmentCommonFacadeService) {
        this.attachmentCommonFacadeService = attachmentCommonFacadeService;
    }

    public TechEvalClarifyService getService() {
        return service;
    }

    public void setService(TechEvalClarifyService service) {
        this.service = service;
    }
    public PageInfo getpageInfo(){
    	HttpServletRequest request = ServletActionContext.getRequest();
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		PageInfo pageInfo = new PageInfo(pageIndex, pageSize, sortField, sortOrder);
		return pageInfo;
	}

	public <T> Page<T> getPage(){
		HttpServletRequest request = ServletActionContext.getRequest();
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		return new Page<T>(pageIndex, pageSize, sortField, sortOrder);
	}
	
}
