<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>权限编辑</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta name="content-type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<style type="text/css">

html,body,form {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
}

.mini-listbox-item td {
	cursor: pointer;
}

</style>
<script type="text/javascript">
	$().ready(function() {
		getHasOrgs();
		LoadOrgTree();
		LoadRoleList();
	});
	
	//角色选择
	function setItemRole(e) {
		var listbox = e.sender;
		var defaultPerson = mini.get("defaultPerson");
		var defaultAllRole = mini.get("defaultAllRole");
		if (listbox.id == "allRoleList") {
			if (defaultAllRole.findItems(listbox.getValue()).length == 0) {
				defaultAllRole.addItem(mini.clone(e.item));
			}
		} else if (listbox.id == "defaultPerson") {
			defaultPerson.removeItems(defaultPerson.findItems(listbox
					.getValue()));
		} else if (listbox.id == "defaultAllRole") {
			defaultAllRole.removeItems(defaultAllRole.findItems(listbox
					.getValue()));
		}
	}
	
	function getHasOrgs() {
		$.ajax({
			url : "${pageContext.request.contextPath}/admin/findOrgIds.action",
			cache : false,
			dataType : "text",
			data : {
				"right.docId" : "${param.docId}",
				"right.modoName" : "${param.modoName}"
			},
			type : "post",
			async : false,
			success : function(data) {
				gArrOrgId = mini.decode(data);
			}
		});
	}
	function LoadOrgTree() {
		//异步加载数据，去掉数据最前端的逗号，使得tree数据格式正确。
		$.ajax({
			url : "${pageContext.request.contextPath}/admin/findOrgTree.action",
			cache : false,
			dataType : "text",
			success : function(data) {
				var orgTree = mini.get("OrgTree");
				var orgData = mini.decode(data);
				if (orgData) {
					orgTree.loadList(orgData, "id", "pid");
					orgTree.expandLevel(0);
					//加载已选的组织列表
					var arrData = [];
					for (var i = 0; i < orgData.length; i++) {
						if (gArrOrgId.indexOf(orgData[i].id) > -1) {
							arrData.push(mini.clone(orgData[i]));
						}
					}
					mini.get("defaultPerson").addItems(arrData);
				} else {
					orgTree.loadList([], "id", "pid");
				}
				orgTree.on("nodedblclick", function(e) {
					var node = e.node;
					var defaultPerson = mini.get("defaultPerson");
					defaultPerson.addItem({
						id : node.id,
						text : node.text,
						type : node.type
					});
				});
			}
		});
	}

	/*
		描述:加载角色列表
	 */
	function LoadRoleList() {
		var path = "${pageContext.request.contextPath}/admin/findAllRoleJson.action";
		$.post(path, {}, function(data) {
			var roleList = mini.decode(data);
			var roleGrid = mini.get("allRoleList");
			if (roleList) {
				roleGrid.load(roleList);
				//加载已选的组织列表
				var arrData = [];
				for (var i = 0; i < roleList.length; i++) {
					if (gArrOrgId.indexOf(roleList[i].id) > -1) {
						arrData.push(mini.clone(roleList[i]));
					}
				}
				mini.get("defaultAllRole").addItems(arrData);
			} else {
				roleGrid.load([]);
			}
		});
	}
	
	function CloseWindow(action) {
		if (window.CloseOwnerWindow) {
			return window.CloseOwnerWindow(action);
		} else {
			window.close();
		}
	}
	function goSubmit() {
		if (confirm("您确信需要保存吗？")) {
			var orgList = mini.get("defaultPerson").data;
			var roleList = mini.get("defaultAllRole").data;
			//将权限数据json字符串保存至textarea进行保存
			$("[name='rightJson']").val(mini.encode(orgList.concat(roleList)));
			document.forms[0].submit();
		}
	}
</script>

</head>

<body text="#000000" bgcolor="#FFFFFF">
	<form method="post" action="${pageContext.request.contextPath}/admin/updateRight.action" name="addMenu">
		<div style="display:none">
			<textarea name="rightJson"></textarea>
			<input name="docId" value="${param.docId }" />
			<input name="modoName" value="${param.modoName }" />
		</div>
		<div class="mini-layout" style="width:100%;height:100%;" borderStyle="border:0">
			<div title="center" region="center" style="border:0;">
				<div id="miniTabs" class="mini-tabs" activeIndex="0" style="width:99%;height:100%;margin:0 auto" plain="true">
					<div title="文档授权">
						<table border="0" cellpadding="1" cellspacing="2" style="width:98%;">
							<tr>
								<td style="text-align:center;width:100px">默认角色名称：<br /> <font color="red">例如:公司领导</font></td>
								<td style="width:520px">
									<div class="mini-listbox" id="defaultAllRole" onitemdblclick="setItemRole" style="width:260px;height:160px;float:left" data="[]">
										<div property="columns">
											<div type="indexcolumn" width="5px"></div>
											<div field="text">名称</div>
										</div>
									</div>
									<div class="mini-listbox" id="allRoleList" onitemdblclick="setItemRole" style="width:260px;height:160px;left:10px">
										<div property="columns">
											<div type="indexcolumn" width="5px"></div>
											<div field="text">名称</div>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td style="text-align:center;width:100px">默认组织：</td>
								<td>
									<div class="mini-listbox" id="defaultPerson" onitemdblclick="setItemRole" style="width:260px;height:180px;float:left" data="[]">
										<div property="columns">
											<div type="indexcolumn" width="5px"></div>
											<div field="text">名称</div>
										</div>
									</div>
									<div class="mini-panel-border mini-grid-border" style="width:260px;height:180px;left:10px;">
										<div id='OrgTree' style="width:100%;height:100%" class='mini-tree' showTreeIcon='true' textField="text" valueField="id" resultAsTree='false' showCheckBox='false' checkRecursive='true'
											expandOnLoad='0' expandOnDblClick='false'></div>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div title="south" region="south" showSplit="false" showHeader="false" height="35px" splitSize=1 borderStyle="border:0" style="overflow:hidden">
				<div class="mini-toolbar" style="padding:2px;border:0">
					<table style='width:100%;'>
						<tr>
							<td style='width:100%;white-space:nowrap;text-align:right'><a class="mini-button" iconCls="icon-ok" plain="true" style="display:0" onclick="goSubmit()">完成</a>  <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="CloseWindow()">关闭</a></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
	<script>
		var gOrgTree, gRoleGrid;
		var oTip = new mini.ToolTip();
		oTip.set({
			target : document,
			selector : '[data-tooltip],[title]',
			onbeforeopen : function(e) {
				e.cancel = false;
			}
		});
	</script>
</body>
</html>
