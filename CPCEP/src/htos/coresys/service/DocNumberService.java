package htos.coresys.service;

import htos.coresys.entity.DocNumber;


public interface DocNumberService {

	public String checkNumber(String modeName);
	public String checkNumberFour(String modeName);
	public String checkProjectNumber(String modeName,String docNum);

	public String findNumberByMode(String modeName);
	/**
	 * @param id
	 * @return
	 */
	String findNumberByMID(String id);
	/**
	 * @param id
	 * @return
	 */
	String checkNumberByMID(String id);
}
