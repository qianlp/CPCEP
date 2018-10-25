package htos.business.action.biddingFile;

import PluSoft.Utils.JSON;

import com.opensymphony.xwork2.ActionSupport;

import htos.business.entity.biddingFile.BiddingFileRelease;
import htos.business.entity.bulletin.BiddingBulletin;
import htos.business.entity.procurement.PurchasePlan;
import htos.business.entity.supplier.view.ViewAttachment;
import htos.business.service.bidFileRelease.BidFileReleaseService;
import htos.common.util.StringUtil;
import htos.coresys.action.BaseAction;
import htos.coresys.entity.Menu;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.MenuService;
import htos.coresys.util.CommonUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 招标文件发布（问题类型）
 * @author
 */

public class BiddingFileAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private CommonFacadeService<BiddingFileRelease> commonFacadeService;
	private CommonFacadeService bulletinFacadeService;
	private MenuService menuService;
	private BiddingFileRelease biddingFileRelease ;

	private BidFileReleaseService bidFileReleaseService;

	public void setBidFileReleaseService(BidFileReleaseService bidFileReleaseService) {
		this.bidFileReleaseService = bidFileReleaseService;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public CommonFacadeService<BiddingFileRelease> getCommonFacadeService() {
		return commonFacadeService;
	}

	public void setCommonFacadeService(
			CommonFacadeService<BiddingFileRelease> commonFacadeService) {
		this.commonFacadeService = commonFacadeService;
	}

	/**
	 * struts2的驱动模型
	 * 对实体自动赋值
	 * @return
	 */
	//批量保存或更新类型
	public String saveUpdateBidFile() throws Exception{
		if(!StringUtil.isEmpty(biddingFileRelease.getUuid())){//更新
			commonFacadeService.saveOrUpdate(biddingFileRelease);
		}else{//保存
			String menuId=ServletActionContext.getRequest().getParameter("menuId");
			biddingFileRelease.setUpdateBy(biddingFileRelease.getCreateBy());

			//Menu menu = menuService.findOneMenuById("entityClsName", biddingFileRelease.getClass().getSimpleName());
			BiddingBulletin bulletin = (BiddingBulletin) bulletinFacadeService.getEntityByID(BiddingBulletin.class.getSimpleName(),biddingFileRelease.getBulletinUuid());
			bulletin.setWfStatus("9");
			bulletinFacadeService.save(bulletin);
			commonFacadeService.save(biddingFileRelease, menuId);
		}
		return "success";
	}
	//查询类型维护
	public String queryBidFileData(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String orgIds = (String) request.getSession().getAttribute("orgIds");
		List<BiddingFileRelease> list = commonFacadeService.loadList(BiddingFileRelease.class.getSimpleName());
		CommonUtil.toJsonStr(ServletActionContext.getResponse(),list);
		return null;
	}


	@Override
	public BiddingFileRelease getModel() {
		if(CommonUtil.isNullOrEmpty(biddingFileRelease)){
			biddingFileRelease= new BiddingFileRelease();
		}
		return biddingFileRelease;
	}


	/**
	 * 分页加载
	 * @return
	 */
	public String loadDataForPage() {
		String pid = request.getParameter("pid");
		String search = request.getParameter("search");
		List searchList=null;
		if (!StringUtils.isEmpty(search)){
			searchList = (List) JSON.Decode(search);
		}
		CommonUtil.toJsonStr(response,bidFileReleaseService.loadDataForPage(pid,searchList, getpageInfo()));
		return null;
	}


	public String attachmentPage() {
		String uuid = request.getParameter("uuid");
		request.setAttribute("uuid", uuid);
		return ActionSupport.SUCCESS;
	}

	public void attachment() {
		String uuid = request.getParameter("uuid");
		BiddingFileRelease file = bidFileReleaseService.findById(uuid);
		List<ViewAttachment> attachments = new ArrayList<ViewAttachment>();
		if(StringUtils.isNotBlank(file.getTeachFile())) {
			ViewAttachment file1 = new ViewAttachment();
			file1.setFileName("技术标");
			file1.setId(file.getTeachFile());
			attachments.add(file1);
		}

		if(StringUtils.isNotBlank(file.getBusinessFile())) {
			ViewAttachment file2 = new ViewAttachment();
			file2.setFileName("商务标");
			file2.setId(file.getBusinessFile());
			attachments.add(file2);
		}

		if(StringUtils.isNotBlank(file.getOverallFile())) {
			ViewAttachment file3 = new ViewAttachment();
			file3.setFileName("价格标");
			file3.setId(file.getOverallFile());
			attachments.add(file3);
		}
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), attachments);
	}

	/**
	 * @return the bulletinFacadeService
	 */
	public CommonFacadeService getBulletinFacadeService() {
		return bulletinFacadeService;
	}

	/**
	 * @param bulletinFacadeService the bulletinFacadeService to set
	 */
	public void setBulletinFacadeService(CommonFacadeService bulletinFacadeService) {
		this.bulletinFacadeService = bulletinFacadeService;
	}

	
}
