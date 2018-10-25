package htos.business.action.biddingFile;


import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import htos.business.action.supplier.SupplierFileAction;
import htos.business.entity.bid.TechBidEval;
import htos.business.entity.bid.TechEvalClarify;
import htos.business.entity.biddingFile.BidFileQuestion;
import htos.business.entity.biddingFile.BiddingFileRelease;
import htos.business.entity.biddingFile.IssueType;
import htos.business.entity.bulletin.view.ViewBiddingBulletin;
import htos.business.entity.supplier.SupplierAttachment;
import htos.business.entity.supplier.SupplierExt;
import htos.business.entity.supplier.view.Page;
import htos.business.entity.supplier.view.ViewUserSupplier;
import htos.business.service.bidFileRelease.BidFileQuestionService;
import htos.business.service.bidFileRelease.BidFileReleaseService;
import htos.business.service.supplier.SupplierService;
import htos.business.utils.ajax.AjaxCode;
import htos.business.utils.ajax.AjaxUtils;
import htos.common.util.StringUtil;
import htos.coresys.action.BaseAction;
import htos.coresys.entity.Menu;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.MenuService;
import htos.coresys.util.CommonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 招标文件质疑
 * @author
 */

public class BidFileQuestionAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    private CommonFacadeService<BidFileQuestion> commonFacadeService;
    private CommonFacadeService<SupplierAttachment> attachmentCommonFacadeService;

    private BidFileReleaseService bidFileReleaseService;
    private CommonFacadeService<SupplierExt> supplierExtCommonFacadeService;
    private MenuService menuService;
    private BidFileQuestionService  bidFileQuestionService;
    private BidFileQuestion biddingFileQue ;

    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public CommonFacadeService<BidFileQuestion> getCommonFacadeService() {
        return commonFacadeService;
    }

    public void setCommonFacadeService(
            CommonFacadeService<BidFileQuestion> commonFacadeService) {
        this.commonFacadeService = commonFacadeService;
    }

    public BidFileQuestionService getBidFileQuestionService() {
        return bidFileQuestionService;
    }

    public void setBidFileQuestionService(BidFileQuestionService bidFileQuestionService) {
        this.bidFileQuestionService = bidFileQuestionService;
    }
  
    
    public String saveBidQuestionDetail(){
    	  HttpServletRequest request = ServletActionContext.getRequest();
          User user = (User) request.getSession().getAttribute("user");
          biddingFileQue.setSupUUID(user.getUuid());
          biddingFileQue.setStatus("0");//0：未处理，1：已处理
    	commonFacadeService.save(biddingFileQue);
      return "success";
     }
    
    public String bidQuestionCheck(){
    	  HttpServletRequest request = ServletActionContext.getRequest();
    	  User user = (User) request.getSession().getAttribute("user");
          String uuid= request.getParameter("uuid");
          String type= request.getParameter("type");
          //BidFileQuestion bidFileQuestion = commonFacadeService.get(BidFileQuestion.class, uuid); 
          BidFileQuestion bidFileQuestion = commonFacadeService.getEntityByID(BidFileQuestion.class.getSimpleName(),uuid);
          SupplierAttachment feedBack= attachmentCommonFacadeService.getEntityByID(SupplierAttachment.class.getSimpleName(), bidFileQuestion.getFeedBackFileUUID());
          SupplierAttachment questionFile= attachmentCommonFacadeService.getEntityByID(SupplierAttachment.class.getSimpleName(), bidFileQuestion.getQuestionFileUUID());
          request.setAttribute("bidFileQuestion", bidFileQuestion);
          request.setAttribute("feedBack", feedBack);
          request.setAttribute("questionFile", questionFile);
          request.setAttribute("type", type);
          return "success";
    }
    /**
     * struts2的驱动模型
     * 对实体自动赋值
     * @return
     */
    //批量保存或更新类型
    public void saveUpdateBidFileQue() throws Exception{

        HttpServletRequest request = ServletActionContext.getRequest();
        String data= request.getParameter("submitData");

        JSONObject jsonObject=JSONObject.fromObject(data);
        BidFileQuestion bidFileQuestion=(BidFileQuestion)JSONObject.toBean(jsonObject, BidFileQuestion.class);
        if(StringUtil.isEmpty(bidFileQuestion.getQuestionFileUUID())){
            bidFileQuestion.setQuestionFileUUID(null);
        }

        if(StringUtil.isEmpty(bidFileQuestion.getFeedBackFileUUID())){
            bidFileQuestion.setFeedBackFileUUID(null);
        }

        if(!StringUtil.isEmpty(bidFileQuestion.getUuid())){//更新
            commonFacadeService.update(bidFileQuestion);
        }else{//保存
            commonFacadeService.save(bidFileQuestion);
        }
    }

    //招标文件澄清 根据bidFileUUID 查询
    public String getQuestionListByBidId(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String bidFileUUID =request.getParameter("bidFileUUID");

       //筛选
        String questionIds = request.getParameter("questionIds");
        List<BidFileQuestion> list= commonFacadeService.getListByProperty(BidFileQuestion.class.getSimpleName(),"bidFileUUID",bidFileUUID);
        List<BidFileQuestion> result = new ArrayList<>();
        if(!StringUtil.isEmpty(questionIds)){
            for(BidFileQuestion bidFileQuestion :list){
                if(questionIds.contains(bidFileQuestion.getUuid())){
                    result.add(bidFileQuestion);
                }
            }
        }else{
            result.addAll(list);
        }
        for(BidFileQuestion bidFileQuestion:result){
            if(bidFileQuestion.getQuestionFileAttachment()!=null&& !StringUtil.isEmpty(bidFileQuestion.getQuestionFileAttachment().getFileName())){
                bidFileQuestion.setQuestionFileName(bidFileQuestion.getQuestionFileAttachment().getFileName());
            }
            if(bidFileQuestion.getFeedBackFileAttachment()!=null && !StringUtil.isEmpty(bidFileQuestion.getFeedBackFileAttachment().getFileName())){
                bidFileQuestion.setFeedBackFileName(bidFileQuestion.getFeedBackFileAttachment().getFileName());
            }
        }
        CommonUtil.toJsonStr(response, result,"questionFileAttachment","feedBackFileAttachment");
        return null;
    }


    // 供应商获取澄清反馈列表
    public String getFeedBackList(){
        HttpServletRequest request = ServletActionContext.getRequest();
        User user = (User) request.getSession().getAttribute("user");

        Page<BidFileQuestion> page =getPage();
        bidFileQuestionService.findPage(user.getUuid(),page);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", page.getTotalCount());
        map.put("data", page.getResult());
        CommonUtil.toJsonStr(response, map,"questionFileAttachment","feedBackFileAttachment");

        return null;
    }


    //供应商质疑澄清列表
    public String bidQuestionList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String bidFileUUID = request.getParameter("bidFileUUID");
        request.setAttribute("bidFileUUID", bidFileUUID);
        return ActionSupport.SUCCESS;
    }

    public String publicClarifyList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String bidFileUUID = request.getParameter("bidFileUUID");
        String questionIds = request.getParameter("questionIds");
        request.setAttribute("bidFileUUID", bidFileUUID);
        request.setAttribute("questionIds", questionIds);
        return ActionSupport.SUCCESS;
    }


    /**
     * 保存设备分项信息
     */
    public void saveFeedBacks() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String feedbacks = request.getParameter("feedbacks");

        try {

            JSONArray jsonArray=JSONArray.fromObject(feedbacks);
            List<BidFileQuestion> list =JSONArray.toList(jsonArray,BidFileQuestion.class);

            for(BidFileQuestion question:list){
                if(StringUtils.isBlank(question.getQuestionFileUUID())){
                    question.setQuestionFileUUID(null);
                }
                if(StringUtils.isBlank(question.getFeedBackFileUUID())){
                    question.setFeedBackFileUUID(null);
                }
                //公开澄清
                question.setIsPublic("1");
                commonFacadeService.update(question);
            }
            AjaxUtils.success();
        } catch (Exception e) {
            e.printStackTrace();
            AjaxUtils.error(AjaxCode.SERVER_ERROR, e.getMessage());
        }
    }

    public void getBidFileQuestion() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String bidFileUUID =request.getParameter("bidFileUUID");
        String supUUID =request.getParameter("supUUID");

        List<BidFileQuestion> list = bidFileQuestionService.search(bidFileUUID,supUUID);
        CommonUtil.toJsonStr(ServletActionContext.getResponse(),list);
    }
    //查询类型维护
    public String queryBidFileQue(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String orgIds = (String) request.getSession().getAttribute("orgIds");
        List<BidFileQuestion> list = commonFacadeService.loadList(BidFileQuestion.class.getSimpleName());
        CommonUtil.toJsonStr(ServletActionContext.getResponse(),list);
        return null;
    }


    public String showQuestionPage(){

        String uuid = request.getParameter("uuid");
        BidFileQuestion bidFileQuestion = commonFacadeService.getEntityByID(BidFileQuestion.class.getSimpleName(),uuid);
        request.setAttribute("bidFileUUID",bidFileQuestion.getBidFileUUID());
        request.setAttribute("bidCode",bidFileQuestion.getBidCode());
        request.setAttribute("bidName",bidFileQuestion.getBidName());
        request.setAttribute("supName",bidFileQuestion.getSupName());
        request.setAttribute("supUUID",bidFileQuestion.getSupUUID());
        request.setAttribute("version",bidFileQuestion.getVersion());
        request.setAttribute("remark",bidFileQuestion.getRemark());
        request.setAttribute("contact",bidFileQuestion.getContact());
        request.setAttribute("issueType",bidFileQuestion.getIssueType());
        request.setAttribute("questionFileUUID",bidFileQuestion.getQuestionFileUUID());
        request.setAttribute("feedBack",bidFileQuestion.getFeedBack());
        request.setAttribute("feedBackFileUUID",bidFileQuestion.getFeedBackFileUUID());

        String questionFileName = "";
        if(!StringUtil.isEmpty(bidFileQuestion.getQuestionFileUUID())){
            SupplierAttachment supplierAttachment = attachmentCommonFacadeService.getEntityByID(SupplierAttachment.class.getSimpleName(),bidFileQuestion.getQuestionFileUUID());
            if(supplierAttachment!=null&&supplierAttachment.getFileName()!=null)
                questionFileName = supplierAttachment.getFileName();
        }

        request.setAttribute("questionFileName",questionFileName);
        return ActionSupport.SUCCESS;

    }

    public String bidFileFeedBack() {
        HttpServletRequest request = ServletActionContext.getRequest();



        String uuid = request.getParameter("uuid");
        String bidFileUUID = request.getParameter("bidFileUUID");
        BidFileQuestion bidFileQuestion = commonFacadeService.getEntityByID(BidFileQuestion.class.getSimpleName(),uuid);
        request.setAttribute("bidFileUUID",bidFileUUID);
        request.setAttribute("bidCode",bidFileQuestion.getBidCode());
        request.setAttribute("bidName",bidFileQuestion.getBidName());
        request.setAttribute("supName",bidFileQuestion.getSupName());
        request.setAttribute("supUUID",bidFileQuestion.getSupUUID());
        request.setAttribute("version",bidFileQuestion.getVersion());
        request.setAttribute("remark",bidFileQuestion.getRemark());
        request.setAttribute("contact",bidFileQuestion.getContact());
        request.setAttribute("issueType",bidFileQuestion.getIssueType());
        request.setAttribute("questionFileUUID",bidFileQuestion.getQuestionFileUUID());
        String questionFileName = "";
        if(!StringUtil.isEmpty(bidFileQuestion.getQuestionFileUUID())){
            SupplierAttachment supplierAttachment = attachmentCommonFacadeService.getEntityByID(SupplierAttachment.class.getSimpleName(),bidFileQuestion.getQuestionFileUUID());
            if(supplierAttachment!=null&&supplierAttachment.getFileName()!=null)
               questionFileName = supplierAttachment.getFileName();
        }

        request.setAttribute("questionFileName",questionFileName);
        return ActionSupport.SUCCESS;
    }

    @Override
    public BidFileQuestion getModel() {
        if(CommonUtil.isNullOrEmpty(biddingFileQue)){
            biddingFileQue= new BidFileQuestion();
        }
        return biddingFileQue;
    }


    public BidFileReleaseService getBidFileReleaseService() {
        return bidFileReleaseService;
    }

    public void setBidFileReleaseService(BidFileReleaseService bidFileReleaseService) {
        this.bidFileReleaseService = bidFileReleaseService;
    }



    public CommonFacadeService<SupplierExt> getSupplierExtCommonFacadeService() {
        return supplierExtCommonFacadeService;
    }

    public void setSupplierExtCommonFacadeService(CommonFacadeService<SupplierExt> supplierExtCommonFacadeService) {
        this.supplierExtCommonFacadeService = supplierExtCommonFacadeService;
    }

    public CommonFacadeService<SupplierAttachment> getAttachmentCommonFacadeService() {
        return attachmentCommonFacadeService;
    }

    public void setAttachmentCommonFacadeService(CommonFacadeService<SupplierAttachment> attachmentCommonFacadeService) {
        this.attachmentCommonFacadeService = attachmentCommonFacadeService;
    }

	public BidFileQuestion getBiddingFileQue() {
		return biddingFileQue;
	}

	public void setBiddingFileQue(BidFileQuestion biddingFileQue) {
		this.biddingFileQue = biddingFileQue;
	}
    
}
