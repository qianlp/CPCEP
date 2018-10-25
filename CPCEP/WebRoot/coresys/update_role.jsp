<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

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

<title>角色修改</title>
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
	var gArrListBox ="",addUserId="",addUserList="",oDataTree="";
	
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
			return false;
		}
		if ($("#listCategory").val() == "") {
			alert("请选择一个分类！");
			return;
		}
		$.each(gListBox.getData(),function(){
			gArrListBox.push(this.id);
		})
		if (confirm("您确信需要保存吗？")) {
			mini.mask({
	            el: document.body,
	            cls: 'mini-mask-loading',
	            html: '数据提交中...'
	        });
			document.forms[0].Members.value = gArrListBox.join(",");
			document.forms[0].submit();
		}
	}
	
	
	
	/*
	 描述：增加人员
	 */
	 gArrListBox=[];
	 function goAdd(e) {
			var Name = e.node.text;
			addUserId = e.node.id;
			if(e.node.type == "user"){
				if ($.inArray(addUserId,gArrListBox) < 0) {
					gListBox.addItem({
						id : addUserId,
						text : Name
					});
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
			gListBox.removeItem(e.item);
			//alert(userId);
			/*
			if ($.inArray(userId,gArrListBox) > -1) {
				//从已选择数组中移出所选项
				gArrListBox = $.grep(gArrListBox, function(n,i) {
					return n == userId;
				}, true);
				//从列表中移出所选项
			}
			*/
		}

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
	<form method="post" action="${pageContext.request.contextPath}/admin/updateUserToRole.action">
		<div style="display:none">
			<input name="role.listCategory" id="listCategory" value="${role.listCategory }" />
			<input name="role.uuid" value="${role.uuid }"/>
			<input name="Members"/>
			<input name="role.oldRoleDepts" 	value="${role.roleDepts}" />
			<input name="role.oldRoleMenus" 	value="${role.roleMenus}" />
			<input name="role.oldListCategory"  value="${role.listCategory }" />
		</div>
		<table border="0" cellpadding="1" cellspacing="4" style="width:98%;height:100%;margin:0 auto">
			<tr>
				<td align="right" style="width:70px">角色名称：</td>
				<td style="width:285px"><input name="role.roleName"	value="${role.roleName}" class="mini-textbox" style="width:99%"></td>
			</tr>
			<tr>
				<td align="right" style="width:85px">角色分类：</td>
				<td style="width:285px">
					<div id="ListCategory_P" class="mini-combobox" style="width:99%"
						popupWidth="250" textField="text" valueField="id" value="${role.listCategory }"
						multiSelect="true" showClose="true" oncloseclick="onCloseClick"
						url="${pageContext.request.contextPath}/admin/findAllRoleTypeJson.action"
					>
						<div property="columns">
							<div header="分类名称" field="text"></div>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td align="right" style="width:70px">关联部门：</td>
				<td ><input name="role.roleDepts" class="mini-treeselect"  multiSelect="true"
				value="${role.roleDepts}"
				url="${pageContext.request.contextPath}/admin/findAllDeptsJson.action"
				style="width:99%"></td>
			</tr>
			<tr>
				<td align="right" style="width:70px">关联菜单：</td>
				<td ><input name="role.roleMenus" class="mini-treeselect" id="roleMenus"  multiSelect="true"
				value="${role.roleMenus}"
				url="${pageContext.request.contextPath}/admin/findAllMenuList.action"
				style="width:99%"></td>
			</tr>
			<tr>
				<td align="right" style="width:70px">描述：</td>
				<td><input name="role.roleDescribe" value="${role.roleDescribe }" class="mini-textbox" style="width:99%"></td>
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
									onitemdblclick="goDel" valueField="id" textField="text"
									url="${pageContext.request.contextPath}/admin/findUserIdByRole.action?role.uuid=${role.uuid}"
									></div>
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
			
			/*
			$.ajax({
				url : "${pageContext.request.contextPath}/admin/findUserIdByRole.action?role.uuid=${role.uuid}",
				cache : false,
				dataType : "text",
				success : function(data) {
					var list= mini.decode(data);
					gArrListBox=[];
					for(var i=0;i<list.length;i++){
						gArrListBox.push(list[i].id);
					}
					gListBox.addItems(list);
				}
			});
			*/
			
			$(document).ready(function(){
				mini.get("ListCategory_P").on("itemclick", function(e) {
					var obj = e.sender;
					$("#listCategory").val(obj.getValue());
				});
			});
			
		</script>
	</form>
</body>
</html>
