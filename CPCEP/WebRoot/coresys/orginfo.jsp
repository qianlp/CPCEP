<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>组织机构管理</title>
<meta name="description" content="组织机构管理">
<meta name="content-type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/js/miniui/scripts/bootView.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 1;
	width: 100%;
	height: 100%;
	overflow: hidden;
}

.header {
	background: url(${pageContext.request.contextPath}/js/miniui/demo/header.gif) repeat-x 0 -1px;
}

.mini-splitter-border{
	box-sizing: border-box;
}
</style>
<script type="text/javascript">
mini.open=function(E) {
	if (!E) return;
	var C = E.url;
	if (!C) C = "";
	var B = C.split("#"),
		C = B[0];
	if (C && C[O1O1oo]("_winid") == -1) {
		var A = "_winid=" + mini._WindowID;
		if (C[O1O1oo]("?") == -1) C += "?" + A;
		else C += "&" + A;
		if (B[1]) C = C + "#" + B[1]
	}
	E.url = C;
	E.Owner = window;
	var $ = [];

	function _(A) {
		try {
			if (A.mini) $.push(A);
			if (A.parent && A.parent != A) _(A.parent)
		} catch (B) {}
	}
	_(window);
	var D = $[0];
	
	return D["mini"]._doOpen(E)
}
	
	var gType=0;
	function CloseWin(n){
		if(n==1){
			LoadOrgTree();
		}else{
			if(gDeptId!=""){
				LoadUserInfo();
			}
		}
	}

	/*
		描述:创建部门树
	*/
	function LoadOrgTree(){
		//异步加载数据，去掉数据最前端的逗号，使得tree数据格式正确。
		$.ajax({url:"${pageContext.request.contextPath}/admin/findAllDeptsJson.action",cache:false,dataType:"text",success:function(data){
			var MenuText = mini.decode(data);
			if(MenuText){
				gOrgTree.loadList(MenuText,"id","pid");
				gOrgTree.expandLevel(0);
			}else{
				gOrgTree.loadList([],"id","pid");
			}
		}});
	}
	/*描述:部门树节点点击时触发*/
	function onNodeSelect(e) {
		var node = e.node;
		//var userName = mini.get("Key").getValue();
		gDeptId = node.id+"";
		LoadUserInfo();
		//gGrid.load({"user.userName":$.trim(userName),"deptId":gDeptId});
    }
	/*描述:部门管理*/
	function goDept(e){
		var strURL="",strTitle="",deptName="",deptId="",intWidth=400,intHeight=370;
		var node = gOrgTree.getSelectedNode();
		if(node){
			deptId=node.id;
			deptName = node.text;
		}
		if(typeof(e.node)=="undefined"){
			strTitle="新建部门";
			strURL="${pageContext.request.contextPath}/coresys/dept.jsp?deptName="+deptName+"&deptId="+deptId;
			//strURL="${pageContext.request.contextPath}/admin/newDept.action?deptId="+deptId;
		}else{
			intHeight=500;
			var deptId=e.node.id;
			strTitle="维护部门";
			strURL="${pageContext.request.contextPath}/admin/findDeptById.action?deptId="+deptId;
		}
		gType=1;
		var oWinDlg=mini.get('oWinDlg');
		if(oWinDlg==null){
			oWinDlg=mini.open({
				id:"oWinDlg",
				showFooter:true,
				headerStyle:"font-weight:bold;letter-spacing:4px",
				footerStyle:"padding:5px;height:25px"
			});
		}
		var strButton='<div style="width:100%;text-align:right"><a id="btnSave" class="mini-button" plain="true" iconCls="icon-ok" onclick="goSubmit">确定</a>';
		strButton+='&nbsp;&nbsp;<a class="mini-button" plain="true" iconCls="icon-cancel" onclick="goCloseDlg(\'oWinDlg\')">取消</a></div>';
		oWinDlg.setUrl(strURL);
		oWinDlg.setTitle(strTitle);
		oWinDlg.setWidth(intWidth);
		oWinDlg.setHeight(intHeight);
		oWinDlg.setFooter(strButton);
		oWinDlg.showAtPos("center","middle");
	}
	function LoadUserInfo(){
		var userName = mini.get("Key").getValue();
		gGrid.load({"user.userName":$.trim(userName),"deptId":gDeptId});
		
	}
	
	$().ready(function(){
		LoadUserInfo();
		LoadPosTree();
	});
	
	//下载模板
	function downloadFile(){
		window.location="${pageContext.request.contextPath}/admin/downloadFile.action";
	}
	
	/*描述:职能管理*/
	/*
		描述:创建部门树
	*/
	function LoadPosTree(){
		//异步加载数据，去掉数据最前端的逗号，使得tree数据格式正确。
		$.ajax({url:"${pageContext.request.contextPath}/admin/findAllPosJson.action",cache:false,dataType:"text",success:function(data){
			var MenuText = mini.decode(data);
			if(MenuText){
				mini.get("PosTree").loadList(MenuText,"uuid","pid");
			}else{
				mini.get("PosTree").loadList([],"uuid","pid");
			}
		}});
	}
	function goPos(e){
		var strURL="",strTitle="",intWidth=400,intHeight=500;
		var node = mini.get("PosTree").getSelectedNode();
		if(typeof(e.node)=="undefined"){
			strTitle="新建职能";
			strURL="${pageContext.request.contextPath}/coresys/pos.jsp";
		}else{
			intHeight=500;
			var posId=e.node.uuid;
			strTitle="维护职能";
			strURL="${pageContext.request.contextPath}/admin/findEditPosById.action?uuid="+posId;
		}
		var oWinDlg=mini.get('oWinDlg');
		if(oWinDlg==null){
			oWinDlg=mini.open({
				id:"oWinDlg",
				showFooter:true,
				headerStyle:"font-weight:bold;letter-spacing:4px",
				footerStyle:"padding:5px;height:25px"
			});
		}
		gType=3;
		var strButton='<div style="width:100%;text-align:right"><a id="btnSave" class="mini-button" plain="true" iconCls="icon-ok" onclick="goSubmit">确定</a>';
		strButton+='&nbsp;&nbsp;<a class="mini-button" plain="true" iconCls="icon-cancel" onclick="goCloseDlg(\'oWinDlg\')">取消</a></div>';
		oWinDlg.setUrl(strURL);
		oWinDlg.setTitle(strTitle);
		oWinDlg.setWidth(intWidth);
		oWinDlg.setHeight(intHeight);
		oWinDlg.setFooter(strButton);
		oWinDlg.showAtPos("center","middle");
	}
	
	function goRemovePos(){
		var oNode = mini.get("PosTree").getSelectedNode();
		if (typeof(oNode)!="undefined") {
			if (confirm("确定删除选中职能？")) {
				var msgId=mini.loading("处理中...","");
				$.ajax({
					url: encodeURI("${pageContext.request.contextPath}/admin/delPostion.action?uuid="+oNode.uuid+"&className=Postion"),
					type:"get",
					cache: false,
					dataType:"text",
					success: function(text){
						LoadPosTree();
						mini.hideMessageBox(msgId);
					},
					error: function(){
						mini.hideMessageBox(msgId);
					}
				});
			}
		} else {
			mini.alert("请选中一个职能");
		}
	}
