<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>数据权限管理</title>
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}
.divP{
	width:100%;
	min-height:50px;
	border-bottom:1px solid #ccc;
}

.one{
	height:70px;
}

.mini-panel-body{
	padding:0px;
}
.mini-panel-border{
	border:0px;
}
</style>
<script type="text/javascript">
	
</script>
</head>
<body>
<div class="mini-fit">
<div class="mini-splitter" style="width:100%;height:100%;border:0" allowResize="false">
	<div size="300" showCollapseButton="false" >
		<div class="mini-fit" style="padding:2px;border-top:0;border-left:0;border-right:0;">
					<div id='menuTree' class='mini-tree' style="height:100%;" showTreeIcon='true' showCheckBox='false' checkRecursive='true' expandOnLoad='0' expandOnDblClick='false'
						url="${pageContext.request.contextPath}/admin/findAllMenuList.action" resultAsTree="false"
						onnodeclick="nodeclick"
					>
					</div>
		</div>
	</div>
	<div size="100%">
	<div id="panel1" class="mini-panel" showHeader="false" style="width:100%;height:100%;" 
		url="${pageContext.request.contextPath}/admin/findDataRightById.action?menuId="
	></div>
	</div>
</div>
</div>
<script>
	mini.parse();
	var gDir="${pageContext.request.contextPath}";
	function nodeclick(e){
		mini.get("panel1").setUrl(gDir+"/admin/findDataRightById.action?menuId="+e.node.id);
	}
</script>
</body>
</html>
