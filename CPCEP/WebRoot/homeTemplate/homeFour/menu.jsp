<%@ page language="java" contentType="text/html; charset=Utf-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html>
<head>
<title>消息中心</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/homeTemplate/homeFour/css/amazeui.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/homeTemplate/homeFour/css/admin.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/homeTemplate/homeFour/css/app.css">
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/homeTemplate/homeFour/js/echarts.min.js"></script>
<link href="${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/default/large-mode.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/pure/skin.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/homeTemplate/homeFour/js/amazeui.min.js"></script>
<style type="text/css">
	body {
		margin-top: 90px
	}
	body .search_body{border:0px solid #000;overflow:hidden;display:block;padding-top:5px;}
	.search_content,.search_title,.search_field,.search_button{float:left}
	.search_content{width:240px;margin:2px;height:30px;}
	.search_title{width:90px;text-align:right;padding-top:1px;letter-spacing:2px;height:28px;line-height:26px;}
	.search_field{width:130px;text-align:left}
	.search_button{margin:1px;}
	.search_field .mini-radiobuttonlist{padding-top:3px}
	.mini-grid-emptyText{
		font-size:15px;
		color:#ccc;
		height:200px;
		text-align:center;
	}
	.mini-panel-body{
		padding:0px;
	}
	.mini-panel-border{
		border:0px;
	}
</style>
<script>
	var gDir="${pageContext.request.contextPath}";
	var gLoginUser="${sessionScope.user.userName}";
</script>
</head>
<body data-type="index" style="">
	<%@include file="menuTemplate.jsp" %>
	<div class="tpl-page-container tpl-page-header-fixed" style="padding-top: 0px">
		<%@include file="leftTemplate.jsp" %>
		<div class="row">
            <div class="am-u-md-12 am-u-sm-12 row-mb">
            	<div class="tpl-portlet">
            		<div class="tpl-caption font-green" style="float:none;padding:0px;padding-bottom:10px;">
                    	<span id="pMenuName">${param.menuName}</span>
                    </div>
                    <div class="mini-fit">
                    <div class="mini-panel" id="pMenuFram" showHeader="false" style="width:100%;height:100%;" 
                    	url="${pageContext.request.contextPath}/admin/findHasRightMenu.action?menu.uuid=${param.menuId}"
                    >
					</div>
					</div>	
            	</div>
            </div>
        </div>
	</div>	
<script>
	var fpx = ($("body").innerWidth() - 1130) / 2;
	$(".tpl-portlet").css("min-height",($("body").innerHeight()-111)+"px");
	
	mini.parse();
	</script>
</body>
</html>