</script>
<style>
#split .mini-splitter-border{
	border:0px;
}
</style>
</head>
<body>
	<div class="mini-splitter" style="width:100%;height:100%;border:0"
		vertical="true" allowResize="false">
		<div size="60" showCollapseButton="true" class="header">
			<h1 style="margin:0;padding:15px;font-family:微软雅黑,黑体,宋体;">组织机构管理</h1>
			<div style="position:absolute;top:10px;right:20px;">
				<a class="mini-button mini-button-iconTop" iconCls="icon-user"
					onclick="goShowRole" plain="true" visible="true">职能管理</a>
			</div>
			<div style="position:absolute;top:35px;left:180px;font-size:12px">版本:V1.0</div>
		</div>
		<div showCollapseButton="false" style="border:0">
			<div class="mini-layout" style="width:100%;height:100%;" id="layout">
				<div region="west" style="overflow:hidden;border-bottom:0"
					showHeader="false" width="200px" minWidth="200" maxWidth="350"
					showSplitIcon="true">
					<div class="mini-toolbar"
						style="padding:2px;border-top:0;border-left:0;border-right:0;">
						<table style='width:100%;'>
							<tr>
								<td style='width:100%;white-space:nowrap;'><a
									class="mini-button" iconCls="icon-add" plain="true"
									onclick="goDept" visible="true">新增部门</a> <a class="mini-button"
									iconCls="icon-remove" plain="true" onclick="goRemoveDept()"
									visible="true">删除部门</a></td>
							</tr>
						</table>
					</div>
					<div class="mini-fit">
						<ul id="OrgTree" class="mini-tree" onnodeclick="onNodeSelect"
							onnodedblclick="goDept" textField="text" height="100%" >
						</ul>
					</div>
				</div>
				<div region="center"
					style="overflow:hidden;border-bottom:0;border-right:1">
    				<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">
						<table style='width:100%;'>
							<tr>
								<td style='width:100%;white-space:nowrap;'>
									<a class="mini-button" iconCls="icon-add" plain="true" onclick="goPerson('New')" visible="true">新增</a>
									<a class="mini-button" iconCls="icon-edit" plain="true" onclick="goPerson('Edit')" visible="true">维护</a>
									<a class="mini-button" iconCls="icon-remove" plain="true" onclick="goRemovePerson" visible="true">删除</a>
									<a class="mini-button" iconCls="icon-upload" plain="true" onclick="goImportFile" visible="true">导入</a>
									<a class="mini-button" iconCls="icon-download" plain="true" onclick="downloadFile" visible="true">模板下载</a>
								</td>
								<td style='white-space:nowrap;' id='rightGridToolBar'>
									<a class="mini-button" iconCls="icon-download" plain="true" onclick="goExportAllUser" visible="true">导出所有人员</a>
									<span class='separator'></span> 
										<label>中文姓名: </label>
										<input id="Key"	class='mini-textbox' emptyText="仅搜索部门下人员" onenter="LoadUserInfo" />
										<a class='mini-button' iconCls='icon-search' plain='true' onclick="LoadUserInfo">查询</a> 
										<a class='mini-button' id="btnRefresh" iconCls='icon-reload' plain='true' onclick='LoadUserInfo()'>刷新</a>
								</td>
							</tr>
						</table>
					</div>
					<div class="mini-fit">
						<div id="miniDataGrid" class="mini-datagrid" style="width:100%;height:100%;" borderStyle="border:0;" sizeList='[10,20,30,50,100]' pageSize='20' multiSelect='true',
							showReloadButton='false' url="${pageContext.request.contextPath}/admin/findUsersJson.action">
							<div property="columns">
								<div type='indexcolumn' width="20px"></div>
								<div type='checkcolumn' width="15px"></div>
								<div field="userPerEname" width="70" headerAlign="center" allowSort="true">英文姓名</div>
								<div field="userName" width="70" headerAlign="center" allowSort="true">中文姓名</div>
								<div field="deptName" width="120" headerAlign="center" allowSort="true">部门名称</div>
								<div field="userSex" width="30" headerAlign="center" allowSort="true">性别</div>
								<div field="userMail" width="80" headerAlign="center" allowSort="true">邮箱</div>
							</div>
						</div>
					</div>
				</div>
				<div region="east" style="overflow:hidden;border-bottom:0"
					showHeader="false" width="200px" showSplitIcon="false"
					showSplit="false" visible="false">
					<div class="mini-toolbar"
						style="padding:2px;border-top:0;border-left:0;border-right:0;">
						<table style='width:100%;'>
							<tr>
								<td style='width:100%;white-space:nowrap;'><a
									class="mini-button" iconCls="icon-add" plain="true"
									onclick="goPos" visible="true">新增职能</a> <a class="mini-button"
									iconCls="icon-remove" plain="true" onclick="goRemovePos()"
									visible="true">删除职能</a></td>
							</tr>
						</table>
					</div>
					<div class="mini-fit">
						<ul id="PosTree" class="mini-tree" onnodedblclick="goPos"
							textField="posName" idField="uuid" height="100%">
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var gOrgTree= mini.get("OrgTree");
		//装载组织树
		LoadOrgTree();
		//所有人员表格对象
		var gGrid = mini.get('miniDataGrid');
		var gDeptId = "";
		var gPosId="";
		/*
			描述:删除部门
		*/
		function goRemoveDept(){
			var oNode = gOrgTree.getSelectedNode();
			if (typeof(oNode)!="undefined") {
				if (confirm("确定删除选中部门？")) {
					var msgId=mini.loading("处理中...","");
					$.ajax({
						url: encodeURI("${pageContext.request.contextPath}/admin/deleteDept.action?deptId="+oNode.id),
						type:"get",
						cache: false,
						dataType:"text",
						success: function(text){
							LoadOrgTree();
							mini.hideMessageBox(msgId);
						},
						error: function(){
							mini.hideMessageBox(msgId);
						}
					});
				}
			} else {
				mini.alert("请选中一个部门");
			}
		}
		/*
			描述:人员管理
		*/
		function goPerson(T){
			var strURL="",strTitle="",deptId="",deptName="",strDocID="",intWidth=650,intHeight=400;
			var node = gOrgTree.getSelectedNode();
			if(node){
				deptId=node.id;
				deptName = node.text;
			}
			if(T=="New"){
				strTitle="新建人员";
				strURL="${pageContext.request.contextPath}/coresys/user.jsp?deptName="+deptName+"&deptId="+deptId;
			}else{
				var oRow = gGrid.getSelecteds();
				if(oRow.length>=1){
					if(oRow.length == 1){
						strDocID=oRow[0].userId;
						strURL="${pageContext.request.contextPath}/admin/findUserById.action?userId="+strDocID;
					}else{
						mini.alert("请只选中一行！");return;
					}
				}else{
					mini.alert("请先选中人员！");return;
				}

			}
			gType=2;
			var oWinDlg=mini.get('oWinDlg');
			if(oWinDlg==null){
				oWinDlg=mini.open({
					id:"oWinDlg",
					showFooter:true,
					headerStyle:"font-weight:bold;letter-spacing:4px;",
					footerStyle:"padding:5px;height:25px"
				});
			}
			var strButton='<div style="width:100%;text-align:right"><a id="btnSave" class="mini-button" plain="true" iconCls="icon-ok" onclick="goSubmit">确定</a>';
			strButton+='&nbsp;&nbsp;<a class="mini-button" plain="true" iconCls="icon-cancel" onclick="goCloseDlg(\'oWinDlg\')">取消</a></div>';
			oWinDlg.setUrl(encodeURI(strURL));
			oWinDlg.setTitle(strTitle);
			oWinDlg.setWidth(intWidth);
			oWinDlg.setHeight(intHeight);
			oWinDlg.setFooter(strButton);
			oWinDlg.showAtPos("center","middle");
		}
		/*
			描述:提交窗口内Form
		*/
		function goSubmit(){
			mini.get('oWinDlg').getIFrameEl().contentWindow.goSubmit();
			//LoadUserInfo();
			//goCloseDlg("oWinDlg");
		}
		/*
			描述:删除人员
		*/
		function goRemovePerson(){
			var oRows = gGrid.getSelecteds();
			if (oRows.length > 0) {
				if (confirm("确定删除选中记录？")) {
					var arrIDs = [];
					for (var intM=0,intL=oRows.length;intM<intL;intM++) {
						arrIDs.push(oRows[intM].userId);
					}
					gGrid.loading("操作中，请稍后......");
					$.ajax({
						url: "${pageContext.request.contextPath}/admin/deleteUsers.action",
						type:"post",
						cache: false,
						data:{userIds: arrIDs.join(",")},
						success: function (text) {
							LoadUserInfo();
						},
						error: function () {
						}
					});
				}
			} else {
				mini.alert("请选中一条记录");
			}
		}
		/*
			描述:关闭窗口
		*/
		function goCloseDlg(name){
			var oWinDlg=mini.get(name);
			oWinDlg.setUrl("about:blank");
			$(oWinDlg.getBodyEl()).html("");
			$(oWinDlg.getFooterEl()).html("");
			oWinDlg.hide();
			
			if(gType==1){
				LoadOrgTree();
			}else if(gType==3){
				LoadPosTree();
			}else{
				if(gDeptId!=""){
					LoadUserInfo();
				}
			}
		}
		/*
			描述:点击提交窗口内Form时,控制“确定”状态
		*/
		function goToolsBtn(status){
			var oButton=mini.get("btnSave");
			oButton.setEnabled(status);
		}
		/*
			描述:导入数据
		*/
		function goImportFile(){
			var strURL="${pageContext.request.contextPath}/coresys/importUser.jsp",strTitle="人员导入",intWidth=400,intHeight=350;
			var oWinDlg=mini.get('oWinDlg');
			if(oWinDlg==null){
				oWinDlg=mini.open({
					id:"oWinDlg",
					showFooter:true,
					headerStyle:"font-weight:bold;letter-spacing:4px",
					footerStyle:"padding:5px;height:25px"
				});
			}
			var strButton='<div style="width:100%;text-align:right"><a id="btnSave" class="mini-button" plain="true" iconCls="icon-ok" onclick="goSubmit">确定</a>';
			strButton+='&nbsp;&nbsp;<a class="mini-button" plain="true" iconCls="icon-cancel" onclick="goCloseDlg(\'oWinDlg\')">取消</a></div>';
			oWinDlg.setUrl(strURL);
			oWinDlg.setTitle(strTitle);
			oWinDlg.setWidth(intWidth);
			oWinDlg.setHeight(intHeight);
			oWinDlg.setFooter(strButton);
			oWinDlg.showAtPos("center","middle");
		}
		/*
			描述:控制角色管理区域的显示，初次显示时装载角色树。
		*/
		function goShowRole(){
			var layout = mini.get("layout");
			var bV=layout.isVisibleRegion("east")?false:true;
			layout.updateRegion("east",{visible: bV});
			if(bV){LoadRoleTree()}
		}
		/*
			描述:创建角色树
		*/
		function LoadRoleTree(){
			var oRoleTree= mini.get("RoleTree");
			//异步加载数据
			$.ajax({url:"/OA6.0/HT_Org.nsf/agtRoleRead?OpenAgent",cache:false,dataType:'text',success:function(RoleText){
				if(RoleText!=""){
					oRoleTree.loadList(mini.decode(RoleText),"id","pid");
					oRoleTree.expandLevel(0);
				}else{
					oRoleTree.loadList([],"id","pid");
				}
			}})
		}
		/*
			描述:角色新建，维护操作
		*/
		function goRole(e){
			var strURL="",strTitle="",intWidth=600,intHeight=500;
			if(typeof(e.node)=="undefined"){
				strTitle="新建角色";
				strURL="/OA6.0/HT_Org.nsf/fmRole?OpenForm";
			}else{
				if(e.node.id=="0"){return}
				strTitle="维护角色";
				strURL="/OA6.0/HT_Org.nsf/fmRole?OpenForm&Role="+encodeURI(e.node.text.replace(/＃/g,"#"));
			}
			var oWinDlg=mini.get('oWinDlg');
			if(oWinDlg==null){
				oWinDlg=mini.open({
					id:"oWinDlg",
					showFooter:true,
					headerStyle:"font-weight:bold;letter-spacing:4px",
					footerStyle:"padding:5px;height:25px"
				});
			}
			var strButton='<div style="width:100%;text-align:right"><a id="btnSave" class="mini-button" plain="true" iconCls="icon-ok" onclick="goSubmit">确定</a>';
			strButton+='&nbsp;&nbsp;<a class="mini-button" plain="true" iconCls="icon-cancel" onclick="goCloseDlg(\'oWinDlg\')">取消</a></div>';
			oWinDlg.setUrl(strURL);
			oWinDlg.setTitle(strTitle);
			oWinDlg.setWidth(intWidth);
			oWinDlg.setHeight(intHeight);
			oWinDlg.setFooter(strButton);
			oWinDlg.showAtPos("center","middle");
		}

		function goExportAllUser(){
			if(confirm("您确定需要导出人员吗？")){
				window.location = "${pageContext.request.contextPath}/admin/exportOrgUser.action";
			}
		}
		
		function goReload() {
			mini.alert("保存成功!","消息提示",function(){
				goCloseDlg("oWinDlg");
			});
		}
	</script>
</body>
</html>
