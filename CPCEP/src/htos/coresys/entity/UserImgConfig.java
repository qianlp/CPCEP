package htos.coresys.entity;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


/**
 * UserConfig entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ht_user_img_config")

public class UserImgConfig  implements java.io.Serializable {


    // Fields    

     private String uuid;
     private String userId;
     private String imgConfig;
     private String status;


    // Constructors

    /** default constructor */
    public UserImgConfig() {
    }

    
    /** full constructor */
    public UserImgConfig(String userId, String imgConfig) {
        this.userId = userId;
        this.imgConfig = imgConfig;
    }

   
    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "uuid", unique = true, nullable = false, length = 64)
    public String getUuid() {
        return this.uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    @Column(name="userId", length=64)

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    @Column(name="imgConfig", length=500)

    public String getImgConfig() {
        return this.imgConfig;
    }
    
    public void setImgConfig(String imgConfig) {
        this.imgConfig = imgConfig;
    }

    @Column(name="status")
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
   

    






}