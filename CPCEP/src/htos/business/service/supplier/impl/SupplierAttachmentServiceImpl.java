package htos.business.service.supplier.impl;

import htos.business.dao.supplier.SupplierAttachmentDao;
import htos.business.entity.supplier.SupplierAttachment;
import htos.business.service.supplier.SupplierAttachmentService;
import htos.common.util.DateUtil;
import htos.sysfmt.file.action.AdenexaAction;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author qinj
 * @date 2018-02-13 21:49
 **/
public class SupplierAttachmentServiceImpl implements SupplierAttachmentService {

	private SupplierAttachmentDao supplierAttachmentDao;

	/**
	 * @description 保存供应商附件
	 * @author qinj
	 * @date 2018/2/13 21:58
	 */
	@Override
	public SupplierAttachment save(short type, String fileName, File file) throws IOException {
		String subPath = DateUtil.DateConversion8String(new Date());
		String path = AdenexaAction.FILE_PATH + "/" + subPath + "/";// 目标路径
		String filenameNew = DateUtil.DateConversion14String(new Date())
				+ DateUtil.getDatecurrentTime()
				+ fileName.substring(fileName.lastIndexOf("."));// 文件名
		File savefile = new File(new File(path), filenameNew);
		if (!savefile.getParentFile().exists()) {
			savefile.getParentFile().mkdirs();
		}
		FileUtils.copyFile(file, savefile);
		String size = convertFileSize(FileUtils.sizeOf(file));

		SupplierAttachment entity = new SupplierAttachment();
		entity.setFileName(fileName);
		entity.setSize(size);
		entity.setNewFileName(filenameNew);
		entity.setType(type);
		entity.setUploadTime(new Date());
		entity.setFilePath(path + filenameNew);
		supplierAttachmentDao.save(entity);
		return entity;
	}

	@Override
	public SupplierAttachment getAttachment(String id) {
		return supplierAttachmentDao.get(SupplierAttachment.class, id);
	}

	public void setSupplierAttachmentDao(SupplierAttachmentDao supplierAttachmentDao) {
		this.supplierAttachmentDao = supplierAttachmentDao;
	}

	@Override
	public List<SupplierAttachment> findBySupplier(String supplierId) {
		String hql = "from SupplierAttachment where supplier.uuid = ?";
		List<SupplierAttachment> list = supplierAttachmentDao.find(hql, new Object[]{supplierId});
		return list;
	}

	private String convertFileSize(long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;

		if (size >= gb) {
			return String.format("%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else
			return String.format("%d B", size);
	}
}
