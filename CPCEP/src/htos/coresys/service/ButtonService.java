package htos.coresys.service;

import htos.coresys.entity.Button;

import java.util.Collection;
import java.util.List;
import java.util.Map;




public interface ButtonService {
	public void saveButton(Button button);
	public List<Button> findAllButton();
	public List<Map<String, String>> dataList(Collection<Button> buttonList);
	public void updateButtonJson(Button buttonModel);
	
}