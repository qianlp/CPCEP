<%@ page language="java" contentType="text/html; charset=Utf-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html>
<head>
<title>消息中心</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/homeTemplate/homeFour/js/echarts.min.js"></script>
<link href="${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/default/large-mode.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/pure/skin.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/homeTemplate/homeFour/js/amazeui.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/homeTemplate/homeFour/css/amazeui.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/homeTemplate/homeFour/css/admin.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/homeTemplate/homeFour/css/app.css">
<style type="text/css">
	body {
		margin-top: 90px
	}
	body .search_body{border:0px solid #000;overflow:hidden;display:block;padding-top:5px;}
	.search_content,.search_title,.search_field,.search_button{float:left}
	.search_content{width:240px;margin:2px;height:30px;}
	.search_title{width:90px;text-align:right;padding-top:1px;letter-spacing:2px;height:28px;line-height:26px;}
	.search_field{width:130px;text-align:left}
	.search_button{margin:1px;}
	.search_field .mini-radiobuttonlist{padding-top:3px}
	.mini-grid-emptyText{
		font-size:15px;
		color:#ccc;
		height:200px;
		text-align:center;
	}
</style>
<script>
	
</script>
</head>
<body data-type="index" style="">
	<%@include file="menuTemplate.jsp" %>
	<div class="tpl-page-container tpl-page-header-fixed" style="padding-top: 0px">
		<%@include file="leftTemplate.jsp" %>
		<div class="row">
            <div class="am-u-md-12 am-u-sm-12 row-mb">
            	<div class="tpl-portlet">
            		<div class="tpl-caption font-green" style="float:none;padding:0px;padding-bottom:10px;">
                    	<span>消息中心</span>
                    </div>
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
<script>
	var fpx = ($("body").innerWidth() - 1130) / 2;
	$(".tpl-portlet").css("min-height",($("body").innerHeight()-111)+"px");
	
	mini.parse();
	
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