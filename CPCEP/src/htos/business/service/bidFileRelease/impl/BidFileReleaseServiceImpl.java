package htos.business.service.bidFileRelease.impl;

import htos.business.dao.bid.SupplierBidDao;
import htos.business.dao.bidFileRelease.BidFileReleaseDao;
import htos.business.dto.BidFileWithProject;
import htos.business.dto.PurchaseMaterialParamInfo;
import htos.business.dto.SupplierMaterialParamInfo;
import htos.business.entity.biddingFile.BiddingFileRelease;
import htos.business.entity.procurement.PurchaseMaterial;
import htos.business.entity.procurement.PurchaseMaterialParam;
import htos.business.service.bidFileRelease.BidFileReleaseService;
import htos.business.utils.BusinessConstants;
import htos.common.entity.PageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BidFileReleaseServiceImpl implements BidFileReleaseService {
	private BidFileReleaseDao bidFileReleaseDao;
	private SupplierBidDao supplierBidDao;

	public SupplierBidDao getSupplierBidDao() {
		return supplierBidDao;
	}

	public void setSupplierBidDao(SupplierBidDao supplierBidDao) {
		this.supplierBidDao = supplierBidDao;
	}

	@Override
	public List<Map<String, String>> findTreeJson() {
		return getList(bidFileReleaseDao.findAll());
	}

	@Override
	public Map<String, Object> loadDataForPage(String pid, List search, PageInfo pageInfo) {
		return null;
	}

	@Override
	public void updateDelFlagByIds(String[] ids) {
		for (String uuid : ids) {
			updateChildDelFlagByIds(uuid);
			bidFileReleaseDao.updateDelFlagById(uuid);
		}
	}

	private void updateChildDelFlagByIds(String pid) {
		List<BiddingFileRelease> lists = bidFileReleaseDao.findByPid(pid);
		for (BiddingFileRelease biddingFileRelease : lists) {
			updateChildDelFlagByIds(biddingFileRelease.getUuid());
			bidFileReleaseDao.updateDelFlagById(biddingFileRelease.getUuid());
		}
	}

	private List<Map<String, String>> getList(List<BiddingFileRelease> biddingFileReleaseList) {
		List<Map<String, String>> list = new ArrayList<>(biddingFileReleaseList.size());
		for (BiddingFileRelease biddingFileRelease : biddingFileReleaseList) {
			Map<String, String> map = new HashMap<>(4);
			map.put("id", biddingFileRelease.getUuid());
			map.put("text", biddingFileRelease.getName());
			map.put("pid", biddingFileRelease.getCode());
			map.put("type", "materialCategory");
			list.add(map);
		}
		return list;
	}

	public BidFileReleaseDao getBidFileReleaseDao() {
		return bidFileReleaseDao;
	}

	public void setBidFileReleaseDao(BidFileReleaseDao bidFileReleaseDao) {
		this.bidFileReleaseDao = bidFileReleaseDao;
	}

	/**
	 * 查找招标关联的文件
	 *
	 * @return
	 */
	@Override
	public BiddingFileRelease findByBid(String bidId) {
		String hql = "from BiddingFileRelease where biddingBulletin.id=?";
		return (BiddingFileRelease) bidFileReleaseDao.findUnique(hql, new Object[]{bidId});
	}

	@Override
	public BiddingFileRelease findById(String uuid) {
		String hql = "from BiddingFileRelease where uuid=?";
		return (BiddingFileRelease) bidFileReleaseDao.findUnique(hql, new Object[]{uuid});
	}

	/**
	 * 开标
	 *
	 * @param uuid
	 */
	@Override
	public void open(String uuid) {
		BiddingFileRelease biddingFileRelease = this.findById(uuid);
		if (biddingFileRelease == null)
			throw new RuntimeException("招标没有找到!");
		if (biddingFileRelease.getStatus() == BusinessConstants.BID_STATUS_OPENING)
			throw new RuntimeException("该招标已经开标!");
		if (biddingFileRelease.getStatus() == BusinessConstants.BID_STATUS_CLOSE)
			throw new RuntimeException("该招标已经结束!");
		biddingFileRelease.setStatus(BusinessConstants.BID_STATUS_OPENING);
		bidFileReleaseDao.update(biddingFileRelease);
	}

	@Override
	public List findTechBidEvalMaterialParam(String bidFileUuid) {
		List<PurchaseMaterialParamInfo> purchaseMaterialParamInfos = bidFileReleaseDao.findPurchaseMaterialParam(bidFileUuid);
		List<SupplierMaterialParamInfo> supplierMaterialParams = supplierBidDao.findSupplierMaterialParams(bidFileUuid);
		List<Map<String, Object>> list = new ArrayList<>();
		Integer i = 0, j = 0;
		String temp = "";
		for (PurchaseMaterialParamInfo paramInfo : purchaseMaterialParamInfos) {
			Map<String, Object> map = new HashMap<>();
			if (!temp.equals(paramInfo.getMaterialName())) {
				temp = paramInfo.getMaterialName();
				++i;
				j = 1;
			} else {
				j++;
			}
			map.put("rowId", i);
			map.put("materialName", paramInfo.getMaterialName());
			map.put("paramIndex", j);
			map.put("paramName", paramInfo.getParamName());
			map.put("requiredValue", paramInfo.getRequiredValue());
			for (SupplierMaterialParamInfo supplierParam : supplierMaterialParams) {
				if (supplierParam.getPurchaseMaterialUuid().equals(paramInfo.getPurchaseMaterialUuid())
						&& supplierParam.getPurchaseParamUuid().equals(paramInfo.getParamUuid())) {
					map.put("responseValue_" + supplierParam.getSupplierUuid(), supplierParam.getResponseValue());
					map.put("clarifyValue_" + supplierParam.getSupplierUuid(), supplierParam.getClarifyValue());
				}
			}
			list.add(map);
		}
		return list;
	}

	@Override
	public BidFileWithProject findBidFileWithProject(String bidFileUuid) {
		return bidFileReleaseDao.findBidFileWithProject(bidFileUuid);
	}

	@Override
	public String findPurchaseDeviceStr(String bidFileUuid) {
		List<PurchaseMaterial> pms = bidFileReleaseDao.findPurchaseMaterial(bidFileUuid);
		int sum = 0;
		StringBuilder result = new StringBuilder();
		StringBuilder devicesStr = new StringBuilder("其中");
		for (PurchaseMaterial pm : pms) {
			sum += pm.getNum();
			devicesStr.append(pm.getNum()).append("台").append(pm.getMaterialName()).append(",");
		}
		result.append("共").append(sum).append("台，").append(devicesStr);
		return result.toString();
	}

}
