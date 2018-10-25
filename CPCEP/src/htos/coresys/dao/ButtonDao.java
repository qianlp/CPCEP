package htos.coresys.dao;

import htos.coresys.entity.Button;

import java.util.List;


/** 
 *  
 *  
 */

public interface ButtonDao extends BaseDao<Button> {

	public List<Button> findAllButton();
}