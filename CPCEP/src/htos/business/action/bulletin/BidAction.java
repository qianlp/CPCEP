package htos.business.action.bulletin;

import htos.business.entity.bid.Invitation;
import htos.business.entity.bid.SupplierBid;
import htos.business.entity.biddingFile.BidFileQuestion;
import htos.business.entity.biddingFile.BiddingFileRelease;
import htos.business.entity.bulletin.BiddingBulletin;
import htos.business.entity.bulletin.view.ViewBiddingBulletin;
import htos.business.entity.procurement.PurchasePlan;
import htos.business.entity.supplier.SupplierExt;
import htos.business.entity.supplier.view.Page;
import htos.business.entity.supplier.view.ViewUserSupplier;
import htos.business.service.bidFileRelease.BidFileReleaseService;
import htos.business.service.bulletin.BiddingBulletinService;
import htos.business.service.supplier.SupplierService;
import htos.business.utils.ajax.AjaxCode;
import htos.business.utils.ajax.AjaxUtils;
import htos.coresys.action.BaseAction;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.util.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import PluSoft.Utils.JSON;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 开标管理
 *
 * @author qinj
 * @date 2018-03-27 22:48
 **/
public class BidAction extends BaseAction {

    private BiddingBulletinService service;
    private SupplierService supplierService;
    private BidFileReleaseService bidFileReleaseService;
    private CommonFacadeService commonFacadeService;
    private CommonFacadeService<BidFileQuestion> questionCommonFacadeService;
    private CommonFacadeService<SupplierBid> supplierBidCommonFacadeService;
    private CommonFacadeService<Invitation> invitationCommonFacadeService;
    private CommonFacadeService<SupplierExt> supExtCommonFacadeService;

    /**
     * 开标列表
     */
    public String listView() {
        User user = (User) request.getSession().getAttribute("user");
        String bidName = null;
        if (request.getParameter("search") != null) {
            List<Map> searchList = (List<Map>) JSON.Decode(request.getParameter("search"));
            if (!searchList.isEmpty()) {
                bidName = (String) searchList.get(0).get("value");
            }
        }

        Page<ViewBiddingBulletin> page = getPage();
        service.findPage(page, bidName, user);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", page.getTotalCount());
        map.put("data", page.getResult());
        CommonUtil.toJsonStr(response, map);

        return null;
    }

    public void bidClarifyListByPlanId(){
    	String planId=request.getParameter("planId");
    	PurchasePlan plan = (PurchasePlan) commonFacadeService.getEntityByID(PurchasePlan.class.getSimpleName(), planId);
        List<BiddingFileRelease> fList=commonFacadeService.getHqlList(" from BiddingFileRelease where planId='"+planId+"' order by createDate desc");
        List<ViewBiddingBulletin> bList=new ArrayList();
        for(BiddingFileRelease b:fList){
        	ViewBiddingBulletin v=new ViewBiddingBulletin();
        	v.setUuid(b.getUuid());
        	v.setProjCode(plan.getProjectCode());
        	v.setProjName(plan.getProjectName());
            v.setProjectId(plan.getProjectUuid());
            v.setName(b.getName());
            v.setCode(b.getCode());
            
            List<BidFileQuestion> questions = questionCommonFacadeService.getHqlList(" from BidFileQuestion  where  bidFileUUID ='"+v.getUuid()+"'  and  feedBack is  null");
            if(questions !=null&&questions.size()>0){
                v.setClarifyStatus(1);
            }
            bList.add(v);
        }
        CommonUtil.toJsonStr(response, bList);
    }
    /**
     * 招标文件澄清管理
     */
    public String bidClarifyList() {
        User user = (User) request.getSession().getAttribute("user");
        String bidName = null;
        if (request.getParameter("search") != null) {
            List<Map> searchList = (List<Map>) JSON.Decode(request.getParameter("search"));
            if (!searchList.isEmpty()) {
                bidName = (String) searchList.get(0).get("value");
            }
        }

        Page<ViewBiddingBulletin> page = getPage();
        service.findPage(page, bidName, user);
        Map<String, Object> map = new HashMap<String, Object>();
        for (ViewBiddingBulletin bulletin : page.getResult()) {
            PurchasePlan plan = (PurchasePlan) commonFacadeService.getEntityByID(PurchasePlan.class.getSimpleName(), bulletin.getPurchasePlanUuid());
            if (plan != null) {
                bulletin.setProjCode(plan.getProjectCode() == null ? "" : plan.getProjectCode());
                bulletin.setProjName(plan.getProjectName() == null ? "" : plan.getProjectName());
                bulletin.setProjectId(plan.getProjectUuid());
            }

            List<BidFileQuestion> questions = questionCommonFacadeService.getHqlList(" from BidFileQuestion  where  bidFileUUID ='"+bulletin.getUuid()+"'  and  feedBack is  null");
            if(questions !=null&&questions.size()>0){
                bulletin.setClarifyStatus(1);
            }

        }

        map.put("total", page.getTotalCount());
        map.put("data", page.getResult());
        CommonUtil.toJsonStr(response, map);

        return null;
    }

