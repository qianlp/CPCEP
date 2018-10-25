package htos.business.service.bulletin.impl;

import htos.business.dao.bid.SupplierBidDao;
import htos.business.dao.bulletin.BiddingBulletinDao;
import htos.business.dao.bulletin.InvitationDao;
import htos.business.dao.procurement.PurchaseInfoTypeDao;
import htos.business.dao.procurement.PurchasePlanDao;
import htos.business.entity.bid.Invitation;
import htos.business.entity.bid.SupplierBid;
import htos.business.entity.bulletin.BiddingBulletin;
import htos.business.entity.bulletin.view.ViewBiddingBulletin;
import htos.business.entity.procurement.PurchaseApply;
import htos.business.entity.procurement.PurchaseInfoType;
import htos.business.entity.procurement.PurchasePlan;
import htos.business.entity.supplier.SupplierExt;
import htos.business.entity.supplier.SupplierSignup;
import htos.business.entity.supplier.view.Page;
import htos.business.entity.supplier.view.ViewUserSupplier;
import htos.business.service.bulletin.BiddingBulletinService;
import htos.business.service.supplier.SupplierService;
import htos.business.utils.HttpUtils;
import htos.coresys.dao.CommonDao;
import htos.coresys.entity.User;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

public class BiddingBulletinServiceImpl implements BiddingBulletinService {

    private Logger log = LoggerFactory.getLogger(BiddingBulletinServiceImpl.class);

	private BiddingBulletinDao biddingBulletinDao;
	private InvitationDao invitationDao;
	private SupplierBidDao supplierBidDao;
	private SupplierService supplierService;
	private PurchaseInfoTypeDao purchaseInfoTypeDao;
	private PurchasePlanDao purchasePlanDao;
	private CommonDao<PurchasePlan> commonDao;

	@Override
	public BiddingBulletin findById(String id) {
		return biddingBulletinDao.get(BiddingBulletin.class, id);
	}

    public void setPurchasePlanDao(PurchasePlanDao purchasePlanDao) {
        this.purchasePlanDao = purchasePlanDao;
    }
    
