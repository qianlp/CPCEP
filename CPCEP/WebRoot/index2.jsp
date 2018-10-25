<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>系统界面</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
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
</head>
<body>
	<!--Layout-->
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
		<div class="header" region="north" height="70" showSplit="false"
			showHeader="false"  bodyStyle="overflow:hidden;">
			<!--<div style='width:548px;height:47px;margin-top:10px;margin-left:15px;background:url(/OA6.0/Home.nsf/lgk.png)'></div>-->
			<h1
				style="margin:10px auto;padding:15px;cursor:default;font-family:微软雅黑,黑体,宋体;">华腾信息管理系统
				V1.0</h1>
			<div style="position:absolute;top:18px;right:10px;">
				<a class="mini-button mini-button-iconTop" iconCls="icon-goto"
					onclick="linkmail" plain="true">个人邮箱</a> <a
					class="mini-button mini-button-iconTop" iconCls="icon-addfolder"
					onclick="linkAtt" plain="true">上传与下载</a> <a
					class="mini-button mini-button-iconTop" iconCls="icon-edit"
					onclick="changePas" plain="true">修改密码</a> <a
					class="mini-button mini-button-iconTop" iconCls="icon-close"
					onclick="onClick" plain="true">注销</a>
			</div>
			<div
				style="position:absolute;right:290px;bottom:15px;font-size:12px;line-height:25px;font-weight:normal;">
				<span style="color:Red;font-family:Tahoma"></span>选择皮肤： <select
					id="selectSkin" onchange="onSkinChange(this.value)"
					style="width:100px;">
					<option value="">-请选择-</option>
					<option value="">Default</option>
					<option value="blue">Blue</option>
					<option value="gray">Gray</option>
					<option value="olive2003">Olive2003</option>
					<option value="blue2003">Blue2003</option>
					<option value="blue2010">Blue2010</option>
					<option value="bootstrap">Bootstrap</option>
				</select>
			</div>
		</div>
		<div title="center" region="center" style="border:0;"
			bodyStyle="overflow:hidden;">
			<!--Splitter-->
			<div class="mini-splitter" style="width:100%;height:100%;"
				borderStyle="border:0;">
				<div size="180" maxSize="250" minSize="100"
					showCollapseButton="true" style="border-top:1px solid #8c8c8c">
					<div class="mini-toolbar"
						style="padding:0px;border-top:0;border-left:0;border-right:0;height:25px">
						<table style='width:100%;'>
							<tr>
								<td
									style='width:100%;white-space:nowrap;font-weight:bold;font-size:14px;text-align:center;letter-spacing:5px;'>应用导航</td>
							</tr>
						</table>
					</div>
					<div class="mini-fit">
						<ul id="leftTree" class="mini-tree" onnodeclick="onNodeSelect"
							showTreeIcon="true" style="width:100%;height:100%;"></ul>
					</div>
				</div>
				<div showCollapseButton="false" style="border:0;">
					<!--Tabs-->
					<div id="mainTabs" class="mini-tabs" activeIndex="0"
						style="width:100%;height:100%;" plain="false"
						onactivechanged="onTabsActiveChanged" contextMenu="#tabsMenu">
						<div name="first" title="首页" url="http://baidu.com"></div>
					</div>
					<ul id="tabsMenu" class="mini-contextmenu"
						onbeforeopen="onBeforeOpen">
						<li onclick="closeTab">关闭标签页</li>
						<li onclick="closeAllBut">关闭其他标签页</li>
						<li onclick="closeAllButFirst">关闭所有标签页</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var tabs = mini.get("mainTabs");
		var currentTab = null;
		/*页签打开时执行*/
		function onBeforeOpen(e) {
			currentTab = tabs.getTabByEvent(e.htmlEvent);
			if (!currentTab) {
				e.cancel = true;
			}
		}
		function changePas() {
			var url = '/OA6.0/ht_org.nsf/fmPassword?OpenForm';
			mini.open({
				title : "修改密码",
				url : url,
				width : 520,
				height : 310
			});
		}
		function linkAtt() {
		}
		function linkmail() {
		}
		var tree = mini.get("leftTree");
		//异步加载数据，去掉数据最前端的逗号，使得tree数据格式正确。
		$.ajax({
			url : encodeURI("${pageContext.request.contextPath}/admin/findHasRightMenus.action"),
			cache : false,
			async : false,
			success : function(MenuText) {
				tree.loadList(mini.decode(MenuText));
			}
		});
		tree.on("selectNode", function(node) {
			console.log(node);
		});
		function showTab(node) {
			var tabs = mini.get("mainTabs");
			var docid = node.id;
			var id = "Tab" + docid;
			var tab = tabs.getTab(id);//alert(tab)
			if (tab) {
				tabs.activeTab(tab);
				return;
			}
			var strLink = "", strTitle = node.text;
			tab = {};
			tab._nodeid = id;
			tab.name = id;
			tab.title = strTitle;
			tab.showCloseButton = true;
			strLink = "${pageContext.request.contextPath}/admin/findHasRightMenu.action?menu.uuid=" + docid;
		//	console.log(strLink);
			if (node.openstyle) {
				var objWin = window.open(strLink, "_blank", "");
				setTimeout(function() {
					objWin.focus()
				}, 100);
			} else {
				tab.url = strLink;
				tabs.addTab(tab);
				tabs.activeTab(tab);
			}
		}
		function onNodeSelect(e) {
			var node = e.node;
			var isLeaf = e.isLeaf;
			if (isLeaf) {
				showTab(node);
			} else {
				if (node.nav) {
					showTab(node)
				}
			}
		}
		function onClick(e) {
			 location.replace("${pageContext.request.contextPath}/admin/loginOut.action");
		}
		function onTabsActiveChanged(e) {
			var tabs = e.sender;
			var tab = tabs.getActiveTab();
			if (tab && tab._nodeid) {
				var node = tree.getNode(tab._nodeid);
				if (node && !tree.isSelectedNode(node)) {
					tree.selectNode(node);
				}
			}
		}
		function onSkinChange(skin) {
			mini.Cookie.set('miniuiSkin', skin);
			//mini.Cookie.set('miniuiSkin', skin, 100);//100天过期的话，可以保持皮肤切换
			window.location.reload()
		}
		function expandOnNodeClick(e) {
			//alert(e.node)
			if (e.isLeaf == false)
				tree.expandPath(e.node);
		}
		function openTab(id, url, title) {
			var tabs = mini.get("mainTabs");
			var tid = "Tab" + id;
			var tab = tabs.getTab(tid);
			if (!tab) {
				tab = {};
				tab._nodeid = id;
				tab.name = tid;
				tab.title = title;
				tab.showCloseButton = true;
				tab.url = url;
				tabs.addTab(tab);
			}
			tabs.activeTab(tab);
		}
		/*描述:关闭窗口*/
		function goCloseDlg(name) {
			var oWinDlg = mini.get(name);
			oWinDlg.setUrl("about:blank");
			$(oWinDlg.getBodyEl()).html("");
			$(oWinDlg.getFooterEl()).html("");
			oWinDlg.hide()
		}
		/*描述:点击提交窗口内Form时,控制“确定”状态*/
		function goToolsBtn(status) {
			var oButton = mini.get("btnSave");
			oButton.setEnabled(status);
		}
		/*
		描述:提交窗口内Form
		 */
		function goSubmit() {
			mini.get('oWinDlg').getIFrameEl().contentWindow.goSubmit();
		}
		/*关闭页签--开始*/
		function closeTab() {
			var msgId = mini.loading("页签正在关闭中...", "友情提示");
			if (!(currentTab.name == "first" || currentTab.name == "two")) {
				tabs.removeTab(currentTab);
			}
			setTimeout(function() {
				mini.hideMessageBox(msgId)
			}, 50);
		}
		function closeAllBut() {
			var msgId = mini.loading("页签正在关闭中...", "友情提示");
			var but = [ currentTab ];
			but.push(tabs.getTab("first"));
			but.push(tabs.getTab("two"));
			tabs.removeAll(but);
			setTimeout(function() {
				mini.hideMessageBox(msgId)
			}, 50);
		}
		function closeAllButFirst() {
			var msgId = mini.loading("页签正在关闭中...", "友情提示");
			var but = [];
			but.push(tabs.getTab("first"));
			but.push(tabs.getTab("two"));
			tabs.removeAll(but);
			setTimeout(function() {
				mini.hideMessageBox(msgId)
			}, 50);
		}
		/*关闭页签--结束*/
	</script>
</body>
</html>
