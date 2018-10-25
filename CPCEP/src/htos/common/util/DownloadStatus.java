package htos.common.util;


public enum DownloadStatus {
		Remote_File_Noexist,    //远程文件不存在   
	    Local_Bigger_Remote,    //本地文件大于远程文件   
	    Download_New_Success,   //全新下载文件成功   
	    Download_New_Failed;    //全新下载文件失败   
}
