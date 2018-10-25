package htos.coresys.service.impl;

import htos.coresys.dao.ButtonDao;
import htos.coresys.entity.Button;
import htos.coresys.service.ButtonService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ButtonServiceImpl implements ButtonService {
	private ButtonDao buttonDao;
	
	@Override
	public void saveButton(Button button){
		buttonDao.save(button);
	}
	
	@Override
	public List<Button> findAllButton() {
		return buttonDao.findAllButton();
	}
	
	@Override
	public List<Map<String, String>> dataList(Collection<Button> buttonList) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (Button b : buttonList) {
			map = new HashMap<String, String>();
			map.put("id", b.getUuid());
			map.put("text", b.getBtnName());
			map.put("type", Button.class.getSimpleName());
			list.add(map);
		}
		return list;
	}
	
	public ButtonDao getButtonDao() {
		return buttonDao;
	}

	public void setButtonDao(ButtonDao buttonDao) {
		this.buttonDao = buttonDao;
	}

	@Override
	public void updateButtonJson(Button buttonModel) {
		buttonDao.update(buttonModel);
	}

	
	
}