    /**
     * 投标的供应商列表页面
     *
     * @return
     */
    public String bidSuppliersPage() {
        // TODO 没有招标文件
        String uuid = this.request.getParameter("uuid");
        request.setAttribute("uuid", uuid);
        String name = this.request.getParameter("name");
        request.setAttribute("name", name);

        String code = this.request.getParameter("code");
        request.setAttribute("code", code);

        String version = this.request.getParameter("version");
        request.setAttribute("version", version);
        BiddingFileRelease file = bidFileReleaseService.findByBid(uuid);
       // SupplierBid entityByProperty = supplierBidCommonFacadeService.getEntityByProperty("SupplierBid", "biddingFileUuid", file.getUuid());
        List<SupplierBid> list = supplierBidCommonFacadeService.getListByProperty("SupplierBid", "biddingFileUuid", uuid);
        for(SupplierBid sup:list){
        	Invitation invitation = invitationCommonFacadeService.getEntityByProperty("Invitation", "supplierBidUuid", sup.getUuid());
        	if(invitation!=null){
        	  if(invitation.getType()==1){
		           request.setAttribute("endDate", invitation.getEndDate());	
		           break;
        	  }
         	}else{
         		continue;
         	}
        }
        request.setAttribute("fileId", file == null ? "" : file.getUuid());
        return ActionSupport.SUCCESS;
    }
    
    /**
     * 参与澄清或竞价的供应商及信息
     */
    public void bidClarifySupList(){
    	String bidFileId = this.request.getParameter("bidFileId");
    	String type = this.request.getParameter("type");
    	List<Invitation> supList =new ArrayList<>();
    	List<SupplierBid> list = supplierBidCommonFacadeService.getListByProperty("SupplierBid","biddingFileUuid", bidFileId);
    	for(SupplierBid bid:list){
    		String hql="from Invitation a where a.supplierBidUuid='"+bid.getUuid()+"' and a.type='"+type+"'";
    		//List<Invitation> inList = invitationCommonFacadeService.getHqlList(hql);
    		Invitation invitation = invitationCommonFacadeService.getHqlObject(hql);
    		if(invitation!=null){
    		SupplierExt supplierExt = supExtCommonFacadeService.getEntityByProperty("SupplierExt", "account", bid.getCreateUuid());
    		invitation.setSupplierName(supplierExt.getName());
    		supList.add(invitation);
    		}
    	}
        CommonUtil.toJsonStr(response, supList,"deliveryDate");
    }
    /**
     * 参与投标的供应商
     */
    public void bidSuppliers() {
        String uuid = this.request.getParameter("uuid");//bidFileId
        String supplierName = request.getParameter("name");
        String hasBid = request.getParameter("hasBid");
        Page<ViewBiddingBulletin> page = getPage();
        service.findSupplier(page, uuid, supplierName, hasBid);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", page.getTotalCount());
        map.put("data", page.getResult());
        CommonUtil.toJsonStr(response, map);
    }

    /**
     * 开标页面
     *
     * @return
     */
    public String openPage() {
        String uuid = this.request.getParameter("uuid");
        BiddingFileRelease biddingFile = bidFileReleaseService.findById(uuid);
        request.setAttribute("biddingFile", biddingFile);
        return ActionSupport.SUCCESS;
    }

