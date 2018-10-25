package htos.business.action.bid;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import htos.business.dto.InviteSupplier;
import htos.business.dto.SupplierMaterialParamInfo;
import htos.business.dto.SupplierMaterialPriceInfo;
import htos.business.entity.bid.*;
import htos.business.entity.biddingFile.BidFileQuestion;
import htos.business.entity.biddingFile.BiddingFileRelease;
import htos.business.entity.supplier.SupplierAttachment;
import htos.business.entity.supplier.SupplierSignup;
import htos.business.service.bid.BidEvaluationService;
import htos.business.service.bid.SupplierBidService;
import htos.business.utils.ajax.AjaxCode;
import htos.business.utils.ajax.AjaxUtils;
import htos.common.util.StringUtil;
import htos.coresys.entity.User;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.util.CommonUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierBidAction  extends ActionSupport implements ModelDriven<SupplierBid> {
	private SupplierBid supplierBid;
	private TechBidEval techBidEval;
	private SupplierBidService supplierBidService;
	private BiddingFileRelease biddingFileRelease;
	private CommonFacadeService<BiddingFileRelease> biddingFileReleaseCommonFacadeService;
	private CommonFacadeService<SupplierBid> supplierBidCommonFacadeService;
	private CommonFacadeService<SupplierAttachment>  supplierAttachmentCommonFacadeService;
	private CommonFacadeService<BidFileQuestion>  bidFileQuestionCommonFacadeService;
	private BidEvaluationService bidEvaluationService;
	
	public void rankBidOffer(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String biddingFileId = request.getParameter("uuid");
		JSONObject json=new JSONObject();
		 List<SupplierBid> list = supplierBidCommonFacadeService.getListByLikeProperty(SupplierBid.class.getSimpleName(), "biddingFileUuid", biddingFileId);
		for(SupplierBid supBid:list){	
			//if(!StringUtils.isBlank(supBid.getTechThirdPrice())){
			if(supBid.getInvitationTimes().intValue()>=1){
				if(user.getUuid().equals(supBid.getCreateUuid())){
				   json.put("success", "success");
				   CommonUtil.toJsonStr(ServletActionContext.getResponse(),json);
				}
			}
		}
		
	}

	public String showBidOffer() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String biddingFileId = request.getParameter("uuid");
		String times = request.getParameter("times");
		BiddingFileRelease biddingFileRelease = biddingFileReleaseCommonFacadeService.getEntityByID(BiddingFileRelease.class.getSimpleName(), biddingFileId);
		request.setAttribute("biddingFileRelease", biddingFileRelease);
		request.setAttribute("times",times!=null?times:1);
		String userId = request.getParameter("userId");    // 指定用户
		if (!StringUtils.isBlank(userId)) {
			supplierBid = supplierBidService.findBy(userId, biddingFileId);
		} else {
			User user = (User) session.getAttribute("user");
			userId=user.getUuid();
			supplierBid = supplierBidService.findBy(user.getUuid(), biddingFileId);
		}
		if(supplierBid == null || supplierBid.getInvitationTimes() == null)
		    request.setAttribute("invitationTimes", 0);
		//获取报名信息
		SupplierSignup supplierSignup = supplierBidService.findSignupInfo(biddingFileRelease.getBiddingBulletin().getUuid(), userId);
		request.setAttribute("supplierSignup",supplierSignup);
		String planId = supplierBidService.findPlanId(biddingFileId);
		request.setAttribute("planId", planId);
		if(supplierBid!=null){
			if(StringUtils.isNotBlank(supplierBid.getTechnicalFileUuid())){
				SupplierAttachment file = supplierAttachmentCommonFacadeService.getEntityByID(SupplierAttachment.class.getSimpleName(), supplierBid.getTechnicalFileUuid());
				request.setAttribute("techFileName",file!=null?file.getFileName():"");
			}
			if(StringUtils.isNotBlank(supplierBid.getBusinessFileUuid())){
				SupplierAttachment file = supplierAttachmentCommonFacadeService.getEntityByID(SupplierAttachment.class.getSimpleName(), supplierBid.getBusinessFileUuid());
				request.setAttribute("businessFileName",file!=null?file.getFileName():"");
			}
			if(StringUtils.isNotBlank(supplierBid.getPriceFileUuid())){
				SupplierAttachment file = supplierAttachmentCommonFacadeService.getEntityByID(SupplierAttachment.class.getSimpleName(), supplierBid.getPriceFileUuid());
				request.setAttribute("priceFileName",file!=null?file.getFileName():"");
			}
			if(StringUtils.isNotBlank(supplierBid.getClarifyFileUuid())){
				SupplierAttachment file = supplierAttachmentCommonFacadeService.getEntityByID(SupplierAttachment.class.getSimpleName(), supplierBid.getClarifyFileUuid());
				request.setAttribute("clarifyFileName",file!=null?file.getFileName():"");
			}
			if(StringUtils.isNotBlank(supplierBid.getCompeteFileUuid())){
				SupplierAttachment file = supplierAttachmentCommonFacadeService.getEntityByID(SupplierAttachment.class.getSimpleName(), supplierBid.getCompeteFileUuid());
				request.setAttribute("competeFileName",file!=null?file.getFileName():"");
			}
		}

		// 邀请竞价
        if(supplierBid != null) {
            Invitation invitation = supplierBidService.getInvitation(supplierBid.getUuid());
            request.setAttribute("invitation", invitation);
        }

		return ActionSupport.SUCCESS;
	}

	public String bidQuestion() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String uuid = request.getParameter("uuid");
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		String fileName = request.getParameter("fileName");
		String version = request.getParameter("version");

		request.setAttribute("uuid", uuid);
		request.setAttribute("name", name);
		request.setAttribute("code", code);
		request.setAttribute("fileName", fileName);
		request.setAttribute("version", version);

		return ActionSupport.SUCCESS;
	}
    
	public void bidQuestionList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String uuid =request.getParameter("uuid");
		List<BidFileQuestion> questionList = supplierBidService.findBidQuestionList(uuid, user.getUuid());
		CommonUtil.toJsonStrData(ServletActionContext.getResponse(), questionList);
	}
	
	public String bidQuestionDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		request.setAttribute("userType", user.getUserType());
		String uuid = request.getParameter("uuid");
		String fileName = request.getParameter("fileName");
		String code = request.getParameter("code");
		String version = request.getParameter("version");
		BidFileQuestion bidFileQuestion=new BidFileQuestion();
		bidFileQuestion.setBidCode(code);
		bidFileQuestion.setBidName(fileName);
		bidFileQuestion.setVersion(version);
		bidFileQuestion.setSupName(user.getUserName());
		bidFileQuestion.setContact(user.getUserTelephone());
		//BidFileQuestion bidFileQuestion = bidFileQuestionCommonFacadeService.getEntityByProperty("BidFileQuestion", "bidFileUUID", uuid);
		request.setAttribute("bidFileQuestion", bidFileQuestion);
		return ActionSupport.SUCCESS;
	}
	
