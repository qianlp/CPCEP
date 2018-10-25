<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>异常处理页面</title>
<link href="../css/common/bootstrap.min.css?v=3.4.0" rel="stylesheet">
<link href="../css/common/font-awesome.min.css?v=4.3.0" rel="stylesheet">
<link href="../css/common/animate.min.css" rel="stylesheet">
<link href="../css/common/style.min.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="middle-box text-center loginscreen   animated fadeInDown" style="width:500px;max-width:500px">
	<div>
		<div>
			<img src="../img/common/logo.png" style="width:150px;height:150px;"></img>
            	</div>
		<h3>异常提示</h3>
		<div id="Msg" class="alert alert-danger" style="margin-top:20px;font-size:16px;">
			出错啦！<s:property value="exception.message"/>
		</div>
	</div>
</div>
</body>
</html>