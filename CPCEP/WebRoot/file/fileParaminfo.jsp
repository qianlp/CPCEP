<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
 <title>文件参数配置</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<%@include file="../resource.jsp" %>
	<style>
		#profile{maposition: absolute;top:50%;left:0%;}
		#profile .ptr{height:30px;font-size:12px}
		#profile,#profile .ptr .ptd{border:1px solid #cdcdcd;padding-left:5px;}
		
	</style>

</head>
<body text="#000000" bgcolor="#FFFFFF">

<form method="post" action="${pageContext.request.contextPath}/profile/updateFileParam.action" name="_fmfileMgr" id="form1">
	<input name="uuid" class="mini-textbox" style="display: none;">
	
	<table align="center" width="600px" id="profile">
	<tr class="ptr">
		<td class="ptd">可上传角色</td><td class="ptd">
			<input name="upRole"  class="mini-treeselect" required="true"  multiSelect="true" url="${pageContext.request.contextPath}/admin/findAllRoleJson.action" valueField="id" textField="text" style="width:400px;"  showClose="true" oncloseclick="onCloseClick"></td>
	</tr>
	<tr class="ptr">
		<td class="ptd">可上传人员</td><td class="ptd">
			<input name="upUser" class="mini-treeselect" required="true"  multiSelect="true" url="${pageContext.request.contextPath}/admin/findOrgTree.action" valueField="id" textField="text"   style="width:400px;" showClose="true" oncloseclick="onCloseClick" ></td>
	</tr>
	<tr class="ptr">
		<td class="ptd">可删除角色</td><td class="ptd">
			<input name="delRole"  class="mini-treeselect" required="true" multiSelect="true" url="${pageContext.request.contextPath}/admin/findAllRoleJson.action" valueField="id" textField="text"    style="width:400px;" showClose="true" oncloseclick="onCloseClick" ></td>
	</tr>
	<tr class="ptr">
		<td class="ptd">可删除人员</td><td class="ptd">
			<input name="delUser"  class="mini-treeselect"required="true"  multiSelect="true" url="${pageContext.request.contextPath}/admin/findOrgTree.action"  valueField="id" textField="text"   style="width:400px;" showClose="true" oncloseclick="onCloseClick" ></td>
	</tr>
	<tr style="height:40px">
		<td colspan=2 style="padding-right:20px;">
			<a class="mini-button" iconCls="icon-ok" style="float:right;display:1" onclick="goSubmit()">确定</a>
		</td>
	</tr>
	</table>
  </form>
  
  <script language="JavaScript" type="text/javascript">

	function goSubmit(){
		document.forms[0].submit();
	}
	
	//异步加载文件配置角色和用户数据
	function ajaxRoleUser(){
		var form = new mini.Form("#form1");
	//	form.loading();
		$.ajax({
			url:"${pageContext.request.contextPath}/profile/findAllFileParamJson.action",
			success:function(text){
					var data=mini.decode(text);//反序列化成对象  
					form.setData(data); //设置多个控件数据  
					form.unmask();
				}
			});
		}
	
		mini.parse();
		ajaxRoleUser();
		
	</script>
</body>
</html>
