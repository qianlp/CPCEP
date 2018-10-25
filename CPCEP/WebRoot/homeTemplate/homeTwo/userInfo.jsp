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

<!-- 自定义js -->
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<script>
	var gDir="${pageContext.request.contextPath}";
	var gLoginUser="${sessionScope.user.userName}";

</script>
<style>
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
</head>
<body class="ibbody" style="padding:15px 0px;">
	<div style="float:left;width:100%;display:block;">
		<div class="col-sm-3" style="float:left;position:relative;">
			<div class="da-panel-widget">
				<h3>个人资料</h3>
				<div id="UserHtml" style="min-height:500px;">
					<div class="ibox-content" style="text-align:center;">
						<s:if test="#session.imgPath eq '' ">
							<img alt="image" id="userImg" width="150" height="150"
								style="border-radius:5px;"
								src="${pageContext.request.contextPath}/img/common/pic_user.gif">
						</s:if>
						<s:else>
							<img alt="image" id="userImg" width="150" height="150"
								style="border-radius:5px;"
								src="${pageContext.request.contextPath}${session.imgPath}">
						</s:else>
						<div style="width:100%;padding-top:10px;">
							<a class="mini-button" onclick="openUserImg()">更改头像</a> <a
								class="mini-button" onclick="openPwd()">修改密码</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-9" style="float:right;position:relative;">
			<div class="da-panel-widget">
				<h3>权限一览</h3>
				<div id="ProwerHtml" style="min-height:500px;height:500px;">
					<div id="MyPrower" class="mini-treegrid" style="width:100%;height:495px;"
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
	<script>
	mini.parse();
	$.ajax({
		url : encodeURI("${pageContext.request.contextPath}/admin/findHasRightMenus.action"),
		cache : false,
		async : false,
		success : function(MenuText) {
			treeData=mini.arrayToTree(mini.decode(MenuText),"children","id","pid");
			getMenu();
		}
	});
	
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
	function getMenu() {
		mini.get("MyPrower").setData(treeData);
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