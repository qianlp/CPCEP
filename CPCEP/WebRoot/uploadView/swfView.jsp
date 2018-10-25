<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String swfpath = session.getAttribute("swfpath").toString(); %>
<!DOCTYPE html >
<html>
<head>
<title>附件查看</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ht/flexpaper/flexpaper_flash.js"></script>
<style>
	html,body{
		width:100%;height:100%;padding:0px;margin:0px;overflow:hidden;
	}
</style>
</head>
<body >
<div style="width:100%;height:100%;padding:0px;margin:0px;"><a id="viewerPlaceHolder" style="width: 100%; height: 100%;padding:0px;margin:0px; display: block"></a> <script type="text/javascript">
			var fp = new FlexPaperViewer('${pageContext.request.contextPath}/js/ht/flexpaper/FlexPaperViewer',
			'viewerPlaceHolder', {
				config : {
					SwfFile : escape('<%=swfpath%>'),
					EncodeURI:true,
					Scale : 0.6,
					ZoomTransition : 'easeOut',
					ZoomTime : 0.5,
					ZoomInterval : 0.2,
					FitPageOnLoad : true,
					FitWidthOnLoad : true,
					FullScreenAsMaxWindow : false,
					ProgressiveLoading : true,
					MinZoomSize : 0.2,
					MaxZoomSize : 5,
					SearchMatchAll : false,
					InitViewMode : 'SinglePage',
					ViewModeToolsVisible : true,
					ZoomToolsVisible : true,
					NavToolsVisible : true,
					CursorToolsVisible : true,
					SearchToolsVisible : true,
					localeChain : 'zh_CN'
				}
			});
</script></div>
</body>
</html>



















