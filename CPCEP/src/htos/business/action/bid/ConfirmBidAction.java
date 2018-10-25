package htos.business.action.bid;

import htos.business.dto.SupplierMaterialPriceInfo;
import htos.business.entity.bid.BiddingConfirm;
import htos.business.entity.bid.ConfirmMessage;
import htos.business.entity.bid.SupplierBid;
import htos.business.entity.bid.SupplierMaterialPrice;
import htos.business.entity.bid.view.ViewConfirmBid;
import htos.business.entity.biddingFile.BiddingFileRelease;
import htos.business.entity.supplier.view.Page;
import htos.business.service.bid.ConfirmBidService;
import htos.business.service.bid.SupplierBidService;
import htos.coresys.action.BaseAction;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.util.CommonUtil;
import htos.sysfmt.message.entity.MsgInfo;
import htos.sysfmt.message.service.MsgInfoService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 定标
 *
 * @author qinj
 * @date 2018-05-27 15:31
 **/
public class ConfirmBidAction extends BaseAction {
	private CommonFacadeService<SupplierBid> supplierBidCommonFacadeService;
	private CommonFacadeService<BiddingConfirm> biddingConfirmCommonFacadeService;
	private CommonFacadeService biddingFileReleaseCommonFacadeService;
	private MsgInfoService msgInfoService;

	/**
	 * @return the biddingFileReleaseCommonFacadeService
	 */
	public CommonFacadeService getBiddingFileReleaseCommonFacadeService() {
		return biddingFileReleaseCommonFacadeService;
	}

	/**
	 * @param biddingFileReleaseCommonFacadeService the biddingFileReleaseCommonFacadeService to set
	 */
	public void setBiddingFileReleaseCommonFacadeService(
			CommonFacadeService biddingFileReleaseCommonFacadeService) {
		this.biddingFileReleaseCommonFacadeService = biddingFileReleaseCommonFacadeService;
	}

	private SupplierBidService supplierBidService;

	public SupplierBidService getSupplierBidService() {
		return supplierBidService;
	}

	public void setSupplierBidService(SupplierBidService supplierBidService) {
		this.supplierBidService = supplierBidService;
	}

	public CommonFacadeService<BiddingConfirm> getBiddingConfirmCommonFacadeService() {
		return biddingConfirmCommonFacadeService;
	}

	public void setBiddingConfirmCommonFacadeService(CommonFacadeService<BiddingConfirm> biddingConfirmCommonFacadeService) {
		this.biddingConfirmCommonFacadeService = biddingConfirmCommonFacadeService;
	}

	public ConfirmBidService getConfirmBidService() {
		return confirmBidService;
	}

	public CommonFacadeService<SupplierBid> getSupplierBidCommonFacadeService() {
		return supplierBidCommonFacadeService;
	}

	public void setSupplierBidCommonFacadeService(CommonFacadeService<SupplierBid> supplierBidCommonFacadeService) {
		this.supplierBidCommonFacadeService = supplierBidCommonFacadeService;
	}
	//发送通知
	/*public void sendMessage(String biddingId,String supplierId,String message, String biddingBulletinCode, String biddingBulletinName,String biddingFileReleaseVersion,String confirmTime){
		String biddingId = request.getParameter("biddingId");
		String supplierId = request.getParameter("supplierId");
		String message = request.getParameter("message");
		String biddingBulletinCode = request.getParameter("biddingBulletinCode");
		String biddingBulletinName = request.getParameter("biddingBulletinName");
		String confirmTime = request.getParameter("confirmTime");
		String biddingFileReleaseVersion = request.getParameter("biddingFileReleaseVersion");
		ConfirmMessage conf =new ConfirmMessage(biddingBulletinCode,biddingBulletinName,confirmTime,biddingFileReleaseVersion,message);
		String uuid = biddingFileReleaseCommonFacadeService.save(conf);
		MsgInfo msg =new MsgInfo();
		msg.setDocId(uuid);
		msg.setMsgTitle(biddingBulletinName+"项目已中标，请了解中标信息");
		msg.setMsgType(1);
		msg.setCreateDate(new Date());
		msg.setStatus(1);
		msg.setUserId(supplierId);
		msg.setMenuId("402880e464d9854e0164dabb4453008f");
		msgInfoService.saveMsg(msg);
		
	}*/
	public void confirmListByPlanId(){
		String planId = ServletActionContext.getRequest().getParameter("planId");
		System.out.println(planId);
		List<BiddingFileRelease> bfrList=biddingFileReleaseCommonFacadeService.getHqlList(" from BiddingFileRelease where planId='"+planId+"' order by createDate desc");
        List<ViewConfirmBid> vcfList=new ArrayList();
        for(BiddingFileRelease b:bfrList){
        	ViewConfirmBid vfr=new ViewConfirmBid();
        	vfr.setName(b.getName());
        	vfr.setCode(b.getCode());
        	vfr.setVersion(b.getVersion());
        	vfr.setBidId(b.getUuid());
        	vfr.setBidStartTime(b.getBiddingBulletin().getBidStartTime());
        	vfr.setBidEndTime(b.getBiddingBulletin().getBidEndTime());
        	vfr.setBidOpenTime(b.getBiddingBulletin().getBidOpenTime());
        	
        	BiddingConfirm bc=(BiddingConfirm)biddingFileReleaseCommonFacadeService.getEntityByProperty("BiddingConfirm", "biddingFile", b.getUuid());
        	if(bc!=null){
        		vfr.setConfirmId(bc.getUuid());
        	}else{
        		vfr.setConfirmId("");
        	}
        	vcfList.add(vfr);
        }
        CommonUtil.toJsonStr(response, vcfList);
	}