    public CommonDao<PurchasePlan> getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao<PurchasePlan> commonDao) {
		this.commonDao = commonDao;
	}

	public void setPurchaseInfoTypeDao(PurchaseInfoTypeDao purchaseInfoTypeDao) {
        this.purchaseInfoTypeDao = purchaseInfoTypeDao;
    }

    public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public void setInvitationDao(InvitationDao invitationDao) {
		this.invitationDao = invitationDao;
	}

	public BiddingBulletinDao getBiddingBulletinDao() {
		return biddingBulletinDao;
	}

	public void setBiddingBulletinDao(BiddingBulletinDao biddingBulletinDao) {
		this.biddingBulletinDao = biddingBulletinDao;
	}

	public void setSupplierBidDao(SupplierBidDao supplierBidDao) {
		this.supplierBidDao = supplierBidDao;
	}

	/**
	 * 招标列表
	 *  @param page
	 * @param bidName
	 */
	@Override
	public void findPage(Page<ViewBiddingBulletin> page, String bidName, User user) {
		if(user.getUserType() == null || user.getUserType() == 0) {
			String sql = "FROM bs_bidding_file a " +
					"JOIN bs_bidding_bulletin b ON a.bulletin_uuid=b.uuid " +
					"where 1=1 ";
			if (StringUtils.isNotBlank(bidName)) {
				sql += "and b.name like '%" + bidName + "%' ";
			}
			Number count = (Number) biddingBulletinDao.getCurrentSession().createSQLQuery("select count(*) " + sql).uniqueResult();
			page.setTotalCount(count.intValue());
			if (count.intValue() == 0)
				return;
			int from = (page.getpageIndex() ) * page.getPageSize();
			sql = "SELECT a.uuid as uuid, a.status as status, b.name AS name, a.fileName AS fileName, a.version AS version, b.code AS code, b.purchase_plan_uuid AS purchasePlanUuid " + sql;
			List<ViewBiddingBulletin> list = biddingBulletinDao.getCurrentSession().createSQLQuery(sql)
					.setResultTransformer(Transformers.aliasToBean(ViewBiddingBulletin.class))
					.setMaxResults(page.getPageSize())
					.setFirstResult(from)
					.list();
			//获取采购申请中的技术招标人
			for(ViewBiddingBulletin bid : list){
				PurchasePlan plan = commonDao.getEntityByID("PurchasePlan", bid.getPurchasePlanUuid());
				if(user.getUuid().equals(plan.getTechnologyReviewerUuid())){
					bid.setTechStatus("1");
				}else{
					bid.setTechStatus("0");
				}
			}
			page.setResult(list);
			//邀请澄清状态的判定
			if(page.getResult() == null || page.getResult().isEmpty())
			    return ;
            for (ViewBiddingBulletin view : page.getResult()) {
                sql = "SELECT COUNT(*) FROM bs_invitation a " +
                        "join bs_supplier_bid b ON b.uuid = a.supplier_bid_uuid " +
                        " WHERE b.bidding_file_uuid=:uuid AND a.TYPE=:type ";
                count = (Number) biddingBulletinDao.getCurrentSession().createSQLQuery(sql)
                        .setParameter("uuid", view.getUuid())
                        .setParameter("type", 1)
                        .uniqueResult();
                if(count == null || count.intValue() == 0)
                    view.setInvitationClarify(false);
                else
                	 view.setInvitationClarify(true);
            }
            //邀请竞价装填的判定
			if(page.getResult() == null || page.getResult().isEmpty())
			    return ;
            for (ViewBiddingBulletin view : page.getResult()) {
                sql = "SELECT COUNT(*) FROM bs_invitation a " +
                        "join bs_supplier_bid b ON b.uuid = a.supplier_bid_uuid " +
                        " WHERE b.bidding_file_uuid=:uuid AND a.TYPE=:type ";
                count = (Number) biddingBulletinDao.getCurrentSession().createSQLQuery(sql)
                        .setParameter("uuid", view.getUuid())
                        .setParameter("type", 2)
                        .uniqueResult();
                if(count == null || count.intValue() == 0)
                    view.setInvitationBid(false);
                else
                    view.setInvitationBid(true);
            }

		} else {
			SupplierExt supplier = supplierService.findByUser(user.getUuid());
			String sql = "FROM bs_bidding_file a " +
					"JOIN bs_bidding_bulletin b ON a.bulletin_uuid=b.uuid " +
					"JOIN bs_supplier_signup d ON d.bidding=b.uuid " +
					"join bs_supplier_ext ext ON d.supplier = ext.UUID " +
					"left JOIN bs_supplier_bid c ON c.bidding_file_uuid=a.uuid and c.create_uuid = ext.account " +
					"where d.supplier=:userId  and d.wfStatus=2 ";
			if (StringUtils.isNotBlank(bidName)) {
				sql += "and b.name like '%" + bidName + "%' ";
			}
			Number count = (Number) biddingBulletinDao.getCurrentSession().createSQLQuery("select count(*) " + sql).setParameter("userId", supplier.getUuid()).uniqueResult();
			page.setTotalCount(count.intValue());
			if (count.intValue() == 0)
				return;
			int from = page.getpageIndex() * page.getPageSize();
			sql = "SELECT a.uuid as uuid, a.status as status, b.name AS name, a.fileName AS fileName, a.version AS version, b.code AS code, " +
					"c.uuid as supplierBidId, c.invitation_clarify as invitationClarify, c.invitation_bid as invitationBid, c.second_total_price as secondTotalPrice, c.third_total_price as thirdTotalPrice " +
					"" + sql;
			List<ViewBiddingBulletin> list = biddingBulletinDao.getCurrentSession().createSQLQuery(sql)
					.setParameter("userId", supplier.getUuid())
					.setResultTransformer(Transformers.aliasToBean(ViewBiddingBulletin.class))
					.setMaxResults(page.getPageSize())
					.setFirstResult(from)
					.list();
			if(list != null && !list.isEmpty()) {
				for (ViewBiddingBulletin view : list) {
					if(StringUtils.isBlank(view.getThirdTotalPrice())) {
						view.setRank(-1);
						continue;
					}
					sql = "SELECT COUNT(*) FROM bs_supplier_bid sb " +
							"JOIN bs_supplier_score sc ON sc.supplier_bid_uuid = sb.uuid " +
							"WHERE sc.is_feasible ='可行' and sb.bidding_file_uuid=:uuid AND CONVERT(sb.third_total_price,SIGNED)<:price and sb.create_uuid!=:userId ";

					// JOIN bs_supplier_score sc ON  sc.supplier_bid_uuid = sb.uuid where bf.uuid=:bidFileId and sc.is_feasible ='可行'
					count = (Number) biddingBulletinDao.getCurrentSession().createSQLQuery(sql)
							.setParameter("uuid", view.getUuid())
                            .setParameter("userId", user.getUuid())
							.setParameter("price", Float.parseFloat(view.getThirdTotalPrice()))
							.uniqueResult();
					if(count == null)
						view.setRank(1);
					else
						view.setRank(count.intValue() + 1);
					if(StringUtils.isBlank(view.getSupplierBidId())) {
                        view.setInvitationClarify(false);
                        view.setInvitationBid(false);
                        continue ;
                    }

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String nowStr = sdf.format(new Date());
					// 邀请澄清
                    sql = "SELECT COUNT(*) FROM bs_invitation a " +
                            " WHERE a.supplier_bid_uuid=:uuid AND a.TYPE=:type and a.end_date>=:endDate";
                    count = (Number) biddingBulletinDao.getCurrentSession().createSQLQuery(sql)
                            .setParameter("uuid", view.getSupplierBidId())
                            .setParameter("endDate",nowStr)
                            .setParameter("type", 1)
                            .uniqueResult();
                    if(count == null || count.intValue() == 0)
                        view.setInvitationClarify(false);
                    else
                        view.setInvitationClarify(true);

                    // 邀请竞价
                    sql = "SELECT COUNT(*) FROM bs_invitation a " +
                            " WHERE a.supplier_bid_uuid=:uuid AND a.TYPE=:type and a.end_date>=:endDate";
                    count = (Number) biddingBulletinDao.getCurrentSession().createSQLQuery(sql)
                            .setParameter("uuid", view.getSupplierBidId())
                            .setParameter("endDate", nowStr)
                            .setParameter("type", 2)
                            .uniqueResult();
                    if(count == null || count.intValue() == 0)
                        view.setInvitationBid(false);
                    else
                        view.setInvitationBid(true);


				}
			}
			page.setResult(list);
		}
	}

    public static void main(String[] args) {
        System.out.println(Integer.parseInt("31000.0"));
    }

	/**
	 * 参与招标的供应商列表
	 *
	 * @param page
	 * @param bidId
	 * @param supplierName
	 */
	@Override
	public void findSupplier(Page<ViewBiddingBulletin> page, String bidId, String supplierName,String hasBid) {
		String sql = "FROM bs_supplier_signup a " +
				"JOIN bs_supplier_ext b ON b.uuid=a.supplier " +
				"JOIN bs_bidding_bulletin c ON c.uuid=a.bidding " +
				"JOIN bs_bidding_file f on f.bulletin_uuid = c.uuid " +
				"Left join bs_supplier_bid sb on sb.bidding_file_uuid = f.uuid and b.account = sb.create_uuid " +
				"WHERE f.uuid=? and a.wfStatus!=0 and a.wfStatus!=1 ";
		if (StringUtils.isNotBlank(supplierName)) {
			sql += "and b.name like '%" + supplierName + "%' ";
		}
		if(StringUtils.isNotBlank(hasBid)&&hasBid.equalsIgnoreCase("true")){
			sql +="and sb.uuid is not null ";
		}
		Number count = (Number) biddingBulletinDao.getCurrentSession()
				.createSQLQuery("select count(*) " + sql)
				.setParameter(0, bidId)
				.uniqueResult();
		page.setTotalCount(count.intValue());
		if (count.intValue() == 0)
			return;
		int from = (page.getpageIndex() - 1) * page.getPageSize();
		sql = "SELECT b.account as userId, b.NAME AS supplierName,b.CONTACTS AS contacts,b.PHON AS phon,a.wfStatus AS wfStatus, c.name AS name, c.code AS code,sb.uuid as bidUuid " + sql;
		List<ViewBiddingBulletin> list = biddingBulletinDao.getCurrentSession()
				.createSQLQuery(sql)
				.setParameter(0, bidId)
				.setResultTransformer(Transformers.aliasToBean(ViewBiddingBulletin.class))
				.setMaxResults(page.getPageSize()).setFirstResult(from).list();
		page.setResult(list);
	}


	/**
	 * 邀请开标
	 */
	@Override
	public void invite(String[] supplierIds, BiddingBulletin model) {
		for (String supplierId : supplierIds) {
			String hql = "from Invitation where supplierBidUuid=? and type = ?";
			List<Invitation> list = invitationDao.find(hql, new Object[]{supplierId, model.getType()});
			boolean exist = list != null && !list.isEmpty();
			Invitation entity = exist ? list.get(0) : new Invitation();
			if(entity.getPrice() != null)
				continue ;// TODO
			if(model.getType() == 2) {
				hql = "select count(*) from Invitation where supplierBidUuid=:supplierBidUuid and type = 1";
				Number count = (Number) invitationDao.getCurrentSession().createQuery(hql).setParameter("supplierBidUuid", supplierId).uniqueResult();
				if(count.intValue() == 0)
					throw new RuntimeException("必须先进行澄清报价才能进行邀请报价!");
			}
			entity.setSupplierBidUuid(supplierId);
			entity.setDownExtent(model.getDownExtent());
			entity.setUpExtent(model.getUpExtent());
			entity.setEndDate(model.getEndDate());
			entity.setPayType(model.getPayType());
			entity.setDeliveryDate(model.getDeliveryDate());
			entity.setDescription(model.getDescription());
			entity.setType(model.getType());
			entity.setMinAmount(model.getMinAmount());
			if (!exist)
				invitationDao.save(entity);
			else
				invitationDao.update(entity);

			SupplierBid supplierBid = supplierBidDao.get(SupplierBid.class, supplierId);
			if(model.getType() == 1)
				supplierBid.setInvitationClarify(true);
			else if(model.getType() == 2)
				supplierBid.setInvitationBid(true);
		}
	}

	/**
	 * 查找我的受邀招标
	 *
	 * @param page
	 */
	@Override
	public void findMyInvite(Page<Invitation> page, String supplierId) {
		String sql = "FROM bs_invitation a " +
				"join bs_supplier_bid b on b.uuid=a.supplier_bid_uuid " +
				"join bs_supplier_ext d on d.account=b.create_uuid " +
				"join bs_bidding_file e on e.uuid=b.bidding_file_uuid " +
				"join bs_bidding_bulletin f on f.uuid=e.bulletin_uuid " +
				"where d.account=:supplierId ";
		Number count = (Number) biddingBulletinDao.getCurrentSession().createSQLQuery("select count(*) " + sql).setParameter("supplierId", supplierId).uniqueResult();
		page.setTotalCount(count.intValue());
		if (count.intValue() == 0)
			return;
		sql = "SELECT a.uuid as invitationId, f.uuid as id, e.name as name, a.type as type, a.down_extent as downExtent, a.end_date as endDate, a.pay_type as payType, " +
				"a.delivery_date as deliveryDate, a.description as description, a.min_amount as minAmount, a.price as price " + sql;
		List<Invitation> list = biddingBulletinDao.getCurrentSession().createSQLQuery(sql)
				.setParameter("supplierId", supplierId)
				.setMaxResults(page.getPageSize())
				.setFirstResult(page.getpageIndex() * page.getPageSize())
				.setResultTransformer(Transformers.aliasToBean(Invitation.class)).list();
		page.setResult(list);
	}

	/**
	 * 未报名供应商名单
	 *
	 * @param page
	 */
	@Override
	public void findInvitationSupplier(Page<ViewUserSupplier> page, String bidId) {
		String sql = "FROM bs_supplier_ext " +
				"where uuid not in (select supplier FROM bs_supplier_signup where bidding=:bidId) ";
		Number count = (Number) biddingBulletinDao.getCurrentSession().createSQLQuery("select count(*) " + sql).setParameter("bidId", bidId).uniqueResult();
		page.setTotalCount(count.intValue());
		if (count.intValue() == 0)
			return;
		sql = "SELECT name as name, contact_address as contactAddress, corporations as corporations, " +
				"contacts as contacts, mobile as mobile " + sql;
		List<ViewUserSupplier> list = biddingBulletinDao.getCurrentSession().createSQLQuery(sql)
				.setParameter("bidId", bidId).setResultTransformer(Transformers.aliasToBean(ViewUserSupplier.class)).list();
		page.setResult(list);
	}

	/**
	 * 报价
	 * @param invitationId
	 * @param price
	 */
	@Override
	public void saveQuotePrice(String invitationId, float price) {
		Invitation entity = invitationDao.get(Invitation.class, invitationId);
		if(entity == null)
			throw new RuntimeException("报价信息不存在!");
		if(entity.getPrice() != null)
			throw new RuntimeException("不能重复报价!");
		if(entity.getMinAmount() > price)
			throw new RuntimeException("价格不能低于下限金额!");
		entity.setPrice(price);
		invitationDao.update(entity);

		SupplierBid supplierBid = supplierBidDao.get(SupplierBid.class, entity.getSupplierBidUuid());
		supplierBid.setSecondTotalPrice(price + "");
		supplierBidDao.update(supplierBid);
	}

    /**
     * @description 发送招标信息到采招网
     * @author qinj
     * @date 2018/6/23 16:48
     */
    @Override
    public void sendBid(BiddingBulletin biddingBulletin) {
        PurchasePlan plan = purchasePlanDao.get(PurchasePlan.class, biddingBulletin.getPurchasePlanUuid());
        if(!StringUtils.equalsIgnoreCase(plan.getPurchaseMethod(), "公开招标")) {
            // 公开招标
            return ;
        }

        String userName = "中国移动";
        String user_hash_key = "7hy8T063qfi5lMY3kjJvVtD6";


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> map = new HashMap<String, String>();
        //zhanghaitao 采招网测试                Y63qfX@2AtrUOVWGGYhiJy
        String title = biddingBulletin.getName();
        // 招标类型
        String typeId = plan.getPurchaseType();
        PurchaseInfoType type = purchaseInfoTypeDao.get(PurchaseInfoType.class, typeId);

        Base64 base64 = new Base64();
        int p_hash_code = (biddingBulletin.getContent() + title + user_hash_key).hashCode();
        log.info("::title:" + title + " user_hast_key:" + user_hash_key + " p_hast_code:" + p_hash_code );
        String xml = "<tender>"
                + "<bidding_agent>-</bidding_agent>"
                + "<tenderee>北京国能中电节能环保技术股份有限公司</tenderee>"
                + "<title>" + title + "</title>"
                + "<bid_no>" + biddingBulletin.getCode() + "</bid_no>"
                + "<info_class>ZBGG</info_class>"
//                + "<publish_date>" + format.format(new Date()) + "</publish_date>"
                + "<bid_class_id>" + type.getRemark() + "</bid_class_id>"
                + "<bid_class>" + type.getPurType() + "</bid_class>"
                + "<bid_type_id>0</bid_type_id>"
                + "<bid_type>国内招标</bid_type>"
                + "<industry_category>6</industry_category>"
                + "<industry_category_name>环保</industry_category_name>"
                + "<regional_area>1</regional_area>"
                + "<regional_area_name>北京</regional_area_name>"
                + "<capital_source_id>10</capital_source_id>"
                + "<capital_source>其它</capital_source>"
                + "<main_body>" + base64.encodeAsString(biddingBulletin.getContent().getBytes()) + "</main_body>"
                + "<userName>" + userName + "</userName>"
                + "<HashKey>" + p_hash_code + "</HashKey>"
//                + "<attachments>"
//                + "<attachment type=\"doc\" mime=\"application/msword\" name=\"a.doc\"><cdate>" + attachment + "</cdate></attachment>"
//                + "</attachments>"
                + "</tender>";
        map.put("xml", xml);
        String temp = HttpUtils.doPost( "https://jiekou.chinabidding.cn/", map, "UTF-8" );
        log.info("返回的消息是:" + temp);
    }

	@Override
	public void sendSupplierNotice() {
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.DAY_OF_YEAR,3);//当前时间加三天
		rightNow.set(Calendar.HOUR_OF_DAY,0);
		rightNow.set(Calendar.MINUTE,0);
		rightNow.set(Calendar.SECOND,0);
		rightNow.set(Calendar.MILLISECOND,0);
		Date threeDate = rightNow.getTime();
		//查询项目
		List<ViewBiddingBulletin> biddingBulletins = biddingBulletinDao.findBidStartInDate(threeDate);
		if (biddingBulletins==null||biddingBulletins.isEmpty()){
			return;
		}
		//查询报名的供应商
		for(ViewBiddingBulletin view : biddingBulletins){
			List<SupplierSignup> supplierSignups = supplierService.findBulletinSignUp(view.getUuid());
			//TODO 发送消息
			for (SupplierSignup signup :supplierSignups){
				log.info("电话:{},收到:{}项目,{}日期开始投标", new Object[]{signup.getMobile(), view.getProjectName(), view.getBidStartTime()});
			}
		}
	}
	
	public Invitation findInvitation(String invitationId){
		String hql="SELECT min_amount as minAmount,end_date as endDate, down_extent as downExtent ,description as description FROM bs_invitation where uuid =:invitationId";
		Invitation inv = (Invitation)biddingBulletinDao.getCurrentSession().createSQLQuery(hql).setParameter("invitationId", invitationId).setResultTransformer(Transformers.aliasToBean(Invitation.class)).uniqueResult();
		return inv;
	}
}
