<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
	<title>${menu.menuName}</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<link rel="stylesheet" type='text/css' href="../css/form/loaders.css" />
	<%@include file="../resource.jsp" %>
	<script src="../js/form/noFlow.js" type="text/javascript"></script>
</head>
<body text="#000000" bgcolor="#FFFFFF"  style='padding:0px;width:100%;height:100%;background:#f3f3f3;'>
<div id="bg"></div>
<div class="loader" id="bg-loader">
	<div class="loader-inner ball-spin-fade-loader">
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
    </div>
</div>
<form method="post" action="${pageContext.request.contextPath}${menu.actionAddress}" name="form1" id="form1" >
<%@include file="../workflow/briefField.jsp" %>
<script src="../js/file/upLoad.js" type="text/javascript"></script>
<div id="PageBody">
	<jsp:include page="..${menu.pageSubAddress}" />
</div>
<%@include file="../toolbar/noFlowFoot.jsp" %>
</form>
</body>
</html>
