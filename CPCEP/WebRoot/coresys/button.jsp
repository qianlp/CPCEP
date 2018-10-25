<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<base href="<%=basePath%>">
<title>新建按钮</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<style type="text/css">
html,body,form {
	font-size: 12px;
	padding: 0;
	margin: 0;
	border: 0;
	height: 100%;
	overflow: hidden;
}
</style>
<script language="JavaScript" type="text/javascript">
<!--
	function goSubmit() {
		if (confirm("您确信需要保存吗？")) {
			document.forms[0].submit();
		}
	}
	function CloseWindow(action) {
		if (window.CloseOwnerWindow) {
			return window.CloseOwnerWindow(action);
		} else {
			window.close();
		}
	}
// -->
</script>
</head>

<body text="#000000" bgcolor="#FFFFFF">

	<form method="post" action="${pageContext.request.contextPath}/admin/addButton.action">
		<c:import url='/coresys/requireFields.jsp'></c:import>
		
		<div class="mini-layout" style="width:100%;height:100%;" borderStyle="border:0">
			<div title="center" region="center" style="border:0;">
				<div class="mini-tabs" activeIndex="0" style="width:99%;height:100%;margin:0 auto" plain="true">
					<div title="基本信息">
						<table border="0" cellpadding="1" cellspacing="2" style="width:95%;margin:0 auto">
							<tr>
								<td style="text-align:right">按钮名称：</td>
								<td><input name="button.btnName" value="" class="mini-textbox" style="width:100%" vtype="maxLength:6"></td>
								<td style="text-align:right">按钮提示语：</td>
								<td><input name="button.btnTitle" value="功能按钮" class="mini-textbox" style="width:100%"></td>
							</tr>
							<tr>
								<td style="width:100px;text-align:right">默认可见：</td>
								<td><input name="button.btnIsLook" value="1" class="mini-radiobuttonlist" data="[{id:'1',text:'是'},{id:'0',text:'否'}]" style="width:100%"></td>
								<td style="text-align:right">按钮图标：</td>
								<td><input name="button.btnIcon" value="" class="mini-textbox" style="width:100%"></td>
							</tr>
							<tr>
								<td style="width:100px;text-align:right">其它：</td>
								<td colspan=""><input name="button.btnStyle" value="" class="mini-textbox" style="width:100%"></td>

							</tr>
							<tr>
								<td style="text-align:right">操作函数：</td>
								<td colspan=3><textarea name="button.btnFunction" class="mini-textarea" style="width:100%;height:200px" rows="2" cols="20">btnEvent({action:'del'})</textarea></td>
							</tr>
						</table>
					</div>
					<div title="按钮权限拥有者">
						<table border="0" cellpadding="1" cellspacing="2" style="width:98%;">
							<tr>
								<td style="text-align:center;width:100px">群组成员：</td>
								<td>
									<div class="mini-listbox" style="width:300px;height:180px;"
										data="[{id:'[超级管理员]'},{id:'总经理办公室_北京国电龙高科'},{id:'哈尔滨办公室_北京国电龙高科'},{id:'公司领导_北京国电龙高科'},{id:'工程二部_北京国电龙高科'},{id:'售后服务部_北京国电龙高科'},{id:'研发部_北京国电龙高科'},{id:'顾问_北京国电龙高科'}]">
										<div property="columns">
											<div type="indexcolumn" width="5px"></div>
											<div field="id">名称</div>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td style="text-align:center;width:100px">个人成员：</td>
								<td>
									<div class="mini-listbox" style="width:300px;height:180px;"
										data="[{id:'admin'},{id:'于京'},{id:'刘大壮'},{id:'张媛媛'},{id:'王帅'},{id:'于京'},{id:'测试001'},{id:'测试003'},{id:'测试002'},{id:'张琪'},{id:'张士明'},{id:'任立立'},{id:'肖凤娟'},{id:'谭国俊'},{id:'杨阳'},{id:'董彬'},{id:'田岩'},{id:'陆叶'},{id:'刘庆威'},{id:'杜大全'},{id:'陆宝宽'},{id:'王正阳'},{id:'王兴华'}]">
										<div property="columns">
											<div type="indexcolumn" width="5px"></div>
											<div field="id">名称</div>
										</div>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div title="south" region="south" showSplit="false" showHeader="false" height="37px" splitSize=1 borderStyle="border:0" style="overflow:hidden">
				<div class="mini-toolbar" style="padding:2px;border:0">
					<table style='width:100%;'>
						<tr>
							<td style='width:100%;white-space:nowrap;text-align:right'><a class="mini-button" iconCls="icon-ok" plain="true" style="display:0" onclick="goSubmit()">保存</a> <a class="mini-button"
								iconCls="icon-remove" plain="true" visible="true" onclick="goDelDoc('3554FDA2646D21B648257D31000961CB')">删除</a> <a class="mini-button" iconCls="icon-cancel" plain="true"
								onclick="CloseWindow()">关闭</a></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
