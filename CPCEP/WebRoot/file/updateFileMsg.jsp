<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script>
	
	var gArrbtn=[
             {id:"btnSave",text:"保&nbsp;&nbsp;存",style:"btn-primary",align:"right",event:"toSave()"},
             {id:"btnOver",text:"同&nbsp;&nbsp;意",style:"btn-success",align:"right",event:"toOver()"},
             {id:"btnClose",text:"关&nbsp;&nbsp;闭",style:"btn-default",align:"right",event:"toClose()"}
	];
	gIsShowSign=1;
	function toSave(){
		if(mini.getbyName("warnBy").getValue()==""){
			mini.alert("请先选择提醒人！");
			return;
		}
		$("[name=wfStatus]").val("1");
		$("[name=msgTitle]").val("项目："+mini.getbyName("projectNo").getValue()+" 文件补充更新需要您处理！");
		$("[name=curUser]").val(mini.getbyName("warnBy").getText());
		$("[name=userId]").val(mini.getbyName("warnBy").getValue());
		if($("[name=msgStatus]").val()==""){
			$("[name=msgStatus]").val("已发送");
		}
		document.forms[0].submit();
	}
	
	function toOver(){
		if(mini.get("att_att").getData().length==0){
			mini.alert("请先上传附件！");
			return;
		}
		$("[name=wfStatus]").val("2");
		$("[name=curUser]").val($("[name=createBy]").val());
		$("[name=msgTitle]").val("您发给["+mini.getbyName("warnBy").getText()+"]的文件补充更新已完成！");
		$("[name=msgStatus]").val("已更新");
		document.forms[0].submit();
	}
	
	function wfSubmitDoc(){
		if(mini.getbyName("warnBy").getValue()==""){
			mini.alert("请先选择提醒人！");
			return;
		}
		$("[name=msgTitle]").val("项目："+mini.getbyName("projectNo").getValue()+" 文件补充更新需要您处理！");
		$("[name=curUser]").val(mini.getbyName("warnBy").getText());
		$("[name=userId]").val(mini.getbyName("warnBy").getValue());
		if($("[name=msgStatus]").val()==""){
			$("[name=msgStatus]").val("已发送");
		}
		gIsAppointUser=1;
		if(arguments.length==1){gWQSagent=arguments[0]}
		wfSubDocStart();
	}
	
	function wfAgreeDoc(){
		//提交前处理数据
		if(confirm("您确定提交吗？")){
			//$("[name=curUser]").val($("[name=createBy]").val());
			//$("[name=msgTitle]").val("您发给["+mini.getbyName("warnBy").getText()+"]的文件补充更新已完成！");
			$("[name=msgStatus]").val("已更新");
			
			if(arguments.length==1){gWQSagent=arguments[0]}
			gForm.wfTacheName.value="同意";
			wfSubDocStart();
		}
	}
	
</script>
<div style="display:none">
	<input name="prjID"   	value="${comObj.prjID}" />
	<input name="pid"   	value="${comObj.pid}" />
	<input name="msgStatus" value="${comObj.msgStatus}" />
	<input name="curFj" value="${comObj.curFj}" />
	<input name="fjName" value="${comObj.fjName}" />
	<input name="status" />
