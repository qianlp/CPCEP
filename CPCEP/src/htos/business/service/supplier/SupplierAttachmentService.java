package htos.business.service.supplier;

import htos.business.entity.supplier.SupplierAttachment;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface SupplierAttachmentService {

	SupplierAttachment save(short type, String fileName, File file) throws IOException;

	SupplierAttachment getAttachment(String id);

	List<SupplierAttachment> findBySupplier(String supplierId);

}
