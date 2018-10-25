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
	body{
		padding:0px;
		overflow:hidden;
		margin:0;padding:0;
		background:#fff;
	}
	
	#WFOrgTree div{margin:0;padding:0;border:0;text-align:left}
	tr{height:30px;font-size:12px}
	table,td{border:1px solid #cdcdcd}
	input{border:0;border-bottom:1px solid #cdcdcd;width:250px}
	.DataTable{width:425px;}
	.DataTable .LabelText{text-indent:10px}
</style>
<script>
	var gDir="${pageContext.request.contextPath}";
	var gLoginUser="${sessionScope.user.userName}";
	var gImgList=[];
</script>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/admin/updatePwd.action" name="form1" id="form1" enctype="multipart/form-data">
	<div style="display:none">
		<input name="userId" type="text" value="${session.user.uuid}" />
	</div>
	
<!--表单内容开始-->
<TABLE class="DataTable" align="center">
<tr>
<td class="LabelText" style="width:150px">原密码：</td> 
<td class="LabelText"> 
<input name="password" value="" type="password" maxlength=256> </td>
</tr>
<tr>
<td class="LabelText">新密码：</td> 
<td class="LabelText"> 
<input name="passwordNew" value="" type="password" maxlength=256> </td>
</tr>
<tr>
<td class="LabelText">确认密码：</td> 
<td class="LabelText"> 
<input name="passwordConfirm" value="" type="password" maxlength=256>  </td>
</tr>
<!--表单内容结束-->
<!--操作按钮开始-->
	<tr>
		<td style='white-space:nowrap;text-align:right;height:35px' colspan=2>
<a class="mini-button" iconCls="icon-ok" style="display:0" id="btnSave" onclick="goSubmit()">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
</TABLE>
</form>
	<script>
	mini.parse();
	function goSubmit(){
		if($("[name=password]").val()==""){
			alert("请输入原密码！");
			return;
		}
		if($("[name=passwordNew]").val()==""){
			alert("请输入新密码！");
			return;
		}
		if($("[name=passwordConfirm]").val()==""){
			alert("请确认新密码！");
			return;
		}
		if($("[name=passwordConfirm]").val()!=$("[name=passwordNew]").val()){
			alert("请确认新密码！");
			return;
		}
		mini.mask({
	     	el: document.body,
	     	cls: 'mini-mask-loading',
	     	html: '数据提交中...'
	     });
		document.forms[0].submit();
	}
	</script>
</body>
</html>
