<%@ page language="java" contentType="text/html; charset=Utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>系统登录</title>
	<!--Bootstrap Stylesheet [ REQUIRED ]-->
    <link href="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/css/bootstrap.min.css" rel="stylesheet">
	<!--Nifty Stylesheet [ REQUIRED ]-->
    <link href="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/css/nifty.min.css" rel="stylesheet">
    <!--Font Awesome [ OPTIONAL ]-->
    <link href="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!--Demo [ DEMONSTRATION ]-->
    <link href="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/css/demo/nifty-demo.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/homeTemplate/homeThree/homePlugins/js/jquery-2.2.4.min.js"></script>
<style>
	body{
		width:100%;height:100%;
		overflow:hidden;
		font-family: "Microsoft Yahei";
    	font-size: 13px;
	}
</style>
</head>
<body>
	<div id="container" class="cls-container">
		<!-- BACKGROUND IMAGE -->
		<!--===================================================-->
		<div id="bg-overlay" class="bg-img img-balloon"></div>
		<!-- HEADER -->
		<!--===================================================-->
		<div class="cls-header cls-header-lg">
			<div class="cls-brand">
				<a class="box-inline" href="#"> <!-- <img alt="Nifty Admin" src="img/logo.png" class="brand-icon"> -->
					<span class="brand-title"></span>
				</a>
			</div>
		</div>
		<!--===================================================-->


		<!-- LOGIN FORM -->
		<!--===================================================-->
		<div class="cls-content">
			<div class="cls-content-sm panel">
				<div class="panel-body">
					<p class="pad-btm">系统登录</p>
					<form id="formT" autocomplete="off" method="post" action="${pageContext.request.contextPath}/admin/login.action" name="login">
						<div style="display:none">
							<input type="text" id="uname" name="user.userName"  value="">
						</div>
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon">
									<i class="fa fa-user"></i>
								</div>
								<input type="text" class="form-control input-lg" placeholder="用户名" onFocus="this.value = '';" onBlur="$('#uname').val(this.value);"  >
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon">
									<i class="fa fa-asterisk"></i>
								</div>
								<input type="password" class="form-control input-lg"
									placeholder="密码" name="user.userPassword" value="" onFocus="this.value = '';">
							</div>
						</div>
						<button class="btn btn-primary btn-lg btn-block" type="submit">登&nbsp;&nbsp;录</button>
						<div class="row">
							<div class="col-xs-12">
								&nbsp;
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>