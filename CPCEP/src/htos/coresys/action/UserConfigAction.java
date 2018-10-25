package htos.coresys.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import htos.coresys.entity.UserImgConfig;
import htos.coresys.service.CommonService;
import htos.coresys.util.CommonUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserConfigAction extends ActionSupport implements ModelDriven<UserImgConfig> {

	private CommonService<UserImgConfig> commonService;
	private UserImgConfig userImgConfig;
	private File userImg;
	private String userImgFileName;
	
	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("UploadPath");
	public final static  String FILE_PATH=getSessionattachmenttitle("FILE_PATH");//文件上传路径
	
	
	public void imgConfigSave(){
		try {
			String userId=ServletActionContext.getRequest().getParameter("userId");
			List<UserImgConfig> list=getImgList(userId);
			for(UserImgConfig img:list){
				if(img.getStatus().equals("1")){
					img.setStatus("0");
					commonService.update(img);
				}
			}
			
            //文件存储的路径  
            String storePath = FILE_PATH+"/userImg";
            
            if (userImg != null) {
            	String fileName = UUID.randomUUID()+userImgFileName.substring(userImgFileName.lastIndexOf("."));
            	String folder=storePath+"/"+userId;
            	
            	System.out.println(folder);
            	File savefile = new File(new File(folder), fileName);
                if (!savefile.getParentFile().exists())
                    savefile.getParentFile().mkdirs();
                FileUtils.copyFile(userImg, savefile);
                
                userImgConfig.setUserId(userId);
    			userImgConfig.setImgConfig("userImg/"+userImgConfig.getUserId()+"/"+fileName);
    			userImgConfig.setStatus("1");
    			commonService.save(userImgConfig).toString();
    			ActionContext.getContext().getSession().put("imgPath", "userImg/"+userImgConfig.getUserId()+"/"+fileName);
    			
    			PrintWriter out = ServletActionContext.getResponse().getWriter();
    			out.println("<script>parent.closeImgWin('/upload/userImg/"+userImgConfig.getUserId()+"/"+fileName+"')</script>");
            }
            
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showPicture(){
		String userId=ServletActionContext.getRequest().getParameter("userId");
		String uuid=ServletActionContext.getRequest().getParameter("uuid");
		
		String imgPath="";
		if(userId!=null && !userId.equals("")){
			List<UserImgConfig> imgList=commonService.getListByProperty("UserImgConfig", "userId", userId);
			for(UserImgConfig img:imgList){
				if(img.getStatus().equals("1")){
					imgPath=img.getImgConfig();
					break;
				}
			}
		}else{
			userImgConfig=commonService.getEntityByID("UserImgConfig", uuid);
			imgPath=userImgConfig.getImgConfig();
		}
		File file = new File(FILE_PATH+"/"+imgPath);
		if (!file.exists()) {
			System.out.println("找不到文件[" +FILE_PATH+"/"+imgPath+ "]");
			return;
		}        
		ServletActionContext.getResponse().setContentType("multipart/form-data");
		InputStream reader = null;
		try {
			reader = new FileInputStream(file);
			byte[] buf = new byte[1024];
			int len = 0;            
			OutputStream out = ServletActionContext.getResponse().getOutputStream();
			while ((len = reader.read(buf)) != -1) {
				out.write(buf, 0, len);            
			}            
			out.flush();        
		} catch (Exception ex) {
			System.out.println("显示图片时发生错误:" + ex.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception ex) {
					System.out.println("关闭文件流出错");
				}            
			}       
		}    
	}
	
	public void imgConfigUpdate(){
		String userId=ServletActionContext.getRequest().getParameter("userId");
		String uuid=ServletActionContext.getRequest().getParameter("uuid");
		List<UserImgConfig> list=getImgList(userId);
		for(UserImgConfig img:list){
			if(img.getStatus().equals("1")){
				img.setStatus("0");
				commonService.update(img);
			}
		}
		userImgConfig=commonService.getEntityByID(userImgConfig.getClass().getSimpleName(), uuid);
		userImgConfig.setStatus("1");
		commonService.update(userImgConfig);
		ActionContext.getContext().getSession().put("imgPath", userImgConfig.getImgConfig());
		
	}
	
	public void imgConfigDel(){
		userImgConfig.setUuid(ServletActionContext.getRequest().getParameter("uuid"));
		commonService.delete(userImgConfig);
	}
	
	public void imgConfigList(){
		String userId=ServletActionContext.getRequest().getParameter("userId");
		CommonUtil.toJsonStr(ServletActionContext.getResponse(), commonService.getListByProperty(userImgConfig.getClass().getSimpleName(), "userId", userId));
	}
	
	private List<UserImgConfig> getImgList(String userId){
		return commonService.getListByProperty(userImgConfig.getClass().getSimpleName(), "userId", userId);
	}
	
	
	//路径获取
	public static final String getSessionattachmenttitle(String sessionName) {
		return bundle.getString(sessionName);
	}

	public CommonService<UserImgConfig> getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService<UserImgConfig> commonService) {
		this.commonService = commonService;
	}

	
	public UserImgConfig getUserImgConfig() {
		return userImgConfig;
	}

	public void setUserImgConfig(UserImgConfig userImgConfig) {
		this.userImgConfig = userImgConfig;
	}

	
	public File getUserImg() {
		return userImg;
	}

	public void setUserImg(File userImg) {
		this.userImg = userImg;
	}
	
	

	public String getUserImgFileName() {
		return userImgFileName;
	}

	public void setUserImgFileName(String userImgFileName) {
		this.userImgFileName = userImgFileName;
	}

	@Override
	public UserImgConfig getModel() {
		if(userImgConfig==null){
			userImgConfig=new UserImgConfig();
		}
		return userImgConfig;
	}
	
}
