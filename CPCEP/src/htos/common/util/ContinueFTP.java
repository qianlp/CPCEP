package htos.common.util;

import htos.common.util.swftools.SwfToolsUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
/**
 * Ftp操作（上传、下载、删除）
 * @author zcl
 *
 */
 
public class ContinueFTP {
	
	private final static Logger log = Logger.getLogger(ContinueFTP.class);
    public static FTPClient ftpClient = new FTPClient();  
     //读取ftp路径\用户名\密码\端口号\
    public final static  String HOSTNAME=ResourceUtil.getSessionattachmenttitle("ftp_hostname");
    public final static  Integer PORT=Integer.valueOf(ResourceUtil.getSessionattachmenttitle("ftp_port"));
    public final static  String USERNAME=ResourceUtil.getSessionattachmenttitle("ftp_username");
    public final static  String PASSWORD=ResourceUtil.getSessionattachmenttitle("ftp_password");
//    public ContinueFTP(){
//        //设置将过程中使用到的命令输出到控制台  
//        this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));  
//    }
     
    public static boolean connect() throws IOException{  
        ftpClient.connect(HOSTNAME, PORT);  
        ftpClient.setControlEncoding("GBK");  
        if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){  
            if(ftpClient.login(USERNAME, PASSWORD)){
                return true;  
            }  
        }  
        disconnect();
        return false;  
    }  
    
    //ftp上传操作
    public static String upload(String path,String filenameNew,String local) throws IOException{
    	String remote = path+"/"+filenameNew;
        //设置PassiveMode传输  
        ftpClient.enterLocalPassiveMode();  
        //设置以二进制流的方式传输  
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);  
        ftpClient.setControlEncoding("GBK");  
        //取得文件大小
        InputStream is = new FileInputStream(local);
        int size = is.available();//文件字节数
		int sizeM = size/1024;
		String msize = String.valueOf(sizeM);
        //对远程目录的处理  
        String remoteFileName = remote;
        if(remote.contains("/")){  
            remoteFileName = remote.substring(remote.lastIndexOf("/")+1);  
            //创建服务器远程目录结构，创建失败直接返回  
            if(CreateDirecroty(remote, ftpClient)==UploadStatus.Create_Directory_Fail){
            	log.error("ftp上传upload创建目录失败========================");
                return "";//失败返回空值
            }  
        }  
        UploadStatus result = uploadFile(remoteFileName, new File(local), ftpClient,filenameNew);
         
        return UploadStatus.Upload_New_File_Success.equals(result)?msize:"";  
    }  
     
    public static void disconnect() throws IOException{  
        if(ftpClient.isConnected()){  
            ftpClient.disconnect();  
        }  
    }  
      
    //创建远程目录
    public static UploadStatus CreateDirecroty(String remote,FTPClient ftpClient) throws IOException{  
        UploadStatus status = UploadStatus.Create_Directory_Success;  
        String directory = remote.substring(0,remote.lastIndexOf("/")+1);  
        if(!directory.equalsIgnoreCase("/")&&!ftpClient.changeWorkingDirectory(new String(directory.getBytes("GBK"),"iso-8859-1"))){  
            //如果远程目录不存在，则递归创建远程服务器目录  
            int start=0;  
            int end = 0;  
            if(directory.startsWith("/")){  
                start = 1;  
            }else{  
                start = 0;  
            }  
            end = directory.indexOf("/",start);
            while(true){  
                String subDirectory = new String(remote.substring(start,end).getBytes("GBK"),"iso-8859-1");  
                if(!ftpClient.changeWorkingDirectory(subDirectory)){  
                    if(ftpClient.makeDirectory(subDirectory)){  
                        ftpClient.changeWorkingDirectory(subDirectory);
                        log.info("=========================UploadStatus=====>CreateDirecroty创建目录成功=====================");
                    }else {  
                    	log.error("=========================UploadStatus=====>CreateDirecroty创建目录失败====================="); 
                        return UploadStatus.Create_Directory_Fail;  
                    }  
                }  
                start = end + 1;  
                end = directory.indexOf("/",start);  
                  
                //检查所有目录是否创建完毕  
                if(end <= start){  
                    break;  
                }  
            }  
        }  
        return status;  
    }  
      
     
    public static UploadStatus uploadFile(String remoteFile,File localFile,FTPClient ftpClient,String filenameNew) throws IOException{  
        UploadStatus status;
        //显示进度的上传  
        long step = localFile.length() / 100;  
        long process = 0;  
        long localreadbytes = 0L;  
        RandomAccessFile is = new RandomAccessFile(localFile,"r");
        OutputStream out = ftpClient.appendFileStream(new String(remoteFile.getBytes("GBK"),"iso-8859-1"));
        byte[] bytes = new byte[1024];  
        int c;  
        while((c = is.read(bytes))!= -1){  
            out.write(bytes,0,c);  
            localreadbytes+=c;  
            if(localreadbytes / step != process){  
                process = localreadbytes / step;  
                System.out.println("后台上传进度:" + process);  
            }  
        }  
        out.flush();  
        is.close();  
        out.close();  
        boolean result =ftpClient.completePendingCommand();  
        status = result?UploadStatus.Upload_New_File_Success:UploadStatus.Upload_New_File_Failed;
        if(result){
        	//生成pdf和swf文件
			SwfToolsUtil.convert2SWF(remoteFile.substring(0,remoteFile.lastIndexOf("/"))+filenameNew);
			log.info("ftp上传convert2SWF将文件转换成swf成功========================");
        }
        return status;  
    }  
      
    //ftp下载操作
    public DownloadStatus download(String fileName,String remote,HttpServletResponse response) throws IOException{
        //设置被动模式  
        ftpClient.enterLocalPassiveMode();  
        //设置以二进制方式传输  
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);  
        DownloadStatus result;  
          
        //检查远程文件是否存在  
        FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("GBK"),"iso-8859-1"));  
        if(files.length != 1){
        	log.error("ftp下载download远程文件不存在========================");
            System.out.println("远程文件不存在");  
            return DownloadStatus.Remote_File_Noexist;  
        }
        fileName = new String(fileName.getBytes("GBK"),"ISO8859-1");
        //设置输出格式
		response.reset();
		response.setContentType("bin");
		response.addHeader("Content-Disposition", "attachment;filename="+fileName); 
        long lRemoteSize = files[0].getSize();  
          InputStream in= ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"),"iso-8859-1"));  
            byte[] bytes = new byte[1024];  
            long step = lRemoteSize /100;  
            long process=0;  
            long localSize = 0L;  
            int c;  
            while((c = in.read(bytes))!= -1){
            	response.getOutputStream().write(bytes, 0, c);
                localSize+=c;  
                long nowProcess = localSize /step;  
                if(nowProcess > process){  
                    process = nowProcess;  
                    if(process % 10 == 0)  
                        System.out.println("下载进度："+process);  
                }  
            }  
			//关闭流
            in.close();
            response.getOutputStream().close();
            
            boolean upNewStatus = ftpClient.completePendingCommand();
            if(upNewStatus){  
                result = DownloadStatus.Download_New_Success;
                log.info("ftp下载download下载成功========================");
            }else {  
                result = DownloadStatus.Download_New_Failed;
                log.error("ftp下载download下载失败========================");
            }  
        return result;  
    } 
    
    /**
     * ftp删除操作
     * @param fileName
     */
    public static void deleteFile(String remote){
    	try {
			boolean flag = ftpClient.deleteFile(new String(remote.getBytes("GBK"),"iso-8859-1"));
			if(flag){
				log.info("ftp删除deleteFile删除成功========================");
			}else{
				log.error("ftp删除deleteFile删除失败========================");
			}
		} catch (IOException e) {
			log.error("ftp删除deleteFile删除失败========================",e);
			e.printStackTrace();
		}
    	
    }
    
   /* public static void main(String[] args) {  
        ContinueFTP myFtp = new ContinueFTP();  
        try {  
            myFtp.connect("localhost", 21, "123", "123");  
//          myFtp.ftpClient.makeDirectory(new String("电视剧".getBytes("GBK"),"iso-8859-1"));  
//          myFtp.ftpClient.changeWorkingDirectory(new String("电视剧".getBytes("GBK"),"iso-8859-1"));  
//          myFtp.ftpClient.makeDirectory(new String("走西口".getBytes("GBK"),"iso-8859-1"));  
//          System.out.println(myFtp.upload("http://www.5a520.cn /yw.flv", "/yw.flv",5));  
          System.out.println(myFtp.upload("D:/ziliao/MyEclipse全面详解.pdf", "D:/dd/MyEclipse全面详解.pdf"));  
   //         System.out.println(myFtp.download("/央视走西口/新浪网/走西口24.mp4", "E:\\走西口242.mp4"));  
            myFtp.disconnect();  
        } catch (IOException e) {  
            System.out.println("连接FTP出错："+e.getMessage());  
        }  
    } */ 
} 