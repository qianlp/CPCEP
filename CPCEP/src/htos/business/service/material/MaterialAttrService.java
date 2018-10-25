package htos.business.service.material;

import htos.business.entity.material.MaterialAttr;
import htos.business.entity.supplier.view.Page;

import java.util.List;

public interface MaterialAttrService {

	void saveDeviceAttr(String planId, String materialId, String deviceAttr, String userId, String supplier);

	void findDeviceAttr(Page<MaterialAttr> page, String planId, String materialId, String userId);

    void updateMaterial(String tempId, String uuid);

	List findDeviceAttrNoPage(String planId, String materialId, String userId);

	void deleteDeviceAttr(String[] ids);
}