//	public void saveBidQuestion(){
//		HttpServletRequest request = ServletActionContext.getRequest();
//		HttpSession session = request.getSession();
//		User user = (User) session.getAttribute("user");
//		String uuid = request.getParameter("uuid");
//		String code = request.getParameter("code");
//		String name = request.getParameter("name");
//		String version = request.getParameter("version");
//		String contact = request.getParameter("contact");
//		String remark = request.getParameter("remark");
//
//		BidFileQuestion bidFileQuestion = new BidFileQuestion();
//		bidFileQuestion.setBidFileUUID(uuid);
//		bidFileQuestion.setBidCode(code);
//		bidFileQuestion.setBidName(name);
//		bidFileQuestion.setVersion(version);
//		bidFileQuestion.setContact(contact);
//		bidFileQuestion.setRemark(remark);
//		bidFileQuestion.setSupName(user.getUserName());
//		bidFileQuestion.setSupUUID(user.getUuid());
//
//
//		if(supplierBid == null) {
//			AjaxUtils.error(AjaxCode.SERVER_ERROR, "没有投标不可撤回!");
//			return ;
//		}
//		supplierBid.setDelFlag("0");
//		supplierBidCommonFacadeService.update(supplierBid);
//		AjaxUtils.success();
//	}


	public void inviteSupplierList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String bidFileId = request.getParameter("bidFileId");
		String type = request.getParameter("type");
		List<InviteSupplier> list = supplierBidService.findInviteSupplier(bidFileId,type);
		techBidEval = bidEvaluationService.findByBidFile(bidFileId);
		//邀请竞价选择可行供应商
		if(type.equals("2")){
			List<InviteSupplier> supList=new ArrayList<>();
			for(InviteSupplier sup: list){	
			  SupplierScore supplierScore = supplierBidService.getSupplierScore(sup.getUuid(),techBidEval.getUuid());
			  if(supplierScore.getIsFeasible().equals("可行")){
				  supList.add(sup);
			  }
			}
			CommonUtil.toJsonStr(ServletActionContext.getResponse(),supList);
		}
		//邀请澄清
		if(type.equals("1")){
			CommonUtil.toJsonStr(ServletActionContext.getResponse(),supplierBidService.findInviteSupplier(bidFileId,type));
		}
	}

	public void findSupplierBidFiles(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String supplierBidId = request.getParameter("uuid");
		SupplierBid supplierBid = supplierBidCommonFacadeService.getEntityByID(SupplierBid.class.getSimpleName(), supplierBidId);
		List<Map<String,String>> list = new ArrayList<>();
		if(StringUtils.isNotBlank(supplierBid.getTechnicalFileUuid())){
			Map<String,String> map = new HashMap<>(2);
			map.put("fileId",supplierBid.getTechnicalFileUuid());
			map.put("fileName","技术标");
			list.add(map);
		}
		if(StringUtils.isNotBlank(supplierBid.getBusinessFileUuid())){
			Map<String,String> map = new HashMap<>(2);
			map.put("fileId",supplierBid.getBusinessFileUuid());
			map.put("fileName","商务标");
			list.add(map);
		}
		if(StringUtils.isNotBlank(supplierBid.getPriceFileUuid())){
			Map<String,String> map = new HashMap<>(2);
			map.put("fileId",supplierBid.getPriceFileUuid());
			map.put("fileName","价格标");
			list.add(map);
		}
		if(StringUtils.isNotBlank(supplierBid.getClarifyFileUuid())){
			Map<String,String> map = new HashMap<>(2);
			map.put("fileId",supplierBid.getClarifyFileUuid());
			map.put("fileName","澄清文件");
			list.add(map);
		}
		if(StringUtils.isNotBlank(supplierBid.getCompeteFileUuid())){
			Map<String,String> map = new HashMap<>(2);
			map.put("fileId",supplierBid.getCompeteFileUuid());
			map.put("fileName","竞价文件");
			list.add(map);
		}
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),list);
	}

	public String showSupplierBidInfo(){
		//获取供应商信息
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String userId = request.getParameter("userId");	// 指定用户
		if(StringUtils.isBlank(userId)) {
			User user = (User) session.getAttribute("user");
			String biddingFileId = request.getParameter("biddingFileId");
			biddingFileRelease = biddingFileReleaseCommonFacadeService.getEntityByID(BiddingFileRelease.class.getSimpleName(), biddingFileId);
			supplierBid = supplierBidService.findBy(user.getUuid(), biddingFileId);
		} else {
			User user = (User) session.getAttribute("user");
			String biddingFileId = request.getParameter("biddingFileId");
			biddingFileRelease = biddingFileReleaseCommonFacadeService.getEntityByID(BiddingFileRelease.class.getSimpleName(), biddingFileId);
			supplierBid = supplierBidService.findBy(userId, biddingFileId);
		}
		String role = request.getParameter("role");
		request.setAttribute("role", role);
		return "success";
	}

	public void recallBid(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String uuid = request.getParameter("uuid");
		supplierBid = supplierBidService.findBy(user.getUuid(), uuid);
		if(supplierBid == null) {
			AjaxUtils.error(AjaxCode.SERVER_ERROR, "没有投标不可撤回!");
			return ;
		}
		BiddingFileRelease bidFile = biddingFileReleaseCommonFacadeService.getEntityByID(BiddingFileRelease.class.getSimpleName(), supplierBid.getBiddingFileUuid());
		if(bidFile.getStatus()>1){
			AjaxUtils.error(AjaxCode.SERVER_ERROR, "已过撤回阶段，不可撤回!");
			return ;
		}
		supplierBidService.deleteSupplierBid(supplierBid.getUuid());
		/*supplierBid.setDelFlag("0");
		supplierBidCommonFacadeService.update(supplierBid);*/
		AjaxUtils.success();
	}

	public void loadMaterialPrice(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String deviceType = request.getParameter("deviceType");
		String purchaseId = request.getParameter("purchaseId");
		String supplierBidUuid = request.getParameter("supplierBidUuid");
		String needFinal = request.getParameter("needFinal");
		List<SupplierMaterialPriceInfo> materialInfos = supplierBidService.findMaterialInfo(deviceType, purchaseId, supplierBidUuid);
		for(SupplierMaterialPriceInfo info : materialInfos){
			if((needFinal==null||!needFinal.equals("0"))&&StringUtils.isBlank(info.getFinalUnitPrice())){
				info.setFinalUnitPrice(info.getThirdUnitPrice());
			}
		}
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),materialInfos);
	}

	public void loadSupplierMaterialParam(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String purchaseMaterialId = request.getParameter("purchaseMaterialId");
		String supplierBidUuid = request.getParameter("supplierBidUuid");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),supplierBidService.findSupplierMaterialParam(purchaseMaterialId,supplierBidUuid));
	}

	private void syncsPrice(SupplierBid supplierBid, String times) {
		if (times.equals("1")) {
			supplierBid.setDeviceSecondPrice(supplierBid.getDeviceFirstPrice());
			supplierBid.setDeviceThirdPrice(supplierBid.getDeviceFirstPrice());
			supplierBid.setTechSecondPrice(supplierBid.getTechFirstPrice());
			supplierBid.setTechThirdPrice(supplierBid.getTechFirstPrice());
			supplierBid.setTransportSecondPrice(supplierBid.getTransportFirstPrice());
			supplierBid.setTransportThirdPrice(supplierBid.getTransportFirstPrice());
			supplierBid.setSecondTotalPrice(supplierBid.getFirstTotalPrice());
			supplierBid.setThirdTotalPrice(supplierBid.getFirstTotalPrice());
		} else if (times.equals("2")) {
			supplierBid.setDeviceThirdPrice(supplierBid.getDeviceSecondPrice());
			supplierBid.setTechThirdPrice(supplierBid.getTechSecondPrice());
			supplierBid.setTransportThirdPrice(supplierBid.getTransportSecondPrice());
			supplierBid.setThirdTotalPrice(supplierBid.getSecondTotalPrice());
		}
	}

	private void syncsPrice(List<SupplierMaterialPriceInfo> devices,String times){
		for (SupplierMaterialPriceInfo device: devices) {
			if(times.equals("1")){
				device.setSecondUnitPrice(device.getFirstUnitPrice());
				device.setThirdUnitPrice(device.getFirstUnitPrice());
			}else if(times.equals("2")){
				device.setThirdUnitPrice(device.getSecondUnitPrice());
			}
		}
	}
	public String saveOrUpdate(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String uuid ;
		String times = request.getParameter("times");
		//supplierBid 同步价格
		syncsPrice(supplierBid,times);
		if(org.apache.commons.lang3.StringUtils.equals(times, "3"))
		    supplierBid.setInvitationTimes(supplierBid.getInvitationTimes() == null ? 1 : supplierBid.getInvitationTimes() + 1);
		if(StringUtils.isNotBlank(supplierBid.getUuid())){
			//修改
			uuid = supplierBid.getUuid();
			supplierBidCommonFacadeService.update(supplierBid);
		}else{
			//新增
			uuid = supplierBidCommonFacadeService.save(supplierBid);
		}
		List<SupplierMaterialPriceInfo> devices = JSONObject.parseArray(request.getParameter("devices"), SupplierMaterialPriceInfo.class);
		List<SupplierMaterialPriceInfo> baks = JSONObject.parseArray(request.getParameter("baks"), SupplierMaterialPriceInfo.class);
		List<SupplierMaterialPriceInfo> tools = JSONObject.parseArray(request.getParameter("tools"), SupplierMaterialPriceInfo.class);
		devices.addAll(baks);
		devices.addAll(tools);
		syncsPrice(devices,times);
		List<SupplierMaterialPrice> materialPrices = new ArrayList<>(devices.size());
		List<SupplierMaterialParam> supplierMaterialParams = new ArrayList<>();
		for (SupplierMaterialPriceInfo supplierMaterialPriceInfo: devices) {
			materialPrices.add(supplierMaterialPriceInfo.intoSupplierMaterialPrice(uuid));
			if(StringUtils.isNotBlank(supplierMaterialPriceInfo.getParamsJson())){
				List<SupplierMaterialParamInfo> supplierMaterialParamInfos = JSONObject.parseArray(supplierMaterialPriceInfo.getParamsJson(), SupplierMaterialParamInfo.class);
				for (SupplierMaterialParamInfo supplierMaterialParamInfo:supplierMaterialParamInfos) {
					supplierMaterialParams.add(supplierMaterialParamInfo.intoParam(uuid,supplierMaterialPriceInfo.getUuid()));
				}
			}
		}
		supplierBidService.saveOrUpdate(materialPrices);
		supplierBidService.saveOrUpdateMaterialParam(supplierMaterialParams);
		return "success";
	}

	public SupplierBidService getSupplierBidService() {
		return supplierBidService;
	}

	public void setSupplierBidService(SupplierBidService supplierBidService) {
		this.supplierBidService = supplierBidService;
	}

	public CommonFacadeService<BiddingFileRelease> getBiddingFileReleaseCommonFacadeService() {
		return biddingFileReleaseCommonFacadeService;
	}

	public void setBiddingFileReleaseCommonFacadeService(CommonFacadeService<BiddingFileRelease> biddingFileReleaseCommonFacadeService) {
		this.biddingFileReleaseCommonFacadeService = biddingFileReleaseCommonFacadeService;
	}

	public CommonFacadeService<SupplierBid> getSupplierBidCommonFacadeService() {
		return supplierBidCommonFacadeService;
	}

	public void setSupplierBidCommonFacadeService(CommonFacadeService<SupplierBid> supplierBidCommonFacadeService) {
		this.supplierBidCommonFacadeService = supplierBidCommonFacadeService;
	}

	public BiddingFileRelease getBiddingFileRelease() {
		return biddingFileRelease;
	}

	public void setBiddingFileRelease(BiddingFileRelease biddingFileRelease) {
		this.biddingFileRelease = biddingFileRelease;
	}

	public SupplierBid getSupplierBid() {
		return supplierBid;
	}

	public void setSupplierBid(SupplierBid supplierBid) {
		this.supplierBid = supplierBid;
	}

	public CommonFacadeService<SupplierAttachment> getSupplierAttachmentCommonFacadeService() {
		return supplierAttachmentCommonFacadeService;
	}

	public void setSupplierAttachmentCommonFacadeService(CommonFacadeService<SupplierAttachment> supplierAttachmentCommonFacadeService) {
		this.supplierAttachmentCommonFacadeService = supplierAttachmentCommonFacadeService;
	}

	@Override
	public SupplierBid getModel() {
		if(CommonUtil.isNullOrEmpty(supplierBid)){
			supplierBid= new SupplierBid();
		}
		return supplierBid;
	}

	public TechBidEval getTechBidEval() {
		return techBidEval;
	}

	public void setTechBidEval(TechBidEval techBidEval) {
		this.techBidEval = techBidEval;
	}

	public BidEvaluationService getBidEvaluationService() {
		return bidEvaluationService;
	}

	public void setBidEvaluationService(BidEvaluationService bidEvaluationService) {
		this.bidEvaluationService = bidEvaluationService;
	}

	public CommonFacadeService<BidFileQuestion> getBidFileQuestionCommonFacadeService() {
		return bidFileQuestionCommonFacadeService;
	}

	public void setBidFileQuestionCommonFacadeService(
			CommonFacadeService<BidFileQuestion> bidFileQuestionCommonFacadeService) {
		this.bidFileQuestionCommonFacadeService = bidFileQuestionCommonFacadeService;
	}
	
}
