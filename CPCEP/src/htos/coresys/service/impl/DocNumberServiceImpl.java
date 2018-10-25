package htos.coresys.service.impl;

import java.util.Calendar;
import java.util.Date;

import htos.coresys.dao.DocNumberDao;
import htos.coresys.entity.DocNumber;
import htos.coresys.service.DocNumberService;


public class DocNumberServiceImpl implements DocNumberService {

	private DocNumberDao docNumberDao;

	@Override
	public String findNumberByMode(String modeName) {
		Calendar a=Calendar.getInstance();
		DocNumber docNumber=docNumberDao.findNumberByMode(modeName, String.valueOf(a.get(Calendar.YEAR)));
		if(docNumber==null){
			return "1";
		}
		return String.valueOf(docNumber.getDocNo()+1);
	}
	
	@Override
	public String findNumberByMID(String id) {
		Calendar a=Calendar.getInstance();
		DocNumber docNumber=docNumberDao.findNumberByMode(id);
		if(docNumber==null){
			return "1";
		}
		return String.valueOf(docNumber.getDocNo()+1);
	}
	
	@Override
	public String checkNumberByMID(String id){
		Calendar a=Calendar.getInstance();
		DocNumber docNumber=docNumberDao.findNumberByMode(id);
		String docNo="";
		if(docNumber==null){
			docNo="1";
		}else{
			docNo=String.valueOf(docNumber.getDocNo()+1);
		}
		if(docNumber==null){
			docNumber=new DocNumber();
			docNumber.setCreateDate(new Date());
			docNumber.setYear(String.valueOf(a.get(Calendar.YEAR)));
			docNumber.setModeName(id);
			docNumber.setDocNo(Integer.parseInt(docNo));
			String uuid=docNumberDao.save(docNumber).toString();
		}else{
			docNumber.setDocNo(Integer.parseInt(docNo));
			docNumberDao.update(docNumber);
		}
		return docNo;
	}
	
	public String checkNumber(String modeName){
		Calendar a=Calendar.getInstance();
		DocNumber docNumber=docNumberDao.findNumberByMode(modeName, String.valueOf(a.get(Calendar.YEAR)));
		String docNo="";
		String bDocNo="";
		if(docNumber==null){
			docNo="1";
		}else{
			docNo=String.valueOf(docNumber.getDocNo()+1);
		}
		if(docNo.length()==1){
			bDocNo="00"+docNo;
		}else if(docNo.length()==2){
			bDocNo="0"+docNo;
		}else{
			bDocNo=docNo;
		}
		bDocNo=String.valueOf(a.get(Calendar.YEAR))+"-"+bDocNo;
		
		if(docNumber==null){
			docNumber=new DocNumber();
			docNumber.setCreateDate(new Date());
			docNumber.setYear(String.valueOf(a.get(Calendar.YEAR)));
			docNumber.setModeName(modeName);
			docNumber.setDocNo(Integer.parseInt(docNo));
			String uuid=docNumberDao.save(docNumber).toString();
		}else{
			docNumber.setDocNo(Integer.parseInt(docNo));
			docNumberDao.update(docNumber);
		}
		return bDocNo;
	}
	
	public String checkNumberFour(String modeName){
		Calendar a=Calendar.getInstance();
		DocNumber docNumber=docNumberDao.findNumberByMode(modeName, String.valueOf(a.get(Calendar.YEAR)));
		String docNo="";
		String bDocNo="";
		if(docNumber==null){
			docNo="1";
		}else{
			docNo=String.valueOf(docNumber.getDocNo()+1);
		}
		if(docNo.length()==1){
			bDocNo="000"+docNo;
		}else if(docNo.length()==2){
			bDocNo="00"+docNo;
		}else if(docNo.length()==3){
			bDocNo="0"+docNo;
		}else{
			bDocNo=docNo;
		}
		bDocNo=String.valueOf(a.get(Calendar.YEAR))+"-"+bDocNo;
		
		if(docNumber==null){
			docNumber=new DocNumber();
			docNumber.setCreateDate(new Date());
			docNumber.setYear(String.valueOf(a.get(Calendar.YEAR)));
			docNumber.setModeName(modeName);
			docNumber.setDocNo(Integer.parseInt(docNo));
			String uuid=docNumberDao.save(docNumber).toString();
		}else{
			docNumber.setDocNo(Integer.parseInt(docNo));
			docNumberDao.update(docNumber);
		}
		return bDocNo;
	}
	
	
	public String checkProjectNumber(String modeName,String docNum){
		Calendar a=Calendar.getInstance();
		DocNumber docNumber=docNumberDao.findNumberByMode(modeName, String.valueOf(a.get(Calendar.YEAR)));
		String docNo="";
		String bDocNo="";
		if(docNumber==null){
			docNo="1";
		}else{
			docNo=String.valueOf(docNumber.getDocNo()+1);
		}
		if(docNo.length()==1){
			bDocNo="00"+docNo;
		}else if(docNo.length()==2){
			bDocNo="0"+docNo;
		}else{
			bDocNo=docNo;
		}
		String[] arrStr=docNum.split("-");
		docNum="";
		for(int k=0;k<arrStr.length;k++){
			if(!docNum.equals("")){
				docNum+="-";
			}
			if(k==1){
				docNum+=bDocNo;
			}else{
				docNum+=arrStr[k];
			}
		}
		bDocNo=String.valueOf(a.get(Calendar.YEAR))+"-"+bDocNo;
		
		if(docNumber==null){
			docNumber=new DocNumber();
			docNumber.setCreateDate(new Date());
			docNumber.setYear(String.valueOf(a.get(Calendar.YEAR)));
			docNumber.setModeName(modeName);
			docNumber.setDocNo(Integer.parseInt(docNo));
			docNumberDao.save(docNumber);
		}else{
			docNumber.setDocNo(Integer.parseInt(docNo));
			docNumberDao.update(docNumber);
		}
		return docNum;
	}
	


	public DocNumberDao getDocNumberDao() {
		return docNumberDao;
	}


	public void setDocNumberDao(DocNumberDao docNumberDao) {
		this.docNumberDao = docNumberDao;
	}


	
	
}
