package htos.business.entity.supplier.view;

/**
 * @author qinj
 * @date 2018-05-12 20:19
 **/
public class ViewScope {

	private String id;
	private String name;
	private boolean used;

	public ViewScope(String id, String name, boolean used) {
		this.id = id;
		this.name = name;
		this.used = used;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
}
