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
<link href="${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/pure/skin.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	body{overflow:hidden;}
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
</style>
<script>
	var gDir="${pageContext.request.contextPath}";
	var gLoginUser="${sessionScope.user.userName}";
	
	function CommonOpenDoc(menuId,uuid,msgId,docBusinessId,taskId,taskName){
		var strPathUrl=gDir+"/admin/findDocById.action?menuId="+menuId+"&uuid="+uuid+"&msgId="+msgId;
		if(uuid=="" || uuid==null || uuid=="null"){
			strPathUrl=gDir+"/admin/rtuMenuById.action?menuId="+menuId+"&msgId="+msgId+"&docBusinessId="+docBusinessId+"&taskId="+taskId+"&taskName="+encodeURI(encodeURI(taskName));
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
	<%@include file="menuTemplate.jsp" %>
	<div class="container-fluid main-content">
		<div class="social-wrapper wrapper" id="description"
			style="overflow:hidden;position:absolute;width:100%;margin-top:3px;">
			<div id="social-container"
				style="margin:auto;margin-top:40px;width:1120px;">
				<div id="userDiv" class="item widget-container fluid-height  col-md-7-k"
					style="width: 1100px;padding-bottom:0px;padding-top:5px;height:auto;">
						
						<div class="search_body" id="search_body" style="margin-bottom:10px;">
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
									class="mini-button" iconCls="icon-search"
									onclick="CommonSearch">搜索</a>
							</div>
							<div class="pull-right"
							style="position:  relative;top: -10px;right: 10px;">
							<a href="javascript:void(0)" class="db" title="待办"><i></i></a> <a
								href="javascript:void(0)" class="tz" title="通知"><i></i></a> <a
								href="javascript:void(0)" class="yj" title="预警"><i></i></a>
							</div>
						</div>
						<div class="mini-fit">
						<div id="mainTabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;"
							onactivechanged="tabActive"
						>
							<div title="消息中心">
								<div id="wfMsg" class="mini-datagrid" style="width:100%;height:100%;"  idField="uuid" allowAlternating="true" 
									sizeList="[20,50,100]" pageSize="20"
									url="${pageContext.request.contextPath}/msg/getPageMsgList.action"
								>
        							<div property="columns">
            							<div type="indexcolumn" ></div>
            							<div field="msgTitle" width="60%" headerAlign="left"  renderer='CommonRowLink'>标题</div>   
            							<div field="createDate" width="20%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd hh:mm">发起日期</div>   
           	 							<div field="op" width="20%" headerAlign="center" align="center" renderer='CommonRowOP'>操作</div>         
        							</div>
    							</div>
                			</div>
            				<div title="待办">
            					<div id="wfMsg1" class="mini-datagrid" style="width:100%;height:100%;"  idField="uuid" allowAlternating="true" 
									sizeList="[20,50,100]" pageSize="20"
									url="${pageContext.request.contextPath}/msg/getPageMsgList.action"
								>
        							<div property="columns">
            							<div type="indexcolumn" ></div>
            							<div field="msgTitle" width="60%" headerAlign="left"  renderer='CommonRowLink'>标题</div>   
            							<div field="createDate" width="20%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd hh:mm">发起日期</div>   
           	 							<div field="op" width="20%" headerAlign="center" align="center" renderer='CommonRowOP'>操作</div>         
        							</div>
    							</div>
                			</div>
            				<div title="通知">
            					<div id="wfMsg2" class="mini-datagrid" style="width:100%;height:100%;"  idField="uuid" allowAlternating="true" 
									sizeList="[20,50,100]" pageSize="20"
									url="${pageContext.request.contextPath}/msg/getPageMsgList.action"
								>
        							<div property="columns">
            							<div type="indexcolumn" ></div>
            							<div field="msgTitle" width="60%" headerAlign="left"  renderer='CommonRowLink'>标题</div>   
            							<div field="createDate" width="20%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd hh:mm">发起日期</div>   
           	 							<div field="op" width="20%" headerAlign="center" align="center" renderer='CommonRowOP'>操作</div>         
        							</div>
    							</div>
                			</div>
            				<div title="预警">
            					<div id="wfMsg3" class="mini-datagrid" style="width:100%;height:100%;"  idField="uuid" allowAlternating="true" 
									sizeList="[20,50,100]" pageSize="20"
									url="${pageContext.request.contextPath}/msg/getPageMsgList.action"
								>
        							<div property="columns">
            							<div type="indexcolumn" ></div>
            							<div field="msgTitle" width="60%" headerAlign="left" renderer='CommonRowLink'>标题</div>   
            							<div field="createDate" width="20%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd hh:mm">发起日期</div>   
           	 							<div field="op" width="20%" headerAlign="center" align="center" renderer='CommonRowOP'>操作</div>         
        							</div>
    							</div>
                			</div>
            				<div title="已处理">
            					<div id="wfMsg4" class="mini-datagrid" style="width:100%;height:100%;"  idField="uuid" allowAlternating="true" 
									sizeList="[20,50,100]" pageSize="20"
									url="${pageContext.request.contextPath}/msg/getPageMsgList.action"
								>
        							<div property="columns">
            							<div type="indexcolumn" ></div>
            							<div field="msgTitle" width="80%" headerAlign="left"  renderer='CommonRowLink'>标题</div>   
            							<div field="createDate" width="20%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd hh:mm">发起日期</div>
        							</div>
    							</div>
                			</div>
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
	$("#logoInfo").css("left",(fpx+3)+"px");
	$("#userDiv").css("min-height",($("body").innerHeight()-190)+"px");
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
		
		
		//清空查询条件
		function ClearForm(){
			var form = new mini.Form("#search_body");
			form.reset();
			docType="";
			status="";
			getMsgList();
		}
		
		function CommonRowLink(cell){
			var type="db"
			var color="blue";
			if(cell.row.msgType==2){
				type="tz";
				color="green";
			}else if(cell.row.msgType==3){
				type="yj";
				color="red";
			}
			return "<a style='color:"+color+"' href=\"javascript:CommonOpenDoc('"+cell.row.menuId+"','"+cell.row.docId+"','"+cell.row.uuid+"','"+cell.row.docBusinessId+"','"+cell.row.taskId+"','"+cell.row.taskName+"');\"><span href=\"javascript:void(0)\" hidefocus=\"true\" target=\"_self\" class=\""+type+"\"><i></i></span>" + cell.value + "</a>";
		}
		
		function CommonRowOP(cell){
			var htmlStr="";
			if(cell.row.msgType==2 || cell.row.msgType==3){
				htmlStr+="<img onclick='upMsgStatus(\""+cell.row.uuid+"\")' style='cursor:pointer;width:20px;height:20px;margin-right:5px;' title='标为已阅' src='${pageContext.request.contextPath}/homeTemplate/homeOne/image/msg-yy.png' />";
			}
			htmlStr+="<img onclick='removeMsg(\""+cell.row.uuid+"\")' style='cursor:pointer;width:20px;height:20px;' title='移除' src='${pageContext.request.contextPath}/homeTemplate/homeOne/image/trash.png' />";
			return htmlStr;
		}
		
		function upMsgStatus(id){
			var path=gDir+"/msg/upMsgStatus.action";
			$.post(path,{docId:id},function(data){
				if(data=="ok"){
					mini.showTips({
						content: "<b>成功</b> <br/>已标为已阅",
						state: "success",
				        x: "center",
				        y: "center",
				        timeout: 3000
				    });
					CommonSearch();
				}
			})
		}
		function removeMsg(id){
			var path=gDir+"/msg/removeMsgStatus.action";
			mini.confirm("确认要删除选定文档吗？", "操作提示", function(action){
				if(action=="ok"){
					$.post(path,{docId:id},function(data){
						if(data=="ok"){
							mini.showTips({
				           		content: "<b>成功</b> <br/>已删除",
				            	state: "success",
				            	x: "center",
				            	y: "center",
				           		timeout: 3000
				        	});
							CommonSearch();
						}
					})
				}
			})
		}
		
		function tabActive(e){
			var tab=e.tab;
			if(tab.title=="消息中心"){
				mini.get("wfMsg").loading();
				mini.get("wfMsg").load({status:""});
			}else if(tab.title=="待办"){
				mini.get("wfMsg1").loading();
				mini.get("wfMsg1").load({msgType:1,status:""});
			}else if(tab.title=="通知"){
				mini.get("wfMsg2").loading();
				mini.get("wfMsg2").load({msgType:2,status:""});
			}else if(tab.title=="预警"){
				mini.get("wfMsg3").loading();
				mini.get("wfMsg3").load({msgType:3,status:""});
			}else if(tab.title=="已处理"){
				mini.get("wfMsg4").loading();
				mini.get("wfMsg4").load({status:2});
			}
		}
		
		/*
		描述：查询
		*/
		function CommonSearch(){
			var tab=mini.get("mainTabs").getActiveTab();
			var msgTitle=mini.getbyName("msgTitle").getValue();
			var createBy=mini.getbyName("createBy").getValue();
			if(tab.title=="消息中心"){
				mini.get("wfMsg").loading();
				mini.get("wfMsg").load({status:"",msgTitle:msgTitle,createBy:createBy});
			}else if(tab.title=="待办"){
				mini.get("wfMsg1").loading();
				mini.get("wfMsg1").load({msgType:1,status:"",msgTitle:msgTitle,createBy:createBy});
			}else if(tab.title=="通知"){
				mini.get("wfMsg2").loading();
				mini.get("wfMsg2").load({msgType:2,status:"",msgTitle:msgTitle,createBy:createBy});
			}else if(tab.title=="预警"){
				mini.get("wfMsg3").loading();
				mini.get("wfMsg3").load({msgType:3,status:"",msgTitle:msgTitle,createBy:createBy});
			}else if(tab.title=="已处理"){
				mini.get("wfMsg4").loading();
				mini.get("wfMsg4").load({status:2,msgTitle:msgTitle,createBy:createBy});
			}
		}
		
	</script>
</body>
</html>
