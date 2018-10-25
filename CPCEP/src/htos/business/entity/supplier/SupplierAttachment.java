package htos.business.entity.supplier;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author qinj
 * @date 2018-02-13 21:42
 **/
@Entity
@Table(name = "bs_supplier_attachment")
public class SupplierAttachment {

	private String uuid;
	private String fileName;
	private String size;
	private Date uploadTime;
	private String newFileName;
	private String filePath;
	private Short type; // 15:供应商质疑文件，16:标办针对供应商质疑反馈文件
	private SupplierExt supplier;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "uuid", unique = true, nullable = false, length = 64)
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Basic
	@Column(name = "file_name")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Basic
	@Column(name = "size")
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Basic
	@Column(name = "upload_time")
	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	@Basic
	@Column(name = "new_file_name")
	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	@Basic
	@Column(name = "file_path")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Basic
	@Column(name = "type")
	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@ManyToOne
	@JoinColumn(name = "supplier")
	public SupplierExt getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierExt supplier) {
		this.supplier = supplier;
	}
}
