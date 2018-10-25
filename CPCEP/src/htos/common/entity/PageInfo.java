package htos.common.entity;

/**
 * 
 * @describe : 分页器类
 * @author : pangchengxiang
 * @version : v1.0
 */
public class PageInfo {
	private int pageSize;		// 每页条数
	private int pageIndex;		// 当前页码
	private String sortField;	//排序字段
	private String sortOrder;	//排序顺序 (desc/asc)
	private int totalPage;		// 总页数
	private int totalCount;		// 符合条件的总条数
	private int firstResult;	// 从那条开始查

	public PageInfo(int pageIndex, int pageSize) {
		this.pageSize = pageSize;
		this.pageIndex = pageIndex;
		this.firstResult = (this.pageIndex - 1) * this.pageSize;
	}
	
	public PageInfo(int pageIndex, int pageSize,String sortField,String sortOrder) {
		this.pageSize = pageSize;
		this.pageIndex = pageIndex;
		this.sortField = sortField;
		this.sortOrder = sortOrder;
		this.firstResult = (this.pageIndex - 1) * this.pageSize;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		this.totalPage = this.totalCount % this.pageSize == 0 ? this.totalCount
				/ this.pageSize : this.totalCount / this.pageSize + 1;
	}

	public PageInfo() {
		super();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if(pageSize>1){
			this.pageSize = pageSize;
		}else{
			this.pageSize = 1;
		}
		
	}

	public int getpageIndex() {
		return pageIndex;
	}

	public void setpageIndex(int pageIndex) {
		if(pageIndex>0){
			this.pageIndex = pageIndex;
		}else{
			this.pageIndex = 0;
		}
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		if(sortOrder=="desc"){
			this.sortOrder="desc";
		}else if(sortOrder=="asc"){
			this.sortOrder="asc";
		}else{
			this.sortOrder="";
		}
	}
}
