package htos.business.service.bid;

import htos.business.entity.bid.TechEvalClarify;
import htos.business.entity.supplier.view.Page;
import htos.coresys.entity.User;

public interface TechEvalClarifyService {

    void findPage(User user, Page<TechEvalClarify> page);
}
