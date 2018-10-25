<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>

	<link href="${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/default/miniui.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/home/default/BgSkin.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/icons.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div style="margin: 10px">
	${view.content}
</div>

<div style="margin: 10px;text-align: center;">
	<a class="mini-button" href="javascript:void(0)" onclick="parent.signup();"><span class="mini-button-text  mini-button-icon-text "><span class="mini-button-icon mini-iconfont icon-add" style=""></span>报名</span></a>
</div>
</body>
</html>
