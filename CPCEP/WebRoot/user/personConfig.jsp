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
<script src="${pageContext.request.contextPath}/css/home/default/koala.min.1.5.js" type="text/javascript"></script>
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
.smallBtn{
			background:#deecfc url(${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/images/tabs/tab.gif) repeat-x 0 0;
			border:solid 1px #aaa;
			margin-right:7px;
			height:24px;
			line-height:18px;
			border-radius:4px;
			border:1px solid #8CB2E2;
		}
</style>
<script>
	var gDir="${pageContext.request.contextPath}";
	var gLoginUser="${sessionScope.user.userName}";
</script>
</head>
<body class="ibbody" style="overFlow:hidden">
	<%@include file="../homeTemplate/homeOne/menuTemplate.jsp" %>
	<div class="container-fluid main-content">
		<div class="social-wrapper wrapper" id="description"
			style="overflow:hidden;position:absolute;width:100%;margin-top:3px;">
			<div id="social-container"
				style="margin:auto;margin-top:40px;width:1120px;">
				<div id="userDiv" class="item widget-container fluid-height  col-md-7-k"
					style="width: 1100px;padding-bottom:8px;">
					<div class="mbox-body" style="height:100%;">
						<div style="width:300px;height:100%;float:left;border-right:4px solid #e7eaec;">
							<div class="ibox-title" >
								<h5>个人资料</h5>
                    		</div>
                    		<div class="ibox-content" style="text-align:center;">
                    				<s:if test="#session.imgPath eq '' ">
										<img alt="image" id="userImg" width="150" height="150" style="border-radius:5px;" src="${pageContext.request.contextPath}/img/common/pic_user.gif">
									</s:if>
									<s:else>
										<img alt="image" id="userImg" width="150" height="150" style="border-radius:5px;" src="${pageContext.request.contextPath}/admin/showPicture.action?userId=${session.user.uuid}">
									</s:else>
									<div style="width:100%;padding-top:10px;">
										<a class="mini-button" onclick="openUserImg()">更改头像</a>
										<a class="mini-button" onclick="openPwd()">修改密码</a>
									</div>
							</div>
							<div class="ibox-content" style="text-align:center;">
									
							</div>
						</div>
						<div style="width:796px;height:100%;float:left;">
							<div class="ibox-title" >
								<h5>权限一览</h5>
                    		</div>
                    		<div class="ibox-content" style="text-align:center;">
                    			<div id="MyPrower" class="mini-treegrid" style="width:100%;height:400px;"
    								treeColumn="text" idField="id" parentField="pid" resultAsTree="false"  
    								allowResize="false" expandOnLoad="true" showTreeIcon="true"
    								allowSelect="true" allowCellSelect="false" enableHotTrack="false"
    								ondrawcell="ondrawcell" allowCellWrap="true"
									imgPath='${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/icons/'
								>
    								<div property="columns">
        								<div type="indexcolumn"></div>
        								<div name="text" field="text" width="260" >模块名称</div>
        								<div field="functions" width="100%">操作</div>
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
	$("#userDiv").css("height",($("body").innerHeight()-200)+"px");
	$("#MyPrower").css("height",($("body").innerHeight()-270)+"px");
	mini.parse();
	$.ajax({
		url : encodeURI("${pageContext.request.contextPath}/admin/findHasRightMenus.action"),
		cache : false,
		async : false,
		success : function(MenuText) {
			treeData=mini.arrayToTree(mini.decode(MenuText),"children","id","pid");
			initMenu();
			getMenu();
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
					html += '<input onclick="" type="button" value="' + fn.name+ '" class="smallBtn"/>';
				}
				return html;
			}
			if (field == 'functions') {
				if (tree.isLeaf(record)) {
					e.cellHtml = createButton(mini.decode(funs));
					//+ '<input onclick="alert(\'施工中...\')" type="button" value="添加至快捷操作" class="smallBtn" style="float:right;"/>';
				} else {
					e.cellHtml = createButton(mini.decode(funs));
				}
			}
		}
		function getMenu() {
			var path="${pageContext.request.contextPath}/admin/findHasRightMenusAndBtn.action";
			$.post(path,{},function(data){
				data=mini.arrayToTree(mini.decode(data),"children","id","pid");
				mini.get("MyPrower").setData(data);
			})
		}
		function DGMenu(task) {
			if (task.children) {
				$.each(task.children, function() {
					if (this.Form == "fmBtnName") {
						task.functions = task.children;
						task.children = [];
						return false;
					} else {
						DGMenu(this)
					}
				})
			}
		}
		
		var imgWin=null;
		function openUserImg(){
			var path=gDir+"/user/imgConfig.jsp";
			imgWin=mini.open({
				title:"头像上传",
				url:path,
				width: 400,
				allowResize:false,
	    		height: 331
			});
		}
		
		function closeImgWin(src){
			if(src){
				$("#userImg").attr("src",gDir+src);
				$("#bigUserImg").attr("src",gDir+src);
			}
			if(imgWin){
				imgWin.destroy();
			}
		}
		
		var win=null;
		function openPwd(){
			var path=gDir+"/user/updatePassword.jsp";
			win=mini.open({
				title:"修改密码",
				url:path,
				width: 400,
				allowResize:false,
	    		height: 155
			});
		}
		
		function closeWin(){
			if(win){
				win.destroy();
			}
		}
	</script>
</body>
</html>
