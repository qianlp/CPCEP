<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'headPortrait.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="${pageContext.request.contextPath}/css/common/bootstrap.min.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/js/miniui/scripts/miniui/res/font-awesome/css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
<link href="../personstyle/se7en-font.css" media="all" rel="stylesheet"  type="text/css" />
<link href="../personstyle/isotope.css" media="all" rel="stylesheet" type="text/css" />
<link href="../personstyle/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/home/style.css" rel="stylesheet" type="text/css" />
<link href="../personstyle/home.css" media="all" rel="stylesheet" type="text/css" />
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<style type="text/css">
	body{
		padding:0px;
		overflow:hidden;
	}
</style>
</head>



<body>
	<div class="col-md-12" style="width:100%;margin:0px;padding:0px;">
		<div class="widget-container fluid-height" id="userPage"
			style="height:auto;border-radius:4px;padding:0px;">
			<div style="height:300px;">
				<div style="height:100%;width:100%;">
					<div
						style="width:225px;float:left;height:100%;border-right:1px solid #ccc">
						<div id="toolbar1" class="mini-toolbar"
							style="padding:2px;border-top:0px;border-left:0px;border-right:0px;">
							使用过的头像</div>
						<div id="imgList"
							style="width:100%;height:274px;overflow-y:scroll;padding-top:10px;padding-bottom:10px;">

						</div>
					</div>
					<div
						style="width:150px;float:left;height:100%;text-align:center;padding-top:50px;">
						<img style="border-radius:50px;" id="userImg" width="100"
							height="100" src="<计算的值>" />
						<div
							style="width:100%;text-align:center;padding-top:3px;margin-top:10px;">
							<a href="javascript:SelectImg()" title="" class="mini-button"
								onclick="">选择</a>
						</div>
						<div style="width:100%;text-align:center;padding-top:3px;">
							<a href="javascript:goSubmit()" title="" class="mini-button"
								onclick="">上传</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
