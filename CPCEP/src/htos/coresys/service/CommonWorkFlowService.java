package htos.coresys.service;

public interface CommonWorkFlowService<T> {
	//不带权限、带流程和代办信息保存或修改(综合评审)
    public void saveOrUpdate(T o);
    
    public String save(T o,String rightId);
}
