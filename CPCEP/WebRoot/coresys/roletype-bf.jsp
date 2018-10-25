<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<title>角色管理</title>

<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/ht/pagertree.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}

.header {
	background: url(/miniui/js/demo/header.gif) repeat-x 0 -1px;
	padding: 3px;
}

#oData .mini-splitter-border {
	border: 0px;
}

#dataTree .mini-panel-border {
	border: 0px;
}

#RoleInfo .mini-panel-border {
	border: 0px;
}

#MenuCat .mini-splitter-border {
	border-bottom: 0px;
}

.mini-grid-detailCell {
	padding: 0px;
}
</style>
<script type='text/javascript' charset="UTF-8">
	$(document).ready(function(){
		roleTypeLoad();
	});
	
	 /*
		新增角色类型&角色 
	*/
	function addFun(n) {
		if (n == 1) {
			mini.prompt("请输入角色类型：",
					"请输入", function(action, value) {
						if (action == "ok") {
							console.log();encodeURI(value)
							$.ajax({
									url:"${pageContext.request.contextPath}/admin/saveRoleType.action",
									type:"post",
									data: {roleTypeName: value },
									cache:false,
									dataType:"text",
									//contentType: "application/x-www-form-urlencoded; charset=utf-8",
									success:function(response){
										roleTypeLoad();
										mini.unmask(document.getElementById(oId));
									}
							});
						}
					});
		} else if (n == 2) {
			var rtName = "";
			if (gCurType != null) {
				rtName = gCurType.roleTypeName;
			}
			var path = "${pageContext.request.contextPath}/coresys/addrole.jsp?roleTypeName=" + rtName;
			var oPanel = mini.get("RoleInfo");
			oPanel.setIconCls("icon-add");
			oPanel.setUrl(path);
			mini.get("oData").expandPane(2);
		}
	}
