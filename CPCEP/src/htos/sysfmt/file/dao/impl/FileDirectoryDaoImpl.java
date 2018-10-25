package htos.sysfmt.file.dao.impl;

import htos.coresys.dao.impl.BaseDaoImpl;
import htos.sysfmt.file.dao.FileDirectoryDao;
import htos.sysfmt.file.entity.FileDirectory;

import java.util.List;

public class FileDirectoryDaoImpl extends BaseDaoImpl<FileDirectory> implements FileDirectoryDao{

	@Override
	public FileDirectory fileDirectory(String fileDirectoryCode) {
		return this.get(FileDirectory.class, fileDirectoryCode);
	}

	@Override
	public List<FileDirectory> fileDirectoryList() {
		String hql = "from FileDirectory d";
		return super.find(hql);
	}


	@Override
	public void updateFileDirectory(FileDirectory fileDirectory) {
		super.update(fileDirectory);
	}

	@Override
	public void deleteFileDirectory(String attr,String value) {
		String hql = "delete from FileDirectory t where t."+attr+"='"+value+"'";
		super.delete(hql);
	}

	@Override
	public void addFileDirectory(FileDirectory fileDirectory) {
		super.save(fileDirectory);
	}

	@Override
	public List<FileDirectory> fileDirectoryList(FileDirectory fileDirectory) {
		String hql = "from FileDirectory d where ";
		String subql="";
		if(fileDirectory.getRelationMenuID()!=null && !fileDirectory.getRelationMenuID().equals("")){
			subql=" d.relationMenuID like '%" + fileDirectory.getRelationMenuID() + "%'";
		}
		
		if(fileDirectory.getLookRolesId()!=null && !fileDirectory.getLookRolesId().equals("")){
			if(!subql.equals("")){ subql+=" and"; }
			subql+=" d.lookRolesId like '%" + fileDirectory.getLookRolesId() + "%'";
		}
		
		if(fileDirectory.getLookNamesId()!=null && !fileDirectory.getLookNamesId().equals("")){
			if(!subql.equals("")){ subql+=" and"; }
			subql+=" d.lookNamesId like '%" + fileDirectory.getLookNamesId() + "%'";
		}
		return super.find(hql+subql);
	}

	/* (non-Javadoc)
	 * @see htos.sysfmt.file.dao.FileDirectoryDao#fileDirectoryList(java.lang.String)
	 */
	@Override
	public List<FileDirectory> fileDirectoryList(String orgIds) {
		String hql = "from FileDirectory d where ";
		if(orgIds.equals("")){
			hql+=" 1=2 ";
		}else{
			hql+=" 1=1 ";
			String[] ids=orgIds.split(",");
			for(int k=0;k<ids.length;k++){
				if(k==0){
					hql+=" and (d.lookNamesId like '%"+ids[k]+"%' ";
				}else{
					hql+=" or d.lookRolesId like '%"+ids[k]+"%' ";
				}
				if((k+1)==ids.length){
					hql+=")";
				}
			}
		}
		
		hql+=" order by createDate asc";
		return super.find(hql);
	}
	
}