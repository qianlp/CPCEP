<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>

<script src="../js/ckeditor/ckeditor.js" type="text/javascript"></script>
<script src="../js/ckeditor/config.js" type="text/javascript"></script>
<script type="text/javascript">

	var gForm = document.forms[0];
	var gCkEditor = null;
	
	function afterLoad(){
		mini.parse();
		//mini.getbyName("cby").setValue($("[name=createBy]").val());
		initCkEditor();
		$("#btnContR").hide();
	}
	
	function initCkEditor(){
	/* 	if (gIsRead || gLoginUser!=$("[name=createBy]").val()) {
			CKEDITOR.config.readOnly = true;
		} */
		if (gCkEditor == null) {
			gCkEditor = CKEDITOR.appendTo('editor', {
				customConfig : 'ckeditor_config_QC.js',
				customConfig : 'ckeditor_config.js',
				on : {
					instanceReady : function(ev) {
						
						gCkEditor.setData(gForm.message.value);
					}
				}
			});
		}
	}

</script>
<style>
#gTab .mini-panel-border {
	border: 0px;
}
</style>

<div style="display:none">
<textarea name="message" id="message" value="${comObj.message}">${comObj.message}</textarea>
</div>
<div class="col-md-7" name="FormMode" id="FormMode"
	style="width:100%;margin:auto;float:none;">
	<div class="widget-container fluid-height col-md-7-k"
		style="height:auto;border-radius:4px;">
		<div class="mbox-header">
			<span class="form-title"
				style="height:100%;line-height:45px;text-align:center;"> 定标内容
			</span>
		</div>

		<div class="mbox-body" id="mbox-body"
			style="height:100%;padding:10px;">
			<table cellspacing="1" cellpadding="1" id="tab"
				style="width:90%;">
				 <tr>
                                <td class="td_left">招标编号：</td>
                                <td class="td_right">
                                    <input name="biddingBulletinCode" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true"
                                           value="${comObj.biddingBulletinCode}"/>
                                </td>
                                <td class="td_left">招标名称：</td>
                                <td class="td_right">
                                    <input name="biddingBulletinName" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true"
                                           value="${comObj.biddingBulletinName}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_left">定标时间：</td>
                                <td class="td_right">
                           
                                        <input name="confirmTime" allowInput="false" readonly="true"
                                               value="${comObj.confirmTime}" class="mini-datepicker"
                                               style="width:99%;" title="定标时间">
                                
                                </td>
                                <td class="td_left">招标文件版本号：</td>
                                <td class="td_right">
                                    <input name="biddingFileReleaseVersion" class="mini-textbox" style="width:99%;"
                                           allowInput="false" readonly="true" value="${comObj.biddingFileReleaseVersion}"/>
                                </td>
			</table>
		</div>
	</div>

	<br />
	<div class="widget-container fluid-height col-md-7-k"
		style="height:auto;border-radius:4px;">
		<div class="mbox-header">
			<span class="form-title"
				style="height:100%;line-height:45px;text-align:center;">定标通知信息</span>
		</div>
		<div class="mbox-body" id="mbox-body" style="padding:5px;width:100%">
			<table cellspacing="1" cellpadding="1" id="tab"
				style="width:100%;margin:0 auto;">
				<tr>
					<td class="td_left" colspan=4>
						<div id='editor'
							style="overflow:hidden;"></div>
						<div id='read'
							style='color:black;padding-left:15px;width:90%;display:none'>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>