</script>
</head>
<body style="margin:0;width:100%;height:100%;"
	onselectstart='return false' oncontextmenu='return true'>
	<div class="mini-fit">
		<div id="MenuCat" class="mini-splitter"
			style="width:100%;height:100%;">
			<div id="TypeFit" size="240" showCollapseButton="true">
				<div class="mini-toolbar"
					style="padding:2px;border-top:0;border-left:0;border-right:0;">
					<table style="width:100%;">
						<tr>
							<td style="width:100%;"><a class="mini-button"
								iconCls="icon-add" plain="true" onclick="addFun(1)">增加</a> <a
								class="mini-button" iconCls="icon-remove" plain="true"
								onclick="DelRoleType()">删除</a></td>
						</tr>
					</table>
				</div>
				<div class="mini-fit">
					<ul id="TypeTree" class="mini-tree" style="width:100%;"
						showTreeIcon="true" textField="text" idField="id"
						parentField="pid" resultAsTree="false">
					</ul>
				</div>
			</div>
			<div showCollapseButton="true">
				<div id="oData" class="mini-splitter"
					style="width:100%;height:100%;">
					<div size="400px">
						<div class="mini-toolbar"
							style="padding:2px;border-top:0;border-left:0;border-right:0;">
							<table style="width:100%;">
								<tr>
									<td style="width:100%;"><a class="mini-button"
										iconCls="icon-add" plain="true" id="oAddG" onclick="addFun(2)">增加</a>
										<a class="mini-button" iconCls="icon-remove" plain="true"
										onclick="goRemoveRole()">删除</a></td>
									<td style="white-space:nowrap;"><input id="key"
										class="mini-textbox" emptyText="请输入名称" style="width:150px;" onenter="onKeyEnter"/>
										<a class="mini-button" iconCls="icon-search" plain="true"
										onclick="search()" >查询</a></td>
								</tr>
							</table>
						</div>
						<div class="mini-fit">
							<div id="dataTree" class="mini-pagertree"
								style="width:100%;height:100%;" url="" allowSelect="true"
								multiSelect="true" onshowrowdetail="onShowRowDetail"
								allowResize="false" parentField="pid" resultAsTree="true"
								showCheckBox="true" idField="id" treeColumn="role"
								pageSize="20" fitColumns="false">
								<div property="columns">
									<div type="checkcolumn" width="80px">选择</div>
									<div type="expandcolumn" name="colCell">#</div>
									<div name="RoleName" field="text" width="350px"
										allowResize="false" renderer="CommonRowLink">&nbsp;&nbsp;&nbsp;&nbsp;角色名称</div>
								</div>
							</div>
							<div id="RoleGrid_Form" style="display:none;">
								<div id="subRole_grid" class="mini-datagrid" multiSelect="true"
									onSelect="SubRowSelect" style="width:100%;height:200px;">
									<div property="columns">
										<div type="checkcolumn" width="80px">选择</div>
										<div name="roleName" field="text" width="300px"
											renderer="CommonRowLink">&nbsp;&nbsp;&nbsp;&nbsp;角色名称</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div expanded="false" size="400px">
						<div id="RoleInfo" class="mini-panel" title="角色"
							iconCls="icon-edit" style="width:100%;height:100%"
							showToolbar="false" showCloseButton="false" showFooter="true"
							url="">
							<div property="footer" style="padding:3px;text-align:center">
								<a id="btnSave" class="mini-button" plain="true"
									iconCls="icon-ok" onclick="goSubmit">确定</a> &nbsp;&nbsp;<a
									class="mini-button" plain="true" iconCls="icon-cancel"
									onclick="CloseWindow">取消</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
	mini.parse();
	var gCurType=null,gIntM="",gPrjId="",gTypeTree="", oDataTree="",gRoleGrid = null;
	function search() {
		oDataTree=mini.get("dataTree");
		var key = mini.get("key").getValue();
		if(key == ""){
			console.log(key);
			oDataTree.load();
		}else{
			var rows = oDataTree.findRows(function(row){
			console.log(row.text == key);
    		if(row.text.indexOf(key)>-1) return true;
		});	
			oDataTree.loadData(rows);
		}
	}	
	function onKeyEnter(e){
		search();
	}
   /*
    	角色类型加载
	*/	
	function roleTypeLoad(){
		//异步加载数据，去掉数据最前端的逗号，使得tree数据格式正确。
		$.ajax({url:"${pageContext.request.contextPath}/admin/findAllRoleTypeJson.action",cache:false,dataType:"text",success:function(data){
			var MenuText = mini.decode(data);
			if(data.indexOf(",")>-1){
					gTypeTree=mini.get("TypeTree");
					gTypeTree.loadList(MenuText);
					gTypeTree.on("nodedblclick",function(e){
					gCurType=e.node;
				});
					gTypeTree.on("nodeclick",function(e){
					gCurType=e.node;
					mini.get("oData").collapsePane(2);
					roleLoad();
				});
			}
		}});
	}
	
	/*
 	 	角色加载
 	 */
	function roleLoad(){
		if(gCurType==null){ return }
		oDataTree=mini.get("dataTree");
		if(gCurType.roleTypeName=="项目角色"){
			oDataTree.showColumn(oDataTree.getColumn("colCell"));
			mini.get("oAddG").disable();
			gIntM="1";
		}else{
			oDataTree.hideColumn(oDataTree.getColumn("colCell"));
			mini.get("oAddG").enable();
			gIntM="";
		}
		oDataTree.setUrl('${pageContext.request.contextPath}/admin/findAllRoleJson.action');
		//oDataTree.setIconCls("icon-node");
		//绑定事件
		//oDataTree.on("beforeload",ServerBeforeLoad);
		oDataTree.load();
	}
	
	
	//div加载
	function loadding(oId,oMsg){
		mini.mask({
        	el: document.getElementById(oId),
        	cls: 'mini-mask-loading',
        	html: oMsg==""?"请稍候...":oMsg
    	});
	}

	//消息提示
	function showTips(state){
    	mini.showTips({
       		content: "<b>成功</b> <br/>操作成功",
        	state: state,
        	x: "center",
        	y: "top",
        	timeout: 2000
    	});
	}
		
   /*
		删除角色类型
	*/
	function DelRoleType() {
		gTypeTree = mini.get("TypeTree");
		var node = gTypeTree.getSelectedNode();
		if (node.roleTypeId == '') {
			return
		}
		mini.confirm(
			"确认要删除该类型吗？",
			"操作提示", function(action) {
			if (action == "ok") {
			var path = "${pageContext.request.contextPath}/admin/deleteRoleType.action";
			$.post(path, {
				roleTypeId : node.id
				}, function(response) {
					showTips("success");
					roleTypeLoad();
				})
			}
		})

	}

	/*
 	 	编辑角色 
 	 */
	function EditRole(oId){
		if (gCurType != null) {
			rtId = gCurType.roleTypeId;
		}
		oDataTree = mini.get("dataTree");
		//var roleTypeId = gCurType.roleTypeId;
		var node = oDataTree.getSelecteds();
		var roleId = node[0].id;
		var path="${pageContext.request.contextPath}/admin/findRoleById.action?role.uuid="+roleId;
		var oPanel=mini.get("RoleInfo");
		oPanel.setIconCls("icon-edit");
		oPanel.setUrl(path);
		mini.get("oData").expandPane(2);
	}
	
	/*
	 	描述:角色删除
	 */
	function RemoveRole(){
		oDataTree=mini.get("dataTree");
		var NArr = oDataTree.getSelecteds();
		if(gIntM=="1" || gIntM=="2"){
			if(NArr.length>0){
				if (confirm("确定删除选中记录？")) {
					var arrIDs = [];
					for (var intM=0,intL=NArr.length;intM<intL;intM++) {
						arrIDs.push(NArr[intM].roleId);
					}
					oDataTree.loading("操作中，请稍后......");
					$.ajax({
						url: "${pageContext.request.contextPath}/admin/deleteRoles.action",
						type:"post",
						cache: false,
						data:{roles: arrIDs.join(",")},
						success: function (text) {
							roleLoad();
						},
						error: function () {
						}
					});
					}
			}else{
				mini.alert("请选中一条记录");
			}
		}else{
			RemoveRole(NArr);
		}
	}
	
	//删除角色
	function goRemoveRole(){
		oDataTree=mini.get("dataTree");
		var NArr = oDataTree.getSelecteds();
		if(NArr.length>0){
			if (confirm("确定删除选中记录？")) {
				var arrIDs = [];
				for (var intM=0,intL=NArr.length;intM<intL;intM++) {
						arrIDs.push(NArr[intM].id);
					}
					oDataTree.loading("操作中，请稍后......");
					$.ajax({
						url: "${pageContext.request.contextPath}/admin/deleteRoles.action",
						type:"post",
						cache: false,
						data:{id: arrIDs.join(",")},
						success: function (text) {
							roleLoad();
						},
						error: function () {
						}
					});
					}
			}else{
				mini.alert("请选中一条记录");
			}
	}
	
		
	/*
	 	关闭
 	 */
	function CloseWindow(){
		mini.get("oData").collapsePane(2);
	}
    
	/*
	 	显示项目角色
 	 */
	function onShowRowDetail(e) {
    	var grid = e.sender;
    	var row = e.record;
    	gIntM="2";
    	gPrjId=row.uid;
    
    	if(gRoleGrid==null){ gRoleGrid=document.getElementById("RoleGrid_Form"); }
    
    	var td = grid.getRowDetailCellEl(row);
    	td.appendChild(gRoleGrid);
    	$(gRoleGrid).show();
    
    	var subRole = mini.get("subRole_grid");
    	subRole.setUrl('/'+gCurDB+'/agtGetPrjRoleList?OpenAgent');
		//绑定事件
    	subRole.on("beforeload",ServerBeforeLoad);
    	subRole.load();
	}
	
	/*
		 描述：
			 用于解决每行链接的打开。
	 	参数：
	 		cell：单元格对象
	 */
	function CommonRowLink(cell){
		if(cell.row.pid=="-1"){
			return cell.value;
		}
		return '<a href="javascript:EditRole(\''+cell.row.unid+'\');">' + cell.value + '</a>';
	}
	
	//提交
	function goSubmit(){
		var oPanel=mini.get("RoleInfo");
		var iframe = oPanel.getIFrameEl(); 
		iframe.contentWindow.goSubmit();
		showTips("success");
		roleLoad();
	}
	
</script>
</body>
</html>

