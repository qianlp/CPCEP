<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html>
<head>
<title>系统界面</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<link href="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
<link href="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
<link href="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/css/animate.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/css/style.min.css?v=3.2.0" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/home/image.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/css/diy.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type='text/css' href="${pageContext.request.contextPath}/css/form/loaders.css" />

<!-- 自定义js -->
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/default/large-mode.css" rel="stylesheet"
        type="text/css" />
<link href="${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/bootstrap/skin.css" rel="stylesheet" type="text/css" />
<script>
	var gDir="${pageContext.request.contextPath}";
	var gLoginUser="${sessionScope.user.userName}";
	var docType="";
	var status="";
	
	function getMsgList(){
		var path=gDir+"/msg/getMsgList.action";
		$.post(path,{
			msgTitle:mini.getbyName("msgTitle").getValue(),
			createBy:mini.getbyName("createBy").getValue(),
			msgType:docType,
			status:status
		},function(data){
			data=mini.decode(data);
			var htmlStr="";
			$.each(data,function(){
				var type="db";
				var color="blue";
				if(this.msgType==2){
					type="tz";
					color="green";
				}else if(this.msgType==3){
					type="yj";
					color="red";
				}
				htmlStr+="<div class=\"Msg boder-"+color+"\" onclick=\"CommonOpenDoc('"+this.menuId+"','"+this.docId+"','"+this.uuid+"')\" >";
				htmlStr+="<div class=\"msg-status\">";
				htmlStr+="<a href=\"javascript:void(0)\" class=\""+type+"\" ><i></i></a>";
				htmlStr+="</div>";
				htmlStr+="<div class=\"msg-title\" >";
				htmlStr+="<a href=\"#\" >"+(this.msgTitle==""?"无标题":this.msgTitle)+"</a>";
				htmlStr+="</div>";
				htmlStr+="<div class=\"msg-util\">"+this.createBy+"&nbsp;&nbsp;&nbsp;"+mini.formatDate(this.createDate,"yyyy-MM-dd HH:mm")+"</div>";
				htmlStr+="</div>";
			})
			if(htmlStr!=""){
				$("#MsgHtml").removeClass("line2");
			}else{
				$("#MsgHtml").addClass("line2");
			}
			$("#MsgHtml").html(htmlStr);
		})
	}
	
	function CommonOpenDoc(menuId,uuid,msgId){
		var strPathUrl=gDir+"/admin/findDocById.action?menuId="+menuId+"&uuid="+uuid+"&msgId="+msgId;
		if(uuid=="" || uuid==null){
			strPathUrl=gDir+"/admin/rtuMenuById.action?menuId="+menuId+"&msgId="+msgId;
		}
		winH=screen.height-100;//高度
		winH=winH==0?(screen.height-100):winH;
		winW=screen.width-20;//宽度
		winW=winW==0?(screen.width-20):winW;
		if(winH>0){var T=(screen.height-100-winH)/2}else{var T=0}
		if(winW>0){var L=(screen.width-20-winW)/2}else{var L=0}
		var pstatus="height="+winH+",width="+winW+",top="+T+",left="+L+",toolbar=no,menubar=no,location=no,status=yes,resizable=yes,scrollbars=yes";
		window.open(strPathUrl,'_blank',pstatus);
	}
</script>
</head>
<body class="ibbody" style="padding:15px 0px;overflow-x:hidden;overflow-y:auto;">
	<div style="float:left;width:100%;display:block;">
		<div class="col-sm-12" style="float:right;position:relative;">
			<div class="da-panel-widget">
				<h3>消息中心</h3>
				<div class="pull-right" >
					<a href="javascript:void(0)" class="db" title="待办"><i></i></a>
					<a href="javascript:void(0)" class="tz" title="通知"><i></i></a>
					<a href="javascript:void(0)" class="yj" title="预警"><i></i></a>
				</div>
				<div id="MsgDivHtml"
					style="min-height:500px;padding-top:10px;">
					<div class="search_body" id="search_body">
						<div class="search_content" style="width:180px;">
							<a id="btn_DB" checkOnClick="true" groupName="docType" class="mini-button mini-button-primary" oncheckedchanged="onCheckedChanged" >待办</a>
    						<a id="btn_TZ" checkOnClick="true" groupName="docType" class="mini-button mini-button-success" oncheckedchanged="onCheckedChanged" >通知</a>
   	 						<a id="btn_YJ" checkOnClick="true" groupName="docType" class="mini-button mini-button-danger" oncheckedchanged="onCheckedChanged" >预警</a>
						</div>
						<div class="search_content">
							<div class="search_title">标题：</div>
							<div class="search_field" Operator="@" DataType="text" Item="">
								<input name="msgTitle" class="mini-textbox">
							</div>
						</div>
						<div class="search_content">
							<div class="search_title">发起人：</div>
							<div class="search_field" Operator="@" DataType="text" Item="">
								<input name="createBy" class="mini-textbox">
							</div>
						</div>
						<div class="search_button">
							<a class="mini-button" tooltip="清空查询条件" plain="true"
								iconCls="icon-remove" onclick="ClearForm"></a> &nbsp;<a
								class="mini-button" iconCls="icon-search" onclick="CommonSearch">搜索</a>
						</div>
					</div>
					<div id="MsgHtml"  class="line2" style="border-top:1px solid #EDB23F;margin-top:10px;min-height:450px;padding-bottom:20px;">
						<div id="bg"></div>
						<div class="loader" id="bg-loader">
							<div class="loader-inner ball-spin-fade-loader">
								<div></div>
								<div></div>
								<div></div>
								<div></div>
								<div></div>
								<div></div>
								<div></div>
								<div></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
	function onCheckedChanged(e) {
        var btn = e.sender;
        var checked = btn.getChecked();
        var text = btn.getText();

        if(checked){
            if(text=="待办"){
            	docType=1;
            }
            if(text=="通知"){
            	docType=2;
            }
            if(text=="预警"){
            	docType=3;
            }
            getMsgList();
        }
    }
	
	//清空查询条件
	function ClearForm(){
		var form = new mini.Form("#search_body");
		form.reset();
		docType="";
		status="";
		getMsgList();
	}
	/*
	描述：查询
	*/
	function CommonSearch(){
		getMsgList();
	}
	mini.parse();
	getMsgList();
	</script>
</body>
</html>