package htos.coresys.dao.impl;

import htos.coresys.dao.ButtonDao;
import htos.coresys.entity.Button;

import java.util.List;


public class ButtonDaoImpl extends BaseDaoImpl<Button> implements ButtonDao {

	@Override
	public List<Button> findAllButton() {
		// TODO Auto-generated method stub
		String hql="from Button";
		return super.find(hql);
	}

	
}