	/**
	 * 定标列表
	 */
	public void confirmList() {
		// TODO
		String name = request.getParameter("name");
		Page<ViewConfirmBid> page = getPage();
		confirmBidService.findConfirmList(page, name);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("data", page.getResult());
		CommonUtil.toJsonStr(response, map);
	}

	/**
	 * 招标信息
	 */
	public String bidInfo() {
		// TODO
		String bidFileId = request.getParameter("bidFileId");
		ViewConfirmBid bid = confirmBidService.findBidInfo(bidFileId);
		request.setAttribute("view", bid);
		String planId = supplierBidService.findPlanId(bidFileId);
		request.setAttribute("planId", planId);
		return ActionSupport.SUCCESS;
	}

	/**
	 * 投标的供应商
	 */
	public void bidSupplier() {
		// TODO
		String bidFileId = request.getParameter("bidFileId");
		List<ViewConfirmBid> confirmBids= confirmBidService.findBidSupplier(bidFileId);
	/*	for(ViewConfirmBid view:confirmBids){
			if(StringUtils.isBlank(view.getFinalPrice())){
				view.setFinalPrice(view.getThirdTotalPrice());
			}
		}*/
		Iterator<ViewConfirmBid> iterator = confirmBids.iterator();
		while(iterator.hasNext()){
			ViewConfirmBid bid = iterator.next();
			if(StringUtils.isBlank(bid.getFinalPrice())){
				bid.setFinalPrice(bid.getThirdTotalPrice());
			}
			if(bid.getStatus().equals("不可行")){
				iterator.remove();
			}
		}
		Collections.sort(confirmBids,new Comparator<ViewConfirmBid>() {
		    @Override
			public int compare(ViewConfirmBid o1, ViewConfirmBid o2) {
				return new BigDecimal(StringUtils.isNotBlank(o1.getFinalPrice()) ? o1.getFinalPrice() : "0").compareTo(
						new BigDecimal(StringUtils.isNotBlank(o2.getFinalPrice()) ? o2.getFinalPrice() : "0")
				);
			}  
		});
//		SortList<ViewConfirmBid> sort = new SortList<ViewConfirmBid>();
//		 sort.Sort(confirmBids, "getFinalPrice", "acs");
		CommonUtil.toJsonStr(response,confirmBids);
	}

	/**
	 * 获取选中投标的信息
	 */
	public void loadSupplierBidInfo() {
		String supplierBidUuid = request.getParameter("uuid");
		SupplierBid supplierBid = supplierBidCommonFacadeService.get(SupplierBid.class, supplierBidUuid);
		if(StringUtils.isBlank(supplierBid.getTechFinalPrice())){
			supplierBid.setTechFinalPrice(supplierBid.getTechThirdPrice());
		}
		if(StringUtils.isBlank(supplierBid.getTransportFinalPrice())){
			supplierBid.setTransportFinalPrice(supplierBid.getTransportThirdPrice());
		}
		if(StringUtils.isBlank(supplierBid.getFinalTotalPrice())){
			supplierBid.setFinalTotalPrice(supplierBid.getThirdTotalPrice());
		}
		CommonUtil.toJsonStr(response,supplierBid, "supplierMaterialPrices");
	}

	/**
	 * 设备分项
	 */
	public void loadMaterialDetailPrice() {
		String supplierBidUuid = request.getParameter("supplierBidUuid");
		SupplierBid supplierBid = supplierBidCommonFacadeService.get(SupplierBid.class, supplierBidUuid);
		CommonUtil.toJsonStr(response,confirmBidService.findMaterialDetail(supplierBidUuid,supplierBid.getCreateUuid()));
	}