</div>
<div class="col-md-7" name="FormMode" id="FormMode"
		style="width:100%;margin:auto;float:none;">
		<div class="widget-container fluid-height col-md-7-k"
			style="height:auto;border-radius:4px;">
			<div class="mbox-header">
				<span class="form-title" style="height:100%;line-height:45px;">
					 文档补充更新提醒
				</span>
			</div>
			<div class="mbox-body" style="height:100%;padding:10px;">
				<table cellspacing="1" cellpadding="1" id="tab"
					style="width:90%;">
					<tr>
						<td class="td_left">项目编号：</td>
						<td class="td_right"><input name="projectNo"  value="${comObj.projectNo}" text="${comObj.projectNo}" 
							class="mini-textbox" allowInput="false" required="true"
							style="width:99%;" title="项目编号"></td>
						<td class="td_left">项目名称：</td>
						<td class="td_right"><input name="projectName"  value="${comObj.projectName}"
							class="mini-textbox" allowInput="false" required="true"
							style="width:99%;" title="项目名称"></td>
					</tr>
					<tr>
						<td class="td_left">提醒人：</td>
						<td class="td_right">
							<input name="warnBy" multiSelect="false"
							class="mini-treeselect" required="true"  value="${comObj.warnBy}" 
							url="${pageContext.request.contextPath}/admin/findOrgTree.action" showClose="true"
							oncloseclick="onCloseClick" textField='text' valueField='id'
							style='width:99%;'  title="提醒人"></td>
						<td class="td_left">当前附件：</td>
						<td class="td_right" id="curFJ" colspan="3"></td>
					</tr>
					<tr>
					<td class="td_left">文档类型：</td>
					<td class="td_right"><input name="docTypeId" value="${comObj.docTypeId}"
					class="mini-treeselect" emptyText="下拉框选择" allowInput="false" multiSelect="true"
						url="${pageContext.request.contextPath}/param/findAllDoctypeJson.action"
						valueField="uuid" textField="name" style="width:99%;" title="文档类型"></td>
						<td class="td_left">提醒内容：</td>
						<td class="td_right" colspan="3" style="padding:3px;">
							<input  value="${comObj.remark}" 
							name="remark" class="mini-textarea" style="width:99.9%;"
							title="提醒内容"></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<script>
		var timerObj=null;
		function afterLoad(){
		if("${param.uuid}"==""){
			if(window.opener.curPrj){
				var curPrj=window.opener.curPrj;
				$("[name=prjID]").val(curPrj.uuid);
				mini.getbyName("projectNo").setValue(curPrj.projectNo);
				mini.getbyName("projectName").setValue(curPrj.projectName);
			}
			if(window.opener.LinObj){
				var LinObj=window.opener.LinObj;
				var htmlStr="<a style='color:#B94A48;' href='${pageContext.request.contextPath}/profile/findDowloadAdenexaById.action?uuid="+LinObj.uuid+"' target='_blank'>" + LinObj.filename + "</a>";
				$("[name=curFj]").val(htmlStr);
				$("[name=catalogId]").val(LinObj.catalogId);
				$("[name=pid]").val(LinObj.uuid);
				$("[name=fjName]").val(LinObj.filename);
				$("#curFJ").html(htmlStr);
				pid=LinObj.uuid;
				timerObj=setInterval(function(){
					if($("#FileMode [iconCls=icon-upload]").length>0){
						initUpBtnNew();
						clearInterval(timerObj);
					}
				},500);
				
			}
		}else{
			$("#curFJ").html($("[name=curFj]").val());
			initUpBtnNew();
		}
		}
		function initUpBtn(){
			
		}
		
		function initUpBtnNew(){
			if($("[name=pid]").val()==""){
				return
			}
			$("#FileMode [iconCls=icon-upload]").each(function(){
					var obj={};
					var id=$("[name=prjID]").val();
					if(!id){
						id="";
					}
					obj.queueID=$("#"+this.id).attr("gId");
					obj.formData={
							prjID:id,
							parentDocId:$("[name=curDocId]").val(),
							catalogId:$("[name=catalogId]").val(),
							menuId:menuId,
							pid:$("[name=pid]").val(),
							userName:gLoginUser,
							userIds:gUserId,
							curDocId:$("[name=curDocId]").val(),
							orgIds:gOrgIds
					}
					$("#"+this.id).htUpload(obj);
					$("#"+this.id).css("margin-right","20px");
					$("#"+this.id).attr("iconCls","icon-upload");
					$("#"+this.id).attr("gId",obj.queueID);
			})
		}
	</script>