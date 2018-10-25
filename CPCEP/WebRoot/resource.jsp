<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%String path=request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<!-- 公共资源CSS,JS  -->
	<%-- 页面引入的css文件 --%>
	<tmpl:block name="page_css"></tmpl:block>
	<%-- 全局引入的js文件 --%>
	<script type='text/javascript'>
		var miniParseOnLoad = true;
	</script>
	<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/base/htoa_base.js" type="text/javascript"></script>
	<!-- 去掉jsp页面缓存-->
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<style>
		.mini-mask-background{
			opacity:0.7;
		}
	</style>