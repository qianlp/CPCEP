<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>数据导入管理</title>
<meta name="description" content="数据导入管理">
<meta name="content-type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<%@include file="../resource.jsp" %>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 1;
	width: 100%;
	height: 100%;
	overflow: hidden;
}

.header {
	background: url(${pageContext.request.contextPath}/js/miniui/demo/header.gif) repeat-x 0 -1px;
}
</style>
<script type="text/javascript">
mini.open=function(E) {
	if (!E) return;
	var C = E.url;
	if (!C) C = "";
	var B = C.split("#"),
		C = B[0];
	if (C && C[O1O1oo]("_winid") == -1) {
		var A = "_winid=" + mini._WindowID;
		if (C[O1O1oo]("?") == -1) C += "?" + A;
		else C += "&" + A;
		if (B[1]) C = C + "#" + B[1]
	}
	E.url = C;
	E.Owner = window;
	var $ = [];

	function _(A) {
		try {
			if (A.mini) $.push(A);
			if (A.parent && A.parent != A) _(A.parent)
		} catch (B) {}
	}
	_(window);
	var D = $[0];
	
	return D["mini"]._doOpen(E)
}
	
</script>
</head>
<body>
	<div class="mini-splitter" style="width:100%;height:100%;border:0" vertical="true" allowResize="false">
		<div size="60" showCollapseButton="true" class="header">
			<h1 style="margin:0;padding:15px;font-family:微软雅黑,黑体,宋体;">数据导入管理</h1>
			<div style="position:absolute;top:35px;left:180px;font-size:12px">版本:V1.0</div>
		</div>
		<div showCollapseButton="false" style="border:0">
			<div class="mini-layout" style="width:100%;height:100%;" id="layout">
				<div region="center"
					style="overflow:hidden;border-bottom:0;border-right:1">
					<div class="mini-toolbar"
						style="padding:2px;border-top:0;border-left:0;border-right:0;">
						<table style='width:100%;'>
							<tr>
								<td id="userNone" style='width:100%;white-space:nowrap;'>
									<a class="mini-button" iconCls="icon-upload" plain="true" onclick="goImportFile" visible="true">导入模块数据</a>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		function goSubmit(){
			mini.get('oWinDlg').getIFrameEl().contentWindow.goSubmit();
		}

		function goCloseDlg(name){
			var oWinDlg=mini.get(name);
			oWinDlg.setUrl("about:blank");
			$(oWinDlg.getBodyEl()).html("");
			$(oWinDlg.getFooterEl()).html("");
			oWinDlg.hide();
		}

		function goToolsBtn(status){
			var oButton=mini.get("btnSave");
			oButton.setEnabled(status);
		}
	
		/*
			描述:导入数据
		*/
		function goImportFile(){
			var strURL="${pageContext.request.contextPath}/coresys/importExcel.jsp",strTitle="数据导入",intWidth=400,intHeight=350;
			var oWinDlg=mini.get('oWinDlg');
			if(oWinDlg==null){
				oWinDlg=mini.open({
					id:"oWinDlg",
					showFooter:true,
					headerStyle:"font-weight:bold;letter-spacing:4px",
					footerStyle:"padding:5px;height:25px"
				});
			}
			var strButton='<div style="width:100%;text-align:right"><a id="btnSave" class="mini-button" plain="true" iconCls="icon-ok" onclick="goSubmit">确定</a>';
			strButton+='&nbsp;&nbsp;<a class="mini-button" plain="true" iconCls="icon-cancel" onclick="goCloseDlg(\'oWinDlg\')">取消</a></div>';
			oWinDlg.setUrl(strURL);
			oWinDlg.setTitle(strTitle);
			oWinDlg.setWidth(intWidth);
			oWinDlg.setHeight(intHeight);
			oWinDlg.setFooter(strButton);
			oWinDlg.showAtPos("center","middle");
		}
		
	</script>
</body>
</html>
