<%@ page language="java" contentType="text/html; charset=Utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>系统登录</title>
<link href="${pageContext.request.contextPath}/homeTemplate/homeTwo/login/css/style.css" rel='stylesheet' type='text/css' />
<script src="${pageContext.request.contextPath}/homeTemplate/homeTwo/homePlugins/js/jquery-2.1.1.min.js"></script>
<style>
	body{
		width:100%;height:100%;
		overflow:hidden;
	}
</style>
</head>
<body>
<h1></h1>
<div class="login-form">
	<div class="head-info">
		<label class="lbl-1"> </label>
		<label class="lbl-2"> </label>
		<label class="lbl-3"> </label>
	</div>
	<div class="clear"> </div>
	<form id="formT" autocomplete="off" method="post" action="${pageContext.request.contextPath}/admin/login.action" name="login">
		<div style="display:none">
			<input type="text" autocomplete="off" id="uname" name="user.userName" class="text" value="" onFocus="this.value = '';"  >
		</div>
		<input type="text" autocomplete="off" class="text" value="" onFocus="this.value = '';" onBlur="$('#uname').val(this.value);"  >
		<div class="key"><input type="password" autocomplete="off" name="user.userPassword" value="" onFocus="this.value = '';"></div>
		<div class="signin" ><input type="submit" value="登&nbsp;录" style="font-size:21px;font-family:Microsoft YaHei;" ></div>
	</form>
</div>
<div class="copy-rights">
	<p>Copyright &copy; 2016.</p>
</div>
<script>
</script>
</body>
</html>