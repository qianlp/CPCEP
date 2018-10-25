<%@ page language="java" contentType="text/html; charset=Utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>系统界面</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<link href="${pageContext.request.contextPath}/css/common/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/home/style.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/home/default/BgSkin.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/js/miniui/scripts/boot.js" type="text/javascript"></script>
<style type="text/css">
	.navbar{
		margin-bottom:8px;
	}
	body {
		margin: 0;
		padding: 0;
		border: 1;
		width: 100%;
		height: 100%;
		overflow: hidden;
	}
	.mini-layout-region-body {
		overflow:hidden;
	}
	.mini-layout-region-center{
		border:0px;
	}
</style>
</head>
<body>
<div id="layout1" class="mini-layout" style="width:100%;height:100%;"  borderStyle="border:solid 1px #aaa;">
    <div title="north" allowResize="false" region="north" showheader="false" height="64" showSplitIcon="false" >
    	<%@include file="menuTemplate.jsp" %>
    </div>
    <div showProxyText="true" showheader="false" region="west" width="230" showSplitIcon="true">
        <div id="leftTree" class="mini-outlooktree" resultAsTree="false" onnodeclick="onNodeSelect"
            		idField="id" parentField="pid" textField="text" borderStyle="border:0"
        		>
        		</div>
    </div>
    <div title="center" region="center"  >
    	<div id="mainTabs" class="mini-tabs" activeIndex="0"
						style="width:100%;height:100%;overflow:hidden;" plain="false"
						 contextMenu="#tabsMenu">
					</div>
					<ul id="tabsMenu" class="mini-contextmenu"
						onbeforeopen="onBeforeOpen">
						<li onclick="closeTab">关闭标签页</li>
						<li onclick="closeAllBut">关闭其他标签页</li>
						<li onclick="closeAllButFirst">关闭所有标签页</li>
					</ul>
    </div>
</div>


	<script>
		mini.parse();
		var treeData = null;
		var tabs=mini.get("mainTabs");
		$("#userInfo").css("right", "5px");
		$("#user_login_card").css("right","10px");
		$.ajax({
			url : encodeURI("${pageContext.request.contextPath}/admin/findHasRightMenus.action"),
			cache : false,
			async : false,
			success : function(MenuText) {
				treeData = mini.arrayToTree(mini.decode(MenuText), "children",
						"id", "pid");
				initMenu();
				setMenu("${param.menuId}");
			}
		});

		function initMenu() {
			var htmlStr = "";
			htmlStr+="<li title='首页'>";
			htmlStr+="<a href=\"javascript:location.href='${pageContext.request.contextPath}/index.jsp';\" style='padding-top:9px;padding-bottom:2px;color:#666666;font-weight:bold;'>";
			htmlStr+="<img src=\"${pageContext.request.contextPath}/img/menu/home.png\" width=\"28px\" height=\"28px\"></img>";
			htmlStr+="首页";
			htmlStr+="</a></li>";
			htmlStr+="</a></li>";
			$.each(treeData,function(i) {
				if (i > 5) {
					htmlStr+="<li title='更多'>";
					htmlStr+="<a href=\"javascript:showMenuCard();\" style='padding-top:9px;padding-bottom:2px;color:#666666;font-weight:bold;'>";
					htmlStr+="<img src=\"${pageContext.request.contextPath}/img/menu/icon"+i+".png\" width=\"28px\" height=\"28px\"></img>";
					htmlStr+="更多";
					htmlStr+="</a></li>";
					htmlStr+="</a></li>";
					return false;
				}
				htmlStr += "<li title='"+this.text+"'>";
				htmlStr += "<a href=\"javascript:setMenu('"+this.id+"');\" style='padding-top:9px;padding-bottom:2px;color:#666666;font-weight:bold;'>";
				htmlStr += "<img src=\"${pageContext.request.contextPath}/img/menu/icon"+i+".png\" width=\"28px\" height=\"28px\"></img>";
				htmlStr += subText(this.text);
				htmlStr += "</a></li>";
				htmlStr += "</a></li>";
			})
			$("#navBar").append(htmlStr);
			initSubMenu();
		}

		function initSubMenu(){
			var k=1,o=1;
			$("#menu_body").append("<ul id=\"navBar"+k+"\" class=\"navbar main-nav nav-collapse nav\" style=\"width:100%;text-align:left;padding-left:25px;height:63px;\"></ul>");
			var htmlStr = "";
			var intCar=1;
			for(var b=6;b<treeData.length;b++){
				if(o>8){
					intCar++;
					$("#navBar"+k).append(htmlStr);
					htmlStr="";
					k++;
					o=1;
					$("#menu_body").append("<ul id=\"navBar"+k+"\" class=\"navbar nav\" style=\"width:100%;text-align:left;padding-left:25px;height:63px;\"></ul>");
				}
				htmlStr += "<li title='"+treeData[b].text+"'>";
				htmlStr += "<a href=\"javascript:setMenu('"+treeData[b].id+"');\" style='padding-top:9px;padding-bottom:2px;color:#666666;font-weight:bold;'>";
				htmlStr += "<img src=\"${pageContext.request.contextPath}/img/menu/icon8.png\" width=\"28px\" height=\"28px\"></img>";
				htmlStr += subText(treeData[b].text);
				htmlStr += "</a></li>";
				htmlStr += "</a></li>";
				o++;
			}
			$("#navBar"+k).append(htmlStr);
			$("#menu_body").css("height",(63*intCar)+"px");
		}

		function setMenu(id){
			tabs.removeAll();
			$.each(treeData,function(i) {
				if(this.id==id){
					mini.get("leftTree").setData([this]);
					if(!$("#menu_card").is(":hidden")){
						$("#menu_card").hide();
					}
					return false;
				}
			})
		}

		function subText(str) {
		    if(str == null)
		        return "";
			if (str.length > 5) {
				return str.substr(0, 4) + ".."
			}
			return str;
		}

		function showTab(node) {
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

		/*描述:关闭窗口*/
		function goCloseDlg(name) {
			var oWinDlg = mini.get(name);
			oWinDlg.setUrl("about:blank");
			$(oWinDlg.getBodyEl()).html("");
			$(oWinDlg.getFooterEl()).html("");
			oWinDlg.hide();
			window.location.reload();
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

		function goReload() {
			mini.alert("保存成功!","消息提示",function(){
				goCloseDlg("oWinDlg");
			});
		}
	</script>
</body>
</html>
