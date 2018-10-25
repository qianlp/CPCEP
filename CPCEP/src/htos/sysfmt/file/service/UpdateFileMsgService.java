/**
 * 
 */
package htos.sysfmt.file.service;

import htos.sysfmt.file.entity.UpdateFileMsg;

/**
 * @author 温勋
 * @ClassName : UpdateFileMsgService
 * @Version V1.1
 * @ModifiedBy 温勋
 * @Copyright 华胜龙腾
 * @date 2016年8月29日 下午2:21:41
 */
public interface UpdateFileMsgService {
	void saveFileMsg(UpdateFileMsg updateFileMsg);
	void updateFileMsg(UpdateFileMsg updateFileMsg);
}