    /**
     * 开标
     */
    public void open() {
        String uuid = this.request.getParameter("uuid");//bidFileId
        try {
            bidFileReleaseService.open(uuid);
            AjaxUtils.success();
        } catch (Exception e) {
            AjaxUtils.error(AjaxCode.SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 邀请招标页面
     *
     * @return
     */
    public String invitePage() {
        String bidId = this.request.getParameter("uuid");
        request.setAttribute("uuid", bidId);
        return ActionSupport.SUCCESS;
    }

    /**
     * 邀请招标form
     *
     * @return
     */
    public String inviteForm() {
        String bidId = this.request.getParameter("bidId");
        request.setAttribute("bidId", bidId);
        String supplierId = this.request.getParameter("supplierId");
        request.setAttribute("supplierId", supplierId);
        return ActionSupport.SUCCESS;
    }

    /**
     * 邀请竞价
     */
    public void invite() {
        // TODO
        String supplierBidUuids = this.request.getParameter("supplierBidUuids");
        try {
            service.invite(supplierBidUuids.split(","), model);
            AjaxUtils.success();
        } catch (RuntimeException e) {
            e.printStackTrace();
            AjaxUtils.error(AjaxCode.SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 获取我的受邀招标
     */
    public void findMyInvite() {
        User user = (User) request.getSession().getAttribute("user");
//		SupplierExt supplierExt = supplierService.findByUser(user.getUuid());
        htos.business.entity.supplier.view.Page<Invitation> page = getPage();
        service.findMyInvite(page, user.getUuid());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", page.getTotalCount());
        map.put("data", page.getResult());
        CommonUtil.toJsonStr(response, map);
    }
    /**
     *获取邀请竞价的招标办发布的信息
     */
    public String findInvitation(){
    	  String id = this.request.getParameter("id");
    	 Invitation invitation = service.findInvitation(id);
    	 request.setAttribute("invitation", invitation);
    	 return ActionSupport.SUCCESS;
    }

    /**
     * 邀请招标供应商名单
     */
    public void findInvitationSupplier() {
        String bidId = this.request.getParameter("uuid");
        htos.business.entity.supplier.view.Page<ViewUserSupplier> page = getPage();
        service.findInvitationSupplier(page, bidId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", page.getTotalCount());
        map.put("data", page.getResult());
        CommonUtil.toJsonStr(response, map);
    }

    /**
     * 报价
     */
    public void quotePrice() {
        String invitationId = request.getParameter("invitationId");
        String price = request.getParameter("price");
        try {
            service.saveQuotePrice(invitationId, Float.parseFloat(price));
            AjaxUtils.success();
        } catch (RuntimeException e) {
            AjaxUtils.error(AjaxCode.SERVER_ERROR, e.getMessage());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //
    //

    private BiddingBulletin model;

    @Override
    public BiddingBulletin getModel() {
        if (model == null)
            model = new BiddingBulletin();
        return model;
    }

    public void setService(BiddingBulletinService service) {
        this.service = service;
    }

    public void setSupplierService(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    public void setBidFileReleaseService(BidFileReleaseService bidFileReleaseService) {
        this.bidFileReleaseService = bidFileReleaseService;
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

	public CommonFacadeService<BidFileQuestion> getQuestionCommonFacadeService() {
        return questionCommonFacadeService;
    }

    public void setQuestionCommonFacadeService(CommonFacadeService<BidFileQuestion> questionCommonFacadeService) {
        this.questionCommonFacadeService = questionCommonFacadeService;
    }

	public CommonFacadeService<SupplierBid> getSupplierBidCommonFacadeService() {
		return supplierBidCommonFacadeService;
	}

	public void setSupplierBidCommonFacadeService(
			CommonFacadeService<SupplierBid> supplierBidCommonFacadeService) {
		this.supplierBidCommonFacadeService = supplierBidCommonFacadeService;
	}

	public CommonFacadeService<Invitation> getInvitationCommonFacadeService() {
		return invitationCommonFacadeService;
	}

	public void setInvitationCommonFacadeService(
			CommonFacadeService<Invitation> invitationCommonFacadeService) {
		this.invitationCommonFacadeService = invitationCommonFacadeService;
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public BidFileReleaseService getBidFileReleaseService() {
		return bidFileReleaseService;
	}

	public CommonFacadeService<SupplierExt> getSupExtCommonFacadeService() {
		return supExtCommonFacadeService;
	}

	public void setSupExtCommonFacadeService(
			CommonFacadeService<SupplierExt> supExtCommonFacadeService) {
		this.supExtCommonFacadeService = supExtCommonFacadeService;
	}


    
}
