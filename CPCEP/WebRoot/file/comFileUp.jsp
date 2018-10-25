<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String msg=new String(request.getParameter("msg").getBytes("ISO-8859-1"),"utf-8");
%>
<!DOCTYPE HTML>
<html>
<head>
<title>附件页面弹出上传</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<script
	src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js"
	type="text/javascript"></script>
<script>
	function toSubmit(){
		if($("[name=upData]").val()==""){
			alert("请选择一个附件！");
			return;
		}
		mini.mask({
            el: document.body,
            cls: 'mini-mask-loading',
            html: '附件上传中...'
        });
		document.forms[0].submit();
	}
</script>
</head>
<body bgcolor="#FFFFFF">
	<form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}${param.action}">
		<input class="mini-htmlfile" name="upData" style="width:100%;"/>
		<div style="width:100%;">提示：<%=msg %></div>
		<div style="width:100%;text-align:right;">
			<a class="mini-button" iconCls="icon-ok" href="javascript:toSubmit();" >确认</a>
		</div>
	</form>
</body>
</html>

