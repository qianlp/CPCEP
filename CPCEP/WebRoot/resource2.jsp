<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%	String path=request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<!-- 这里主要用于上传相关的引用 -->
 	<script src='${pageContext.request.contextPath}/js/ht/swfupload/jquery-1.10.2.min.js' type='text/javascript'></script>
 	<link href="${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/default/miniui.css" rel="stylesheet" type="text/css" />
 	<link href="${pageContext.request.contextPath}/js/miniui/scripts/miniui/themes/icons.css" rel="stylesheet" type="text/css" />
   	<script src="${pageContext.request.contextPath}/js/miniui/scripts/miniui/miniui.js" type="text/javascript" ></script>
	<link rel="stylesheet" type='text/css' href="${pageContext.request.contextPath}/css/ht/global.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/ht/cssBtnDojo.css" />
	<link href="${pageContext.request.contextPath}/css/ht/form.css" rel="stylesheet" type="text/css"/>
	<link href='${pageContext.request.contextPath}/css/ht/upload.css' rel='stylesheet' type='text/css'/>
	<link href='${pageContext.request.contextPath}/js/ht/swfupload/css/swfupload.css' rel='stylesheet' type='text/css'/>
	<script src='${pageContext.request.contextPath}/js/ht/swfupload/swfupload.package.js' type='text/javascript'></script>
	<script src='${pageContext.request.contextPath}/js/ht/swfupload/handlers.js' type='text/javascript'></script>
	<script src="${pageContext.request.contextPath}/js/base/htoa_base.js" type="text/javascript"></script>
	<!-- 去掉jsp页面缓存-->
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">