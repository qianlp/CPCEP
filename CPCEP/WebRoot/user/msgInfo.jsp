<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>系统界面</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<link href="${pageContext.request.contextPath}/css/common/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/home/default/BgSkin.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/home/style.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/css/home/image.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/common/diy.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/css/home/default/koala.min.1.5.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/default/large-mode.css" rel="stylesheet"
        type="text/css" />
<link href="${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/bootstrap/skin.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.mbox-body table tr td a{
		color:#000;
	}
	.mbox-body table tr td a:hover{
		color:blue;
	}
	.ibox-title {
    -moz-border-bottom-colors: none;
    -moz-border-left-colors: none;
    -moz-border-right-colors: none;
    -moz-border-top-colors: none;
    background-color: #fff;
    border-color: #e7eaec;
    border-image: none;
    border-style: solid solid none;
    border-width: 4px 0 0;
    color: inherit;
    margin-bottom: 0;
    padding: 14px 15px 7px;
    min-height: 18px;
	}
	.ibox-title h5 {
    display: inline-block;
    font-size: 14px;
    margin: 0 0 7px;
    padding: 0;
    text-overflow: ellipsis;
    float: left;
    font-weight: 600;
}
	.ibox-content {
    background-color: #fff;
    color: inherit;
    padding: 15px 20px 20px 20px;
    border-color: #e7eaec;
    border-image: none;
    border-style: solid solid none;
    border-width: 1px 0;
}
a:link {    
text-decoration: none;   
}   
a:visited {   
text-decoration: none;   
}   
a:hover {   
text-decoration: underline;   
}  
</style>
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
				htmlStr+="<div id='"+this.uuid+"' class=\"Msg boder-"+color+"\" style='position:relative;' >";
				htmlStr+="<div class=\"msg-status\">";
				htmlStr+="<a href=\"javascript:void(0)\" class=\""+type+"\" ><i></i></a>";
				htmlStr+="</div>";
				htmlStr+="<div class=\"msg-title\" >";
				htmlStr+="<a href=\"javascript:CommonOpenDoc('"+this.menuId+"','"+this.docId+"','"+this.uuid+"')\" >"+(this.msgTitle==""?"无标题":this.msgTitle)+"</a>";
				htmlStr+="</div>";
				htmlStr+="<div class=\"btn-group\" style='right:0px;top:5px;position: absolute;'>";
				if(this.msgType==2){
					htmlStr+="<a href=\"javascript:upMsgStatus('"+this.uuid+"')\" title=\"标记为已阅\" class=\"btn btn-mini tip\">";
					htmlStr+="<img src=\"../img/common/tick.png\" alt=\"\"></a>";
				}
				htmlStr+="<a href=\"javascript:delMsg('"+this.uuid+"')\" title=\"删除消息\" class=\"btn btn-mini tip deleteRow\">";
				htmlStr+="<img src=\"../img/common/trash.png\" alt=\"\"></a></div>";
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
	
	function upMsgStatus(id){
		var path=gDir+"/msg/updateMsgStatus.action";
		mini.mask({
            el: document.getElementById(id),
            cls: 'mini-mask-loading',
            html: '更新中...'
        });
		$.post(path,{uuid:id},function(data){
			$("#"+id).remove();
		})
	}
	
	function delMsg(id){
		mini.confirm("确认要删除这条消息吗？", "操作提示", function(action){
			if(action=="ok"){
				mini.mask({
		            el: document.getElementById(id),
		            cls: 'mini-mask-loading',
		            html: '数据删除中...'
		    	});
				var path=gDir+"/admin/deleteModelId.action"
				$.post(path,{uuid:id,className:"MsgInfo"},function(data){
					$("#"+id).remove();
				})
			}
		});
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
<body class="ibbody" style="overFlow-x:hidden">
	<%@include file="../homeTemplate/homeOne/menuTemplate.jsp" %>
	<div class="container-fluid main-content">
		<div class="social-wrapper wrapper" id="description"
			style="overflow:hidden;position:absolute;width:100%;margin-top:3px;">
			<div id="social-container"
				style="margin:auto;margin-top:40px;width:1120px;">
				<div id="userDiv" class="item widget-container fluid-height  col-md-7-k"
					style="width: 1100px;padding-bottom:8px;height:auto;">
					<div class="mbox-body" style="position: relative;padding: 0.5em 1.2em;">
						<h3>消息中心</h3>
						<div class="pull-right"
							style="position:  absolute;top: 0px;right: 10px;">
							<a href="javascript:void(0)" class="db" title="待办"><i></i></a> <a
								href="javascript:void(0)" class="tz" title="通知"><i></i></a> <a
								href="javascript:void(0)" class="yj" title="预警"><i></i></a>
						</div>
						<div class="search_body" id="search_body">
							<div class="search_content" style="width:180px;">
								<a id="btn_DB" checkOnClick="true" groupName="docType"
									class="mini-button mini-button-primary"
									oncheckedchanged="onCheckedChanged">待办</a> <a id="btn_TZ"
									checkOnClick="true" groupName="docType"
									class="mini-button mini-button-success"
									oncheckedchanged="onCheckedChanged">通知</a> <a id="btn_YJ"
									checkOnClick="true" groupName="docType"
									class="mini-button mini-button-danger"
									oncheckedchanged="onCheckedChanged">预警</a>
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
							<div class="search_button">&nbsp;
								<a class="mini-button" tooltip="清空查询条件"
									iconCls="icon-remove" onclick="ClearForm">重置</a> &nbsp;<a
									class="mini-button" iconCls="icon-search"
									onclick="CommonSearch">搜索</a>
							</div>				
						</div>
						<div id="MsgHtml" 
							style="border-top:1px solid #EDB23F;margin-top:10px;min-height:450px;">
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
	var treeData=null;
	var fpx = ($("body").innerWidth() - 1130) / 2;
	$("#userInfo").css("right", (fpx+10)+ "px");
	$("#user_login_card").css("right",(fpx+10)+"px");
	$("#userDiv").css("min-height",($("body").innerHeight()-200)+"px");
	mini.parse();
	$.ajax({
		url : encodeURI("${pageContext.request.contextPath}/admin/findHasRightMenus.action"),
		cache : false,
		async : false,
		success : function(MenuText) {
			treeData=mini.arrayToTree(mini.decode(MenuText),"children","id","pid");
			initMenu();
		}
	});
	
	function initMenu(){
		var htmlStr="";
		htmlStr+="<li title='首页'>";
		htmlStr+="<a href=\"javascript:location.href='${pageContext.request.contextPath}/index.jsp';\" style='padding-top:9px;padding-bottom:2px;color:#666666;font-weight:bold;'>";
		htmlStr+="<img src=\"${pageContext.request.contextPath}/img/menu/home.png\" width=\"28px\" height=\"28px\"></img>";
		htmlStr+="首页";
		htmlStr+="</a></li>";
		htmlStr+="</a></li>";
		$.each(treeData,function(i){
			if(i>5){
				htmlStr+="<li title='更多'>";
				htmlStr+="<a href=\"javascript:showMenuCard();\" style='padding-top:9px;padding-bottom:2px;color:#666666;font-weight:bold;'>";
				htmlStr+="<img src=\"${pageContext.request.contextPath}/img/menu/icon"+i+".png\" width=\"28px\" height=\"28px\"></img>";
				htmlStr+="更多";
				htmlStr+="</a></li>";
				htmlStr+="</a></li>";
				return false;
			}
			htmlStr+="<li title='"+this.text+"'>";
			htmlStr+="<a href=\"javascript:location.href='${pageContext.request.contextPath}/menu.jsp?menuId="+this.id+"';\" style='padding-top:9px;padding-bottom:2px;color:#666666;font-weight:bold;'>";
			htmlStr+="<img src=\"${pageContext.request.contextPath}/img/menu/icon"+i+".png\" width=\"28px\" height=\"28px\"></img>";
			htmlStr+=subText(this.text);
			htmlStr+="</a></li>";
			htmlStr+="</a></li>";
		})
		$("#navBar").append(htmlStr);
		initSubMenu();
	}
	
	function initSubMenu(){
		var k=1,o=1;
		$("#menu_body").append("<ul id=\"navBar"+k+"\" class=\"navbar main-nav nav-collapse nav\" style=\"width:100%;text-align:left;padding-left:25px;height:63px;\"></ul>");
		var htmlStr = "";
		var intCar=1;
		for(var b=6;b<treeData.length;b++){
			if(o>8){
				intCar++;
				$("#navBar"+k).append(htmlStr);
				htmlStr="";
				k++;
				o=1;
				$("#menu_body").append("<ul id=\"navBar"+k+"\" class=\"navbar nav\" style=\"width:100%;text-align:left;padding-left:25px;height:63px;\"></ul>");
			}
			htmlStr += "<li title='"+treeData[b].text+"'>";
			htmlStr += "<a href=\"javascript:location.href='${pageContext.request.contextPath}/menu.jsp?menuId="+treeData[b].id+"';\" style='padding-top:9px;padding-bottom:2px;color:#666666;font-weight:bold;'>";
			htmlStr += "<img src=\"${pageContext.request.contextPath}/img/menu/icon8.png\" width=\"28px\" height=\"28px\"></img>";
			htmlStr += subText(treeData[b].text);
			htmlStr += "</a></li>";
			htmlStr += "</a></li>";
			o++;
		}
		$("#navBar"+k).append(htmlStr);
		$("#menu_body").css("height",(63*intCar)+"px");
	}
	
	function subText(str){
		if(str.length>5){
			return str.substr(0,4)+".."
		}
		return str;
	}
	

	function ondrawcell(e) {
			var tree = e.sender, record = e.record, column = e.column, field = e.field, funs = record.functions;
			function createButton(funs) {
				if (!funs)
					return "";
				var html = "";
				for (var i = 0, l = funs.length; i < l; i++) {
					var fn = funs[i];
					html += '<input onclick="" type="button" value="' + fn.text+ '" class="smallBtn"/>';
				}
				return html;
			}
			if (field == 'functions') {
				if (tree.isLeaf(record)) {
					e.cellHtml = createButton(funs);
					//+ '<input onclick="alert(\'施工中...\')" type="button" value="添加至快捷操作" class="smallBtn" style="float:right;"/>';
				} else {
					e.cellHtml = createButton(funs);
				}
			}
		}
		
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
