package htos.business.dao.bidFileRelease.impl;

import htos.business.dao.bidFileRelease.BidFileQuestionDao;
import htos.business.dao.bidFileRelease.BidFileReleaseDao;
import htos.business.dto.PurchaseMaterialParamInfo;
import htos.business.entity.biddingFile.BidFileQuestion;
import htos.business.entity.biddingFile.BiddingFileRelease;
import htos.coresys.dao.impl.BaseDaoImpl;
import htos.coresys.entity.User;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.transform.Transformers;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class BidFileQuestionDaoImpl extends BaseDaoImpl<BidFileQuestion> implements BidFileQuestionDao {

}