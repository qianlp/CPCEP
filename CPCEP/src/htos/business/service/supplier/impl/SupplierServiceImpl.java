package htos.business.service.supplier.impl;

import htos.business.dao.bulletin.BiddingBulletinDao;
import htos.business.dao.supplier.*;
import htos.business.entity.bulletin.BiddingBulletin;
import htos.business.entity.material.MaterialCategory;
import htos.business.entity.supplier.*;
import htos.business.entity.supplier.view.Page;
import htos.business.entity.supplier.view.ViewAttachment;
import htos.business.entity.supplier.view.ViewSupplierTrader;
import htos.business.entity.supplier.view.ViewUserSupplier;
import htos.business.service.supplier.SupplierService;
import htos.business.utils.BusinessConstants;
import htos.coresys.entity.User;
import htos.coresys.service.UserService;
import htos.coresys.util.MD5Util;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author qinj
 * @date 2018-02-07 21:15
 **/
public class SupplierServiceImpl implements SupplierService {

	private Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);

	private UserService userService;
	private SupplierExtDao extDao;
	private SupplierBillingDao billingDao;
	private SupplierAttachmentDao attachmentDao;
	private BiddingBulletinDao biddingBulletinDao;
	private SupplierSignupDao supplierSignupDao;
	private GoodsScopeDao goodsScopeDao;
	private SupplierGoodsScopeDao supplierGoodsScopeDao;

	/**
	 * @description 注册
	 * @author qinj
	 * @date 2018/2/7 21:33
	 */
	@Override
	public boolean register(ViewUserSupplier model) {
		// 创建后台账号
		User user = createAccount(model);
		// 保存扩展信息
		SupplierExt ext = new SupplierExt();
		ext.setName(model.getName());
		ext.setRegisterAddress(model.getRegisterAddress());
		ext.setTaxpayerNo(model.getTaxpayerNo());
		ext.setCorporations(model.getCorporations());
		ext.setNature(model.getNature());
		ext.setRegisterCapital(model.getRegisterCapital());
		ext.setRegisterTime(model.getRegisterTime());
		ext.setContacts(model.getContacts());
		ext.setContactAddress(model.getContactAddress());
		ext.setPhon(model.getPhon());
		ext.setMobile(model.getMobile());
		ext.setFax(model.getFax());
		ext.setEmail(model.getEmail());
		ext.setLicense(model.getLicense());
		ext.setCertificate(model.getCertificate());
		ext.setAccount(user.getUuid());
		ext.setWfStatus(BusinessConstants.ACCOUNT_CHECK_WAITING);
		ext.setCreateDate(new Date());

		ext.setEnableFlag(true);
		ext.setLicenseFile(model.getLicenseFile());
		ext.setCertificateFile(model.getCertificateFile());
		ext.setMaterialName(model.getMaterialName());
		ext.setMaterialId(model.getMaterialId());
		extDao.save(ext);
		String [] getMaterialId = null;
		if(model.getMaterialId()==null || model.getMaterialId().equals("")){
		}else{
			getMaterialId = model.getMaterialId().split(";");
		}
		
		for (String materialid : getMaterialId) {
			SupplierGoodsScope supplierGoodsScope = new SupplierGoodsScope();
			supplierGoodsScope.setSupplier(ext);
			MaterialCategory materialCategory = new MaterialCategory();
			materialCategory.setUuid(materialid);
			supplierGoodsScope.setScope(materialCategory);
			supplierGoodsScopeDao.save(supplierGoodsScope);
		}

		// 保存开票信息
		saveBilling(model, ext);
		// TODO 保存附件

		SupplierAttachment licenseFile = attachmentDao.get(SupplierAttachment.class, model.getLicenseFile());
		if(licenseFile == null)
			throw new RuntimeException("社会统一信用代码");
		licenseFile.setSupplier(ext);
		attachmentDao.update(licenseFile);
		SupplierAttachment certificateFile = attachmentDao.get(SupplierAttachment.class, model.getCertificateFile());
		if(certificateFile == null)
			throw new RuntimeException("请上传专业能力资质证书!");
		certificateFile.setSupplier(ext);
		attachmentDao.update(certificateFile);

		String[] performances = model.getPerformance();
		updateAttachement(ext, performances);
		String[] performanceProves = model.getPerformanceProve();
		updateAttachement(ext, performanceProves);

		String[] otherAttachments = model.getOtherAttachment();
		updateAttachement(ext, otherAttachments);
		return true;
	}

	@Override
	public void check(String id, String checkStatus, User checkUser,String content) {
		SupplierExt ext = extDao.get(SupplierExt.class, id);
		if(ext == null)
			throw new RuntimeException("供应商信息不存在!");
		User user = userService.findUserById(ext.getAccount());
		if(user == null)
			throw new RuntimeException("账号信息没有找到!");
		user.setWfStatus(checkStatus);
		user.setUpdateBy(checkUser.getUserPerEname());
		user.setUpdateDate(new Date());
		user.setContent(content);
          
		ext.setCurUser(checkUser.getUserPerEname());
		ext.setWfStatus(checkStatus);
	}

	/**
	 * 详情
	 */
	@Override
	public ViewUserSupplier findById(String id) {
		SupplierExt ext = extDao.get(SupplierExt.class, id);
		User user = userService.findUserById(ext.getAccount());

		ViewUserSupplier view = new ViewUserSupplier();
		view.setEnableFlag(ext.getEnableFlag());
		view.setId(ext.getUuid());
		view.setName(ext.getName());
		view.setRegisterAddress(ext.getRegisterAddress());
		view.setTaxpayerNo(ext.getTaxpayerNo());
		view.setCorporations(ext.getCorporations());
		view.setNature(ext.getNature());
		view.setRegisterCapital(ext.getRegisterCapital());
		view.setRegisterTime(ext.getRegisterTime());
		view.setContacts(ext.getContacts());
		view.setContactAddress(ext.getContactAddress());
		view.setPhon(ext.getPhon());
		view.setMobile(ext.getMobile());
		view.setFax(ext.getFax());
		view.setEmail(ext.getEmail());
		view.setMaterialName(ext.getMaterialName());
		view.setMaterialId(ext.getMaterialId());
		view.setLicense(ext.getLicense());
		view.setCertificate(ext.getCertificate());
		view.setAccount(user.getUuid());
		view.setUsername(user.getUserName());
		

		Set<SupplierBilling> billings = ext.getBillings();
		if(billings != null && !billings.isEmpty()) {
			Iterator<SupplierBilling> it = billings.iterator();
			SupplierBilling billing = it.next();
			view.setBillingName(billing.getBillingName());
			view.setBillingBankAccount(billing.getBankAccount());
			view.setBillingBankName(billing.getBankName());
			view.setBillingTaxpayerId(billing.getTaxpayerId());
			view.setBillingAddress(billing.getAddress());
			view.setBillingPhone(billing.getPhone());
		}
		return view;
	}

	/**
	 * 获取附件
	 */
	@Override
	public List<ViewAttachment> findAttachments(String supplierId) {
		String hql = "from SupplierAttachment where supplier.uuid=? order by type asc, uploadTime desc";
		List<SupplierAttachment> list = attachmentDao.find(hql, new Object[]{supplierId});
		if(list == null)
			return null;
		List<ViewAttachment> newList = new ArrayList<ViewAttachment>();
		for (SupplierAttachment attachment : list) {
			ViewAttachment newAttachment = new ViewAttachment();
			newAttachment.setFileName(attachment.getFileName());
			newAttachment.setId(attachment.getUuid());
			newAttachment.setSize(attachment.getSize());
			newAttachment.setType(attachment.getType());
			newAttachment.setUploadTime(attachment.getUploadTime());
			newList.add(newAttachment);
		}
		return newList;
	}

	/**
	 * @description 获取有资格报名的招标
	 * @author qinj
	 * @date 2018/3/7 21:43
	 */
	@Override
	public void findTender(Page<ViewSupplierTrader> page, User user) {
		// TODO
        Date now = new Date();
		SupplierExt ext = this.findByUser(user.getUuid());

		String sql = "SELECT COUNT(*) FROM bs_bidding_bulletin a " +
				"JOIN bs_purchase_plan b ON b.uuid=a.purchase_plan_uuid " +
                "JOIN bs_bidding_file d on d.bulletin_uuid=a.uuid " +
				"WHERE purchase_method=:pmethod and a.wfStatus>=:status and d.status != 2 and a.register_end_time > :endTime";
		Number openCount = (Number) biddingBulletinDao.getCurrentSession().createSQLQuery(sql)
				.setParameter("status", "9")
				.setParameter("pmethod", "公开招标")
                .setParameter("endTime", now)
				.uniqueResult();

		/*sql = "SELECT COUNT(*) FROM bs_bidding_bulletin a " +
				"JOIN bs_purchase_plan b ON b.uuid=a.purchase_plan_uuid " +
				"JOIN bs_purchase_plan_candiate  c ON c.plan_id=b.uuid " +
                "JOIN bs_bidding_file d on d.bulletin_uuid=a.uuid " +
				"WHERE purchase_method=:pmethod AND c.supplier_id=:supplierId and a.wfStatus=:status and d.status != 2 " +
                "and a.register_end_time > :endTime";
		Number invCount = (Number) biddingBulletinDao.getCurrentSession().createSQLQuery(sql)
				.setParameter("status", "2")
				.setParameter("supplierId", ext.getUuid())
				.setParameter("pmethod", "邀请招标")
                .setParameter("endTime", now)
				.uniqueResult();*/

		page.setTotalCount(openCount.intValue());

		if(page.getTotalCount() == 0)
			return ;
		sql = "SELECT a.name as name, a.uuid AS id, c.wfStatus AS status, a.register_start_time as registerStartTime, " +
				"a.register_end_time as registerEndTime, a.bid_start_time as bidStartTime, " +
				"a.bid_end_time as bidEndTime, a.bid_open_time as bidOpenTime " +
				"FROM bs_bidding_bulletin a " +
				"JOIN bs_purchase_plan b ON b.uuid=a.purchase_plan_uuid " +
                "JOIN bs_bidding_file d on d.bulletin_uuid=a.uuid " +
				"left JOIN bs_supplier_signup c ON c.bidding=a.uuid and c.supplier=:supplier " +
				"WHERE purchase_method=:pmethod and a.wfStatus>=:status  and d.status != 2 and a.register_end_time > :endTime";

		List<ViewSupplierTrader> totalList = new ArrayList<ViewSupplierTrader>();
		List<ViewSupplierTrader> list = biddingBulletinDao.getCurrentSession().createSQLQuery(sql)
				.setParameter("status", "9")
				.setParameter("pmethod", "公开招标")
                .setParameter("supplier", ext.getUuid())
                .setParameter("endTime", now)
				.setResultTransformer(Transformers.aliasToBean(ViewSupplierTrader.class)).list();
		totalList.addAll(list);

		/*sql = "SELECT a.name as name, a.uuid AS id, d.wfStatus AS status, a.register_start_time as registerStartTime, " +
				"a.register_end_time as registerEndTime, a.bid_start_time as bidStartTime, " +
				"a.bid_end_time as bidEndTime, a.bid_open_time as bidOpenTime " +
				"FROM bs_bidding_bulletin a " +
				"JOIN bs_purchase_plan b ON b.uuid=a.purchase_plan_uuid " +
				"JOIN bs_purchase_plan_candiate  c ON c.plan_id=b.uuid " +
				"left JOIN bs_supplier_signup d ON d.bidding=a.uuid and d.supplier=:supplierId " +
				"WHERE purchase_method=:pmethod  AND c.supplier_id=:supplierId and a.wfStatus=:status " +
                "and a.register_end_time > :endTime";
		list = biddingBulletinDao.getCurrentSession().createSQLQuery(sql)
				.setParameter("status", "2")
				.setParameter("supplierId", ext.getUuid())
				.setParameter("pmethod", "邀请招标")
                .setParameter("endTime", now)
				.setResultTransformer(Transformers.aliasToBean(ViewSupplierTrader.class)).list();




		totalList.addAll(list);
*/
		page.setResult(totalList);
	}
  
	@Override
	public void findInvite(Page<ViewSupplierTrader> page, User user) {
		// TODO
        Date now = new Date();
		SupplierExt ext = this.findByUser(user.getUuid());

     String sql = "SELECT COUNT(*) FROM bs_bidding_bulletin a " +
				"JOIN bs_purchase_plan b ON b.uuid=a.purchase_plan_uuid " +
				"JOIN bs_purchase_plan_candiate  c ON c.plan_id=b.uuid " +
                "JOIN bs_bidding_file d on d.bulletin_uuid=a.uuid " +
				"WHERE purchase_method=:pmethod AND c.supplier_id=:supplierId and a.wfStatus>=:status and d.status != 2 " +
                "and a.register_end_time > :endTime";
		Number invCount = (Number) biddingBulletinDao.getCurrentSession().createSQLQuery(sql)
				.setParameter("status", "2")
				.setParameter("supplierId", ext.getUuid())
				.setParameter("pmethod", "邀请招标")
                .setParameter("endTime", now)
				.uniqueResult();

		page.setTotalCount(invCount.intValue());

		if(page.getTotalCount() == 0)
			return ;
		List<ViewSupplierTrader> totalList = new ArrayList<ViewSupplierTrader>();
		sql = "SELECT a.name as name, a.uuid AS id, d.wfStatus AS status, a.register_start_time as registerStartTime, " +
				"a.register_end_time as registerEndTime, a.bid_start_time as bidStartTime, " +
				"a.bid_end_time as bidEndTime, a.bid_open_time as bidOpenTime " +
				"FROM bs_bidding_bulletin a " +
				"JOIN bs_purchase_plan b ON b.uuid=a.purchase_plan_uuid " +
				"JOIN bs_purchase_plan_candiate  c ON c.plan_id=b.uuid " +
				"left JOIN bs_supplier_signup d ON d.bidding=a.uuid and d.supplier=:supplierId " +
				"WHERE purchase_method=:pmethod  AND c.supplier_id=:supplierId and a.wfStatus>=:status " +
                "and a.register_end_time > :endTime";
		List<ViewSupplierTrader> list = biddingBulletinDao.getCurrentSession().createSQLQuery(sql)
				.setParameter("status", "2")
				.setParameter("supplierId", ext.getUuid())
				.setParameter("pmethod", "邀请招标")
                .setParameter("endTime", now)
				.setResultTransformer(Transformers.aliasToBean(ViewSupplierTrader.class)).list();

		totalList.addAll(list);

		page.setResult(totalList);
	}

	/**
	 * @description 招标报名
	 * @author qinj
	 * @date 2018/3/8 23:35
	 */
	@Override
	public void signup(ViewUserSupplier model, User user, String tabType) {
		SupplierExt supplier = this.findByUser(user.getUuid());
		if(supplier == null)
			throw new RuntimeException("账号信息不存在，请联系管理员核实!");
		BiddingBulletin bidding = biddingBulletinDao.get(BiddingBulletin.class, model.getId());
		if(bidding == null)
			throw new RuntimeException("没有找到招标，请联系管理员核实!");
		String hql = "from SupplierSignup where supplier.uuid=? and bidding.uuid=?";
		SupplierSignup entity = supplierSignupDao.get(hql, new Object[]{supplier.getUuid(), bidding.getUuid()});
		if(entity != null) {
			short status = Short.parseShort(entity.getWfStatus());
			if(status == BusinessConstants.SUPPLIER_TRADER_SIGNUP)
				throw new RuntimeException("已经报名该招标!");
			if(status == BusinessConstants.SUPPLIER_TRADER_SIGNUP_SUCCESS)
				throw new RuntimeException("已经通过审核!");
			if(status == BusinessConstants.SUPPLIER_TRADER_SIGNUP_FAILED)
				throw new RuntimeException("没有通过审核!");
			if(status == BusinessConstants.SUPPLIER_TRADER_DRAFT) {
				entity.setCreateDate(new Date());
				entity.setMobile(model.getMobile());
				entity.setPhone(model.getPhone());
				entity.setEmail(model.getEmail());
				entity.setContact(model.getContact());
				entity.setPosition(model.getPosition());
				if(model.getSignup()){
					if(tabType.equals("1")){ //邀请招标
						entity.setWfStatus(BusinessConstants.SUPPLIER_TRADER_SIGNUP_SUCCESS+"");
					}else{//公开招标
					    entity.setWfStatus(BusinessConstants.SUPPLIER_TRADER_SIGNUP + "");
					}
				}		
				supplierSignupDao.update(entity);
				return;
			}
		}

		entity = new SupplierSignup();
		if(model.getSignup()){
			if(tabType.equals("1")){//邀请招标
				entity.setWfStatus(BusinessConstants.SUPPLIER_TRADER_SIGNUP_SUCCESS+"");
			} else{
		     	entity.setWfStatus(BusinessConstants.SUPPLIER_TRADER_SIGNUP + "");
			}
		}else{
			entity.setWfStatus(BusinessConstants.SUPPLIER_TRADER_DRAFT + "");
		}
		entity.setCreateDate(new Date());
		entity.setCreateBy(supplier.getName());
		entity.setBidding(bidding);
		entity.setSupplier(supplier);
		entity.setDelFlag("0");
		entity.setMobile(model.getMobile());
		entity.setPhone(model.getPhone());
		entity.setEmail(model.getEmail());
		entity.setContact(model.getContact());
		entity.setPosition(model.getPosition());
		supplierSignupDao.save(entity);
	}

	/**
	 * @description 查找供应商信息
	 * @author qinj
	 * @date 2018/3/8 23:38
	 */
	@Override
	public SupplierExt findByUser(String userId) {
		String hql = "from SupplierExt where account=?";
		return (SupplierExt) supplierSignupDao.findUnique(hql, new Object[]{userId});
	}

	/**
	 * @description 查找报名信息
	 * @param traderId
	 * @param supplier
	 * @return
	 */
	@Override
	public SupplierSignup findMySignupByTrader(String traderId, String supplier) {
		String hql = "from SupplierSignup where supplier.uuid = ? and bidding.uuid = ?";
		return (SupplierSignup) supplierSignupDao.findUnique(hql, new Object[]{supplier, traderId});
	}

	/**
	 *
	 * @param page
	 * @param user
	 */
	@Override
	public void findNeedCheckSignup(Page<ViewSupplierTrader> page, User user, String supplierName) {
		String sql = "FROM bs_supplier_signup a " +
				"JOIN bs_bidding_bulletin  b ON b.uuid = a.bidding " +
				"JOIN bs_supplier_ext c on c.uuid = a.supplier " +
				"WHERE a.wfStatus != :status ";
		if(StringUtils.isNotBlank(supplierName)) {
			sql += "and c.name like '%" + supplierName + "%' ";
		}
		Number count = (Number) biddingBulletinDao.getCurrentSession()
				.createSQLQuery("select count(*) " + sql)
				.setParameter("status", BusinessConstants.SUPPLIER_TRADER_DRAFT).uniqueResult();
		page.setTotalCount(count.intValue());
		if(count.intValue() == 0)
			return ;
		sql = "SELECT a.uuid as signupId, b.uuid as id, c.uuid as supplier, a.contact as contact, a.position as position, a.phone as phone, a.mobile as mobile, a.email as email, " +
				"a.wfStatus AS wfStatus, b.name as tradeName, c.name as supplierName " + sql;

		int from = (page.getpageIndex() ) * page.getPageSize();
		List<ViewSupplierTrader> list = biddingBulletinDao
				.getCurrentSession()
				.createSQLQuery(sql)
				.setFirstResult(from)
				.setMaxResults(page.getPageSize())
				.setParameter("status", BusinessConstants.SUPPLIER_TRADER_DRAFT)
				.setResultTransformer(Transformers.aliasToBean(ViewSupplierTrader.class)).list();
		page.setResult(list);
	}

	@Override
	public void signCheck(String id, String checkStatus, User checkUser) {
		String[] ids = id.split("\\*");
		for(int i=0;i<ids.length;i++){
			SupplierSignup signup = supplierSignupDao.get(SupplierSignup.class, ids[i]);
			if(signup == null)
				throw new RuntimeException("报名信息不存在!");
			signup.setWfStatus(checkStatus);
			signup.setUpdateBy(checkUser.getUserPerEname());
			signup.setUpdateDate(new Date());
			signup.setCurUser(checkUser.getUserPerEname());
			supplierSignupDao.update(signup);
		}

	}

	/**
	 * @description 启用/禁用
	 * @author qinj
	 * @date 2018/5/8 22:41
	 */
	@Override
	public void enable(String id) {
		SupplierExt ext = extDao.get(SupplierExt.class, id);
		if(ext == null)
			return ;
		ext.setEnableFlag(!ext.getEnableFlag());
		extDao.update(ext);
	}

	/**
	 * @description 查找开票信息
	 * @author qinj
	 * @date 2018/5/12 12:15
	 */
	@Override
	public SupplierBilling findBillig(String supplierId) {
		String hql = "from SupplierBilling where supplier.uuid = ?";
		SupplierBilling billing = (SupplierBilling) billingDao.findUnique(hql, new Object[]{supplierId});
		return billing;
	}

	/**
	 * @description 修改供应商信息
	 * @author qinj
	 * @date 2018/5/12 12:24
	 */
	@Override
	public void update(ViewUserSupplier model) {
		// TODO
		SupplierExt ext = extDao.get(SupplierExt.class, model.getUuid());
		if(ext == null)
			throw new RuntimeException("供应商信息不存在!");
		// 保存扩展信息
		ext.setName(model.getName());
		ext.setRegisterAddress(model.getRegisterAddress());
		ext.setTaxpayerNo(model.getTaxpayerNo());
		ext.setCorporations(model.getCorporations());
		ext.setNature(model.getNature());
		ext.setRegisterCapital(model.getRegisterCapital());
		ext.setRegisterTime(model.getRegisterTime());
		ext.setContacts(model.getContacts());
		ext.setContactAddress(model.getContactAddress());
		ext.setPhon(model.getPhon());
		ext.setMobile(model.getMobile());
		ext.setFax(model.getFax());
		ext.setEmail(model.getEmail());
		ext.setLicense(model.getLicense());
		ext.setTaxCertificate(model.getTaxCertificate());
		ext.setOrgCode(model.getOrgCode());
		ext.setCertificate(model.getCertificate());
		// 修改信息后状态调整为待审核
		ext.setWfStatus(BusinessConstants.ACCOUNT_CHECK_WAITING);
		ext.setUpdateDate(new Date());
		ext.setEnableFlag(true);

		ext.setLicenseFile(model.getLicenseFile());
		ext.setTaxCertificateFile(model.getTaxCertificateFile());
		ext.setOrgCodeFile(model.getOrgCodeFile());
		ext.setCertificateFile(model.getCertificateFile());
		ext.setMaterialName(model.getMaterialName());
		ext.setMaterialId(model.getMaterialId());
		extDao.update(ext);

		User user = userService.findUserById(ext.getAccount());
		user.setWfStatus(BusinessConstants.ACCOUNT_CHECK_WAITING);
		user.setUserTelephone(model.getMobile());
		String [] getMaterialId = null;

		String hql = "delete from SupplierGoodsScope where supplier.uuid=?";
		supplierGoodsScopeDao.executeHql(hql, new Object[]{ext.getUuid()});
		if(model.getMaterialId()==null || model.getMaterialId().equals("")){
		}else{
			getMaterialId = model.getMaterialId().split(";");
		}
		
		for (String materialid : getMaterialId) {
			SupplierGoodsScope supplierGoodsScope = new SupplierGoodsScope();
			supplierGoodsScope.setSupplier(ext);
			MaterialCategory materialCategory = new MaterialCategory();
			materialCategory.setUuid(materialid);
			supplierGoodsScope.setScope(materialCategory);
			supplierGoodsScopeDao.save(supplierGoodsScope);
		}

		// 保存开票信息
		updateBilling(model, ext);

		// TODO 保存附件
		SupplierAttachment licenseFile = attachmentDao.get(SupplierAttachment.class, model.getLicenseFile());
		if(licenseFile == null)
			throw new RuntimeException("请上传营业执照!");
		licenseFile.setSupplier(ext);
		attachmentDao.update(licenseFile);
		SupplierAttachment certificateFile = attachmentDao.get(SupplierAttachment.class, model.getCertificateFile());
		if(certificateFile == null)
			throw new RuntimeException("请上传专业能力资质证书!");
		certificateFile.setSupplier(ext);
		attachmentDao.update(certificateFile);

		String[] performances = model.getPerformance();
		updateAttachement(ext, performances);
		String[] performanceProves = model.getPerformanceProve();
		updateAttachement(ext, performanceProves);
		String[] otherAttachments = model.getOtherAttachment();
		updateAttachement(ext, otherAttachments);
	}

	/**
	 * @description 获取供货范围
	 * @author qinj
	 * @date 2018/5/12 20:14
	 */
	@Override
	public List<GoodsScope> findGoodsScope() {
		return goodsScopeDao.find("from GoodsScope");
	}

	@Override
	public List<SupplierGoodsScope> findMyScope(String supplierId) {
		String hql = "from SupplierGoodsScope where supplier.uuid=?";
		return supplierGoodsScopeDao.find(hql, new Object[]{supplierId});
	}

	@Override
	public GoodsScope getGoodsScope(String uuid) {
		return goodsScopeDao.get(GoodsScope.class, uuid);
	}

	@Override
	public void deleteScope(String uuid) {
		supplierGoodsScopeDao.delete("delete SupplierGoodsScope where scope.uuid='" + uuid + "'");
		goodsScopeDao.delete("delete  GoodsScope where uuid='" + uuid + "'");
	}

	@Override
	public List<Map<String, Object>> findScope() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String hql = "from GoodsScope";
		List<GoodsScope> scopes = goodsScopeDao.find(hql);
		if(scopes == null || scopes.isEmpty())
			return list;
		for (GoodsScope scope : scopes) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", scope.getUuid());
			map.put("text", scope.getName());
			map.put("pid", -1);
			map.put("type", "scope");
			list.add(map);
		}
		return list;
	}

	@Override
	public void findPage(Page<ViewUserSupplier> page, String name, String scopeId,String pass,String wfStatus) {
		String sql = "FROM bs_supplier_Ext a ";
		if(StringUtils.isNotBlank(scopeId))
				sql = sql + "JOIN bs_supplier_goods_scope b ON b.supplier_id=a.UUID ";
		sql = sql + "WHERE 1=1 ";
		if(StringUtils.isNotBlank(name)) {
			sql += "and name like :name ";
		}
		if(StringUtils.isNotBlank(scopeId)) {
			sql += "and b.scope_id=:scopeId ";
		}
		if(StringUtils.isNotBlank(pass)&&pass.equals("pass")){
			sql += " and  a.wfStatus=2 and enable_flag = true ";
		}
		if(StringUtils.isNotBlank(wfStatus)){
			sql += " and  a.wfStatus=:wfStatus ";
		}
		Query query = biddingBulletinDao.getCurrentSession().createSQLQuery("select count(*) " + sql);
		if(StringUtils.isNotBlank(name))
			query.setParameter("name", "%" + name + "%");
		if(StringUtils.isNotBlank(scopeId))
			query.setParameter("scopeId", scopeId);
		if(StringUtils.isNotBlank(wfStatus))
			query.setParameter("wfStatus", wfStatus);

		Number count = (Number) query.uniqueResult();
		page.setTotalCount(count.intValue());
		if(count.intValue() == 0)
			return ;
		sql = "SELECT a.uuid as uuid, a.name as name, a.uuid AS id, a.contacts as contacts, a.phon as phone, a.mobile as mobile, a.fax as fax, " +
				"a.email as email, a.contact_address as contactAddress, a.corporations as corporations, a.wfStatus as wfStatus, " +
				"a.curUser as curUser, a.enable_flag as enableFlag, a.update_date as updateDate " + sql;
		sql += " order by a.register_time desc";
		query = biddingBulletinDao.getCurrentSession().createSQLQuery(sql);
		if(StringUtils.isNotBlank(name))
			query.setParameter("name", "%" + name + "%");
		if(StringUtils.isNotBlank(scopeId))
			query.setParameter("scopeId", scopeId);
		if(StringUtils.isNotBlank(wfStatus))
			query.setParameter("wfStatus", wfStatus);
		query.setMaxResults(page.getPageSize());
		query.setFirstResult(page.getpageIndex() * page.getPageSize());
		List<ViewUserSupplier> list = query.setResultTransformer(Transformers.aliasToBean(ViewUserSupplier.class)).list();
		page.setResult(list);
	}

	@Override
	public List<SupplierSignup> findBulletinSignUp(String bulletinUuid) {
		return supplierSignupDao.findByBulletin(bulletinUuid);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//
	//

	private void updateBilling(ViewUserSupplier model, SupplierExt ext) {
		String hql = "from SupplierBilling where supplier.uuid = ?";
		SupplierBilling billing = (SupplierBilling) billingDao.findUnique(hql, new Object[]{ext.getUuid()});
		if(billing == null) {
			saveBilling(model, ext);
			return ;
		}
		billing.setBillingName(model.getBillingName());
		billing.setBankAccount(model.getBillingBankAccount());
		billing.setBankName(model.getBillingBankName());
		billing.setTaxpayerId(model.getBillingTaxpayerId());
		billing.setAddress(model.getBillingAddress());
		billing.setPhone(model.getBillingPhone());
		billingDao.update(billing);
	}

	private void updateAttachement(SupplierExt ext, String[] attachements) {
		if(attachements == null || attachements.length == 0)
			return ;
		for (String performance : attachements) {
			SupplierAttachment attachment = attachmentDao.get(SupplierAttachment.class, performance);
			attachment.setSupplier(ext);
			attachmentDao.update(attachment);
		}
	}

	/**
	 * @description 新增账号信息
	 * @author qinj
	 * @date 2018/2/7 21:58
	 */
	private User createAccount(ViewUserSupplier model) {
		User user = new User();
		user.setUserName(model.getUsername());
		user.setUserPassword(MD5Util.EncoderByMd5(model.getPassword()));
		user.setUserMail(model.getEmail());
		user.setUserFax(model.getFax());
		user.setUserType(BusinessConstants.USER_TYPE_SUPPLIER);
		user.setUserTelephone(model.getMobile());
		user.setUserPostion(0);
		user.setWfStatus(BusinessConstants.ACCOUNT_CHECK_WAITING);
		// TODO 所属部门
//		user.setUserDeptId("");
		userService.saveUser(user);
		return user;
	}

	/**
	 * @description 保存开票信息
	 * @author qinj
	 * @date 2018/2/7 21:52
	 */
	private void saveBilling(ViewUserSupplier model, SupplierExt ext) {
		SupplierBilling billing = new SupplierBilling();
		billing.setBillingName(model.getBillingName());
		billing.setBankAccount(model.getBillingBankAccount());
		billing.setBankName(model.getBillingBankName());
		billing.setTaxpayerId(model.getBillingTaxpayerId());
		billing.setAddress(model.getBillingAddress());
		billing.setPhone(model.getBillingPhone());
		billing.setSupplier(ext);
		billingDao.save(billing);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//
	//

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setExtDao(SupplierExtDao extDao) {
		this.extDao = extDao;
	}

	public void setBillingDao(SupplierBillingDao billingDao) {
		this.billingDao = billingDao;
	}

	public void setAttachmentDao(SupplierAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	public void setBiddingBulletinDao(BiddingBulletinDao biddingBulletinDao) {
		this.biddingBulletinDao = biddingBulletinDao;
	}

	public void setSupplierSignupDao(SupplierSignupDao supplierSignupDao) {
		this.supplierSignupDao = supplierSignupDao;
	}

	public void setGoodsScopeDao(GoodsScopeDao goodsScopeDao) {
		this.goodsScopeDao = goodsScopeDao;
	}

	public void setSupplierGoodsScopeDao(SupplierGoodsScopeDao supplierGoodsScopeDao) {
		this.supplierGoodsScopeDao = supplierGoodsScopeDao;
	}
}
