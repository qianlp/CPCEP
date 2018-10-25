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
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>维护部门</title>
<script
	src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js"
	type="text/javascript"></script>
<style type="text/css">
html,body,form {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
}
</style>
<script type="text/javascript">
	function goSubmit() {
		with (gForm) {
			var strDeptName = mini.getbyName("dept.deptName").getValue();
			if (strDeptName == "") {
				alert("请填写部门名称！");
				return false;
			} 
			parent.goToolsBtn(false);
			if (confirm("您确信需要保存吗？")) {
				//赋值为0，为了阻止，文档提交就保存。希望通过提交代理后才保存，这样视图中的文档此不会变化，为了处理多人并发保存。
				//同时也为了防止在客户端修改这个值时。导致代理提交就保存
				submit();
			} else {
				parent.goToolsBtn(true);
			}
		}
	}
	/*
	 描述：
	     根据父节点id选择同层级部门
	 参数：
	  e：部门树节点对象
	 */
	function loadSameLevDept(e) {
		var deptPid = mini.getbyName("dept.deptPid").getValue();
		if (deptPid == "无") {
			deptPid = "-1";
		}
		if (deptPid) {
			e.sender
					.load(encodeURI("${pageContext.request.contextPath}/admin/findChildDeptsJson.action?dept.deptPid="
							+ deptPid));
		}
	}

	function checkPosition(obj) {
		var objCombox = mini.getbyName("sameLevelNodeId");
		if (obj.value == "0") {
			objCombox.setValue();
			objCombox.setText();
			mini.getbyName("sameLevelNodeId").setData([]);
			objCombox.disable();
		} else {
			objCombox.enable();
		}
	}

	/*
	 描述：
	 选择部门后给页面的设置域的实际值和显示值
	 参数：
	 e：部门树节点对象
	 */
	function SelectOrgDept(e) {
		e.sender.setValue(e.node["id"]);
		e.sender.setText(e.node["text"]);
		mini.getbyName("sameLevelNodeId").setValue();
		mini.getbyName("sameLevelNodeId").setText();
		mini.getbyName("sameLevelNodeId").setData([]);
	}
	/*
	 描述：
	 选择部门中加入清除图标，点击清除值
	 参数：
	 e：部门树节点对象
	 */
	function onCloseClick(e) {
		e.sender.setValue("-1");
		e.sender.setText("无");
		mini.getbyName("sameLevelNodeId").setValue();
		mini.getbyName("sameLevelNodeId").setText();
		mini.getbyName("sameLevelNodeId").setData([]);
	}
	
	function onCloseClickuser(e) {
		e.sender.setValue("");
		e.sender.setText("");
	}
	
</script>


</head>

<body text="#000000" bgcolor="#FFFFFF">

	<form method="post"
		action="${pageContext.request.contextPath}/admin/updateDept.action"
		name="addNewDept">
		<input type="hidden" name="__Click" value="0">
		<script type="text/javascript">
			var gForm = document.forms[0], gIsNewDoc = 1;
		</script>
		<div style="display:none">
			<input name="dept.uuid" value="${dept.uuid }"
				class="mini-textbox" /> <input name="dept.deptPid"
				value="${dept.deptPid }" class="mini-textbox" />
		</div>
		<table border="0" cellpadding="1" cellspacing="2"
			style="width:98%;margin:0 auto">
			<tr>
				<td align="right" style="width:120px">部门名称：</td>
				<td><input name="dept.deptName" value="${dept.deptName }"
					class="mini-textbox" style="width:100%;"></td>
			</tr>
			<tr>
				<td align="right" style="width:120px">部门简称：</td>
				<td><input name="dept.sortDeptName"
					value="${dept.sortDeptName }" class="mini-textbox"
					style="width:100%;"></td>
			</tr>
			<tr>
				<td align="right" style="width:120px">部门代字(中)：</td>
				<td><input name="dept.daiziCn" class="mini-textbox"
					value="${dept.daiziCn }" style="width:100%;" /></td>
			</tr>
			<tr>
				<td align="right" style="width:120px">部门代字(英)：</td>
				<td align="right"><input name="dept.daiziEn"
					class="mini-textbox" value="${dept.daiziEn }" style="width:100%;" /></td>
			</tr>
			<tr>
				<td align="right" style="width:120px">部门负责人：</td>
				<td align="right"><input name="dept.deptUserId" value="${dept.deptUserId}" 
					 class="mini-treeselect" valueField="id" textField="text" url="${pageContext.request.contextPath}/admin/findOrgTree.action"  style="width:99%" showClose="true" oncloseclick="onCloseClickuser" title="部门负责人"></td>
			</tr>
			<tr>
				<td align="right">部门位置：</td>
				<td><label><input type="radio" name="position"
						value="0" checked style="border-width:0px"
						onclick="checkPosition(this)">保持当前位置</label> <br> <label>
						<input type="radio" name="position" value="1"
						style="border-width:0px" onclick="checkPosition(this)">在
				</label> <input name="sameLevelNodeId" value="" class="mini-combobox"
					style="width:150px;" textField="text" valueField="id"
					emptyText="请选择..." showNullItem="true" nullItemText="请选择..."
					onbeforeshowpopup="loadSameLevDept" enabled="false"
					id="sameLevelNodeId">之前</td>
			</tr>
		</table>
	</form>
</body>
</html>