	public String saveOrUpdate(){
		String confirmTime = request.getParameter("confirmTime");
		String confirmId = request.getParameter("confirmId");
		String bidId = request.getParameter("bidId");
		String biddingId = request.getParameter("biddingId");
		String supplierId = request.getParameter("supplierId");
		String message = request.getParameter("message");
		String finalTotalPrice = request.getParameter("finalTotalPrice");
		String transportFinalPrice = request.getParameter("transportFinalPrice");
		String techFinalPrice = request.getParameter("techFinalPrice");
		String biddingBulletinCode = request.getParameter("biddingBulletinCode");
		String biddingBulletinName = request.getParameter("biddingBulletinName");
		String biddingFileReleaseVersion = request.getParameter("biddingFileReleaseVersion");
		short status =4;
		ConfirmMessage conf =new ConfirmMessage(biddingBulletinCode,biddingBulletinName,confirmTime,biddingFileReleaseVersion,message,supplierId);
		String uuid = biddingFileReleaseCommonFacadeService.save(conf);
		MsgInfo msg =new MsgInfo();
		msg.setDocId(uuid);
		msg.setMsgTitle(biddingBulletinName+"项目已中标，请了解中标信息");
		msg.setMsgType(1);
		msg.setCreateDate(new Date());
		msg.setStatus(1);
		msg.setUserId(supplierId);
		msg.setMenuId("402880e464d9854e0164dabb4453008f");
		msgInfoService.saveMsg(msg);
		if(StringUtils.isBlank(confirmId)){
			//添加
			BiddingConfirm biddingConfirm = new BiddingConfirm();
			biddingConfirm.setBiddingFile(bidId);
			biddingConfirm.setBiddingId(biddingId);
			biddingConfirm.setConfirmTime(new Timestamp(System.currentTimeMillis()));
			biddingConfirm.setMessage(message);
			biddingConfirm.setSupplierId(supplierId);
			biddingConfirmCommonFacadeService.save(biddingConfirm);

			BiddingFileRelease biddingFileRelease =(BiddingFileRelease) biddingFileReleaseCommonFacadeService.getEntityByID(BiddingFileRelease.class.getSimpleName(),biddingConfirm.getBiddingFile());

			biddingFileRelease.setStatus(status);
			biddingFileReleaseCommonFacadeService.update(biddingFileRelease);
		}else{
			//修改
			BiddingConfirm biddingConfirm = biddingConfirmCommonFacadeService.getEntityByID(BiddingConfirm.class.getSimpleName(), confirmId);
			biddingConfirm.setBiddingId(biddingId);
			biddingConfirm.setSupplierId(supplierId);
			biddingConfirm.setMessage(message);
			biddingConfirmCommonFacadeService.update(biddingConfirm);

			BiddingFileRelease biddingFileRelease =(BiddingFileRelease) biddingFileReleaseCommonFacadeService.getEntityByID(BiddingFileRelease.class.getSimpleName(),biddingConfirm.getBiddingFile());
			biddingFileRelease.setStatus(status);
			biddingFileReleaseCommonFacadeService.update(biddingFileRelease);
		}
		//更新投标信息
		SupplierBid supplierBid = supplierBidCommonFacadeService.getEntityByID(SupplierBid.class.getSimpleName(), biddingId);
		supplierBid.setFinalTotalPrice(finalTotalPrice);
		supplierBid.setTechFinalPrice(techFinalPrice);
		//supplierBid.setTechFinalPrice(transportFinalPrice);
		supplierBid.setTransportFinalPrice(transportFinalPrice);
		supplierBidCommonFacadeService.update(supplierBid);
		//更新设备最终定价
		List<SupplierMaterialPriceInfo> devices = JSONObject.parseArray( request.getParameter("devices"), SupplierMaterialPriceInfo.class);
		List<SupplierMaterialPriceInfo> baks = JSONObject.parseArray(request.getParameter("baks"), SupplierMaterialPriceInfo.class);
		List<SupplierMaterialPriceInfo> tools = JSONObject.parseArray(request.getParameter("tools"), SupplierMaterialPriceInfo.class);
		devices.addAll(baks);
		devices.addAll(tools);
		List<SupplierMaterialPrice> materialPrices = new ArrayList<>(devices.size());
		for (SupplierMaterialPriceInfo supplierMaterialPriceInfo: devices) {
			materialPrices.add(supplierMaterialPriceInfo.intoSupplierMaterialPrice(biddingId));
		}
		supplierBidService.saveOrUpdate(materialPrices);
		//更新设备分项最终价格
		/*List<ViewMaterialDetail> details = JSONObject.parseArray(request.getParameter("details"), ViewMaterialDetail.class);
		confirmBidService.updateMaterialAttrFinalPrice(details);*/
		return ActionSupport.SUCCESS;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//
	//

	private ConfirmBidService confirmBidService;

	public void setConfirmBidService(ConfirmBidService confirmBidService) {
		this.confirmBidService = confirmBidService;
	}

	public MsgInfoService getMsgInfoService() {
		return msgInfoService;
	}

	public void setMsgInfoService(MsgInfoService msgInfoService) {
		this.msgInfoService = msgInfoService;
	}
   
}
