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
<title>按钮维护</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<style type="text/css">
html,body,form {
	font-size: 12px;
	padding: 0;
	margin: 0;
	border: 0;
	height: 100%;
	overflow: hidden;
}
</style>
<script language="JavaScript" type="text/javascript">
	mini.parse();
	function goSubmit() {
		if (confirm("您确信需要保存吗？")) {
			var orgList = mini.get("defaultPerson").data;
			var roleList = mini.get("defaultAllRole").data;
			$("[name='rightJson']").val(mini.encode(orgList.concat(roleList)));
			document.forms[0].submit();
		}
	}

	$().ready(function() {
		getHasOrgs();
		LoadOrgTree();
		LoadRoleList();
	});
	/*
	 描述:创建部门树
	 */
	function LoadOrgTree() {
		//异步加载数据，去掉数据最前端的逗号，使得tree数据格式正确。
		$.ajax({
			url : "${pageContext.request.contextPath}/admin/findOrgTree.action",
			cache : false,
			dataType : "text",
			success : function(data) {
				var orgData = mini.decode(data);
				if (orgData) {
					gOrgTree = mini.get("OrgTree");
					gOrgTree.loadList(orgData, "id", "pid");
					gOrgTree.expandLevel(0);
					//加载已选的组织列表
					var arrData = [];
					for (var i = 0; i < orgData.length; i++) {
						if (gArrOrgId.indexOf(orgData[i].id) > -1) {
							arrData.push(mini.clone(orgData[i]));
						}
					}
					mini.get("defaultPerson").addItems(arrData);
				} else {
					gOrgTree.loadList([], "id", "pid");
				}
				gOrgTree.on("nodedblclick", function(e) {
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
			gRoleGrid = mini.get("allRoleList");
			if (roleList) {
				gRoleGrid.load(roleList);
				//加载已选的组织列表
				var arrData = [];
				for (var i = 0; i < roleList.length; i++) {
					if (gArrOrgId.indexOf(roleList[i].id) > -1) {
						arrData.push(mini.clone(roleList[i]));
					}
				}
				mini.get("defaultAllRole").addItems(arrData);
			} else {
				gRoleGrid.load([]);
			}
		});
	}

	function getHasOrgs() {
		$.ajax({
			url : "${pageContext.request.contextPath}/admin/findOrgIds.action",
			cache : false,
			dataType : "text",
			data : {
				"right.docId" : $("[name='menuToBtn.uuid']").val(),
				"right.modoName" : "MenuToBtn"
			},
			type : "post",
			async : false,
			success : function(data) {
				gArrOrgId = mini.decode(data);
			}
		});

	}
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
	//删除按钮
	function goDelDoc() {
		mini.confirm("您确定删除该按钮吗？","提示",function(r){
			if (r=="ok"){
				strPath = "${pageContext.request.contextPath}/admin/delMenuBtnById.action";
			$.post(strPath, {
				"menuToBtn.uuid" : $("[name='menuToBtn.uuid']").val()
			}, function(data) {
				if(data.success){
					mini.alert(data.msg,"消息提示",function(){
					parent.goCloseDlg("oWinDlg");
				});
				//	setTimeout(parent.goCloseDlg("oWinDlg"), 3000);
				}else{
					mini.alert(data.msg);
				}
			},"json");
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
// -->
</script>
</head>

<body text="#000000" bgcolor="#FFFFFF">

	<form method="post" action="${pageContext.request.contextPath}/admin/updateMenuBtn.action">
		<div style="display:none">
			<textarea name="rightJson"></textarea>
			<input name="menuToBtn.uuid" value="${menuToBtn.uuid }" />
			<input name="menuToBtn.btnId" value="${menuToBtn.btnId }" />
			<input name="menuToBtn.menu.uuid" value="${menuToBtn.menu.uuid }" />
		</div>

		<div class="mini-layout" style="width:100%;height:100%;" borderStyle="border:0">
			<div title="center" region="center" style="border:0;">
				<div class="mini-tabs" activeIndex="0" style="width:99%;height:100%;margin:0 auto" plain="true">
					<div title="基本信息">
						<table border="0" cellpadding="1" cellspacing="2" style="width:95%;margin:0 auto">
							<tr>
								<td style="text-align:right">按钮名称：</td>
								<td><input name="menuToBtn.btnName" value="${menuToBtn.btnName }" class="mini-textbox" style="width:100%" vtype="maxLength:6"></td>
								<td style="text-align:right">按钮提示语：</td>
								<td><input name="menuToBtn.btnTitle" value="${menuToBtn.btnTitle }" class="mini-textbox" style="width:100%"></td>
							</tr>
							<tr>
								<td style="width:100px;text-align:right">默认可见：</td>
								<td><input name="menuToBtn.btnIsLook" value="${menuToBtn.btnIsLook }" class="mini-radiobuttonlist" data="[{id:'1',text:'是'},{id:'0',text:'否'}]" style="width:100%"></td>
								<td style="text-align:right">按钮图标：</td>
								<td><input name="menuToBtn.btnIcon" value="${menuToBtn.btnIcon }" class="mini-textbox" style="width:100%"></td>
							</tr>
							<tr>
								<td style="width:100px;text-align:right">其它：</td>
								<td colspan=""><input name="menuToBtn.btnStyle" value="${menuToBtn.btnStyle }" class="mini-textbox" style="width:100%"></td>

							</tr>
							<tr>
								<td style="text-align:right">操作函数：</td>
								<td colspan=3><textarea name="menuToBtn.btnFunction" class="mini-textarea" style="width:100%;height:200px" rows="2" cols="20">${menuToBtn.btnFunction }</textarea></td>
							</tr>
						</table>
					</div>
					<div title="按钮操作权限">
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
									<!--<div style="width:100%;height:30px;right:10px;padding-top:3px;">
										<a class="mini-button" iconCls="icon-ok" style="display:0" onclick="ProwerSynch()">权限同步</a>
									</div>-->
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div title="south" region="south" showSplit="false" showHeader="false" height="37px" splitSize=1 borderStyle="border:0" style="overflow:hidden">
				<div class="mini-toolbar" style="padding:2px;border:0">
					<table style='width:100%;'>
						<tr>
							<td style='width:100%;white-space:nowrap;text-align:right'><a class="mini-button" iconCls="icon-ok" plain="true" style="display:0" onclick="goSubmit()">保存</a> <a class="mini-button"
								iconCls="icon-remove" plain="true" visible="true" onclick="goDelDoc()">删除</a> <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="CloseWindow()">关闭</a></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
