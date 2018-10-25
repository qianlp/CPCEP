<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<s:action name="findFirstFim" namespace="/business/flow" executeResult="true">
	<s:param name="planId" >${param.planId}</s:param>
</s:action>
<html>
  <head>
    <title>项目框图</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
	<link rel="stylesheet" type='text/css' href="${pageContext.request.contextPath}/business/diagramFlow/about/bootstrap.css" />
	<link rel="stylesheet" type='text/css' href="${pageContext.request.contextPath}/business/diagramFlow/about/style.css" />
	<script src="${pageContext.request.contextPath}/business/diagramFlow/about/FlowDiag.js" type="text/javascript"></script>
	<style>
		html,body{
			overflow-x: hidden; overflow-y: hidden;
			padding:0px;
			margin:0px;
		}
		.input-square{
			width:120px;
			height:24px;
			line-height:15px;
		}
		.box-head{
			background:url('${pageContext.request.contextPath}/business/diagramFlow/about/images/panel-header.png');
		}
		.table{
			font-size:14px;
		}
		#title-two table{
			font-size:13px;
		}
		.asLabel .mini-textbox-border,
    	.asLabel .mini-textbox-input,
    	.asLabel .mini-buttonedit-border,
    	.asLabel .mini-buttonedit-input,
    	.asLabel .mini-textboxlist-border
    	{
        	border:0;background:none;cursor:default;
    	}
    	.asLabel .mini-buttonedit-button,
    	.asLabel .mini-textboxlist-close
    	{
        	display:none;
    	}
    	.asLabel .mini-textboxlist-item
    	{
       	 	padding-right:8px;
    	}    
	</style>
	<script>
		var gDir="${pageContext.request.contextPath}";
		var StatusColor= mini.decode("${requestScope.statusColor}");
		var planId="${param.planId}";
        var bUuid="${requestScope.bBull.uuid}";
        var bFileUuid="${requestScope.bFile.uuid}";
		var bFileName="${requestScope.bFile.name}";
		var bFileCode="${requestScope.bFile.code}";
		var bFileVersion="${requestScope.bFile.version}";
		var pMethod="${plan.purchaseMethod}";
		var personName="${plan.technologyReviewerName}";
		var curUserName="${session.user.userName}";
		var invStruts="${requestScope.invStruts}";//邀请竞价状态
		var clarifyStruts="${requestScope.clarifyStruts}";//邀请澄清状态
	</script>
	${fim.htmlBody}
  </head>
  <body>
  <div style="display:none">
  	<input name="prjId" value="${param.prjId}" >
  	<input name="planId" value="${param.planId}" >
  	<textarea name="gressList" >${fim.dataList}</textarea>
  </div>
   <div class="main" style="background:#FFFFFF;height:100%;border:1px solid #CCCCCC">
	<div class="container-fluid" style="padding:0px;margin:0px">
		<div class="content" style="padding:0px;margin:0px;">
			<div class="row-fluid">
				<div id="title-two" class="box" style="margin-top:10px;border:0px;background:#fff;padding:5px 0px">
					<table style="border:0px" width=100% >
						<tr>
						<td style="text-align:right;">项目名称：</td>
						<td style="text-align:left;">
						<input name="projectName" class="mini-textbox" value="${plan.projectName}" />
						</td>
						<td style="text-align:right">采购计划编号：</td>
						<td style="text-align:left">
						<input name="purchasePlanName" class="mini-textbox" value="${plan.purchasePlanCode}" />
						</td>
						<td style="text-align:right">采购计划名称：</td>
						<td style="text-align:left">
						<input name="purchaseMethod" class="mini-textbox" value="${plan.purchasePlanName}" />
						</td>
						</tr>
						<tr>
							<td style="text-align:right;">采购执行人：</td>
							<td style="text-align:left;">
								<input name="executePeoName" class="mini-textbox" value="${plan.executePeoName}" />
							</td>
							<td style="text-align:right">报名开始时间：</td>
							<td style="text-align:left">
								<input name="registerStartTime" class="mini-datepicker" value="${bBull.registerStartTime}" />
							</td>
							<td style="text-align:right">报名结束时间：</td>
							<td style="text-align:left">
								<input name="registerEndTime" class="mini-datepicker" value="${bBull.registerEndTime}" />
							</td>
						</tr>
						<tr>
							<td style="text-align:right;">投标开始时间：</td>
							<td style="text-align:left;">
								<input name="bidStartTime" class="mini-datepicker" value="${bBull.bidStartTime}" />
							</td>
							<td style="text-align:right">投标结束时间：</td>
							<td style="text-align:left">
								<input name="bidEndTime" class="mini-datepicker" value="${bBull.bidEndTime}" />
							</td>
							<td style="text-align:right">预计开标时间：</td>
							<td style="text-align:left">
								<input name="bidOpenTime" class="mini-datepicker" value="${bBull.bidOpenTime}" />
							</td>
						</tr>
					</table>
				</div>
				<div id="title-three" class="box" style="margin-top:0px;border:0px">
					<div id="boxTitle" class="box-head" style="text-align:left;border-bottom:0px;padding-top:2px;padding-bottom:2px;border:0px">
						<div style="width:34%;float:left;">
							<div style="text-align:left;cursor:default;padding:5px;line-height:20px;"><b>框图信息</b></div>
						</div>
						<div style="width:65%;float:left;text-align:right">
							<div style="float:left;padding-top:2px;">
								<a class="mini-button" iconCls="icon-node" onclick="lookSupSignup()" >查看报名信息</a>&nbsp;
								<a class="mini-button" iconCls="icon-node" onclick="openSupBidList()" >供应商投标列表</a>&nbsp;
								<a class="mini-button" iconCls="icon-node" onclick="openSubScopeList()" >竞价排名</a>&nbsp;
								<a class="mini-button" iconCls="icon-node" onclick="openBidReturn()" style="display:none" >评标澄清反馈</a>&nbsp;
								<a class="mini-button" iconCls="icon-node" onclick="alert('开发中')" style="display:none">查看项目文件</a>&nbsp;
							</div>
							<div style="text-align:right;padding-top:2px;">
								<a class="mini-button" iconCls="icon-reload" onclick="location.reload()" >刷新</a>&nbsp;
								<a class="mini-button" iconCls="icon-undo" onclick="self.close()" >返回</a>
							</div>
						</div>
					</div>
				</div>
				<div id="panals" style="width:700px;height:300px;text-align:center;overflow-x: hidden;overflow-y: scroll">
					<div id="panal_1" style="width:100%;padding:5px 20px 20px 20px;"></div>
					<div id="panal_2" style="width:100%;padding:5px 20px 20px 20px;"></div>
					<div id="panal_3" style="width:100%;padding:5px 20px 20px 20px;"></div>
					<div id="panal_4" style="width:100%;padding:5px 20px 20px 20px;"></div>
					<div id="panal_5" style="width:100%;padding:5px 20px 20px 20px;"></div>
					<div id="panal_6" style="width:100%;padding:5px 20px 20px 20px;"></div>
					<div id="panal_7" style="width:100%;padding:5px 20px 20px 20px;"></div>
					<div id="panal_8" style="width:100%;padding:5px 20px 20px 20px;"></div>
					<div id="panal_9" style="width:100%;padding:5px 20px 20px 20px;"></div>
					<div id="panal_10" style="width:100%;padding:5px 20px 20px 20px;"></div>
				</div>
			</div>
		</div>
	</div>
	</div>
   <div class="modal hide" id="myModal" style="width:100%;margin:0px;height:100%;left:0px;top:0px;border:0px">
		<div class="modal-body" id="newMod" style="height:100%;overflow-y: hidden;max-height:900px;padding:0px">
			<iframe id="ifr"  src="about:blank" frameborder="0" style="border-width:0px;height:100%;width:100%;text-align:center"></iframe>
		</div>
	</div>
	<script>
	var ProwerCon=[];
	function getNode(){
		ProwerCon=mini.decode($("[name=gressList]").val());
		if(ProwerCon.length==0){
			mini.alert("未维护框图！");
			return;
		}
		CreateProHtml();
	}
	
	
	function createIframe(src){
		$("#newMod").html("<iframe id='ifr'  src='"+src+"' frameborder='0' style='border-width:0px;height:100%;width:100%;text-align:center'></iframe>")
	}
	//模态显示
	var curMenuId="";
	var isNew=true;
	function ModalShow(obj){
		console.log(obj);
		curMenuId=obj.menuId;
		if(!curMenuId){
			/* if(obj.itemName=="技术评标报告" && curUserName!=personName){
				mini.alert("只有技术评标人才能进行技术评标");
				return;
			}
		 */
			if(obj.itemEvent){
				eval(obj.itemEvent);
			}
			return;
		}
		if(obj.itemName=="招标公告"){
			if(!isBidNew){
				isNew=false;
			}
		}
		if(obj.itemName=="上传招标文件"){
			if(!isBidFileNew){
				isNew=false;
			}
		}
		
		createIframe();
		if(curMenuId && curMenuId!=""){
			$("#ifr").attr("src",gDir+"/admin/findHasRightMenuFlow.action?menu.uuid="+curMenuId+"&prjId="+$("[name=prjId]").val()+"&planId="+$("[name=planId]").val());
		}else{
			$("#ifr").attr("src",gDir+"/admin/findHasRightMenuFlow.action?menu.uuid=8ac4a8b1573c664e01573c7a185b0013&prjId="+$("[name=prjId]").val()+"&taskId="+obj.uuid);
		}
		$("#myModal").fadeIn(1000);
	}

	//模态隐藏
	function ModalHide(){
		//getNodeStatus(curMenuId);
		$("#myModal").fadeOut(1000);
	}
	
	//动态获取高度
	function olNum(){
		var wxWidth=parent.$("body").innerWidth();
		var wxHeight=parent.$("#myModal").innerHeight()-$("#title-one").innerHeight()-$("#title-two").innerHeight()-$("#title-three").innerHeight();
		$("#panals").css("width",wxWidth)	
		$("#panals").css("height",wxHeight);
		$("#panal_1").css("width",wxWidth)	
		$("#panal_2").css("width",wxWidth)	
		$("#panal_3").css("width",wxWidth)	
		$("#panal_4").css("width",wxWidth)	
		$("#panal_5").css("width",wxWidth)		
	}
	
    function labelModel(form) {
        var fields = form.getFields();
        for (var i = 0, l = fields.length; i < l; i++) {
            var c = fields[i];
            if (c.setReadOnly) c.setReadOnly(true);     //只读
            if (c.setIsValid) c.setIsValid(true);      //去除错误提示
            if (c.addCls) c.addCls("asLabel");          //增加asLabel外观
        }
    }
	
    $(document).ready(function(){
    	mini.parse();
    	olNum();
    	getNode();
    	labelModel(new mini.Form("title-two"));
    })
	
	</script>
  </body>
</html>
