<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//request.setCharacterEncoding("GB2312");
	String roleId = request.getParameter("roleId");
	
	if (roleId != null) {
		//roleId = new String(roleId.getBytes("iso-8859-1"), "GB2312");
	} else {
		roleId = "";
	}
	String userList = request.getParameter("userList");
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>角色</title>
<script src='${pageContext.request.contextPath}/js/miniui/scripts/boot.js' type='text/javascript'></script>
<style type='text/css'>
html,body,form {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
}
</style>
<script language="JavaScript" type="text/javascript">
	var gArrListBox = [],addUserId="",addUserList="";
	var gType="${param.rtId }";
	
	function onCloseClick(e) {
	    var obj = e.sender;
		obj.setText("");
		obj.setValue("");
		$("#listCategory").val("");
	}
	function goSubmit() {
		var strRoleName = mini.getbyName("role.roleName").getValue();
		if (strRoleName == "") {
			alert("请填写角色名称！");
			return;
		}
		if ($("#listCategory").val() == "") {
			alert("请选择一个分类！");
			return;
		}
		if (confirm("您确信需要保存吗？")) {
			mini.mask({
	            el: document.body,
	            cls: 'mini-mask-loading',
	            html: '数据提交中...'
	        });
			$("#oldRoleDepts").val(mini.get("roleDepts").getValue());
			document.forms[0].Members.value = gArrListBox.join(",");
			document.forms[0].submit();
		}
	}
	/*
	 描述：增加人员
	 */
	function goAdd(e) {
		var Name = e.node.text;
		addUserId = e.node.id;
		if (e.node.type == "user") {
			if ($.inArray(addUserId, gArrListBox) < 0) {
				gListBox.addItem({
					id : addUserId,
					text : Name
				});
				gArrListBox.push(addUserId);
			} else {
				alert(Name + " 已存在！");
			}
		}

	}
	/*
	 描述：删除人员
	 */
	function goDel(e) {
		var userId = e.item.id;
		if ($.inArray(userId, gArrListBox) > -1) {
			//从已选择数组中移出所选项
			gArrListBox = $.grep(gArrListBox, function(n, i) {
				return n == userId;
			}, true);
			//从列表中移出所选项
			gListBox.removeItem(e.item);
		}

	}

	/*
	描述：关闭窗口
	 */
	function CloseWindow() {
		if (window.CloseOwnerWindow) {
			return window.CloseOwnerWindow();
		} else {
			window.close();
		}
	}
</script>
</head>
<body text="#000000" bgcolor="#FFFFFF">

	<form method="post" action="${pageContext.request.contextPath}/admin/saveUserToRole.action">
		<div style="display:none">
			<input name="OldRoleName" />
			<input name="Members" />
			<input name="role.oldRoleDepts" id="oldRoleDepts" />
		</div>
		<table border="0" cellpadding="1" cellspacing="4" style="width:98%;height:100%;margin:0 auto">
			<tr>
				<td align="right" style="width:70px">角色名称：</td>
				<td ><input name="role.roleName" class="mini-textbox" style="width:99%"></td>
			</tr>
			<tr>
				<td align="right" style="width:70px">角色分类：</td>
				<td>
					<div id="ListCategory_P" class="mini-combobox" style="width:99%"
						popupWidth="250" textField="text" valueField="id" value="${param.rtId }"
						multiSelect="true" showClose="true"  oncloseclick="onCloseClick"
						url="${pageContext.request.contextPath}/admin/findAllRoleTypeJson.action"
						>
						<div property="columns">
							<div header="分类名称" field="text"></div>
						</div>
					</div>
					<input name="role.listCategory" id="listCategory" value="${param.rtId }" style="display:none">
					<input name="role.uuid" value="" style="display:none"/>
					<input name="userList" id="userList" style="display:none"/>
				</td>
			</tr>
			<tr>
				<td align="right" style="width:70px">关联部门：</td>
				<td ><input name="role.roleDepts" class="mini-treeselect" id="roleDepts"  multiSelect="true"
				url="${pageContext.request.contextPath}/admin/findAllDeptsJson.action"
				style="width:99%"></td>
			</tr>
			<tr>
				<td align="right" style="width:70px">关联菜单：</td>
				<td ><input name="role.roleMenus" class="mini-treeselect" id="roleMenus"  multiSelect="true"
				url="${pageContext.request.contextPath}/admin/findAllMenuList.action"
				style="width:99%"></td>
			</tr>
			<tr>
				<td align="right" style="width:70px">描述：</td>
				<td><input name="role.roleDescribe" value=""
					class="mini-textbox" style="width:99%"></td>
			</tr>
			<tr>
				<td colspan=2 valign="top" align="center">
					<table border="0" cellpadding="0" cellspacing="4"
						style="width:100%">
						<tr>
							<td style="width:50%" valign="top">
								<div style="border:1px solid #808080;height:352px;width:100%;">
									<ul id="OrgTree" class="mini-tree" onnodedblclick="goAdd"
										textField="text" height="100%">
									</ul>
								</div>
							</td>
							<td style="width:50%" valign="top">
								<div id="ListBox" class="mini-listbox"
									style="width:100%;height:354px;" 
									onitemdblclick="goDel" valueField="id" textField="text"></div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
			mini.parse();
			var gListBox = mini.get("ListBox");
			var gOrgTree = mini.get("OrgTree");
			//异步加载数据，去掉数据最前端的逗号，使得tree数据格式正确。
			$.ajax({
				url : "${pageContext.request.contextPath}/admin/findOrgTree.action",
				cache : false,
				dataType : "text",
				success : function(data) {
					var MenuText = mini.decode(data);
					if (data.indexOf(",") > -1) {
						gOrgTree.loadList(MenuText, "id", "pid");
						gOrgTree.expandLevel(0);
					} else {
						gOrgTree.loadList([], "id", "pid");
					}
				}
			});
			
			$(document).ready(function() {
				mini.get("ListCategory_P").on("itemclick", function(e) {
					var obj = e.sender;
					$("#listCategory").val(obj.getValue());
				});
			})
		</script>
	</form>
</body>
</html>
